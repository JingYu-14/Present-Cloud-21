<template>
  <div>
	<div @click="toPage(item.id)" class="content" v-for='item in taskData'>
    <div class="left"><img src="../../assets/images/07.png" alt=""></div>
    <div class="right">
      <div class="title"><span>{{item.state}}</span>{{item.name}}</div>
      <!-- <div class="cont">共30组参与 | {{item.deadline}} | <span>已参与</span> <span>10经验</span> | <span>超时</span></div> -->
      <div class="cont">共30组参与 | {{item.deadline}} |  <span>10经验</span> </div>
    </div>
    <div @click.stop="del(item.id)" style="float: right;margin: 10px 40px;"><el-button type="danger" icon="el-icon-delete" circle></el-button></div>
  </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      taskData: [],
    }
  },
  mounted(){
    this.load(localStorage.getItem('id'));
  },
  methods:{
    async load(id) {
      // 从本地存储中获取令牌
      const params = { id: id};
      const data = await this.$http.get('/tasks', {
        params
      });
      if (data.data.meta.status === 200) {
        this.taskData = data.data.data;
      } else {
        this.$message({
          type: 'error',
          message: data.data.meta.msg
        });
      }
    },
    toPage(id){
      this.$router.push({
            name: 'TaskDetails',
            params: {'taskId': id}
          });
    },
    async del(id){
       const params = { id: id};
        const data = await this.$http.put('/tasks', params);
        if (data.data.meta.status === 200) {
          this.$message({
            type: 'success',
            message: data.data.meta.msg
          });
          this.load(localStorage.getItem('id'));
        } else {
          this.$message({
            type: 'error',
            message: data.data.meta.msg
          });
        }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.content{
font-size: 0;
    padding: 20px;
    background: white;
        margin-bottom: 10px;
        border-radius: 8px;
}
.content:hover{
cursor: pointer;
border: 1px solid #0BD;
}
.content .left{
width: 60px;
    height: 60px;
    display: inline-block;
    vertical-align: top;
    margin-right: 10px;
}
.content .left img{
width: 100%;
    height: 100%;
    border-radius: 8px;
}
.content .right{
display: inline-block;
    font-size: 16px;
}
.content .right .title{
margin-bottom: 20px;
}
.content .right .title span{
display: inline-block;
    width: 50px;
    height: 18px;
    line-height: 18px;
    text-align: center;
    border-radius: 4px;
    margin-right: 6px;
    vertical-align: middle;
    color: #0BD;
    border: 1px solid #0BD;
}
.content .right .cont{
  font-size: 12px;
}
</style>
