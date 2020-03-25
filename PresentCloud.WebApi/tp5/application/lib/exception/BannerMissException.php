<?php
namespace app\lib\exception;

class BannerMissException extends BaseException
{
	// 当时该文件后缀名忘记写，一直提示could not found app\lib\exception\BannerMissException
	public $code=404;
	public $msg='请求的Banner不存在';
	public $errorCode=40000;
}
