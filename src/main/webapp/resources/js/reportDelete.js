$(document).ready(function(){
$('#reportDelete').click(function(e){
		if(!editMode){
			e.stopPropagation();
			e.preventDefault();
			return;		
		}else{
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
				var msg="You have not selected any records for deleting, please select at least one record for deleting.";
				billerAlert(msg,true,"Okay",false,"NO","","Notification");							
				//alert("You have not selected any records for editing, please select record for editing");
			} else {
			$("#deleteView").modal('toggle');
		}
		}
	});


$('#reportDeleteSubmit').click(function(e) {
	
	if(!editMode){
		e.stopPropagation();
		e.preventDefault();
		return;
	}
	
	var billCycle = $('#currentBillCycle').val();
	var reportTable = $('#report').DataTable();
	var reportTable2 = $('#report').dataTable();
	var seqIDList = new Array();
	
	/*$("#report  tr").find('input[type="checkbox"]:checked').each(function(){
		var selectedRowID = $(this).closest('tr').attr("id");
		var rowIDSelector = "#" + selectedRowID;
		var isCopied = selectedRowID.split("_");
		var rowIDSplit = selectedRowID.split("-");
		if (isCopied.length ==1){
			seqIDList.push(rowIDSplit[1]);
		}
		reportTable.rows(rowIDSelector).remove().draw();
		
		});*/
		reportTable.rows().every(function(){
			var rowIdx = this.index();
			var rowNode = reportTable2.fnGetNodes(rowIdx);
			var rowIDSelector = '#' +  $(rowNode).attr('id');
			if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
				
				var isCopied = rowIDSelector.split("_");
				var rowIDSplit = rowIDSelector.split("-");
				if (isCopied.length ==1){
					seqIDList.push(rowIDSplit[1]);
				}
				reportTable.rows(rowIDSelector).remove().draw();
				
			}
			
		});
		if (seqIDList.length != 0){		
			$.ajax({
				url : 'data/delete.do?billCycle=' + billCycle,
				data : JSON.stringify(seqIDList),
				contentType: 'application/json',
	    		dataType: 'json',
				type : 'POST',
				success : function(result) {
					if (result) {
						var msg = "Data deleted successfuly";
						billerAlert(msg,true, 'Okay', false, '','', "Alert !");
					}
				}
			});
		}
		
	
	

});

});