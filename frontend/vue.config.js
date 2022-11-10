const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: true,
});

module.exports = {
  outputDir: '../src/main/resources/static',
  indexPath: '../templates/index.html',
  devServer: {
    port: 3000,
    proxy: 'http://localhost:8080',
  },
  chainWebpack: config => {
    const svgRule = config.module.rule('svg');
    svgRule.uses.clear();
    svgRule.use('vue-svg-loader').loader('vue-svg-loader');
  },
};
