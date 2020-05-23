<?php
namespace app\exception;

/**
 * token验证失败时抛出此异常 
 */
class TokenException extends BaseException
{
    public $data = [];
    public $msg = '无效Token';
    public $status = 401;
}