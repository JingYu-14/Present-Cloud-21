<?php
namespace app\api\model;
use app\api\model\BaseModel;

class Category extends BaseModel 
{
	protected $hidden=['create_time','update_time','delete_time'];
	
	public function img(){
		return $this->hasOne('Image','id','topic_img_id');
	}
}