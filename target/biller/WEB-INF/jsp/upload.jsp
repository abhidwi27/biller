<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<title>BILLER</title>
<script src="resources/js/upload.js"> </script>
<script src="resources/js/moment.min.js"> </script>
<script src="resources/js/bootstrap-datetimepicker.js"> </script>
<script src="resources/js/billerDatePicker.js"> </script>
<link rel="stylesheet" href="resources/css/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="resources/css/upload.css">
<link rel="icon" type="image/png" href="resources/image/biller-icon.ico"/>
</head>

<div role="tabpanel" class="tab-pane fade" id="upload">
	<div class="container container-table">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9 biller-panel">						
				<div class="panel panel-primary">
					<div class="panel-heading"> Upload Criteria 
					</div>
				<div class="panel-body">  
					<div class="row biller-row" id="uploadDataType">
						<div class="col-md-3 text-left biller-panel-label">
							<label class="biller-label">
								Data Type :
							</label>
						</div>
						<div class="col-md-9">			
							<div class="col-md-4 text-left" >
								<div class="radio-inline">
									<label class="biller-radio-label">
											<input type="radio" name="uploadRadio" value="0" checked>
											<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
											ILC Data 
									</label>
								</div>
							</div>
							<div class="col-md-4 text-left biller-radio-align">
								<div class="radio-inline">
									<label class="biller-radio-label">
											<input type="radio" name="uploadRadio" value="1" >
											<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
											SLA Data
									</label>
								</div>
							</div>
													
						</div>
					</div>
					<div class="row biller-row">
						<div class="col-md-3 text-left biller-panel-label">
							<label class="biller-label">
								Bill Cycle :
							</label>
						</div>
						<div class="col-md-9">
						<div class="form-group col-md-4 biller-month-dropdown">							  
							  <select class="selectpicker"  data-dropup-auto="false" data-width="100px" data-live-search="true" id="uploadMonth">
							  <c:if test="${not empty monthList}">
									<c:forEach var="o" items="${monthList}">
									 <option value="${o.monthID}"> ${o.monthName}</option>
									 </c:forEach>
							  </c:if>
							  </select>
						</div>
						<div class="form-group col-md-4 biller-year-dropdown">								
								<select class="selectpicker" data-dropup-auto="false" data-width="100px" data-live-search="true" id="uploadYear">
									<c:if test="${not empty yearList}">
										<c:forEach var="o" items="${yearList}">
									 		<option > ${o}</option>
									 </c:forEach>
							  		</c:if>
								</select>							
						</div>
						</div>						
					</div>										
				    <div class="row biller-row">
				    	<div class="col-lg-3 text-left biller-panel-label">	
								<label class="biller-label">
									Weekend :
								</label>
						</div>
			            <div class="form-group col-md-8 biller-weekend-input">
			                <div class='input-group date col-xs-10' id='uploadWeekEndDate'>
			                    <input type='text' class="form-control input-sm" />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </div>
				     </div>
				     <div class="row biller-row">
							<div class="col-md-3 text-left biller-panel-label">	
								<label class="biller-label">
									Select Files :
								</label>
							</div>
							<div class="form-group col-md-8 biller-upload-input">
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
				    <div class="panel-footer">
						<div class="text-center">
								<div class="biller-footer">
									<a class="btn btn-primary btn-outline biller-btn" id="uploadSubmit">Submit</a>
								</div>
								<div class="biller-footer">
									<a class="btn btn-primary btn-outline biller-btn">Cancel</a>
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