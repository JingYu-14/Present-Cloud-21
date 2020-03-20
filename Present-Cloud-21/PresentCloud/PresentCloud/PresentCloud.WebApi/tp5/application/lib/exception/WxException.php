<?php
namespace app\lib\exception;

class WxException extends BaseException
{
	// HTTP状态码返回404时用浏览器打开只会提示该网页无法访问，用postman打开才会返回预定的json数据
	public $code=400;
	public $msg='微信服务器接口调用失败';
	public $errorCode=999;
}
