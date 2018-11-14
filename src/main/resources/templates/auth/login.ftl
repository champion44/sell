<html>
<head>
    <meta charset="utf-8">
    <title>网站登录</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
        	<script type="text/javascript" src="http://cdn.staticfile.org/jquery/2.0.0/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.0.1/js/transition.min.js"></script>
                        <link rel="stylesheet" type="text/css" href="../css/newIndex.css"> 
</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
		<img class='col-md-12' style='padding:30px;'  alt="140x140" src="http://ibootstrap-file.b0.upaiyun.com/lorempixel.com/1600/500/sports/1/default.jpg" />
			<form role="form" style='width:95%;margin-left:2.5%;' method="post" action="/sell/login/validate">
				<div class="form-group ">
					 <label for="exampleInputEmail1">用户名</label><input name='username' type="text" class="form-control"  />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">密码</label><input name='password' type="password" class="form-control" />
				</div>
				
				<button type="submit" style='margin-right:30px;margin-top:20px;' class="btn btn-primary">登录</button>
											<a  class="btn btn-default" style='margin-right:30px;margin-top:20px;' href='/sell/login/regist'>注册</a>	
			</form>
		</div>
	</div>
</div>

</body>


</html>