// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/css/style.css';
import http from '@/assets/js/http';
import MyBreadcrumb from '@/components/Brandcrumb';

Vue.config.productionTip = false
Vue.use(ElementUI);
// 在assets文件夹下的http.js中封装的axios的插件
Vue.use(http);
Vue.component(MyBreadcrumb.name, MyBreadcrumb);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
