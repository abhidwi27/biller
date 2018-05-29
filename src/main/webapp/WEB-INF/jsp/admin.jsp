<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>

<head>
	<title>BILLER</title>
	<script src="resources/js/status.js"> </script>
	<link rel="stylesheet" href="resources/css/billerStatus.css">
	<link rel="icon" type="image/png" href="resources/image/biller-icon.ico"/>
</head>

<div role="tabpanel" class="tab-pane fade" id="admin">


 <div class="card biller-Home">      	
        <ul class="nav nav-pills" role="tablist">
          <li role="presentation" class="active" ><a href="#reportPanel" aria-controls="reportPanel" role="tab" data-toggle="tab"><i class="fa fa-users biller-home-tab-icon"></i><span class="biller-home-tab-icon">Add user</span></a></li>
          <li role="presentation" ><a href="#adminDelegate" aria-controls="adminDelegate" role="tab" data-toggle="tab"><i class="fa fa-superpowers biller-home-tab-icon"></i><span class="biller-home-tab-icon">Delegate</span></a></li>
          <li role="presentation"><a href="#download" aria-controls="download" role="tab" data-toggle="tab"><i class="fa fa-unlock biller-home-tab-icon"></i><span class="biller-home-tab-icon">Release Lock</span></a></li>
          <li role="presentation" ><a href="#statusTab" aria-controls="statusTab" role="tab" data-toggle="tab"><i class="fa fa-list-ul biller-home-tab-icon"></i><span class="biller-home-tab-icon" >TBD</span></a></li>
          <li role="presentation"><a href="#admin" aria-controls="admin" role="tab" data-toggle="tab"><i class="fa fa-user-circle-o biller-home-tab-icon"></i><span class="biller-home-tab-icon">TBD</span></a></li>
   </ul>
</div>

<div class="tab-content">          
          
          <jsp:include page="adminDelegate.jsp"></jsp:include>
          
        </div>