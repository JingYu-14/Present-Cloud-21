<?php
namespace app\lib\exception;
use think\Exception;
use think\exception\Handle;
use think\Log;

class ExceptionHandler extends Handle
{
	private $code;
	private $msg;
	private $errorCode;
	private $requestUrl;

   // Exception代表think目录下的Exception，\Exception代表根目录下的Exception是所有异常类的基类。有的时候会抛出HTTPException，但是它和think\Exception没有继承关系所以会出错，但他们有共同的基类\Exception
   public function render(\Exception $ex){
   		// return json('dsad');
   		// 下面返回字符串会出错
   		// return 'dsad';
   		
   		if($ex instanceof BaseException){
   			// 如果异常属于用户级别
   			$this->code=$ex->code;
   			$this->msg=$ex->msg;
   			$this->errorCode=$ex->errorCode;
   		}else{
   			// 如果异常属于服务器级别
            if(config('app_debug')){
               // 如果是调试模式返回原来的异常页面给服务端开发人员，就是调用Handle类中原来的render方法
               // 必须加上return不然会继续执行下面的代码
               return parent::render($ex);
            }else{
               // 如果是生产模式返回json，方便客户端查看
               $this->code=500;
               $this->msg='服务器内部错误，不想告诉你';
               $this->errorCode=999;
               $this->recordErrorLog($ex);
            } 
   			
   		}

   		$request=request();
		   $this->requestUrl=$request->url();

   		$result=[
   			'msg'=>$this->msg,
   			'error_code'=>$this->errorCode,
   			'request_url'=>$this->requestUrl
   		];

   		return json($result,$this->code);
   }

   private function recordErrorLog(\Exception $ex){
      // 由于在config.php中关闭了系统自带的日志记录，所以要用到日志的时候得手动初始化
      Log::init([
         'type'=>'File',
         'path'=>LOG_PATH,
         // 只记录error和以上的级别
         'level'=>['error']
      ]);
      Log::record($ex->getMessage(),'error');
   }
}
