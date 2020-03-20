<?php
namespace app\api\controller\v1;
use think\Controller;
use app\api\model\Product as ProductModel;
use app\lib\exception\ProductException;

class Product extends Controller{
	/**
	 * @url /product/recent/:count
	 * @return 最近商品
	 */
	public function getRecent($count = 15){
		$validate=validate('Count');
        $validate->goCheck();

        $products = ProductModel::getMostRecent($count);
        // 因为设置了'resultset_type'  => 'collection'，集合是个对象不能再用！判空
        // if(!$products){
        // 	throw new ProductException();
        // }
        if($products->isEmpty()){
        	throw new ProductException();
        }
        $products=$products->hidden(['summary']);
        return json($products);
    }

    public function getByCategory($id){
        $validate=validate('IdValidate');
        $validate->goCheck();

        $products=ProductModel::getProductsByCategory($id);
        if($products->isEmpty()){
            throw new ProductException();
        }
        $products=$products->hidden(['summary']);
        return json($products);
    }

    /**
     * @url /product/:id
     * @return 某个商品的详情信息
     */
    public function getOne($id){
        $validate=validate('IdValidate');
        $validate->goCheck();

        $products=ProductModel::getProductDetail($id);
        if(!$products){
            throw new ProductException();
        }
        return json($products);
    }

    public function deleteOne($id){
        
    }
}