let vehicleSystemApi = (config) => ({
    // 获取车管所订单列表
    listVehicleOrder: `${config.vehicleSystemUrl}/vehicle/order`,
    // 统计每个日期的订单数
    countDateOrder: `${config.vehicleSystemUrl}/vehicle/order/count`
});

export default vehicleSystemApi;
