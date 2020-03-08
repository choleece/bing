let commonSystemApi = (config) => ({
    // 公共信息
    login: `${config.commonSystemUrl}/index/login`,
    loginUser: `${config.commonSystemUrl}/sys/user/login/user`,
    loginOut: `${config.commonSystemUrl}/index/logout`,
    userOwnResource: `${config.commonSystemUrl}/sys/resource/user`,
    getOrdersDetail: `${config.commonSystemUrl}/bsp/order/ids`,
    getPrintInfo:`${config.commonSystemUrl}/bsp/order/print/info`
});

export default commonSystemApi;
