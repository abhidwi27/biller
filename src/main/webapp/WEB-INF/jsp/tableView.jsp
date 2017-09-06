<div id="tab2" class="tab">
			<div class="upload-div" id="upload-block">
							<form id="uploadForm" method="post" action="ILCApp/upload-Files" enctype="multipart/form-data">
								<div class="upload-div-block">
										<div class="upload-element upld-label">					
											<label> Data Type </label>
										</div>
							 
									  <div id="tblDataType" class="upload-element">
									  <label> 
										  <input type="radio" name="Report" value="0" checked>
										  <span>ILC Report </span> 
									   </label>
									  <label> 
										  <input type="radio" name="Report" value="1">
										  <span>SLA Report </span> 
									  </label>
									   <br>
									 </div>
								</div>
									<div class="upload-div-block">
										<div class="upload-element upld-label">
											<label> Billing Cycle </label>
										</div>
										<div class="drop-down upload-drop-down">
											<select id="tblMonth">
												  <option value="01"> Jan</option>
												  <option value="02">Feb</option>
												  <option value="03">March</option>
												  <option value="04">April</option>
												  <option value="05">May</option>
												  <option value="06">Jun</option>
											</select>
										</div>
										<div class="drop-down upload-drop-down">
											<select  id="tblYear">
												  <option value="2014">2014</option>
												  <option value="2015">2015</option>
												  <option value="2016">2016</option>
												  <option value="2017">2017</option>
											</select>
										</div>	
							 		</div>
								
								<div class="upload-div-block">
										<div class="upload-element tower-label">
											<label> Tower </label>
										</div>
										<div >
											<select id="tblTower" class="drop-down tower-drop-down">
												<option value="1"> Select Tower</option>									
												<option value="2">Feb</option>
												<option value="3">March</option>
												<option value="4">April</option>
											</select>
										</div>
								</div>
								<br>
								<br>
								<div class="upload-div-block">					
										<div class="upld-sub">						
											<input type="button" id="tablebtn" value="Submit"></input>							
											<input type="button" id="resetbtn" value="Reset"></input>
										 </div>
								</div>
							 
							 
						</form>
			</div>
		</div>