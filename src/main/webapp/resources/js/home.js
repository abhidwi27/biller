var dataTableInitialized = false;
$(document).ready(function(){		
		
	var userProfile = JSON.parse($('#strUserProfile').val());	
	
	$('#reportDiv').hide();
	
	$("#reportSubmit").click(function(){
		$(".biller-loader-div").fadeIn(1);		
		$("#reportPanel").hide();
		$("#reportDiv").show();
		
		if ( dataTableInitialized ) {
			  $('#report').DataTable().destroy();
			}
		
		
	     var reportDataType = $("#reportDataType .radio-inline input:radio[name='reportRadio']:checked").val();
	     
		 var month = $("#reportMonth").val();
		 var year = $("#reportYear option:selected").text().trim();
		 var tower = $("#reportTower").val();
		 var billCycle = month+ year;
		 
		 /*if(tblDataType == 0 || userProfile.roleID == 1){
			 $('#EditBtn').hide();
			 $('#SaveBtn').hide();
			 $('#SaveRlsBtn').hide();
			 $('#SignOffBtn').hide();			 
		 }*/		 
		 var url;
		 if(userProfile.roleID == 8){
			 url = 'pmo';
		 }else{
			 url = 'manage';
		 }		 
		 url = url+ '/read.do?dataType=' + reportDataType + '&billCycle=' + billCycle + '&towerID=' + tower;		 
		 $.ajax({
			    url: url,
			    dataType: false,
			    processData: false,
			    contentType: false,
			    type: 'GET',
			    success: function(data){
				    var tableJson = JSON.parse(data);			    	
	                var editMode = false;	    
				    for(var i=0 ; i< tableJson["header"].length; i++){
	                	$('#report').find("thead tr").append("<th>" + tableJson["header"][i] + "</th>");                	
	                }                
				    var settings = {   		
				    		"scrollX": true,
				            "aoColumnDefs": [
				            	{ "bVisible": true, "aTargets": ['_all'] },
				            	{ "bVisible": false, "aTargets": ['_all'] }	            	
				            	],
				            	"iDisplayLength": 10
				    			} 	
			        var reportTable = $('#report').DataTable(settings);
	                for(var i=0; i<tableJson["tableBody"].length; i++){			    	
						    //var seqID = tableJson["tableBody"][i].seqID;
						    //newRowID = "row-" + seqID;			    
						    delete tableJson["tableBody"][i].seqID;   
						     var newRowData = [];			    
						    newRowData.push('<input type=\"checkbox\" class=\"rowcheckbox\" onclick=\"editRow(this.parentNode.parentNode.id)\" />');			    
							    for (var prop in tableJson["tableBody"][i]){
							    	newRowData.push(tableJson["tableBody"][i][prop]);				    	
								 }
					    newRowData.push('<img src=\"resources/image/copy.png\" onclick=\"copyRow(this.parentNode.parentNode.id)\"></img> <img src=\"resources/image/delete.png\" onclick=\"deleteRow(this.parentNode.parentNode.id)\"></img>');
					    newRow = $("#report").dataTable().fnAddData(newRowData);
					  	//$(newRow).attr("id", newRowID);
				    }
			 			reportTable.rows().nodes().to$().each(function(){
			 				$(this).find('input[type="checkbox"]').attr("disabled", true);
			 			});			    
			    }
		 		
			  });
		 
		 	/*$.ajax({
			  url: 'resources/js/report.js',
			  dataType: "script",
			  success: function(data){					  
				  }
			});*/
		 	$(".biller-loader-div").fadeOut("slow");
	    
	});
	 
	 $("#backbtn").click(function(){		 
		 $('link[rel=stylesheet][href~="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.css"]').remove();
		 $("#tableView").hide();
		 $("#tabView").show();
	 });
	 
	 $('.tabs .tab-links a').click( function(e)  {
        var currentAttrValue = $(this).attr('href'); 
        $('.tabs ' + currentAttrValue).fadeIn(400).siblings().hide();
        $(this).parent('li').addClass('active').siblings().removeClass('active');
        e.preventDefault();
     });
		
	 $("#dmSignOffStatus").click(function(){
			$(".status_content").slideToggle("slow");
	 });

	
		
});