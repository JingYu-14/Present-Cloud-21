<?php
namespace app\api\model;
use think\Model;

class Product extends BaseModel 
{
	protected $hidden = [
        'delete_time', 'main_img_id', 'pivot', 'from', 'category_id',
        'create_time', 'update_time'
    ];

    public function getMainImgUrlAttr($value, $data){
        return $this->prefixImgUrl($value, $data);
    }

    public static function getMostRecent($count){
        $products = self::limit($count)
            ->order('create_time desc')
            ->select();
        return $products;
    }

    public static function getProductsByCategory($id){
        $products=self::where('category_id','=',$id)->select();
        return $products;
    }

    public function imgs(){
        return $this->hasMany('ProductImage','product_id','id');
    }

    public function property(){
        return $this->hasMany('ProductProperty','product_id','id');
    }

    public static function getProductDetail($id){
        // 这里不是对product表排序，所以不能直接用链式方法order()
        $products=self::with([
            'imgs'=>function($query){
                $query->with(['imgUrl'])->order('order','asc');
        }])
            ->with(['property'])
            ->find($id);
        return $products;
    }
}
