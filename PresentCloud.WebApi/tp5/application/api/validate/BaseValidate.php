<?php
namespace app\api\validate;
use think\Validate;
use think\Exception;
use app\lib\exception\ParameterException;

class BaseValidate extends Validate
{
    public function goCheck(){
    	$data=input('param.');
    	$result=$this->batch()->check($data);
    	if($result){
    		return true;
    	}else{
    		throw new ParameterException([
                'msg'=>$this->error,
            ]);
    	}
    }

    protected function isPositiveInteger($value,$rule='',$data='',$field=''){
        // 传过来的$value是字符串，所以加0变数字
        if(is_numeric($value) && is_int($value+0) && ($value+0)>0){
            return true;
        }else{
            // return $field.'必须是正整数';
            return false;
        }
    }

    protected function isNotEmpty($value,$rule='',$data='',$field=''){
        if(empty($value)){
            return false;
        }else{
            return true;
        }
    }

     protected function isMobile($value){
        $rule='^1(3|4|5|7|8)[0-9]\d{8}$^';
        $result=preg_match($rule,$value);
        if($result)
          return true;
        else
          return false;
    }

    public function getDatasByRule($array){
      if(array_key_exists('user_id',$array)|array_key_exists('uid',$array))
      {
        // 错把数组用{}包了起来
        // throw new ParameterException({
        //     'msg'=>'参数中包含有非法的参数名user_id或uid'
        //  });
         throw new ParameterException([
            'msg'=>'参数中包含有非法的参数名user_id或uid'
         ]);
      }

      // 客户端传过来的数据可能不仅有验证器包含的数据，但是我们只要验证器里面有验证的数据
      foreach($this->rule as $key=>$value){
         $newArray[$key]=$array[$key];
      }

      return $newArray;
   }
}
