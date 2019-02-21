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
	
	$('#reportReject').click(function(e){
		var userProfile = JSON.parse($('#strUserProfile').val());
		if(userProfile.roleID == 3 && hasApprovedBillCycle == 1){
			e.stopPropagation();
			e.preventDefault();
			return;
		}else{
			$("#rejectView").modal('toggle');
		}
	});
	
	$("#rejectSubmit").click(function() {
		var billCycle = $('#currentBillCycle').val();
		var rejectedFor = $("#rejectForUser option:selected").val();		
		var rejectComments = $('#rejectEmailComments').val();
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
					var msg = "Rejection submitted successfully";	
					billerAlert(msg,true, 'Okay', false, '','', "Alert !");
				}else{
					var msg = "Error occured while rejecting";
					billerAlert(msg,true, 'Okay', false, '','', "Alert !");
				}
			},
			error: function(result){
	 			location.href = 'error.do';
	 		}
		});

	});
	
	
	
	
});