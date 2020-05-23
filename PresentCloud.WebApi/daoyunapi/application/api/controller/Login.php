<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-29 19:35:38
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-05-22 17:47:10
 */
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;
use think\Cookie;

class Login extends Base
{
    public function index()
    {
    	$username = input('username');
    	$password = input('password');
        if(!Db::table('user')->where('account',$username)->find())
        {
            return $this->returnMsg([],'账号不存在',400);
        }
    	if(md5($password)==Db::table('user')->where('account',$username)->value('pwd'))
    	{
            $type=Db::table('user')->where('account',$username)->value('type');
            $id=Db::table('user')->where('account',$username)->value('id');
            $name=Db::table('user')->where('account',$username)->value('name');
            $avatar=Db::table('user')->where('account',$username)->value('avatar');
            switch ($type) 
            {
               case '0':
                 $pathName='AdminHome';
                 break;
               case '1':
                 $pathName='StudentHome';
                 break;
               case '2':
                 $pathName='TeacherHome';
                 break;
            }
            $token=md5(time());
            $expire=strtotime("+30 days");
            Db::table('user')->where('account',$username)->update(['token'=>$token,'expire'=>$expire]);
    		return $this->returnMsg(['pathName'=>$pathName,'id'=>$id,'name'=>$name,'avatar'=>$avatar,'token'=>$token], '登录成功', 200);
    	}else{
    		return $this->returnMsg([], '密码错误', 404);
    	}
    }
}
