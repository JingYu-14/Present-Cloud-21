<?php
namespace app\api\service;
use app\api\model\Product;
use app\api\model\UserAddress;
use app\api\model\OrderProduct;
use app\lib\exception\UserException;
use app\api\model\Order as OrderModel;

class Order{
	// 订单的商品列表也就是客户端传过来的products参数
	public $oproducts;
	// 真实的商品信息，包括库存量
	public $products;
	public $uid;

	public function __construct($uid,$oproducts){
		$this->oproducts=$oproducts;
		$this->products=$this->getProductsByOrder($oproducts);
		$this->uid=$uid;
	}

	public function place(){
		// 将oproducts和products作对比
		$status=$this->getOrderStatus();

		if(!$status['pass']){
			$status['order_id']=-1;
			return $status;
		}
		// 库存量检测成功，开始创建订单
		$orderSnap=$this->getOrderSnap($status);
		$status=$this->createOrderBySnap($orderSnap);
		$status['pass']=true;
		return $status;
	}

	// 根据订单信息查询真实的商品信息
	private function getProductsByOrder(){
		// 要避免循环查询数据库，可以将所有id放在一个数组里面，然后统一查询一次
		$opids=[];
		foreach($this->oproducts as $oproduct){
			array_push($opids,$oproduct['product_id']);
		}
		$products=Product::all($opids)
				->visible('id','price','stock','name','main_img_url')
				->toArray();
		return $products;	
	}

	private function getOrderStatus(){
		$status=[
			'pass'=>true,
			'orderPrice'=>0,
			'totalCount'=>0,
			'pStatusArray'=>[]
		];

		foreach($this->oproducts as $oproduct){
			$pStatus=$this->getProductStatus(
				$oproduct['product_id'],$oproduct['count'],$this->products
			);
			if(!$pStatus['haveStock']){
				$status['pass']=false;
			}
			$status['orderPrice']+=$pStatus['totalPrice'];
			$status['totalCount']+=$pStatus['count'];
			array_push($status['pStatusArray'],$pStatus);
		}
		return $status;
	}

	private function getProductStatus($opid,$ocount,$products){
		// 保存订单里面某个商品的详细信息
		$pSatus=[
			'id'=>0,
			'haveStock'=>false,
			'count'=>0,
			'name'=>'',
			'totalPrice'=>0
		];

		for($i=0;$i<count($products);$i++){
			if($opid==$products[$i]['id']){
				$product=$products[$i];
			}
		}

		$pStatus['id']=$product['id'];
		$pStatus['name']=$product['name'];
		$pStatus['count']=$ocount;
		$pStatus['totalPrice']=$product['price']*$ocount;

		if($product['stock']>=$ocount){
			$pStatus['haveStock']=true;
		}

		return $pStatus;
	}

	private function getOrderSnap($status){
		$snap=[
			'orderPrice'=>0,
			'totalCount'=>0,
			'pStatusArray'=>[],
			'snapAddress'=>'',
			'snapName'=>'',
			'snapImg'=>''
		];

		$snap['orderPrice']=$status['orderPrice'];
		$snap['totalCount']=$status['totalCount'];
		$snap['pStatusArray']=$status['pStatusArray'];
		// 对象或数组要存进数据库之前必须序列化
		$snap['snapAddress']=json_encode($this->getUserAddress());
		$snap['snapName']=$this->products[0]['name'];
		$snap['snapImg']=$this->products[0]['main_img_url'];
		if(count($this->products)>1){
			$snap['snapName'].='等';
		}
		return $snap;
	}

	private function getUserAddress(){
		$userAddress=UserAddress::where('user_id','=',$this->uid)->find();
		if(!$userAddress){
			throw new UserException([
				'msg'=>'用户收货地址不存在，下单失败',
				'errorCode'=>'60001'
			]);
		}
		return $userAddress->toArray();
	}

	private function createOrderBySnap($snap){
		$orderNo=self::makeOrderNo();
		$order=new OrderModel();
		$order->user_id=$this->uid;
		$order->order_no=$orderNo;
		$order->total_price=$snap['orderPrice'];
		$order->total_count=$snap['totalCount'];
		$order->snap_img=$snap['snapImg'];
		$order->snap_name=$snap['snapName'];
		$order->snap_address=$snap['snapAddress'];
		$order->snap_items=json_encode($snap['pStatusArray']);
		$order->save();
		
		foreach($this->oproducts as &$oproduct){
			// 利用foreach修改数组时需要加引用符&
			$oproduct['order_id']=$order->id;
			$orderProduct=new OrderProduct();
			$orderProduct->saveAll($this->oproducts);

			return [
				'order_no'=>$ordrNo,
				'order_id'=>$order->id,
				'create_time'=>$order->create_time
			];
		}
	}

	public static function makeOrderNo(){
		$yCode=array('A','B','C','D','E','F','G','H','I','J');
        $orderSn =$yCode[intval(date('Y'))-2017].strtoupper(dechex(			date('m'))).date('d').substr(time(),-5).substr(			microtime(),2,5).sprintf('%02d', rand(0, 99));
        return $orderSn;
	}
}