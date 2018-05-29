<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<link rel="stylesheet" href="resources/css/reportCustom.css">
<script src="resources/js/reportCustom.js"> </script>
</head>
<div role="tabpanel" class="tab-pane fade" id="adminDelegate">
<div class="container container-table">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9 biller-panel">						
				<div class="panel panel-primary">
					<div class="panel-heading"> Delegation
					</div>
				<div class="panel-body">  
					<div class="row biller-row">
								<div class="col-md-2">
									<label class="biller-label biller-modal-label">
										Role  :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-dropup-auto="false"  data-size="7" data-live-search="true" id="key1">
											<option value="0"> -- Select -- </option>
											<c:if test="${not empty customizeList}">
												<c:forEach var="o" items="${customizeList}">
											 		<option value="${o.headerId}"> ${o.headerDesc}</option>
											 </c:forEach>
									  		</c:if>																						
										</select>
								</div>
						</div>
						<div class="row biller-row">
						<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Delegate From  :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-dropup-auto="false"  data-size="7" data-live-search="true" id="key1">
											<option value="0"> -- Select -- </option>
											<c:if test="${not empty customizeList}">
												<c:forEach var="o" items="${customizeList}">
											 		<option value="${o.headerId}"> ${o.headerDesc}</option>
											 </c:forEach>
									  		</c:if>																						
										</select>
								</div>
								<div class="col-md-1 ">
									<label class="biller-label biller-modal-label">
										
									</label>
								</div>
								<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Delegate To :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-dropup-auto="false"  data-size="7" data-live-search="true" id="key1">
											<option value="0"> -- Select -- </option>
											<c:if test="${not empty customizeList}">
												<c:forEach var="o" items="${customizeList}">
											 		<option value="${o.headerId}"> ${o.headerDesc}</option>
											 </c:forEach>
									  		</c:if>																						
										</select>
								</div>
					
					</div>
					<div class="alert alert-warning" id="customSelectionMessage" style="text-align: center; display:none;">
				<span ></span>
			</div>
		  	<div class="modal-footer">
				<a class="btn btn-outline biller-btn"  id="customSubmit">Submit</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="customClose">Close</a>
			</div>
			</div>
			</div>
</div>
</div>
</div>
</div>
