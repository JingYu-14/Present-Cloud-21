<?php
namespace app\api\model;
use app\api\model\BaseModel;

class Order extends BaseModel 
{
	protected $hidden=['update_time','delete_time','user_id'];
	protected $autoWriteTimeStamp=true;
}