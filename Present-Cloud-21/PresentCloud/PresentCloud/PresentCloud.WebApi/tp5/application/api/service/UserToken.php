<?php
namespace app\api\service;
use think\Exception;
use app\lib\exception\WxException;
use app\lib\exception\TokenException;
use app\api\model\User;
use app\lib\enum\ScopeEnum;

class UserToken extends Token{
	protected $code;
	protected $appID;
	protected $appSecret;
	protected $loginUrl;

	public function __construct($code){
		$this->code=$code;
		$this->appID=config('wx.app_id');
		$this->appSecret=config('wx.app_secret');
		$this->loginUrl=sprintf(config('wx.login_url'),$this->appID,$this->appSecret,$this->code);
	}

	public function getToken(){
		$result=curl_get($this->loginUrl);
		$result=json_decode($result,true);

		if(empty($result)){
			throw new Exception('获取openid时异常，微信内部错误');
		}else if(array_key_exists('errcode',$result)){
			throw new WxException([
				'msg'=>$result['errmsg'],
				'errorCode'=>$result['errcode']
			]);
		}else{
			$openid=$result['openid'];
			$user=User::getByOpenid($openid);
			if($user){
				$uid=$user->id;
			}else{
				$user=User::create(['openid'=>$openid]);
				$uid=$user->id;
			}

			// 准备缓存数据，scope代表权限
			$cacheValue=$result;
			$cacheValue['uid']=$uid;
			// scope=16代表app用户的权限，32代表cms用户的权限
			$cacheValue['scope']=ScopeEnum::USER;
			$cacheValue=json_encode($cacheValue);

			$key=self::generateToken();
			$expire_in=config('setting.token_expire_in');

			// 写入缓存
			$result=cache($key,$cacheValue,$expire_in);
			if(!$result){
				throw new TokenException([
					'msg'=>'服务器缓存异常',
					'errorCode'=>10005
				]);
			}
			// 返回令牌到客户端
			return $key;
		}
	}

}
