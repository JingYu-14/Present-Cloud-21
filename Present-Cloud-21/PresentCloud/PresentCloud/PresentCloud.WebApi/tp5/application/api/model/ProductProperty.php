<?php
namespace app\api\model;
use app\api\model\BaseModel;

class ProductProperty extends BaseModel 
{
	protected $hidden=['id','delete_time','product_id'];
	
}