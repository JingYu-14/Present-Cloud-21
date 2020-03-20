<?php
namespace app\test\controller;
use think\Controller;
use think\View;
use think\Db;
use think\Request;
use think\Validate;

class Index extends Controller
{
    public function index()
    {
    	/*$view=new View();
    	$view->name='wjh';
        return $view->fetch();*/
        $name='wjh';
        $this->assign('name',$name);
        // 这种方法必须extends controller
        // return $this->fetch();
        // return url('index');
        
        // 调用request对象有三种方法
        // $request=new Request();这样是错的，为什么？
        $request=Request::instance();
        // return $request->url();
        // 这种方法必须extends controller
        // return $this->request->url();
        // 助手函数request()
        // return request()->url();
        
        /*response*/
        $data=array('name'=>'tp5','status'=>'1');
        // return $data;会报错
        // return json($data);
        // return xml($data);
        
        // validate可以独立验证也可用做验证器
        // 独立验证
        /*$data=[
            'name'=>'',
            'email'=>'vendorqq.com'
        ];

        $validate=new Validate([
            'name'=>'require|max:10',
            'email'=>'email'
        ]);

        $result=$validate->batch()->check($data);
        dump($validate->getError());*/
    }

    public function test(){
        
    }
}
