
<head>
<link rel="stylesheet" href="resources/css/reportReject.css">
<script src="resources/js/reportReject.js"> </script>
</head>
<div id="rejectView" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> Rejection Criteria </h4>
				</div>
				<div class="modal-body">
					<div class="row biller-row">
							<div class="col-md-4 ">
								<label class="biller-label biller-modal-label">
									Tower :
								</label>
							</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="rejectTower">																				
									</select>
									
								</div>
					</div>					
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Reject For :
									</label>
								</div>
								<div class="form-group col-md-8 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="rejectForUser">																							
									</select>
								</div>
					</div>
			   </div>
		  	<div class="modal-footer">
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="rejectNext">Next</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="rejectClose"> Cancel</a>
			</div>
		</div>
	</div>
</div>