$(function(){
	$("#reportBack").click(function(){
		$('#reportDiv').hide();
		$('#reportSelection').fadeIn("slow");
		$('#report').DataTable().destroy();
		$('#report').empty();
		dataTableInitialized = false;
	});
});