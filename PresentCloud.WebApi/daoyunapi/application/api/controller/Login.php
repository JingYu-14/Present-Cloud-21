<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-29 19:35:38
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-07-05 18:09:23
 */
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;
use think\Cookie;

class Login extends Base
{
    public function index()
    {
    	$account = input('account');
        if(!$account)
        {
            $account = input('username');
        }
    	$password = input('password');
        // mode为1表示用账号登陆，为2表示用手机号码登陆，为3表示账号或者手机不存在
        $mode=1;
        if(!Db::table('user')->where('account',$account)->find())
        {
            if(!Db::table('user')->where('mobile',$account)->find())
            {
                $mode=3;
            }else{
                $mode=2;
            }
        }
        if($mode==1){
            if(md5($password)==Db::table('user')->where('account',$account)->value('pwd'))
            {
                $type=Db::table('user')->where('account',$account)->value('type');
                $id=Db::table('user')->where('account',$account)->value('id');
                $name=Db::table('user')->where('account',$account)->value('name');
                $avatar=Db::table('user')->where('account',$account)->value('avatar');
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
                Db::table('user')->where('account',$account)->update(['token'=>$token,'expire'=>$expire]);
                return $this->returnMsg(['pathName'=>$pathName,'id'=>$id,'name'=>$name,'avatar'=>$avatar,'token'=>$token], '登录成功', 200);
            }else{
                // print(Db::table('user')->where('account',$account)->value('pwd'));
                // print('\n');
                // print(md5($password));
                // exit;
                return $this->returnMsg([], '密码错误', 404);
            }
        }else if($mode==2){
            if(md5($password)==Db::table('user')->where('mobile',$account)->value('pwd'))
            {
                $type=Db::table('user')->where('mobile',$account)->value('type');
                $id=Db::table('user')->where('mobile',$account)->value('id');
                $name=Db::table('user')->where('mobile',$account)->value('name');
                $avatar=Db::table('user')->where('mobile',$account)->value('avatar');
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
                Db::table('user')->where('mobile',$account)->update(['token'=>$token,'expire'=>$expire]);
                return $this->returnMsg(['pathName'=>$pathName,'id'=>$id,'name'=>$name,'avatar'=>$avatar,'token'=>$token], '登录成功', 200);
            }else{
                // print(Db::table('user')->where('account',$account)->value('pwd'));
                // print('\n');
                // print(md5($password));
                // exit;
                return $this->returnMsg([], '密码错误', 404);
            }
        }else{
            return $this->returnMsg([],'账号或手机不存在',400);
        }
    	/*if(md5($password)==Db::table('user')->where('account',$username)->value('pwd'))
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
    	}*/
    }
}
