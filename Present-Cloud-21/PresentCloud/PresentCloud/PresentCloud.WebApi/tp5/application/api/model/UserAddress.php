<?php
namespace app\api\model;
use app\api\model\BaseModel;

class UserAddress extends BaseModel 
{
	protected $hidden=['id','user_id','delete_time'];
	
}