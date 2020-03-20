<?php

namespace app\admin\controller;
use app\admin\model\ArticleModel;
use app\admin\model\ArticleCateModel;
use think\Db;
use org\Qiniu;

class Article extends Base
{
    /**
     * [index 文章列表]
     * @author
     */
    public function index(){
        if(request()->isAjax ()){
            extract(input());
            $map = [];
            if(isset($key)&&$key!=""){
                $map['r.title'] = ['like',"%" . $key . "%"];
            }
            if(isset($start)&&$start!=""&&isset($end)&&$end=="")
            {
                $map['r.create_time'] = ['>= time',$start];
            }
            if(isset($end)&&$end!=""&&isset($start)&&$start=="")
            {
                $map['r.create_time'] = ['<= time',$end];
            }
            if(isset($start)&&$start!=""&&isset($end)&&$end!="")
            {
                $map['r.create_time'] = ['between time',[$start,$end]];
            }
            $field=input('field');//字段
            $order=input('order');//排序方式
            if($field && $order){
                $od="r.".$field." ".$order;
            }else{
                $od="r.create_time desc";
            }
            $Nowpage = input('get.page') ? input('get.page'):1;
            $limits = input("limit")?input("limit"):10;
            $count = Db::name('article')->alias('r')->where($map)->count();//计算总页面
            $article = new ArticleModel();
            $lists = $article->getArticleByWhere($map, $Nowpage, $limits,$od);
            for($i=0;$i<count($lists);$i++){
                $photo = explode(',',$lists[$i]['photo']);
                $lists[$i]['photo'] = array_values($photo);
            }
            return json(['code'=>220,'msg'=>'','count'=>$count,'data'=>$lists]);
        }
        return $this->fetch("article/index");
    }

    //tableSelect测试数据
    public function getUserData(){
        if(request()->isGet ()){
            extract(input());
            $map = [];
            if(isset($keyword)&&$keyword!=""){
                $map['name'] = ['like',"%" . $keyword . "%"];
            }
            $Nowpage = input('get.page') ? input('get.page'):1;
            $limits = input("limit")?input("limit"):10;
            $count = Db::name('test')->where($map)->count();//计算总页面
            $lists = Db::name('test')
                ->where($map)
                ->page($Nowpage,$limits)
                ->select();
            return json(['code'=>220,'msg'=>'','count'=>$count,'data'=>$lists]);
        }
        if(request()->isPost ()){
            $data = Db::name('test')
                ->where('id','in',input('id'))
                ->select();
            return json(['code'=>200,'data'=>$data]);
        }
    }

//    public function insertData(){
//        set_time_limit (0);
//        for($i=0;$i<100000;$i++){
//            $param = ['name'=>'kevin'.($i+1)];
//            Db::name('test')->insert($param);
//        }
//    }


    /**
     * [add_article 添加文章]
     * @return [type] [description]
     * @author
     */
    public function add_article()
    {
        if(request()->isPost()){
            extract(input());
            $param = input('post.');
            if(!isset($status)){
                $param['status'] = 2;
            }
            $param['photo'] = trim($param['photo'],',');
            $article = new ArticleModel();
            $flag = $article->insertArticle($param);
            return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
        }
        $cate = new ArticleCateModel();
        $area = new \app\common\place\Area;
        return $this->fetch('article/add_article',['province'=>$area->province(),'cate'=>$cate->getCate()]);
    }


    /**
     * [edit_article 编辑文章]
     * @return [type] [description]
     * @author
     */
    public function edit_article()
    {
        $article = new ArticleModel();
        if(request()->isPost()){
            $param = input('post.');
            $imgs = explode(',',$param['del']);
            foreach($imgs as $vo){
                $add = str_replace ('http://p73q8jzf0.bkt.clouddn.com/','',$vo);
                $up = new Qiniu();
                $up->delFile($add,'kevin');
            }
            $param['photo'] = trim($param['photo'],',');
            $flag = $article->updateArticle($param);
            return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
        }

        $id = input('param.id');
        $cate = new ArticleCateModel();
        $data = $article->getOneArticle($id);
        if(!empty($data['photo'])){
            $data['photo'] = trim($data['photo'],',');
//            $img = explode(',',$data['photo']);
//            foreach($img as $vo){
//                $photo[] = '/uploads/images/'.$vo;
//            }
            $data['imges'] = $data['photo'];
        }else{
            $data['photo'] = '';
            $data['imges'] = '';
        }
        $this->assign('cate',$cate->getCate());
        $this->assign('article',$data);
        return $this->fetch();
    }

//    /**
//     * imgDelete 删除图片
//     * @return \think\response\Json
//     * @throws \think\Exception
//     * @throws \think\exception\PDOException
//     */
//    public function imgDelete(){
//        extract(input());
//        $photo = Db::name('article')->where('id',$id)->value('photo');
//        $add = str_replace('/uploads/images/',"",$add);
//        $imgs = explode(',',$photo);;
//        if(in_array($add,$imgs)){
//            $key = array_search ($add,$imgs);
//            $place  = 'uploads/images/'.$imgs[$key];
//            unset($imgs[$key]);
//            $img = implode(',',$imgs);
//            trim($img,',');
//            $res = Db::name('article')->where('id',$id)->update(['photo'=>$img]);
//            if($res){
//                unlink ($place);
//                return json(['code'=>200,'msg'=>'删除成功']);
//            }else{
//                return json(['code'=>100,'msg'=>'删除失败！']);
//            }
//        }
//    }
//
//    /**
//     * updatePhoto 修改图片
//     * @return \think\response\Json
//     */
//    public function updatePhoto(){
//        extract(input());
//        $photo = Db::name('article')->where('id',$id)->value('photo');
//        if($photo != ""){
//            $photo = $photo.",".$add;
//        }else{
//            $photo = $add;
//        }
//        $res = Db::name('article')->where('id',$id)->setField ('photo',$photo);
//        if($res){
//            return json(['code'=>200,'msg'=>'']);
//        }else{
//            return json(['code'=>100,'msg'=>'']);
//        }
//    }

    /**
     * [del_article 删除文章]
     * @return [type] [description]
     * @author
     */
    public function del_article()
    {
        $id = input('param.id');
        $cate = new ArticleModel();
        $flag = $cate->delArticle($id);
        return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
    }



    /**
     * [article_state 文章状态]
     * @return [type] [description]
     * @author
     */
    public function article_state()
    {
        extract(input());
        $cate = new ArticleModel();
        $flag = $cate->articleState($id,$num);
        return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
    }
    /**
     * batchDelArticle 批量删除文章
     * @return \think\response\Json
     */
    public function batchDelArticle(){
        extract(input());
        if(empty($ids)){
            return json(['code'=>100,'msg'=>'请选择要删除的记录！']);
        }
        $article = new ArticleModel();
        $flag = $article->batchDelArticle($ids);
        return json(['code' => $flag['code'], 'msg' => $flag['msg']]);
    }

    /**
     * 批量启用文章
     * @return \think\response\Json
     */
    public function usingArticle(){
        extract(input());
        $list = [];
        if($ids){
            $ids = explode(',',$ids);
            for($i=0;$i<count($ids);$i++){
                $param = [
                    'id'=>$ids[$i],
                    'status'=>1
                ];
                $list[] = $param;
            }
        }
        $article = new ArticleModel();
        $flag = $article->usingArticle($list);
        return json(['code' => $flag['code'], 'msg' => $flag['msg']]);
    }

    /**
     * 批量禁用文章
     * @return \think\response\Json
     */
    public function forbiddenArticle(){
        extract(input());
        $list = [];
        if($ids){
            $ids = explode(',',$ids);
            for($i=0;$i<count($ids);$i++){
                $param = [
                    'id'=>$ids[$i],
                    'status'=>2
                ];
                $list[] = $param;
            }
        }
        $article = new ArticleModel();
        $flag = $article->forbiddenArticle($list);
        return json(['code' => $flag['code'], 'msg' => $flag['msg']]);
    }


//    /**
//     * showImg 多图预览
//     * @return mixed
//     */
//    public function showImg(){
//        $imgs = Db::name('article')->where('id',input('id'))->value('photo');
//        $imgs = explode(',',$imgs);
////        $this->assign('imgs',$imgs);
////        return $this->fetch('article/show_img');
//        return json(['msg'=>$imgs,'one'=>$imgs[0]]);
//
//    }



    //*********************************************分类管理*********************************************//

    /**
     * [index_cate 分类列表]
     * @return [type] [description]
     * @author
     */
    public function index_cate(){
        if(request()->isAjax ()){
            extract(input());
            $map = [];
            if(isset($key)&&$key!=""){
                $map['name'] = ['like',"%" . $key . "%"];
            }
            if(isset($start)&&$start!=""&&isset($end)&&$end=="")
            {
                $map['create_time'] = ['>= time',$start];
            }
            if(isset($end)&&$end!=""&&isset($start)&&$start=="")
            {
                $map['create_time'] = ['<= time',$end];
            }
            if(isset($start)&&$start!=""&&isset($end)&&$end!="")
            {
                $map['create_time'] = ['between time',[$start,$end]];
            }
            $field=input('field');//字段
            $order=input('order');//排序方式
            if($field && $order){
                $od=$field." ".$order;
            }else{
                $od="create_time desc";
            }
            $cate = new ArticleCateModel();
            $nowpage = input('get.page') ? input('get.page'):1;
            $limits = input("limit")?input("limit"):10;
            $count = $cate->getAllCount($map);//计算总页面
            $lists = $cate->getAllCate($map, $nowpage, $limits,$od);
            return json(['code'=>0,'msg'=>'','count'=>$count,'data'=>$lists]);
        }
        return $this->fetch("article/index_cate");
    }


    /**
     * [add_cate 添加分类]
     * @return [type] [description]
     * @author
     */
    public function add_cate()
    {
        if(request()->isPost()){
            extract(input());
            $param = input('post.');
            if(!isset($status)){
                $param['status'] = 2;
            }
            $cate = new ArticleCateModel();
            $flag = $cate->insertCate($param);
            return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
        }

        return $this->fetch();
    }


    /**
     * [edit_cate 编辑分类]
     * @return [type] [description]
     * @author
     */
    public function edit_cate()
    {
        $cate = new ArticleCateModel();

        if(request()->isPost()){

            $param = input('post.');
            $flag = $cate->editCate($param);
            return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
        }

        $id = input('param.id');
        $this->assign('cate',$cate->getOneCate($id));
        return $this->fetch();
    }


    /**
     * [del_cate 删除分类]
     * @return [type] [description]
     * @author
     */
    public function del_cate()
    {
        $id = input('param.id');
        $cate = new ArticleCateModel();
        $flag = $cate->delCate($id);
        return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
    }



    /**
     * [cate_state 分类状态]
     * @return [type] [description]
     * @author
     */
    public function cate_state()
    {
        extract(input());
        $cate = new ArticleCateModel();
        $flag = $cate->cateState($id,$num);
        return json(['code' => $flag['code'], 'data' => $flag['data'], 'msg' => $flag['msg']]);
    }

    /**
     * batchDelCate 批量删除文章分类
     * @return \think\response\Json
     */
    public function batchDelCate(){
        extract(input());
        if(empty($ids)){
            return json(['code'=>100,'msg'=>'请选择要删除的记录！']);
        }
        $cate = new ArticleCateModel();
        $flag = $cate->batchDelCate($ids);
        return json(['code' => $flag['code'], 'msg' => $flag['msg']]);
    }

    /**
     * 批量启用分类
     * @return \think\response\Json
     */
    public function usingCate(){
        extract(input());
        $list = [];
        if($ids){
            $ids = explode(',',$ids);
            for($i=0;$i<count($ids);$i++){
                $param = [
                    'id'=>$ids[$i],
                    'status'=>1
                ];
                $list[] = $param;
            }
        }
        $cate = new ArticleCateModel();
        $flag = $cate->usingCate($list);
        return json(['code' => $flag['code'], 'msg' => $flag['msg']]);
    }

    /**
     * 批量禁用分类
     * @return \think\response\Json
     */
    public function forbiddenCate(){
        extract(input());
        $list = [];
        if($ids){
            $ids = explode(',',$ids);
            for($i=0;$i<count($ids);$i++){
                $param = [
                    'id'=>$ids[$i],
                    'status'=>2
                ];
                $list[] = $param;
            }
        }
        $cate = new ArticleCateModel();
        $flag = $cate->forbiddenCate($list);
        return json(['code' => $flag['code'], 'msg' => $flag['msg']]);
    }

    public function printOrder(){
        return $this->fetch('article/order');
    }
}