<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<link rel="stylesheet" href="resources/css/reportCustom.css">
<script src="resources/js/reportCustom.js"> </script>
</head>
<div id="customize" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: center;"> Customize Report </h4>
				</div>
				<div class="modal-body">
					<div class="row biller-row">
								<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Key  :
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
										Value :
									</label>
								</div>
								<div class="form-group col-md-4 text-left biller-modal-dropdown">																	
									<select  multiple="multiple" id="value1">
										</select>									
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Key  :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-dropup-auto="false" data-size="7" data-live-search="true" id="key2">
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
										Value :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select multiple="multiple" id="value2">																							
									</select>									
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Key  :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-dropup-auto="false" data-size="7" data-live-search="true" id="key3">
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
										Value :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select multiple="multiple" id="value3">																							
									</select>
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Key  :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-dropup-auto="false" data-size="7" data-live-search="true" id="key4">
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
										Value :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select multiple="multiple" id="value4">																							
									</select>
								</div>
					</div>
					
					<div class="row biller-row">
								<div class="col-md-2 ">
									<label class="biller-label biller-modal-label">
										Key  :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-dropup-auto="false" data-size="7" data-live-search="true" id="key5">
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
										Value :
									</label>
								</div>
								<div class="form-group col-md-3 text-left biller-modal-dropdown">																	
									<select multiple="multiple"  id="value5">																							
									</select>
								</div>
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
