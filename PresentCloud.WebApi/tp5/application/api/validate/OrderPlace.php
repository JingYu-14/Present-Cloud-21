<?php
namespace app\api\validate;
use app\lib\exception\ParameterException;

class OrderPlace extends BaseValidate
{
	// products是类似这样的二维数组
	// $products=[
	// 	[
	// 		'product_id'=>1,
	// 		'count'=>3
	// 	],
	// 	[
	// 		'product_id'=>2,
	// 		'count'=>4
	// 	]
	// ];
    protected $rule = [
        // 如果写'products' => 'checkProducts'，然后不传products参数是不会提示错误的。因为如果没有传products参数就不会去检验对应的checkproducts规则，但是加了require就不一样了
        'products' => 'require|checkProducts',
    ];
    protected $singleRule=[
    	'product_id' => 'require|isPositiveInteger',
    	'count' => 'require|isPositiveInteger'
    ];
    protected $message=[
        'products'=>'商品列表不能为空'
    ];

    protected function checkProducts($value){
        if(!is_array($value)){
            throw new ParameterException([
                'msg'=>'商品列表参数必须是数组'
            ]);
        }
    	foreach($value as $data){
    		$this->checkProduct($data);
    	}
    	return true;
    }

    private function checkProduct($value){
    	$validate=new BaseValidate($this->singleRule);
    	$result=$validate->batch()->check($value);
    	if(!$result){
    		throw new ParameterException([
    			'msg'=>'商品列表参数错误'
    		]);
    	}
    }
}
