<?php
namespace app\exception;

use think\exception\Handle;
use Exception;

/*
 * 重写Handle的render方法，实现自定义异常消息
 */
class ExceptionHandler extends Handle
{
    private $data;
    private $msg;
    private $status;

    public function render(Exception $e)
     {
        $this->data = $e->data;
        $this->msg = $e->msg;
        $this->status = $e->status;
        $meta = ["msg" => $this->msg,"status" => $this->status];
        return json(["data" => $this->data, "meta" => $meta]);
    }

}