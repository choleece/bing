import axios from 'axios'
import {Message} from 'element-ui'
import store from '../store'
import auth from './auth'
import router from '../router'

// 创建axios实例
const service = axios.create({
    withCredentials: true,
    timeout: 30000 // 请求超时时间
});

// request拦截器
service.interceptors.request.use(
    config => {
        if (store.getters.token) {
            // 让每个请求携带自定义token 请根据实际情况自行修改
            config.headers['t'] = auth.getToken()
        }
        return config
    },
    error => {
        // Do something with request error
        Promise.reject(error)
    }
);

// response 拦截器
service.interceptors.response.use(
    response => {
        /**
         * code为非0是抛错 可结合自己业务进行修改
         */
        const res = JSON.parse(response.data);
        if (res.code != 0) {
            // 100未授权访问;
            if (res.code === 403) {
                Message({
                    message: res.msg || "未授权访问",
                    type: 'warning',
                    duration: 5 * 1000
                });
                // 移出token
                //auth.removeToken();
                //router.push({path: "/login?redirect="+router.currentRoute.path});
            } else if(res.code === 401 || res.code === 101){ //登录超时
                Message({
                    message: "登录超时",
                    type: 'warning',
                    duration: 5 * 1000
                });
                // 移出token
                auth.removeToken();
                router.push({path: "/login?redirect=" + router.currentRoute.path});
            }
            return res;
        } else {
            return res;
        }
    },
    error => {
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        });
        return Promise.reject(error)
    }
);

service.postForm = (url,params)=> {
    let ret = '?';
    for (let it in params) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(params[it]) + '&'
    }
    return service.post(url+ret,params, {headers:  {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}});
};

export const get = (url, params) => {
    return service({
        url,
        method: 'get',
        params
    });
};

export const post = (url, data) => {
    return service({
        url,
        method: 'post',
        data
    });
};

export default service
