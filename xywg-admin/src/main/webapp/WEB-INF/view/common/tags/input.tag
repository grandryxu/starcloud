@/*
表单中input框标签中各个参数的说明:

hidden : input hidden框的id
id : input框id
name : input框名称
readonly : readonly属性
clickFun : 点击事件的方法名
style : 附加的css属性
@*/
<div class="form-group"
@if(isNotEmpty(parentId)){
     id="${parentId}"
@}

     @if(isNotEmpty(visible)){
     style="display:none;"
     @}
>
    <label class="col-sm-3 control-label">
        @if(isNotEmpty(requiredFlag)){
        <text style="color: red"
              @if(isNotEmpty(requiredFlagId)){
              id="${requiredFlagId}"
              @}
        >*</text>
        @}
        ${name}</label>
    <div class="col-sm-9">
        <input  digits class="form-control" aria-required="true" id="${id}" name="${id}"
               @if(isNotEmpty(value)){
               value="${tool.dateType(value)}"
               @}
               @if(isNotEmpty(type)){
               type="${type}"
               @}else{
               type="text"
               @}
               @if(isNotEmpty(readonly)){
               readonly="${readonly}"
               @}
               @if(isNotEmpty(autocomplete)){
               autocomplete="${autocomplete}"
               @}
               @if(isNotEmpty(clickFun)){
               onclick="${clickFun}"
               @}
               @if(isNotEmpty(onfocus)){
                onfocus="${onfocus}"
               @}
               @if(isNotEmpty(style)){
               style="${style}"
               @}
               @if(isNotEmpty(disabled)){
               disabled="${disabled}"
               @}
               @if(isNotEmpty(placeholder)){
               placeholder="${placeholder}"
               @}
               @if(isNotEmpty(onkeyup)){
               onkeyup="${onkeyup}"
               @}
               @if(isNotEmpty(ariaRequired)){
                aria-required="${ariaRequired}"
               @}
                @if(isNotEmpty(required)){
                required="${required}"
                @}
                @if(isNotEmpty(maxlength)){
                maxlength="${maxlength}"
                @}
        >
        @if(isNotEmpty(hidden)){
        <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}" >
        @}

        @if(isNotEmpty(selectFlag)){
        <div id="${selectId}" style="display: none; position: absolute; z-index: 200;">
            <ul id="${selectTreeId}" class="ztree tree-box" style="${selectStyle!}"></ul>
        </div>
        @}
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
@}


