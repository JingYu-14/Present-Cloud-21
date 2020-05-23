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
Route::get('classes', 'api/Admin/getClasses');
Route::delete('classes', 'api/Admin/deleteClass');
Route::get('students', 'api/Admin/getStudents');
Route::get('signs', 'api/Admin/getSigns');
Route::put('signs', 'api/Admin/changeSignState');
Route::get('tasks', 'api/Admin/getTasks');
Route::put('tasks', 'api/Admin/changeTaskState');
Route::get('taskDetails', 'api/Admin/getTaskDetails');
Route::get('schools', 'api/Admin/getSchools');
Route::get('schools/:id', 'api/Admin/getSchool');
Route::post('schools', 'api/Admin/addSchools');
Route::delete('schools/:id', 'api/Admin/deleteSchools');
Route::put('schools/:id', 'api/Admin/changeSchoolInfo');
Route::get('systems', 'api/Admin/getSystems');
Route::get('systems/:id', 'api/Admin/getSystem');
Route::post('systems', 'api/Admin/addSystems');
Route::delete('systems/:id', 'api/Admin/deleteSystems');
Route::put('systems/:id', 'api/Admin/changeSystemInfo');

Route::get('teacherMenus', 'api/Teacher/getMenus');
Route::get('teacherClasses', 'api/Teacher/getClasses');
Route::post('teacherClasses', 'api/Teacher/addClass');
Route::delete('teacherClasses', 'api/Teacher/deleteClass');
Route::get('teacherStudents', 'api/Teacher/getStudents');
Route::post('teacherStudents', 'api/Teacher/addStudent');
Route::delete('teacherStudents', 'api/Teacher/deleteStudent');
Route::post('teacherSigns', 'api/Teacher/addSign');
Route::get('teacherSigns', 'api/Teacher/getSign');
Route::post('teacherTasks', 'api/Teacher/addTask');

Route::get('studentMenus', 'api/Student/getMenus');
Route::get('studentClasses', 'api/Student/getClasses');
Route::post('studentClasses', 'api/Student/addClass');
Route::get('studentSigns', 'api/Student/getSign');
Route::post('upload', 'api/Student/upload');

Route::put('sign', 'api/Student/sign');