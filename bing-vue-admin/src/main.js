import Vue from 'vue'

import 'normalize.css/normalize.css'

import ElementUI from 'element-ui'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'

import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n
import axios from 'axios'
import '@/styles/index.scss' // global css
import App from './App.vue'
import router from './router'
import store from './store'
import './element-variables.scss'

import '@/utils/request'
import '@/icons' // icon
import '@/permission'
import { get, post } from "./utils/request"; // permission control
import API from './common/api'

import Print from 'vue-print-nb'

Vue.use(Print);

Vue.use(ElementUI, { locale })
Vue.use(Viewer);

Vue.config.productionTip = true;

let startApp = function () {
  axios.get('static/config.json').then((res) => {
    Vue.prototype.POSTS = window.POSTS = res.data;
    Vue.prototype.API = window.API = API.API(res.data);
    Vue.prototype.$get = get;
    Vue.prototype.$post = post;
    Vue.prototype.loadingTxt = "努力加载中...";
    Vue.prototype.TITLE = "冰科技后台管理平台";
    Vue.prototype.defaultPageSize = 10;
    return new Vue({
      router,
      store,
      render: h => h(App)
    }).$mount('#app');
  })
}
export default startApp();
