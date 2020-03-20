<?php
namespace app\api\validate;
use think\Validate;

class TestValidate extends BaseValidate
{
    protected $rule=[
        'name'=>'require|max:3'
    ];
}
