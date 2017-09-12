
<head>
<link rel="stylesheet" href="resources/css/reportCustom.css">
<script src="resources/js/reportCustom.js"> </script>
</head>
<div id="customize" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> Customize Report </h4>
				</div>
				<div class="modal-body">
					<div class="row biller-row">
							<div class="col-md-4 ">
								<label class="biller-customReport-label">
									Team Name:
								</label>
							</div>
								<div class="form-group col-md-5 text-left">																	
									<select class="selectpicker"  data-live-search="true" id="customTeam">
																				
									</select>
									
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-customReport-label">
										Work Request :
									</label>
								</div>
								<div class="form-group col-md-5 text-left">																	
									<select class="selectpicker"  data-live-search="true" id="customWr">
																				
									</select>
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-customReport-label">
										Weekend :
									</label>
								</div>
								<div class="form-group col-md-5 text-left">																	
									<select class="selectpicker"  data-live-search="true" id="customWeekend">
																							
									</select>
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-customReport-label">
										Employee Name :
									</label>
								</div>
								<div class="form-group col-md-8 text-left">																	
									<select class="selectpicker"  data-live-search="true" id="customEmp">
																							
									</select>
								</div>
					</div>
			</div>
		  	<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="customSubmit">Submit</button>
				<button type="button" class="btn btn-default" data-dismiss="modal" id="customClose">Close</button>
			</div>
		</div>
	</div>
</div>