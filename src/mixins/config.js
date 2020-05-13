import {
	mapState
} from 'vuex'
let config = {
	data() {
		return {
			c_pageSizes: [10, 15, 20, 25],
			c_primary: "color: #409EFF",
			c_success: "color: #67C23A",
			c_danger: "color: red",
		};
	},
	filters: {
		addressShow(json, detail) {
			if (!json) return ''
			let arr = JSON.parse(json);
			let str = ''
			arr.forEach(el => {
				str += el.n;
			})
			return str + detail
		}
	},
	created() {
		this.pageSize = this.c_pageSize;
	},
	computed: {
		...mapState(['c_pageSize'])
	},
	watch: {},
	methods: {}
};
export default config;