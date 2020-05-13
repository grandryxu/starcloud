$(function () {
    createTable(priceName);
});


function createTable(a) {
    var data = priceTable[a];
    if (data) {
        var html = "<tr><th>"+data[0][0]+"</th><th>"+data[0][1]+"</th></tr>";
        for (var i = 1; i< data.length; i++) {
            html += "<tr><td><input class=\"form-control\" aria-required=\"true\" name='name' disabled='disabled'  type=\"text\" value=\""+data[i][0]+"\" /></td><td><input class=\"form-control\" aria-required=\"true\" name='name' disabled='disabled'  type=\"text\" value=\""+data[i][1]+"\" /></td></tr>";
        }
        $("#priceCompareTable").html(html);
    } else {
        var html = "<tr><th>参建单位</th><th>产品价格</th></tr>";
        $("#priceCompareTable").html(html);
    }
}
