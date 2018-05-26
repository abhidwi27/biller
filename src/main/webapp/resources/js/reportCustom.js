$(document).ready(function(){
	
	

	$('#key1').find("option").each(function(){
		allHeaderOptions.push($(this).val());
	});
	
	$(document).on('change', "#customize select", function(){
		$('#customSelectionMessage').hide();
		var key = $(this).attr('id');
		var keyIndex = parseInt(key.charAt(3));
		
		optionsToHide = [];
		for(var i=1; i<keyIndex; i++ ){
			var keySelector = "#key" + i;
			if($(keySelector).val() != "0"){
				optionsToHide.push($(keySelector).val());
			}
		}
		
		for(var i=keyIndex; i >=1 ; i--){
			var keySelector = "#key" + i;
			if( $(keySelector).val() == "0"){
				
				for(var c=i; c <=5 ; c++){
					var resetKeySelector = "#key" + c;
					$(resetKeySelector).val("0");
					$(resetKeySelector).selectpicker('refresh');
					var valueSelector = "#value" + c;
					$(valueSelector).empty();
					$(valueSelector).multiselect('destroy');
					$(valueSelector).multiselect({
						buttonWidth: '220px',
					});
				}

				optionsToShow = [];
				optionsToShow = $(allHeaderOptions).not(optionsToHide).get();
				for(var l= i ; l<=5 ; l++){
					var keySelector = "#key" + l;
						for(var k=0; k< optionsToShow.length ; k++){
							var optionSelector = "option[value=" + optionsToShow[k] + "]";
							$(keySelector).find(optionSelector).show();
						}
						for(var k=0; k< optionsToHide.length ; k++){
							var optionSelector = "option[value=" + optionsToHide[k] + "]";
							$(keySelector).find(optionSelector).hide();
						}
						$(keySelector).val("0");
						$(keySelector).selectpicker('refresh');
						
				}
				if( i < keyIndex){
					var msg = "No column name selected for filter " + i + " Please select column name.";
					$('#customSelectionMessage').html();
					$('#customSelectionMessage').html(msg);
					$('#customSelectionMessage').show();					
					
				}
				return;
				
 			}			
		}
		var keySelector = '#key' + keyIndex
		optionsToHide.push($(keySelector).val());
		optionsToShow = [];
		optionsToShow = $(allHeaderOptions).not(optionsToHide).get();
		
		
		for(var i= keyIndex + 1; i<=5 ; i++){
			var keySelector = "#key" + i;
				for(var k=0; k< optionsToShow.length ; k++){
					var optionSelector = "option[value=" + optionsToShow[k] + "]";
					$(keySelector).find(optionSelector).show();
				}
				for(var k=0; k< optionsToHide.length ; k++){
					var optionSelector = "option[value=" + optionsToHide[k] + "]";
					$(keySelector).find(optionSelector).hide();
				}
				$(keySelector).val("0");
				$(keySelector).selectpicker('refresh');
				var valueSelector = "#value" + i;
				$(valueSelector).empty();
				$(valueSelector).multiselect('destroy');
				$(valueSelector).multiselect({
					buttonWidth: '220px',
				});
				
		}
		
		
	});
	
	var optionsToHideMap = new Object();
	$('#value1').multiselect({
		buttonWidth: '220px',
	});
	
	$('#value2').multiselect({
		buttonWidth: '220px',
	});
	
	$('#value3').multiselect({
		buttonWidth: '220px',
	});
	
	$('#value4').multiselect({
		buttonWidth: '220px',
	});
	
	$('#value5').multiselect({
		buttonWidth: '220px',
	});
	
	$('#customSubmit').click(function(){
		
		for(var i=1; i<=5; i++){
			var keySelector   = "#key" + i;
			var valueSelector = "#value" + i + " option:selected";
			var keyVal = [];
			
			$(valueSelector).each(function(){
				keyVal.push($(this).val());
			});
			if( $(keySelector).val() != "0" && keyVal.length == 0){
				var msg = "No value has been selected for column " + i + " .Please either reset this column or select at least one value.";
				$('#customSelectionMessage').html();
				$('#customSelectionMessage').html(msg);
				$('#customSelectionMessage').show();
				return;
			}
		}
		
		$('#customize').modal('toggle');
		$(".biller-loader-div").fadeIn(1);
		
		var dataType = $('#currentDataType').val();
		var billCycle = $('#currentBillCycle').val();		
		var tower = $('#currentTower').val();
		var accountId = $('#currentAccount').val();		 
		var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var billCycle = month + year;
		
		var key1 = $('#key1').find('option:selected').val();
		var key2 = $('#key2').find('option:selected').val();
		var key3 = $('#key3').find('option:selected').val();
		var key4 = $('#key4').find('option:selected').val();
		var key5 = $('#key5').find('option:selected').val();
		var val1 = [];
		var val2 = [];
		var val3 = [];
		var val4 = [];
		var val5 = [];
		
		$('#value1 option:selected').each(function(){
			val1.push($(this).val());
		});
		
		$('#value2 option:selected').each(function(){
			val2.push($(this).val());
		});
		
		$('#value3 option:selected').each(function(){
			val3.push($(this).val());
		});
		
		$('#value4 option:selected').each(function(){
			val4.push($(this).val());
		});
		
		$('#value5 option:selected').each(function(){
			val5.push($(this).val());
		});
		
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
		dataFilter["accountId"]= accountId;
		dataFilter["key1"]= key1;
		dataFilter["val1"]= val1;
		dataFilter["key2"]= key2;
		dataFilter["val2"]= val2;
		dataFilter["key3"]= key3;
		dataFilter["val3"]= val3;
		dataFilter["key4"]= key4;
		dataFilter["val4"]= val4;
		dataFilter["key5"]= key5;
		dataFilter["val5"]= val5;
		
		
		
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
				    	$('#report').find('thead tr input[type="checkbox"]').prop('checked', false);
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
			 }, 50);
		
		
	});	
	
	
	
	
	$(document).on('change', '#key1', function(){
		//$('#value1').closest('div').append("<i class=\"fa fa-spinner fa-spin\" style=\"margin-left: 10px; color: #6666B2; \"></i>");
		var key1 = $(this).find('option:selected').val();		
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var billCycle = month+ year;
		var accountId = $('#currentAccount').val();
				
		$.ajax({
			url: 'data/level1.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId + '&key1=' + key1,
			type: 'GET',
			success: function(level1ValueList){
				if(level1ValueList.length!=0){
					$('#value1').empty();
					$('#value1').multiselect('destroy');
					for (var i=0; i<level1ValueList.length; i++){
						$('#value1').append('<option>' + level1ValueList[i] + '</option>');
					}
					$('#value1').multiselect({
						 buttonWidth: '220px',
						 maxHeight: '220',
						 includeSelectAllOption: true,
						 enableFiltering: true,
						 onDropdownShown: function(e) {
							   $('.multiselect-container li a label span').remove();
						       $('.multiselect-container li a label').append('<span class=\"checkmark\"></span>');
						       $('.multiselect-container .multiselect-filter span').remove();
						       $('.multiselect-container').css('overflow-x', 'auto');
						    }
					});
				}
			}
		});
		//$('#value1').closest('div').find("i").remove();
	});
	
	$(document).on('change', '#key2', function(){
		var key1 = $('#key1').find('option:selected').val();
		var val1 = [];
		$('#value1 option:selected').each(function(){
			val1.push($(this).val());
		});
		
		var key2 = $(this).find('option:selected').val();
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var accountId = $("#accountId").val();
		var billCycle = month+ year;
		
		$.ajax({
			url: 'data/level2.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower  + '&accountId=' + accountId + '&key1=' + key1 + '&val1=' + val1 + '&key2=' + key2,
			type: 'GET',
			success: function(level2ValueList){
				if(level2ValueList.length!=0){
					$('#value2').empty();
					$('#value2').multiselect('destroy');
					for (var i=0; i<level2ValueList.length; i++){
						$('#value2').append('<option>' + level2ValueList[i] + '</option>');
					}
					$('#value2').multiselect({
						 buttonWidth: '220px',
						 maxHeight: '220',
						 includeSelectAllOption: true,
						 enableFiltering: true,
						 onDropdownShown: function(e) {
							   $('.multiselect-container li a label span').remove();
						       $('.multiselect-container li a label').append('<span class=\"checkmark\"></span>');
						       $('.multiselect-container .multiselect-filter span').remove();
						       $('.multiselect-container').css('overflow-x', 'auto');
						    }
					});
				}
			}
		});
		$('#customize biller-modal-dropdown i').removeClass('fa fa-spinner fa-spin');
	});
	
	$(document).on('change', '#key3', function(){
		
		var key1 = $('#key1').find('option:selected').val();
		var key2 = $('#key2').find('option:selected').val();
		var key3 = $(this).find('option:selected').val();
		var val1 = [];
		var val2 = [];
		$('#value1 option:selected').each(function(){
			val1.push($(this).val());
		});
		
		$('#value2 option:selected').each(function(){
			val2.push($(this).val());
		});
		
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var billCycle = month+ year;
		var accountId = $("#accountId").val();
		
		
		
		$.ajax({
			url: 'data/level3.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId  + '&key1=' + key1 + '&val1=' + val1 + '&key2=' + key2
				 + '&val2=' + val2 + '&key3=' + key3 ,
			type: 'GET',
			success: function(level3ValueList){
				if(level3ValueList.length!=0){
					$('#value3').empty();
					$('#value3').multiselect('destroy');
					for (var i=0; i<level3ValueList.length; i++){
						$('#value3').append('<option>' + level3ValueList[i] + '</option>');
					}
					$('#value3').multiselect({
						 buttonWidth: '220px',
						 maxHeight: '220',
						 includeSelectAllOption: true,
						 enableFiltering: true,
						 onDropdownShown: function(e) {
							   $('.multiselect-container li a label span').remove();
						       $('.multiselect-container li a label').append('<span class=\"checkmark\"></span>');
						       $('.multiselect-container .multiselect-filter span').remove();
						       $('.multiselect-container').css('overflow-x', 'auto');
						    }
					});
				}
			}
		});
	});
	
	
	$(document).on('change', '#key4', function(){		
		var key1 = $('#key1').find('option:selected').val();
		var key2 = $('#key2').find('option:selected').val();
		var key3 = $('#key3').find('option:selected').val();
		var key4 = $(this).find('option:selected').val();
		var val1 = [];
		var val2 = [];
		var val3 = [];
		$('#value1 option:selected').each(function(){
			val1.push($(this).val());
		});
		
		$('#value2 option:selected').each(function(){
			val2.push($(this).val());
		});
		
		$('#value3 option:selected').each(function(){
			val3.push($(this).val());
		});
		
		
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var billCycle = month+ year;
		var accountId = $("#accountId").val();
		
		$.ajax({
			url: 'data/level4.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId+ '&key1=' + key1 + '&val1=' + val1 + '&key2=' + key2
				 + '&val2=' + val2 + '&key3=' + key3 + '&val3=' + val3 + '&key4=' + key4,
			type: 'GET',
			success: function(level4ValueList){
				if(level4ValueList.length!=0){
					$('#value4').empty();
					$('#value4').multiselect('destroy');
					for (var i=0; i<level4ValueList.length; i++){
						$('#value4').append('<option>' + level4ValueList[i] + '</option>');
					}
					$('#value4').multiselect({
						 buttonWidth: '220px',
						 maxHeight: '220',
						 includeSelectAllOption: true,
						 enableFiltering: true,
						 onDropdownShown: function(e) {
							   $('.multiselect-container li a label span').remove();
						       $('.multiselect-container li a label').append('<span class=\"checkmark\"></span>');
						       $('.multiselect-container .multiselect-filter span').remove();
						       $('.multiselect-container').css('overflow-x', 'auto');
						    }
					});
				}
			}
		});
	});
	
	$(document).on('change', '#key5', function(){		
		var key1 = $('#key1').find('option:selected').val();
		var key2 = $('#key2').find('option:selected').val();
		var key3 = $('#key3').find('option:selected').val();
		var key4 = $('#key4').find('option:selected').val();
		var key5 = $(this).find('option:selected').val();
		var val1 = [];
		var val2 = [];
		var val3 = [];
		var val4 = [];
		$('#value1 option:selected').each(function(){
			val1.push($(this).val());
		});
		
		$('#value2 option:selected').each(function(){
			val2.push($(this).val());
		});
		
		$('#value3 option:selected').each(function(){
			val3.push($(this).val());
		});
		
		$('#value4 option:selected').each(function(){
			val4.push($(this).val());
		});
		
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var billCycle = month+ year;
		var accountId = $("#accountId").val();
		
		$.ajax({
			url: 'data/level5.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId + '&key1=' + key1 + '&val1=' + val1 + '&key2=' + key2
				 + '&val2=' + val2 + '&key3=' + key3 + '&val3=' + val3 + '&key4=' + key4 + '&val4=' + val4 + '&key5=' + key5,
			type: 'GET',
			success: function(level5ValueList){
				if(level5ValueList.length!=0){
					$('#value5').empty();
					$('#value5').multiselect('destroy');
					for (var i=0; i<level5ValueList.length; i++){
						$('#value5').append('<option>' + level5ValueList[i] + '</option>');
					}
					$('#value5').multiselect({
						 buttonWidth: '220px',
						 maxHeight: '220',
						 includeSelectAllOption: true,
						 enableFiltering: true,
						 onDropdownShown: function(e) {						       
							   $('.multiselect-container li a label span').remove();
						       $('.multiselect-container li a label').append('<span class=\"checkmark\"></span>');
						       $('.multiselect-container .multiselect-filter span').remove();
						       $('.multiselect-container').css('overflow-x', 'auto');						       
						    }
					});
				}
			}
		});
	});
	
	
	
	
});


function resetCustomizeModal(){
	optionsToShow = allHeaderOptions;
	for (var i=1 ; i<=5 ; i++){
		var keySelector = "#key" + i;
		
		for(var k=0; k< optionsToShow.length ; k++){
			var optionSelector = "option[value=" + optionsToShow[k] + "]";
			$(keySelector).find(optionSelector).show();
		}
		
		$(keySelector).val("0");
		$(keySelector).selectpicker('refresh');
		var valueSelector = "#value" + i;
		$(valueSelector).empty();
		$(valueSelector).multiselect('destroy');
		$(valueSelector).multiselect({
			buttonWidth: '220px',
		});
	}
}