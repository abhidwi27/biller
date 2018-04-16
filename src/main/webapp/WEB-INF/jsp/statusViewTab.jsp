<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
	<title>| BILLER</title>
	<script src="resources/js/status.js"> </script>
	<link rel="stylesheet" href="resources/css/billerStatus.css">
	<link rel="icon" type="image/png" href="resources/image/biller-icon.ico"/>
</head>

<div role="tabpanel" class="tab-pane fade" id="statusTab">
	
<div class="billerProgress">
	<div class="row">
       <div class="col-md-12 board">                    
           <div class="board-inner">
		               <ul class="nav nav-tabs" id="myTab">               	   
		                   <div class="liner"></div>
		                   <li class="active" id="dmApprovalGroupTab">
		                       <a href="#dmApprovalList" aria-controls="dmApprovalList" role="tab" data-toggle="tab" title="DM Group Approval Status"
		                       		aria-expanded="true" aria-describedby="tooltipDmApproval">
		             			<span class="round-tabs one">
		             			<!--  
		             			<i class="fa fa-thumbs-o-up fa-2x" style="margin-top: 8px; color: green;" aria-hidden="true"></i>   
		             			-->          			                     		
		             			</span>
		                       </a>
		                       <div class="tooltip fade top in" role="tooltip" id="tooltipDmApproval" style="top: -13px; left: 48.4833px; display: block;">
									<div class="tooltip-arrow active" style="left: 50%;">
									</div><div class="tooltip-inner active">DM Group Approval Status</div>
							   </div>
		                   </li>		                   
		                   <li id="bamApprovalGroupTab">
		                   	<a href="#bamApprovalList" aria-controls="bamApprovalList" role="tab" data-toggle="tab" title="BAM Group Approval Status"
		                   		aria-expanded="true" aria-describedby="tooltipBamApproval">
		           			 <span class="round-tabs two">
		           			 <!-- 
		           			 <i class="fa fa-spinner fa-2x" style="margin-top: 8px; color: blue;" aria-hidden="true"></i>  
		           			  -->              		
		            		 </span>
		                   	</a>
		                   	<div class="tooltip fade top in" role="tooltip" id="tooltipBamApproval" style="top: -13px; left: 45.4833px; display: block;">
								<div class="tooltip-arrow" style="left: 50%;"></div>
								<div class="tooltip-inner">BAM Group Approval Status</div>
							</div>
		                   </li>
		                   <li id="srBamApprovalGroupTab">
		                   	<a href="#srBamApprovalList" aria-controls="srBamApprovalList" role="tab" data-toggle="tab" title="Sr BAM Group Approval Status"
		                   		aria-expanded="true" aria-describedby="tooltipSrBamApproval">
		            			<span class="round-tabs three">
		            			<!--  
		            			<i class="fa fa-ban fa-2x" style="margin-top: 8px; color: red;" aria-hidden="true"></i>
		            			-->
		            			</span>
		            		</a>
		            		<div class="tooltip fade top in" role="tooltip" id="tooltipSrBamApproval" style="top: -13px; left: 36.6px; display: block;">
								<div class="tooltip-arrow" style="left: 50%;"></div>
								<div class="tooltip-inner">Sr BAM Group Approval Status</div>
							</div>
		                   </li>
		                   <li id="pmoApprovalGroupTab">
		                   	<a href="#pmoApprovalList" aria-controls="pmoApprovalList" role="tab" data-toggle="tab" title="PMO Approval Status"
		                   	aria-expanded="true" aria-describedby="tooltipPmoApproval">
		                		<span class="round-tabs four">
		               		    </span>
		                   	</a>
		                   	<div class="tooltip fade top in" role="tooltip" id="tooltipPmoApproval" style="top: -13px; left: 60.6px; display: block;">
								<div class="tooltip-arrow" style="left: 50%;"></div>
								<div class="tooltip-inner">PMO Approval Status</div>
							</div>
		                   </li>                   
		               </ul>
		              </div>             
               
           <div class="tab-content">
              <div class="tab-pane fade in active" id="dmApprovalList">
                
                  <table class="table text-center" style="width:50%;margin-left:auto;margin-right:auto;">
	    			<thead>
				      <tr >
				        <th style="text-align:center;">Name</th>
				        <th style="text-align:center;">Approval Status</th>
				      </tr>
	    			</thead>
			    	<tbody style="vertical-align:middle;">				     
			    	</tbody>
				</table>
              
               </div>
               <div class="tab-pane fade" id="bamApprovalList">
               <!--  <p class="narrow text-center"> -->
               <table class="table text-center" style="width:50%;margin-left:auto;margin-right:auto;">
                	<thead>
				      <tr >
				        <th style="text-align:center;">Name</th>
				        <th style="text-align:center;">Approval Status</th>
				      </tr>
	    			</thead>
	    			<tbody style="vertical-align:middle;">				      
			    	</tbody> 
			    </table> 
				
               </div>
               <div class="tab-pane fade" id="srBamApprovalList">
               
               <table class="table text-center" style="width:50%;margin-left:auto;margin-right:auto;">
               		<thead>
				      <tr >
				        <th style="text-align:center;">Name</th>
				        <th style="text-align:center;">Approval Status</th>
				      </tr>
	    			</thead>
	    			<tbody style="vertical-align:middle;">				      
			    	</tbody>
			    </table>
                
               </div>
               <div class="tab-pane fade" id="pmoApprovalList">
               
               <table class="table text-center" style="width:50%;margin-left:auto;margin-right:auto;">
               		<thead>
				      <tr >
				        <th style="text-align:center;">Name</th>
				        <th style="text-align:center;">Approval Status</th>
				      </tr>
	    			</thead>
	    			<tbody style="vertical-align:middle;">				      
			    	</tbody>
                </table> 
                
               </div>
               
               <div class="clearfix"></div>
           </div>

       </div>
   </div>
 </div>
</div>