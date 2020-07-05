<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-30 18:42:58
 * @Last Modified by:   Administrator
 * @Last Modified time: 2020-07-05 13:10:36
 */ 
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;

class Admin extends Base
{
    protected $beforeActionList = [
        'checkToken' =>  ['except'=>'addUsers,changePassword'],
    ];
    
    public function getMenus()
    {
    	$data = [];
        $arr = Db::table('permission')->where('level',0)->where('type',0)->select();
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
            $arr = Db::table('user')->field('id,account,name,email,type,state')->where('state',1)->select();
        }else
        {
            $arr = Db::table('user')->field('id,account,name,email,type,state')->where('name','like','%'.$searchValue.'%')->where('state',1)->select();
            if(!$arr)
            {
                $arr = Db::table('user')->field('id,account,name,email,type,state')->where('account','like','%'.$searchValue.'%')->where('state',1)->select();
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
        $arr = Db::table('user')->where('id',$id)->field('id,name,account,pwd,type')->where('state',1)->select();
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
            return $this->returnMsg([],'修改用户信息失败',400);
        }
        return $this->returnMsg([],'修改用户信息成功',200);
    }

    public function addUsers()
    {
        $account=input('account');
        $pwd=md5(input('pwd'));
        $email=input('email');
        $name=input('name');
        $mobile=input('mobile');
        if(input('type'))
        {
        	$type=input('type');
        }else
        {
        	$type=1;
        }

        if(Db::table('user')->where('account',$account)->find())
        {
            return $this->returnMsg([],'账号已注册',404);
        }

        if(Db::table('user')->where('mobile',$mobile)->find())
        {
            return $this->returnMsg([],'手机号已注册',404);
        }
        
        $data = ['account' => $account, 'pwd' => $pwd,'email'=>$email,'name'=>$name,'state'=>true,'type'=>$type,'exp'=>0,'mobile'=>$mobile];
        if(Db::table('user')->insert($data)==1)
        {
            return $this->returnMsg([],'添加用户成功',201);
        }
        return $this->returnMsg([],'添加用户失败',404);
    }

    public function changePassword()
    {
        $account=input('account');
        $password=md5(input('password'));
        $mobile=input('mobile');
        if(Db::table('user')->where('account',$account)->find())
        {
            if($mobile==Db::table('user')->where('account',$account)->value('mobile'))
            {
                if(Db::table('user')->where('account',$account)->update(['pwd'=>$password]))
                {
                    return $this->returnMsg([],'重置密码成功',200);
                }else{
                    return $this->returnMsg([],'重置密码失败',400);
                }

            }else{
                return $this->returnMsg([],'手机号错误',400);
            }
        }else{
            return $this->returnMsg([],'账号不存在',400);
        }
    }

    public function deleteUsers($id)
    {
        if(Db::table('user')->where('id', $id)->update(['state' => 0])==0)
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
            return $this->returnMsg([],'修改用户角色失败',400);
        }
        return $this->returnMsg([],'修改用户角色成功',200);
    }

    public function getClasses()
    {
        $id=input('id');
        if(!$id)
        {
            $arr=Db::table('class')->select();
            foreach($arr as &$value)
            {
                $value['name']=Db::table('user')->where('id',$value['tno'])->value('name');
                 if(!$value['task_id'])
                {
                    $value['task']='';
                }else
                {
                    $taskId=explode(",",$value['task_id']);
                    $taskId=$taskId[count($taskId)-2];
                    $value['task']=Db::table('task')->where('id',$taskId)->value('detail');
                }
            }
            return $this->returnMsg($arr,'获取班级信息成功',200);
        }else
        {
             return $this->returnMsg(['name'=>Db::table('class')->where('id',$id)->value('class_name')],'获取班级信息成功',200);
        }
        
    }

    public function deleteClass()
    {
        $id=input('id');
        if(Db::table('class')->where('id',$id)->delete()!=0)
        {
            return $this->returnMsg([],'删除班级成功',200);
        }else
        {
            return $this->returnMsg([],'删除班级失败',404);
        }
    }

    public function getStudents()
    {
        $id=input('id');
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $temp=[];
        $arr=Db::table('course_select')->where('class_id',$id)->column('sno');
        for($i=0;$i<count($arr);$i++)
        {
            $info=Db::table('user')->where('id',$arr[$i])->field('account,name,exp,id,state')->find();
            $info['state'] = $info['state']==0?false:true;
            array_push($temp,$info);
        }
        $total=count($temp);
        $a=[];
        for ($i=$pagesize*($pagenum-1)+1; $i <=min($total,$pagesize*$pagenum) ; $i++) { array_push($a,$temp[$i-1]);
        }
        $data=['total'=>$total,'pagenum'=>$pagenum,'students'=>$a];
        return $this->returnMsg($data,'获取学生信息成功',200);
    }

    public function getSigns()
    {
        $id=input('id');
        $uid=input('uid');
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        if(!$uid)
        {
            $arr=Db::table('sign')->where('class_id',$id)->select();
         }else
         {
             $arr=Db::table('sign')->where('class_id',$id)->where('sno',$uid)->select();
         }
       
        foreach($arr as &$value)
        {
            $value['account']=Db::table('user')->where('id',$value['sno'])->value('account');
            $value['state'] = $value['state']==0?false:true;
            $value['date']=date('Y-m-d',$value['time']);
            $value['time']=date('H-i-s',$value['time']);
        }
        $total=count($arr);
        $sign=[];
        for ($i=$pagesize*($pagenum-1)+1; $i <=min($total,$pagesize*$pagenum) ; $i++) { array_push($sign,$arr[$i-1]);
        }
        $data=['total'=>$total,'pagenum'=>$pagenum,'signs'=>$sign];
        return $this->returnMsg($data,'获取签到信息成功',200);
    }

    public function changeSignState()
    {
        $id=input('id');
        $state=input('state');
        if($state)
        {
            $state=true;
        }else{
            $state=false;
        }
        if(Db::table('sign')->where('id', $id)->update(['state' => $state])==0)
        {
            return $this->returnMsg([],'修改签到状态失败',400);
        }

        $sno=Db::table('sign')->where('id', $id)->value('sno');
        if($state)
        {
            $exp=Db::table('user')->where('id',$sno)->value('exp')+Db::table('system')->where('state',1)->value('sign_exp');
        }else{
            $exp=Db::table('user')->where('id',$sno)->value('exp')-Db::table('system')->where('state',1)->value('sign_exp');
        }
        Db::table('user')->where('id',$sno)->update(['exp'=>$exp]);

        return $this->returnMsg([],'修改签到状态成功',200);
    }

    public function getTasks()
    {
        $id=input('id');
        $taskId=Db::table('class')->where('id',$id)->value('task_id');
        $taskId=explode(",",$taskId);
        array_pop($taskId);
        $arr=Db::table('task')->where('id','in',$taskId)->where('state',1)->select();
        foreach($arr as &$value)
        {
            $value['deadline']=date('Y-m-d',$value['end_time']);
            $value['state']=$value['end_time']>time()?'进行中':'过期';
        }
        return $this->returnMsg($arr,'获取签到信息成功',200);
    }

    public function changeTaskState()
    {
        $id=input('id');
        if(Db::table('task')->where('id', $id)->update(['state' => 0])==0)
        {
            return $this->returnMsg([],'删除任务失败',400);
        }
        return $this->returnMsg([],'删除任务成功',200);
    }

    public function getTaskDetails()
    {
        $classId=input('classId');
        $taskId=input('taskId');
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $sno=Db::table('course_select')->where('cno',$classId)->column('sno');
        $arr=Db::table('task_stu')->where('task_id',$taskId)->where('sno','in',$sno)->select();
        foreach($arr as &$value)
        {
            $value['account']=Db::table('user')->where('id',$value['sno'])->value('account');
            $value['name']=Db::table('user')->where('id',$value['sno'])->value('name');
            if($value['presentation']){
                $value['presentation']='http://localhost/daoyunapi/public/uploads/'.$value['presentation'];
            }
        }
        $total=count($arr);
        $data=[];
        for ($i=$pagesize*($pagenum-1)+1; $i <=min($total,$pagesize*$pagenum) ; $i++) { array_push($data,$arr[$i-1]);
        }
        $data=['total'=>$total,'pagenum'=>$pagenum,'taskDetails'=>$data,
        'detail'=>Db::table('task')->where('id',$taskId)->value('detail')];
        return $this->returnMsg($data,'获取具体任务信息成功',200);
    }

    public function getSchools()
    {
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $searchValue=input('query');
        if(!$searchValue)
        {
            $arr = Db::table('school')->field('id,school,college,profession')->where('state',1)->select();
        }else
        {
            $arr = Db::table('school')->field('id,school,college,profession')->where('school|college|profession','like','%'.$searchValue.'%')->where('state',1)->select();
        }

        $data = [];
        $info=[];
        $total=count($arr);
        for ($i=$pagesize*($pagenum-1)+1; $i <=min($total,$pagesize*$pagenum) ; $i++) { array_push($info,$arr[$i-1]);
        }
        $data=['total'=>$total,'pagenum'=>$pagenum,'schools'=>$info];
        return $this->returnMsg($data,'获取校园信息成功',200);
    }

    public function addSchools()
    {
        $school=input('school');
        $college=input('college');
        $profession=input('profession');
        
        $data = ['school' => $school, 'college' => $college,'profession'=>$profession,'state'=>true];
        if(Db::table('school')->insert($data)==1)
        {
            return $this->returnMsg([],'添加组织成功',201);
        }
        return $this->returnMsg([],'添加组织失败',404);
    }

    public function deleteSchools($id)
    {
        if(Db::table('school')->where('id',$id)->update(['state'=>0])==0)
        {
            return $this->returnMsg([],'删除失败',404);
        }
        return $this->returnMsg([],'删除成功',200);
    }

    public function getSchool($id)
    {
        $arr = Db::table('school')->where('id',$id)->field('id,school,college,profession')->select();
         return $this->returnMsg($arr[0],'获取单条组织信息成功',200);
    }

    public function changeSchoolInfo($id)
    {
        $college=input('college');
        $profession=input('profession');
        if(Db::table('school')->where('id', $id)->update(['college' => $college,'profession'=>$profession])==0)
        {
            return $this->returnMsg([],'修改组织信息失败',400);
        }
        return $this->returnMsg([],'修改组织信息成功',200);
    }

    public function getSystems()
    {
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $searchValue=input('query');
        $arr = Db::table('system')->field('id,sign_exp,sign_dist,task_exp')->where('state',1)->select();
        $data = [];
        $info=[];
        $total=count($arr);
        for ($i=$pagesize*($pagenum-1)+1; $i <=min($total,$pagesize*$pagenum) ; $i++) { array_push($info,$arr[$i-1]);
        }
        $data=['total'=>$total,'pagenum'=>$pagenum,'systems'=>$info];
        return $this->returnMsg($data,'获取系统参数成功',200);
    }

    public function addSystems()
    {
        $sign_exp=input('sign_exp');
        $sign_dist=input('sign_dist');
        $task_exp=input('task_exp');
        
        $data = ['sign_exp' => $sign_exp, 'sign_dist' => $sign_dist,'task_exp'=>$task_exp,'state'=>1];
        if(Db::table('system')->insert($data)==1)
        {
            return $this->returnMsg([],'添加参数成功',201);
        }
        return $this->returnMsg([],'添加参数失败',404);
    }

    public function deleteSystems($id)
    {
        if(Db::table('system')->where('id',$id)->update(['state'=>0])==0)
        {
            return $this->returnMsg([],'删除失败',404);
        }
        return $this->returnMsg([],'删除成功',200);
    }

    public function getSystem($id)
    {
        $arr = Db::table('system')->where('id',$id)->field('id,sign_exp,sign_dist,task_exp')->select();
         return $this->returnMsg($arr[0],'获取参数成功',200);
    }

    public function changeSystemInfo($id)
    {
        $sign_dist=input('sign_dist');
        $task_exp=input('task_exp');
        if(Db::table('system')->where('id', $id)->update(['task_exp' => $task_exp,'sign_dist'=>$sign_dist])==0)
        {
            return $this->returnMsg([],'修改参数失败',400);
        }
        return $this->returnMsg([],'修改参数成功',200);
    }
}