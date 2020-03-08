<template>
    <el-scrollbar wrap-class="scrollbar-wrapper">
        <div class="header-title" v-if="sidebar.opened" style="height: 49px">
            <h1>
                冰科技管理系统
            </h1>
        </div>
        <el-menu
                :show-timeout="200"
                :default-active="$route.path"
                :collapse="isCollapse"
                mode="vertical"
                background-color="#fff"
                text-color="#666666"
                :unique-opened = "true"
                active-text-color="#ff5157">
            <sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path"/>
       </el-menu>
    </el-scrollbar>
</template>

<script>
    import {mapGetters} from 'vuex'
    import SidebarItem from './SidebarItem'
    import auth from '@/utils/auth' // 验权

    export default {
        components: {SidebarItem},
        computed: {
            ...mapGetters([
                'sidebar'
            ]),
            routes() {
                var menu = auth.getMenus();
                return JSON.parse(menu);
            },
            isCollapse() {
                return !this.sidebar.opened
            }
        },
        mounted() {

        }
    }
</script>
<style >
   .el-menu-item.is-active:before{
    content: ' ';
    position: absolute;
    background: #ff5157;
    width: 5px;
    height: 100%;
    left: 0px;
  }
</style>
