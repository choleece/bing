import service from "@/utils/request";

/**
 * 登录
 * @param userInfo
 */
export function login(userInfo) {
    return service({
        url: window.API.commonSystem.login,
        method: 'post',
        data: userInfo
    });
}

/**
 * 获取登录用户信息
 * @param token
 */
export function getUserInfo(token) {
    return service({
        url: window.API.commonSystem.loginUser,
        method: 'get',
        params: {token}
    })
}

/**
 * 退出
 */
export function logout() {
    return service({
        url: window.API.commonSystem.loginOut,
        method: 'post'
    })
}

/**
 * 获取用户菜单信息
 * @param token
 */
export function getUserMenus(token) {
    return service({
        url: window.API.commonSystem.userOwnResource,
        method: 'post',
        params: {token}
    })
}
