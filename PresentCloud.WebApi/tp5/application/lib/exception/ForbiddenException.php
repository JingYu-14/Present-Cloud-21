<?php
namespace app\lib\exception;

class ForbiddenException extends BaseException
{
	// HTTP状态码返回404时用浏览器打开只会提示该网页无法访问，用postman打开才会返回预定的json数据
	public $code=403;
	public $msg='权限不够';
	public $errorCode=10001;
}
