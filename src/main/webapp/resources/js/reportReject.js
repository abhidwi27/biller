$(document).ready(function(){
	
	
	
	$("#rejectFirstPage").show();
	$("#rejectSecondPage").hide();
	$("#rejectBack").hide();
	$("#rejectSubmit").hide();
	
	$("#rejectNext").click(function(){
		$("#rejectFirstPage").hide();
		$("#rejectSecondPage").fadeIn();		
		$("#rejectNext").hide();
		$("#rejectBack").show();
		$("#rejectSubmit").show();
	});
	
	$("#rejectBack").click(function(){
		$("#rejectSecondPage").hide();
		$("#rejectFirstPage").fadeIn();
		$("#rejectBack").hide();
		$("#rejectSubmit").hide();
		$("#rejectNext").show();
		
	});
	
	$("#rejectSubmit").click(function(){
		
	});

	
	
	
	
});