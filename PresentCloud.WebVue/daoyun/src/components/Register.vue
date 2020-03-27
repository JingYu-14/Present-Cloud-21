<template>
   <div class="main0"> 
    <div class="main_left"> 
     <img src="../assets/images/zhuce-image-3.png" class="theimg" /> 
     <img src="../assets/images/zhuce-image-2.png" class="secimg" /> 
     <img src="../assets/images/zhuce-image-1.png" class="firimg" /> 
    </div> 
    <div class="main_right"> 
     <div class="main_r_up"> 
      <img src="../assets/images/user.png" /> 
      <div class="pp">
       注册
      </div> 
     </div> 
     <div class="sub">
      <p>已经注册？<a href="/#/login"><span class="blue">请登录</span></a></p>
     </div> 
     <div class="txt txt0"> 
      <span style="letter-spacing:8px;">用户名:</span> 
      <input name="" type="text" class="txtphone" placeholder="请输入用户名" /> 
     </div> 
     <div class="txt txt0"> 
      <span style="letter-spacing:4px;">登录密码:</span> 
      <input name="" type="text" class="txtphone" placeholder="请输入密码" /> 
     </div> 
     <div class="txt txt0"> 
      <span style="letter-spacing:4px;">重复密码:</span> 
      <input name="" type="text" class="txtphone" placeholder="请再次输入密码" /> 
     </div> 
     <div class="txt txt0"> 
      <a href="/#/login">
       <div class="zhucebtn">
        确认注册
       </div></a> 
     </div> 
    </div> 
   </div> 
  </div>
</template>

<script>
export default {
  name: 'Register',
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

<style scoped src="../assets/css/zhuce.css">
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
