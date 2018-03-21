$(document).ready(function(){
	
	
	$('a[title]').tooltip({
	    trigger : 'hover'
	});
	
		
	
	$.ajax({
		url: 'data/getApprovalStatus.do',
		type: 'GET',
		data: false,		
		success: function(approvalStatus){
		 updateStatusView(approvalStatus)
		}
	});
	
});

function updateStatusView(approvalStatus){
	var groupApproval = approvalStatus["groupApproval"];
	 var userApprovalList = approvalStatus["userApprovalList"];
	 var userListID;
	 
	 var userProfile = JSON.parse($('#strUserProfile').val());
	 
	 var roleID = userProfile.roleID;
	 var roleDesc = userProfile.roleDesc;
	 
	 $.each(groupApproval, function(key,value){
		 var selector = "#" + key + "GroupTab";
		 $(selector).find("a span i").remove();
		 if(value==2){
			 $(selector).find("a span").append("<i class=\"fa fa-thumbs-o-up fa-lg\"  style=\"color: #22C222;\" aria-hidden=\"true\"></i>");
		 }else if (value == 1){
			 $(selector).find("a span").append("<i class=\"fa fa-spinner fa-lg\"   style=\"color: #6666b2;\"aria-hidden=\"true\"></i>");
		 }else{
			 $(selector).find("a span").append("<i class=\"fa fa-ban fa-lg\"  style=\"color: #ff704d;\" aria-hidden=\"true\"></i>"); 
		 }
	 });
	 var roleDescMap = {
			 2 : "dmApproval",
	 		 3 : "bamApproval",
	 		 4 : "srBamApproval",
	 		 8 : "pmoApproval"		 		 
	 }
	 
	 var exitLoop = false;
	 
	 while(!exitLoop && roleID > 2){
		 if(groupApproval[roleDescMap[roleID]] < 2){
			$('#reportApprove').on('click', function(e) {
				   e.stopPropagation();
			});
			$('#approveView').on('click', function(e) {
				   e.stopPropagation();
			});
			exitLoop = true;
		 } 
		 roleID = roleID - 1;
	 }
	
	 
	 $.each(userApprovalList, function(key,value){		
		 userListID = "#" + key;
		 var selector = ".billerProgress .tab-content " + userListID + " table";
		 var selectorBody = selector + " tbody";
		 $(selectorBody).empty();
		 
		 for (var user in value){			 
			 var status = (userApprovalList[key][user]["approvalStatus"] == 1) ? "&#10004" : "&#10006";
			 var rowData = "<tr><td>"+ userApprovalList[key][user]["userName"] + "</td> <td>" + status  + "</td></tr>";
			 $(selector).find("tbody").append(rowData);
		 }
	});
}