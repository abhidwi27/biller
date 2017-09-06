$(document).ready(function(){		
		
	var userProfile = JSON.parse($('#strUserProfile').val());
		
	$("#customizeBtn").click(function(){
		$('.popup').css('display','block');
		$('link[rel=stylesheet][href~="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.css"]').remove();
		$('link[rel=stylesheet][href~="resources/css/progress_bar.css"]').remove(); 		
	});
	
	$("#customizeBtnClose").click(function(){
		$('.popup').css('display','none');
		var css1 = jQuery("<link>");
	    css1.attr({
	      rel:  "stylesheet",
	      type: "text/css",
	      href: "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.css"
	    });
	    $("head").append(css1);	    
	    var css2 = jQuery("<link>");
	    css2.attr({
	      rel:  "stylesheet",
	      type: "text/css",
	      href: "resources/css/progress_bar.css"
	    });
	    $("head").append(css2);	
	});
	
	$("#tablebtn").click(function(){
		$(".loader-div").fadeIn(1);
		$(".loader-div").css('display', 'block');
		 $("#tabView").hide();		 
		  var css1 = jQuery("<link>");
			    css1.attr({
			      rel:  "stylesheet",
			      type: "text/css",
			      href: "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.css"
			    });
	     $("head").append(css1);   
	     var css2 = jQuery("<link>");
			    css2.attr({
			      rel:  "stylesheet",
			      type: "text/css",
			      href: "resources/css/report.css"
			    });
		$("head").append(css2);		
		$("#tableView").show();
		
		if ( $.fn.DataTable.isDataTable('#ilcReport') ) {
			  $('#ilcReport').DataTable().destroy();
			}
		
		$.ajax({
			  url: 'resources/js/reportCustomize.js',
			  dataType: "script",
			  success: function(data){				  
			  }
			});
	     var tblDataType = $("#tblDataType input:radio[name='Report']:checked").val();		 
		 var month = $("#tblMonth").val();
		 var year = $("#tblYear option:selected").text();
		 var tower = $("#tblTower").val();
		 var billCycle = month+year;
		 
		 if(tblDataType == 0 || userProfile.roleID == 1){
			 $('#EditBtn').hide();
			 $('#SaveBtn').hide();
			 $('#SaveRlsBtn').hide();
			 $('#SignOffBtn').hide();			 
		 }		 
		 var url;
		 if(userProfile.roleID == 8){
			 url = 'pmo';
		 }else{
			 url = 'manage';
		 }		 
		 url = url+ '/read.do?dataType=' + tblDataType + '&billCycle=' + billCycle + '&towerID=' + tower;		 
		 $.ajax({
			    url: url,
			    dataType: false,
			    processData: false,
			    contentType: false,
			    type: 'GET',
			    success: function(data){
				    var tableJson = JSON.parse(data);			    	
	                var editMode = false;	    
				    for(i=0 ; i< tableJson["header"].length; i++){
	                	$('#ilcReport').find("thead tr").append("<th>" + tableJson["header"][i] + "</th>");                	
	                }                
				    var settings = {   		
				    		"scrollX": true,
				            "aoColumnDefs": [
				            	{ "bVisible": true, "aTargets": ['_all'] },
				            	{ "bVisible": false, "aTargets": ['_all'] }	            	
				            	],
				            	"iDisplayLength": 10
				    			} 	
			        var reportTable = $('#ilcReport').DataTable(settings);
	                for(i=0; i<tableJson["tableBody"].length; i++){			    	
						    var seqID = tableJson["tableBody"][i].seqID;
						    newRowID = "row-" + seqID;			    
						    delete tableJson["tableBody"][i].seqID;   
						     var newRowData = [];			    
						    newRowData.push('<input type=\"checkbox\" class=\"rowcheckbox\" onclick=\"editRow(this.parentNode.parentNode.id)\" />');			    
							    for (var prop in tableJson["tableBody"][i]){
							    	newRowData.push(tableJson["tableBody"][i][prop]);				    	
								 }
					    newRowData.push('<img src=\"resources/image/copy.png\" onclick=\"copyRow(this.parentNode.parentNode.id)\"></img> <img src=\"resources/image/delete.png\" onclick=\"deleteRow(this.parentNode.parentNode.id)\"></img>');
					    newRow = $("#ilcReport").dataTable().fnAddData(newRowData);
					  	$(newRow).attr("id", newRowID);
				    }
			 			reportTable.rows().nodes().to$().each(function(){
			 				$(this).find('input[type="checkbox"]').attr("disabled", true);
			 			});			    
			    }
		 		
			  });
		 
		 	$.ajax({
			  url: 'resources/js/report.js',
			  dataType: "script",
			  success: function(data){					  
				  }
			});
		 	$(".loader-div").fadeOut("slow");
	    
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