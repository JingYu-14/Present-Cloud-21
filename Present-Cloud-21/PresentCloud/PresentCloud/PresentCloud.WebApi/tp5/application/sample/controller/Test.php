<?php
namespace app\sample\controller;
use think\Controller;

// 要extends controller必须加上use think\Controller;，不然会提示Class 'app\sample\controller\Controller' not found
class Test extends Controller
{
    public function hello($id,$name)
    {
    	return 'hello</br>'.$id.$name;
    }
}