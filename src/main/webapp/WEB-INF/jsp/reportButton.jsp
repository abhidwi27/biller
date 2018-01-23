
<head>
<script src="resources/js/reportButton.js"> </script>
<link rel="stylesheet" href="resources/css/reportButton.css">
</head>

<div id="reportButtons">
	
	<ul class="nav navbar-nav navbar-right">
		<li><a id="reportBack"><span class="glyphicon glyphicon-arrow-left report-button-icon"  data-toggle="tooltip" data-original-title="Back"></span></a></li>
		<li><a ><span class="report-button-icon"> | </span></a></li>
		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="fa fa-filter report-button-icon" data-toggle="tooltip" data-original-title="Select Columns"></span></a>
          <ul class="dropdown-menu">            
         </ul>
        </li>
		<li data-toggle="modal" data-target="#customize"><a ><span data-toggle="tooltip" data-original-title="Customize"><i class="fa fa-sliders report-button-icon" ></i></span></a></li>
		<!-- DO NOT REMOVE THIS COMMENTED CODE
		-->
		<li><a id="reportTotHrs"><span data-toggle="tooltip" data-original-title="Total Hours"><i class="fa fa-plus-square-o report-button-icon" ></i></span></a></li>
		
		<li><a ><span class="report-button-icon"> | </span></a></li>  
		<li><a id="reportLock"><span data-toggle="tooltip" data-original-title="Lock Data"><i class="fa fa-lock report-button-icon" ></i></span></a></li>						
		<li><a id="reportEdit"><span data-toggle="tooltip" data-original-title="Edit"><i class="fa fa-edit report-button-icon"></i></span></a></li>
		<li><a id="reportCopy"><span data-toggle="tooltip" data-original-title="Copy"><i class="fa fa-copy report-button-icon"></i></span></a></li>		
		<li><a id="reportSave"><span data-toggle="tooltip" data-original-title="Save"><i class="fa fa-save report-button-icon"></i></span></a></li>
		<li><a id="reportDelete"><span data-toggle="tooltip" data-original-title="Delete"><i class="fa fa-trash-o report-button-icon"></i></span></a></li>
		<li><a ><span class="report-button-icon"> | </span></a></li>
		<li data-toggle="modal" data-target="#approveView"><a id="reportApprove"><span data-toggle="tooltip" data-original-title="Approve"><i class="fa fa-thumbs-o-up report-button-icon"></i></span></a></li>
		<li data-toggle="modal" data-target="#rejectView"><a id="reportReject"><span data-toggle="tooltip" data-original-title="Reject"><i class="fa fa-thumbs-o-down report-button-icon"></i></span></a></li>
		
		<!-- DO NOT REMOVE THIS COMMENTED CODE
		<li><a ><span data-toggle="tooltip" data-original-title="Approve"><i class="fa fa-thumbs-o-up" style="font-size:24px;color:#00d535;"></i></span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Reject"><i class="fa fa-thumbs-o-down" style="font-size:24px;color:#fa032f;"></i></span></a></li>
		 -->
	</ul>
</div>

<jsp:include page="reportCustom.jsp"></jsp:include>
<jsp:include page="reportReject.jsp"></jsp:include>
<jsp:include page="reportApprove.jsp"></jsp:include>