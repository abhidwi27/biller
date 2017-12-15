$(document).ready(function(){
	
	var approveListlength = $('#reportApprovalList').children('option').length;
	
	if(approveListlength == 0){
		$('#approvalRadio').hide();
		$('#approvalUserBlock').hide();
		$('#approvalConfirm').show();
	}else{
		$('#approvalRadio').show();
		$('#approvalConfirm').hide();
		
		if($("#approveRadioBlock .radio-inline input:radio[name='approveForRadio']:checked").val() == 0){
			$('#approvalUserBlock').hide();	
		}
	}
	
	
	$('#approvalRadio').change(function(){
		if($("#approveRadioBlock .radio-inline input:radio[name='approveForRadio']:checked").val() == 1){
			$('#approvalUserBlock').fadeIn(500);			
		}else{
			$('#approvalUserBlock').fadeOut(500);
		}
	});
	
	$("#reportApproveSubmit").click(function() {
		var billCycle = $('#currentBillCycle').val();
		var approveFor;
		
		var userProfile = JSON.parse($('#strUserProfile').val());
		
		if(approveListlength != 0 ) {
			if($("#approveRadioBlock .radio-inline input:radio[name='approveForRadio']:checked").val() == 0){
				approveFor = userProfile.userID ;
			}else{
				approveFor = $("#reportApprovalList option:selected").val();
			}
		}else{
			approveFor =  userProfile.userID;
		}
		
		url = 'data/approve.do?billCycle=' + billCycle + '&approveFor=' + approveFor;
		$.ajax({
			url : url,
			data : false,
			dataType: false,
		    processData: false,
		    contentType: false,
			type : 'GET',
			success : function(reviewWrapper) {
				var approvalStatus = reviewWrapper["approvalStatus"];
				var reviewFlag = reviewWrapper["reviewFlag"];				
				updateStatusView(approvalStatus);
				if (reveiwFlag == 1) {
					alert("Arpproval submitted successfully");
				} else {
					alert("Error: Report could not be approved");
				}
			}
		});
	});
	
});