<head>
<link rel="stylesheet" href="resources/css/billerHeader.css">
<script src="resources/js/logout.js"> </script>
</head>
<div class="menu">
    <div class="container-fluid">
		<div >
			<ul class="nav navbar-nav navbar-left" >
			<li><img alt="" src="resources/image/B1.png"></img></li>
			</ul>
		</div>
		<div>
			<ul class="nav navbar-nav navbar-right">
				<li data-toggle="modal" data-target="#delegate" id="delegateOption"><a href="#"><span class="glyphicon glyphicon-hand-right"></span>Delegate</a></li>
				<li id="billerLogout"><a href="#"><span class="glyphicon glyphicon-log-in"></span>Logout</a></li>
			</ul>
		</div>
	</div>
</div>

<jsp:include page="delegate.jsp"></jsp:include>