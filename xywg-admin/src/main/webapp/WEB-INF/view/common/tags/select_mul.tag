@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">
        @if(isNotEmpty(requiredFlag)){
        <text style="color: red">*</text>
        @}
        ${name}</label>
    <div class="col-sm-9">
        <select @if(isNotEmpty(class)){
                class="${class}"
                @}
                id="${id}" name="${id}"
                @if(isNotEmpty(disabled)){
                disabled="${disabled}"
                @}
                @if(isNotEmpty(onchange)){
                onchange="${onchange}"
                @}
        >
            ${tagBody!}
        </select>
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}">
        @}
    </div>
</div>

