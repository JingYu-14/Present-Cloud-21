<?php
namespace app\index\controller;
use think\Controller;
use think\Db;
use app\api\controller\Base;

class Index extends Base
{
    public function index()
    {
       $d=strtotime("last Saturday");
        var_dump($d);
    }
    public function make()
    {
    	$chars = array('A', 'B', 'C', 'D', 
    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L','M', 'N', 'O', 
    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y','Z', 
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    // 在 $chars 中随机取 $length 个数组元素键名
    	$length=6;
    $keys = array_rand($chars, $length); 
    $password = '';
    for($i = 0; $i < $length; $i++)
    {
        // 将 $length 个数组元素连接成字符串
        $password .= $chars[$keys[$i]];
    }
    var_dump(time());return;
    // var_dump($password);return;
	}
}
