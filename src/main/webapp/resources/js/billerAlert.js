$(document).ready(function(){
	
});


function billerAlert(message, isFirstBtn, firstBtnLabel, isSecondBtn, secondBtnLabel, alertType, header){
	var selector = "#biller-alert " + ".modal-dialog";
	$(selector).find(".modal-header span").html("");
	$(selector).find(".modal-footer").html("");
	$(selector).find(".modal-body #alertMessageText").html("");
	$(selector).find(".modal-header span").append(header);
	if(isFirstBtn){	
		var firstBtnDiv = '<a class=\"btn btn-primary btn-outline biller-btn\" data-dismiss=\"modal\" id="alertFirstBtn">' + firstBtnLabel + '</a>' ;
		$(selector).find(".modal-footer").append(firstBtnDiv);
	}
	if(isSecondBtn){
		var secondBtnDiv = '<a class=\"btn btn-primary btn-outline biller-btn\" data-dismiss=\"modal\" id="alertSecondBtn">' + secondBtnLabel + '</a>' ;
		$(selector).find(".modal-footer").append(secondBtnDiv);
	}	
	$(selector).find(".modal-body #alertMessageText").append(message);
	$("#biller-alert").modal('toggle');
	
}