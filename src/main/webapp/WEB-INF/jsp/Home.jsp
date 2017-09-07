<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML>
<html>
<head>

<link rel="stylesheet" href="https://opensource.keycdn.com/fontawesome/4.7.0/font-awesome.min.css" /> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />
<link rel="stylesheet" href="resources/css/sweetalert.css">
<link rel="stylesheet" href="resources/css/style.css" />
<link rel="stylesheet" href="resources/css/animate.css" />
<link rel="stylesheet" href="resources/css/style.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"> </script>
<script	src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap4.min.js"> </script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js"></script>
<script src="resources/js/home.js"> </script>
<script src="resources/js/sweetalert.min.js"> </script>

</head>

<body>

<jsp:include page="billerLoader.jsp"></jsp:include>

<br>
<br>
<br>


<input name="strUserProfile" id="strUserProfile" type="hidden" value='${fn:escapeXml(strUserProfile)}' />

<div class=".container-fluid">
  <div class="row">
    <div class="col-md-12">      
      <div class="card">
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active" id="myhome"><a href="#home" aria-controls="home" role="tab" data-toggle="tab"><i class="fa fa-upload "></i>  <span>Upload</span></a></li>
          <li role="presentation"><a href="#reportPanel" aria-controls="reportPanel" role="tab" data-toggle="tab"><i class="fa fa-tablet"></i>  <span>View</span></a></li>
          <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab"><i class="fa fa-tasks"></i>  <span>Status</span></a></li>
          <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab"><i class="fa fa-cog"></i>  <span>Settings</span></a></li>
          <li role="presentation"><a href="#extra" aria-controls="settings" role="tab" data-toggle="tab"><i class="fa fa-plus-square-o"></i>  <span>extra</span></a></li>
        </ul>
        
     
        <div class="tab-content">
          
          <jsp:include page="upload.jsp"></jsp:include>
		  <jsp:include page="reportViewTab.jsp"></jsp:include>
          
          <div role="tabpanel" class="tab-pane fade" id="messages">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</div>
          <div role="tabpanel" class="tab-pane fade" id="settings">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passage..</div>
          <div role="tabpanel" class="tab-pane fade" id="extra">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passage..</div>
        </div>
      </div>
    </div>
  </div>
</div>


</body>
</html>