<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
<link rel="stylesheet" href="resources/css/bootstrap-min-3.3.7.css"> 
<link rel="stylesheet" href="resources/css/font-awesome-4.7.0.css">
<link rel="stylesheet" href="resources/css/login.css">
<link rel="stylesheet" href="resources/css/style.css">
<script src="resources/js/jquery-3.2.1-min.js"></script>
<script src="resources/js/bootstrap.min.3.3.7.js"></script>
<script src="resources/js/login.js"> </script>
<title>Biller</title>
<script type="text/javascript">
$(document).ready(function(){
	$("[name='password']").keyup(function(event) {
		    if (event.keyCode === 13) {
		        $('#loginSubmit').click();
		    }
		});
});
</script>
</head>


<body>
<div id=background>	
</div>
 <div class="container">
     <div class="biller-wrapper">
         <form method="post" id="loginForm" class="biller-signin-form">         	  
              <div class="row text-center biller-login-top">
              	<i class="fa fa-circle"></i> 
              	<h3 class="text-center">
                 Biller
                </h3>             	
              </div>              
                          
             <div class="input-group">                 
                <input type="text" class="form-control" name="userId" placeholder="Username" />
             </div>
             <div class="input-group">                 
                <input type="password" class="form-control" name="password" placeholder="Password"/>         	  
             </div>
             <a class="btn btn-primary button-outline biller-btn btn-block"  id="loginSubmit"> Sign In</a>
             <div id="loginMessage" class="biller-login-message">
             </div>                           
         </form>
      <input name="loginError" id="loginError" type="hidden" value='${fn:escapeXml(error)}' />  	
      </div>
</div>
	
			
</body>
