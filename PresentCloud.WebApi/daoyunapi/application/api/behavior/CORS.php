<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-29 20:33:03
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-04-04 07:58:05
 */
namespace app\api\behavior;

use think\Response;

class CORS
{
    public function appInit(&$params)
    {
        header('Access-Control-Allow-Origin: *');
        header("Access-Control-Allow-Headers: token,Origin, X-Requested-With, Content-Type, Accept");
        header('Access-Control-Allow-Methods: POST,GET,PUT,DELETE');
        if(request()->isOptions()){
            exit();
        }
    }
}