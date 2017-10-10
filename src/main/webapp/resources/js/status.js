$(document).ready(function(){
	
	
	$('a[title]').tooltip({
	    trigger : 'hover'
	});
	
		
	
	$.ajax({
		url: 'data/getApprovalStatus.do',
		type: 'GET',
		data: false,		
		success: function(approvalStatus){
		 var groupApproval = approvalStatus["groupApproval"];
		 var userApprovalList = approvalStatus["userApprovalList"];
		 var userListID;
		 
		 $.each(userApprovalList, function(key,value){		
			 userListID = "#" + key;
			 for (var user in value){
				 var selector = ".billerProgress .tab-content " + userListID + " table";
				 var status = (userApprovalList[key][user]["approvalStatus"] == 1) ? "&#10004" : "&#10006";
				 var rowData = "<tr><td>"+ userApprovalList[key][user]["userName"] + "</td> <td>" + status  + "</td></tr>";
				 $(selector).find("tbody").append(rowData);
			 }
		});
		}
	});
	
});