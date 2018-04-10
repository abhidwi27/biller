
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
					<!--  
					<div class="row biller-row">
							<div class="col-md-4 ">
								<label class="biller-label biller-modal-label">
									App Area:
								</label>
							</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="customTeam">																				
									</select>
									
								</div>
					</div>
					-->
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Billable  :
									</label>
								</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="customBillable">
										<option value="0"> ALL </option>
										<option value="1"> Yes </option>
										<option value="2"> No </option>																							
									</select>
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Work Request :
									</label>
								</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="customWr">																				
									</select>
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Weekend :
									</label>
								</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="customWeekend">																							
									</select>
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Employee Name :
									</label>
								</div>
								<div class="form-group col-md-8 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="customEmp">																							
									</select>
								</div>
					</div>
					
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Remarks :
									</label>
								</div>
								<div class="form-group col-md-8 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="customRemarks">																							
									</select>
								</div>
					</div>
			</div>
		  	<div class="modal-footer">
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="customSubmit">Submit</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="customClose">Close</a>
			</div>
		</div>
	</div>
</div>