<html>
<head>
<meta charset="utf-8">
<title>商户列表</title>
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
									商户id
								</th>
								<th>
									商户名
								</th>
								<th>
									商户地址
								</th>
								<th>
									商户图片
								</th>
								<th>
									商户简介
								</th>
								<th>
									商户折扣
								</th>
								<th>
									商户电话
								</th>
								<th>
									运营时间
								</th>
							</tr>
						</thead>
						<tbody>
						<#list storesPage.content as stores>
							<tr>
								<td>
									${stores.storeId}
								</td>
								<td>
									${stores.storeName}
								</td>
								<td>
									${stores.storeAdd}
								</td>
								<td>
									${stores.storePic}
								</td>
								<td>
									${stores.storeSubtitle}
								</td>
								<td>
									${stores.discount}折
								</td>
								<td>
									${stores.storeTel}
								</td>
								<td>
									${stores.storeTime}
								</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
