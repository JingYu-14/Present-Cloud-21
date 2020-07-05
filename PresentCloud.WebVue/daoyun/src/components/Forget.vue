<template>
  <div class="forget-wrap">
    <el-form
      label-position="top"
      :rules="rules"
      ref="ruleForm"
      :model="formData"
      label-width="80px">
      <h2>忘记密码</h2>
      <el-form-item label="账号" prop="account">
        <el-input v-model="formData.account"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input type="password" v-model="formData.password"></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input v-model="formData.mobile"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class="forget-btn" type="primary" @click="forget">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Forget',
  data() {
    // var validatorPhone = function (rule, value, callback) {
    //     if (value === '') {
    //       callback(new Error('手机号不能为空'));
    //     } else if (!/^1\d{10}$/.test(value)) {
    //       callback(new Error('手机号格式错误'));
    //     } 
    //   };
    return {
      content:'发送验证码',
      isDisable: false,
      formData: {
        account: '',
        password: '',
        mobile:'',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, max: 12, message: '长度必须是5到12个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 11, message: '长度在 6 到 11 个字符', trigger: 'blur' }
        ],
        mobile: [
          { required: true, /*validator: validatorPhone,*/ trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    async forget() {
      // 表单验证
      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) {
          return;
        }
        const res = await this.$http.post('/forget', this.formData);
        const data = res.data;
        // console.log(res);
        if (data.meta.status === 200) {
          this.$message({
            type: 'success',
            message: '重置密码成功!'
          });
          this.$router.push({
            name:'Login',
          });
        } else {
          // 登录失败，返回失败的原因
          this.$message({
            type: 'error',
            message: data.meta.msg
          });
        }
      });
    }
  }
};
</script>

<style scoped>
  .forget-wrap{
    background-image: url('../assets/images/login.jpg');
    background-repeat: no-repeat;
    background-size: 100% 100%;
  }
  .forget-wrap {
    background-color: #324152;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .el-form.el-form--label-top {
    padding: 40px;
    width: 500px;
    border-radius: 5px;
    background-color: #fff;
  }
  .el-form .forget-btn {
    width: 100%;
  }
</style>
