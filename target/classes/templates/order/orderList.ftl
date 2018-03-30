<html>
<head>
<meta charset="utf-8">
<title>商品订单列表</title>
<link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
<div class="row clearfix">
		<div class="col-md-12 column">
			<ul class="nav nav-tabs">
				<li class="active">
					 <a href="/sell/seller/order/list">订单列表</a>
				</li>
				<li>
					 <a href="#">类目管理</a>
				</li>
				<li class="disabled">
					 <a href="/sell/seller/order/storeList">商户管理</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>
									订单id(用于退款)
								</th>
								<th>
									交易商户
								</th>
								<th>
									商户id
								</th>
								<th>
									手机号
								</th>
								<th>
									openId(用户唯一标志)
								</th>
								<th>
									应付款
								</th>
								<th>
									折扣
								</th>
								<th>
									实付款
								</th>
								<th>
									支付情况
								</th>
								<th>
									交易时间
								</th>
							</tr>
						</thead>
						<tbody>
						<#list orderDTOPage.content as orderDTO>
							<tr>
								<td>
									${orderDTO.orderId}
								</td>
								<td>
									${orderDTO.buyerAddress}
								</td>
								<td>
									${orderDTO.storeId}
								</td>
								<td>
									${orderDTO.buyerPhone}
								</td>
								<td>
									${orderDTO.buyerOpenid}
								</td>
								<td>
									¥${orderDTO.oriPrice}
								</td>
								<td>
									${orderDTO.buyerName}折
								</td>
								<td>
									¥${orderDTO.orderAmount}
								</td>
								<#if orderDTO.payStatus==0>
																<td>未支付</td>
								<#elseif orderDTO.payStatus==1>								<td>已支付</td>
								<#else><td>已退款</td>
								</#if>
								<td>
									${orderDTO.createTime}
								</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<#--分页-->
		<div class="col-md-12 column">
			<ul class="pagination pagination-sm pull-right">
			<#if currentPage lte 1>
				<li class = "disabled"><a href="#">上一页</a></li>
				<#else>
								<li><a href="/sell/seller/order/list?page=${currentPage-1}&size=10">上一页</a></li>
</#if>
				<#list 1..orderDTOPage.getTotalPages() as index>
								<#if currentPage == index>
								<li class="disabled"> <a href="#">${index}</a></li>
								<#else>
																<li > <a href="/sell/seller/order/list?page=${index}&size=10">${index}</a></li>
								</#if>

				</#list>
				<#if currentPage gte orderDTOPage.getTotalPages()>
				<li class="disabled"> <a href="#">下一页</a></li>		
					<#else>
							<li> <a href="/sell/seller/order/list?page=${currentPage+1}&size=10">下一页</a></li>
				</#if>
			</ul>
		</div>
	</div>
</div>
</body>
</html>
