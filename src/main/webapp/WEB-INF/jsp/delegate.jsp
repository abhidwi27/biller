
<head>
<link rel="stylesheet" href="resources/css/reportCustom.css">
<script src="resources/js/reportCustom.js"> </script>
</head>
<div id="delegate" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> Delegate Selection </h4>
				</div>
				<div class="modal-body">
					<div class="row biller-row">
							<div class="col-md-4 ">
								<label class="biller-label biller-modal-label">
									Tower :
								</label>
							</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="delegateTower">																				
									</select>
									
								</div>
					</div>
					
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Delegate To :
									</label>
								</div>
								<div class="form-group col-md-8 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="delegateToUser">																							
									</select>
								</div>
					</div>
			</div>
		  	<div class="modal-footer">
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="delegateSubmit">Submit</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="delegateClose"> Close </a>
			</div>
		</div>
	</div>
</div>