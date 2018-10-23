import request from '../utils/request';

export async function query() {
  return request('/api/users');
}

export async function queryCurrent() {
  return request('/api/currentUser');
}

/**
 * 获取用户的菜单列表
 * @returns {Promise.<void>}
 */
export async function listUserMenu(params) {
  return request('/api/sys/user/menu', { method: 'POST', body: params });
}
