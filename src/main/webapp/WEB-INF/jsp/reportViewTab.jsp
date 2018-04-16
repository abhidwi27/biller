<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<title>BILLER</title>
<script src="resources/js/reportView.js"> </script>
<link rel="stylesheet" href="resources/css/reportViewTab.css">
<link rel="stylesheet" href="resources/css/buttons.dataTables.min.css">
<link rel="stylesheet" href="resources/css/scroller.dataTables.min.css">
<script src="resources/js/dataTables.buttons.min.js"> </script>
<script src="resources/js/buttons.flash.min.js"> </script>

<script src="resources/js/jszip.min.js"></script>
<script src="resources/js/pdfmake.min.js"></script>
<script src="resources/js/vfs_fonts.js"></script>

<script src="resources/js/buttons.html5.min.js"></script>
<script src="resources/js/buttons.print.min.js"></script> 
<script src="resources/js/dataTables.scroller.min.js"></script>
<script src="resources/js/scrolling.js"></script>
<link rel="icon" type="image/png" href="resources/image/biller-icon.ico"/>
</head>

<input name="currentBillCycle" id="currentBillCycle" type="hidden" value='' />
<input name="currentDtaType" id="currentDataType" type="hidden" value='' />
<input name="currentTower" id="currentTower" type="hidden" value='' />
<input name="currentAccount" id="currentAccount" type="hidden" value='' />

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
								<c:if test="${not empty accountList}">
									<c:forEach var="o" items="${accountList}">										
									 		<option value="${o.accountId}"> ${o.accountDesc}</option>
									 </c:forEach>
							  	</c:if>
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

