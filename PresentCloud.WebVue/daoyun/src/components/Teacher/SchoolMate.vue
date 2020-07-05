<template>
  <div>
    <el-row style=" padding-bottom: 15px;"> 
    <el-col :span="24"> 
     <div class="search"> 
      <el-input v-model="searchValue" placeholder="请输入内容" clearable=""> 
       <el-button @click="handleSearch" slot="append" icon="el-icon-search"></el-button> 
      </el-input> 
      <el-button type="success" @click="addStudentFormVisible = true" plain="">
       添加学生
      </el-button> 
     </div> 
    </el-col> 
   </el-row> 
	  <el-table :data="tableData" style="width: 100%">
      <el-table-column type="index" width="260"> 
      </el-table-column> 
      <el-table-column
        prop="account"
        label="账号"
        width="260">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="260">
      </el-table-column>
      <el-table-column
        prop="exp"
        label="经验">
      </el-table-column>
      <el-table-column label="操作" width="200"> 
     <template slot-scope="scope"> 
      <el-button type="danger" @click="handleDelete(scope.row)" size="mini" icon="el-icon-delete" plain=""></el-button> 
     </template> 
    </el-table-column> 
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total"> 
   </el-pagination> 

   <!-- 添加学生 --> 
     <el-dialog title="添加学生" :visible.sync="addStudentFormVisible"> 
      <el-form label-position="right" label-width="100px"  :model="addFormData"> 
       <el-form-item label="账号"> 
        <el-input v-model="addFormData.account" auto-complete="off"></el-input> 
       </el-form-item> 
       </el-form> 
      <div slot="footer" class="dialog-footer"> 
       <el-button @click="addStudentFormVisible = false">
        取 消
       </el-button> 
       <el-button type="primary" @click="handleAddStudent">
        确 定
       </el-button> 
      </div> 
     </el-dialog> 
  </div>
</template>

<script>
export default {
  name: 'TeacherSchoolMate',
  data () {
    return {
      tableData: [],
      pagesize: 10,
      currentPage: 1,
      total: 0,
      searchValue:'',
      addStudentFormVisible: false,
       addFormData: {
          account: '',
          id:'',
        },
    }
  },
  mounted(){
    if(this.$route.params.id){
      localStorage.setItem('id', this.$route.params.id);
      this.load(this.$route.params.id);
    }else{
      this.load(localStorage.getItem('id'));
    }
  },
  methods:{
    async load(id) {
      // 从本地存储中获取令牌
      const params = { id: id,pagenum: this.currentPage, pagesize: this.pagesize};
      const data = await this.$http.get('/students', {
        params
      });
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.students;
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
      this.load(localStorage.getItem('id'));
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.load(localStorage.getItem('id'));
    },
   
     async handleSearch() {
      const params = {
        id:localStorage.getItem('id'),
        pagenum: this.currentPage,
        pagesize: this.pagesize,
        query: this.searchValue
      };
      const data = await this.$http.get('/teacherStudents', {
        params
      });
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.students;
        // 总数据条数
        this.total = data.data.data.total;
      } else {
        this.$message({
          type: 'error',
          message: data.data.meta.msg
        });
      }
    },
    async handleAddStudent() {
        this.addFormData.id=localStorage.getItem('id');
        // 表单提交
        const data = await this.$http.post('/teacherStudents', this.addFormData);
        if (data.data.meta.status === 201) {
          this.load(localStorage.getItem('id'));
          this.addStudentFormVisible = false;
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
     
    },
    async handleDelete(user) {
      // 删除提示
      this.$confirm('确认要删除该用户么？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const params={uid:user.id,id:localStorage.getItem('id')};
        const data = await this.$http.delete('/teacherStudents',{params});
        if (data.data.meta.status === 200) {
          this.load(localStorage.getItem('id'));
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
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.search .el-input {
  width: 300px;
}
</style>
