<?php
/**
 * Created by PhpStorm.
 * User: Kevin
 * Date: 2018/8/2
 * Time: 21:54
 */
namespace app\admin\controller;
use think\Controller;
use think\Db;
class Index extends  Base
{
    /**
     * 框架页面
     * @return mixed
     */
    public function index()
    {
        return $this->fetch('/index');
    }

    /**
     * 首页
     * @return mixed|\think\response\Json
     */
    public function indexPage()
    {
        return $this->fetch('index/index');
    }

    /**
     * 清除缓存
     */
    public function clear() {
        if (delete_dir_file(CACHE_PATH) && delete_dir_file(TEMP_PATH)) {
            writelog('清除缓存成功',200);
            return json(['code' => 200, 'msg' => '清除缓存成功']);
        } else {
            writelog('清除缓存失败',100);
            return json(['code' => 100, 'msg' => '清除缓存失败']);
        }
    }

    public function webuploader(){
        return $this->fetch('/webuploader');
    }
}