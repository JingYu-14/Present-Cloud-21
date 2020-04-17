<template>
  <div>
    <div class="detail" style="padding-bottom:35px;">
      <p>任务详情</p>
      <span>{{detail}}</span>
    </div>
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
        prop="state"
        label="状态">
        <template slot-scope="scope">
          <span v-if='scope.row.state' style="color:#67C23A">已提交</span>
          <span v-else style="color:#F56C6C">未完成</span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total"> 
   </el-pagination> 
  </div>
</template>

<script>
export default {
  name: 'StudentTaskDetails',
  data () {
    return {
      tableData: [],
      pagesize: 10,
      currentPage: 1,
      total: 0,
      detail:'',
    }
  },
  mounted(){
    this.load(localStorage.getItem('id'));
  },
  methods:{
    async load(id) {
      // 从本地存储中获取令牌
      const params = {  taskId: this.$route.params.taskId,
      					classId: id,
				      	pagenum: this.currentPage, 
				      	pagesize: this.pagesize
				     };
      const data = await this.$http.get('/taskDetails', {
        params
      });
      if (data.data.meta.status === 200) {
        // 表格数据
        this.tableData = data.data.data.taskDetails;
        this.total = data.data.data.total;
        this.detail = data.data.data.detail;
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
  .detail{
      padding: 0 20px;
      background: white;
      padding-top: 1px;
  }
  .detail p{
    font-size: 14px;
    margin: 30px 20px 20px 20px;
    line-height: 14px;
    color: #333;
    border-left: 5px solid #0bd;
    padding-left: 10px;
  }
  .detail span{
      margin: 20px 0px 0px;
      height: auto;
      font-size: 13px;
      line-height: 1.8;
      word-wrap: break-word;
      white-space: pre-wrap;
      padding: 0 20px;
      color: #333;
  }
</style>
