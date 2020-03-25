<?php
namespace app\api\model;
use think\Model;
use app\api\model\BaseModel;

class BannerItem extends BaseModel 
{
	protected $hidden=['id','img_id','banner_id','update_time','delete_time'];

	public function img(){
		return $this->hasOne('Image','id','img_id');
	}
}
