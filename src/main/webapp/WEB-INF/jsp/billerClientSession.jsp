<head>
<script src="resources/js/billerClientSession.js"> </script>
<script src="resources/js/billerKeepSessionAlive.js"> </script>
</head>

<div id="biller-session-expiry-alert" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" style="color: #FFFFFF; opacity: 1;" data-dismiss="modal">&times;</button>				
				<span class="modal-title" style="text-align: center;"> Session Time Out Alert !</span> 
			</div>
			<div class="modal-body">				
				<div class="row biller-alert-message" id="billerSessionAlertMessageText" >
					You are about to be logged out in 5 minutes. Please click 'No' to continue working.
				</div>
			</div>
		  	<div class="modal-footer">
		  		<a class="btn btn-outline biller-btn" data-dismiss="modal"  id="billerSessionSubmit"> Yes, log me out</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="billerSessionCancel"> No </a>
			</div>
		</div>
	</div>
</div>