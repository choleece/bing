import axios from 'axios';
import { Message } from 'element-ui';

import config from '../cosntant/config';

/**
 * 获取http的请求头
 * @returns {{Content-Type: string}}
 */
const getHeader = () => {
    return {
        'Content-Type': 'application/json; charset=utf-8'
    };
};

axios.defaults.baseURL = config.base_url;
axios.defaults.headers = getHeader();
axios.defaults.timeout = 1000 * 30;

const request = (method, api, params) => {
    return axios({
        method: method,
        url: api,
        data: params,
        transformRequest: [function (data) {
            let ret = ''
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret
        }],
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
        // 跳转到登录页面
    }
    return response
}, error => {
    return Promise.reject(error)
});
