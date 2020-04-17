/*
* @Author: wujinhan
* @Date:   2020-04-03 15:24:47
* @Last Modified by:   wujinhan
* @Last Modified time: 2020-04-11 16:52:00
*/
export default {
	name: 'Userlist',
  data() {
    return {
      loading: true,
      tableData: [],
      pagesize: 10,
      currentPage: 1,
      total: 0,
      searchValue: '',
      // 添加用户的对话框是否显示
      addUserFormVisible: false,
      // 编辑用户的对话框是否显示
      editUserFormVisible: false,
      // 分配角色的对话框是否显示
      selectRolesDialogVisible: false,
      // 分配角色 ，绑定下拉框的角色列表
      options: [
        {type:0,roleName:'管理员',id:0},
        {type:1,roleName:'学生',id:1},
        {type:2,roleName:'教师',id:2},
      ],
      selectRolesFormData: {
        account: '',
        roleName: '',
        type: ''
      },
      addFormData: {
        name: '',
        pwd: '',
        email: '',
        account: ''
      },
      editFormData: {
        name: '',
        pwd: '',
        account: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 11, message: '长度在 6 到 11 个字符', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '邮箱地址格式不正确', trigger: 'blur' }
        ],
        account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
        ]
      }
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    async load() {
      // 从本地存储中获取令牌
      const params = { pagenum: this.currentPage, pagesize: this.pagesize };
      const data = await this.$http.get('/users', {
        params
      });
      this.loading = false;
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.users;
        // 总数据条数
        this.total = data.data.data.total;
      } else {
        this.$message({
          type: 'error',
          message: data.data.meta.msg
        });
      }
    },
    async handleSearch() {
      this.loading = true;
      const params = {
        pagenum: this.currentPage,
        pagesize: this.pagesize,
        query: this.searchValue
      };
      const data = await this.$http.get('/users', {
        params
      });
      this.loading = false;
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.users;
        // 总数据条数
        this.total = data.data.data.total;
      } else {
        this.$message({
          type: 'error',
          message: data.data.meta.msg
        });
      }
    },
    // 分页方法
    handleSizeChange(val) {
      this.pagesize = val;
      // 当每页条数发生变化，修改当前页码为第一页
      this.currentPage = 1;
      this.load();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.load();
    },
    // 用户状态改变
    async handleChange(val, id) {
      const params = { id: id, state: val };
      const data = await this.$http.put('/users', params);
      // const data = await this.$http.put(`/users/${id}/state/${val}`);
      if (data.data.meta.status === 200) {
        this.$message({
          type: 'success',
          message: data.data.meta.msg
        });
        this.load();
      } else {
        this.$message({
          type: 'error',
          message: data.data.meta.msg
        });
      }
    },
    // 提交表单
    async handleAddUser() {
      // 表单提交前，先进行表单验证
      this.$refs.addUserForm.validate(async (valid) => {
        if (!valid) {
          return;
        }
        // 表单提交
        const data = await this.$http.post('/users', this.addFormData);
        if (data.data.meta.status === 201) {
          // 重新加载列表
          this.load();
          // 隐藏添加窗口
          this.addUserFormVisible = false;
          this.$message({
            type: 'success',
            message: data.data.meta.msg
          });
          // this.$refs.ruleForm.resetFields();
        } else {
          this.$message({
            type: 'error',
            message: data.data.meta.msg
          });
        }
      });
    },
    // 删除
    async handleDelete(user) {
      // 删除提示
      this.$confirm('确认要删除该用户么？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const data = await this.$http.delete(`/users/${user.id}`);
        if (data.data.meta.status === 200) {
          console.log(this.load);
          // 删除成功重新加载数据
          this.load();
          this.$message({
            type: 'success',
            message: data.data.meta.msg
          });
        } else {
          this.$message({
            type: 'error',
            message: data.data.meta.msg
          });
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 编辑 - 获取用户信息
    async handleGetUserInfo(user) {
      // 显示修改用户对话框
      this.editUserFormVisible = true;
      const res = await this.$http.get(`/users/${user.id}`);
      this.editFormData = res.data.data;
    },
    // 编辑 - 修改用户信息
    async handleEditUser() {
      this.editUserFormVisible = false;
      // 获取用户id
      const id = this.editFormData.id;
      const res = await this.$http.put(`/users/${id}`, this.editFormData);
      if (res.data.meta.status === 200) {
        this.$message({
          type: 'success',
          message: res.data.meta.msg
        });
        // 重新加载数据
        this.load();
      } else {
        this.$message({
          type: 'error',
          message: res.data.meta.msg
        });
      }
    },
    // 显示分配角色的对话框
    async showSelectRoles(user) {
      const userRes = await this.$http.get(`/users/${user.id}`);
      // 请求所有的角色，绑定到下拉框中
      this.selectRolesDialogVisible = true;
      this.selectRolesFormData = userRes.data.data;
    },
    // 分配角色
    async handleRole() {
      this.selectRolesDialogVisible = false;
      const res = await this.$http.put('roles', {
        roleName: this.selectRolesFormData.roleName,id:this.selectRolesFormData.id
      });
      if (res.data.meta.status === 200) {
        this.selectRolesDialogVisible = false;
        this.$message({
          type: 'success',
          message: '分配角色成功'
        });
        this.load();
      } else {
        this.$message({
          type: 'error',
          message: '分配角色失败'
        });
      }
    }
  }
};