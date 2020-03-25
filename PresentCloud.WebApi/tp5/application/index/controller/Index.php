<?php
namespace app\index\controller;
use think\Controller;
use think\View;
use think\Db;
use think\Request;
use think\Validate;

class Index extends Controller
{
    public function index()
    {
        $url='http://gameid.5173.com/detail/DB041-20190614-64591529.shtml';
        $this->assign('url',$url);
        return $this->fetch();
    	/*$view=new View();
    	$view->name='wjh';
        return $view->fetch();*/
        // $name='wjh';
        // $this->assign('name',$name);
        // 这种方法必须extends controller
        // return $this->fetch();
        // return url('index');
        
        // 调用request对象有三种方法
        // $request=new Request();这样是错的，为什么？
        // $request=Request::instance();
        // return $request->url();
        // 这种方法必须extends controller
        // return $this->request->url();
        // 助手函数request()
        // return request()->url();
        
        /*response*/
        // $data=array('name'=>'tp5','status'=>'1');
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
        $arr=[];
        $result=Db::table('lol')->where('price','>=',90)->where('price','<=',100)->order('price')->select();
        $i=1;
        foreach($result as $key=>$value){
            $arr[$key]['index']=$value['id'];
            $arr[$key]['id']=$i++;
            if(!strstr($value['title'],'|')){
                $arr[$key]['title']=explode(" ",$value['title'])[0];
                $arr[$key]['title']=
                substr($arr[$key]['title'],strpos($arr[$key]['title'],'】')+3);
            }else{
                if(array_key_exists("2",explode("|",$value['title']))){
                    $arr[$key]['title']=explode("|",$value['title'])[2];
                }else{
                    $arr[$key]['title']=explode("|",$value['title'])[1];
                }
            }
            // echo $arr['id'].'、';
            // echo $arr['title'].'<br>';
            $arr[$key]['href']=$value['href'];
            $arr[$key]['price']=$value['price'];
        }
        $this->assign('arr',$arr);
        // dump($arr);
        return $this->fetch();
    }
}
