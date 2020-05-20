import Vue from 'vue'
import Store from '@/store'
import ZH from "@/lang/zh.js"
import EN from "@/lang/en.js"
import JA from "@/lang/ja.js"

const utils = {
    testPhone: (phone) => {
        phone = phone.toString();
        if (/^1[3456789]\d{9}$/.test(phone)) {
            return true
        }
        return false
    },
    setStorage: (key, json, tag = false) => {
        if (tag) {
            localStorage.setItem(key, JSON.stringify(json))
        } else {
            sessionStorage.setItem(key, JSON.stringify(json))
        }
    },
    getStorage: (key) => {
        let json = localStorage.getItem(key) || sessionStorage.getItem(key)
        if (json) {
            json = JSON.parse(json)
        }
        return json
    },
    //批量处理
    batcheHandle: async function (rows, options = {}, handle, handleName, callback) {
        let count = 0;
        for (let i = 0; i < rows.length; i++) {
            let rules = {};
            Object.keys(options).forEach(function (key) {
                Object.assign(rules, {[key]: rows[i][options[key]]})
            });
            console.log(rules)
            let res = await handle(rules);
            if (res) {
                count++
            }
        }
        if (count) {
            Vue.prototype.$message({
                type: "success",
                message: `成功${handleName + count}条,${handleName}失败${rows.length - count}条`
            });
        }
        callback(count)
    },
    //数组/字符串的转换
    arrayAndStringTransform(obj, key, separate = '/') {
        if (typeof obj[key] === 'string') {
            obj[key] = obj[key].split(separate)
        } else {
            obj[key] = obj[key].join(separate)
        }
    },

    // 遍历路由，与userinfo中的权限进行匹配判断，显示到页面。
    filterAsyncRouter(asyncRouterMap, permissions) {
        const accessedRouters = asyncRouterMap.filter(route => {
            if (route.meta && route.meta.title) {
                if (!permissions.includes(route.meta.title)) {
                    return false;
                } else {
                    if (route.children && route.children.length) {
                        route.children.forEach((vv, ii, aa) => {
                            if (permissions.includes(vv.name)) {
                                vv.hidden = false;
                            } else {
                                vv.hidden = true;
                            }
                        })
                    }
                }
                //先按照目前层级来，如果以后还有更深层级，则需要在后台数据库以及权限配置中继续增加子路由的meta.title属性值即可
                // if (route.childreZHn && route.children.length) {
                //     route.children = filterAsyncRouter(route.children, permissions)
                // }
            }
            return true
        })

        return accessedRouters
    },
    //通过关键词遍历语言库返回key值
    traverseLangLibsByWords(lgLibs, words) {
        let temKey = void 0;
        Object.keys(lgLibs).forEach(pkey => {
            Object.keys(lgLibs[pkey]).forEach(ckey => {
                if (lgLibs[pkey][ckey] === words) {
                    temKey = ckey
                }
            })
        })
        return temKey
    },

    //通过key遍历语言库返回words值
    traverseLangLibsBykey(lgLibs, key) {
        let temWords = void 0;
        Object.keys(lgLibs).forEach(pkey => {
            Object.keys(lgLibs[pkey]).forEach(ckey => {
                if (ckey == key) {
                    temWords = lgLibs[pkey][ckey]
                }
            })
        })
        return temWords
    },

    //拉取对应的语言库
    pullLangLibs(lang) {
        switch (lang) {
            case 'zh':
                return ZH
                break;
            case 'en':
                return EN
                break;
            case 'ja':
                return JA
                break
        }
    },
    //两个语种进行翻译
    traverseLang(curLgLibs, tarLgLibs, words) {
        let key = this.traverseLangLibsByWords(curLgLibs, words);
        if (key) {
            words = this.traverseLangLibsBykey(tarLgLibs, key)
        }
        return words;
    },
    // 判断是否为中文
    isChinese(str) {
        if (escape(str).indexOf("%u") < 0) {
            return false
        } else {
            return true
        }
    },
    //判断是哪一种语言
    isLang(words) {
        if (/[a-zA-Z]+/.test(words)) {
            return 'en'
        } else if (/[\u0800-\u4e00]+/.test(words)) {
            return 'ja'
        } else if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(words)) {
            return 'zh'
        }
    },
    //遍历页面dom进行翻译
    traversePageDom(curLg, tarLg, dom) {
        let curLgLibs = this.pullLangLibs(curLg);
        let tarLgLibs = this.pullLangLibs(tarLg);
        if (!dom.children.length) {
            return;
        } else {
            dom.children.forEach(ele => {
                if (ele.tagName === "LABEL" && ele.innerText) {
                    if(ele.children.length){
                        ele.children.forEach(chil=>{
                            if (chil.tagName === "SPAN" && chil.innerText){
                                let innerCurLgLibs = this.pullLangLibs(this.isLang(chil.innerText))
                                chil.innerText = this.traverseLang(innerCurLgLibs, tarLgLibs, chil.innerText)
                            }
                            this.traversePageDom(curLg, tarLg, chil);
                        })
                    }else{
                        let innerCurLgLibs = this.pullLangLibs(this.isLang(ele.innerText))
                        ele.innerText = this.traverseLang(innerCurLgLibs, tarLgLibs, ele.innerText)
                    } 
                } else if (
                    ele.tagName === "BUTTON" &&
                    ele.children[ele.children.length - 1] &&
                    ele.children[ele.children.length - 1].tagName === "SPAN"
                ) {
                    let innerCurLgLibs = this.pullLangLibs(this.isLang(ele.children[ele.children.length - 1].innerText))
                    ele.children[ele.children.length - 1].innerText = this.traverseLang(innerCurLgLibs, tarLgLibs, ele.children[ele.children.length - 1].innerText)
                } 
                else if (ele.tagName === "TH" && ele.children[0] && !ele.children[0].children.length) {
                    let innerCurLgLibs = this.pullLangLibs(this.isLang(ele.children[0].innerText))
                    ele.children[0].innerText = this.traverseLang(innerCurLgLibs, tarLgLibs, ele.children[0].innerText)
                } 
                else if (
                    ele.tagName === "INPUT" &&
                    ele.getAttribute("placeholder")
                ) {
                    let placeholder = ele.getAttribute("placeholder");
                    let words = '';
                    let curLgLibs = this.pullLangLibs(this.isLang(placeholder))
                    words = this.traverseLang(curLgLibs, tarLgLibs, placeholder)
                    ele.setAttribute("placeholder", words)
                } else {
                    this.traversePageDom(curLg, tarLg, ele);
                }
            });
        }
        Store.commit('STORECURRENTLANG', tarLg)
    },
    //遍历dialogForm页面dom进行翻译
    traverseDialogDom(curLg, tarLg, dialog) {
        let curLgLibs = this.pullLangLibs(curLg);
        let tarLgLibs = this.pullLangLibs(tarLg);
        if (!dialog.$children.length) {
            return;
        } else if(dialog.$children.length) {
            dialog.$children.forEach(ele => {
                let firstInnerCurLgLibs = this.pullLangLibs(curLg);
                let lastInnerCurLgLibs = this.pullLangLibs(curLg);
                if (this.isChinese(ele.$el.firstChild.innerText)) {
                    firstInnerCurLgLibs = this.pullLangLibs('zh');
                } else {
                    firstInnerCurLgLibs = this.pullLangLibs('en');
                }
                ele.$el.firstChild.innerText = this.traverseLang(firstInnerCurLgLibs, tarLgLibs, ele.$el.firstChild.innerText);
                if (ele.$el.lastChild.firstElementChild.placeholder) {
                    let placeholder = ele.$el.lastChild.firstElementChild.placeholder;
                    let words = '';
                    lastInnerCurLgLibs = this.pullLangLibs(this.isLang(placeholder))
                    words = this.traverseLang(lastInnerCurLgLibs, tarLgLibs, placeholder);
                    ele.$el.lastChild.firstElementChild.placeholder = words;
                } else if (ele.$el.lastChild.firstElementChild.firstElementChild.placeholder) {
                    let placeholder = ele.$el.lastChild.firstElementChild.firstElementChild.placeholder;
                    let words = '';
                    lastInnerCurLgLibs = this.pullLangLibs(this.isLang(placeholder))
                    words = this.traverseLang(lastInnerCurLgLibs, tarLgLibs, placeholder);
                    ele.$el.lastChild.firstElementChild.firstElementChild.placeholder = words;

                } else if (
                    ele.$el.lastChild.firstElementChild.firstElementChild.firstElementChild.placeholder) {
                    let placeholder = ele.$el.lastChild.firstElementChild.firstElementChild.firstElementChild.placeholder;
                    let words = '';
                    lastInnerCurLgLibs = this.pullLangLibs(this.isLang(placeholder))
                    words = this.traverseLang(lastInnerCurLgLibs, tarLgLibs, placeholder);
                    ele.$el.lastChild.firstElementChild.firstElementChild.firstElementChild.placeholder = words;
                }
            })
        }
        Store.commit('STORECURRENTLANG', tarLg)
    },
    //翻译dialogHeader
    traverDialogHeader(header, tarLg){
        if (!header) {
            return
        }
            let words = header;
            let zhKey = this.traverseLangLibsByWords(this.pullLangLibs(this.isLang(words)), words);//获取中文字典中的key
            if (zhKey) {
                header = this.traverseLangLibsBykey(this.pullLangLibs(tarLg), zhKey)
            }
            console.log(header)
        return header
    },
    //遍历表单验证
    traverseFormValidator(rules, tarLg) {
        if (!rules) {
            return
        }
        Object.keys(rules).forEach(key => {
            let words = rules[key][0].message;
            let zhKey = this.traverseLangLibsByWords(this.pullLangLibs(this.isLang(words)), words);//获取中文字典中的key
            if (zhKey) {
                rules[key][0].message = this.traverseLangLibsBykey(this.pullLangLibs(tarLg), zhKey)
            }
        });
        return rules
    },


    /*
        datetrans(time,format){
            format='yyyy-MM-dd';
            let moment = require("moment");
            return  moment(time).format(format);
        },
    */


    //时间格式化
    datetransDay(time) {
        let moment = require("moment");
        return moment(time).format("YYYY-MM-DD");
    },
    //时间格式化 yyyy-MM-dd hh:mm:ss
    datetransSecond(time) {
        let moment = require("moment");
        return moment(time).format("YYYY-MM-DD hh:mm:ss");
    },
    //时间格式化 yyyy-MM-dd hh:mm:ss.xxx
    datetransMsecond(time) {
        let moment = require("moment");
        return moment(time).format("YYYY-MM-DD hh:mm:ss.SSS");
    },
    //时间格式化
     format(date, format) {
        if (typeof date == 'string') {
            if (date.indexOf('T') >= 0) {
                date = date.replace('T', ' ')
            }
            date = new Date(Date.parse(date.replace(/-/g, "/")))
        }
        var o = {
            "M+": date.getMonth() + 1,
            "d+": date.getDate(),
            "h+": date.getHours(),
            "m+": date.getMinutes(),
            "s+": date.getSeconds(),
            "q+": Math.floor((date.getMonth() + 3) / 3),
            "S": date.getMilliseconds()
        };
        var w = [
            ['日', '一', '二', '三', '四', '五', '六'],
            ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
        ];
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(w+)/.test(format)) {
            format = format.replace(RegExp.$1, w[RegExp.$1.length - 1][date.getDay()]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }

}

export default utils