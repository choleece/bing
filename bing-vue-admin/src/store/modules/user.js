import {login, logout, getUserInfo, getUserMenus} from '@/api/login'
import auth from '@/utils/auth'
import { listToTree } from "@/utils";

const user = {
    state: {
        token: auth.getToken(),
        name: '',
        avatar: '',
        roles: [],
        menus: []
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token;
        },
        SET_NAME: (state, name) => {
            state.name = name;
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar;
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles;
        },
        SET_MENUS: (state, menus) => {
            state.menus = menus;
        }
    },

    actions: {
        Login({commit}, userInfo) { // 登录
            return new Promise((resolve, reject) => {
                login(userInfo).then(res => {
                    if(res.code != 0) {
                        reject(res);
                        return;
                    }
                    //成功后设置值
                    if(res.code==0) {
                        auth.setToken(res.data);
                        commit('SET_TOKEN', res.data);
                    }
                    resolve(res);
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 登出
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(() => {
                    commit('SET_TOKEN', '');
                    commit('SET_ROLES', []);
                    commit('SET_MENUS', []);
                    commit('SET_NAME', '');
                    auth.removeToken();
                    auth.removeMenus();
                    auth.removeUser();
                    resolve();
                }).catch(error => {
                    reject(error)
                })
            })
        },
        //获取用户信息
       GetUserInfo({commit}) {
           return new Promise((resolve, reject) => {
               getUserInfo(auth.getToken()).then(res => {
                   let data = res.data;
                   commit('SET_NAME', data.userName);
                   auth.setUser(JSON.stringify(data));
                   resolve(data);
               }).catch(error => {
                   reject(error);
               });
           })
       },
        //获取用户菜单
        GetUserMenus({commit}) {
            return new Promise((resolve, reject) => {
                getUserMenus(auth.getToken()).then(response => {
                    let data = response.data;
                    console.log(data);
                    let menus = [];
                    for(let i=0; i < data.length;i++) {
                        let item = data[i];
                        let menu={};
                        menu.path = "/" + item.identify;
                        menu.component = item.url;
                        menu.name = item.identify;
                        menu.meta = {title: item.name, icon: item.icon};
                        menu.id = item.id;
                        menu.parentId = item.parentId;
                        menus.push(menu);
                    }
                    menus = listToTree(menus, "id", "parentId");
                    console.log(menus);
                    let  cache = [];
                    let menuStr = JSON.stringify(menus,function (key,value) {
                        if (typeof value === 'object' && value !== null) {
                            if (cache.indexOf(value) !== -1) {
                                // Circular reference found, discard key
                                return;
                            }
                            // Store value in our collection
                            cache.push(value);
                        }
                        return value;
                    });
                    auth.setMenus(menuStr);
                    commit('SET_MENUS', menus);
                    resolve(data);
                }).catch(error => {
                    reject(error);
                });
            })
        },
        // 前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '');
                auth.removeToken();
                commit('SET_MENUS', []);
                auth.removeMenus();
                resolve();
            })
        }
    }
};

export default user
