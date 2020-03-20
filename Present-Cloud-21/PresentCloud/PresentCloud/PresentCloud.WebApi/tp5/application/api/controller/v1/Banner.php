<?php
namespace app\api\controller\v1;
use think\Controller;
use think\Validate;
use think\Exception;
use app\lib\exception\BannerMissException;
use app\api\model\Banner as BannerModel;
use app\api\model\Theme as ThemeModel;

class Banner extends Controller
{
    /**
     * 获取指定id的banner信息
     * @url /banner/:id
     * @http GET
     * @id banner的id号
     */
    public function getBanner($id)
    {
        $validate=validate('IdValidate');
        $validate->goCheck();

        // 正常处理异常
        // try{
        //     $banner=BannerModel::getBannerById($id);
        // }catch(Exception $ex){
        //     $err=[
        //         'error_code'=>10001,
        //         'msg'=>$ex->getMessage()
        //     ];
        //     return json($err,400);
        // }
        
        // 全局处理异常
        // $banner=BannerModel::getBannerById($id);
        // 以下返回的$banner是一个对象
        // ORM里面特有的查询get和all不能用于链式操作，但是find和select可在ORM里使用，BannerModel::with('items')->get($id)会出错
        // $banner=BannerModel::with('items')->find($id);
        // 嵌套关联
        // $banner=BannerModel::with(['items','items.img'])->find($id);
        $banner=BannerModel::getBannerById($id);

        // 在模型外面隐藏字段，不方便
        // $banner->hidden(['delete_time','update_time']);
        // $banner->visible(['id']);

        if(!$banner){
            throw new BannerMissException();
        }

        // extra文件夹中的配置文件能够自动加载，config(文件名.配置参数名)
        // $base=config('setting.img_prefix');
        // return $base;
        return json($banner);
    }
}
