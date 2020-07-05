import axios from 'axios';

const httpHelper = {};
// 配置Vue插件
httpHelper.install = function fn(Vue) {
  // axios.defaults.baseURL = 'http://127.0.0.1/daoyunapi/public/index.php';
  axios.defaults.baseURL = 'http://129.211.87.192/daoyunapi/public/index.php';
  // axios 拦截器
  // 当不是登录的时候添加authorization
  axios.interceptors.request.use(function (config) {
    if (config.url !== '/Login'|| config.url !== '/Register' || config.url !== '/Forget') {
      config.headers.Authorization = localStorage.getItem('token');
    }
    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });

  Vue.prototype.$http = axios;
};

export default httpHelper;
