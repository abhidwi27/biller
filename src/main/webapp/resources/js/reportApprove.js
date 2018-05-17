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
	
	$('#reportApprove').click(function(e){
		if(approveListlength == 0 && hasApprovedBillCycle == 1){
			e.stopPropagation();
			e.preventDefault();
			return;
		}else{
			$("#approveView").modal('toggle');
		}
	});	
	
	
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
		var tower = $('#currentTower').val();
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
						hasApprovedBillCycle = 1;
						editMode[tower] = false;
						$('#reportLock').find('span i').addClass('biller-icon-disabled');
						$('#reportUnlock').find('span i').addClass('biller-icon-disabled');
					}					
				}else if (reviewFlag == 0){
					billerAlert("Error: Your have already approved report for selected bill cycle.",true, 'Okay', false, '','', "Alert !");
					
				}else{
					billerAlert("Error: report could not be approved.",true, 'Okay', false, '','', "Alert !");					
				}
				if (!editMode[tower]){					
					$('#reportEdit').find('span i').addClass('biller-icon-disabled');
					$('#reportCopy').find('span i').addClass('biller-icon-disabled');
					$('#reportSave').find('span i').addClass('biller-icon-disabled');
					$('#reportDelete').find('span i').addClass('biller-icon-disabled');
					$('#reportBulkUpdate').find('span i').addClass('biller-icon-disabled');
				}else{
					if($('#reportEdit').find('span i').hasClass('biller-icon-disabled')){
						$('#reportEdit').find('span i').removeClass('biller-icon-disabled');
					}
					if($('#reportCopy').find('span i').hasClass('biller-icon-disabled')){
						$('#reportCopy').find('span i').removeClass('biller-icon-disabled');
					}
					if($('#reportSave').find('span i').hasClass('biller-icon-disabled')){
						$('#reportSave').find('span i').removeClass('biller-icon-disabled');
					}
					if($('#reportDelete').find('span i').hasClass('biller-icon-disabled')){
						$('#reportDelete').find('span i').removeClass('biller-icon-disabled');
					}
					if($('#reportLock').find('span i').hasClass('biller-icon-disabled')){
						$('#reportLock').find('span i').removeClass('biller-icon-disabled');
		    		}
					if($('#reportUnlock').find('span i').hasClass('biller-icon-disabled')){
						$('#reportUnLock').find('span i').removeClass('biller-icon-disabled');
		    		}
					if($('#reportBulkUpdate').find('span i').hasClass('biller-icon-disabled')){
						$('#reportBulkUpdate').find('span i').removeClass('biller-icon-disabled');
		    		}
				}
			},
			error: function(result){
	 			location.href = 'error.do';
	 		}
		});		
		$(".biller-loader-div").fadeOut("slow");
	});
	
});