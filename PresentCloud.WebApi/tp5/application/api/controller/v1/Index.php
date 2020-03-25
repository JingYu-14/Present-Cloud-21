<?php

/**
 * @Author: Administrator
 * @Date:   2019-04-26 08:55:06
 * @Last Modified by:   Administrator
 * @Last Modified time: 2019-04-26 09:17:55
 */
namespace app\api\controller\v1;
use think\Controller;

class Index extends Controller{
	public function index(){
		$token=request()->header('token');
		$vars=cache($token);
		dump($vars);
	}
}