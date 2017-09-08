<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div role="tabpanel" class="tab-pane fade" id="reportPanel">
	<div class="container container-table" id="reportSelection">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9 biller-panel">						
				<div class="panel panel-primary">
					<div class="panel-heading"> Data View Criteria </span>
					</div>
				<div class="panel-body">  
					<div class="row biller-row" id="reportDataType">
						<div class="col-md-3">
							<label class="biller-report-label">
								Report Type :
							</label>
						</div>
						<div class="col-md-9">
								<div class="col-md-4 text-left">
									<div class="radio-inline">
										<label>
												<input type="radio" name="reportRadio" value="0"  checked="">
												<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
												ILC Report
										</label>
									</div>
								</div>
								<div class="col-md-4 text-left">
									<div class="radio-inline">
										<label>
												<input type="radio" name="reportRadio" value="1"  >
												<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
												SLA Report
										</label>
									</div>
								</div>	
						</div>					
					</div>
					<div class="row biller-row">
						<div class="col-md-3 ">
							<label class="biller-label-bill-view">
								Bill Cycle:
							</label>
						</div>
						<div class="form-group col-md-4 biller-drop-2nd ">	
							<label class="biller-label">
										Month:
							</label>
							<select class="selectpicker"  data-dropup-auto="false" data-width="100px" data-live-search="true" id="reportMonth">
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
								<select class="selectpicker"  data-dropup-auto="false" data-width="100px" data-live-search="true" id="reportYear">
									<c:if test="${not empty yearList}">
										<c:forEach var="o" items="${yearList}">
									 		<option > ${o}</option>
									 </c:forEach>
							  		</c:if>
								</select>
						</div>						
					</div>
					<div class="row biller-row">
						<div class="col-md-3 ">
							<label class="biller-label-Tower">
								Tower:
							</label>
						</div>
						<div class="form-group col-md-5 text-left biller-drop-3rd">	
							<select class="selectpicker" data-dropup-auto="false" data-width="auto"  data-live-search="true" id="reportTower">
								<c:if test="${not empty towerList}">
									<c:forEach var="o" items="${towerList}">
									 <option value="${o.towerID}"> ${o.towerDesc}</option>
									 </c:forEach>
							  </c:if>
							</select>
								
							</div>
						</div>
						<div class="row biller-row">
								<div class="col-md-3">										
								</div>
								<div class="col-md-8">
									<div class="col-md-4 text-center">	
										<a class="btn btn-primary btn-outline btn-block btn-md" id="reportSubmit">Submit</a>
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
		<jsp:include page="reportData.jsp"></jsp:include>
</div>

