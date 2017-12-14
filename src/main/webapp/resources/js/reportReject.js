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
	
	
	$("#rejectSubmit").click(function() {
		var billCycle = $('#currentBillCycle').val();
		var rejectedFor = $("#rejectForUser option:selected").val();
		
		/*var emailTo = $('#rejectEmailTo').val();
		var emailCc = $('#rejectEmailCopiedto').val();
		var emailSubject = $('#rejectEmailSubject').val();*/
		
		var rejectComments = $('#rejectEmailComments').val();
		//var rejectObject = new Array();
		
		/*emailObject.append("emailTo", emailTo);
		emailObject.append("emailCc", emailCc);
		emailObject.append("emailSubject", emailSubject);*/
		
		//rejectObject.push("rejectComments", rejectComments);
		var rejectObj = "{" + "\"rejectComments\" : " + "\"" + rejectComments + "\"" + "}";
		url = 'data/reject.do?billCycle=' + billCycle + '&rejectedFor=' + rejectedFor;
		$.ajax({
			url : url,
			data : rejectObj,
			dataType: false,
		    processData: false,
			contentType : 'application/json',
			type : 'POST',
			success : function(reviewWrapper) {
				var approvalStatus = reviewWrapper["approvalStatus"];
				var reviewFlag = reviewWrapper["reviewFlag"];
				updateStatusView(approvalStatus);
				if (reviewFlag == 1) {
					alert("Rejection submitted successfully");		
				}else{
					alert("Error occured while rejecting");
				}
			}
		});

	});
	
	
	
	
});