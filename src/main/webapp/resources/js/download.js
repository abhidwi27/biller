$(document).ready(function(){
	$("#downloadSubmit").click(function(){		
		var month = $("#downloadMonth option:selected").text().trim();
		var year = $("#downloadYear option:selected").text().trim();
		var billCycle = month+ year;
		var filename = $("#downloadFilename option:selected").text().trim();
		var downloadDataType = $("#downloadDataType .radio-inline .biller-radio-label input[name='downloadRadio']:checked").val();
		var reportWeekend = $('#downloadWeekEndDate input[type="text"]').val();
		url = 'file/download.do?' + 'month=' + month + '&year=' + year+ '&weekEnd=' + reportWeekend + '&filename=' + filename;
		
		window.open(url);
	});
	
	$(document).on('change','input[name="downloadRadio"]' ,function(){
		$('#downloadWeekEndDate').data("DateTimePicker").clear()
	});
	
	$(document).on('change','#downloadMonth' ,function(){
		$('#downloadWeekEndDate').data("DateTimePicker").clear()
	});
	
	$(document).on('change','#downloadYear' ,function(){
		$('#downloadWeekEndDate').data("DateTimePicker").clear()
	});
	
	$('#downloadWeekEndDate').on('dp.change', function (e) {
	
		var month = $("#downloadMonth option:selected").text().trim();
		var year = $("#downloadYear option:selected").text().trim();
		var downloadDataType = $("#downloadDataType .radio-inline .biller-radio-label input[name='downloadRadio']:checked").val();
		var reportWeekend = $('#downloadWeekEndDate input[type="text"]').val();
		url = 'file/filename.do?'+ 'month=' + month + '&year=' + year + '&weekEnd=' + reportWeekend + '&fileType=' + downloadDataType;
		$.ajax({
			url : url,
			data : false,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'GET',
			success : function(fileNameList) {
				fileNameList = JSON.parse(fileNameList);
				$('#downloadFilename').empty();
				if(fileNameList.length !=0){
					for ( var i=0; i< fileNameList.length; i++){
						$('#downloadFilename').append('<option>' + fileNameList[i] + '</option>');
					}
				}else{
					$('#downloadFilename').append('<option>' + "File not found" + '</option>');
				}
				 $('#downloadFilename').selectpicker('refresh');
			},
			error: function(result){
	 			location.href = 'error.do';
	 		}
		});
	});
		
	
});