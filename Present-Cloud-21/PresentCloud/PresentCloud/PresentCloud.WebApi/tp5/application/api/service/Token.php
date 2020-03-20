<?php
namespace app\api\service;
use think\Exception;
use app\lib\exception\TokenException;
use app\lib\exception\ForbiddenException;
use app\lib\enum\ScopeEnum;
use think\Cache;

class Token{
	// 用户和CMS管理员都可以访问的权限
	public static function needPrimaryScope(){
		$scope=self::getCurrentTokenVar('scope');
        if($scope){
            if($scope>=ScopeEnum::USER){
                return true;
            }else{
                throw new ForbiddenException();
            }
        }else{
            throw new TokenException();
        }     
	}
	// 只有用户可以访问的接口权限
	public static function needExclusiveScope(){
		$scope=self::getCurrentTokenVar('scope');
        if($scope){
            if($scope==ScopeEnum::USER){
                return true;
            }else{
                throw new ForbiddenException();
            }
        }else{
            throw new TokenException();
        }     
	}

	public static function generateToken(){
		// token令牌就是一串无意义的随机字符串
		$randChars=getRandChar(32);
		// 当前时间戳
		$timestamp=time();
		// 盐，就是一组固定的随机字符串
		$salt='HHsTieBU377mJtKr';
		// 用三组字符串进行md5加密
		return md5($randChars.$timestamp.$salt);
	}

	public static function getCurrentTokenVar($key){
		// 规定token从post请求头传进来，不能通过body部分
		$token=request()->header('token');
		$vars=cache($token);
		if(!$vars){
			throw new TokenException();
		}
		// 默认的缓存是文件系统，存成字符串，但是数组比较好处理
		$vars=json_decode($vars,true);
		if(array_key_exists($key,$vars)){
			return $vars[$key];
		}else{
			throw new Exception('尝试获取的token变量不存在');
		}
	}

	public static function getCurrentUid(){
		$uid=self::getCurrentTokenVar('uid');
		return $uid;
	}
}