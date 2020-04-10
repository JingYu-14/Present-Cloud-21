<?php

/**
 * @Author: wujinhan
 * @Date:   2020-03-30 16:05:45
 * @Last Modified by:   wujinhan
 * @Last Modified time: 2020-03-30 16:06:35
 */
namespace app\api\model;

use think\Model;

class User extends Model
{
    public function items()
    {
        return $this->hasMany('BannerItem', 'banner_id', 'id');
    }
}
