<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2016 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

// return [
//     '__pattern__' => [
//         'name' => '\w+',
//     ],
//     '[hello]'     => [
//         ':id'   => ['index/hello', ['method' => 'get'], ['id' => '\d+']],
//         ':name' => ['index/hello', ['method' => 'post']],
//     ],

// ];
use think\Route;

Route::get('test', 'index/Index/index');
Route::post('login', 'api/Login/index');
Route::get('menus', 'api/Admin/getMenus');
Route::get('users', 'api/Admin/getUsers');
Route::get('users/:id', 'api/Admin/getUser');
Route::put('users', 'api/Admin/changeUserState');
Route::put('users/:id', 'api/Admin/changeUserInfo');
Route::post('users', 'api/Admin/addUsers');
Route::delete('users/:id', 'api/Admin/deleteUsers');
Route::put('roles', 'api/Admin/changeUserType');
