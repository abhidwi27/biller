<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<link rel="stylesheet" href="resources/css/reportCustom.css">
<script src="resources/js/delegate.js"> </script>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
</head>

<div id="delegate" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> Delegate Selection </h4>
				</div>
				<div class="modal-body">
				  <!---
					<div class="row biller-row">
							<div class="col-md-4 ">
								<label class="biller-label biller-modal-label">
									Tower :
								</label>
							</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="delegateTower">
										<c:if test="${not empty towerList}">
										 <c:forEach var="o" items="${towerList}">
										   <option value="${o.towerID}"> ${o.towerName}</option>
										 </c:forEach>
								        </c:if>																			
									</select>									
								</div>
					</div>
					--->
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Delegation Status :
									</label>
								</div>
								<div class="form-group col-md-8 text-left">																	
									<input id="delegationStatus"  checked data-toggle="toggle" type="checkbox">
								</div>
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Delegate To :
									</label>
								</div>
								<div class="form-group col-md-8 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="delegateToUser">	
										<c:if test="${not empty delegateUserList}">
										 <c:forEach var="user" items="${delegateUserList}">
										   <option value="${user.userID}"> ${user.name}</option>
										 </c:forEach>
								        </c:if>																						
									</select>
								</div>
					</div>
					
			</div>
		  	<div class="modal-footer">		  		
				<a class="btn btn-outline biller-btn"  id="delegateSubmit">Submit</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="delegateCacel"> Cancel </a>
			</div>
		</div>
	</div>
</div>