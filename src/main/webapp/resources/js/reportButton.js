var rowEditMap = new Object();
var copyInfo = new Object();
var copyEditMap = new Object();
var visibleColumns = [];

var settings = {
		"scrollX" : true,
		"aoColumnDefs" : [ {
			"bVisible" : true,
			"aTargets" : [ '_all' ]			
		}, {
			"bVisible" : false,
			"aTargets" : [ '_all' ]			
		} ],
		"iDisplayLength" : 10
	};

$(function() {
	
	$("[data-toggle=tooltip]").tooltip();
	$('[data-toggle="modal"][title]').tooltip();
	
	$("#reportBack").click(function() {
		$('#reportDiv').hide();
		$('#reportSelection').fadeIn("slow");
		$('#report').DataTable().destroy();
		$('#report').empty();
		rowEditMap = {};
		copyInfo = {};
		copyEditMap = {};
		visibleColumns = [];
		dataTableInitialized = false;
	});

	$("#reportTotHrs").click(function() {
		var reportTable = $('#report').DataTable();
		var reportTable2 = $('#report').dataTable();
		var totSum = 0;
		reportTable.rows().every(function(){
			
			var rowIdx = this.index();
			var rowNode = reportTable2.fnGetNodes(rowIdx);
			var rowID = '#' +  $(rowNode).attr('id');
			var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
			if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
				if (rowEditMap[rowID] == true || copyEditMap[rowID]) {
					var rowData = [];
					$(rowNode).find('input[type="text"]').each(function(){			  				  
		  				  rowData.push($(this).val())
		  			});
					var data = parseFloat(rowData[10]);
				}else{
					if(reportDataType == 0){
						var data = parseFloat(reportTable.cell( this, 6 ).data());
					}else{
						var data = parseFloat(reportTable.cell( this, 11 ).data());
					}
				}
				totSum = data + totSum;
			}
		});
		var msg = "Total Sum is " + totSum;
		billerAlert(msg,true, 'Okay', false, '','', "Alert !");
		
	});

	$("#reportLock").click(function(e) {

		if(hasApprovedBillCycle == 1){
			e.stopPropagation();
			e.preventDefault();
			return;
		}
		var tower = $('#currentTower').val();
		var billCycle = $('#currentBillCycle').val();
		var reportTable = $('#ilcReport').DataTable();
		var userProfile = JSON.parse($('#strUserProfile').val());

		url = 'data/lock.do?billCycle=' + billCycle + '&towerID=' + tower;

		$.ajax({
			url : url,
			data : false,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'GET',
			success : function(strlockResponseMap) {
				var lockResponseMap = JSON.parse(strlockResponseMap);
				if (lockResponseMap.msg == "success") {
					editMode = true;
					billerAlert("Table lock established successfully.",true, 'Okay', false, '','', "Alert !");
					
				} else if (lockResponseMap.lockedBy != undefined && lockResponseMap.lockedBy.userID == userProfile.userID) {
					editMode = true;
					billerAlert("You have already established Lock..!!",true, 'Okay', false, '','', "Alert !");
					
				} else if(lockResponseMap.lockedBy != undefined && lockResponseMap.lockedBy != ''){
					editMode = false;
					var msg = "Table is already locked by " + lockResponseMap.lockedBy.name;
					billerAlert(msg,true, 'Okay', false, '','', "Alert !");
					
				} else if(lockResponseMap.lockedForTower != ''){
					editMode = false;
					var msg = "You have already established for tower " + lockResponseMap.lockedForTower;
					billerAlert(msg,true, 'Okay', false, '','', "Alert !");
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
				
		    	
			}
			
		});
	});

	$("#reportEdit")
			.click(
					function(e) {
						if(!editMode){
							e.stopPropagation();
							e.preventDefault();
							return;
						}
						
						var reportTable = $('#report').DataTable();
						var reportTable2 = $('#report').dataTable();
						var rowCount = 0;
						
						
						reportTable.rows().every(function(){
							var rowIdx = this.index();
							var rowNode = reportTable2.fnGetNodes(rowIdx);
							
							var rowIDSelector = '#' +  $(rowNode).attr('id');
							if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
								rowCount = rowCount + 1;
								
								}
						});

						if (rowCount == 0) {
							var msg="You have not selected any records for editing, please select at least one record for editing.";
							billerAlert(msg,true,"Okay",false,"NO","","Notification");							
							//alert("You have not selected any records for editing, please select record for editing");
						} else {
							
							reportTable.rows().every(function(){
								//var idx = reportTable.row( this ).index(); 
								/*reportTable.cell( this, 0 ).data(content ).draw();
								$(this).prop('checked', prop);
							});
							$(".biller-loader-div").fadeOut("slow");
							$('#report tbody tr').find(
							'input[type="checkbox"]:checked').each(
							function(){*/
								
							/*var rowID = "#"
									+ $(this).closest('tr').attr("id");*/
							//var rowID = '#' + reportTable.rows(this).ids();
								//var rowID = this.id();
								//var row5 = reportTable.fnGetNodes(this);
								//var rowD1 = $(this).attr('id');
								//var rowD2 = reportTable.row(this).id();
								var rowIdx = this.index();
								var rowNode = reportTable2.fnGetNodes(rowIdx);
								var rowID = '#' +  $(rowNode).attr('id');
								
							//var rowIdx = reportTable.row(rowID).index();
							var oldData = reportTable.row(rowIdx).data();
							//var oldSettings = reportTable2.fnSettings();
							
							
							if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
								
								/*$('#report').DataTable().destroy();
								dataTableInitialized = false;
								$('#report').DataTable(
										{
											"scrollX" : true,
											"aoColumnDefs" : [ {
									"bVisible" : true,
									"aTargets" : [ '_all' ]			
								}, {
									"bVisible" : false,
									"aTargets" : [ '_all' ]			
								} ]});
								dataTableInitialized = true;*/
								
								if (!rowEditMap[rowID]) {
									//for (i = 1; i < oldData.length-1; i++) {
										//var selector = rowID + " " + "td";
										//$(selector).eq(i).html(
										/*$(rowNode).find('td').eq(i).html(
												'<input type=\"text\" class=\"form-control\" value=\"'
														+ oldData[i] + '\">');*/
										/*$(rowNode).eq(i).html(
												'<input type=\"text\" class=\"form-control\" value=\"'									
														+ oldData[i] + '\">')*/
									var i=0;
									var j=1;
									
									$(rowNode).find('td').each(function(){
										
										if (visibleColumns.length != 0){
										
											var visibleColumnIndex = visibleColumns[i]
											var cellData = oldData[visibleColumnIndex];
											
											if(visibleColumnIndex != 0){
												$(rowNode).find('td').eq(i).html(
													'<input type=\"text\" class=\"form-control\" value=\"'
															+ cellData + '\">');
											}
											i = i+1;
										}else{
											
											$(rowNode).find('td').eq(j).html(
													'<input type=\"text\" class=\"form-control\" value=\"'
															+ oldData[j] + '\">');
										j = j+1;
										}
										
										
											
									});
										
										//reportTable.row(rowID).data()[i]= '<input type=\"text\" class=\"form-control\" value=\"'+ oldData[i] + '\">';
										
										//var mydata = '<input type=\"text\" class=\"form-control\" value=\"'+ oldData[i] + '\">';
										//reportTable.row(rowID).data(myda].draw();
										rowEditMap[rowID] = true;
									//}
									
								}
							}
							
							/*$('#report').DataTable().destroy();
							dataTableInitialized = false;
							$('#report').DataTable(settings);
							$('#report').DataTable().draw();
							dataTableInitialized = true;*/
							
							/*else {
								alert("You are already editing this row ..!!")
							}*/

							$('#report th').eq(0).click();
							//reportTable.columns.adjust().draw();
							//$('#report').find('thead th').css('width', 'auto');
							//$('#report').find('tbody td').css('width', '100%');
							
						});
						}
					

					});

	$("#reportCopy")
			.click(
					function(e) {
						
						if(!editMode){
							e.stopPropagation();
							e.preventDefault();
							return;
						}
						
						var reportTable = $('#report').DataTable();
						var reportTable2 = $('#report').dataTable();
						var rowCount = 0;
						var rowSelectedForCopy;
						var rowSelectedIndex;
						var rowID;
						
						reportTable.rows().every(function(){
							var rowIdx = this.index();
							var rowNode = reportTable2.fnGetNodes(rowIdx);
							
							var rowIDSelector = '#' +  $(rowNode).attr('id');
							if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
								rowCount = rowCount + 1;
								rowSelectedForCopy = rowNode;
								rowSelectedIndex = rowIdx;
								rowID =  $(rowNode).attr('id');
								}
						});

						/*$('#report tbody tr').find(
								'input[type="checkbox"]:checked').each(
								function() {
									rowCount = rowCount + 1;
								});*/

						if (rowCount == 0) {
							var msg = "You have not selected any row, please select one row for copying.";
							billerAlert(msg,true, 'Okay', false, '','', "Alert !");
							//break;
							return;
						} else if (rowCount == 1) {
							
							
							if(!(visibleColumns.length == 0 || visibleColumns.length ==30)){
								
								var msg = "You can not copy data in column filter mode.";
								billerAlert(msg,true, 'Okay', false, '','', "Alert !");
								return;
							}
							var prevCount;
							var splitRowID;
							//var rowID = $('#report tbody tr').find(
									//'input[type="checkbox"]:checked').closest(
									//'tr').attr("id");
							//var rowIDSelector = "#" + rowID;
							//var rowIdx = reportTable.row(rowIDSelector).index();
							var copiedData = [];
							var newRowID;
							var newData = new Array();
							var tempData = [];
							var rowIDSelector = '#' +  $(rowSelectedForCopy).attr('id');
							rowIdx = rowSelectedIndex;
							if (rowEditMap[rowIDSelector]) {
								tempData =  reportTable.row(rowIdx).data();
								copiedData
										.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');
								$(rowIDSelector)
										.find('input[type="text"]')
										.each(
												function() {
													copiedData
															.push('<input type=\"text\" class=\"form-control\" value=\"'
																	+ $(this)
																			.val()
																	+ '\">');
												});
								copiedData.push(tempData[tempData.length - 1]);

							} else {
								copiedData = reportTable.row(rowIdx).data();
							}
							prevCount = copyInfo[rowID];
							splitRowID = rowID.split("_");
							if (typeof prevCount === "undefined"
									&& splitRowID.length == 1) {
								prevCount = 0;
							} else if (typeof prevCount === "undefined"
									&& splitRowID.length == 2) {
								prevCount = parseInt(copyInfo[splitRowID[0]]);
							}
							if (splitRowID.length == 1) {
								copyInfo[rowID] = prevCount + 1;
								newRowID = rowID + "_" + copyInfo[rowID];
							} else {
								copyInfo[splitRowID[0]] = prevCount + 1;
								newRowID = splitRowID[0] + "_"
										+ copyInfo[splitRowID[0]];
							}
							for (var j = 0; j < copiedData.length; j++) {
								newData[j] = copiedData[j];
							}
							var temp = $("#report").dataTable().fnAddData(
									newData);
							var rowCount = reportTable.data().length - 1;
							var newRowIDSelector = "#" + newRowID;
							var newRow = $('#report').dataTable().fnGetNodes(
									temp);
							$(newRow).attr('id', newRowID);
							insertedRow = reportTable.row(rowCount).data();
							for (var i = rowCount; i > rowIdx + 1; i--) {
								var tempRow = reportTable.row(i - 1).data();
								var tempRowInstance = $('#report').dataTable()
										.fnGetNodes(i - 1);
								var tempRowID = $(tempRowInstance).attr("id");
								reportTable.row(i).data(tempRow);
								var oldRow = $('#report').dataTable()
										.fnGetNodes(i);
								$(oldRow).attr("id", tempRowID);
								reportTable.row(i - 1).data(insertedRow);
								var tempNewRow = $('#report').dataTable()
										.fnGetNodes(i - 1);
								$(tempNewRow).attr("id", newRowID);
							}

							if (rowEditMap[rowIDSelector]) {
								rowEditMap[newRowIDSelector] = true;
								copyEditMap[newRowIDSelector] = true;
							}

							reportTable.draw();
							/*$(newRowIDSelector).find('input[type="checkbox"]')
									.prop("checked", true);*/
							var newRowNode = reportTable2.fnGetNodes(rowIdx+1);
							$(newRowNode).find('input[type="checkbox"]')
							.prop("checked", true);
							$(newRowNode).css("background-color", "#b7e1fe");
							var msg = "Data copied successfully";
							billerAlert(msg,true, 'Okay', false, '','', "Alert !");
						} else {
							//break;
							
							var msg = "You have selected more than one row, please select one row for copying.";
							billerAlert(msg,true, 'Okay', false, '','', "Alert !");
							return;
						}
						
						

					});

	

	$("#reportSave").click(function(e) {
		
		if(!editMode){
			e.stopPropagation();
			e.preventDefault();
			return;
		}
		
		$(".biller-loader-div").fadeIn(1);		
		var reportTable = $('#report').DataTable();
		var reportTable2 = $('#report').dataTable();
    	var updateRows = [];
    	var newRows=[];
    	var saveRecords = {};
    	
    	reportTable.rows().every(function(){
			//var idx = reportTable.row( this ).index(); 
			/*reportTable.cell( this, 0 ).data(content ).draw();
			$(this).prop('checked', prop);
		});
		$(".biller-loader-div").fadeOut("slow");
		$('#report tbody tr').find(
		'input[type="checkbox"]:checked').each(
		function(){*/
			
		/*var rowID = "#"
				+ $(this).closest('tr').attr("id");*/
		//var rowID = '#' + reportTable.rows(this).ids();
			//var rowID = this.id();
			//var row5 = reportTable.fnGetNodes(this);
			//var rowD1 = $(this).attr('id');
			//var rowD2 = reportTable.row(this).id();
			var rowIdx = this.index();
			var rowNode = reportTable2.fnGetNodes(rowIdx);
			var rowID = '#' +  $(rowNode).attr('id');
			var oldData = reportTable.row(rowIdx).data();
    	
    	//$("#report  tr").find('input[type="checkbox"]:checked').each(function(){
			//if($(this).eq(0).find('input[type="checkbox"]:checked')){
			if($(rowNode).find('input[type="checkbox"]').prop("checked")){
    			//var selectedRowID = $(this).closest('tr').attr("id");
	  			//selectedRowID = '#' + selectedRowID;
				var selectedRowID = rowID;
		  		var rowData = [];				  		
		  		var isCopied = selectedRowID.split("_");
		  		var rowIDSplit = selectedRowID.split("-");
		  		 
		  		if (isCopied.length ==1){
			  		//rowData.push( rowIDSplit[1]);
		  			rowData[0] = rowIDSplit[1] ;
		  			/*$(selectedRowID).find('input[type="text"]').each(function(){	
			  		//$(this).cells().find('input[type="text"]').each(function(){			  		
		  				  rowData.push("\"" + $(this).val() +"\"")
		  			});*/
			  		//var i = 0;
			  		/*$(rowNode).find('input[type="text"]').each(function(){
			  			    
			  				var cellIndex = visibleColumns[i];
			  				  //rowData.push("\"" + $(this).val() +"\"")
			  				if(cellIndex == 0){
			  					i=i+1;
			  					cellIndex = visibleColumns[i];
			  				}
			  					rowData[cellIndex] = "\"" + $(this).val() + "\"";
			  				
			  			i=i + 1;
			  		});*/
		  			var i = 0;
		  			
		  			$(rowNode).find('td:visible').each(function(){
		  				if(visibleColumns.length != 0){
			  				var thisColumnIndex = visibleColumns[i];
			  				
			  				//var thisTd = $(this);
			  				var thisInputVal = $(this).find('input[type="text"]').val();
			  				//var thisColumIndex = visibleColumns[i];
			  				//var myInputVal = $(myInput).val();
			  				if(thisInputVal != undefined && thisColumnIndex != 0){
			  					rowData[thisColumnIndex] =  "\"" + thisInputVal+ "\""  ;
			  					
			  				}
			  				
		  				}else{
		  					var thisInputVal = $(this).find('input[type="text"]').val();
		  					if(thisInputVal != undefined ){
		  						rowData[i] =  "\"" + thisInputVal+ "\""  ;
		  					}
		  				}
		  				i++;
		  				
		  			});
		  			/*for (var i=0; i<=visibleColumns.length ; i++){
		  				//var myCell = $(rowNode).eq(visibleColumns[i]);
		  				var Mytd = $(rowNode).eq(visibleColumns[i]).find('td');
			  			//var myInput = $(rowNode).eq(visibleColumns[i]).find('input[type="text"]');
		  			}*/
			  		//});
			  		
			  		for ( var k=1; k <= 29 ; k++){
			  			if(rowData[k] == void 0){
			  				rowData[k] = "\"" + oldData[k] +"\"";
			  			}
			  		}
			  		
		  				updateRows.push("{ \"rowID\" : " + rowIDSplit[1] + " , " + "\"rowData\" : [" + rowData.join(',') + ']}' );	
		  				
		  		}
		  		if(isCopied.length==2){
		  			var i = 0;
		  			var selectedRowID = rowID;
		  			rowData[0] = "\"" + rowIDSplit[1]+"\"";
		  			//rowData.push("\"" + rowIDSplit[1] + "\"");
	  				/*$(selectedRowID).find('input[type="text"]').each(function(){			  				  
	  				  	rowData.push("\"" + $(this).val() +"\"")
	  				});*/
		  			$(rowNode).find('td:visible').each(function(){
		  				if(visibleColumns.length != 0){
			  				var thisColumnIndex = visibleColumns[i];
			  				
			  				//var thisTd = $(this);
			  				var thisInputVal = $(this).find('input[type="text"]').val();
			  				//var thisColumIndex = visibleColumns[i];
			  				//var myInputVal = $(myInput).val();
			  				if(thisInputVal != undefined && thisColumnIndex != 0){
			  					rowData[thisColumnIndex] =  "\"" + thisInputVal+ "\""  ;
			  					
			  				}
			  				
		  				}else{
		  					var thisInputVal = $(this).find('input[type="text"]').val();
		  					if(thisInputVal != undefined ){
		  						rowData[i] =  "\"" + thisInputVal+ "\""  ;
		  					}
		  				}
		  				i++;
		  				
		  			});
		  			for ( var k=1; k <= 29 ; k++){
			  			if(rowData[k] == void 0){
			  				rowData[k] = "\"" + oldData[k] +"\"";
			  			}
			  		}
	  				newRows.push("{ \"rowID\" : " + "\"" + rowIDSplit[1] +"\"" + " , " + "\"rowData\" : [" + rowData.join(',') + ']}' );
	  				
		  		}
		    //});
			}
    	
    	});
    	
    	var updateRecords ="[" +  updateRows.join(' , ') + "]";
    	var newRecords = "[" +  newRows.join(' , ') + "]";
    	saveRecords["updateRecords"] = JSON.parse(updateRecords);
    	saveRecords["newRecords"] = JSON.parse(newRecords);	
    	var billCycle = $('#currentBillCycle').val();
    	var tower = $('#currentTower').val();
    	
    	$.ajax({	        		
    		type: 'POST',
    		url: 'data/update.do?billCycle=' + billCycle + '&towerID=' + tower,
    		contentType: 'application/json',
    		dataType: 'json',
    		data: JSON.stringify(saveRecords),
    		success: function (result){	
    			if(result){
    				var msg = "Data saved successfully";
    				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
    				editMode = false;
    				rowEditMap = {};
    				copyInfo = {};
    				copyEditMap = {};
    				visibleColumns = [];
    				$("#reportSubmit").trigger("click");
    				
    			}else{
    				var msg = "Error occured while saving data";
    				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
    			}    			
    		}        			
    	});
	});

	
	

	$(document).on('change','#report tbody tr .checkbox',function() {
						
						var rowID = "#" + $(this).closest('tr').attr("id");

						if (copyEditMap[rowID]) {
							var msg = "This operation will keep copied data in read only mode";
							billerAlert(msg,true, 'Okay', false, '','', "Alert !");
							var reportTable = $('#report').DataTable();
							var rowIdx = reportTable.row(rowID).index();
							var rowData = new Array();
							var selector = rowID + " " + "td";
							rowData
									.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');
							$(selector).find('input[type="text"]').each(
									function() {
										rowData.push($(this).val());
									});

							reportTable.row(rowIdx).data(rowData);
							var checkData = reportTable.row(rowIdx).data();
							rowEditMap[rowID] = false;
							copyEditMap[rowID] = false;

						}
						if (!(this.checked) && rowEditMap[rowID] == true) {
							var msg = "This operation will undo any changes made for this record";
							billerAlert(msg,true, 'Okay', false, '','', "Alert !");
							var reportTable = $('#report').DataTable();
							var rowIdx = reportTable.row(rowID).index();
							var oldData = reportTable.row(rowIdx).data();
							for (i = 1; i < oldData.length + 1; i++) {
								var selector = rowID + " " + "td";
								$(selector).eq(i).html(oldData[i]);
							}
							rowEditMap[rowID] = false;
						}

	});
				

});

function customizeColumns() {

	var columnSelected = "["
	visibleColumns = [];
	
	var allColumns = [];
	
	var hideColumns = [];
	var columnLength;
	
	var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	
	if (reportDataType == 0){
		columnLength = 43;
	} else{
		columnLength = 31;
	}
	
	for (var i=0; i<columnLength; i++){
		allColumns.push(i);
	}
	settings = {
		"scrollX" : true,
		"aoColumnDefs" : [ {
			"bVisible" : true,
			"aTargets" : [ '_all' ]			
		}, {
			"bVisible" : false,
			"aTargets" : [ '_all' ]			
		} ],
		"iDisplayLength" : 10
	}
	
	visibleColumns.push(0);
	$("#reportButtons .dropdown .dropdown-menu li.divider").nextAll().find(
			'input[type="checkbox"]:checked').each(function() {
				visibleColumns.push(Number($(this).val()));
	});
	columnSelected = columnSelected + visibleColumns.join(", ") + "]";

	
	hideColumns = $(allColumns).not(visibleColumns).get();
	
	var reportTable = $('#report').DataTable();
	
	//var columns = reportTable.columns();
	
	//reportTable.columns.visible(visibleColumns).visible(false,false);
	reportTable.columns( visibleColumns ).visible( true, true);
	//reportTable.columns( hideColumns ).visible(false);
	for ( var i=0 ; i<hideColumns.length-1; i++){
		reportTable.column(hideColumns[i]).visible(false, false);
	}
	reportTable.draw();
	$('#report th').eq(0).click();
	/*$('#report').DataTable().destroy();
	dataTableInitialized = false;
	settings.aoColumnDefs[0].aTargets = visibleColumns;
	$('#report').DataTable(settings);
	dataTableInitialized = true;
	$('#report').DataTable().draw();*/
	
}

function modifyAllColumns(element) {
	var prop = $(element).prop('checked');
	$("#reportButtons .dropdown .dropdown-menu li.divider").nextAll().find(
			'input[type="checkbox"]').each(function() {
		$(this).prop('checked', prop);
	});
}

function SelectAllRecords(element) {
	$(".biller-loader-div").fadeIn(1);
	var prop = $(element).prop('checked');
	var reportTable = $('#report').DataTable();
	var reportTable2 = $('#report').dataTable();
	/*$("reportTable tbody").find('tr td input[type="checkbox"]').each(function() {
		$(this).prop('checked', prop);
	});*/
	var content;
	if(prop){
		content = '<div class=\"checkbox\"> <input type=\"checkbox\"  checked class=\"styled\"/>  <label > </label> </div>' ;
	}else{
		content = '<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>' ;
	}
	reportTable.rows().every(function(){
		//var idx = reportTable.row( this ).index(); 
		reportTable.cell( this, 0 ).data(content ).draw();
		//$(this).prop('checked', prop);
		var rowIdx = this.index();
		var rowNode = reportTable2.fnGetNodes(rowIdx);
		var rowID = '#' +  $(rowNode).attr('id');
		//var rowID = "#" + $(this).closest('tr').attr("id");

		if (copyEditMap[rowID]) {
			//alert("This operation will keep copied data in read only mode");
			//var reportTable = $('#report').DataTable();
			//var rowIdx = reportTable.row(rowID).index();
			var rowData = new Array();
			var selector = rowID + " " + "td";
			rowData
					.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');
			$(rowNode).find('td input[type="text"]').each(
					function() {
						rowData.push($(this).val());
					});

			reportTable.row(rowIdx).data(rowData);
			var checkData = reportTable.row(rowIdx).data();
			rowEditMap[rowID] = false;
			copyEditMap[rowID] = false;

		}
		if (!(prop) && rowEditMap[rowID] == true) {
			//alert("This operation will undo any changes made for this record");
			//var reportTable = $('#report').DataTable();
			//var rowIdx = reportTable.row(rowID).index();
			var oldData = reportTable.row(rowIdx).data();
			for (i = 1; i < oldData.length + 1; i++) {
				var selector = rowID + " " + "td";
				$(rowNode).find('td').eq(i).html(oldData[i]);
			}
			rowEditMap[rowID] = false;
		}

	
	});
	$(".biller-loader-div").fadeOut("slow");
}


function clearArray(array) {
	  while (array.length) {
	    array.pop();
	  }
	}