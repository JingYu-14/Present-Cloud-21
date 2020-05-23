<?php

/**
 * @Author: wujinhan
 * @Date:   2020-04-10 17:42:58
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-05-23 08:07:17
 */ 
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;

class Teacher extends Base
{
    // protected $beforeActionList = [
    //     'checkToken'
    // ];
    
    public function getMenus()
    {
    	$data = [];
        $arr = Db::table('permission')->where('level',0)->where('type',2)->select();
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

    public function addClass()
    {
        $code=input('code');
        $name=input('name');
        $uid=input('uid');
        $data = ['invitation_code' => $code, 'class_name' => $name,'tno'=>$uid];
        if(Db::table('class')->insert($data)==1)
        {
            return $this->returnMsg([],'添加班级成功',201);
        }
        return $this->returnMsg([],'添加班级失败',404);
    }

    public function getClasses()
    {
        $uid=input('uid');
        $id=input('id');
        if(!$uid)
        {
            $arr=Db::table('class')->where('id',$id)->select();
        }else{
            $arr=Db::table('class')->where('tno',$uid)->select();
        }
        
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
        $query=input('query');
        $temp=[];
        $arr=Db::table('course_select')->where('class_id',$id)->column('sno');
        for($i=0;$i<count($arr);$i++)
        {
            $info=Db::table('user')->where('id',$arr[$i])->where('name|account','like','%'.$query.'%')->field('account,name,exp,id,state')->find();
            if(!$info)break;
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

    public function addStudent()
    {
        $account=input('account');
        $id=input('id');
        if(!Db::table('user')->where('account',$account)->find())
        {
            return $this->returnMsg([],'该账号不存在',404);
        }
        $sno=Db::table('user')->where('account',$account)->value('id');
        $data = ['cno'=>1,'sno'=>$sno,'class_id'=>$id];
        if(Db::table('course_select')->insert($data)==1)
        {
            return $this->returnMsg([],'添加学生成功',201);
        }
        
    }

    public function deleteStudent()
    {
        $id=input('id');
        $uid=input('uid');
        if(Db::table('course_select')->where('sno',$uid)->where('class_id',$id)->delete()!=0)
        {
            return $this->returnMsg([],'删除学生成功',200);
        }else
        {
            return $this->returnMsg([],'删除学生失败',404);
        }
    }

    public function addSign()
    {
        $signtime=strtotime(input('signtime'));
        $code=input('code');
        $id=input('id');
        $sno=Db::table('course_select')->where('class_id',$id)->column('sno');
        for($i=0;$i<count($sno);$i++)
        {
            $sname=Db::table('user')->where('id',$sno[$i])->value('name');
            $data = ['code'=>$code,'sname'=>$sname,'class_id'=>$id,'time'=>$signtime,'sno'=>$sno[$i]];
            Db::table('sign')->insert($data);
        }
        return $this->returnMsg([],'发起签到成功',200);
    }

    public function getSign()
    {
        $searchtime=strtotime(input('searchtime'));
        $id=input('id');

        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $arr=Db::table('sign')->where('class_id',$id)->where('time',$searchtime)->select();
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

    public function addTask()
    {
        $deadline=strtotime(input('deadline'));
        $name=input('name');
        $detail=input('detail');
        $id=input('id');
        $data=['exp'=>10,'start_time'=>time(),'end_time'=>$deadline,'name'=>$name,'detail'=>$detail,'state'=>1];
        $taskId=Db::table('task')->insertGetId($data);

        $sno=Db::table('course_select')->where('cno',$id)->column('sno');
        for($i=0;$i<count($sno);$i++)
        {
            Db::table('task_stu')->insert(['task_id'=>$taskId,'sno'=>$sno[$i]]);
        }

        $taskId=Db::table('class')->where('id',$id)->value('task_id').$taskId.',';
        Db::table('class')->where('id',$id)->update(['task_id'=>$taskId]);
        return $this->returnMsg([],'发布任务成功',201);
        
    }
}