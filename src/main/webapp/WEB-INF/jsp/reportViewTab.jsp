<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div role="tabpanel" class="tab-pane animated slideInUp" id="reportPanel">
	<div class="container container-table">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9">						
				<div class="panel panel-primary">
					<div class="panel-heading"> Data View Criteria </span>
					</div>
				<div class="panel-body">  
					<div class="row my-row" id="reportDataType">
						<div class="col-md-2">
							<label>
								Data Type :
							</label>
						</div>
						<div class="col-md-3 text-left">
							<div class="radio-inline">
								<label>
										<input type="radio" name="reportRadio" value="0"  checked="">
										<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
										ILC Data
								</label>
							</div>
						</div>
						<div class="col-md-3 text-left">
							<div class="radio-inline">
								<label>
										<input type="radio" name="reportRadio" value="1"  >
										<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
										SLA Data
								</label>
							</div>
						</div>						
					</div>
					<div class="row my-row">
						<div class="col-md-2 ">
							<label>
								Bill Cycle:
							</label>
						</div>
						<div class="form-group col-md-5 ">	
							<label class="my-label">
										Month:
							</label>
							<select class="selectpicker"  data-dropup-auto="false" data-width="auto" data-live-search="true" id="reportMonth">
							<c:if test="${not empty monthList}">
									<c:forEach var="o" items="${monthList}">
									 <option value="${o.monthID}"> ${o.monthDesc}</option>
									 </c:forEach>
							  </c:if>														
							</select>
							
						</div>
						<div class="form-group col-md-5 ">	
								<label class="my-label">
										Year:
								</label>
								<select class="selectpicker"  data-dropup-auto="false" data-width="auto" data-live-search="true" id="reportYear">
									<c:if test="${not empty yearList}">
										<c:forEach var="o" items="${yearList}">
									 		<option > ${o}</option>
									 </c:forEach>
							  		</c:if>
								</select>
						</div>						
					</div>
					<div class="row my-row">
						<div class="col-md-2 ">
							<label>
								Tower:
							</label>
						</div>
						<div class="form-group col-md-5 text-left">	
							<select class="selectpicker" data-dropup-auto="false" data-width="auto"  data-live-search="true" id="reportTower">
								<c:if test="${not empty towerList}">
									<c:forEach var="o" items="${towerList}">
									 <option value="${o.towerID}"> ${o.towerDesc}</option>
									 </c:forEach>
							  </c:if>
							</select>
								
							</div>
						</div>
						<div class="row my-row">
								<div class="col-md-3">										
								</div>
								<div class="col-md-6 text-center">	
									<a class="btn btn-primary btn-outline btn-block" id="reportSubmit">Submit</a>
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

<jsp:include page="reportData.jsp"></jsp:include>