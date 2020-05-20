<template>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="450px" append-to-body>
        <el-form inline ref="scanForm" :model="scanForm" :rules="rules">
            <el-form-item label="扫描码" label-width="100px" prop="imporder_bill_bar">
                <el-input class="iot-w240" v-model="scanForm.imporder_bill_bar" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="物料名称" label-width="100px" prop="goods_name">
                <el-input class="iot-w240" v-model="scanForm.goods_name" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="物料规格" label-width="100px" prop="goods_standard">
                <el-input class="iot-w240" v-model="scanForm.goods_standard" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="物料单位" label-width="100px" prop="goods_unit">
                <el-input class="iot-w240" v-model="scanForm.goods_unit" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="大批号" label-width="100px" prop="imporder_batch_no">
                <el-input class="iot-w240" v-model="scanForm.imporder_batch_no" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="小批号" label-width="100px" prop="imporder_lots_no">
                <el-input class="iot-w240" v-model="scanForm.imporder_lots_no" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="托盘状态" label-width="100px" prop="imporder_stock_flag">
                <el-select class="iot-w240" v-model="scanForm.imporder_stock_flag" :placeholder="$t('import.pleaseselect')+$t('import.palletstatus')" clearable>
                    <el-option v-for="item in imporder_stock_flagList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="质量状态" label-width="100px" prop="impbody_quality_status">
                <el-input class="iot-w240" v-model="scanForm.impbody_quality_status" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="计划数量" label-width="100px" prop="impbody_plan_quantity">
                <el-input class="iot-w240" v-model="scanForm.impbody_plan_quantity" placeholder="" clearable disabled></el-input>
            </el-form-item>
            <el-form-item label="绑定数量" label-width="100px" prop="impbody_binding_quantity">
                <el-input class="iot-w240" v-model="scanForm.impbody_binding_quantity" placeholder="" clearable disabled></el-input>
            </el-form-item>

            <el-form-item label="物资数量" label-width="100px" prop="imporder_quantity">
<!--                <MyNumberInput :point="2" :max="99999"  v-model.number="scanForm.imporder_quantity" :placeholder="$t('import.pleaseinput')+$t('import.materialquantity')"  @checkrule="checkrule($event)" ></MyNumberInput>-->
                <el-input type="number" class="iot-w240" v-model="scanForm.imporder_quantity" :placeholder="$t('import.pleaseinput')+$t('import.materialquantity')"  @blur="checkrule"></el-input>
            </el-form-item>
            <el-form-item label="托盘条码" label-width="100px" prop="imporder_stock_code">
                <el-input class="iot-w240" v-model="scanForm.imporder_stock_code" :placeholder="$t('import.pleaseinput')+$t('import.palletbarcode')" ></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit_Scan">保 存</el-button>
      </span>
    </el-dialog>
</template>

<script>
    import MyNumberInput from "../../../MyNumberInput";
    import {ScanaddApi} from "./api";
    export default {
        name: "importbilldialog",
        components: {
            MyNumberInput,

        },
        data() {
            return {
                dialogTitle: "模拟扫描",
                scanForm:{
                    imporder_stock_code:'',
                    imporder_stock_flag:'',
                    imporder_quantity:'',
                },
                companyId:'',
                imporder_stock_flagList: [
                    { value: 1, label: "正常" },
                    { value: 2, label: "抽检" }
                ],
            }
        },
        props: {
            dialogVisible: {
                type: Boolean,
                required: true
            },
            title: {
                type: String,
                required: true
            },
            rowlistinfo: {
                type: Object,
                required: true
            },


            /* rowlistinfo: {
                 type: Array,
                 default(){
                     return []
                 }
             },*/

        },
        watch: {
            dialogVisible: {
                handler: function (val, oldval) {
                    if (oldval != val) {
                        this.$emit("updataDialogVisible",val)
                        if( this.dialogVisible==true){
                            this.dialogVisible = val
                            this.scanForm.imporder_stock_code=''
                            this.scanForm.imporder_stock_flag=''
                            this.scanForm.imporder_quantity=''

                        }
                    }
                },
                deep: true//对象内部的属性监听，也叫深度监听
            },
            rowlistinfo: {
                handler: function (val, oldval) {
                    if (oldval != val) {
                        this.getInfo();
                    }
                },
                deep: true//对象内部的属性监听，也叫深度监听
            },
        },
        computed:{
            rules() {　　//此处的rules 是你声明的名字，参考下图
                var validate_Types = (rule, value, callback) => {   //兑换类型规则
                    if (this.scanForm.types == '') {
                        callback(new Error(this.$t('navbar.systembusy')));
                    }
                    callback();
                };
                return {　　//注意此处的return，别忘记了
                    ConverTypes: [
                        {validator: validate_Types, trigger: 'change'},
                    ],
                    imporder_quantity: [
                        {required: true, message: this.$t('import.pleaseselect')+this.$t('import.materialquantity'), trigger: 'change'},
                    ],
                    imporder_stock_code: [
                        {required: true, message: this.$t('import.pleaseinput')+this.$t('import.palletbarcode'), trigger: 'change'}
                    ],
                    imporder_stock_flag: [
                        {required: true, message: this.$t('import.pleaseinput')+this.$t('import.palletstatus'), trigger: 'change'}
                    ],
                }
            },
        },
        mounted() {
            if (Utils.getStorage('userInfo'))
            {
                this.companyId = Utils.getStorage("userInfo").data.result.companyId;
            }

        },
        created(){
        },
        methods: {
           /* checkrule(val){
                if(this.scanForm.impbody_plan_quantity - this.scanForm.impbody_binding_quantity < val)
                {
                    this.$message({
                        message: '物资数量不能大于计划数量与绑定数量之差！',
                        type: 'warning'
                    });
                    this.scanForm.imporder_quantity='';
                }
            },*/
            checkrule(){
                if(this.scanForm.impbody_plan_quantity - this.scanForm.impbody_binding_quantity < this.scanForm.imporder_quantity)
                {
                    this.$message({
                        message: '物资数量不能大于计划数量与绑定数量之差！',
                        type: 'warning'
                    });
                    this.scanForm.imporder_quantity='';
                }
            },
            getInfo(){

                this.scanForm.imporder_bill_bar=this.rowlistinfo.impbody_bill_bar
                this.scanForm.goods_name=this.rowlistinfo.goods_name
                this.scanForm.goods_standard=this.rowlistinfo.goods_standard
                this.scanForm.goods_unit=this.rowlistinfo.goods_unit
                this.scanForm.imporder_lots_no=this.rowlistinfo.impbody_lots_no
                this.scanForm.imporder_batch_no=this.rowlistinfo.impbody_batch_no
                this.scanForm.impbody_quality_status=this.rowlistinfo.impbody_quality_status
                this.scanForm.impbody_plan_quantity=this.rowlistinfo.impbody_plan_quantity
                this.scanForm.impbody_binding_quantity= this.rowlistinfo.impbody_binding_quantity
                this.scanForm.imporder_quality_status=this.rowlistinfo.imporder_quality_status
                this.scanForm.imporder_goods_id= this.rowlistinfo.imporder_goods_id
                this.scanForm.imporder_body_id=this.rowlistinfo.imporder_body_id
                this.scanForm.imporder_warehouse_id=this.rowlistinfo.imporder_warehouse_id
            },
            click_submit_Scan(){
                this.$refs['scanForm'].validate((valid) => {
                    if (valid) {

                        if (!Object.keys(this.scanForm).length) {
                            this.dialogVisible = false;
                            return;
                        }
                        this.$msgbox({
                            title: "提示",
                            message: '确定保存吗？',
                            showCancelButton: true,
                            type: "warning"
                        }).then(async action => {
                            let params = {
                                ...this.scanForm
                            };
                            console.log('------------------------开始-------------')
                            console.log(params)

                            let res = await ScanaddApi(params)
                            if (res) {
                                this.$emit("GetAllGoods")
                                this.$message({
                                    type: "success",
                                    message: "新增成功"
                                });
                                this.dialogVisible = false;
                            }
                        });

                    }
                })

            },

        }

    }
</script>

<style scoped>

</style>