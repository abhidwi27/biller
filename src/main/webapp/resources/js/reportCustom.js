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
		var url;
		var dataFilter = {};
		
		dataFilter["dataType"] = dataType;
		dataFilter["billCycle"] = billCycle;
		dataFilter["towerID"]= tower;
		dataFilter["wrNo"]= wr;
		dataFilter["weekEndDate"]= weekend;
		dataFilter["empName"]= emp;
		
		var dataFilter = JSON.stringify(dataFilter);
		$('#report').DataTable().clear();
		$("#report").DataTable().draw();
 
		url = 'data/readCustom.do';
		 $.ajax({
			    url: url,
			    data: dataFilter,
			    contentType: 'application/json',
			    processData:false,
			    type: 'POST',
			    success: function(tableBody){
			    	var rowNo;
	                for(rowNo=0; rowNo<tableBody.length; rowNo++){
	                	if(!(dataType=0)){
						    var seqID = tableBody[rowNo].seqID;
						    newRowID = "row-" + seqID;			    
						   delete tableBody[rowNo].seqID;
	                	}
						     var newRowData = [];			    
						    newRowData.push('<div class=\"checkbox\"> <input type=\"checkbox\"  class=\"styled\"/>  <label > </label> </div>');			    
							    for (var prop in tableBody[rowNo]){
							    	newRowData.push(tableBody[rowNo][prop]);				    	
								 }
					    
						var temp  = $("#report").dataTable().fnAddData(newRowData);
						if (!(reportDataType == 0)){
					    	var newRow = $('#report').dataTable().fnGetNodes(temp);
					    		$(newRow).attr('id', newRowID);					    	
					    }
					  	
				    }
	                $("#report").DataTable().draw();
			    }
		 		
			  });	 
		 	  $(".biller-loader-div").fadeOut("slow");
	});
	
});