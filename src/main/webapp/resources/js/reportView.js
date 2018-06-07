var dataTableInitialized = false;
var editMode = [];
var hasApprovedBillCycle;
var approveListlength;
var tableHeader;
var excelFileName;
var columnLength;
var optionsToShow = [];
var optionsToHide = [];
var allHeaderOptions = [];


$(document).ready(function(){		
	
	$("#reportTower").val("0");
	$("#reportTower").selectpicker('refresh');
	
	var userProfile = JSON.parse($('#strUserProfile').val());	
	
	$('#reportDiv').hide();
	/*if( userProfile.roleID != 8){
		$("#reportTower option[value=0]").remove();
		$('#reportTower').selectpicker('refresh');
	}*/
	
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
		var accountId = $("#accountId option:selected").val();
		var accountIdStr = $("#accountId option:selected").text();
		var strExcel;
		if(reportDataType == 0){
			strExcel = 'ILC_Report_';
		}else{
			strExcel = 'SLA_Report_';
		}
		excelFileName = strExcel + accountIdStr + "_" +  month + "_" + year;
		
		$('#currentBillCycle').val(billCycle);
		$('#currentDataType').val(reportDataType);
		$('#currentTower').val(tower);
		$('#currentAccount').val(accountId);
		
		 
		 if(reportDataType == 0 || userProfile.roleID == 1){
			 $('#reportLock').hide();
			 $('#reportUnlock').hide();
			 $('#reportEdit').hide();
			 $('#reportCopy').hide();
			 $('#reportBulkUpdate').hide();
			 $('#reportDelete').hide();
			 $('#reportSave').hide();
			 $('#reportSaveSubmit').hide();
			 $('#reportReject').hide();
			 $('#reportApprove').hide();
			 $(".biller-icon-separator").hide();
		 }else{
			 $('#reportLock').show();
			 $('#reportUnlock').show();
			 $('#reportEdit').show();
			 $('#reportCopy').show();
			 $('#reportBulkUpdate').show();
			 $('#reportDelete').show();
			 $('#reportSave').show();
			 $('#reportSaveSubmit').show();
			 $('#reportReject').show();
			 $('#reportApprove').show();
			 $(".biller-icon-separator").show();
		 }
		if( userProfile.roleID != 3){
			$('#reportReject').hide();
		}else{
			$('#reportReject').show();
		}
		
		 url = 'data/read.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower  + '&accountId=' + accountId;		 
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
			    	var remarksList = responseDataEnvelope["remarksList"]; 
			    	hasApprovedBillCycle = responseDataEnvelope["hasApprovedBillCycle"];
			    	approveListlength = $('#reportApprovalList').children('option').length;
			    	var userProfile = JSON.parse($('#strUserProfile').val());
			    	
			    	if(hasApprovedBillCycle == 1){
			    		$('#reportLock').find('span i').addClass('biller-icon-disabled');
			    		$('#reportUnlock').find('span i').addClass('biller-icon-disabled');
			    	}
			    	
			    	if(tower == 0){
			    		$('#reportLock').find('span i').addClass('biller-icon-disabled');
			    		$('#reportUnlock').find('span i').addClass('biller-icon-disabled');
			    		editMode[tower] = false;
			    	}
			    	
			    	/*if(approveListlength == 0 && hasApprovedBillCycle ==1 ){
			    		$('#reportApprove').find('span i').addClass('biller-icon-disabled');
			    	}*/
			    	
			    	if(userProfile.roleID ==3 && hasApprovedBillCycle ==1 ){
			    		$('#reportReject').find('span i').addClass('biller-icon-disabled');
			    	}
			    	
			    	if(hasApprovedBillCycle == 0 && tower != 0){
			    			if(dataLockedBy!= undefined && dataLockedBy.userID == userProfile.userID){
			    				editMode[tower] =true;
			    				if($('#reportUnlock').find('span i').hasClass('biller-icon-disabled')){
									$('#reportUnlock').find('span i').removeClass('biller-icon-disabled');
					    		}
			    			}
				    	if($('#reportLock').find('span i').hasClass('biller-icon-disabled')){
							$('#reportLock').find('span i').removeClass('biller-icon-disabled');
			    		}
			    	}else{
			    		editMode[tower] = false;
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
						if($('#reportBulkUpdate').find('span i').hasClass('biller-icon-disabled')){
							$('#reportBulkUpdate').find('span i').removeClass('biller-icon-disabled');
						}
					}
			    	
			    	
	                var seqID;
	                $('#report').append("<thead>" +"<tr>" + "</tr>"  + "</thead>");
	                $('#report').append("<tbody>" + "</tbody>");
	                $('#reportButtons').find('.dropdown .dropdown-menu').empty();
	                $('#reportButtons').find('.dropdown .dropdown-menu').append('<li><a class=\"checkbox\"><input type=\"checkbox\" checked class=\"styled\" onclick=\"modifyAllColumns(this)\"/>  <label > </label></input>' + 'Select All' + '</a></li>');
	                $('#reportButtons').find('.dropdown .dropdown-menu').append('<li role=\"separator\" class=\"divider\"></li>');
	                tableHeader = tableJson["tableHeader"];
				    for(var i=0 ; i< tableHeader.length; i++){	
				    	if(i==0){
				    		$('#report').find("thead tr").append("<th>" + '<a class=\"checkbox\"><input type=\"checkbox\" class=\"styled\" onclick=\"SelectAllRecords(this)\" />  <label > </label></input>' + "</th>");	
				    	}else{
	                	$('#report').find("thead tr").append("<th>" + tableHeader[i] + "</th>"); 
				        }
				    	if(i != 0){
	                	$('#reportButtons').find('.dropdown .dropdown-menu').append('<li><a class=\"checkbox\"><input type=\"checkbox\" value=\"'+ i +' \"checked class=\"styled\"/>  <label > </label></input>' + tableHeader[i] + '</a></li>');
				    	}
				    }
				    $('#reportButtons').find('.dropdown .dropdown-menu').append('<li><a><button type=\"button\" class=\"btn btn-primary btn-outline btn-block btn-md\" id=\"columnSelectOk\" onclick=\"customizeColumns()\"> Done </button><a><li>');
				    
				    var tot = [];
				    if(reportDataType == 0){
					    for(rowNo=0; rowNo<tableJson["tableBody"].length; rowNo++){		                	
		                	var newRowData = [];		                	
		                	newRowData.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');		    
							    for (var prop in tableJson["tableBody"][rowNo]){							    		
							    		newRowData.push(tableJson["tableBody"][rowNo][prop]);							    	
								 }
							    tot[rowNo] = newRowData;						   
						}
				    }
				    if(reportDataType == 1){
				    	for(rowNo=0; rowNo<tableJson["tableBody"].length; rowNo++){	
					    	var seqID = tableJson["tableBody"][rowNo].seqID;
						    newRowID = "row-" + seqID;			    
						    delete tableJson["tableBody"][rowNo].seqID;
						    delete tableJson["tableBody"][rowNo].active;
				    	
					     var newRowData = [];
	                	 var checkBoxStr = '<div class=\"checkbox\"> <input id=\"' + newRowID + '\" type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>';
						 newRowData.push(checkBoxStr);			    
					    for (var prop in tableJson["tableBody"][rowNo]){					    	
					    		newRowData.push(tableJson["tableBody"][rowNo][prop]);					    	
						 }
					    	tot[rowNo] = newRowData;
				    	}
				    }
				    
				    var reportTable = $('#report').DataTable({
				    			dom:		 'Blfrtip',
				    			buttons:      [{
								                extend: 'excelHtml5',
								                text:   '<i class="fa fa-file-excel-o" style="font-size:20px;color:#6666b2;"></i>',
								                filename: excelFileName
								              },
							                  ],
						        aoColumnDefs: [{ 
						        				 "bVisible": true, 
						        				 "aTargets": ['_all'] 
							            		},{ 
							            		   "bVisible": false, 
							            		   "aTargets": ['_all'] 
							            		}	            	
							            	   ],						    
							    language:     {
							    				"decimal": ",",
							    				"thousands": "."
			    							   },
				    			data:           tot,
				    			deferRender:    true,
				    	        scrollX: 		true,
				    	        scrollY:        380,
				    	        scrollCollapse: true,
				    	        scroller:       true,
				    	        searching: 		true,
				    	        searchDelay: 500
				    		});
				    reportTable.buttons().container().appendTo( '#report_wrapper .col-sm-6:eq(0)' );
				    dataTableInitialized = true;
				    
				    if (reportDataType == 0){
						columnLength = 43;
					} else{
						columnLength = 31;
					}
					
					for (var i=0; i<columnLength; i++){
						allColumns.push(i);
					}
					resetCustomizeModal();
				    /*addListContent('#customEmp', employeeList);
	                addListContent('#customWr', wrList);
	                addListContent('#customWeekend', weekEndList);
	                addListContent('#customRemarks', remarksList);
	                addListContent('#rejectForUser', rejectForUserList);*/	                

	            },
		 		error: function(result){
		 			location.href = 'error.do';
		 		},
	            complete: function(){			
	   			 $("#report_wrapper .dt-buttons button").css("margin-right", "1em");
				 $("#report_wrapper .dataTables_scrollHead table").css("margin-left", "2px");
				 $(".biller-loader-div").fadeOut("slow");

	            }
		 
			});		 
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