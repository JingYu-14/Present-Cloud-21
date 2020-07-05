<template>
	<div> 
   <my-breadcrumb level2="系统管理" level3="参数列表"></my-breadcrumb> 
   <!-- <el-row> 
    <el-col :span="24"> 
     <div class="search"> 
      <el-button type="success" @click="addParamsFormVisible = true" plain="">
       添加
      </el-button> 
     </div> 
    </el-col> 
   </el-row>  -->
   <el-table :border="true" :data="tableData" v-loading="loading" style="width: 100%"> 
    <el-table-column type="index" width="50"> 
    </el-table-column> 
    <el-table-column prop="sign_exp" label="签到经验" width="160"> 
    </el-table-column> 
    <el-table-column prop="sign_dist" label="签到距离" width="160"> 
    </el-table-column> 
    <el-table-column prop="task_exp" label="任务经验" width="300"> 
    </el-table-column> 
    <el-table-column label="操作" width="200"> 
     <template slot-scope="scope"> 
      <el-button type="primary" @click="handleGetParamsInfo(scope.row)" size="mini" icon="el-icon-edit" plain=""></el-button> 
      <el-button type="danger" @click="handleDelete(scope.row)" size="mini" icon="el-icon-delete" plain=""></el-button> 
     </template> 
    </el-table-column> 
   </el-table> 
   <!-- 表格分页 --> 
   <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total"> 
   </el-pagination>  
   <!-- 添加组织 --> 
   <el-dialog title="添加参数" :visible.sync="addParamsFormVisible"> 
    <el-form label-position="right" label-width="100px" :rules="rules" ref="addParamsForm" :model="addFormData"> 
     <el-form-item label="签到经验" prop="sign_exp"> 
      <el-input v-model="addFormData.sign_exp" auto-complete="off"></el-input> 
     </el-form-item> 
     <el-form-item label="签到距离" prop="sign_dist"> 
      <el-input v-model="addFormData.sign_dist" auto-complete="off"></el-input> 
     </el-form-item> 
     <el-form-item label="任务经验" prop="task_exp"> 
      <el-input v-model="addFormData.task_exp" auto-complete="off"></el-input> 
     </el-form-item> 
    </el-form> 
    <div slot="footer" class="dialog-footer"> 
     <el-button @click="addParamsFormVisible = false">
      取 消
     </el-button> 
     <el-button type="primary" @click="handleAddParams">
      确 定
     </el-button> 
    </div> 
   </el-dialog> 
   <!-- 修改用户 --> 
   <el-dialog title="修改参数" :visible.sync="editParamsFormVisible"> 
    <el-form label-position="right" label-width="100px" :rules="rules" ref="editParamsForm" :model="editFormData"> 
     <el-form-item label="签到经验" prop="sign_exp"> 
      <el-input v-model="editFormData.sign_exp" :disabled="true" size="medium" auto-complete="off"></el-input> 
     </el-form-item> 
     <el-form-item label="签到距离" prop="sign_dist"> 
      <el-input v-model="editFormData.sign_dist" size="medium" auto-complete="off"></el-input> 
     </el-form-item>
      <el-form-item label="任务经验" prop="task_exp"> 
      <el-input v-model="editFormData.task_exp"></el-input> 
     </el-form-item> 
    </el-form> 
    <div slot="footer" class="dialog-footer"> 
     <el-button @click="editParamsFormVisible = false">
      取 消
     </el-button> 
     <el-button type="primary" @click="handleEditParams">
      确 定
     </el-button> 
    </div> 
   </el-dialog> 
</div>
</template>

<script>
export default {
  name: 'Schools',
  data() {
    return {
      loading: true,
      tableData: [],
      pagesize: 10,
      currentPage: 1,
      total: 0,
      // 添加用户的对话框是否显示
      addParamsFormVisible: false,
      // 编辑用户的对话框是否显示
      editParamsFormVisible: false,
      addFormData: {
        sign_exp: '',
        sign_dist: '',
        task_exp: '',
      },
      editFormData: {
        sign_exp: '',
        sign_dist: '',
        task_exp: '',
      },
      rules: {
        sign_exp: [
          { required: true, message: '请设置签到经验', trigger: 'blur' },
        ],
        sign_dist: [
          { required: true, message: '请设置签到距离', trigger: 'blur' },
        ],
        task_exp: [
          { required: true, message: '请设置任务经验', trigger: 'blur' },
        ],
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
      const data = await this.$http.get('/systems', {
        params
      });
      this.loading = false;
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.systems;
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
    // 提交表单
    async handleAddParams() {
      // 表单提交前，先进行表单验证
      this.$refs.addParamsForm.validate(async (valid) => {
        if (!valid) {
          return;
        }
        // 表单提交
        const data = await this.$http.post('/systems', this.addFormData);
        if (data.data.meta.status === 201) {
          // 重新加载列表
          this.load();
          // 隐藏添加窗口
          this.addParamsFormVisible = false;
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
    async handleDelete(system) {
      // 删除提示
      this.$confirm('确认要删除该用户么？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const data = await this.$http.delete(`/systems/${system.id}`);
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
    async handleGetParamsInfo(system) {
      // 显示修改用户对话框
      this.editParamsFormVisible = true;
      const res = await this.$http.get(`/systems/${system.id}`);
      this.editFormData = res.data.data;
    },
    // 编辑 - 修改用户信息
    async handleEditParams() {
      this.editParamsFormVisible = false;
      // 获取用户id
      const id = this.editFormData.id;
      const res = await this.$http.put(`/systems/${id}`, this.editFormData);
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
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .search .el-input {
  width: 300px;
}
</style>