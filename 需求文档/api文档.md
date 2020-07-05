# 到云后台 API 接口文档

## API V1 接口说明

- 接口基准地址：http://129.211.87.192//daoyunapi/public/index.php
- 服务端已开启 CORS 跨域支持   
- 使用 HTTP Status Code 标识状态
- 数据返回格式统一使用 JSON

### 支持的请求方法

- GET（SELECT）：从服务器取出资源（一项或多项）。
- POST（CREATE）：在服务器新建一个资源。
- PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
- DELETE（DELETE）：从服务器删除资源。

### 通用返回状态说明

| *状态码* |         *含义*        |                        *说明*                       |
|----------|-----------------------|-----------------------------------------------------|
|      200 | OK                    | 请求成功                                            |
|      201 | CREATED               | 创建成功                                            |
|      204 | DELETED               | 删除成功                                            |
|      400 | BAD REQUEST           | 请求的地址不存在或者包含不支持的参数                |
|      401 | UNAUTHORIZED          | 未授权                                              |
|      403 | FORBIDDEN             | 被禁止访问                                          |
|      404 | NOT FOUND             | 请求的资源不存在                                    |
|      422 | Unprocesable entity   | [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误 |
|      500 | INTERNAL SERVER ERROR | 内部错误                                            |
|          |                       |                                                     |

---

## 登录

### 登录验证接口

* 请求路径：/login
* 请求方法：post
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| account  | 账号     | 不能为空 |
| password | 密码     | 不能为空 |

* 响应参数

| 参数名   | 参数说明 | 备注                                          |
| -------- | -------- | --------------------------------------------- |
| pathName | 跳转路径 | AdminHome表示用户类型是管理员，跳到管理员首页 |
| id       | 用户id   |                                               |
| name     | 用户名   |                                               |
| avatar   | 用户头像 | 头像文件名                                    |

* 响应数据

```javascript
{
  "data": {
    "pathName": "AdminHome",
    "id": 1,
    "name": "管理员",
    "avatar": "avatar.jpg"
  },
  "meta": {
    "msg": "登录成功",
    "status": 200
  }
}
```

## 管理员

### 获取左侧菜单

* 请求路径：/menus
* 请求方法：get

* 响应参数

| 参数名    | 参数说明     | 备注            |
| --------- | ------------ | --------------- |
| level     | 菜单等级     | 0表示一级菜单   |
| parent_id | 父菜单id     | 0表示没有父菜单 |
| path      | 菜单跳转路径 |                 |
| children  | 子菜单       |                 |

* 响应数据

```javascript
{
  "data": [
    {
      "id": 1,
      "name": "用户管理",
      "level": 0,
      "parent_id": 0,
      "path": "users",
      "type": 0,
      "children": [
        {
          "id": 2,
          "name": "用户列表",
          "level": 1,
          "parent_id": 1,
          "path": "users",
          "type": 0
        }
      ]
    }
  ],
  "meta": {
    "msg": "获取菜单成功",
    "status": 200
  }
}
```

### 获取用户列表

* 请求路径：/users
* 请求方法：get
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| pagesize | 分页尺寸 | 不能为空 |
| pagenum  | 当前页码 | 不能为空 |
| query    | 查询内容 | 可以为空 |

* 响应参数

| 参数名 | 参数说明         | 备注                    |
| ------ | ---------------- | ----------------------- |
| total  | 获取到的用户总数 |                         |
| users  | 具体用户数据     |                         |
| state  | 用户状态         | false代表被删除，不可用 |

* 响应数据

```javascript
{
    "data":{
        "total":214,
        "pagenum":"1",
        "users":[
            {
                "id":1,
                "account":"admin",
                "name":"管理员",
                "email":null,
                "type":"管理员",
                "state":true
            },
            {
                "id":2,
                "account":"teacher",
                "name":"池老标",
                "email":null,
                "type":"教师",
                "state":true
            },
            {
                "id":5,
                "account":"student",
                "name":"吴锦涵",
                "email":null,
                "type":"学生",
                "state":true
            },
       ]
    },
    "meta":{
        "msg":"获取用户信息成功",
        "status":200
    }
}
```

### 根据id获取用户

* 请求路径：/users/:id
* 请求方法：get
* 请求参数

| 参数名 | 参数说明 | 备注                  |
| ------ | -------- | --------------------- |
| id     | 用户 id  | 不能为空`携带在url中` |

* 响应数据

```javascript
{
    "data":{
        "id":1,
        "name":"管理员",
        "account":"admin",
        "pwd":"e10adc3949ba59abbe56e057f20f883e",
        "type":0,
        "roleName":"管理员"
    },
    "meta":{
        "msg":"获取用户信息成功",
        "status":200
    }
}
```

### 修改用户状态

* 请求路径：/users
* 请求方法：put
* 请求参数

| 参数名 | 参数说明 | 备注                    |
| ------ | -------- | ----------------------- |
| id     | 用户 id  | 不能为空                |
| state  | 用户状态 | 不能为空，true或者false |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"修改用户状态成功",
        "status":200
    }
}
```

### 修改用户名密码

* 请求路径：/users/:id
* 请求方法：put
* 请求参数

| 参数名 | 参数说明 | 备注                        |
| ------ | -------- | --------------------------- |
| id     | 用户 id  | 不能为空 `参数是url参数:id` |
| name   | 用户名   | 可以为空                    |
| pwd    | 用户密码 | 可以为空                    |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"修改用户信息成功",
        "status":200
    }
}
```

### 添加用户

* 请求路径：/users
* 请求方法：post
* 请求参数

| 参数名  | 参数说明 | 备注 |
| ------- | -------- | ---- |
| account | 用户账号 |      |
| pwd     | 用户密码 |      |
| email   | 用户邮箱 |      |
| name    | 用户名   |      |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"添加用户成功",
        "status":201
    }
}
```

### 删除用户

* 请求路径：users/:id
* 请求方法：delete
* 请求参数

| 参数名 | 参数说明 | 备注                       |
| ------ | -------- | -------------------------- |
| id     | 用户 id  | 不能为空`参数是url参数:id` |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"删除成功",
        "status":200
    }
}
```

### 修改用户角色

* 请求路径：/roles
* 请求方法：put
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 用户 id  | 不能为空 |
| roleName | 角色名   | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"修改用户角色成功",
        "status":200
    }
}
```

### 获取班级列表

* 请求路径：/classes
* 请求方法：get

* 响应数据

```javascript
{
    "data":[
        {
            "id":1,
            "invitation_code":"VW4568",
            "tno":2,
            "class_name":"软件工程训练1班 ",
            "task_id":"1,2,5,6,7,8,",
            "name":"池老标",
            "task":"asdsadaa"
        },
        {
            "id":18,
            "invitation_code":"ABC123",
            "tno":2,
            "class_name":"dsadasdas",
            "task_id":null,
            "name":"池老标",
            "task":""
        }
    ],
    "meta":{
        "msg":"获取班级信息成功",
        "status":200
    }
}
```

### 删除班级

* 请求路径：/classes
* 请求方法：delete
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 班级 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"删除班级成功",
        "status":200
    }
}
```

### 获取班级学生

* 请求路径：/students
* 请求方法：get
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 班级id   | 不能为空 |
| pagenum  | 当前页码 | 不能为空 |
| pagesize | 每页尺寸 | 不能为空 |

* 响应数据

```javascript
{
    "data":{
        "total":1,
        "pagenum":"1",
        "students":[
            {
                "account":"student",
                "name":"吴锦涵",
                "exp":0,
                "id":5,
                "state":true
            }
        ]
    },
    "meta":{
        "msg":"获取学生信息成功",
        "status":200
    }
}
```

### 获取签到记录

* 请求路径：/signs
* 请求方法：get
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 班级id   | 不能为空 |
| pagenum  | 当前页码 | 不能为空 |
| pagesize | 分页尺寸 | 不能为空 |

* 响应数据

```javascript
{
    "data":{
        "total":6,
        "pagenum":"1",
        "signs":[
            {
                "id":1,
                "code":"JT2346",
                "sname":"吴锦涵",
                "class_id":1,
                "time":"09-58-10",
                "sno":5,
                "state":true,
                "account":"student",
                "date":"2020-04-06"
            },
            {
                "id":10,
                "code":"VSINH3",
                "sname":"吴锦涵",
                "class_id":1,
                "time":"21-27-05",
                "sno":5,
                "state":false,
                "account":"student",
                "date":"2020-04-17"
            }
        ]
    },
    "meta":{
        "msg":"获取签到信息成功",
        "status":200
    }
}
```

### 改变签到状态

* 请求路径：/signs
* 请求方法：put
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 签到id   | 不能为空 |
| state  | 签到状态 | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"修改签到状态成功",
        "status":200
    }
}
```

### 获取任务列表

* 请求路径：/tasks
* 请求方法：get
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 班级 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        {
            "id":1,
            "exp":10,
            "start_time":1585929600,
            "end_time":1586188800,
            "name":"第一周项目任务",
            "detail":"dasdsadsaa大树打撒多多多多多多多多多多多多多多多多多多多",
            "state":"过期",
            "deadline":"2020-04-07"
        },
        {
            "id":8,
            "exp":10,
            "start_time":1587130067,
            "end_time":1587571200,
            "name":"dsadsa",
            "detail":"asdsadaa",
            "state":"进行中",
            "deadline":"2020-04-23"
        }
    ],
    "meta":{
        "msg":"获取签到信息成功",
        "status":200
    }
}
```

### 删除任务

* 请求路径：/tasks
* 请求方法：put
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 任务 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"删除任务成功",
        "status":200
    }
}
```

### 获取任务细节

* 请求路径：/taskDetails
* 请求方法：get
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| taskId   | 任务 id  | 不能为空 |
| classId  | 班级 id  | 不能为空 |
| pagenum  | 当前页码 | 不能为空 |
| pagesize | 分页尺寸 | 不能为空 |

* 响应数据

```javascript
{
    "data":{
        "total":1,
        "pagenum":"1",
        "taskDetails":[
            {
                "id":2,
                "task_id":1,
                "sno":5,
                "state":1,
                "presentation":"http:\/\/localhost\/daoyunapi\/public\/uploads\/20200417\/f54df6f353e032c61dde15746ebaf5da.docx",
                "account":"student",
                "name":"吴锦涵"
            }
        ],
        "detail":"dasdsadsaa大树打撒多多多多多多多多多多多多多多多多多多多"
    },
    "meta":{
        "msg":"获取具体任务信息成功",
        "status":200
    }
}
```

## 教师

### 获取班级列表

* 请求路径：/teacherClasses
* 请求方法：get
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| uid    | 用户id   | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        {
            "id":1,
            "invitation_code":"VW4568",
            "tno":2,
            "class_name":"软件工程训练1班 ",
            "task_id":"1,2,5,6,7,8,",
            "name":"池老标",
            "task":"asdsadaa"
        },
        {
            "id":10,
            "invitation_code":"ABC456",
            "tno":2,
            "class_name":"知识产权1班 ",
            "task_id":"1,",
            "name":"池老标",
            "task":"dasdsadsaa大树打撒多多多多多多多多多多多多多多多多多多多"
        },
    ],
    "meta":{
        "msg":"获取班级信息成功",
        "status":200
    }
}
```

### 删除班级

* 请求路径：/teacherClasses
* 请求方法：delete
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 班级 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"删除班级成功",
        "status":200
    }
}
```

### 新增班级

* 请求路径：/teacherClasses
* 请求方法：post
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| uid    | 教师 id  | 不能为空 |
| code   | 班级码   |          |
| name   | 班级名字 |          |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"添加班级成功",
        "status":201
    }
}
```

### 根据id获取班级

* 请求路径：/teacherClasses
* 请求方法：get
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 班级 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        {
            "id":1,
            "invitation_code":"VW4568",
            "tno":2,
            "class_name":"软件工程训练1班 ",
            "task_id":"1,2,5,6,7,8,",
            "name":"池老标",
            "task":"asdsadaa"
        },
    ],
    "meta":{
        "msg":"获取班级信息成功",
        "status":200
    }
}
```

### 模糊搜索学生

* 请求路径：/teacherStudents
* 请求方法：get
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 班级 id  | 不能为空 |
| pagesize | 分页尺寸 | 不能为空 |
| pagenum  | 当前页码 | 不能为空 |
| query    | 查询内容 |          |

* 响应数据

```javascript
{
    "data":{
        "total":1,
        "pagenum":"1",
        "students":[
            {
                "account":"student",
                "name":"吴锦涵",
                "exp":0,
                "id":5,
                "state":true
            }
        ]
    },
    "meta":{
        "msg":"获取学生信息成功",
        "status":200
    }
}
```

### 添加学生

* 请求路径：/teacherStudents
* 请求方法：post
* 请求参数

| 参数名  | 参数说明 | 备注     |
| ------- | -------- | -------- |
| id      | 班级 id  | 不能为空 |
| account | 学生账号 | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"添加学生成功",
        "status":201
    }
}
```

### 删除学生

* 请求路径：/teacherStudents
* 请求方法：delete
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| id     | 班级 id  | 不能为空 |
| uid    | 学生 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"删除学生成功",
        "status":200
    }
}
```

### 发起签到

* 请求路径：/teacherSigns
* 请求方法：post
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 班级 id  | 不能为空 |
| code     | 签到码   | 不能为空 |
| signtime | 签到时间 | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"发起签到成功",
        "status":200
    }
}
```

### 查询签到记录

* 请求路径：/teacherSigns
* 请求方法：get
* 请求参数

| 参数名     | 参数说明 | 备注     |
| ---------- | -------- | -------- |
| id         | 班级 id  | 不能为空 |
| pagenum    | 当前页码 | 不能为空 |
| pagesize   | 分页尺寸 | 不能为空 |
| searchtime | 查询时间 | 不能为空 |

* 响应数据

```javascript
{
    "data":{
        "total":2,
        "pagenum":"1",
        "signs":[
            {
                "id":11,
                "code":"6WAXC0",
                "sname":"吴锦涵",
                "class_id":1,
                "time":"10-33-05",
                "sno":5,
                "state":false,
                "account":"student",
                "date":"2020-04-19"
            },
            {
                "id":12,
                "code":"6WAXC0",
                "sname":"余晶辣鸡",
                "class_id":1,
                "time":"10-33-05",
                "sno":224,
                "state":false,
                "account":"190327122",
                "date":"2020-04-19"
            }
        ]
    },
    "meta":{
        "msg":"获取签到信息成功",
        "status":200
    }
}
```

### 发布任务

* 请求路径：/teacherTasks
* 请求方法：post
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 班级 id  | 不能为空 |
| name     | 任务名字 | 不能为空 |
| detail   | 任务细节 | 不能为空 |
| deadline | 截止日期 | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"发布任务成功",
        "status":201
    }
}
```

## 学生

### 获取班级列表

* 请求路径：/studentClasses
* 请求方法：get
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| uid    | 用户 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        {
            "id":1,
            "invitation_code":"VW4568",
            "tno":2,
            "class_name":"软件工程训练1班 ",
            "task_id":"1,2,5,6,7,8,9,",
            "name":"池老标",
            "task":"的撒打算的撒了"
        },
        {
            "id":12,
            "invitation_code":"MLK980",
            "tno":2,
            "class_name":"算法设计与分析1班 ",
            "task_id":"1,2,",
            "name":"池老标",
            "task":"dsadsa"
        }
    ],
    "meta":{
        "msg":"获取班级信息成功",
        "status":200
    }
}
```

### 加入班级

* 请求路径：/studentClasses
* 请求方法：post
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| code   | 班级码   | 不能为空 |
| uid    | 用户 id  | 可以为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"加入班级成功",
        "status":201
    }
}
```

### 获取签到记录

* 请求路径：/signs
* 请求方法：get
* 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| id       | 班级 id  | 不能为空 |
| uid      | 用户 id  | 不能为空 |
| pagenum  | 当前页码 | 不能为空 |
| pagesize | 分页尺寸 | 不能为空 |

* 响应数据

```javascript
{
    "data":{
        "total":1,
        "pagenum":"1",
        "signs":[
            {
                "id":3,
                "code":"QGKRBB",
                "sname":"吴锦涵",
                "class_id":12,
                "time":"08-00-00",
                "sno":5,
                "state":false,
                "account":"student",
                "date":"1970-01-01"
            }
        ]
    },
    "meta":{
        "msg":"获取签到信息成功",
        "status":200
    }
}
```

### 提交任务

* 请求路径：/upload
* 请求方法：post
* 请求参数

| 参数名 | 参数说明 | 备注     |
| ------ | -------- | -------- |
| file   | 任务文件 | 不能为空 |
| uid    | 用户 id  | 不能为空 |
| taskId | 任务 id  | 不能为空 |

* 响应数据

```javascript
{
    "data":[
        
    ],
    "meta":{
        "msg":"上传成功",
        "status":200
    }
}
```

