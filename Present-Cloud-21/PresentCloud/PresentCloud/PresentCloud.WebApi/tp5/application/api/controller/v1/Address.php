<?php
namespace app\api\controller\v1;
use app\api\controller\BaseController;
use app\api\service\Token as TokenService;
use app\api\model\User;
use app\api\validate\AddressGet;
use app\lib\exception\UserException;
use app\lib\exception\TokenException;
use app\lib\exception\SuccessException;
use app\lib\exception\ForbiddenException;
use app\lib\enum\ScopeEnum;

class Address extends BaseController{
    protected $beforeActionList=[
        // 用前置操作，后面的函数名字必须全部转为小写，不然进不来
        'checkPrimaryScope'=>['only'=>'createorupdateaddress']
    ];

    // checkscope是createorupdateaddress的前置函数，相当于回调函数，在这里面return非空字符串不会进行输出只会当成是return true，然后继续执行createorupdateaddress。在这里面只有使用halt和抛出异常才能阻止程序的继续执行
    // protected function checkScope(){
    //     $scope=TokenService::getCurrentTokenVar('scope');
    //     if($scope){
    //         if($scope>=ScopeEnum::USER){
    //             return true;
    //         }else{
    //             throw new ForbiddenException();
    //         }
    //     }else{
    //         throw new TokenException();
    //     }     
    // }

    // 测试该接口的时候token放header里面，地址数据放body里
    public function createOrUpdateAddress($a,$b){
        // 数据是从小程序的客户端提交过来的，post方式提交的json数据
        $validate=validate('AddressGet');
        $validate->goCheck();

        $uid=TokenService::getCurrentUid();
        $user=User::get($uid);
        if(!$user){
                throw new UserException([
                'code'=>404,
                'msg'=>'用户收获地址不存在',
                'errorCode'=>60001
            ]);
        }


        $datas=$validate->getDatasByRule(input('post.'));

         $userAddress=$user->address;
         //通过user表关联修改useraddress表而不是直接修改useraddress
        if(!$userAddress){
                $user->address()->save($datas);
        }else{
                $user->address->save($datas);
        }

        // return $user;
        // return 'success';
        // return new SuccessException()会报错
        throw new SuccessException();
    }
}