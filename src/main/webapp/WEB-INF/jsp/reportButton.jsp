
<head>
<script src="resources/js/reportButton.js"> </script>
<link rel="stylesheet" href="resources/css/reportButton.css">
</head>

<div id="reportButtons">
	<ul class="nav navbar-nav navbar-right">
		<li><a id="reportBack"><span class="glyphicon glyphicon-arrow-left" style="font-size:24px;color:#46b5c1;" data-toggle="tooltip" data-original-title="Back"></span></a></li>
		<li><a ><span style="font-size:24px; color:#c0c0c0;"> | </span></a></li>
		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="fa fa-filter" style="font-size:24px; color:#46b5c1;" data-toggle="tooltip" data-original-title="Select Columns"></span></a>
          <ul class="dropdown-menu">            
         </ul>
        </li>
		<li data-toggle="modal" data-target="#customize"><a ><span data-toggle="tooltip" data-original-title="Customize"><i class="fa fa-sliders" style="font-size:24px; color:#46b5c1;"></i></span></a></li>
		<!-- DO NOT REMOVE THIS COMMENTED CODE
		<li><a ><span data-toggle="tooltip" data-original-title="total hours"><i class="fa fa-plus-square-o" style="font-size:24px; color:#46b5c1;"></i></span></a></li>
		-->
		<li><a ><span style="font-size:24px; color:#c0c0c0;"> | </span></a></li>  
		<li><a ><span data-toggle="tooltip" data-original-title="Lock Data"><i class="fa fa-lock" style="font-size:26px; color:#46b5c1;"></i></span></a></li>						
		<li><a ><span data-toggle="tooltip" data-original-title="Edit"><i class="fa fa-edit" style="font-size:26px; color:#46b5c1;"></i></span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Copy"><i class="fa fa-copy" style="font-size:24px; color:#46b5c1;"></i></span></a></li>		
		<li><a ><span data-toggle="tooltip" data-original-title="Save"><i class="fa fa-save" style="font-size:24px; color:#46b5c1;"></i></span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Delete"><i class="fa fa-trash-o" style="font-size:24px; color:#46b5c1;"></i></span></a></li>
		<li><a ><span style="font-size:24px; color:#c0c0c0;"> | </span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Approve"><i class="fa fa-thumbs-o-up" style="font-size:24px;color:#46b5c1;"></i></span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Reject"><i class="fa fa-thumbs-o-down" style="font-size:24px;color:#46b5c1;"></i></span></a></li>
		<!-- DO NOT REMOVE THIS COMMENTED CODE
		<li><a ><span data-toggle="tooltip" data-original-title="Approve"><i class="fa fa-thumbs-o-up" style="font-size:24px;color:#00d535;"></i></span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Reject"><i class="fa fa-thumbs-o-down" style="font-size:24px;color:#fa032f;"></i></span></a></li>
		 -->
	</ul>
</div>

<jsp:include page="reportCustom.jsp"></jsp:include>