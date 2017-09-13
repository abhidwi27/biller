
<head>
<script src="resources/js/reportButton.js"> </script>
<link rel="stylesheet" href="resources/css/reportButton.css">
</head>

<div id="reportButtons">
	<ul class="nav navbar-nav navbar-right">
		<li><a id="reportBack"><span class="glyphicon glyphicon-arrow-left" style="font-size:20px;" data-toggle="tooltip" data-original-title="Back"></span></a></li>
		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-th-list" style="font-size:25px;"data-toggle="tooltip" data-original-title="Select Columns"></span></a>
          <ul class="dropdown-menu">
            
         </ul>
        </li>
		<li><a ><span class="glyphicon glyphicon-plus-sign" style="font-size:25px;" data-toggle="tooltip" data-original-title="total hours"></span></a></li>
		<li data-toggle="modal" data-target="#customize"><a href="#"><span class="glyphicon glyphicon-wrench" style="font-size:25px;" data-toggle="tooltip" data-original-title="Customize"></span></a></li>   
		<li><a ><span class="glyphicon glyphicon-lock" style="font-size:25px;" data-toggle="tooltip" data-original-title="Lock Data"></span></a></li>						
		<li><a ><span class="glyphicon glyphicon-edit" style="font-size:25px;" data-toggle="tooltip" data-original-title="Edit"></span></a></li>
		<li><a ><span class="glyphicon glyphicon-retweet" style="font-size:25px;" data-toggle="tooltip" data-original-title="Copy"></span></a></li>
		<li><a ><span class="glyphicon glyphicon-trash" style="font-size:25px;" data-toggle="tooltip" data-original-title="Delete"></span></a></li>
		<li><a ><span class="glyphicon glyphicon-floppy-save" style="font-size:25px;" data-toggle="tooltip" data-original-title="Save"></span></a></li>
		<li><a ><span class="glyphicon glyphicon-remove" style="font-size:25px;" data-toggle="tooltip" data-original-title="Reject"></span></a></li>
		<li><a ><span class="glyphicon glyphicon-ok" style="font-size:25px;" data-toggle="tooltip" data-original-title="Approve"></span></a></li>
	</ul>
</div>

<jsp:include page="reportCustom.jsp"></jsp:include>