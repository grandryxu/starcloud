
<div class="form-group form-group-sss" style="padding:6px 0;">
    <label class="col-sm-3 control-label control-label-sss" style="padding-top:5px;">
       <text style="color: red"  @if(isNotEmpty(requiredFlag)){
             style="display:none;"
             @}
       >*</text>${label}
    </label>
    <div class="col-sm-9">
        @for(radio in ${radios}){
            <div class="radio radio-info2 radio-inline">
                <input type="radio" id="inlineRadio${name}${radio.num}" value="${radio.num}" name="${name}" >
                <label for="inlineRadio${name}${radio.num}">${radio.name}</label>
            </div>
        @}
    </div>
</div>
