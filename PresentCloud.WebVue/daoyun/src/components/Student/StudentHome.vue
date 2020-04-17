<template>
  <el-container>
    <el-header>
      <el-row>
        <el-col :span="4"><div class="grid-content bg-purple">
          <img id='img' style="width: 45px;
    height: 45px;
    border-radius: 45px;
    vertical-align: middle;
    margin-right: 5px;" :src="avatar" alt=""/><span style="font-size:20px;font-weight:bold;">{{name}}</span>
        </div></el-col>
        <el-col :span="16"><div class="grid-content bg-purple">
          <div class="title">到云后台管理系统</div>
        </div></el-col>
        <el-col :span="4"><div class="grid-content bg-purple">
          <a href="#" @click.prevent="logout" class="logout-btn">退出</a>
        </div></el-col>
      </el-row>
    </el-header>
    <el-container>
      
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>

  </el-container>
</template>

<script>
export default {
  name: 'StudentHome',
  data() {
      return {
        roles: [],
        name:'',
        avatar:''
      };
  },
  mounted(){
    if(this.$route.params.name){
      this.name=this.$route.params.name;
      localStorage.setItem('name', this.name);
    }else{
      this.name=localStorage.getItem('name');
    }

    if(this.$route.params.avatar){
      this.avatar="http://localhost/daoyunapi/public/static/img/"+this.$route.params.avatar;
      localStorage.setItem('avatar', this.avatar);
    }else{
      this.avatar=localStorage.getItem('avatar');
    }
     this.$router.push({
          name: 'StudentClasses',
      });
  },
  methods: {
      logout() {
        this.$confirm('是否要退出系统？', '退出提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }).then(() => {
          this.$router.push({
            name: 'Login'
          });
          this.$message({
            type: 'success',
            message: '退出成功!'
          });
        }).catch(() => {
        });
      },
      
    }
  };
</script>

<style scoped>
.el-aside{
  width: 200px !important;
}
  .el-header{
    background-color: #B3C0D1;
    padding: 0;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-header .title {
    color: #fff;
    font-size: 24px;
    height: 100%;
    line-height: 60px;
  }

  .logout-btn {
    position: absolute;
    right: 10px;
    color: orange;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    padding-top: 0;
  }
  .el-container {
    height: 100%;
  }

  /* 侧边导航 */
  .el-menu {
    width: 200px;
    height: 100%;
  }
</style>
