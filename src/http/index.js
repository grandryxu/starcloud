import axios from 'axios';
import Utils from '@/utils/index'
import router from '../router/index'
import store from '../store/index'

let pending = []; //存储每个ajax请求的取消函数和ajax标识
let cancelToken = axios.CancelToken;
let baseURL = store.state.api_BaseURL

let http = axios.create({
        baseURL:baseURL,
        withCredentials: false,
        timeout: 10000
    });
let removePending = (ever) => {
        for (let k in pending) {
            if (pending[k].u === ever.url + '&' + ever.method) { //当当前请求在数组中存在时执行函数体
                pending[k].f(); //执行取消操作
                pending.splice(k, 1); //把这条记录从数组中移除
            }
        }
    }
    //请求拦截器
    http.interceptors.request.use(config => {
        window.globalLoading = window.rootApp.$loading({
            lock: true,
            text: '加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
        removePending(config); //在一个ajax发送前执行一下取消操作
        config.cancelToken = new cancelToken((c) => {
            // 这里的ajax标识我是用请求地址&请求方式拼接的字符串，当然你可以选择其他的一些方式
            pending.push({
                u: config.url + '&' + config.method,
                f: c
            });
        });
        if (Utils.getStorage('userInfo') && Utils.getStorage('userInfo').data.result.accessToken) {
            config.headers['Authorization'] =`Bearer ${Utils.getStorage('userInfo').data.result.accessToken}`
        } else {
            router.replace('/Login')
        }
        return config;
    });
    //响应拦截器
http.interceptors.response.use(res => {
        window.globalLoading.close();
        removePending(res.config); //响应结束后删除ajax请求记录
        let result = null;
        if (res.data.success) {
            result = res.data.result || true;
        } else {
            window.rootApp.$message({
                type: "error",
                message: (res.data.error || "服务器错误")
            });
        }
        return result;
}, error => {
        window.globalLoading.close();
        console.log(error);
        if(error.response && typeof(error.response.data) !== 'object' && error.response.status === 500) {
            window.rootApp.$message({
                type: "error",
                message: '服务器错误'
            })
        }else if (!Utils.getStorage('userInfo') ||error.response && typeof(error.response.data) === 'object' && error.response.data.error.message) {
            window.rootApp.$message({
                type: "error",
                message: `${error.response.data.error.message}`
            });
        } else {
            window.rootApp.$message({
                type: "error",
                message: '系统繁忙请稍后再试'
            })
        }
        
    });
let get = (url, config) => http.get(url, config);
let request = (type) => {
        let fun = function (url, data = {}, config = {}) {
            if (config._cType == "form") {
                http.defaults.headers[type]['Content-Type'] = "application/x-www-form-urlencoded";
            } else if (config._cType == "formData") {
                http.defaults.headers[type]['Content-Type'] = "multipart/form-data";
            } else {
                http.defaults.headers[type]['Content-Type'] = "application/json";
            }
            config._cType && delete config._cType;
            return http[type](url, data, config);
        };
        return fun;
    }

export default {
    get ,
    post: request('post'),
    put: request('put'),
    delete: request('delete'),
};