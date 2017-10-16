<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<head>
	<script src="resources/js/status.js"> </script>
	<link rel="stylesheet" href="resources/css/billerStatus.css">
	
</head>

<div role="tabpanel" class="tab-pane fade" id="statusTab">
	
<div class="billerProgress">
	<div class="row">
       <div class="col-md-12 board">                    
           <div class="board-inner">
               <ul class="nav nav-tabs" id="myTab">
                   <div class="liner"></div>
                   <li class="active">
                       <a href="#dmApprovalList" aria-controls="dmApprovalList" role="tab" data-toggle="tab" title="DM Group Approval Status">
             			<span class="round-tabs one">                     		
             			</span>
                       </a>
                   </li>
                   <li>
                   	<a href="#bamApprovalList" aria-controls="bamApprovalList" role="tab" data-toggle="tab" title="BAM Group Approval Status">
           			 <span class="round-tabs two">                		
            		 </span>
                   	</a>
                   </li>
                   <li>
                   	<a href="#srBamApprovalList" aria-controls="srBamApprovalList" role="tab" data-toggle="tab" title="Sr BAM Group Approval Status">
            			<span class="round-tabs three">
            			</span>
            		</a>
                   </li>
                   <li>
                   	<a href="#pmoApprovalList" aria-controls="pmoApprovalList" role="tab" data-toggle="tab" title="PMO Approval Status">
                		<span class="round-tabs four">
               		    </span>
                   	</a>
                   </li>                   
               </ul>
               </div>
           <div class="tab-content">
              <div class="tab-pane fade in active" id="dmApprovalList">
                
                  <table class="table table-striped table-bordered text-center" style="width:50%;margin-left:auto;margin-right:auto;">
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
               <table class="table table-striped table-bordered text-center" style="width:50%;margin-left:auto;margin-right:auto;">
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
               
               <table class="table table-striped table-bordered text-center" style="width:50%;margin-left:auto;margin-right:auto;">
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
               
               <table class="table table-striped table-bordered text-center" style="width:50%;margin-left:auto;margin-right:auto;">
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