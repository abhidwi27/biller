	  var copyInfo = new Array();
	  $(document).ready(function() {
		   
		  	$("#Edit").click(function(){
	        	var reportTable = $('#ilcReport').DataTable();	        	
	        	$.ajax({
				    url: '/Inwisey/Inwisey/editReport',
				    data: false,
				    dataType: 'text',
				    processData: false,
				    contentType: false,
				    type: 'GET',
				    success: function(data){				    	
				    	if(data == "success"){
				    		editMode = true;
				    		alert("Table lock established successfully.");
				    		reportTable.rows().nodes().to$().each(function(){
				    			$(this).find('input[type="checkbox"]').attr("disabled", !(editMode));
				    		});
	        			}
	        			else{
	        				editMode = true;
	        				alert("Table is already locked by " + data);
	        			}
				    }
				  });
	        });
	        
	        $("#Save").click(function(){
	        	var reportTable = $('#ilcReport').DataTable();
	        	var updateRows = [];
	        	var newRows=[];
	        	var saveRecords = {};
	        	
	        	$("table tr").find('input[type="checkbox"]:checked').each(function(){
	        			var selectedRow = $(this).parent().parent().attr("id");
			  			selectedRow = '#' + selectedRow;
				  		var rowData = [];				  		
				  		var isCopied = selectedRow.split("_");
				  		var rowIDSplit = selectedRow.split("-");
				  		if (isCopied.length ==1){
			  			$(selectedRow).find('input[type="text"]').each(function(){			  				  
			  				  rowData.push("\"" + $(this).val() +"\"")
			  			});			  		
			  				updateRows.push("{ \"rowID\" : " + rowIDSplit[1] + " , " + "\"rowData\" : [" + rowData.join(',') + ']}' );	
				  		}
				  		if(isCopied.length==2){
			  				$(selectedRow).find('input[type="text"]').each(function(){			  				  
			  				  	rowData.push("\"" + $(this).val() +"\"")
			  			});
			  				newRows.push("{ \"rowID\" : " + rowIDSplit[1] + " , " + "\"rowData\" : [" + rowData.join(',') + ']}' );
				  		}
	  		    });	
	        	
	        	var updateRecords ="[" +  updateRows.join(' , ') + "]";
	        	var newRecords = "[" +  newRows.join(' , ') + "]";
	        	saveRecords["updateRecords"] = JSON.parse(updateRecords);
	        	saveRecords["newRecords"] = JSON.parse(newRecords);	
	        	
	        	$.ajax({	        		
	        		type: 'POST',
	        		url: '/Inwisey/Inwisey/Save',
	        		contentType: 'application/json',
	        		data: JSON.stringify(saveRecords),
	        		success: function (data){	        			
	        			alert("Data saved successfully");
	        			editMode = false;
	        			for (i=0 ; i< Records.length; i++ ){
	    			      	var rowID = Records[i].rowID;
	    			      	var rowData = [];
	    			      	rowData = Records[i].rowData;
	    			      	var selector = "#" + rowID + " " + "td";
	    			      	$(selector).find('input[type="checkbox"]').prop('checked',false);
	    			      	for (j=1 ; j<= rowData.length ; j++){
	    			    		$(selector).eq(j).html(rowData[j-1]);
	    			      	}
	    			      	$('table th').eq(0).click();
	    			      	$("table tr input type=['checkbox']").attr("disabled", true);
	    			  }
	        		}        			
	        	});
			});
	        
	        $("#SignOff").click(function(){
	        	$.ajax({	        		
	        		type: 'GET',
	        		url: '/Inwisey/Inwisey/SignOff/' ,
	        		contentType: 'text/html',
	        		data: false,
	        		success: function (data){	        			
	        			alert("You have signed off sucessfully");
	    			  }        			
	        	});        	
	        });
	        
	        $("#columnSelectbtn").click(function(){	        	
	        	$(".columnSelectionList").css("display", "block");
	        });
	        
	        $("#OK").click(function(){
	        	var columnSelected = "["
	        	var columnArr = [];
	        	$(".columnSelectionList").find('input[type="checkbox"]:checked').each(function(){
	        		columnArr.push(Number($(this).val()));
	        	});
	        	columnSelected = columnSelected + columnArr.join(", ") + "]";
	       		$(".columnSelectionList").css("display", "none");	       		
	       		$('#ilcReport').DataTable().destroy();
	       		var settings = {	        	
			            "scrollX": true,
			            "scrollY": 1000,
			            "aoColumnDefs": [
			            	{ "bVisible": true, "aTargets": ['_all'] },
			            	{ "bVisible": false, "aTargets": ['_all'] }	            	
			            	]
			        }
			  	settings.aoColumnDefs[0].aTargets = columnArr ;
	       	 var reportTable = $('#ilcReport').DataTable(settings);	 
	        });
	  });
	  
	  function editRow(rowID){	
		  
		  var reportTable = $('#ilcReport').DataTable();		  
		  var row = "#" + rowID;		  
		  var rowIdx = reportTable.row(row).index();
		  var rowNum = rowIdx + 1;	
		  var oldData = reportTable.row(rowIdx).data();
		  var prop = $(row).find('input[type="checkbox"]').prop('checked');
		  if (prop){
			  for (i=1; i< oldData.length - 1 ; i++){
			      	var selector = row + " " + "td";
			    	$(selector).eq(i).html('<input type="text" value="'+oldData[i]+'">');		      	
			  }
		  }
		  else{
			  for (i=1; i< oldData.length + 1; i++){	
			    	var selector = row + " " + "td";
			    	$(selector).eq(i).html(oldData[i]);
			  }
		  }
		 $('table th').eq(0).click();
	  }
	  
	  
	  function copyRow(elemID){
		  var prevCount;
		  var splitElemID;
		  var reportTable = $('#ilcReport').DataTable();
		  var rowID = "#" + elemID;		  
		  var rowIdx = reportTable.row(rowID).index();
		  var rowNum = rowIdx + 1;
		  var copiedData = [];
		  var newRowID;
		  var newData = new Array();
		 
		  
		  if($(rowID).find('input[type="checkbox"]').prop("checked")){
			  copiedData.push('<input type=\"checkbox\" class=\"rowcheckbox\" onclick=\"editRow(this.parentNode.parentNode.id)\" />');			  
			  $(rowID).find('input[type="text"]').each(function(){				
				  copiedData.push('<input type=\"text\" value=\"'+$(this).val()+'\">');
				  
			  });
			  copiedData.push('<img src=\"resources/image/copy.png\" onclick=\"copyRow(this.parentNode.parentNode.id)\"></img> <img src=\"resources/image/cancel.png\" onclick=\"deleteRow(this.parentNode.parentNode.id)\"></img>');
			  
		  }else{
		   	copiedData = reportTable.row(rowIdx).data();
		  }
		  
		  prevCount = copyInfo[elemID];
		  splitElemID = elemID.split("_");
		  
		  if (typeof prevCount === "undefined" && splitElemID.length == 1){
			  prevCount = 0;
		  }else if(typeof prevCount === "undefined" && splitElemID.length == 2){
		     prevCount = parseInt(copyInfo[splitElemID[1]]);
		  }
		  
		  if (splitElemID.length == 1){
			  copyInfo[elemID] = prevCount + 1;
			 newRowID = copyInfo[elemID] + "_" + elemID;
		  }else{
			  copyInfo[splitElemID[1]] = prevCount + 1;
			  newRowID = copyInfo[splitElemID[1]] + "_" + splitElemID[1];
		  }
		 
		  
		  for (j=0; j< copiedData.length-1; j++){
		  	 newData[j] = copiedData[j];
		  } 
		  newData.push('<img src=\"resources/image/copy.png\" onclick=\"copyRow(this.parentNode.parentNode.id)\"></img> <img src=\"resources/image/cancel.png\" onclick=\"deleteRow(this.parentNode.parentNode.id)\"></img>');
		
		  var newRow = $("#ilcReport").dataTable().fnAddData(newData);
		  	$(newRow).attr("id", newRowID);
		  
		  
		  var rowCount = reportTable.data().length-1;
		  
		  reportTable.row(rowCount).data().DT_RowId = newRowID;
		  
		  insertedRow = reportTable.row(rowCount).data();
	    for (var i=rowCount;i>rowIdx + 1;i--) {
		        var tempRow = reportTable.row(i-1).data();
		        reportTable.row(i).data(tempRow);
		        reportTable.row(i-1).data(insertedRow);
	    } 
	    var newRowIdx = rowIdx+1; 
	    
	    
	    reportTable.rows().nodes().to$().each(function(){
    			$(this).find('input[type="checkbox"]').attr("disabled", !(editMode));
    		});
	    
	    
	    
	    reportTable.draw();
	    newRowID = "#" + newRowID;
	    if($(rowID).find('input[type="checkbox"]').prop("checked")){
	    	 $(newRowID).find('input[type="checkbox"]').prop("checked", true);
			 
		   }	 
          }
	  function deleteRow(seqID){
		  var reportTable = $('#ilcReport').DataTable();
		  rowID = "#" + seqID;
		  reportTable.rows(rowID).remove().draw();
	  }