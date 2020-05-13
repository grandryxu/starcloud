@/*
    表单中input框标签中各个参数的说明:

    hidden : input hidden框的id
    id : input框id
    name : input框名称
    readonly : readonly属性
    clickFun : 点击事件的方法名
    style : 附加的css属性
@*/
<div class="form-group form-group-sss"
     @if(isNotEmpty(visible)){
     style="display:none;"
     @}
>
    <label class="col-sm-3 control-label control-label-sss">
        @if(isNotEmpty(requiredFlag)){
        <text style="color: red">*</text>
        @}
        ${name}:</label>
    <div class="col-sm-9">
        @if(isNotEmpty(value)){
            <textarea class="textarea-sss" id="${id}" name="${id}" cols="6" style="resize:none;"
                      @if(isNotEmpty(readonly)){
                      readonly="${readonly}"
                      @}
                      @if(isNotEmpty(clickFun)){
                      onclick="${clickFun}"
                      @}
                      @if(isNotEmpty(style)){
                      style="${style}"
                      @}
                      @if(isNotEmpty(disabled)){
                      disabled="${disabled}"
                      @}
                      @if(isNotEmpty(maxlength)){
                      maxlength="${maxlength}"
                      @}
            >${value}</textarea>
        @}else{
            <textarea class="textarea-sss" id="${id}" name="${id}" cols="4" style="resize:none;"
                      @if(isNotEmpty(readonly)){
                      readonly="${readonly}"
                      @}
                      @if(isNotEmpty(clickFun)){
                      onclick="${clickFun}"
                      @}
                      @if(isNotEmpty(style)){
                      style="${style}"
                      @}
                      @if(isNotEmpty(disabled)){
                      disabled="${disabled}"
                      @}
                      @if(isNotEmpty(maxlength)){
                      maxlength="${maxlength}"
                      @}
            ></textarea>
        @}


    </div>
</div>