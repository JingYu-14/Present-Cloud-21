<?php
namespace app\api\model;
use think\Model;

class Theme extends BaseModel 
{
	protected $hidden=['delete_time','update_time','topic_img_id','head_img_id'];
	
	public function topicImg(){
		// 在模型里定义关联关系函数的时候忘记写return会提示Call to a member function eagerlyResultSet() on null
		return $this->hasOne('Image','id','topic_img_id');
	} 

	public function headImg(){
		return $this->hasOne('Image','id','head_img_id');
	} 

	// theme与product表是多对多的关系
	public function products(){
		return $this->belongsToMany('Product','theme_product','product_id','','theme_id');
	}

	public static function getThemeWithProducts($id)
    {
        $theme=self::with('products,topicImg,headImg')->find($id);
        return $theme;
    }
}
