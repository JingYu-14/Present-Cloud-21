<template>
	<div class="content">
		<div class="detail">
			<p>任务详情</p>
			<span>{{detail}}</span>
		</div>
		<div class="upload">
      <form enctype="multipart/form-data" @submit.prevent="submit($event)">
        <input type="file" name="file" class="select"/>  
        <input type="hidden" name='taskId' v-model="taskId">
        <input type="hidden" name='uid' v-model="uid">
        <input type="submit" value="提交" :disabled="state"/>
      </form> 
		</div>
	</div>
</template>

<script>
export default {
  name:'StudentDetail',
	data() {
       return {
        detail:'',
        uid:0,
        taskId:0,
        state:false
      };
    },
    mounted(){
      this.taskId=this.$route.params.taskId;
      this.uid=this.$route.params.uid;
      this.load(localStorage.getItem('id'));

       if(this.$route.params.state==="过期"){
         this.state=true;
       }
    },
    methods: {
      async load(id) {
      // 从本地存储中获取令牌
      const params = {  taskId: this.$route.params.taskId,
                classId: id,
                pagenum: 1, 
                pagesize: 10
             };
      const data = await this.$http.get('/taskDetails', {
        params
      });
      if (data.data.meta.status === 200) {
        this.detail = data.data.data.detail;
      } else {
        this.$message({
          type: 'error',
          message: data.data.meta.msg
        });
      }
    },

      // async submit:function(event)会提示错误
      submit:async function(event) {
        var formData = new FormData(event.target);
        const res = await this.$http.post('/upload', formData);
        // 必须使用async和await，不然接收到的是promise对象不是json对象
        // console.log(res);
        if (res.data.meta.status === 200) {
          this.$message({
            type: 'success',
            message: '上传成功!'
          });
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

<style scoped>
.content{
	background: white;
    padding-top: 1px;
}
.content .detail{
padding: 0 20px;
}
.content .detail p{
font-size: 14px;
    margin: 30px 20px 20px 20px;
    line-height: 14px;
    color: #333;
    border-left: 5px solid #0bd;
    padding-left: 10px;
}
.content .detail span{
    margin: 20px 0px 0px;
    height: auto;
    font-size: 13px;
    line-height: 1.8;
    word-wrap: break-word;
    white-space: pre-wrap;
    padding: 0 20px;
    color: #333;
}
.content .upload{
padding: 20px;
}
.content .btn{
	text-align: center;
    padding: 20px;
}
input[type="file" i] {
  padding-left: 20px;
  }
button .el-button el-button--primary {
  /* Font & Text */
  font-family: Arial;
  font-size: 14px;
  font-style: normal;
  font-variant: normal;
  font-weight: 500;
  letter-spacing: normal;
  line-height: 14px;
  text-decoration: none solid rgb(255, 255, 255);
  text-align: center;
  text-indent: 0px;
  text-transform: none;
  vertical-align: baseline;
  white-space: nowrap;
  word-spacing: 0px;

  /* Color & Background */
  background-attachment: scroll;
  background-color: rgb(102, 177, 255);
  background-image: none;
  background-position: 0% 0%;
  background-repeat: repeat;
  color: rgb(255, 255, 255);

  /* Box */
  height: 39.6px;
  width: 70px;
  border: 1px solid rgb(102, 177, 255);
  border-top: 1px solid rgb(102, 177, 255);
  border-right: 1px solid rgb(102, 177, 255);
  border-bottom: 1px solid rgb(102, 177, 255);
  border-left: 1px solid rgb(102, 177, 255);
  margin: 0px;
  padding: 12px 20px;
  max-height: none;
  min-height: 0px;
  max-width: none;
  min-width: 0px;

  /* Positioning */
  position: static;
  top: auto;
  bottom: auto;
  right: auto;
  left: auto;
  float: none;
  display: inline-block;
  clear: none;
  z-index: auto;

  /* List */
  list-style-image: none;
  list-style-type: disc;
  list-style-position: outside;

  /* Table */
  border-collapse: separate;
  border-spacing: 0px 0px;
  caption-side: top;
  empty-cells: show;
  table-layout: auto;

  /* Miscellaneous */
  overflow: visible;
  cursor: pointer;
  visibility: visible;

  /* Effects */
  transform: none;
  transition: all 0.1s ease 0s;
  outline: rgb(255, 230, 230) dashed 0px;
  outline-offset: 0px;
  box-sizing: border-box;
  resize: none;
  text-shadow: none;
  text-overflow: clip;
  word-wrap: normal;
  box-shadow: none;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
}
</style>
