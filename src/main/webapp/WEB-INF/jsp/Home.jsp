<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet"	href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap4.min.css">
	<link rel="stylesheet" href="resources/css/style.css">
	<link rel="stylesheet" href="resources/css/pmo.css">
	<link rel="stylesheet" href="resources/css/progress_bar.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"> </script>
	<script	src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap4.min.js"> </script>
	<script  src="resources/js/home.js"> </script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.js"></script>

	<script>
		$(window).load(function() {
			
			$(".se-pre-con").fadeOut("slow");
		});
	</script>
</head>

<body>
	<div class="loader-div">
		<div class="loader">
		</div>
	</div>
		

	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	
	<input name="strUserProfile" id="strUserProfile" type="hidden" value='${fn:escapeXml(strUserProfile)}' />
	
	
	<div id="tabView" class="tabs">
		<ul class="tab-links">
			<li class="active" id="UploadTab"><a href='#tab1' >UPLOAD</a></li>
			<li><a href='#tab2'>VIEW</a></li>
			<li><a href='#tab3'>STATUS</a></li>
		</ul>
		<div class="tab-content">
				<jsp:include page="/WEB-INF/jsp/upload.jsp"></jsp:include>
				<jsp:include page="/WEB-INF/jsp/tableView.jsp"></jsp:include>
				<jsp:include page="/WEB-INF/jsp/status.jsp"></jsp:include>       
 		</div>
	</div>

<br>
<br>

	<div id="tableView"  style="display: none;">
		<jsp:include page="/WEB-INF/jsp/tableButton.jsp"></jsp:include> 
		<jsp:include page="/WEB-INF/jsp/tableCustomize.jsp"></jsp:include> 	
		
		<br>
		<br>
		<div >
			<table id="ilcReport" class="table table-striped table-bordered myclass" cellspacing="0" width="10%">
				<thead>
					<tr>				
					</tr>
				</thead>
				<tbody>			
				</tbody>			
			</table>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include> 
	
</body>
