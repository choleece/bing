import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login/index.vue'
import Layout from '@/views/layout/Layout'

Vue.use(Router)
const router = new Router({
    routes: [
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/',
            component: Layout,
            redirect: '/index',
            name: '首页',
            hidden: true,
            children: [
                {
                    path: 'index',
                    name:"首页",
                    component: () => import('@/views/busi/dashboard/index'),
                    meta: {title: '首页', icon: 'example'}
                }
            ]
        }
    ]
});
export default router;
