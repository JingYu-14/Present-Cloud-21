<template>
	<div class='content'>
		<div class="header"><span>{{name}}</span></div>
		<div class='center'>
			<ul>
				<template v-for="(item,index) in dataList">
					<li @click="clk(item)" :class="onClk==item.title?'on':''"><router-link :to="item.url">{{item.title}}</router-link></li>
					<!-- <li @click="clk('成绩')" :class="onClk=='成绩'?'on':''"><router-link to="grade">成绩</router-link></li>
					<li @click="clk('签到')" :class="onClk=='签到'?'on':''"><router-link to="sign">签到</router-link></li>
					<li @click="clk('任务')" :class="onClk=='任务'?'on':''"><router-link to="task">任务</router-link></li> -->
				</template>
			</ul>
		</div>
		<div class='footer'>
			<router-view :key="key"></router-view>
		</div>
	</div>
</template>

<script>
export default {
  name:'StudentClass',
  data () {
    return {
     name:'',
     onClk:'同学',
     dataList:[{
     	title:'同学',
     	url:'schoolmate'
     },{
     	title:'签到',
     	url:'sign'
     },{
     	title:'任务',
     	url:'task'
     }]
    }
  },
  computed:{
        key(){
            return this.$route.path + Math.random();
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
         const params = { id: id};
          const data = await this.$http.get('/teacherClasses', {params});
          if (data.data.meta.status === 200) {
            this.name = data.data.data[0].class_name;
          } else {
            this.$message({
              type: 'error',
              message: data.data.meta.msg
            });
          }
          // 一个是router一个是route
        this.$router.push({
          name: 'StudentSchoolMate',
            params:{id:this.$route.params.id},
      });
    },
  	clk(on){
  		this.onClk = on.title
  	}
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.content .header{
	background-repeat: no-repeat;
    background-size: 100% 200px;
    background-image: url(../../assets/images/06.png);
    height: 200px;
    padding: 48px 0 0 40px;
    box-sizing: border-box;
    border-radius: 8px 8px 0 0;
}
.content .header span{
	padding-top: 22px;
    font-size: 36px;
    color: #fff;
    font-weight: 500;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.content .center{
background: white;
border-radius: 0 0 8px 8px;
margin-bottom: 20px;
}
.content .center ul{
margin: 0;
}
.content .center ul li{
    list-style: none;
    display: inline-block;
    font-size: 16px;
    font-weight: 500;
    color: rgba(59, 61, 69, 1);
    padding: 25px;
    cursor: pointer;
}
.content .center ul li a{
text-decoration:none;
color: #818181;
}
.content .center ul li:hover a,.content .center ul li.on a{
	color: #328eeb;
}
.content .center ul li.on{
	border-bottom: 4px solid #2C58AB;
    border-left: 2px solid transparent;
    border-right: 2px solid transparent;
    font-size: 16px;
    font-weight: 500;
    color: rgba(59, 61, 69, 1);
    font-family: PingFangSC-Medium;
    font-weight: 500;
    color: rgba(59, 61, 69, 1);
    border-bottom: 4px solid #328eeb;
}
</style>
