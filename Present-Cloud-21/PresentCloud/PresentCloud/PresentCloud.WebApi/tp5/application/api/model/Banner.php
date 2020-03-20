<?php
namespace app\api\model;
use think\Exception;
use think\Db;
use think\Model;
use app\api\model\BaseModel;

class Banner extends BaseModel 
{
	//数据库表名默认是跟类名一样，如果要设置不一样的话就添加一个$table
	// protected $table='banner_item'; 
   
   protected $hidden=['update_time','delete_time'];
	
   public static function getBannerById($id){
   		// try{
   		// 	1/0;
   		// }catch(Exception $ex){
   		// 	throw $ex;
   		// }
   		// 如果没有写use think\Exception;的话则应该写成catch(\Exception $ex)
   		
   		// 数据库操作有三种方法，原生sql语句，Query查询构造器(可读也可写)，ORM模型
   		// $result=Db::query(
   		// 	'select * from banner_item where banner_id=?',[$id]
   		// );
   		// 查询构造器，find,select,update(不要与ORM模型的save弄乱),insert,delete是主方法，剩下的都是辅助方法可以拿来当链式操作
   		// $result=Db::table('banner_item')
   		// 		->where('banner_id','=',$id)
   				// ->fetchSql()
   				// ->find();
         //调用类自身的静态方法不能用$this      
         $banner=self::with(['items','items.img'])->find($id);
   		return $banner;
   }

   public function items(){
      return $this->hasMany('BannerItem','banner_id','id');
   }
}
