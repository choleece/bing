import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: resolve => require(['../views/common/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children:[
                {
                    path: '/dashboard',
                    component: resolve => require(['../views/Dashboard.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/icon',
                    component: resolve => require(['../views/Icon.vue'], resolve),
                    meta: { title: '自定义图标' }
                },
                {
                    path: '/table',
                    component: resolve => require(['../views/BaseTable.vue'], resolve),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: resolve => require(['../views/Tabs.vue'], resolve),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/form',
                    component: resolve => require(['../views/BaseForm.vue'], resolve),
                    meta: { title: '基本表单' }
                },
                {
                    // 富文本编辑器组件
                    path: '/editor',
                    component: resolve => require(['../views/VueEditor.vue'], resolve),
                    meta: { title: '富文本编辑器' }
                },
                {
                    // markdown组件
                    path: '/markdown',
                    component: resolve => require(['../views/Markdown.vue'], resolve),
                    meta: { title: 'markdown编辑器' }
                },
                {
                    // 图片上传组件
                    path: '/upload',
                    component: resolve => require(['../views/Upload.vue'], resolve),
                    meta: { title: '文件上传' }
                },
                {
                    // vue-schart组件
                    path: '/charts',
                    component: resolve => require(['../views/BaseCharts.vue'], resolve),
                    meta: { title: 'schart图表' }
                },
                {
                    // 拖拽列表组件
                    path: '/drag',
                    component: resolve => require(['../views/DragList.vue'], resolve),
                    meta: { title: '拖拽列表' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: resolve => require(['../views/Permission.vue'], resolve),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: resolve => require(['../views/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../views/403.vue'], resolve),
                    meta: { title: '403' }
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../views/Login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
