
<head>
<script src="resources/js/reportButton.js"> </script>
<link rel="stylesheet" href="resources/css/reportButton.css">
</head>

<div id="reportButtons">
	<ul class="nav navbar-nav navbar-right">
		<li><a id="reportBack"><span class="glyphicon glyphicon-arrow-left"></span> Back</a></li>
		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Select Columns<span class="caret"></span></a>
          <ul class="dropdown-menu">
            
         </ul>
        </li>
		<li><a id="reportTotHrs"><span class="glyphicon glyphicon-plus-sign"></span> Total Hours </a></li>
		<li data-toggle="modal" data-target="#customize"><a href="#"><span class="glyphicon glyphicon-wrench"></span> Customize</a></li>   
		<li><a id="reportLock"><span class="glyphicon glyphicon-lock"></span> Lock Data</a></li>						
		<li><a id="reportEdit"><span class="glyphicon glyphicon-edit"></span> Edit</a></li>
		<li><a id="reportCopy"><span class="glyphicon glyphicon-retweet"></span> Copy</a></li>
		<li><a id="reportDelete"><span class="glyphicon glyphicon-trash"></span> Delete</a></li>
		<li><a id="reportSave"><span class="glyphicon glyphicon-floppy-save"></span> Save</a></li>
		<li><a id="reportSaveSubmit"><span class="glyphicon glyphicon-floppy-saved"></span> Save & Submit</a></li>
		<li><a id="reportReject"><span class="glyphicon glyphicon-remove"></span> Reject</a></li>
		<li><a id="reportApprove"><span class="glyphicon glyphicon-ok" ></span>Approve</a></li>
	</ul>
</div>

<jsp:include page="reportCustom.jsp"></jsp:include>