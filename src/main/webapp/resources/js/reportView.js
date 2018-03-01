var dataTableInitialized = false;
var editMode;
var hasApprovedBillCycle;
var approveListlength;

$(document).ready(function(){		
		
	var userProfile = JSON.parse($('#strUserProfile').val());	
	
	$('#reportDiv').hide();
	
	$("#reportSubmit").click(function(){
		$(".biller-loader-div").fadeIn(1);		
		$("#reportSelection").hide();
		$("#reportDiv").show();
		
		if ( dataTableInitialized ) {
			  $('#report').DataTable().destroy();
			  $('#report').empty();
			  dataTableInitialized = false;
		}
		var url;		
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var billCycle = month+ year;
		
		$('#currentBillCycle').val(billCycle);
		$('#currentDataType').val(reportDataType);
		$('#currentTower').val(tower);
		
		var settings = {   		
				    		"scrollX": true,
				            "aoColumnDefs": [
				            	{ "bVisible": true, "aTargets": ['_all'] },
				            	{ "bVisible": false, "aTargets": ['_all'] }	            	
				            	],
				            	"iDisplayLength": 10
				            	
	    				}
		 
		 if(reportDataType == 0 || userProfile.roleID == 1){
			 $('#reportLock').hide();
			 $('#reportEdit').hide();
			 $('#reportCopy').hide();
			 $('#reportDelete').hide();
			 $('#reportSave').hide();
			 $('#reportSaveSubmit').hide();
			 $('#reportReject').hide();
			 $('#reportApprove').hide();
		 }else{
			 $('#reportLock').show();
			 $('#reportEdit').show();
			 $('#reportCopy').show();
			 $('#reportDelete').show();
			 $('#reportSave').show();
			 $('#reportSaveSubmit').show();
			 $('#reportReject').show();
			 $('#reportApprove').show();
		 }
		if( userProfile.roleID != 3){
			$('#reportReject').hide();
		}else{
			$('#reportReject').show();
		}
		 
		 url = 'data/read.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower;		 
		 $.ajax({
			    url: url,
			    dataType: false,
			    processData: false,
			    contentType: false,
			    type: 'GET',
			    success: function(responseDataEnvelope){	
			    	var tableJson = responseDataEnvelope["tableData"];
			    	var employeeList = responseDataEnvelope["employeeList"];
			    	var weekEndList = responseDataEnvelope["weekEndList"];
			    	var wrList = responseDataEnvelope["wrList"];
			    	var rejectForUserList = responseDataEnvelope["rejectForUserList"];
			    	var dataLockedBy = responseDataEnvelope["dataLockedBy"];
			    	hasApprovedBillCycle = responseDataEnvelope["hasApprovedBillCycle"];
			    	approveListlength = $('#reportApprovalList').children('option').length;
			    	var userProfile = JSON.parse($('#strUserProfile').val());
			    	
			    	if(hasApprovedBillCycle == 1){
			    		$('#reportLock').find('span i').addClass('biller-icon-disabled');
			    	}
			    	
			    	if(approveListlength == 0 && hasApprovedBillCycle ==1 ){
			    		$('#reportApprove').find('span i').addClass('biller-icon-disabled');
			    	}
			    	
			    	if(hasApprovedBillCycle != 0 && dataLockedBy != null && dataLockedBy.userID == userProfile.userID ){
			    		editMode = true;
			    	}else{
			    		editMode = false;
			    	}
			    	
			    	if (!editMode){
						$('#reportEdit').find('span i').addClass('biller-icon-disabled');
						$('#reportCopy').find('span i').addClass('biller-icon-disabled');
						$('#reportSave').find('span i').addClass('biller-icon-disabled');
						$('#reportDelete').find('span i').addClass('biller-icon-disabled');
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
					}
			    	
			    	
	                var seqID;
	                $('#report').append("<thead>" +"<tr>" + "</tr>"  + "</thead>");
	                $('#report').append("<tbody>" + "</tbody>");
	                $('#reportButtons').find('.dropdown .dropdown-menu').empty();
	                $('#reportButtons').find('.dropdown .dropdown-menu').append('<li><a class=\"checkbox\"><input type=\"checkbox\" checked class=\"styled\" onclick=\"modifyAllColumns(this)\"/>  <label > </label></input>' + 'Select All' + '</a></li>');
	                $('#reportButtons').find('.dropdown .dropdown-menu').append('<li role=\"separator\" class=\"divider\"></li>');
				    for(var i=0 ; i< tableJson["tableHeader"].length; i++){	
				    	if(i==0){
				    		$('#report').find("thead tr").append("<th>" + '<a class=\"checkbox\"><input type=\"checkbox\" class=\"styled\" onclick=\"SelectAllRecords(this)\" />  <label > </label></input>' + "</th>");	
				    	}else{
	                	$('#report').find("thead tr").append("<th>" + tableJson["tableHeader"][i] + "</th>"); 
				        }
	                	$('#reportButtons').find('.dropdown .dropdown-menu').append('<li><a class=\"checkbox\"><input type=\"checkbox\" value=\"'+ i +' \"checked class=\"styled\"/>  <label > </label></input>' + tableJson["tableHeader"][i] + '</a></li>');
	                }
				    $('#reportButtons').find('.dropdown .dropdown-menu').append('<li><a><button type=\"button\" class=\"btn btn-primary btn-outline btn-block btn-md\" id=\"columnSelectOk\" onclick=\"customizeColumns()\"> Done </button><a><li>');
				    var reportTable = $('#report').DataTable(settings);
				    dataTableInitialized = true;
				    var rowNo;
	                for(rowNo=0; rowNo<tableJson["tableBody"].length; rowNo++){	
	                	if (reportDataType != 0){			    		
	                		seqID = tableJson["tableBody"][rowNo].seqID;
						    newRowID = "row-" + seqID;			    
						    delete tableJson["tableBody"][rowNo].seqID;
	                	}
	                	var newRowData = [];
	                	
						 newRowData.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');			    
						    for (var prop in tableJson["tableBody"][rowNo]){
						    	if(prop != "active"){
						    		newRowData.push(tableJson["tableBody"][rowNo][prop]);
						    	}
							 }
					   var temp = $("#report").dataTable().fnAddData(newRowData);
					    if (!(reportDataType == 0)){
					    	var newRow = $('#report').dataTable().fnGetNodes(temp);
					    		$(newRow).attr('id', newRowID);
					    }
					}
	                
	                addListContent('#customEmp', employeeList);
	                addListContent('#customWr', wrList);
	                addListContent('#customWeekend', weekEndList);  
	                addListContent('#rejectForUser', rejectForUserList);
	                
	            }
		 
			});
		 $(".biller-loader-div").fadeOut("slow");
	});
});

function addListContent(element, content){
	
	$(element).empty();
	if(element != '#rejectForUser' ){
	$(element).append('<option>' + 'ALL' + '</option>');
	}
    for (var i=0 ; i< content.length ; i++){
    	if(element == '#rejectForUser' ){
    		$(element).append('<option value=' + content[i]["userID"] + '>' + content[i]["name"] +  '</option>'); 		 
    	}else{
    		$(element).append('<option>' + content[i] + '</option>');
    	}
 	  }
 	  $(element).selectpicker('refresh');
}