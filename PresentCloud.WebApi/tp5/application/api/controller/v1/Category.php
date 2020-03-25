<?php
namespace app\api\controller\v1;
use think\Controller;
use app\api\model\Category as CategoryModel;
use app\lib\exception\CategoryException;

class Category extends Controller{
	public function getCategory(){
		$category=CategoryModel::all([],['img']);
		if($category->isEmpty()){
			throw new CategoryException();
		}
		return $category;
	}
}