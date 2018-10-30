<html>
<head>
<meta charset="utf-8">
<title>商品订单列表</title>
<link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body>
<div class="container">
<form action="/sell/weixin/downloadbill" class="form-horizontal"  role="form">
        <fieldset style='text-align:center;'>
            <legend>微信对账单</legend>
			<div class="form-group" >
                <label for="dtp_input2" class="col-md-3 control-label">选择日期</label>
                <div class="input-group date form_date col-md-6" data-date="" data-date-format="yyyymmdd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control" size="16" type="text" value="${(tradeDate)!''}" name="date" >
                    <span class="input-group-addon star" ><span class="glyphicon glyphicon-arrow-right"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="dtp_input2" value=""  /><br/>
            </div>
        </fieldset>
    </form>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<table class="table">
				<thead>
					<tr>
							<th>
							交易时间
						</th>
						<th>
							总交易数
						</th>
						<th>
							正常交易
						</th>
						<th>
							退款交易
						</th>
					</tr>
				</thead>
			<tbody>
							<tr>
							<td class="tradeDate">
									${tradeDate}
								</td>
								<td class="totalRecord">
									${(billList.totalRecord)!0}
								</td>
								<td class="totalFee">
									${(billList.totalFee)!0}
								</td>
								<td class="totalRefundFee">
									${(billList.totalRefundFee)!0}
								</td>
							</tr>
						</tbody>
			</table>

			<table class="table table-condensed"  id="datas">
				<thead>
					<tr>
						<th class="col-md-3 column">
						账单日期
						</th>
						<th >
						账单金额
						</th>
						<th>
								交易状态
						</th>
						<th>
							银行类型
						</th>
					</tr>
				</thead>
				<tbody >
						<tr id="template">
						 <td id="date"></td>
						 <td id="id"></td>
       					 <td id="url"></td>
        					 <td id="title"></td>
					</tr>	
				</tbody>
			</table>
					
		</div>
	</div>
	
	       <div id="container" style="height: 50%"></div>
	       
</div>
<script type="text/javascript" src="../js/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
       <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
       <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);

var app = {};
option = null;
option = {
    title: {
        text: '收入退款统计',
        subtext: '${(msg)!'暂无流水 以0:0:0填充'}',
        left: 'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: ['总流水', '退款','营收']
    },
    series : [
        {
            type: 'pie',
            radius : '65%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            data:[
                {value:${(totalRecord)!0}, name: '总流水'},
                {value:${(totalRefundFee)!0}, name: '退款'},
                {value:${(totalFee)!0}, name: '营收'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
;
//alert(option.series[0].data[0].value)
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
       </script>
<script type="text/javascript">
  
	$('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
	$('.star').click(function(){
	//$('.form-horizontal').submit();
	//alert($('.form-control').val());
	    $.ajax({
             type: "GET",
             url: "http://localhost:8080/sell/weixin/downloadbillJson",
             data: {
             date:$('.form-control').val()
             },
             dataType: "json",
             success: function(data){
            // alert(option.series[0].data[0].value)
             //没有信息
             if(data.code==1){
             alert("没有流水")
              option.series[0].data[0].value = 0
              option.series[0].data[1].value = 0
               option.series[0].data[2].value = 0
             option.title.subtext = '暂无流水 以0:0:0填充'
             if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
              $(".tradeDate").html(data.msg)
              $(".totalRecord").html('0')
               $(".totalFee").html('0')
                $(".totalRefundFee").html('0')
                $("#datas tr:gt(1)").remove();
             }else{
              //填充概览信息
             $(".tradeDate").html(data.msg)
              $(".totalRecord").html(data.data.totalRecord)
               $(".totalFee").html(data.data.totalFee)
                $(".totalRefundFee").html(data.data.totalRefundFee)
           $("#datas tr:gt(1)").remove();
           option.series[0].data[0].value = data.data.totalRecord
              option.series[0].data[1].value = data.data.totalRefundFee
               option.series[0].data[2].value = data.data.totalFee
               option.title.subtext = '微信账单统计'
             if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
             $.each(data.data.wxPayBillBaseResultLst,function(i, n){
             var row = $("#template").clone();
                row.find("#date").text(n.tradeTime);
        if(n.tradeState=='SUCCESS'){
                row.find("#id").text(n.totalFee);
        row.find("#url").text("交易成功");
        }else{
         row.find("#id").text(n.settlementRefundFee);
        row.find("#url").text("退款成功");
        }
        row.find("#title").text(n.feeType);
        row.appendTo("#datas");//添加到模板的容器中
             });
            
             }
             
                      }
         });
});
</script>
 
</body>
</html>
