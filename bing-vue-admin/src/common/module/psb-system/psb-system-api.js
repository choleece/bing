let psbSystemApi = (config) => ({
    // 获取社保订单列表
    listPsbOrder: `${config.psbSystemUrl}/psb/order/page`,
    order: `${config.psbSystemUrl}/psb/order`
});

export default psbSystemApi;