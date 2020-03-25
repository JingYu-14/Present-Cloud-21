<?php
namespace app\lib\exception;

class ThemeException extends BaseException
{
	// HTTP状态码返回404时用浏览器打开只会提示该网页无法访问，用postman打开才会返回预定的json数据
	public $code=404;
	public $msg='指定主题不存在，请检查主题ID';
	public $errorCode=30000;
}
