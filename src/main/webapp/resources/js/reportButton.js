var rowEditMap = new Object();
var rowEditDataMap = new Object();
var copyInfo = new Object();
var copyEditMap = new Object();
var visibleColumns = [];
var allColumns = [];
var headerCheckboxToggle = false;


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
		$(".biller-loader-div").fadeIn(1);
		
		var reportTable = $('#report').DataTable();
		var reportTable2 = $('#report').dataTable();
		var totSum = 0;
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
		if(visibleColumns.length != 0){
			var totHoursVisible = false;
			for(var i=0; i<visibleColumns.length; i++){
				if(reportDataType == 1 && visibleColumns[i] == 11 ){
					totHoursVisible = true;
					break;
				}
				if(reportDataType == 0 && visibleColumns[i] == 6 ){
					totHoursVisible = true;
					break;
				}
			}
			if(!totHoursVisible){
				var msg = "Total Hours can not be calulcated if this column is hidden."
				billerAlert(msg,true, 'Okay', false, '','', "Alert!");
				setTimeout(function(){
					$(".biller-loader-div").fadeOut("slow");
				}, 10);
				return;
			}
		}
		setTimeout(function(){
		reportTable.rows().every(function(){			
			var rowIdx = this.index();
			var rowNode = reportTable2.fnGetNodes(rowIdx);
			var rowID = '#' + $(rowNode).find('td input[type="checkbox"]').attr("id");
			var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
			
			if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
				if (rowEditMap[rowID] == true || copyEditMap[rowID] == true) {
					var rowData = [];
					var cellIndex;
					var hrs;
					
					if (visibleColumns.length != 0){	
						 $(rowNode.innerHTML).find('input[type="text"]').each(function(){	
							cellIndex = $(this).closest('td').index();
							var visibleColumnIndex = visibleColumns[cellIndex];																
							rowData[visibleColumnIndex] = $(this).val();
						 });		
					}else{
						 $(rowNode.innerHTML).find('input[type="text"]').each(function(){
							cellIndex = $(this).closest('td').index();
			  				rowData[cellIndex] = $(this).val(); 
						 });
					}					    
					if(rowData[11] == null || rowData[11] == undefined){						 
						 rowData[11] = rowEditDataMap[rowID][11];
					}
					hrs = parseFloat(rowData[11]);
				}else{
					if(reportDataType == 0){
						hrs = parseFloat(reportTable.cell( this, 6 ).data());
					}else{
						hrs = parseFloat(reportTable.cell( this, 11 ).data());
					}
				}				
				totSum = hrs + totSum;
			}			
		});
		}, 50);
		setTimeout(function(){
	 		  $(".biller-loader-div").fadeOut("slow");
	 		  var msg = "Total Sum is " + totSum;
	 		  billerAlert(msg,true, 'Okay', false, '','', "Result");
	 	}, 100);
	 	
		
		;
	});

	/* Establish lock on  table */
	$("#reportLock").click(function(e) {
		var tower = $('#currentTower').val();
		var billCycle = $('#currentBillCycle').val();
		var reportTable = $('#ilcReport').DataTable();
		var userProfile = JSON.parse($('#strUserProfile').val());
		var accountId = $('#currentAccount').val();
		if(hasApprovedBillCycle == 1 || (tower == 0)){
			e.stopPropagation();
			e.preventDefault();
			return;
		}	

		url = 'data/lock.do?billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId ;

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
					editMode[tower] = true;					
					billerAlert("Table lock established successfully.",true, 'Okay', false, '','', "Alert !");					
				} else if (lockResponseMap.lockedBy != undefined && lockResponseMap.lockedBy.userID == userProfile.userID) {
					editMode[tower] = true;
					billerAlert("You have already established Lock..!!",true, 'Okay', false, '','', "Alert !");					
				} else if(lockResponseMap.lockedBy != undefined && lockResponseMap.lockedBy != ''){
					editMode[tower] = false;
					var msg = "Table is already locked by " + lockResponseMap.lockedBy.name;
					billerAlert(msg,true, 'Okay', false, '','', "Alert !");					
				} else if(lockResponseMap.lockedForTower != ''){
					editMode[tower] = false;
					var msg = "You have already established for tower " + lockResponseMap.lockedForTower;
					billerAlert(msg,true, 'Okay', false, '','', "Alert !");
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
					if($('#reportUnlock').find('span i').hasClass('biller-icon-disabled')){
						$('#reportUnlock').find('span i').removeClass('biller-icon-disabled');
		    		}
					if($('#reportBulkUpdate').find('span i').hasClass('biller-icon-disabled')){
						$('#reportBulkUpdate').find('span i').removeClass('biller-icon-disabled');
		    		}
				}
			},
			error: function(result){
	 			location.href = 'error.do';
	 		}
		});
	});
	
	$("#reportUnlock").click(function(e) {
		var tower = $('#currentTower').val();
		var billCycle = $('#currentBillCycle').val();
		var reportTable = $('#ilcReport').DataTable();
		var userProfile = JSON.parse($('#strUserProfile').val());
		var accountId = $('#currentAccount').val();
		if(hasApprovedBillCycle == 1 || (tower == 0)){
			e.stopPropagation();
			e.preventDefault();
			return;
		}	

		url = 'data/unlock.do?billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId;

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
					editMode[tower] = false;					
					billerAlert("Table unlocked successfully.",true, 'Okay', false, '','', "Alert !");					
				} else  {
					editMode[tower] = false;
					billerAlert("Table couldn't be unlocked, may be it was not locked earlier..!!",true, 'Okay', false, '','', "Alert !");					
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
					if($('#reportUnlock').find('span i').hasClass('biller-icon-disabled')){
						$('#reportUnlock').find('span i').removeClass('biller-icon-disabled');
		    		}
					if($('#reportBulkUpdate').find('span i').hasClass('biller-icon-disabled')){
						$('#reportBulkUpdate').find('span i').removeClass('biller-icon-disabled');
		    		}
				}
			},
			error: function(result){
	 			location.href = 'error.do';
	 		}
		});
	});
	
	$(document).on( 'change','#report tbody tr td .form-control',  function () {
		var cellIdx = $(this).closest('td').index();
		var reportTable = $('#report').DataTable();
		var reportTable2 = $('#report').dataTable();
		var headerIdx;
		var headerVal;
		if(visibleColumns.length != 0){
			headerIdx = visibleColumns[cellIdx];			
		}else{
			headerIdx = allColumns[cellIdx];
		}
			headerVal = tableHeader[headerIdx].trim();
			
		//if(cellIdx == 8){
		if(headerVal == "Activity"){			
			var cellValue = $(this).closest('input').val();
			var rowIdx = $(this).closest('tr').index();
			var wrNo;
			var columnsToUpdate = ["WR Description", "CTC/RTC", "Cost Center",  "Vendor Class", "ASM", "ASD"];  // This should be same as it exists in DB.
			var requiredColumnsVisible = true;
			var columnsToUpdateIdx;
			for(var i=0; i< columnsToUpdate.length ; i++ ){
				columnsToUpdateIdx = tableHeader.indexOf(columnsToUpdate[i]);
				if(visibleColumns.length != 0 && visibleColumns.indexOf(columnsToUpdateIdx) == -1 ){
					requiredColumnsVisible = false;
					break;
				}
			}
			if( ! (requiredColumnsVisible) ){
				var msg = "Required columns are not visible hence it can not be updated.";
				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
				return;
			}
			if(cellValue.substr(0,2).toUpperCase() == "WR"){
				wrNo = cellValue.substr(0,8); 
			}else if(cellValue.substr(0,3).toUpperCase() == "ENH"){
				wrNo = cellValue.substr(0,9);
			}else{
				return;
			}
						
			$.ajax({
				url: 'data/itwrRef.do?wrNo='+wrNo ,
				type: 'GET',
				data: false,
				success: function(itwrRef){
					if(itwrRef.length !=0){
						var data;
						var cellIdxList=[];
						var wrColumn = "WR #";
						var wrColumnIdx;
						wrColumnIdx = tableHeader.indexOf(wrColumn);
						//var cellIdxList = [25,26,27,28,2,3];
						for (var i=0; i< columnsToUpdate.length; i++ ){
							cellIdxList.push(tableHeader.indexOf( columnsToUpdate[i]));
						}
						var i=0;
						var cellNode;
						delete itwrRef[0].reqNo;
						for(var prop in itwrRef[0]){
							cellNode = reportTable.cell( rowIdx, cellIdxList[i] ).node();							
							if($(cellNode).find('input[type="text"]').length != 0){
								data = '<input type=\"text\" class=\"form-control\" value=\"'+ itwrRef[0][prop] +  '\">';
							}else{
								data = itwrRef[0][prop];
							}
							reportTable.cell( rowIdx, cellIdxList[i] ).data(data);
							i++;
						}
						
						cellNode = reportTable.cell( rowIdx, wrColumnIdx).node();							
						if($(cellNode).find('input[type="text"]').length != 0){
							data = '<input type=\"text\" class=\"form-control\" value=\"'+ wrNo +  '\">';
						}else{
							data = wrNo;
						}										
						reportTable.cell( rowIdx, wrColumnIdx ).data(data);
					}
				},
				error: function(result){
		 			location.href = 'error.do';
		 		}
				
			});
		}
		
		else if(headerVal == "Claim Code")
		{			
			var icellValue = $(this).closest('input').val();
			var irowIdx = $(this).closest('tr').index();
			var wrkitem;
			var icolumnsToUpdate = ["Category","Location","Billing","App Area","Business Area","BAM","DM"];  // This should be same as it exists in data_header table in DB.
			var irequiredColumnsVisible = true;
			var icolumnsToUpdateIdx;
			for(var i=0; i< icolumnsToUpdate.length ; i++ ){
				icolumnsToUpdateIdx = tableHeader.indexOf(icolumnsToUpdate[i]);
				if(visibleColumns.length != 0 && visibleColumns.indexOf(icolumnsToUpdateIdx) == -1 ){
					irequiredColumnsVisible = false;
					break;
				}
			}
			if( ! (irequiredColumnsVisible) ){
				var msg = "Required columns are not visible hence it can not be updated.";
				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
				return;
			}

				
			wrkitem = icellValue;
		
			$.ajax({
				url: 'data/wiasmRef.do?wrkitem='+wrkitem ,
				type: 'GET',
				data: false,
				success: function(wiasmRef){
					if(wiasmRef.length !=0){
						var data;
						var icellIdxList=[];
						var clmColumn = "Claim Code";
						var clmColumnIdx;
						
						for (var i=0; i< icolumnsToUpdate.length; i++ ){
							icellIdxList.push(tableHeader.indexOf( icolumnsToUpdate[i]));
						}
						
						var i=0;
						var icellNode;
						delete wiasmRef[0].wrkItem_id;
						for(var prop in wiasmRef[0]){
							icellNode = reportTable.cell( irowIdx, icellIdxList[i] ).node();							
							if($(icellNode).find('input[type="text"]').length != 0){
								data = '<input type=\"text\" class=\"form-control\" value=\"'+ wiasmRef[0][prop] +  '\">';
							}else{
								data = wiasmRef[0][prop];
							}
							reportTable.cell( irowIdx, icellIdxList[i] ).data(data);
							i++;
						}
						
					}
					
					else
						{
						var msg = "Please enter valid claim code.";
						billerAlert(msg,true, 'Okay', false, '','', "Alert !");
						return;
						}
				},
				error: function(result){
		 			location.href = 'error.do';
		 		}
				
			});
	
		}
		
		
		else{
			var data = '<input type=\"text\" class=\"form-control\" value=\"'+$(this).closest('input').val() +  '\">';
			var cell= reportTable.cell($(this).closest('td'));		
		    cell.data( data );
		}
	});

	/* Edit  button */
	$("#reportEdit").click(function(e) {
		var tower = $('#currentTower').val();
		if(!editMode[tower]){
			e.stopPropagation();
			e.preventDefault();
			return;
		}
		
		var reportTable = $('#report').DataTable();
		var reportTable2 = $('#report').dataTable();
		var rowCount = 0;
		var rowNodeList = [];
		var rowNodeIndexList = [];
						
		reportTable.rows().every(function(){
			var rowIdx = this.index();
			var rowNode = reportTable2.fnGetNodes(rowIdx);	
			if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
				rowCount = rowCount + 1;
				rowNodeList[rowCount - 1] = rowNode;
				rowNodeIndexList[rowCount - 1] = rowIdx;
			}
		});

		if (rowCount == 0) {
			var msg="You have not selected any records for editing, please select at least one record for editing.";
			billerAlert(msg,true,"Okay",false,"NO","","Notification");							
		} else if(rowCount <= 30){					
				for ( var r = 0; r< rowNodeList.length; r++){				
					var rowNode = rowNodeList[r];
					var rowIdx =  rowNodeIndexList[r];
					var rowID = '#' +  $(rowNode).find('td input[type="checkbox"]').attr('id');
					
					if($(rowNode).find('td input[type="checkbox"]').prop('checked')){	
						if(!rowEditMap[rowID]){				
							var oldData = reportTable.row(rowIdx).data();
							var tempData = [];
							for( var i=0; i<oldData.length; i++){
								tempData.push(oldData[i]);
							}
							rowEditDataMap[rowID] = tempData;
				
							if (!rowEditMap[rowID]) {						
								var i=0;
								var j=1;						
								$(rowNode).find('td').each(function(){							
									if (visibleColumns.length != 0){							
										var visibleColumnIndex = visibleColumns[i]
										var cellData = oldData[visibleColumnIndex];								
										if(!(visibleColumnIndex == 0 || visibleColumnIndex == 29 || visibleColumnIndex == 30) ){
											if(i < 29){
											$(rowNode).find('td').eq(i).html('<input type=\"text\" class=\"form-control\" value=\"'+ 
													cellData + '\">');
											}
										}
									    i = i+1;
									}else{
										if(j < 29){
											$(rowNode).find('td').eq(j).html('<input type=\"text\" class=\"form-control\" value=\"'	+
													oldData[j] + '\">');
											j = j+1;
										}
									}
								});
								rowEditMap[rowID] = true;
							}
					    }
					}
				}
				setTimeout(function(){
					$('#report th').eq(0).click();
				},500);
			}else {
				var msg="You can not edit more than 30 row.";
				billerAlert("You have already established Lock..!!",true, 'Okay', false, '','', "Alert !");
			}
	});
	
	
	// Copy Button
	$("#reportCopy").click(function(e) {
		var tower = $('#currentTower').val();
		if(!editMode[tower]){
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
		var rowNodeForCopy;
		var rowNode;
		var rowIDSelector;
		var rowIdx;
		var visibleColumnsLength = visibleColumns.length;
		
		if(!(visibleColumnsLength == 0 || visibleColumnsLength == 31)){				
			var msg = "You can not copy data in column filter mode.";
			billerAlert(msg,true, 'Okay', false, '','', "Alert !");
			return;
		}
		$(".biller-loader-div").fadeIn(1);
		
		setTimeout(function(){
		   var checkedRow = 0;
		reportTable.rows().every(function(){
			rowIdx = this.index();
			rowNode = reportTable2.fnGetNodes(rowIdx);			
			if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
				checkedRow = checkedRow + 1;
				rowNodeForCopy = rowNode;
				rowSelectedIndex = rowIdx;
				rowID =  $(rowNode).find('td input[type="checkbox"]').attr('id');
				rowIDSelector = '#' + rowID;
			}
		});
		rowCount = checkedRow;
	
		if (rowCount == 0) {
			var msg = "You have not selected any row, please select one row for copying.";
			billerAlert(msg,true, 'Okay', false, '','', "Alert !");			
			return;
		} else if(rowCount > 1){		
			var msg = "You have selected more than one row, please select one row for copying.";
			billerAlert(msg,true, 'Okay', false, '','', "Alert !");
			return;
		}else  {				
			var prevCount;
			var splitRowID;
			var copiedData = [];
			var newRowID;
			var newData = new Array();
			var tempData = [];
			var cellIndex;
			rowIdx = rowSelectedIndex;
			prevCount = copyInfo[rowID];
			splitRowID = rowID.split("_");
			if (typeof prevCount === "undefined" && splitRowID.length == 1) {
				prevCount = 0;
			} else if (typeof prevCount === "undefined" && splitRowID.length == 2) {
				prevCount = parseInt(copyInfo[splitRowID[0]]);
			}
			if (splitRowID.length == 1) {
				copyInfo[rowID] = prevCount + 1;
				newRowID = rowID + "_" + copyInfo[rowID];
			} else {
				copyInfo[splitRowID[0]] = prevCount + 1;
				newRowID = splitRowID[0] + "_" 	+ copyInfo[splitRowID[0]];
			}
			if (rowEditMap[rowIDSelector]) {
				tempData =  reportTable.row(rowIdx).data();				
				copiedData[0] = '<div class=\"checkbox\"> <input id=\"' +
					newRowID + " " +'\" type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>';				
				$(rowNodeForCopy.innerHTML).find('input[type="text"]').each(function() {
					cellIndex = $(this).closest('td').index();
					copiedData[cellIndex] = '<input type=\"text\" class=\"form-control\" value=\"'+ $(this).val()+ '\">';
				});
				for(var j=1; j<tempData.length -2 ; j++ ){
					if(copiedData[j] == null || copiedData[j] == undefined){
						copiedData[j] = tempData[j];
					} 
				}
				copiedData.push(tempData[tempData.length - 2]);
				copiedData.push(tempData[tempData.length - 1]);
			} else {
				copiedData = reportTable.row(rowIdx).data();				
			}
			for (var j = 0; j < copiedData.length; j++) {
				newData[j] = copiedData[j];
			}
			var temp = $("#report").dataTable().fnAddData(newData);
			var rowCount = reportTable.data().length - 1;
			var newRowIDSelector = "#" + newRowID;
			var newRow = $('#report').dataTable().fnGetNodes(temp);		
			insertedRow = reportTable.row(rowCount).data();
			
			// New row is added at last, looping to bring it next to copied row.
			var insertedRowViewIndex = reportTable.rows( { order: 'applied' } ).nodes().indexOf( newRow );
			var copiedRowViewIndex = reportTable.rows( { order: 'applied' } ).nodes().indexOf( rowNodeForCopy );			
			var sortMap = [];
			reportTable.rows().every(function(){
				var thisRowViewIndex = reportTable.rows( { order: 'applied' } ).nodes().indexOf( $('#report').dataTable().fnGetNodes(this) );
				var thisRowReportTableIndex = this.index();
				sortMap[thisRowViewIndex] = thisRowReportTableIndex;
			});
			for (var i = insertedRowViewIndex; i > copiedRowViewIndex + 1; i--) {
				var tempRow = reportTable.row(sortMap[i - 1]).data();				
				var tempRowInstance = $('#report').dataTable().fnGetNodes(sortMap[i - 1]);				
				var tempRowID = $(tempRowInstance).find('input[type="checkbox"]').attr("id");
				reportTable.row(sortMap[i]).data(tempRow);
				var oldRow = $('#report').dataTable().fnGetNodes(sortMap[i]);
				$(oldRow).find('input[type="checkbox"]').attr("id", tempRowID);
				reportTable.row(sortMap[i - 1]).data(insertedRow);
				var tempNewRow = $('#report').dataTable().fnGetNodes(sortMap[i - 1]);
				$(tempNewRow).find('input[type="checkbox"]').attr("id", newRowID);
			}
	
			if (rowEditMap[rowIDSelector]) {
				rowEditMap[newRowIDSelector] = true;
				copyEditMap[newRowIDSelector] = true;
			}
	
			//reportTable.draw();
			var newRowNode = reportTable2.fnGetNodes(sortMap[copiedRowViewIndex + 1]);
			$(newRowNode).find('input[type="checkbox"]').prop("checked", true);
			$(newRowNode).css("background-color", "#b7e1fe");
			
			var msg = "Data copied successfully";
			billerAlert(msg,true, 'Okay', false, '','', "Alert !");			
		}
		}, 50);
		$(".biller-loader-div").fadeOut("slow");
						
  });

	$("#reportSave").click(function(e) {
		var tower = $('#currentTower').val();
		if(!editMode[tower]){
			e.stopPropagation();
			e.preventDefault();
			return;
		}
		
		$(".biller-loader-div").fadeIn(1);		
		var reportTable = $('#report').DataTable();
		var reportTable2 = $('#report').dataTable();
		var billCycle = $('#currentBillCycle').val();
    	var tower = $('#currentTower').val();
    	var accountId = $('#currentAccount').val();
    	
    	var updateRows = [];
    	var newRows=[];
    	var saveRecords = {};
    	var updateRecords;
    	var newRecords;
    	if(visibleColumns.length != 0 && visibleColumns.length < 31){
    		var msg = "All columns should be visible while saving data";
			billerAlert(msg,true, 'Okay', false, '','', "Alert !");
			$(".biller-loader-div").fadeOut("slow");
			return;
    	}
    	setTimeout(function(){
	    	reportTable.rows().every(function(){
				var rowIdx = this.index();
				var rowNode = reportTable2.fnGetNodes(rowIdx);
				var rowID = '#' +  $(rowNode).find('input[type="checkbox"]').attr('id');
				var oldData = reportTable.row(rowIdx).data();
				if($(rowNode).find('input[type="checkbox"]').prop("checked")){    			
					var selectedRowID = rowID;
			  		var rowData = [];				  		
			  		var isCopied = selectedRowID.split("_");
			  		var rowIDSplit = selectedRowID.split("-");
			  		 
			  		if (isCopied.length ==1){
			  			rowData[0] = rowIDSplit[1] ;		  			
			  			var i = 0;
			  			
			  			$(rowNode).find('td').each(function(){
			  				if(visibleColumns.length != 0){
				  				var thisColumnIndex = visibleColumns[i];			  				
				  				var thisInputVal = $(this).find('input[type="text"]').val();
				  				
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
			  						  		
					  		for ( var k=1; k <= 30 ; k++){
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
			  			
			  			$(rowNode).find('td').each(function(){
			  				if(visibleColumns.length != 0){
				  				var thisColumnIndex = visibleColumns[i];
				  				var thisInputVal = $(this).find('input[type="text"]').val();			  				
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
			  			
				  			for ( var k=1; k <= 30 ; k++){
					  			if(rowData[k] == void 0){
					  				rowData[k] = "\"" + oldData[k] +"\"";
					  			}
					  		}
					  	
		  				newRows.push("{ \"rowID\" : " + "\"" + rowIDSplit[1] +"\"" + " , " + "\"rowData\" : [" + rowData.join(',') + ']}' );	  				
			  		}		    
				}
	    	
	    	});
    	
    	
    	updateRecords ="[" +  updateRows.join(' , ') + "]";
    	newRecords = "[" +  newRows.join(' , ') + "]";    	
    	saveRecords["updateRecords"] = JSON.parse(updateRecords);
    	saveRecords["newRecords"] = JSON.parse(newRecords);	
    	
    	
    	$.ajax({	        		
    		type: 'POST',
    		url: 'data/update.do?billCycle=' + billCycle + '&towerID=' + tower + '&accountId=' + accountId,
    		contentType: 'application/json',
    		dataType: 'json',
    		data: JSON.stringify(saveRecords),
    		success: function (result){	
    			if(result){
    				var msg = "Data saved successfully";
    				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
    				editMode[tower] = false;
    				rowEditMap = {};
    				copyInfo = {};
    				copyEditMap = {};
    				visibleColumns = [];
    				$("#reportSubmit").trigger("click");
    				
    			}else{
    				var msg = "Error occured while saving data";
    				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
    			}    			
    		},
    		error: function(result){
	 			location.href = 'error.do';
	 		}
    	});
    	}, 50);
	});

	
	

	
	$(document).on('change','#report tbody tr td input[type="checkbox"]',function() {		
		if(!headerCheckboxToggle){
		var rowID = "#" + $(this).attr("id");
			if (copyEditMap[rowID]) {
				var msg = "This operation will keep copied data in read only mode";
				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
				
				var reportTable = $('#report').DataTable();
				var reportTable2 = $('#report').dataTable();
				var row = $(this).closest('tr');
				var rowIdx = reportTable.row(row).index();
				var rowNode = reportTable2.fnGetNodes(rowIdx);
				var rowData = new Array();
				var oldData = reportTable.row(rowIdx).data();
				
				rowData[0] ='<div class=\"checkbox\"> <input id=\"'+ rowID +
					'\" type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>';
				$(rowNode.innerHTML).find('input[type="text"]').each(function() {
					var cellIndex = $(this).closest('td').index();
					rowData[cellIndex] = $(this).val();
				});
				for(var i=1; i< oldData.length ; i++){
					if( rowData[i] == null || rowData[i] == undefined){
					rowData[i] = oldData[i];
					}
				}	
				reportTable.row(rowIdx).data(rowData);				
				rowEditMap[rowID] = false;
				copyEditMap[rowID] = false;
	
			}
			if (!(this.checked) && rowEditMap[rowID] == true) {
				var msg = "This operation will undo any changes made for this record";
				billerAlert(msg,true, 'Okay', false, '','', "Alert !");
				var reportTable = $('#report').DataTable();
				var reportTable2 = $('#report').dataTable();
				var row = $(this).closest('tr');
				var rowIdx = reportTable.row(row).index();
				var rowNode = reportTable2.fnGetNodes(rowIdx);
				var oldData = rowEditDataMap[rowID];
				
				reportTable.row(rowIdx).data(oldData);
				reportTable.draw();				
				rowEditMap[rowID] = false;
				rowEditDataMap[rowID] = null;
			}
		}
	});
	
				

});

function customizeColumns() {

	var columnSelected = "["
	visibleColumns = [];	
	var hideColumns = [];		
	var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	
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
	$("#reportButtons .dropdown .dropdown-menu li.divider").nextAll().find('input[type="checkbox"]:checked').each(function() {
		visibleColumns.push(Number($(this).val()));
	});
	columnSelected = columnSelected + visibleColumns.join(", ") + "]";	
	hideColumns = $(allColumns).not(visibleColumns).get();	
	var reportTable = $('#report').DataTable();
	reportTable.columns( visibleColumns ).visible( true, true);
	for ( var i=0 ; i<hideColumns.length; i++){
		reportTable.column(hideColumns[i]).visible(false, false);
	}
	reportTable.draw();
	$('#report th').eq(0).click();
	
	
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
	headerCheckboxToggle = true;
	var prop = $(element).prop('checked');
	var reportTable = $('#report').DataTable();
	var reportTable2 = $('#report').dataTable();	
	var content;
	
	setTimeout(function(){
		reportTable.rows().every(function(){
			var rowIdx = this.index();
			var rowNode = reportTable2.fnGetNodes(rowIdx);
			var rowID = '#' +  $(rowNode).find('input[type="checkbox"]').attr('id');	
			if (copyEditMap[rowID]) {
				var rowData = new Array();
				var selector = rowID + " " + "td";
				rowData.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');
				$(rowNode).find('td input[type="text"]').each(function() {
					rowData.push($(this).val());
				});
	
				reportTable.row(rowIdx).data(rowData);
				rowEditMap[rowID] = false;
				copyEditMap[rowID] = false;
	
			}
			if (!(prop) && rowEditMap[rowID] == true) {
				var oldData = rowEditDataMap[rowID];			
				reportTable.row(rowIdx).data(oldData);
				rowEditMap[rowID] = false;
				rowEditDataMap[rowID] = null;
			}
			$(rowNode).find('td input[type="checkbox"]').prop("checked", prop);
		});
	}, 500);
	
	setTimeout(function(){
		$(".biller-loader-div").fadeOut("slow");
	}, 100);
	headerCheckboxToggle = false;
}


