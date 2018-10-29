import axios from 'axios';
import { Message } from 'element-ui';
import router from '../router/index';

import config from '../cosntant/config';
import { clearLoginInfo, local_data } from "./util";

const codeMessage = {
    200: '服务器成功返回请求的数据。',
    201: '新建或修改数据成功。',
    202: '一个请求已经进入后台排队（异步任务）。',
    204: '删除数据成功。',
    400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
    401: '用户没有权限（令牌、用户名、密码错误）。',
    403: '用户得到授权，但是访问是被禁止的。',
    404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
    406: '请求的格式不可得。',
    410: '请求的资源被永久删除，且不会再得到的。',
    422: '当创建一个对象时，发生一个验证错误。',
    500: '服务器发生错误，请检查服务器。',
    502: '网关错误。',
    503: '服务不可用，服务器暂时过载或维护。',
    504: '网关超时。',
};

/**
 * 获取http的请求头
 * @returns {{Content-Type: string}}
 */
const getHeader = () => {
    return {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorized': JSON.stringify(local_data.get(config.token_name))
    };
};

axios.defaults.baseURL = config.base_url;
axios.defaults.headers = getHeader();
axios.defaults.timeout = 1000 * 30;

/**
 * 检查服务器状态码
 * @param response
 * @returns {*}
 */
const checkStatus = response => {
    if (response.status >= 200 && response.status < 300) {
        return response;
    }
    const errorText = codeMessage[response.status] || response.statusText;
    Message.error(errorText);
    const error = new Error(errorText);
    error.name = response.status;
    error.response = response;
    throw error;
};

const request = (method, api, params) => {
    return new Promise((resolve, reject) => {
        axios({
            method: method,
            url: api,
            data: params,
            transformRequest: [function (data) {
                let ret = '';
                for (let it in data) {
                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                }
                return ret
            }],
        }).then(checkStatus).then(res => resolve(res.data)).catch(e => reject(e));
    });
};

export const get = (api, params) => {
    return request('GET', api, params);
};

export const post = (api, params) => {
    return request('POST', api, params);
};

export const put = (api, params) => {
    return request('PUT', api, params);
};

export const del = (api, params) => {
    return request('DELETE', api, params);
};

export const upload = (api, params) => {
    return axios({
        method: 'POST',
        url: api,
        data: params,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
};

/**
 * 请求拦截
 */
axios.interceptors.request.use(config => {
    // 在请求前做一些操作
    return config
}, error => {
    Message.error('请求超时，请检查网络');
    return Promise.resolve(error)
});

/**
 * 响应拦截
 */
axios.interceptors.response.use(response => {
    if (response.data && response.data.code === 401) { // 401, token失效
        // 清楚登录信息
        clearLoginInfo();
        // 跳转到登录页面
        router.push("/login")
    }
    return response
}, error => {
    Message.error('请求超时，请检查网络');
    return Promise.reject(error)
});
