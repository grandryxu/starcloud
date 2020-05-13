(function () {
    $("body").on('click','.fn-condition .fn-down',function(){
        $(".fn-need-hide").show();
        $('.fn-condition').hide();
        var html = $('.fn-condition').html();
        $(".fn-buttons .col-sm-4").html(html);
    });

    $("body").on('click','.fn-buttons .fn-up',function(){
        $(".fn-need-hide").hide();
        $('.fn-condition').show();
    });
	
} ());