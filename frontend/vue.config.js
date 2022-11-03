const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: "../src/main/resources/static",
  indexPath: "../static/index.html",
  devServer: {
    proxy: "http://localhost:8080"
  },
});
