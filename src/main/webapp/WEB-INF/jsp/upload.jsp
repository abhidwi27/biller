<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div role="tabpanel" class="tab-pane fade in active" id="home">
	<div class="container container-table">
		<div class="row vertical-center-row">
			<div class="text-center col-md-1">					
			</div>
			<div class="text-center col-md-9">						
				<div class="panel panel-primary">
					<div class="panel-heading"> Upload Criteria </span>
					</div>
				<div class="panel-body">  
					<div class="row my-row">
						<div class="col-md-2">
							<label>
								Data Type :
							</label>
						</div>
						<div class="col-md-3 text-left">
							<div class="radio-inline">
								<label>
										<input type="radio" name="o3" value="" checked>
										<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
										ILC Data 
								</label>
							</div>
						</div>
						<div class="col-md-3 text-left">
							<div class="radio-inline">
								<label>
										<input type="radio" name="o3" value="" checked>
										<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
										SLA Data
								</label>
							</div>
						</div>
						<div class="col-md-3 text-left">
							<div class="radio-inline">
								<label>
										<input type="radio" name="o3" value="" checked>
										<span class="cr"><i class="cr-icon fa fa-circle"></i></span>
										Team Data
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
							  <select class="selectpicker"  data-dropup-auto="false" data-width="auto" data-live-search="true" id="uploadMonth">
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
								<select class="selectpicker" data-dropup-auto="false" data-width="auto" data-live-search="true" id="uploadYear">
									<c:if test="${not empty yearList}">
										<c:forEach var="o" items="${yearList}">
									 		<option > ${o}</option>
									 </c:forEach>
							  		</c:if>
								</select>
							
						</div>
						
					</div>
					<div class="row my-row">
							<div class="col-md-2">	
								<label>
									Select Files :
								</label>
							</div>
							<div class="form-group col-md-9 float-right">
								<input type="file" name="img[]" class="file">
								<div class="input-group col-xs-10">
									  <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i>
									  </span>
									  <input type="text" class="form-control input-lg" disabled placeholder="Upload Files">
									  <span class="input-group-btn">
										<button class="browse btn btn-primary btn-outline input-lg" type="button"><i class="glyphicon glyphicon-search"></i> Browse</button>
									  </span>
								</div>
							 </div>
					</div>
					<div class="row my-row">
							<div class="col-md-3">										
							</div>
							<div class="col-md-6 text-center">	
								<a class="btn btn-primary btn-outline btn-block" id="uploadSubmit">Submit</a>
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