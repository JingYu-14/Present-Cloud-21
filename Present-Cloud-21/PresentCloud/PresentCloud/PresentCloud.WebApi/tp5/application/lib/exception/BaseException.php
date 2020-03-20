<?php
namespace app\lib\exception;
use think\Exception;

class BaseException extends Exception
{
	// HTTP状态码，202等
   public $code=400;

   // 错误具体信息
   public $msg='参数错误';

   // 自定义错误码
   public $errorCode=10000;

   // 传一个数组来设置值
   public function __construct($params=[]){
   		//利用array_key_exists可以实现参数默认值，不必每个都传值过来
   		if(array_key_exists('code',$params))
   			$this->code=$params['code'];
   		if(array_key_exists('msg',$params))
   			$this->msg=$params['msg'];
   		if(array_key_exists('errorCode',$params))
   			$this->errorCode=$params['errorCode'];
   }
}
