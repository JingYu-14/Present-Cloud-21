<?php
namespace app\api\validate;
use think\Validate;

class IdValidate extends BaseValidate
{
    protected $rule=[
        'id'=>'require|isPositiveInteger',
    ];

    protected $message=[
        'id'=>'id必须是正整数'
    ];

    // protected function isPositiveInteger($value,$rule,$data,$field){
    // 	// 传过来的$value是字符串，所以加0变数字
    // 	if(is_numeric($value) && is_int($value+0) && ($value+0)>0){
    // 		return true;
    // 	}else{
    // 		return $field.'必须是正整数';
    // 	}
    // }
}
