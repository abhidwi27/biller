<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
<script src="resources/js/reportReject.js"> </script>
<link rel="stylesheet" href="resources/css/reportReject.css">
</head>
<div id="rejectView" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"> Rejection Criteria </h4>
				</div>
				<div class="modal-body">
					
					<div id="rejectFirstPage">
						<!-- -
						<div class="row biller-row">
								<div class="col-md-4 ">
									<label class="biller-label biller-modal-label">
										Tower :
									</label>
								</div>
									<div class="form-group col-md-5 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-live-search="true" id="rejectTower">																				
										</select>
										
									</div>
						</div>
						-->					
						<div class="row biller-row">
									<div class="col-md-4 ">
										<label class="biller-label biller-modal-label">
											Reject For :
										</label>
									</div>
									<div class="form-group col-md-8 text-left biller-modal-dropdown">																	
										<select class="selectpicker"  data-live-search="true" id="rejectForUser">																															
										</select>
									</div>
						</div>
					</div>
					 
					<div id="rejectSecondPage">
						<!-- 
						<div class="form-group">
							    <label for="rejectEmailTo" class="biller-label">Email address</label>
							    <input type="email" class="form-control" id="rejectEmailTo" aria-describedby="emailHelp">
							    
						</div>
						<div class="form-group">
						    <label for="rejectEmailCopiedTo" class="biller-label"> Copied To </label>
						    <input type="text" class="form-control" id="rejectEmailCopiedto" >
						</div>
						<div class="form-group">
						    <label for="rejectEmailSubject" class="biller-label"> Email Subject </label>
						    <input type="text" class="form-control" id="rejectEmailSubject" >
						</div>
					    -->
						<div class="form-group">
						    <label for="rejectEmailComments"  class="biller-label"> Comments</label>
						    <textarea class="form-control" id="rejectEmailComments"></textarea>
					    </div>
					</div>
			   </div>
		  	<div class="modal-footer">		  		 
				<a class="btn btn-outline biller-btn"  id="rejectNext">Next</a>
				<a class="btn btn-outline biller-btn"  id="rejectBack">Back</a>				
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="rejectSubmit">Submit</a>
				<a class="btn btn-outline biller-btn" data-dismiss="modal" id="rejectCancel"> Cancel</a>
			</div>
		</div>
	</div>
</div>