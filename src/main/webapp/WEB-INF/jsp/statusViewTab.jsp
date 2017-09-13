<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>


<head>
	<link rel="stylesheet" href="resources/css/billerStatus.css">
</head>
<div role="tabpanel" class="tab-pane fade" id="status">
	
<div class="billerProgress">
<div class="row">
                <div class="col-md-12 board">
                    
                    <div class="board-inner">
                        <ul class="nav nav-tabs" id="myTab">
                            <div class="liner"></div>
                            <li class="active">
                                <a href="#home" aria-controls="home" role="tab" data-toggle="tab" title="DM">
                      <span class="round-tabs one">
                              <i class="icon icon-profile-male"></i>
                      </span>
                                </a>
                            </li>

                            <li><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" title="Sketch">
                     <span class="round-tabs two">
                         <i class="icon icon-pencil"></i>
                     </span>
                            </a>
                            </li>
                            <li><a href="#prototyping" aria-controls="prototyping" role="tab" data-toggle="tab" title="Prototyping">
                     <span class="round-tabs three">
                          <i class="icon icon-layers"></i>
                     </span> </a>
                            </li>

                            <li><a href="#uidesign" aria-controls="uidesign" role="tab" data-toggle="tab" title="UI Design">
                         <span class="round-tabs four">
                              <i class="icon icon-aperture"></i>
                         </span>
                            </a></li>

                            <li><a href="#doner" aria-controls="doner" role="tab" data-toggle="tab" title="Development">
                         <span class="round-tabs five">
                              <i class="icon icon-tools-2"></i>
                         </span> </a>
                            </li>

                        </ul>
                        </div>

                    <div class="tab-content">
                        <div class="tab-pane fade in active" id="home">
                            <p class="narrow text-center">
                                <table class="table table-striped table-bordered text-center" style="width:50%;margin-left:auto;margin-right:auto;">
										    <thead>
										      <tr >
										        <th style="text-align:center;">Name</th>
										        <th style="text-align:center;">SignoffStatus</th>
										      </tr>
										    </thead>
										    	<tbody style="vertical-align:middle;">
											      <tr>
											       <td>Jyothi</td>
												   <td>&#10004</td>
											      </tr>
											      <tr>
											        <td>Prabha</td>
													<td>&#10004</td>
											      </tr>
											      <tr>
											        <td>Vinitha</td>
													<td>&#10004</td>
											      </tr>
										    	</tbody>
  								</table>
                            </p>
                        </div>
                        <div class="tab-pane fade" id="profile">
                            <h3 class="head text-center">Sketch</h3>
                            <p class="narrow text-center">
                                Lorem ipsum dolor sit amet, his ea mollis fabellas principes. Quo mazim facilis tincidunt ut, utinam saperet facilisi an vim.
                            </p>

                            <p class="text-center">
                                <a href="" class="btn btn-success btn-outline-rounded green"> Get Quote <span style="margin-left:10px;" class="glyphicon glyphicon-send"></span></a>
                            </p>

                        </div>
                        <div class="tab-pane fade" id="prototyping">
                            <h3 class="head text-center">Prototyping</h3>
                            <p class="narrow text-center">
                                Lorem ipsum dolor sit amet, his ea mollis fabellas principes. Quo mazim facilis tincidunt ut, utinam saperet facilisi an vim.
                            </p>

                            <p class="text-center">
                                <a href="" class="btn btn-success btn-outline-rounded green"> Get Quote <span style="margin-left:10px;" class="glyphicon glyphicon-send"></span></a>
                            </p>
                        </div>
                        <div class="tab-pane fade" id="uidesign">
                            <h3 class="head text-center">UI Design</h3>
                            <p class="narrow text-center">
                                Lorem ipsum dolor sit amet, his ea mollis fabellas principes. Quo mazim facilis tincidunt ut, utinam saperet facilisi an vim.
                            </p>

                            <p class="text-center">
                                <a href="" class="btn btn-success btn-outline-rounded green"> Get Quote <span style="margin-left:10px;" class="glyphicon glyphicon-send"></span></a>
                            </p>
                        </div>
                        <div class="tab-pane fade" id="doner">
                            <div class="text-center">
                                <i class="img-intro icon-checkmark-circle"></i>
                            </div>
                            <h3 class="head text-center">Development</h3>
                            <p class="narrow text-center">
                                Lorem ipsum dolor sit amet, his ea mollis fabellas principes. Quo mazim facilis tincidunt ut, utinam saperet facilisi an vim.
                            </p>
                            <p class="text-center">
                                <a href="" class="btn btn-success btn-outline-rounded green"> Get Quote <span style="margin-left:10px;" class="glyphicon glyphicon-send"></span></a>
                            </p>
                        </div>
                        <div class="clearfix"></div>
                    </div>

                </div>
            </div>
</div>
</div>