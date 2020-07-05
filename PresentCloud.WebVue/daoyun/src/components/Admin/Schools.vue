<template>
	<div> 
   <my-breadcrumb level2="校园管理" level3="校园列表"></my-breadcrumb> 
   <el-row> 
    <el-col :span="24"> 
     <div class="search"> 
      <el-input v-model="searchValue" placeholder="请输入内容" clearable=""> 
       <el-button @click="handleSearch" slot="append" icon="el-icon-search"></el-button> 
      </el-input> 
      <el-button type="success" @click="addOrgFormVisible = true" plain="">
       添加
      </el-button> 
     </div> 
    </el-col> 
   </el-row> 
   <el-table :border="true" :data="tableData" v-loading="loading" style="width: 100%"> 
    <el-table-column type="index" width="50"> 
    </el-table-column> 
    <el-table-column prop="school" label="学校" width="160"> 
    </el-table-column> 
    <el-table-column prop="college" label="学院" width="160"> 
    </el-table-column> 
    <el-table-column prop="profession" label="专业" width="300"> 
    </el-table-column> 
    <el-table-column label="操作" width="200"> 
     <template slot-scope="scope"> 
      <el-button type="primary" @click="handleGetOrgInfo(scope.row)" size="mini" icon="el-icon-edit" plain=""></el-button> 
      <el-button type="danger" @click="handleDelete(scope.row)" size="mini" icon="el-icon-delete" plain=""></el-button> 
     </template> 
    </el-table-column> 
   </el-table> 
   <!-- 表格分页 --> 
   <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total"> 
   </el-pagination>  
   <!-- 添加组织 --> 
   <el-dialog title="添加组织" :visible.sync="addOrgFormVisible"> 
    <el-form label-position="right" label-width="100px" :rules="rules" ref="addOrgForm" :model="addFormData"> 
     <el-form-item label="学校" prop="school"> 
      <el-input v-model="addFormData.school" auto-complete="off"></el-input> 
     </el-form-item> 
     <el-form-item label="学院" prop="college"> 
      <el-input v-model="addFormData.college" auto-complete="off"></el-input> 
     </el-form-item> 
     <el-form-item label="专业" prop="profession"> 
      <el-input v-model="addFormData.profession" auto-complete="off"></el-input> 
     </el-form-item> 
    </el-form> 
    <div slot="footer" class="dialog-footer"> 
     <el-button @click="addOrgFormVisible = false">
      取 消
     </el-button> 
     <el-button type="primary" @click="handleAddOrg">
      确 定
     </el-button> 
    </div> 
   </el-dialog> 
   <!-- 修改用户 --> 
   <el-dialog title="修改用户" :visible.sync="editOrgFormVisible"> 
    <el-form label-position="right" label-width="100px" :rules="rules" ref="editOrgForm" :model="editFormData"> 
     <el-form-item label="学校" prop="school"> 
      <el-input v-model="editFormData.school" :disabled="true" size="medium" auto-complete="off"></el-input> 
     </el-form-item> 
     <el-form-item label="学院" prop="college"> 
      <el-input v-model="editFormData.college" size="medium" auto-complete="off"></el-input> 
     </el-form-item>
      <el-form-item label="专业" prop="profession"> 
      <el-input v-model="editFormData.profession"></el-input> 
     </el-form-item> 
    </el-form> 
    <div slot="footer" class="dialog-footer"> 
     <el-button @click="editOrgFormVisible = false">
      取 消
     </el-button> 
     <el-button type="primary" @click="handleEditOrg">
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
      searchValue: '',
      // 添加用户的对话框是否显示
      addOrgFormVisible: false,
      // 编辑用户的对话框是否显示
      editOrgFormVisible: false,
      addFormData: {
        school: '',
        college: '',
        profession: '',
      },
      editFormData: {
        school: '',
        college: '',
        profession: '',
      },
      rules: {
        school: [
          { required: true, message: '请输入学校名字', trigger: 'blur' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        college: [
          { required: true, message: '请输入学院名字', trigger: 'blur' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        profession: [
          { required: true, message: '请输入专业名字', trigger: 'blur' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
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
      const data = await this.$http.get('/schools', {
        params
      });
      this.loading = false;
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.schools;
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
      const data = await this.$http.get('/schools', {
        params
      });
      this.loading = false;
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.schools;
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
    async handleAddOrg() {
      // 表单提交前，先进行表单验证
      this.$refs.addOrgForm.validate(async (valid) => {
        if (!valid) {
          return;
        }
        // 表单提交
        const data = await this.$http.post('/schools', this.addFormData);
        if (data.data.meta.status === 201) {
          // 重新加载列表
          this.load();
          // 隐藏添加窗口
          this.addOrgFormVisible = false;
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
    async handleDelete(school) {
      // 删除提示
      this.$confirm('确认要删除该用户么？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const data = await this.$http.delete(`/schools/${school.id}`);
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
    async handleGetOrgInfo(school) {
      // 显示修改用户对话框
      this.editOrgFormVisible = true;
      const res = await this.$http.get(`/schools/${school.id}`);
      this.editFormData = res.data.data;
    },
    // 编辑 - 修改用户信息
    async handleEditOrg() {
      this.editOrgFormVisible = false;
      // 获取用户id
      const id = this.editFormData.id;
      const res = await this.$http.put(`/schools/${id}`, this.editFormData);
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