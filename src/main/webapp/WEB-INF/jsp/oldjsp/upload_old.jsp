<div id="tab1" class="tab active">				
					<div class="upload-div" id="upload-block">
								<form id="uploadForm" method="post" action="ILCApp/upload-Files" enctype="multipart/form-data">
									 <div class="upload-div-block">
												<div class="upload-element upld-label">					
													<label> Data Type </label>
												</div>
											  <div class="upload-element">
											  <label> 
												  <input type="radio" name="Report" value="ILC Report" checked>
												  <span>ILC Data </span> 
											   </label>
											  <label> 
												  <input type="radio" name="Report" value="SLA Report">
												  <span>SLA Data </span> 
											  </label>
											  <label> 
												  <input type="radio" name="Report" value="SLA Report">
												  <span>Team Data </span> 
											  </label>
											 </div>
									 </div>
									 <div class="upload-div-block">
										<div class="upload-element upld-label ">
												<label> Browse Files </label>
										</div>
										<div class="upload-element">
											 <form class="file-form">
												<input type="text" placeholder="Choose Files"/>
											 </form>
											 <input type="file" name="excelFile" id="excelFile" multiple="multiple"/>
										</div>
									 </div>
									 <div class="upload-div-block">
										<div class="upload-element upld-label">
											<label> Billing Cycle </label>
										</div>
										<div class="drop-down upload-drop-down">
											<select >
												  <option value="volvo"> Jan</option>
												  <option value="saab">Feb</option>
												  <option value="opel">March</option>
												  <option value="audi">April</option>
											</select>
										</div>
										<div class="drop-down upload-drop-down">
											<select >
												  <option value="volvo">2014</option>
												  <option value="saab">2015</option>
												  <option value="opel">2016</option>
												  <option value="audi">2017</option>
											</select>
										</div>				 
									 </div>									 
									 <div class="upload-div-block">
										<br>
										  <div class="upld-sub">						
											<input type="button" id="uploadbtn" value="Submit"></input>							
											<input type="button" id="cancelbtn" value="Cancel"></input>
										  </div>
									 </div>
								</form>
						</div>
				</div>