$(document).ready(function(){
		
	$('#customSubmit').click(function(){
		
		$(".biller-loader-div").fadeIn(1);
		
		var team = $("#customTeam option:selected").text().trim();
		var wr = $("#customWr option:selected").text().trim();
		var weekend = $("#customWeekend option:selected").text().trim();
		var emp = $("#customEmp option:selected").text().trim();
		var billCycle = $('#currentBillCycle').val();
		var dataType = $('#currentDataType').val();
		var tower = $('#currentTower').val();
		var billable = $("#customBillable option:selected").val();
		var remarks = $("#customRemarks option:selected").val();
		var accountId = $('#currentAccount').val(); 
		var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var url;
		var dataFilter = {};
		var strExcel;
		if(dataType == 0){
			strExcel = 'Custom_ILC_Report_';
		}else{
			strExcel = 'Custom_SLA_Report_';
		}
		excelFileName = strExcel + month + "_" + year;
		
		dataFilter["dataType"] = dataType;
		dataFilter["billCycle"] = billCycle;
		dataFilter["towerID"]= tower;
		dataFilter["wrNo"]= wr;
		dataFilter["weekEndDate"]= weekend;
		dataFilter["empName"]= emp;
		dataFilter["billable"]= billable;
		dataFilter["remarks"]= remarks;
		dataFilter["accountId"]= accountId;
		
		var dataFilter = JSON.stringify(dataFilter);
		
		setTimeout(function(){
		if ( dataTableInitialized ){ 	
			$('#report').DataTable().destroy();
			$('#report tbody').empty();
			dataTableInitialized = false;
		}
		
			url = 'data/readCustom.do';
			 $.ajax({
				    url: url,
				    data: dataFilter,
				    contentType: 'application/json',
				    processData:false,
				    type: 'POST',
				    success: function(tableBody){
				    	var rowNo;	                
				    	var customData=[];
				    	if(dataType == 0){
						    for(rowNo=0; rowNo<tableBody.length; rowNo++){		                	
			                	var newRowData = [];		                	
			                	newRowData.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');		    
								    for (var prop in tableBody[rowNo]){							    		
								    		newRowData.push(tableBody[rowNo][prop]);							    	
									 }
								    customData[rowNo] = newRowData;						   
							}
					    }
				    	if(dataType == 1){
					    	for(rowNo=0; rowNo<tableBody.length; rowNo++){	
						    	var seqID = tableBody[rowNo].seqID;
							    newRowID = "row-" + seqID;			    
							    delete tableBody[rowNo].seqID;
							    delete tableBody[rowNo].active;					    	
							    var newRowData = [];
			                	var checkBoxStr = '<div class=\"checkbox\"> <input id=\"' + 
			                	 	newRowID +'\"type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>';
			                	newRowData.push(checkBoxStr);			    
							    for (var prop in tableBody[rowNo]){					    	
							    		newRowData.push(tableBody[rowNo][prop]);					    	
								 }
							    customData[rowNo] = newRowData;
					    	}
					    }
		                $("#report").DataTable({
		                	dom: 			'Blfrtip',
			    			buttons: 		[{
								                extend: 'excelHtml5',
								                text:   '<i class="fa fa-file-excel-o" style="font-size:20px;color:#6666b2;"></i>',
								                filename: excelFileName
							             	},
							             	],
					        aoColumnDefs: [{ 
					        					"bVisible": true, "aTargets": ['_all'] 
					        				 },{ 
					        					 "bVisible": false, "aTargets": ['_all'] }	            	
					        				 ],
					    
					        language: 		{
												"decimal": ",",
												"thousands": "."
		    								},
			    			
		    			    data:           customData,
		    	            deferRender:    true,
		    	            scrollX: 		true,
		    	            scrollY:        380,
		    	            scrollCollapse: true,
		    	            scroller:       true,
			    	     });
		                dataTableInitialized = true;
				    }
			 		
				  });  	  
			 		  
			 }, 50);
		setTimeout(function(){			
			 $("#report_wrapper .dt-buttons button").css("margin-right", "1em");
			 $("#report_wrapper .dataTables_scrollHead table").css("margin-left", "2px");
			 $(".biller-loader-div").fadeOut("slow");
		 }, 1500);
		
	});
	
});