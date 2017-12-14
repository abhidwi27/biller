$(document).ready(function(){
	$("#downloadSubmit").click(function(){
		
		var month = $("#downloadMonth").val();
		var year = $("#downloadYear option:selected").text().trim();
		var billCycle = month+ year;
		var downloadDataType = $("#downloadDataType .radio-inline .biller-radio-label input[name='downloadRadio']:checked").val();
		var reportWeekend = $('#downloadWeekEndDate input[type="text"]').val();
		url = 'file/download.do?' + '&billCycle=' + billCycle + '&weekEnd=' + reportWeekend + '&fileType=' + downloadDataType;
		
		window.open(url);
	});
});