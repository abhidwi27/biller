<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/login.css">
<link rel="stylesheet" href="resources/css/style.css">
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="resources/js/login.js"> </script>
<title>Biller</title>
</head>


<body>
	
 <div class="container">
     <div class="biller-wrapper">
         <form  method="post" id="loginForm" class="biller-signin-form">         	  
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
             <a class="btn btn-primary button-outline biller-btn btn-block"  id="loginSubmit"> Log In</a> 
          </form>	
      </div>
</div>	
			
</body>
