@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/
<div class="form-group form-group-sss"
     @if(isNotEmpty(visible)){
     style="display: none;"
    @}

     @if(isNotEmpty(pid)){
     id="${pid}"
     @}
>
    <label class="col-sm-3 control-label control-label-sss">
        @if(isNotEmpty(requiredFlag)){
        <text style="color: red">*</text>
        @}
        @if(isNotEmpty(must)){
        @if(must + "" == "0"){
        <text style="color: red">*</text>
        @}
        @}
        ${name}:</label>
    <div class="col-sm-9">
            <p class="select-disabled-p">
            <select class="form-control form-control-sss 
	               		@if(isNotEmpty(class)){
	               	     ${class}
	                    @}
            		" id="${id}" name="${id}"
                    @if(isNotEmpty(disabled)){
                    disabled="${disabled}"
                    @}
                    @if(isNotEmpty(onchange)){
                    onchange="${onchange}"
                    @}
            >
                ${tagBody!}
            </select>
            <span class="select-disabled-block"></span>
            </p>
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}">
        @}
    </div>
</div>


