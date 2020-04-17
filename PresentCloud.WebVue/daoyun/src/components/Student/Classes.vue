<template>
	<div class="template">
    <div style="display: flex;flex-wrap: wrap;"> 
		<div class='content' v-for="item in classesData">
			<div class='header'>
				<!-- <div class='xue'>{{item.xue}}</div> -->
				<router-link :to="{name:'StudentClass',params: {id:item.id}}">{{item.class_name}}</router-link>
				<!-- <a href="./class"></a> -->
				<div class='code'>邀请码:{{item.invitation_code}}</div>
			</div>
			<div class='center'>
				<span>近期作业</span>
				<div>{{item.task}}</div>
			</div>
			<div class='footer'>
				<div class='name'>
					<img src="../../assets/images/05.png" alt="">
					{{item.name}}
				</div>
				<!-- <div class='del' @click="handleDeleteClass(item.id)">删除</div> -->
			</div>
		</div>

   <div class='content'>
      <div class='header'></div>
      <div class='center' style="text-align:center;border:none;">
          <p style="font-size: 28px; margin: 36px 0 0 0;cursor:pointer;" @click="addClassFormVisible = true">+</p>
          <p style="font-size: 14px; font-family: PingFangSC-Medium; font-weight: 500; color: rgba(31,32,35,1); line-height: 24px;margin:0;cursor:pointer;" @click="addClassFormVisible = true">加入班级</p>
      </div>
      <div class='footer' style="display:none;">
          <div class='name'></div>
      </div>
  </div>
  </div>


		<!-- 加入班级 --> 
	   <el-dialog title="加入班级" :visible.sync="addClassFormVisible"> 
	    <el-form label-position="right" label-width="100px"  :model="addFormData"> 
	     <el-form-item label="邀请码"> 
	      <el-input v-model="addFormData.code" auto-complete="off"></el-input> 
	     </el-form-item> 
	     </el-form> 
	    <div slot="footer" class="dialog-footer"> 
	     <el-button @click="addClassFormVisible = false">
	      取 消
	     </el-button> 
	     <el-button type="primary" @click="handleAddClass">
	      确 定
	     </el-button> 
	    </div> 
	   </el-dialog> 
	</div>
</template>

<script>
export default {
	name:'StudentClasses',
  data () {
    return {
     classesData:[],
     addClassFormVisible: false,
     addFormData: {
        code: '',
      	uid:''
      },
    }
  },
   mounted() {
    this.load(localStorage.getItem('uid'));
  },
  methods: {
  	 async load(uid) {
   		const params = { uid: uid};
  	      const data = await this.$http.get('/studentClasses',{params});
  	      if (data.data.meta.status === 200) {
  	        this.classesData = data.data.data;
  	      } else {
  	        this.$message({
  	          type: 'error',
  	          message: data.data.meta.msg
  	        });
  	      }
  	 },
  	 async handleAddClass() {
      	this.addFormData.uid=localStorage.getItem('uid');
        // 表单提交
        const data = await this.$http.post('/studentClasses', this.addFormData);
        if (data.data.meta.status === 201) {
          this.load(localStorage.getItem('uid'));
          this.addClassFormVisible = false;
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
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>

	.content{
		width: 270px;
		background: #fff;
		border-radius: 8px;
		margin: 22px;
	}
	.content .header{
		    background-image: url(/static/img/05.42ef092.png);
    background-size: 270px 152px;
    height: 152px;
    position: relative;
    padding-top: 18px;
    box-sizing: border-box;
    border-radius: 8px 8px 0 0;
	}
	.content .header .xue{
		position: absolute;
    right: 20px;
    top: 0;
    background: #32BAF0;
    color: white;
    padding: 1px 3px;
	}
	.content .header a{
		    text-decoration: none;
    font-size: 20px;
    font-weight: lighter;
    color: #fff;
    height: 62px;
    margin-left: 18px;
    margin-right: 48px;
    display: block;
	}
	.content .header a:hover{
		text-decoration: underline;
		cursor: pointer;
	}
	.content .header .code{
		height: 24px;
    background: rgba(255,255,255,.2);
    border-radius: 2px;
    font-size: 16px;
    font-family: PingFang-SC-Medium;
    font-weight: 500;
    color: rgba(255,255,255,1);
    line-height: 24px;
    text-align: center;
    margin-left: 18px;
    padding-left: 2px;
    padding-right: 4px;
    margin-top: 13px;
    display: inline-block;
	}
	.content .center{
		margin-top: 5px;
    padding: 0 16px;
    height: 105px;
    border-bottom: 1px solid rgba(226,230,237,1);
	}
	.content .center span{
		height: 30px;
    line-height: 30px;
    width: 100%;
    overflow: hidden;
    font-size: 14px;
	}
	.content .center div{
		height: 30px;
    line-height: 30px;
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
	}
	.content .footer{
		display: flex;
    padding: 8px 18px;
	}
	.content .footer .name{
		    width: 196px;
    color: rgba(95, 99, 104, 1);
	}
	.content .footer .name img{
		width: 20px;
    height: 20px;
    border-radius: 20px;
    vertical-align: bottom;
    margin-right: 5px;
	}
	.content .footer .del{
		color: rgba(44,88,171,1);
	}
	.content .footer .del:hover{
		cursor: pointer;
	}
</style>
