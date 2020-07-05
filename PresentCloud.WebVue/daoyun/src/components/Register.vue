<template>
  <div class="register-wrap">
    <el-form
      label-position="top"
      :rules="rules"
      ref="ruleForm"
      :model="formData"
      label-width="80px">
      <h2>用户注册</h2>
      <el-form-item label="账号" prop="account">
        <el-input v-model="formData.account"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="formData.password"></el-input>
      </el-form-item>
       <el-form-item
        prop="email"
        label="邮箱"
        :rules="[
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]"
      >
        <el-input v-model="formData.email"></el-input>
      </el-form-item>
      <el-form-item label="名字" prop="name">
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input v-model="formData.mobile"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="value" placeholder="角色选择" style="width: 100%;">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button class="register-btn" type="primary" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    /*var validatorPhone = function (rule, value, callback) {
        if (value === '') {
          callback(new Error('手机号不能为空'));
        } else if (!/^1\d{10}$/.test(value)) {
          callback(new Error('手机号格式错误'));
        } 
      };*/
    return {
      value: '',
      options: [{
          value: '0',
          label: '管理员'
        }, {
          value: '2',
          label: '教师'
        }, {
          value: '1',
          label: '学生'
        }],
      formData: {
        account: '',
        password: '',
        email:'',
        name:'',
        type:1,
        mobile:''
      },
      rules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 5, max: 12, message: '长度必须是5到12个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 11, message: '长度在 6 到 11 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 8, message: '长度必须是2到8个字符', trigger: 'blur' }
        ],
        mobile: [
          { required: true, /*validator: validatorPhone,*/ trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    async register() {
      this.formData.type=this.value
      // 表单验证
      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) {
          return;
        }
        const res = await this.$http.post('/users', this.formData);
        const data = res.data;
        // console.log(res);
        if (data.meta.status === 201) {
          this.$message({
            type: 'success',
            message: '注册成功!'
          });
          this.$router.push({
            name: 'Login'
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
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
  .register-wrap{
    background-image: url('../assets/images/login.jpg');
    background-repeat: no-repeat;
    background-size: 100% 100%;
  }
  .register-wrap {
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
  .el-form .register-btn {
    width: 100%;
  }
</style>
