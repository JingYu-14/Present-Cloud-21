<?php
namespace app\index\controller;
use think\Controller;
use think\Db;
use app\api\controller\Base;
use think\Loader;

use PHPExcel;
use PHPExcel_IOFactory;
use PHPExcel_Cell;

class Index extends Base
{
    public function index()
    {
        echo time()+3600;
    }

    public function upload(){
        // 获取表单上传文件 例如上传了001.jpg
        $file = request()->file('xlsx');
        
        // 移动到框架应用根目录/public/uploads/ 目录下
        if($file){
            $info = $file->move(ROOT_PATH . 'public' . DS . 'uploads');
            if($info){
                echo $info->getExtension();
                // 输出 20160820/42a79759f284b767dfcb2a0197904287.jpg
                echo $info->getSaveName();
                // 输出 42a79759f284b767dfcb2a0197904287.jpg
                echo $info->getFilename(); 
            }else{
                // 上传失败获取错误信息
                echo $file->getError();
            }
        }
    }

    public function importexcel(){
        Loader::import('PHPExcel.PHPExcel');
        Loader::import('PHPExcel.PHPExcel.PHPExcel_IOFactory');
        Loader::import('PHPExcel.PHPExcel.PHPExcel_Cell');
        // 判断文件是什么格式
        $path="C:/Users/wujinhan/Documents/Tencent Files/572415551/FileRecv/MobileFile/1.xlsx";
        $type = pathinfo($path);
        $type = strtolower($type["extension"]);

        if ($type == 'xlsx') {
            $type = 'Excel2007';
        } elseif ($type == 'xls') {
            $type = 'Excel5';
        }
        //最长执行时间,php默认为30秒,这里设置为0秒的意思是保持等待直到程序执行完成
        ini_set('max_execution_time', '0');
        // 判断使用哪种格式
        $objReader = PHPExcel_IOFactory::createReader($type);
        $objPHPExcel = $objReader->load($path);

        $sheet = $objPHPExcel->getSheet(2);//获取工作薄
        // 取得总行数
        $highestRow = $sheet->getHighestRow();
        // 取得总列数
        $highestColumn = $sheet->getHighestColumn();
        //循环读取excel文件,读取一条,插入一条
        $data=array(['name'=>'wjh','time'=>'1']);
        //从第一行开始读取数据  这里类似冒泡算法
        for($j=4;$j<=$highestRow;$j++){
            /*//从A列读取数据
            for($k='A';$k<=$highestColumn;$k++){
                // 读取单元格
                $data[$j][]=$objPHPExcel->getActiveSheet()->getCell("$k$j")->getValue();
            }*/
            // $id=$j-3;
            $name=$objPHPExcel->getActiveSheet()->getCell("A".$j)->getValue();
            $time=$objPHPExcel->getActiveSheet()->getCell("I".$j)->getValue();
            // dump($id);dump($name);dump($time);
            // array_push($data,$name,$time);
            array_push($data,['name'=>$name,'time'=>$time]);
        }
        array_shift($data);
        return $data;
    }

    public function write(){
        $data=$this->importexcel();
        // echo "<pre>";print_r($data);echo "<pre>";return;
        $name=array();
        foreach($data as $value)
        {
            array_push($name,$value['name']);
        }
        $result=array_count_values($name);
        foreach($result as $key=>$value)
        {
            if($value>1)
            {
                dump($key.'在第一张表有'.$value.'次打卡记录');
            }
        }

        Loader::import('PHPExcel.PHPExcel');
        Loader::import('PHPExcel.PHPExcel.PHPExcel_IOFactory');
        Loader::import('PHPExcel.PHPExcel.PHPExcel_Cell');
        // 判断文件是什么格式
        $path="C:/Users/wujinhan/Documents/Tencent Files/572415551/FileRecv/MobileFile/2-2.xlsx";
        $type = pathinfo($path);
        $type = strtolower($type["extension"]);

        if ($type == 'xlsx') {
            $type = 'Excel2007';
        } elseif ($type == 'xls') {
            $type = 'Excel5';
        }
        //最长执行时间,php默认为30秒,这里设置为0秒的意思是保持等待直到程序执行完成
        ini_set('max_execution_time', '0');
        // 判断使用哪种格式
        $objReader = PHPExcel_IOFactory::createReader($type);
        $objPHPExcel = $objReader->load($path);

        $sheet = $objPHPExcel->getSheet(0);//获取工作薄
        // 取得总行数
        $highestRow = $sheet->getHighestRow();
        // 取得总列数
        $highestColumn = $sheet->getHighestColumn();
        //循环读取excel文件,读取一条,插入一条
        $flag=false;
        $count=0;
        $realcount=0;
        $week='';
        $arr=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
        for($k='C';$k<=$highestColumn;$k++){
            $value=$objPHPExcel->getActiveSheet()->getCell($k."2")->getValue();
            if($value)
            {
                if(strpos($value,$arr[date("w",strtotime($data[0]['time']))]))
                {
                    $week=$k;break;
                }
            }
        }
        foreach($data as $value)
        {
            for($j=3;$j<=$highestRow;$j++){
                $name=$objPHPExcel->getActiveSheet()->getCell("B".$j)->getValue();
                if($name==$value['name']){
                    $flag=true;
                    $count++;
                    if(!$objPHPExcel->getActiveSheet()->getCell($week.$j)->getValue())
                    {
                        $realcount++;
                        $objPHPExcel->setActiveSheetIndex(0)->setCellValue($week.$j,"打卡");
                    }
                }
            }
            if(!$flag){
                dump($value['name'].'不在第二张表里');
            }
            $flag=false;
        }
        dump('第一张表'.count($data).'条打卡记录');
        dump('第二张表打卡'.$count.'次，实际打卡'.$realcount.'次');
        $objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel2007');
        $file=$objWriter->save(str_replace('.php', '.xlsx', __FILE__));
        unset($file);
    }
}
