<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<script src="resources/js/reportApprove.js"> </script>
<link rel="stylesheet" href="resources/css/reportApprove.css">
</head>
<div id="approveView" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> SLA Data Approval </h4>
				</div>
				<div class="modal-body">
					<div class="row biller-report-Approve" id="approvalConfirm">
							Are you sure, you want to approve ?
					</div>
					<div class="row biller-row" id="approvalRadio">
						<div class="col-md-4">
							<label class="biller-label biller-modal-label">
								Approve For :
							</label>
						</div>
						<div class="col-md-8"  id="approveRadioBlock">			
							<div class="row text-left biller-approve-row">
								<div class="radio-inline">
									<label class="biller-radio-label">
											<input type="radio" name="approvedForRadio" value="0" checked>
											<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
											Self 
									</label>
								</div>
							</div>
							<div class="row text-left biller-approve-row">
								<div class="radio-inline">
									<label class="biller-radio-label">
											<input type="radio" name="approveForRadio" value="1" >
											<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
											Other
									</label>
								</div>
							</div>
													
						</div>
					</div>
					<div class="row biller-row" id="approvalUserBlock">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">											
											Select Manager: 
									</label>									
								</div>
								<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
									<select class="selectpicker"  data-live-search="true" id="reportApprovalList">
									<c:if test="${not empty delegateByUserList}">
										<c:forEach var="o" items="${delegateByUserList}">
									 		<option value="${o.userID}"> ${o.name}</option>
									 </c:forEach>
							  		</c:if>																							
									</select>
								</div>
					</div>
					
			</div>
		  	<div class="modal-footer">
				<a class="btn btn-outline biller-btn" data-dismiss="modal"  id="reportApproveSubmit">Submit</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="reportApproveCancel">Cancel</a>
			</div>
		</div>
	</div>
</div>