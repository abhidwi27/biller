<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
<title>Data Table</title>
<%@ page isELIgnored="false" %>

<script src="https://code.jquery.com/jquery-1.12.4.js"> </script>
<script
	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"> </script>
<script
	src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap4.min.js"> </script>

<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.css" />"
	rel="stylesheet">
<link
	href="<c:url value="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap4.min.css" />"
	rel="stylesheet">


<style>
th {
	font-size: 12px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

td {
	font-size: 10px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
</style>
<script> 
	  $(document).ready(function() {
	        var reportTable = $('#ilcReport').DataTable({	        	
	            "scrollX": true,
	            "scrollY": 400,
	            "aoColumnDefs": [
	            	
	            	{ "bVisible": true, "aTargets": ['_all'] }
	            	]
	        }); 
	        $("input.rowcheckbox").attr("disabled", true);     
	        
	        $("#Edit").click(function(){
	        	var userID = "adwivedi";
	        	var billCycle = "0507";
	        	$.ajax({
				    url: '/Inwisey/editReport' + '?userID=' + userID + '&billCycle=' + billCycle,
				    data: false,
				    dataType: 'text',
				    processData: false,
				    contentType: false,
				    type: 'GET',
				    success: function(data){				    	
				    	if(data == "success"){	
				    		alert("Table lock established successfully.");
				    		$("input.rowcheckbox").attr("disabled", false);
	        			}
	        			else{
	        				alert("Table is already locked by " + data);
	        			}
				    }
				  });
	        });
	        
	        $("#Save").click(function(){
	        	
	        	var reportTable = $('#ilcReport').DataTable();	        	
	        	//var Records = '[';
	        	var arrRows = [];
	        	$("table tr").find('input[type="checkbox"]:checked').each(function(){
			  			var selectedChkBox = $(this).attr('name');
			  			var selectedRow = "#" + selectedChkBox;
			  			//var selectedRowIdx = reportTable.row(selectedRow).index();
				  		//var selectedRowNum = selectedRowIdx + 1;
				  		var rowData = [];
			  			$(selectedRow).find('input[type="text"]').each(function(){			  				  
			  				  rowData.push("\"" + $(this).val() +"\"")
			  			});			  			
			  			arrRows.push("{ \"rowID\" : " + selectedChkBox + " , " + "\"rowData\" : [" + rowData.join(',') + ']}' );			  			
	  		    		});	        	
	        	var Records ="[" +  arrRows.join(' , ') + "]";	       
	        	Records = JSON.parse(Records);
	        	$.ajax({	        		
	        		type: 'POST',
	        		url: 'Inwisey/Save',
	        		contentType: 'application/json',
	        		data: JSON.stringify(Records),
	        		success: function (data){	        			
	        			alert("Data saved successfully");
	        			
	        			alert("Records length is " + Records.length);
	        			for (i=0 ; i< Records.length; i++ ){	
	    			    	//var selector = "#ilcReport tr:nth-child(" + rowNum + ") td:nth-child(" + i + ")";
	    			      	var rowID = Records[i].rowID;
	    			      	var rowData = [];
	    			      	rowData = Records[i].rowData;
	    			      	
	    			      	var selector = "#" + rowID + " " + "td";
	    			      	$(selector).find('input[type="checkbox"]').prop('checked',false);
	    			      	for (j=1 ; j<= rowData.length ; j++){
	    			    		$(selector).eq(j).html(rowData[j-1]);
	    			      	}
	    			      	$('table th').eq(0).click();
	    			      	$("input.rowcheckbox").attr("disabled", true);
	    			  }
	        		}        			
	        		      		
	        	});
	        	
			});
	        
	        $("#SignOff").click(function(){
	        	
	        	var userID = "adwivedi";
	        	var billCycle = "0507";
	        	$.ajax({	        		
	        		type: 'GET',
	        		url: 'Inwisey/SignOff/' + '?userID=' + userID + '&billCycle=' + billCycle ,
	        		contentType: 'text/html',
	        		data: false,
	        		success: function (data){	        			
	        			alert("You have signed off sucessfully");
	        				      
	    			  }        			
	        	});        	
	        });
	  });
	  
	  function editRow(seqID){		  
		  var reportTable = $('#ilcReport').DataTable();		  
		  var rowID = "#" + seqID;		  
		  var rowIdx = reportTable.row(rowID).index();
		  var rowNum = rowIdx + 1;
		  var oldData = reportTable.row(rowIdx).data();  
		  var chkBox = "[name=" + seqID + "]";
		  var prop = $(chkBox).prop('checked');
		  
		  
		  if (prop){
			  for (i=1; i< oldData.length ; i++){	
			    	//var selector = "#ilcReport tr:nth-child(" + rowNum + ") td:nth-child(" + i + ")";
			      	var selector = rowID + " " + "td";
			    	$(selector).eq(i).html('<input type="text" value="'+oldData[i]+'">');		      	
			  }
			  
			  
		  }
		  else{
			  for (i=1; i< oldData.length + 1; i++){	
			    	//var selector = "#ilcReport tr:nth-child(" + rowNum + ") td:nth-child(" + i + ")";
			    	var selector = rowID + " " + "td";
			      	//$(selector).html(oldData[i-1]);
			    	$(selector).eq(i).html(oldData[i]);
			  }
		  }
		  
		 $('table th').eq(0).click();
	        
	  }

</script>
</head>
<body>
	<div class="topnav">
		<div class="nav-text">
			<h4>BILLER</h4>
		</div>
		<div class="nav-image">
			<img src="<c:url value="/resources/image/user.png"/>" />
			<!-- 		
			<img src="resources/image/user.png"></img>
		 -->
			<div id="usersetting" class="nav-dropdown">
				<ul>
					<li><a href=#> Logout </a></li>
				</ul>
			</div>
		</div>
		<div style="float: right;">
			<ul class="mid-nav">
				<li class="mid-nav-link" id="SignOff">Sign Off</li>
				<li class="mid-nav-link" id="Save">Save</li>
				<li class="mid-nav-link" id="Edit">Edit</li>
			</ul>
		</div>
	</div>
	<br>
   
	 
	<div>
		<form:form action="/ILCApp/save" method="POST">
			<table id="ilcReport"
				class="table table-striped table-bordered myclass" cellspacing="0"
				width="10%">
				<thead>
					<tr>
						<th>Select</th>
						<th>Emp ID</th>
						<th>Name</th>
						<th>Claim Code</th>
						<th>Activity</th>
						<th>Week End</th>
						<th>Total Hrs</th>
						<th>Shift</th>
						<th>US/INDIA</th>
						<th>Location</th>
						<th>Billing</th>
						<th>Category</th>
						<th>BAM</th>
						<th>App Area</th>
						<th>Business Area</th>
						<th>Month</th>
						<th>Quarter</th>
						<th>DM</th>
						<th>ASM</th>
						<th>ASD</th>
						<th>WR #</th>
						<th>Is Ticket ?</th>
						<th>Resource Type</th>
						<th>CTC WR</th>
						<th>RTC WR</th>
						<th>Planned Hours</th>
						<th>Billable?</th>
						<th>Remarks</th>
						<th>CTC/RTC</th>
						<th>Work Type</th>
						<th>WR Description</th>
						<th>Cost Center</th>
						<th>Category 2</th>
						<th>OnOffLanded</th>
						<th>Tower</th>
						<th>Last Modified</th>
						<th>ASM (ITWR)</th>
						<th>ASD (ITWR)</th>
						<th>ITWR Actuals</th>
						<th>Group</th>
						<th>Vendor Class</th>
						<th>WR/INC/DEF</th>
					</tr>
				</thead>

				<tbody>
					<c:if test="${not empty ilcDataList}">					
						<c:forEach var="o" items="${ilcDataList}">
							<tr id="${o.seqID}" class="${o.seqID}">
								<td><input type="checkbox" class="rowcheckbox"
									name="${o.seqID}" onclick="editRow(${o.seqID})" /></td>									
								<td>${o.empID}</td>
								<td>${o.empName}</td>
								<td>${o.claimCode}</td>
								<td>${o.activity}</td>
								<td>${o.weekEndDate}</td>
								<td>${o.totHrs}</td>
								<td>${o.shiftType}</td>
								<td>${o.usInd}</td>
								<td>${o.onOffShore}</td>
								<td>${o.billingType}</td>
								<td>${o.category}</td>
								<td>${o.bam}</td>
								<td>${o.appArea}</td>
								<td>${o.businessArea}</td>
								<td>${o.month}</td>
								<td>${o.quarter}</td>
								<td>${o.dm}</td>
								<td>${o.asm}</td>
								<td>${o.asd}</td>
								<td>${o.wrNo}</td>
								<td>${o.isTicket}</td>
								<td>${o.staffType}</td>
								<td>${o.isCTC}</td>
								<td>${o.isRTC}</td>
								<td>${o.plannedHrs}</td>
								<td>${o.isBillable}</td>
								<td>${o.remarks}</td>
								<td>${o.ctcOrRtc}</td>
								<td>${o.workType}</td>
								<td>${o.wrDesc}</td>
								<td>${o.costCenter}</td>
								<td>${o.category2}</td>
								<td>${o.onOffLanded}</td>
								<td>${o.tower}</td>
								<td>${o.lastModified}</td>
								<td>${o.asmItwr}</td>
								<td>${o.asdItwr}</td>
								<td>${o.itwrActual}</td>
								<td>${o.groupType}</td>
								<td>${o.vendorClass}</td>
								<td>${o.wrIncDef}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</form:form>
		
		
	</div>
	
	<div class="footer">
		<div class="footer-text">IBM Internal</div>

	</div>

</body>

</html>