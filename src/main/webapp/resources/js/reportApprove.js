$(document).ready(function(){
	
	var approveListlength = $('#reportApprovalList').children('option').length;
	
	$('#reportApprove').click(function(e){
		if(approveListlength == 0 && hasApprovedBillCycle == 1){
			e.stopPropagation();
			e.preventDefault();
			return;
		}else{
			$("#approveView").modal('toggle');
		}
	});
	
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
		
		$(".biller-loader-div").fadeIn(1);
		
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
				if (reviewFlag == 1) {
					billerAlert("Approval submitted successfully",true, 'Okay', false, '','', "Alert !");
					if(approveFor ==  userProfile.userID){
						hasApprovedBillCycle == 1;
						editMode = false;
						//$('#reportLock').find('span i').addClass('biller-icon-disabled');
					}
					
				}else if (reviewFlag == 0){
					billerAlert("Error: Your have already approved report for selected bill cycle.",true, 'Okay', false, '','', "Alert !");
					
				}else{
					billerAlert("Error: report could not be approved.",true, 'Okay', false, '','', "Alert !");
					
				}
			}
		});
		
		$(".biller-loader-div").fadeOut("slow");
	});
	
});