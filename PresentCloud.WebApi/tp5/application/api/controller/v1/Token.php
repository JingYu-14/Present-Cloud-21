<?php
namespace app\api\controller\v1;
use think\Controller;
use app\api\validate\TokenGet;
use app\api\service\UserToken;
// 虽然service里面的token和该控制器里面的token路径不一样但是名字一样仍然会提示出错，所以需要用到别名。给token指定一个exception或validate时也不能起token
use app\api\service\Token as TokenService;

class Token extends Controller{
    public function getToken($code=''){
	    $validate=validate('TokenGet');
        $validate->goCheck();

        $ut=new UserToken($code);
        $token=$ut->getToken();
        // return $token;
        // $token是字符串但是返回给客户端的要是json数据，所以改为下面
        // 写成这样微信测试工具会一直提示appservice?t=1555849958161:1087 POST http://www.tp5.com/api/v1/token/user 500 (Internal Server Error)
        // return ['token'=>$token];
        return json($token);
    }
}