<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-29 19:53:56
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-03-30 16:44:12
 */
namespace app\api\controller;

use think\Controller;

class Base extends Controller
{
    protected function returnMsg($data, $msg, $status)
    {
    	$meta = ["msg" => $msg,"status" => $status];
    	return json(["data" => $data, "meta" => $meta]);
    }
}