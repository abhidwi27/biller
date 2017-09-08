<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<script src="resources/js/upload.js"> </script>
<script src="resources/js/moment.min.js"> </script>
<script src="resources/js/bootstrap-datetimepicker.js"> </script>
<script src="resources/js/billerDatePicker.js"> </script>
<link rel="stylesheet" href="resources/css/bootstrap-datetimepicker.css">
</head>

<div role="tabpanel" class="tab-pane fade in active" id="upload">
	<div class="container container-table">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9 biller-panel">						
				<div class="panel panel-primary">
					<div class="panel-heading"> Upload Criteria </span>
					</div>
				<div class="panel-body">  
					<div class="row biller-row">
						<div class="col-md-3 biller-label-data">
							<label>
								Data Type :
							</label>
						</div>
						<div class="col-lg-9">
							<div class="col-lg-3 text-left" id="uploadDataType">
								<div class="radio-inline">
									<label>
											<input type="radio" name="uploadRadio" value="0" checked="">
											<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
											ILC Data 
									</label>
								</div>
							</div>
							<div class="col-lg-3 text-left">
								<div class="radio-inline">
									<label>
											<input type="radio" name="uploadRadio" value="1" >
											<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
											SLA Data
									</label>
								</div>
							</div>						
						</div>
					</div>
					<div class="row biller-row">
						<div class="col-md-3 ">
							<label class="biller-label-bill">
								Bill Cycle:
							</label>
						</div>
						<div class="form-group col-md-4 biller-drop-1st ">
							  <label class="biller-label">
										Month:
							  </label>
							  <select class="selectpicker"  data-dropup-auto="false" data-width="100px" data-live-search="true" id="uploadMonth">
							  <c:if test="${not empty monthList}">
									<c:forEach var="o" items="${monthList}">
									 <option value="${o.monthID}"> ${o.monthDesc}</option>
									 </c:forEach>
							  </c:if>
							  </select>
						</div>
						<div class="form-group col-md-3 ">
								<label class="biller-label">
											Year:
								</label>
								<select class="selectpicker" data-dropup-auto="false" data-width="100px" data-live-search="true" id="uploadYear">
									<c:if test="${not empty yearList}">
										<c:forEach var="o" items="${yearList}">
									 		<option > ${o}</option>
									 </c:forEach>
							  		</c:if>
								</select>
							
						</div>
						
					</div>
					<div class="row biller-row">
							<div class="col-lg-3 text-left">	
								<label class="biller-label-file">
									Select Files :
								</label>
							</div>
							<div class="form-group col-sm-8 biller-upld-box">
								<input type="file" name="uploadfile" id="uploadFile" multiple="multiple" class="file">
								<div class="input-group col-xs-10">
									  <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i>
									  </span>
									  <input id="uploadText" type="text" class="form-control input-sm"  placeholder="Upload Files">
									  <span class="input-group-btn">
										<button class="browse btn btn-primary btn-outline input-sm" type="button" id="uploadBrowse"><i class="glyphicon glyphicon-search"></i> Browse</button>
									  </span>
								</div>
							 </div>
					</div>
					
				    <div class="row biller-row">
				    	<div class="col-lg-3 text-left">	
								<label class="biller-weekend-label">
									Weekend:
								</label>
						</div>
			            <div class="form-group col-sm-8 biller-weekend-box">
			                <div class='input-group date col-xs-10' id='reportWeekEndDate'>
			                    <input type='text' class="form-control input-sm" />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </div>
				     </div>

					<div class="row biller-row">
							<div class="col-md-3">										
							</div>
							<div class="col-md-8">
								<div class="col-md-4 text-center">	
									<a class="btn btn-primary btn-outline btn-block btn-md" id="uploadSubmit">Submit</a>
								</div>
								<div class="col-md-4 text-center">	
									<a class="btn btn-primary btn-outline btn-block btn-md">Cancel</a>
								</div>
							</div>
					</div>
				</div>
				</div>
			</div>
			<div class="text-center col-md-2">					
			</div>
			
		</div>
	</div>
</div>