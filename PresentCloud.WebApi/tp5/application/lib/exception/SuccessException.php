<?php
namespace app\lib\exception;

class SuccessException extends BaseException
{
	// 201一般表示资源状态成功发生变化
	public $code=201;
	public $msg='ok';
	public $errorCode=0;
}
