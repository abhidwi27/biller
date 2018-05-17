$(document).ready(function(){
	
	$('#bulkUpdateConfirm').hide();
	$('#bulkUpdateBack').hide();
	$('#bulkUpdateFirstPage').show();
	$('#bulkUpdateSecondPage').hide();
	
	
	$('#reportBulkUpdate').click(function(e){
		tower = $('#currentTower').val();
		
		if(hasApprovedBillCycle == 1 || (tower == 0)){
			e.stopPropagation();
			e.preventDefault();
			return;
		}else{
			$("#bulkUpdate").modal('toggle');
			$('#bulkUpdateFirstPage').show();
			$('#bulkUpdateBack').hide();
			$('#bulkUpdateSubmit').show();
			$('#bulkUpdateSecondPage').hide();
			$('#bulkUpdateConfirm').hide();
		}
	});
	
	$(document).on('change', '#bulkUpdateHeaderlist', function(){
		var headerId = $(this).find('option:selected').val();
		var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	    var month = $("#reportMonth").val();
		var year = $("#reportYear option:selected").text().trim();
		var tower = $("#reportTower").val();
		var billCycle = month+ year;
		var url = url = 'data/getBulkUpdateData.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&headerId=' + headerId;
		$.ajax({
			url: url,
			type: 'GET',
			success: function(bulkUpdateData){
				if(bulkUpdateData.length!=0){
					$('#bulkUpdateDataList').empty();
					for (var i=0; i<bulkUpdateData.length; i++){
						$('#bulkUpdateDataList').append('<option>' + bulkUpdateData[i] + '</option>');
					}
					$('#bulkUpdateDataList').selectpicker('refresh');
				}
			}
		});
	});
	
	$('#bulkUpdateSubmit').click(function(){
		$('#bulkUpdateFirstPage').hide();
		$('#bulkUpdateSecondPage').show();
		$('#bulkUpdateBack').show();
		$('#bulkUpdateSubmit').hide();
		$('#bulkUpdateConfirm').show();
	});
	
	$('#bulkUpdateBack').click(function(){
		$('#bulkUpdateFirstPage').show();
		$('#bulkUpdateSecondPage').hide();
		$('#bulkUpdateBack').hide();
		$('#bulkUpdateSubmit').show();
		$('#bulkUpdateConfirm').hide();
	});
	
	$('#bulkUpdateConfirm').click(function(){
		$("#bulkUpdate").modal('toggle');
		var header= $('#bulkUpdateHeaderlist').find('option:selected').text().trim();
		var value = $('#bulkUpdateDataList').find('option:selected').val();
		var headerIdx;
		if(visibleColumns.length !=0){
			 headerIdx  = tableHeader.indexOf(header);
		}else{
			headerIdx  = tableHeader.indexOf(header);			
		}
		
		if(visibleColumns.length !=0  && headerIdx == -1){			
			var msg = "Data can not be updated as selected column is not visible"
			billerAlert(msg,true, 'Okay', false, '','', "Alert !");
		}else{
			var reportTable = $('#report').DataTable();
			var reportTable2 = $('#report').dataTable();
			
			reportTable.rows().every(function(){
				rowIdx = this.index();
				var rowNode = reportTable2.fnGetNodes(rowIdx);
				if($(rowNode).find('td input[type="checkbox"]').prop('checked')){
					cellNode = reportTable.cell( rowIdx, headerIdx ).node();
					if($(cellNode).find('input[type="text"]').length != 0){
						data = '<input type=\"text\" class=\"form-control\" value=\"'+ value +  '\">';
					}else{
						data = value;
					}
					reportTable.cell( rowIdx, headerIdx ).data(data);
				}
				
			});
		}
			
	});
	
});