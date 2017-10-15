<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>


<html>
<head>

<link rel="stylesheet" href="https://opensource.keycdn.com/fontawesome/4.7.0/font-awesome.min.css" /> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />
<link rel="stylesheet" href="resources/css/style.css" />
<link rel="stylesheet" href="resources/css/animate.css" />
<link rel="stylesheet" href="resources/css/style.css">
<link rel="stylesheet" href="resources/css/billerHome.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"> </script>
<script	src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap4.min.js"> </script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js"></script>


</head>

<body>

<jsp:include page="billerLoader.jsp"></jsp:include>
<jsp:include page="billerHeader.jsp"></jsp:include>
<jsp:include page="billerAlert.jsp"></jsp:include>

<br>
<br>


<input name="strUserProfile" id="strUserProfile" type="hidden" value='${fn:escapeXml(strUserProfile)}' />

<div class="biller-home-margin">
<div class=".container-fluid">
  <div class="row biller-tab-row">
    <div class="col-md-12">      
      <div class="card biller-Home">      	
        <ul class="nav nav-pills" role="tablist">
          <li role="presentation" class="active" ><a href="#reportPanel" aria-controls="reportPanel" role="tab" data-toggle="tab"><i class="fa fa-table biller-home-tab-icon"></i><span class="biller-home-tab-icon">Data</span></a></li>
          <li role="presentation" ><a href="#upload" aria-controls="upload" role="tab" data-toggle="tab"><i class="fa fa-upload biller-home-tab-icon"></i><span class="biller-home-tab-icon">Upload</span></a></li>
          <li role="presentation" ><a href="#statusTab" aria-controls="statusTab" role="tab" data-toggle="tab"><i class="fa fa-list-ul biller-home-tab-icon"></i><span class="biller-home-tab-icon" >Status</span></a></li>
          <li role="presentation"><a href="#download" aria-controls="download" role="tab" data-toggle="tab"><i class="fa fa-download biller-home-tab-icon"></i><span class="biller-home-tab-icon">Download</span></a></li>
          <li role="presentation"><a href="#admin" aria-controls="admin" role="tab" data-toggle="tab"><i class="fa fa-user-circle-o biller-home-tab-icon"></i><span class="biller-home-tab-icon">Admin</span></a></li>
        </ul>
     
        <div class="tab-content">          
          <jsp:include page="upload.jsp"></jsp:include>
		  <jsp:include page="reportViewTab.jsp"></jsp:include>
		  <jsp:include page="statusViewTab.jsp"></jsp:include>
          <jsp:include page="download.jsp"></jsp:include>
          <jsp:include page="admin.jsp"></jsp:include>      
        </div>
      </div>
    </div>
  </div>
</div>
</div>


</body>
