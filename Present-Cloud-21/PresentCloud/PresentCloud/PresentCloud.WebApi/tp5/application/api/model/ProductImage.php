<?php
namespace app\api\model;
use app\api\model\BaseModel;

class ProductImage extends BaseModel 
{
	protected $hidden=['img_id','delete_time','product_id'];
	
	public function imgUrl(){
		return $this->hasOne('Image','id','img_id');
	}
}