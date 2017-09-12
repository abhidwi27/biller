var rowEditMap = new Object();
var copyInfo = new Object();
var copyEditMap = new Object();

$(function() {
	$("#reportBack").click(function() {
		$('#reportDiv').hide();
		$('#reportSelection').fadeIn("slow");
		$('#report').DataTable().destroy();
		$('#report').empty();
		dataTableInitialized = false;
	});

	$("#reportTotHrs").click(function() {

	});

	$("#reportLock").click(function() {

		var tower = $('#currentTower').val();
		var billCycle = $('#currentBillCycle').val();
		var reportTable = $('#ilcReport').DataTable();

		url = 'data/lock.do?billCycle=' + billCycle + '&towerID=' + tower;

		$.ajax({
			url : url,
			data : false,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'GET',
			success : function(msg) {
				if (msg == "success") {
					editMode = true;
					alert("Table lock established successfully.");
				} else if (msg == userProfile.name) {
					alert("You have already established Lock..!!");
				} else {
					editMode = false;
					alert("Table is already locked by " + msg);
				}
			}
		});
	});

	$("#reportEdit")
			.click(
					function() {

						var reportTable = $('#report').DataTable();
						var rowCount = 0;
						$('#report tbody tr').find(
								'input[type="checkbox"]:checked').each(
								function() {
									rowCount = rowCount + 1;
								});

						if (rowCount == 0) {
							alert("You have not selected any records for editing, please select record for editing");
						} else if (rowCount == 1) {

							var rowID = "#"
									+ $('#report tbody tr').find(
											'input[type="checkbox"]:checked')
											.closest('tr').attr("id");
							var rowIdx = reportTable.row(rowID).index();
							var oldData = reportTable.row(rowIdx).data();

							if (!rowEditMap[rowID]) {
								for (i = 1; i < oldData.length; i++) {
									var selector = rowID + " " + "td";
									$(selector).eq(i).html(
											'<input type=\"text\" class=\"form-control\" value=\"'
													+ oldData[i] + '\">');
									rowEditMap[rowID] = true;
								}
							} else {
								alert("You are already editing this row ..!!")
							}

							$('#report th').eq(0).click();
						} else {
							alert("You have selected more than one records for editing, please select one record at a time");
						}

					});

	$("#reportCopy")
			.click(
					function() {
						var reportTable = $('#report').DataTable();
						var rowCount = 0;

						$('#report tbody tr').find(
								'input[type="checkbox"]:checked').each(
								function() {
									rowCount = rowCount + 1;
								});

						if (rowCount == 0) {
							alert("You have not selected any row, please select one row for copying.");
						} else if (rowCount == 1) {
							var prevCount;
							var splitRowID;
							var rowID = $('#report tbody tr').find(
									'input[type="checkbox"]:checked').closest(
									'tr').attr("id");
							var rowIDSelector = "#" + rowID;
							var rowIdx = reportTable.row(rowIDSelector).index();
							var copiedData = [];
							var newRowID;
							var newData = new Array();

							if (rowEditMap[rowIDSelector]) {
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
							// reportTable.row(rowCount).data().DT_RowId =
							// newRowID;
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
							$(newRowIDSelector).find('input[type="checkbox"]')
									.prop("checked", true);
							alert("Data copied successfully");
						} else {
							alert("You have selected more than one row, please select one row for copying.");
						}

					});

	$("#reportDelete").click(function() {

	});

	$("#reportSave").click(function() {

	});

	$("#reportReject").click(function() {

	});
	$("#reportApprove").click(function() {
		var billCycle = $('#currentBillCycle').val();
		url = 'data/approve.do?billCycle=' + billCycle;
		$.ajax({
			url : url,
			data : false,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'GET',
			success : function(msg) {
				if (msg == "approved") {
					alert("Arpproval submitted successfully");
				} else {
					alert("Error: Report could not be approved");
				}
			}
		});
	});

	$(document)
			.on(
					'change',
					'#report tbody tr .checkbox',
					function() {

						var rowID = "#" + $(this).closest('tr').attr("id");

						if (copyEditMap[rowID]) {
							alert("This operation will keep copied data in read only mode");
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
							alert("This operation will undo any changes made for this record");
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
	var columnArr = [];
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
	}

	$("#reportButtons .dropdown .dropdown-menu li.divider").nextAll().find(
			'input[type="checkbox"]:checked').each(function() {
		columnArr.push(Number($(this).val()));
	});
	columnSelected = columnSelected + columnArr.join(", ") + "]";

	$('#report').DataTable().destroy();
	dataTableInitialized = false;
	settings.aoColumnDefs[0].aTargets = columnArr;
	$('#report').DataTable(settings);
	dataTableInitialized = true;
	$('#report').DataTable().draw();
}

function modifyAllColumns(element) {
	var prop = $(element).prop('checked');
	$("#reportButtons .dropdown .dropdown-menu li.divider").nextAll().find(
			'input[type="checkbox"]').each(function() {
		$(this).prop('checked', prop);
	});
}
