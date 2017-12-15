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
		var uploadDataType = $("#uploadDataType .radio-inline input:radio[name='uploadRadio']:checked").val().trim();
	    var month = $("#uploadMonth").val();
		var year = $("#uploadYear option:selected").text().trim();
		var billCycle = month+ year;
		var reportWeekend = $('#uploadWeekEndDate input[type="text"]').val();
		var oMyForm = new FormData();
		 
		for( i=0 ; i<uploadFile.files.length ; i++){				
			oMyForm.append("file", uploadFile.files[i]);
		}
		
		
		$.ajax({
			    url: 'file/upload.do?dataType=' + uploadDataType + '&billCycle=' + billCycle + '&reportWeekend=' + reportWeekend,
			    data: oMyForm,
			    processData: false,
			    contentType: false,
			    type: 'POST',
			    success: function(data){
			    }
		});
		 
	});	
});