
$(document).ready(function(){
	
	var delegatedUser = $('#delegatedUser').val();
	
	if(delegatedUser == undefined || delegatedUser == ""){
		$("#delegateToUser").val("0");
	}else{
		$("#delegateToUser option:contains(" + delegatedUser + ")").attr('selected', 'selected');
	}
	$("#delegateToUser").selectpicker('refresh');
	 var userProfile = JSON.parse($('#strUserProfile').val());
	 
	 
	 if(userProfile.roleID == 2 || userProfile.roleID == 3 || userProfile.roleID == 8) {
		 $('#delegateOption').show();	
	 }else{
		 $('#delegateOption').hide();
	 }
	 
	$('#delegateSubmit').click(function(){
		
		$("#delegate").modal('toggle');
		var delegateID = $('#delegateToUser').val();
		var delegateStatus = $('#delegationStatus').prop('checked');
		var delegateStatusInt = 0;
		
		
		if(delegateStatus){
			delegateStatusInt = 1;
		}else{
			delegateStatusInt = 0;
		}
		url = 'data/delegate.do?delegateTo=' + delegateID + "&delegateStatus=" + delegateStatusInt;	
		$.ajax({
		    url: url,
		    dataType: false,
		    processData: false,
		    contentType: false,
		    type: 'POST',
		    success: function(result){
		    	var msg;
		    	if (result == 1){
		    		/*for( var userNo in userList){
		    		$('#reportApprovalList').html('<option value=' + "\"" + userList[userNo].userID + "\"" +  ">" + userList[userNo].name + "</option>");
		    		}*/
		    		if(delegateStatusInt == 1){
		    			$("#delegateToUser").val(delegateID);
		    		}else{
		    			$("#delegateToUser").val("0");
		    		}
		    		$("#delegateToUser").selectpicker('refresh');
		    		msg="Delegation setting successful.";
					billerAlert(msg,true,"Okay",false,"","","Notification");
		    	}else{
		    		msg="Delegation setting unsuccessful.";
					billerAlert(msg,true,"Okay",false,"","","Notification");
		    	}
		
		    },
		    error: function(result){
	 			location.href = 'error.do';
	 		}
		});
	});
});