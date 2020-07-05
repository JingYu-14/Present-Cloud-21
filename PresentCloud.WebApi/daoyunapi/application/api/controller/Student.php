<?php

/**
 * @Author: wujinhan
 * @Date:   2020-04-12 17:08:23
 * @Last Modified by:   Administrator
 * @Last Modified time: 2020-07-05 13:05:23
 */
namespace app\api\controller;
use app\api\controller\Base;
use think\Db;

class Student extends Base
{
    protected $beforeActionList = [
        'checkToken'
    ];
    
    public function getMenus()
    {
    	$data = [];
        $arr = Db::table('permission')->where('level',0)->where('type',1)->select();
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

    public function getClasses()
    {
        $uid=input('uid');
        $id=input('id');
        if(!$uid)
        {
            $arr=Db::table('class')->where('id',$id)->select();
        }else{
        	$cno=Db::table('course_select')->where('sno',$uid)->column('class_id');
            $arr=Db::table('class')->where('id','in',$cno)->select();
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

    public function addClass()
    {
        $code=input('code');
        $uid=input('uid');
        $classId=Db::table('class')->where('invitation_code',$code)->value('id');
        $data = ['cno' => 1, 'sno' => $uid,'class_id'=>$classId];
        if(Db::table('course_select')->insert($data)==1)
        {
            return $this->returnMsg([],'加入班级成功',201);
        }
        return $this->returnMsg([],'加入班级失败',404);
    }

    public function getSign()
    {
        $searchtime=strtotime(input('searchtime'));
        $id=input('id');
        $uid=input('uid');
        $pagesize=input('pagesize');
        $pagenum=input('pagenum');
        $arr=Db::table('sign')->where('class_id',$id)->where('sno',$uid)->where('time',$searchtime)->select();
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

    public function upload(){
        // 获取表单上传文件 例如上传了001.jpg
        $file = request()->file('file');
        $taskId=input('taskId');
        $uid=input('uid');
        // 移动到框架应用根目录/public/uploads/ 目录下
        if($file){
            $info = $file->move(ROOT_PATH . 'public' . DS . 'uploads');
            if(Db::table('task_stu')->where('sno',$uid)->where('task_id',$taskId)->update(['presentation'=>str_replace("\\",'/',$info->getSaveName()),'state'=>1])!=0)
            {
                 $exp=Db::table('user')->where('id',$uid)->value('exp')+Db::table('system')->where('state',1)->value('task_exp');
                Db::table('user')->where('id',$uid)->update(['exp'=>$exp]);
                return $this->returnMsg([],'上传成功',200);
            }else{
                // return Db::table('task_stu')->getLastSql();
                return $this->returnMsg([],'上传失败',404);
            }
        }else{
            return $this->returnMsg([],'上传失败',404);
        }
    }

    public function sign()
    {
        $uid=input('uid');
        $code=input('code');
        $res=Db::table('sign')->where('code',$code)->find();
        // 签到的经度
        $lng2=input('lng');
        $lat2=input('lat');
        if(!$res)
        {
            return $this->returnMsg([],'签到失败，签到码不存在',404);
        }else
        {
            $lng1=Db::table('sign')->where('sno',$uid)->where('code',$code)->value('lng1');
            $lat1=Db::table('sign')->where('sno',$uid)->where('code',$code)->value('lat1');
            $radLat1 = deg2rad($lat1); //deg2rad()函数将角度转换为弧度
            $radLat2 = deg2rad($lat2);
            $radLng1 = deg2rad($lng1);
            $radLng2 = deg2rad($lng2);
            $a = $radLat1 - $radLat2;
            $b = $radLng1 - $radLng2;
            $distance = 2 * asin(sqrt(pow(sin($a / 2), 2) + cos($radLat1) * cos($radLat2) * pow(sin($b / 2), 2))) * 6378.137 * 1000;

            $signDist=Db::table('system')->where('state',1)->value('sign_dist');
            exit;
            if($distance>$signDist)
            {
                return $this->returnMsg([],'不在签到范围',404);
            }

            $exp=Db::table('user')->where('id',$uid)->value('exp')+Db::table('system')->where('state',1)->value('sign_exp');
            Db::table('user')->where('id',$uid)->update(['exp'=>$exp]);
            Db::table('sign')->where('sno',$uid)->where('code',$code)->update(['state'=>1,'lng2'=>$lng2,'lat2'=>$lat2,'distance'=>$distance]);
            return $this->returnMsg([],'签到成功',200);
        }
    }
}