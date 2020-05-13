
<div class="form-group form-group-sss" style="padding:6px 0;">
    <label class="col-sm-3 control-label control-label-sss" style="padding-top:5px;">
        ${label}
    </label>

    <div class="col-sm-9">
        @if(isNotEmpty(value)){
            <input type="hidden" id="${name}" value="${value}" name="${name}" />
            @if(value=="0" || value==0){
                <div class="radio radio-info2 radio-inline">
                    <input type="radio" id="inlineRadio${name}1"
                           @if(isNotEmpty(disabled)){
                           disabled = ${disabled}
                                   @}
                           name="inlineRadio${name}" value="0" checked>
                    <label for="inlineRadio${name}1">男</label>
                </div>
                <div class="radio radio-info2 radio-inline">
                    <input type="radio" id="inlineRadio${name}2"
                           @if(isNotEmpty(disabled)){
                           disabled = ${disabled}
                                   @}
                           name="inlineRadio${name}" value="1" >
                    <label for="inlineRadio${name}2">女</label>
                </div>
            @}else{
                <div class="radio radio-info2 radio-inline">
                    <input type="radio" id="inlineRadio${name}1"
                           @if(isNotEmpty(disabled)){
                           disabled = ${disabled}
                                   @}
                           name="inlineRadio${name}" value="0" >
                    <label for="inlineRadio${name}1">男</label>
                </div>
                <div class="radio radio-info2 radio-inline">
                    <input type="radio" id="inlineRadio${name}2"
                           @if(isNotEmpty(disabled)){
                           disabled = ${disabled}
                                   @}
                           name="inlineRadio${name}" value="1" checked>
                    <label for="inlineRadio${name}2">女</label>
                </div>
            @}
        @}else{
            <input type="hidden" id="${name}" value="1" name="${name}" />
            <div class="radio radio-info2 radio-inline">
                <input type="radio" id="inlineRadio${name}1"
                       @if(isNotEmpty(disabled)){
                       disabled = ${disabled}
                               @}
                       name="inlineRadio${name}" value="0" checked>
                <label for="inlineRadio${name}1">男</label>
            </div>
            <div class="radio radio-info2 radio-inline">
                <input type="radio" id="inlineRadio${name}2"
                       @if(isNotEmpty(disabled)){
                       disabled = ${disabled}
                               @}
                       name="inlineRadio${name}" value="1"  >
                <label for="inlineRadio${name}2">女</label>
            </div>
        @}
    </div>
</div>
<script>
    var radio=$('input[name="inlineRadio${name}"]');
    radio.change(function(){
        if($(this).val()=='0'){
            $('input[name="${name}"]').val(0);
        }else{
            $('input[name="${name}"]').val(1);
        }
    });
</script>