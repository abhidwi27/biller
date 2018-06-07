<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>


<head>
<link rel="stylesheet" href="resources/css/delegate.css">
<script src="resources/js/delegate.js"> </script>
<link href="resources/css/bootstrap-toggle-min-2.2.3.css" rel="stylesheet">
<script src="resources/js/bootstrap-toggle-min-2.2.3.js"></script>
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
					<input name="strDelegatedUser" id="delegatedUser" type="hidden" value='${fn:escapeXml(delegateStatus)}' />
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Delegation :
									</label>
								</div>
								<c:choose>
									<c:when test="${not empty delegateStatus}">
										<div class="form-group col-md-8 text-left">																	
											<input id="delegationStatus" checked data-toggle="toggle"  type="checkbox">
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group col-md-8 text-left">																	
											<input id="delegationStatus" data-toggle="toggle"  type="checkbox">
										</div>
									</c:otherwise>
								</c:choose>
								
					</div>
					<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Delegate To :
									</label>
								</div>
								<div class="form-group col-md-8 text-left biller-modal-dropdown">	
								<select class="selectpicker"  data-live-search="true" id="delegateToUser">																
								     <option value="0"> -- Select -- </option>							
										<c:if test="${not empty delegateUserList}">
										<c:forEach var="user"	 items="${delegateUserList}">
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