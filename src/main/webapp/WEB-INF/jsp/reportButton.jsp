
<head>
<script src="resources/js/reportButton.js"> </script>
<link rel="stylesheet" href="resources/css/reportButton.css">
</head>

<div id="reportButtons">
	
	<ul class="nav navbar-nav navbar-right">
		<li data-toggle="tooltip" data-original-title="Back"><a id="reportBack"><span class="glyphicon glyphicon-arrow-left report-button-icon"  ></span></a></li>
		<li><a ><span class="report-button-icon"> | </span></a></li>
		<li class="dropdown" data-toggle="tooltip" data-original-title="Select Columns">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="fa fa-filter report-button-icon" ></span></a>
          <ul class="dropdown-menu">            
         </ul>
        </li>
		<li data-toggle="tooltip" data-original-title="Customize"><a data-toggle="modal" data-target="#customize"><span ><i class="fa fa-sliders report-button-icon" ></i></span></a></li>
		<!-- DO NOT REMOVE THIS COMMENTED CODE
		-->
		<li data-toggle="tooltip" data-original-title="Total Hours"><a id="reportTotHrs"><span ><i class="fa fa-plus-square-o report-button-icon" ></i></span></a></li>
		
		<li><a ><span class="report-button-icon"> | </span></a></li>  
		<li data-toggle="tooltip" data-original-title="Lock Data"><a id="reportLock"><span ><i class="fa fa-lock report-button-icon" ></i></span></a></li>						
		<li data-toggle="tooltip" data-original-title="Edit"><a id="reportEdit"><span ><i class="fa fa-edit report-button-icon"></i></span></a></li>
		<li data-toggle="tooltip" data-original-title="Copy"><a id="reportCopy"><span ><i class="fa fa-copy report-button-icon"></i></span></a></li>		
		<li data-toggle="tooltip" data-original-title="Save"><a id="reportSave"><span ><i class="fa fa-save report-button-icon"></i></span></a></li>
		<li data-toggle="tooltip" data-original-title="Delete"><a id="reportDelete"><span ><i class="fa fa-trash-o report-button-icon"></i></span></a></li>
		<li data-toggle="tooltip" data-original-title="Bulk Update"><a id="reportBulkUpdate"><span ><i class="fa fa-th report-button-icon"></i></span></a></li>
		<li><a ><span class="report-button-icon"> | </span></a></li>
		<li data-toggle="tooltip" data-original-title="Approve"><a id="reportApprove" ><span ><i class="fa fa-thumbs-o-up report-button-icon"></i></span></a></li>
		<li data-toggle="tooltip" data-original-title="Reject"><a id="reportReject" data-toggle="modal" ><span ><i class="fa fa-thumbs-o-down report-button-icon"></i></span></a></li>
		
		<!-- DO NOT REMOVE THIS COMMENTED CODE
		<li><a ><span data-toggle="tooltip" data-original-title="Approve"><i class="fa fa-thumbs-o-up" style="font-size:24px;color:#00d535;"></i></span></a></li>
		<li><a ><span data-toggle="tooltip" data-original-title="Reject"><i class="fa fa-thumbs-o-down" style="font-size:24px;color:#fa032f;"></i></span></a></li>
		 -->
	</ul>
</div>

<jsp:include page="reportCustom.jsp"></jsp:include>
<jsp:include page="reportReject.jsp"></jsp:include>
<jsp:include page="reportApprove.jsp"></jsp:include>
<jsp:include page="reportBulkUpdate.jsp"></jsp:include>
<jsp:include page="reportDelete.jsp"></jsp:include>