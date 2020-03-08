module.exports = {
    devServer:{
      port:8082
    },
    baseUrl: '',
    assetsDir: 'static',
    productionSourceMap: false, // 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度
    configureWebpack: config => {
        config.store;
        process.env.NODE_ENV;
    },
    chainWebpack: config => {
        // svg Loader
        const svgRule = config.module.rule('svg');
        svgRule.uses.clear();
        svgRule
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            });
      }
}
