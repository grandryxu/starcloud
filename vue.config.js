const path = require("path");

const resolve = dir => {
	return path.join(__dirname, dir);
};
const BASE_URL = process.env.NODE_ENV === "production" ? "./" : "/";
module.exports = {
	publicPath: BASE_URL,
	// 如果你不需要使用eslint，把lintOnSave设为false即可
	lintOnSave: false,
	outputDir:'dist',
	assetsDir:'static',
	indexPath:'index.html',
	filenameHashing:true,
	chainWebpack: config => {
		config.resolve.alias
			.set("@", resolve("src"))
			.set("_c", resolve("src/components"));
	},
	// 设为false打包时不生成.map文件
	productionSourceMap: false,
	devServer: {
		hot:true,
        open : true,
        port : 8081,
        host: "0.0.0.0",
		proxy: {
			// "/": {
			// 	target: "http://192.168.1.174:8067",
			// 	changeOrigin: true
			// },
			"/": {
				target: "http://localhost:21022",
				changeOrigin: true
			},
			"/api": {
				target: "http://192.168.1.174:8099",
				changeOrigin: true
			},
			'/api/crm': {
				target: 'http://api.crm-test.lonsid.cn',
				changeOrigin: true
			},
			'/connect': {
				target: 'http://192.168.10.155:8201',
				changeOrigin: true
			},
			'/api/crm/systemUser': {
				target: 'http://soa-test.lonsid.cn',
				changeOrigin: true
			}
		}
	},
	runtimeCompiler: true
};