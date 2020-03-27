<template>
  <div class="main" id="main">
	  <div class="main0">
	     <div class="formBox">
	       <h3>登录密码重置</h3>
	       <div class="itembox itembox_2">
	         <label>登录密码&nbsp;:</label>
	         <input type="text" placeholder="请输入新登录密码"></span>
	       </div>
	       <div class="itembox itembox_2">
	         <label>确认密码&nbsp;:</label>
	         <input type="text" placeholder="请再次输入新密码"></span>
	       </div>
	       <div class="itembox itembox_2">
	         <label>验证码&nbsp;:</label>
	         <input type="text" placeholder="请输入验证码" class="yzm"></span>
	         <div class="yzmbox"><img src="../assets/images/yanzhengma.png" /></div>
	       </div>
	       <div class="btnBox">
	           <button onclick="window.location.href='/#/login'">下一步</button>
	       </div>
	     </div>
	  </div>
	</div>
</template>

<script>
export default {
  name: 'Password',
  data () {
    return {
     
    }
  },
  created(){
  	this.$nextTick(()=>{
  		document.getElementById('main').style.height = document.documentElement.clientHeight + 'px'
  	})
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped src="../assets/css/password.css">
.main{
	position: relative;
}
.main0{
	position: absolute;
    left: 50%;
    top: 50%;
    margin: 0;
    margin-left: -450px;
    margin-top: -250px;
}
</style>
