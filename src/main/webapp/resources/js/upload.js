var overrideSLA = false;

$(document).ready(function(){	
		
		var userProfile = JSON.parse($('#strUserProfile').val());
		
		if(userProfile.roleID == 8){
			$('#uploadTab').show();
			$('#adminTab').show();
			
		}else{
			$('#uploadTab').hide();
			$('#adminTab').hide();
		}
		$('#uploadBrowse').click(function(){
		  var file = $(this).parent().parent().parent().find('.file');
		  file.trigger('click');
		});
		
		
		$('#uploadFile').change(function(){
			var fileName = [];
			
			for (var i=0; i<uploadFile.files.length ; i++){
				fileName.push(uploadFile.files[i].name);
			}
			
			$('#uploadText').val(fileName);
		});

		

		$("#uploadSubmit").click(function(){
		var uploadDataType = $("#uploadDataType .radio-inline input:radio[name='uploadRadio']:checked").val();
	    var month = $("#uploadMonth").val();
		var year = $("#uploadYear option:selected").text().trim();
		var billCycle = month+ year;
		var reportWeekend = $('#uploadWeekEndDate input[type="text"]').val();
		var oMyForm = new FormData();		
		var allFiles = ['ITWR for ILC.xlsx', 'Team data.xlsx', 'WI Vs ASM.xlsx', 'WNPPT.xlsx', 'CSDB Base DATA.xlsx'];
		var missingFile = [];
		var fileName = [];
		for( i=0 ; i<uploadFile.files.length ; i++){				
			oMyForm.append("file", uploadFile.files[i]);
		}
		for (var i=0; i<uploadFile.files.length ; i++){
			fileName.push(uploadFile.files[i].name);
		}
		missingFiles = $(allFiles).not(fileName).get();
		
		if(missingFiles.length > 0){
			var msg =  missingFiles.join(",") + " not selected for uploading. Please select all required files."
			billerAlert(msg,true, 'Okay', false, '','', "Alert!");
			return;
			
		}
		$(".biller-loader-div").fadeIn(1);
		
		$.ajax({
			    url: 'file/upload.do?dataType=' + uploadDataType + '&billCycle=' + billCycle + '&reportWeekend=' + reportWeekend + '&override=' + overrideSLA,
			    data: oMyForm,
			    processData: false,
			    contentType: false,
			    type: 'POST',
			    success: function(msg){
			    	if(msg == "false"){
			    		var overrideConfirm = "SLA Data for selected bill cycle already exists, If you click yes, it will override existing data.\n"			    								+ "Do you still want to continue ?";
			    		billerAlert(overrideConfirm,true, 'Yes', true, 'No','', "Alert!", 'overrideSlaConfirm', '');
			    	}else{
				    	billerAlert(msg,true, 'Okay', false, '','', "Alert!");
				    	overrideSLA = false;
			    	}
			    },
			    error: function(result){
		 			location.href = 'error.do';
		 		},
			    complete: function(result){
			    	$(".biller-loader-div").fadeOut("slow");
			    }
		});
		 
	});	
		
	$(document).on("click", "#overrideSlaConfirm", function(){
		if($("#biller-alert").is(':visible')){
			$("#biller-alert").modal('toggle');
		}
		overrideSLA = true;
		$("#uploadSubmit").click();
	});	
});