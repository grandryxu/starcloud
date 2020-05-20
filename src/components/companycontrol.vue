<template>
    <el-form-item label="所属公司" :label-width="labelwidth" >
        <el-cascader
                v-model="companyId"
                :options="Cmyoptions"
                :props="{ checkStrictly: true }"
                clearable  style="width: 240px;"  ></el-cascader>
    </el-form-item>
</template>

<script>
    export default {
        name: "companycontrol",
        data() {
            return {
                Cmyoptions:[],
                quality_company_id:'',
            }
        },
        props:{
            companyId: {
                type: String,
                required: true
            },
            labelwidth: {
                type: String,
                required: false,
                default:'100px'
            },
        },
        watch: {
            companyId: {
                handler: function (val, oldval) {
                    if (oldval != val) {
                        this.$emit("updateCompanyId",val[val.length-1])
                    }
                },
                deep: true//对象内部的属性监听，也叫深度监听
            },
        },
        methods: {
            //根据当前用户权限标识初始化按钮状态
            btnInit() {
                this.$Common.DistributePermission.call(this)
            },
            async GetCompanyTreeList(){
                let params = { Type: 'companytree'};
                let data = await this.$DropBox.getCompanyTreeList(params);
                if (data) {
                    this.Cmyoptions = data;
                }
            },
        },
        mounted() {
            this.GetCompanyTreeList();

        }
    }
</script>

<style scoped>

</style>