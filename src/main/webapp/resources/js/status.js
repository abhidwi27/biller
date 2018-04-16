$(document).ready(function(){
	
	/*$('a[title]').tooltip({
	    trigger : 'click'
	});*/
	
	$.ajax({
		url: 'data/getApprovalStatus.do',
		type: 'GET',
		data: false,		
		success: function(approvalStatus){
		 updateStatusView(approvalStatus)
		}
	});
	
	$('.billerProgress .nav-tabs li span.round-tabs').click(function(){
		$('.billerProgress .nav-tabs li .tooltip').find('div').each(function(){
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}
		});
		$(this).closest('li').find('.tooltip .tooltip-inner').addClass("active");
		$(this).closest('li').find('.tooltip .tooltip-arrow').addClass("active");
	});
	
});

function updateStatusView(approvalStatus){
	 var groupApproval = approvalStatus["groupApproval"];
	 var userApprovalList = approvalStatus["userApprovalList"];
	 var userListID;	 
	 var userProfile = JSON.parse($('#strUserProfile').val());	 
	 var roleID = userProfile.roleID;
	 var roleDesc = userProfile.roleDesc;
	 var exitLoop = false;
	 
	 $.each(groupApproval, function(key,value){
		 var selector = "#" + key + "GroupTab";
		 $(selector).find("a span i").remove();
		 if(value==2){
			 $(selector).find("a span").append("<i class=\"fa fa-thumbs-o-up fa-lg\"  aria-hidden=\"true\"></i>");
		 }else if (value == 1){
			 $(selector).find("a span").append("<i class=\"fa fa-spinner fa-lg\"  aria-hidden=\"true\"></i>");
		 }else{
			 $(selector).find("a span").append("<i class=\"fa fa-dot-circle-o fa-lg\"  aria-hidden=\"true\"></i>"); 
		 }
	 });
	 var roleDescMap = {
			 2 : "dmApproval",
	 		 3 : "bamApproval",
	 		 4 : "srBamApproval",
	 		 8 : "pmoApproval"		 		 
	 }
	 
	 
	 
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