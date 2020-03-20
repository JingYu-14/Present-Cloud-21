<?php
/**
 * Created by PhpStorm.
 * User: Kevin
 * Date: 2018/11/14
 * Time: 15:51
 */
namespace app\admin\controller;
use think\Db;
class Email extends Base
{
    public function index(){
        return $this->fetch('email/index');
    }

    /*
     * 发送邮件
     */
    public function sendEmail(){
        extract(input());
        $res = sendMail($mailTo,$subject,$body);
        if($res == true){
            return json(['code'=>200,'msg'=>'发送成功']);
        }else{
            return json(['code'=>100,'msg'=>'发送失败']);
        }
    }

    /*
     * 发送云之讯短信
     */
    public function sendYzxCode(){
        extract(input());
        $res = YzxSms($code,$phone);
        if($res['code'] == 000000){
            return json(['code'=>200,'msg'=>'发送成功']);
        }else{
            return json(['code'=>100,'msg'=>$res['msg']]);
        }
    }

    /*
     * 发送阿里短信
     */
    public function sendAliCode(){
        extract(input());
        $res = alisms($phone,$code);
        if($res['Code'] == 'OK'){
            return json(['code'=>200,'msg'=>'发送成功']);
        }else{
            return json(['code'=>100,'msg'=>$res['Message']]);
        }
    }

}