let socialSystemApi = (config) => ({
    // 获取社保订单列表
    listSocialOrder: `${config.socialSystemUrl}/social/order/page`
});

export default socialSystemApi;
