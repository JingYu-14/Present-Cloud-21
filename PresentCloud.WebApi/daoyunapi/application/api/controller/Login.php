<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-29 19:35:38
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-04-04 10:29:53
 */
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;

class Login extends Base
{
    public function index()
    {
    	$username = input('username');
    	$password = input('password');
    	if(md5($password)==Db::table('user')->where('account',$username)->value('pwd'))
    	{
    		return $this->returnMsg([], '登录成功', 200);
    	}else{
    		return $this->returnMsg([], '登录失败', 404);
    	}
    }

}
