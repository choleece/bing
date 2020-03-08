//import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token';
const MenusKey = 'menus-key';
const UserKey='user-key';
const auth = {
    getToken: function () {
        return sessionStorage.getItem(TokenKey)
    },

    setToken: function (token) {
        return sessionStorage.setItem(TokenKey, token)
    },

    removeToken: function () {
        return sessionStorage.removeItem(TokenKey)
    },
    getMenus: function getMenus() {
        return sessionStorage.getItem(MenusKey)
    },

    setMenus: function setMenus(menus) {

        return sessionStorage.setItem(MenusKey, menus)
    },

    removeMenus: function removeMenus() {
        return sessionStorage.removeItem(MenusKey)
    },
    getUser: function getUser() {
        return sessionStorage.getItem(UserKey)
    },

    setUser: function setUser(user) {

        return sessionStorage.setItem(UserKey, user)
    },

    removeUser: function removeUser() {
        return sessionStorage.removeItem(UserKey)
    }
};
export default auth;


