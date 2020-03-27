<template>
  <div class="main" id="main">
	  <div class="main0" >
	     <div class="main_left">
	        <img src="../assets/images/login-image-3.png" class="theimg"/>
	        <img src="../assets/images/login-image-2.png" class="secimg"/>
	        <img src="../assets/images/login-image-1.png" class="firimg"/>
	     </div>
	     <div class="main_right">
	        <div class="main_r_up">
	            <img src="../assets/images/user.png" />
	            <div class="pp">登录</div>
	        </div>
	        <div class="sub"><p>还没有账号？<a href="/#/register"><span class="blue">立即注册</span></a></p></div>
	        <div class="txt">
	            <span style="letter-spacing:8px;">用户名:</span>
	            <input name="" type="text" class="txtphone" placeholder="请输入注册手机号或用户名"/>
	        </div>
	        <div class="txt">
	            <span style="letter-spacing:4px;">登录密码:</span>
	            <input name="" type="text" class="txtphone" placeholder="请输入登录密码"/>
	        </div>
	        <div class="txt">
	            <span style=" float:left;letter-spacing:8px;">验证码:</span>
	            <input name="" type="text" class="txtyzm" placeholder="请输入页面验证码"/>
	            <img src="../assets/images/yanzhengma.png" class="yzmimg"/>
	        </div>
	        <div class="xieyi">
	            <input name="" type="checkbox" value="" checked="checked"/>
	            记住我 <a href="/#/password"><span class="blue" style=" padding-left:130px; cursor:pointer">忘记密码?</span></a>
	        </div>
	        <div class="xiayibu">登录</div>
	     </div>
	  </div>
	</div>
</template>

<script>
export default {
  name: 'Login',
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

<style scoped src="../assets/css/login.css">
.main{
	position: relative;
}
.main0{
	position: absolute;
    left: 50%;
    top: 50%;
    margin: 0;
    margin-left: -600px;
    margin-top: -270px;
}
</style>
