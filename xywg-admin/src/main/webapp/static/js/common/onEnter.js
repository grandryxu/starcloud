
/**
 *  TODO:为form下所有input的添加enter事件 默认为该表单下 class="fa-search" 父级的搜索
 */
$("form").each(function(index,form){
    $(form).find("input[type='text']").each(function(index,input){
        $(input).on("keypress",function (event) {
            if(event.keyCode === 13){
                $($(form).find(".fa-search").parent()).click();
            }
        })
    })
});