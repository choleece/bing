import store from "@/store";
import router from "@/router";
import Layout from '@/views/layout/Layout'
import Catalog from '@/views/layout/Catalog'
const _import = require('@/router/_import_route.js')//获取组件的方法
import auth from './auth' // 验权

export  function setMenus() {  //设置路由菜单
    let menus = auth.getMenus();
    if (menus) {
        try{
            menus = JSON.parse(menus);
            filterAsyncRouter(menus);
            store.state.user.menus = menus;
            router.addRoutes(menus);
        } catch (e) {
            console.log("加载菜单异常"+e);
        }
    }
}

export function filterAsyncRouter(menus) { //遍历后台传来的路由字符串，转换为组件对象
    const accessedRouters = menus.filter(route => {
        if (route.component) {
            if (route.component === 'Layout') {//Layout组件特殊处理
                route.component = Layout
            } else if(route.component === 'Catalog') {
                route.component = Catalog;
            } else {
                route.component = _import(route.component);
            }
        }
        if (route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children)
        }
        return true
    });

    return accessedRouters
}
