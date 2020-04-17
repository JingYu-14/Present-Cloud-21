<template>
	 <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column type="index" width="180"> 
      </el-table-column> 
      <el-table-column
        prop="account"
        label="账号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="sname"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="date"
        label="签到日期">
      </el-table-column>
      <el-table-column
        prop="time"
        label="签到时间">
      </el-table-column> 
      <el-table-column label="签到情况" prop='state'>
        <template slot-scope="scope">
          <i v-if='scope.row.state' class="el-icon-check"></i>
          <i v-else class="el-icon-close"></i>
        </template>
        
      </el-table-column> 
      <el-table-column label="补签" width="260"> 
       <template slot-scope="scope"> 
        <el-switch @change="(val) =&gt; {
              handleChange(val, scope.row.id)
            }" v-model="scope.row.state" active-color="#13ce66" inactive-color="#ff4949"> 
        </el-switch> 
       </template> 
      </el-table-column> 
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total"> 
   </el-pagination> 
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      pagesize: 10,
      currentPage: 1,
      total: 0,
    }
  },
  mounted(){
    this.load(localStorage.getItem('id'));
  },
  methods:{
    async load(id) {
      // 从本地存储中获取令牌
      const params = { id: id,pagenum: this.currentPage, pagesize: this.pagesize};
      const data = await this.$http.get('/signs', {
        params
      });
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.signs;
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
     // 用户状态改变
    async handleChange(val, id) {
      const params = { id: id, state: val };
      const data = await this.$http.put('/signs', params);
      // const data = await this.$http.put(`/users/${id}/state/${val}`);
      if (data.data.meta.status === 200) {
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
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.el-icon-check{
  color: #67C23A;
  font-size: 30px;
  font-weight: bolder;
}
.el-icon-close{
  color: #F56C6C;
  font-size: 30px;
  font-weight: bolder;
}
</style>
