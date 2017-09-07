<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/progress_bar.css" />"
	rel="stylesheet">
<title>Welcome to BILLER</title>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script>
	$(document).ready(
			function() {

				$('#uploadImg').click(function() {
					$("#upload-block").slideDown("slow");
					//$(".upload-div").css('display','block');

				});

				$("#uploadbtn").click(
						function() {
							$(".upload-div").fadeOut("slow");
							var oMyForm = new FormData();
							/* 
							var billCycleType = "0";
							var billCycle = "0507"; 
							*/
							for (i = 0; i < excelFile.files.length; i++) {
								oMyForm.append("file", excelFile.files[i]);
							}
							$.ajax({
								url : '/biller/pmo/upload.do',
								data : oMyForm,
								type : 'POST',
								processData : false,
								contentType : false,
								success : function(data) {

								}
							});
							
							<!--							
							$.ajax({
								url : '/invoice/pmo/generate.do?billCycle='
										+ billCycle + "&billCycleType="
										+ billCycleType,
								dataType : false,
								processData : false,
								contentType : false,
								type : 'GET',
								success : function(data) {
									alert(data);
								}
							});
							-->

						});

				$("#generateBox").click(
						function() {
							alert("generate report called...");
							$.ajax({
								url : '/invoice/pmo/generate.do?billCycle='
										+ billCycle + "&billCycleType="
										+ billCycleType,
								processData : false,
								contentType : false,
								type : 'GET',
								success : function(data) {
									alert(data);
								}
							});

							<!--
							$.ajax({
								url : '/invoice/pmo/generate.do/',
								dataType : false,
								processData : false,
								contentType : false,
								type : 'POST',
								success : function(data) {
									$('#result').html(data);
								}
							});
							-->
							$('#result').html(
									"ILC Report generated successfully");
						});

			});
</script>
</head>
<body>
	<div class="topnav">
		<div class="nav-text">
			<h3>BILLER</h3>
		</div>
		<div class="nav-image">
			<img src="/resources/image/user.png"></img>
			<div id="usersetting" class="nav-dropdown">
				<ul>
					<li><a href=#> Logout </a></li>
				</ul>
			</div>
		</div>

	</div>


	<div class="progress">
		<div class="circle done">
			<span class="label">&#10004</span> <span class="title">DM</span>
		</div>
		<span class="bar done"></span>
		<div class="circle done">
			<span class="label">&#10004</span> <span class="title">BAM</span>
		</div>
		<span class="bar half"></span>
		<div class="circle active">
			<span class="label">>></span> <span class="title">SR.BAM</span>
		</div>
		<span class="bar"></span>
		<div class="circle">
			<span class="label">O</span> <span class="title">PMO</span>
		</div>
		<span class="bar"></span>
		<div class="circle">
			<span class="label">O</span> <span class="title">Finish</span>
		</div>
	</div>


	<div class="homeImage">
		<div class="imgContainer" id="uploadImg">
			<img src="<c:url value="/resources/image/upload.png"/>" />
			<!-- 	<img id="img1"  src="/resources/image/upload.png"/>		 -->
			<label class="icon-label"> Upload Files </label>
		</div>
		<div class="imgContainer" id="generateBox">
			<img src="<c:url value="/resources/image/generateReport.png"/>" />
			<!--	<img src="/resources/image/generateReport.png"/>		-->
			<label class="icon-label"> Generate Report </label>
		</div>
		<div class="imgContainer">
			<img src="<c:url value="/resources/image/viewReport.png"/>" />
			<!-- 	<img src="/resources/image/viewReport.png">		-->
			<label class="icon-label"> View Report </label>
		</div>
		<div class="imgContainer">
			<img src="<c:url value="/resources/image/addUser.png"/>" />
			<!-- 	<img src="resources/image/addUser.png">		-->
			<label class="icon-label"> Add User </label>
		</div>
	</div>


	<br>
	<br>
	<!-- div class="upload-div">
			<form id="uploadForm" method="post" action="ILCApp/upload-Files" enctype="multipart/form-data">
			 <div>                
                 <input type="file" name="excelFile" id="excelFile" multiple="multiple"/>
             </div>
             
             <div class="upload-btn-wrapper">
				   <input id="uploadbtn" type="button" value="Upload Files">
				  
			 </div>
             <div id="buttons">                
                
             </div>
             
             <div class="drop-down">
             	<label> Billing Cycle </label>
             	<div>
             	<select>  </select>
             	</div>
             	<div>
             	<select> </select>
             	</div>
             	
             
             </div>
             </form>
             

</div-->

	<div class="message" id="result"></div>

	<div class="upload-div" id="upload-block">
		<form id="uploadForm" method="post" action="/invoice/pmo/upload.do/"
			enctype="multipart/form-data">
			<div class="upload-div-block">
				<div class="upload-element upld-label">
					<label> Report Type </label>
				</div>

				<div class="upload-element">
					<label> <input type="radio" name="Report"
						value="ILC Report" checked> <span>ILC Report </span>
					</label> <label> <input type="radio" name="Report"
						value="SLA Report"> <span>SLA Report </span>
					</label>
				</div>
			</div>
			<div class="upload-div-block">
				<div class="upload-element upld-label ">
					<label> Browse Files </label>
				</div>
				<div class="upload-element">
					<input type="file" name="excelFile" id="excelFile"
						multiple="multiple" />
				</div>
			</div>


			<div class="upload-div-block">
				<div class="upload-element upld-label">
					<label> Billing Cycle </label>
				</div>
				<div class="upload-drop-down">
					<select>
						<option value="volvo">Jan</option>
						<option value="saab">Feb</option>
						<option value="opel">March</option>
						<option value="audi">April</option>
					</select>
				</div>
				<div class="upload-drop-down">
					<select>
						<option value="volvo">2014</option>
						<option value="saab">2015</option>
						<option value="opel">2016</option>
						<option value="audi">2017</option>
					</select>
				</div>
			</div>

			<div class="upload-div-block">

				<div class="upload-element upld-sub">
					<input type="button" id="uploadbtn" value="Submit"> </input>
				</div>

			</div>

		</form>


	</div>

	<div class="footer">
		<div class="footer-text">IBM Internal</div>

	</div>
</body>
</html>