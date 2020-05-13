import XLSX from 'xlsx'
import FileSaver from 'file-saver'
import Vue from 'vue'
import Print from 'vue-printjs'

//导出Id
class Common{
    //导出表格
    static ExportExcel(tableId){
        //从表生成工作簿对象
        let wb = XLSX.utils.table_to_book(document.querySelector(tableId));
        //获取二进制字符串作为输出
        let wbout = XLSX.write(wb, {
            bookType: "xlsx",
            bookSST: true,
            type: "array"
        });
        try {
            FileSaver.saveAs(
            new Blob([wbout], { type: "application/octet-stream" }),
            //设置导出文件名称
            "WMS.xlsx"
            );
        } catch (e) {
            if (typeof console !== "undefined") console.log(e, wbout);
        }
        return wbout;
    }

    //打印
    static PrintTable(refName){
        this.$refs[refName].bodyWrapper.style.overflow = 'hidden';
        this.visibleSelection = false;
        this.$nextTick(()=>{
            this.$print(this.$refs[refName]);
            this.visibleSelection = true;
            this.$refs[refName].bodyWrapper.style.overflow = 'auto';
        })
    }

    //设置进入页面的权限
    static DistributePermission(){
        let permissionsNode = Array.from(this.$refs.permissions.children);
        permissionsNode = permissionsNode.map(perNode=>{
            if(!this.$store.state.enterTargetPagePermissions.includes(perNode.innerText)){
                perNode.style.display = 'none'
            }
        })
        //判断是否可以双击编辑
        if(!this.$store.state.enterTargetPagePermissions.includes('编辑')){
            this.click_edit = null
        }
    }

}

export default Common