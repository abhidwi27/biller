<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<script src="resources/js/reportView.js"> </script>
<link rel="stylesheet" href="resources/css/reportViewTab.css">
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
<script src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"> </script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.flash.min.js"> </script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script> 
</head>

<input name="currentBillCycle" id="currentBillCycle" type="hidden" value='' />
<input name="currentDtaType" id="currentDataType" type="hidden" value='' />
<input name="currentTower" id="currentTower" type="hidden" value='' />

<div role="tabpanel" class="tab-pane fade  in active" id="reportPanel">
	<div class="container container-table" id="reportSelection">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9 biller-panel">										
				<div class="panel">
					
					<div class="panel-heading"> Data View Criteria </span>
					</div>
				<div class="panel-body">					 
					<div class="row biller-row" id="reportDataType">
						<div class="col-md-3 text-left biller-panel-label">
							<label class="biller-label">
								Report Type :
							</label>
						</div>
						<div class="col-md-9">								  
								<div class="col-md-4 text-left">
									<div class="radio-inline">
										<label class="biller-radio-label">
												<input type="radio" name="reportRadio" value="0"  checked>
												<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
												ILC Report
										</label>
									</div>
								</div>
								<div class="col-md-4 text-left biller-radio-align">
									<div class="radio-inline">
										<label class="biller-radio-label">
												<input type="radio" name="reportRadio" value="1"  >
												<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
												SLA Report
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
								<select class="selectpicker"  data-dropup-auto="false" data-width="100px" data-live-search="true" id="reportMonth">
								<c:if test="${not empty monthList}">
										<c:forEach var="o" items="${monthList}">
										 <option value="${o.monthID}"> ${o.monthName}</option>
										 </c:forEach>
								  </c:if>														
								</select>								
							</div>
							<div class="form-group col-md-4 biller-year-dropdown">									
									<select class="selectpicker"  data-dropup-auto="false" data-width="100px" data-live-search="true" id="reportYear">
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
						<div class="col-md-3 text-left biller-panel-label">
							<label class="biller-label">
								Tower :
							</label>
						</div>
						<div class="form-group col-md-5 text-left biller-tower-dropdown">	
							<select class="selectpicker" data-dropup-auto="false" data-width="auto"  data-live-search="true" id="reportTower">
								<c:if test="${not empty towerList}">
									<c:forEach var="o" items="${towerList}">										
									 		<option value="${o.towerID}"> ${o.towerName}</option>
									 </c:forEach>
							  </c:if>
							</select>
								
							</div>
						</div>
						
						
						<div class="row biller-row">
						<div class="col-md-3 text-left biller-panel-label">
							<label class="biller-label">
								Account Id :
							</label>
						</div>
						<div class="form-group col-md-5 text-left biller-tower-dropdown">	
							<select class="selectpicker" data-dropup-auto="false" data-width="auto"  data-live-search="true" id="accountId">
								<option >WNPPT</option>
								<option >WCF2R</option>
							 	<option >W520O</option>
							</select>
								
							</div>
						</div>





						<div class="panel-footer">
							<div class="text-center">					
								<div class="biller-footer">	
									<a class="btn btn-primary btn-outline biller-btn" id="reportSubmit">Submit</a>
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
		<jsp:include page="reportData.jsp"></jsp:include>
</div>

