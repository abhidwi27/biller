<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<script src="resources/js/reportBulkUpdate.js"> </script>
<link rel="stylesheet" href="resources/css/reportBulkUpdate.css">
</head>

<div id="bulkUpdate" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> Bulk Update </h4>
				</div>
				<div class="modal-body">
					<div id="bulkUpdateFirstPage">				
						<div class="row biller-row">
									<div class="col-md-4 ">
										<label class="biller-label biller-modal-label">
											Column  :
										</label>
									</div>
									<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-dropup-auto="false" data-live-search="true" id="bulkUpdateHeaderlist">
											<option > -- Select -- </option>
											<c:if test="${not empty bulkUpdateHeaderList}">
												<c:forEach var="o" items="${bulkUpdateHeaderList}">
											 		<option value="${o.headerId}"> ${o.headerDesc}</option>
											 </c:forEach>
									  		</c:if>																						
										</select>
									</div>
						</div>
						<div class="row biller-row">
									<div class="col-md-4 ">
										<label class="biller-label biller-modal-label">
											Value :
										</label>
									</div>
									<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-dropup-auto="false" data-live-search="true" id="bulkUpdateDataList">																				
										</select>
									</div>
						</div>
					</div>
					<div id="bulkUpdateSecondPage">
						Are you sure you want to update data ?
					</div>
			</div>
		  	<div class="modal-footer">
				<a class="btn btn-outline biller-btn"  id="bulkUpdateSubmit">Submit</a>
				<a class="btn btn-outline biller-btn"  id="bulkUpdateBack">Back</a>
				<a class="btn btn-outline biller-btn"  id="bulkUpdateConfirm">Yes</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="bulkUpdateClose">Close</a>
			</div>
		</div>
	</div>
</div>