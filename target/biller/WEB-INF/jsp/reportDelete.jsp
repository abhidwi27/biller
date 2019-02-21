<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
<script src="resources/js/reportDelete.js"> </script>
<link rel="stylesheet" href="resources/css/reportDelete.css">
</head>
<div id="deleteView" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> SLA Data Delete </h4>
				</div>
				<div class="modal-body">
					<div class="row biller-report-Delete" id="deleteConfirm">
							Are you sure, you want to delete ?
					</div>
				</div>
			
			  	<div class="modal-footer">
					<a class="btn btn-outline biller-btn" data-dismiss="modal"  id="reportDeleteSubmit">Submit</a>
					<a class="btn btn-outline biller-btn" data-dismiss="modal" id="reportDeleteCancel">Cancel</a>
				</div>
			</div>
		</div>
</div>
