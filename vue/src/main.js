import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import dayjs from 'dayjs'

// step1：引入 axios
import axios from "axios";
Vue.config.productionTip = false;

// step2：把axios挂载到vue的原型中，在vue中每个组件都可以使用axios发送请求,
// 不需要每次都 import一下 axios了，直接使用 $axios 即可
Vue.prototype.$axios=axios;
Vue.prototype.dayjs = dayjs
// step3：使每次请求都会带一个 /api 前缀
axios.defaults.withCredentials = true;

Vue.use(ElementUI,{size:"small"});
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
