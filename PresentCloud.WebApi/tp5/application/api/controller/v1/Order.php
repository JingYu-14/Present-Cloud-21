<?php
namespace app\api\controller\v1;
use app\api\controller\BaseController;
use think\Validate\OrderPlace;
use app\api\model\Product;
use app\api\service\Token as TokenService;
use app\api\service\Order as OrderService;

class Order extends BaseController{
	// 用户在选择商品后向api提供商品的相关信息
	// api接收到信息之后进行库存量检测
	// 有库存则把订单数据存入数据库=下单成功，并返回客户端告知可以支付
	// 调用支付api
	// 库存量检测
	// api调用微信服务器的支付api
	// 微信服务器会返回给我们一个支付的结果（异步）
	// 成功则进行库存量的检查，并进行库存量的扣除
	protected $beforeActionList=[
        'checkExclusiveScope'=>['only'=>'placeorder']
    ];

    // 传给placeOrder接口的参数是一个二维数组，每个数组元素包括某个商品的id和数量
	public function placeOrder(){
		// 该接口只允许用户访问，不允许管理员访问
		// $validate=validate('OrderPlace');
		// $validate->goCheck();

		// products是数组，所以获取的时候要加/a
		$products=input('post.products/a');
		$uid=TokenService::getCurrentUid();
		// 接下来的业务逻辑比较复杂，所以写在service层里面
		$order=new OrderService($uid,$products);
		
		$status=$order->place();
		return $status;
		// [{"product":1,"count":2},{"product":1,"count":2}]
	}
}