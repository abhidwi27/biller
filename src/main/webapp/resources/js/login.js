$(document).ready(function(){
	
	$("#loginSubmit").click(function(){
		$("#loginMessage").html("");		
		var userNameLength = $("input[name=userId]").val().length;
		var passwordLength = $("input[name=password]").val().length;		
		if( userNameLength == 0 || passwordLength == 0 ){
			$("#loginMessage").html("Please enter user name and password.")
			$("#loginMessage").show();
		}else{
			$("#loginForm").submit();
		}	
	});
	

	if($("#loginError").val().length != 0){
		$("#loginMessage").html("");
		$("#loginMessage").html($("#loginError").val());
		$("#loginMessage").show();
		
	}
	
	
});