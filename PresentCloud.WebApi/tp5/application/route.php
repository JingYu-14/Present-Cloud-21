<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2018 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

// 静态配置路由
/*return [
    '__pattern__' => [
        'name' => '\w+',
    ],
    '[hello]'     => [
        ':id'   => ['index/hello', ['method' => 'get'], ['id' => '\d+']],
        ':name' => ['index/hello', ['method' => 'post']],
    ],

];*/

// 动态配置路由
use think\Route;

/*url三种访问模式，PATH_INFO，强制路由模式，混合模式（有的操作可以使用PATH_INFO访问，有的可以使用路由模式访问；而不是说同一个操作可以由两种模式访问）*/
// 默认配置是混合模式，开发api的话建议用强制路由模式
// 一个模块下的一个控制器的一个操作，如果设置了路由模式那么就不能再用PAYH_INFO模式访问
// http://www.tp5.com/sample/test/hello算PATH_INFO模式，http://www.tp5.com/hello算路由模式
// Route::rule('hello','sample/test/hello');

/*Route::rule('路由表达式','路由地址','请求类型','路由参数(数组)','变量规则(数组)');
tp5的请求类型有四种GET，POST，DELETE，PUT，*代表任何一种请求类型都可
*/
// Route::rule('hello','sample/test/hello','GET');
// 快捷绑定路由的几种方法，Route::get，Route::post，*对应Route::any
// Route::get('hello','sample/test/hello');
// Route::post('hello/:id','sample/test/hello');


// 项目开始
// 不要以为第二个参数是完整的路径api/controller/v1/Banner/getBanner，规定是按模块名+控制器名+操作名
// Route::get('api/v1/banner/:id','api/v1.Banner/getBanner');

// 动态接收版本号，然后选择对应控制器
Route::post('api/:version/index','api/:version.Index/index');
//Route::get('api/:version/banner/:id','api/:version.Banner/getBanner');

Route::get('api/:version/theme/:ids','api/:version.Theme/getSimpleList');
Route::get('api/:version/themeproduct/:id','api/:version.Theme/getComplexOne');

Route::get('api/:version/product/by_category/:id','api/:version.Product/			getByCategory');
Route::get('api/:version/product/:id','api/:version.Product/getOne',[],
			['id'=>'\d+']);
Route::get('api/:version/product/recent/:count','api/:version.Product/getRecent');

Route::get('api/:version/category','api/:version.Category/getCategory');

Route::post('api/:version/token/user','api/:version.Token/getToken');

// 设置路由如果太长分为两行的话要注意中间不能有空格，不然会提示找不到方法
Route::post('api/:version/address','api/:version.Address/createOrUpdateAddress');

Route::post('api/:version/order','api/:version.Order/placeOrder');
