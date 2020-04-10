<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-30 18:42:58
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-04-10 18:31:45
 */
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;

class Admin extends Base
{
    public function getMenus()
    {
    	$data = [];
        $arr = Db::table('permission')->where('level',0)->select();
        foreach($arr as $value)
        {
            $value['children']=[];
            $temp = Db::table('permission')->where('parent_id',$value['id'])->select();
            foreach($temp as $t)
            {
                array_push($value['children'],$t);
            }
            array_push($data,$value);
        }
        return $this->returnMsg($data,'获取菜单成功',200);
    }

    public function getUsers()
    {
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $searchValue=input('query');
        if(!$searchValue)
        {
            $arr = Db::table('user')->field('id,account,name,email,type,state')->select();
        }else
        {
            $arr = Db::table('user')->field('id,account,name,email,type,state')->where('name','like','%'.$searchValue.'%')->select();
            if(!$arr)
            {
                $arr = Db::table('user')->field('id,account,name,email,type,state')->where('account','like','%'.$searchValue.'%')->select();
            }
        }

        $data = [];
        
        foreach($arr as &$value)
        {
            switch ($value['type']) 
            {
               case '0':
                 $value['type']='管理员';
                 break;
               case '1':
                 $value['type']='学生';
                 break;
               case '2':
                 $value['type']='教师';
                 break;
            }
            switch ($value['state']) {
               case '0':
                 $value['state']=false;
                 break;
               case '1':
                 $value['state']=true;
                 break;
            }
        }
        $info=[];
        $total=count($arr);
        for ($i=$pagesize*($pagenum-1)+1; $i <=min($total,$pagesize*$pagenum) ; $i++) { array_push($info,$arr[$i-1]);
        }
        $data=['total'=>$total,'pagenum'=>$pagenum,'users'=>$info];
        return $this->returnMsg($data,'获取用户信息成功',200);
    }

    public function getUser($id)
    {
        $arr = Db::table('user')->where('id',$id)->field('id,name,account,pwd,type')->select();
        switch ($arr[0]['type']) 
        {
           case '0':
             $arr[0]['roleName']='管理员';
             break;
           case '1':
             $arr[0]['roleName']='学生';
             break;
           case '2':
             $arr[0]['roleName']='教师';
             break;
        }
         return $this->returnMsg($arr[0],'获取用户信息成功',200);
    }

    public function changeUserState()
    {
        $id=input('id');
        $state=input('state');
        if(Db::table('user')->where('id', $id)->update(['state' => $state])==0)
        {
            return $this->returnMsg([],'修改用户状态失败',400);
        }
        return $this->returnMsg([],'修改用户状态成功',200);
    }

    public function changeUserInfo($id)
    {
        $name=input('name');
        $pwd=md5(input('pwd'));
        if(Db::table('user')->where('id', $id)->update(['name' => $name,'pwd'=>$pwd])==0)
        {
            return $this->returnMsg([],'修改用户状态失败',400);
        }
        return $this->returnMsg([],'修改用户状态成功',200);
    }

    public function addUsers()
    {
        $account=input('account');
        $pwd=md5(input('pwd'));
        $email=input('email');
        $name=input('name');
        $data = ['account' => $account, 'pwd' => $pwd,'email'=>$email,'name'=>$name,'state'=>1];
        if(Db::table('user')->insert($data)==1)
        {
            return $this->returnMsg([],'添加用户成功',201);
        }
        return $this->returnMsg([],'添加用户失败',404);
    }

    public function deleteUsers($id)
    {
        if(Db::table('user')->where('id',$id)->delete()==0)
        {
            return $this->returnMsg([],'删除失败',404);
        }
        return $this->returnMsg([],'删除成功',200);
    }

    public function changeUserType()
    {
        $roleName=input('roleName');
        $id=input('id');
        switch ($roleName) 
        {
           case '管理员':
             $type=0;
             break;
           case '学生':
             $type=1;
             break;
           case '教师':
             $type=2;
             break;
        }
        if(Db::table('user')->where('id', $id)->update(['type' => $type])==0)
        {
            return $this->returnMsg([],'修改用户状态失败',400);
        }
        return $this->returnMsg([],'修改用户状态成功',200);
    }

}