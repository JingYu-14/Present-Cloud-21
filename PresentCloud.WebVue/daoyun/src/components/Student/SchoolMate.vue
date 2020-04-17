<template>
  <div>
	  <el-table :data="tableData" style="width: 100%">
      <el-table-column type="index" > 
      </el-table-column> 
      <el-table-column
        prop="account"
        label="账号"
       >
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        >
      </el-table-column>
       <el-table-column
        prop="exp"
        label="经验">
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total"> 
   </el-pagination> 

  </div>
</template>

<script>
export default {
  name: 'StudentSchoolMate',
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
   
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.search .el-input {
  width: 300px;
}
</style>
