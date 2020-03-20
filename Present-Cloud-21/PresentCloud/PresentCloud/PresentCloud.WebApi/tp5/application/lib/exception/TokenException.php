<?php
namespace app\lib\exception;

class TokenException extends BaseException
{
	// HTTP状态码返回404时用浏览器打开只会提示该网页无法访问，用postman打开才会返回预定的json数据
	public $code=401;
	public $msg='token已过期或无效token';
	public $errorCode=10001;
}
