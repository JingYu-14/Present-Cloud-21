<?php
namespace app\api\model;
use think\Model;
use app\api\model\BaseModel;

class Image extends BaseModel 
{
	protected $hidden=['id','from','update_time','delete_time'];

	// 读取器也是AOP的应用
	// // $value表示要获取的字段值，$data表示一整行的记录值是个数组
	// public function getUrlAttr($value,$data){
	// 	// from字段=1表示图片放在服务器，所有要用基地址加上相对地址。=2表示放在第三方，是绝对路径
	// 	if($data['from']==1){
	// 		return config('setting.img_prefix').$value;
	// 	}else{
	// 		return $value;	
	// 	}		
	// }
	// 可以把getUrlAttr要做的事情封装在BaseModel里面，但是有些url又不需要这么处理
	public function getUrlAttr($value,$data){
		// 获取器里忘记写return不会提示错误，但是该属性的读取结果会变空
		return $this->prefixImgUrl($value,$data);
	}
}
