var WorkerAgeReport = {
    dataArray: []
};
$(function() {
	$.get("/projectIndexReprot/getAgeRange").done(function (data) {
	    if(data.length ===0 ){
	        $("#workerAge").html("<div style='line-height:400px;vertical-align: middle;text-align: center;'><label><h2>暂无数据</h2></label></div>")
            return;
        }
        for (var i = 0; i < data.length; i++) {
        	var dataCh = {"name":data[i].ageRange,"value":data[i].count};
        	WorkerAgeReport.dataArray.push(dataCh);
        }
        init();
    });
});

function init(){
	var s = echarts.init(document.getElementById("workerAge")),
    option = {
		title: {
            text: '人员年龄分布'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series: [{
        	name: '年龄',
            type: 'pie',
            radius: [30, '55%'],
            center: ['50%', '50%'],
            roseType: 'radius',
            color: ['#f2c955', '#00a69d', '#46d185', '#ec5845', '#26d035', '#ec2305'],
            data: WorkerAgeReport.dataArray,
            label: {
                normal: {
                    textStyle: {
                        fontSize: 14
                    },
                    formatter: function(param) {
                        return param.name + ':\n' + Math.round(param.percent) + '%';
                    }
                }
            },
            labelLine: {
                normal: {
                    smooth: true,
                    lineStyle: {
                        width: 2
                    }
                }
            },
            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function(idx) {
                return Math.random() * 200;
            }
        }]
    };
	s.setOption(option),
	$(window).resize(s.resize);
}