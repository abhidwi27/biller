$(document).ready(function(){
	$('#billerLogout').click(function(){
		
		location.href= 'logout.do';
		/*$.ajax({
			url : 'logout.do',
			type : 'GET',
			dataType : false,
			success : function(results) {
				alert("You have been logged out");

			}
		});*/
		
	});
});