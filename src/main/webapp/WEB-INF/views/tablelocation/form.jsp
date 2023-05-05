<div class="" id="tableloc_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="tableloc_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
		
	   
	   <div class="form-group" id="form_div_is_auto_layout">
			<label for="autolayout" class="col-sm-2 control-label">Is Auto Layout</label>
					<div class="col-sm-5">
						<md-checkbox name="autolayout" ng-model="is_autoLayout" 		
								aria-label="Checkbox 2"  ng-disabled="disable_all" >
						</md-checkbox>
					</div>
					
</div>

<div class="form-group" id="form_div_is_service_charges">
			<label for="service_chagre" class="col-sm-2 control-label">Service Charges</label>
					<div class="col-sm-5">
						<md-checkbox name="service_chagre" ng-model="is_serviceCharge" 		
								aria-label="Checkbox 2"  ng-disabled="disable_all" ng-change="changeServiceCharge()">
						</md-checkbox>
					</div>
					
</div>
<div class="form-group" id="form_div_on_item_amount" ng-show="is_serviceCharge">
			<div class="col-sm-2 "></div>
					<div class="col-sm-5">
						
						   <md-radio-group ng-model="on_item_amount">
                                  <md-radio-button  selected="selected" class="md-primary">On item amount</md-radio-button>
                           </md-radio-group>
						
					</div>
					
</div>
<div class="form-group " id="form_div_amount" ng-show="is_serviceCharge">
	<label for="code" class="col-sm-2 control-label">Amount <i class="text-danger">*</i></label>
	<div class="col-sm-3">
		<div class="input-group input-group-lg">
			<input type="text" class="form-control  code-font-size" 
				name="amount" id="amount" ng-model="formData.sc_amount"  maxlength="${11+settings['decimalPlace']+1}" 
				ng-disabled="disable_all" capitalize placeholder="" valid-number> <span
				class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_amount_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="Please enter amount"></i></span>
		</div>
	</div>
	<div class="col-sm-4">
		<span ng-bind="existing_code" class="existing_code_lbl"
				ng-hide="hide_code_existing_er"></span>
	</div>
</div>

<div class="form-group" id="form_div_is_percentage"  ng-show="is_serviceCharge">
			<label for="is_percentage" class="col-sm-2 control-label">Is Percentage</label>
					<div class="col-sm-5">
						<md-checkbox name="is_percentage" ng-model="isPercentage" 		
								aria-label="Checkbox 2"  ng-disabled="disable_all" >
						</md-checkbox>
					</div>
					
</div>

</form>		
</div>
<div class="" id="table_layout_form_div" ng-show="show_form1">
<div >
<i class="fa fa-th-large" aria-hidden="true"></i><b><span style="margin-left: 10px">{{formData.name}}</span></b></div>
<!-- <div class="col-md-8" style="overflow-x:scroll;display:block"> -->
<div style="display: block;"> 
<div class="col-md-8" style="display:block">
			<div id="border"  style="position:relative !important"  ng-style="{'background-image': 'url(' + bgImageUrl + ')'}">
	  			
	  		<!-- <span id="dragThis" data-drag="true" jqyoui-draggable  jqyoui-droppable="{index: {{$index}}}" > -->
	  		<!--  <div id="dragThis" data-drag="true" draggable ng-style="{left: divleft, top:divtop}" xpos="divleft" ypos="divtop" >
	  		
	  		<img ng-src="selectedImage" ng-click="" />
	  		</div> -->
	  		
	  		  <!-- <div ng-repeat="servingTable in servingTablesByLocId"> -->
      
      <!--  <span id="dragThis" data-drag="true" draggable ng-style="{left: servingTable.row_position1, top:servingTable.column_position1}" xpos="servingTable.row_position1" ypos="servingTable.column_position1" > -->
	  		
	  		<img  data-drag="true" ng-repeat="servingTable in servingTablesByLocId" id="servingTable-{{$index}}"
	  		draggable ng-style="{left: servingTable.row_position1, top:servingTable.column_position1 ,width:servingTable.width, height:servingTable.height}" 
	  		 ng-src="{{servingTable.image}}"  style="position:absolute !important ;" class="whiteBorder"
	  		ng-click="selected.value = $index;selectedInCanvas(servingTable)"/>
	  		<!-- xpos="servingTable.row_position1" ypos="servingTable.column_position1"  -->
	  		<!-- </span> -->
 <!--  </div> -->
	  		
			</div>
			<!-- <div ng-controller="MyCtrl">
 <div ng-repeat="position in positions">
      <input type="number" ng-model="position.divleft"/>
      <input type="number" ng-model="position.divtop"/>
      <p draggable class="example" ng-style="{left: position.divleft, top: position.divtop}" xpos="position.divleft" ypos="position.divtop">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
  </div> 
</div> -->
		</div>
		
		<div class="col-md-4">
			
			  <div class="col-md-12" style="padding:0;border:2px solid #3c8dbc;height:736px;background:#eee;">	
				<h3 class="tabledetailshead">Table Details</h3>
				<div class="col-md-12 addmargin" id="tablecode">
				<label>Code</label>
				<input type="text" name="code1" id="code1" class="form-control" 
				ng-model="tableData.code" ng-disabled="disableTableCode" capitalize maxlength="10">
				</div>
				<div class="col-md-12 addmargin" id="tablename">
				<label>Name</label>
				<input type="text" name="name1" id="name1" class="form-control"  ng-model="tableData.name" maxlength="50">
				</div>
				<div class="col-md-12 addmargin" id="tableseat"> 
				<label>Seat#</label>
				<input type="text" name="seat" id="seat" class="form-control" ng-model="tableData.covers" numbers-only
				maxlength="19">
				</div>
				
				<div class="col-md-12 addmargin">
				<button class="choosefile form-control chooseimg" type="button" ng-click="chooseTableImages()">Choose image</button>
				<div class="imagelayout col-md-12 addmargin">
							<img src="{{selectedImage}}"  id="imagelayoutpreview" style="vertical-align: center"  ng-style="{height:tableData.height}" />
														
						</div>
				</div>
				<div class="buttonsclass col-md-12 addmargin">
				       
				    	<div class="col-md-4"><button class="btn btn-warning commonbtn" id="popfromadd" type="button" ng-click=addServingTable()>New</button></div>
				    	<div class="col-md-4"><button class="btn btn-default commonbtn" id="popformcancel" type="button" style="display:none;">Cancel</button>
				    	<button class="btn btn-success commonbtn" type="submit" id="popfromsave" ng-click=saveServingTable()>Save</button></div>
				    	<div class="col-md-4"><button class="btn btn-danger commonbtn" type="button" id="popfromdelete" 
				    	  data-toggle="modal" data-target="#deletemodalview" ng-click="deleteServingtable()">Delete</button>
				    	</div>
				    </div>
				     <div class="col-md-12" style="padding:0;float:left;width:100%;display:block;margin-top:15px">
				   		 <h3 class="tabledetailshead">Set background image</h3>
				   		 <div class="col-md-12 addmargin">
				   		 	<input type="file" name="bgfile" class="form-control" id="bgfile" file-model="myFile1">
							<p id="diaerror" style="color:#848181;">Image size 736 x 736</p>
							<p class="spanerrors" id="imgerror"></p>
				   		 </div>
				   		 <div class="buttonsclass">
				   		 	<button class="btn btn-success" type="submit" id="bgsave" style="border-radius:0;width:50%;" ng-click="updateBgImage()">Update</button>
						<button class="btn btn-danger" type="button" data-toggle="modal" data-target="#bgimagedeletebox" id="bgdelete" 
						style="width:50%;float:right;border-radius:0" ng-click="deleteBgImage()">Delete</button>
				   		 </div>
				   </div>
				    
				   </div>
					
				  	
				   
				</div>

</div></div>
<div class="modal fade" id="addVchrClassModal" role="dialog">
	<div class="modal-dialog modal-lg">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">TABLE IMAGE</h4>
			</div>
			<div class="modal-body">
				<div class="popimglayoutdiv">
					<div class="imglist"  ng-repeat ="image in imageList" style="display: table-cell">
						
					<img ng-src="{{image.image}}" ng-click="selectImage(image);"/>

							
 

					</div>		
						
					</div>
				
			</div>

			<div class="modal-footer" >
		<div >
				

				<button class="btn  btn-sm btn-primary save_button savebtn pull-left"
					type="button" name="btnSave" ng-click="chooseTableImage();" id="btnNew"
					> New
				</button>
				<button class="btn  btn-sm btn-danger save_button savebtn pull-left"
					type="button" name="btnDelete1" id="btnDelete1" ng-disabled="disableDelete"
					ng-click="deleteFromImageList()">
					 Delete
				</button>
				<button class="btn  btn-sm btn-primary save_button savebtn pull-right" type="button" name="btnOk" id="btnOk" 
				ng-click="getSelectedImage()"  data-dismiss="modal" data-target="#importDataModal">
			     Ok
		</button> 
			</div>
			
			</div>
		</div>

	</div>
</div>

<div class="modal fade" id="addNewImageModal" role="dialog">
	<div class="modal-dialog modal-md">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">TABLE IMAGE</h4>
			</div>
			<div class="modal-body">
			 <div class="col-md-12"> 
			 <div class="col-md-3"></div>
			 <div class="col-md-6">
				<div id="imgdiv" data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden">
                                                  <div id="defaultImage" style="width: 200px; height: 150px;" class="fileupload-new thumbnail" >
                                                   <!--    <img alt="" src="{{formData.itemThumb}}"> -->
                                                  </div>
                                                  <div style="border-color: rgb(210, 214, 222);" id="imgshw" style="max-width: 200px; max-height: 150px; line-height: 10px;" class="fileupload-preview fileupload-exists thumbnail">
                                                 </div>
                                                 
                                                
                                                  <div>
                                                   <span class="btn btn-white btn-file">
                                                   <span class="fileupload-new" >Select image</span>
                                                   <span class="fileupload-exists" ng-disabled="disable_all"><i class="fa fa-undo"></i> Change</span>
                                                   <input type="file" class="default"  name="item_thumb"
							                            id="item_thumb" ng-disabled="disable_all" file-model="myFile" >
                                                   </span>
                                                      <a data-dismiss="fileupload" ng-disabled="disable_all" class="btn btn-danger fileupload-exists" href="#" ng-click="removeImage()"><i class="fa fa-trash"></i> Remove</a>
                                                  </div>
                                              </div></div>
                     <div class="col-md-3"></div>                  
                  </div>
                                              
			</div>

			<div class="modal-footer" >
		<div class="col-md-12">
				
			<div class="col-md-8"></div>
			<div class="col-md-4">
				<button class="btn  btn-sm btn-primary save_button savebtn"
					type="button" name="btnSave" ng-click="saveTableImage();" id="btnNew"
					data-dismiss="modal" data-target="#importDataModal">
					<i class="fa fa-floppy-o" aria-hidden="true"></i> Upload
				</button>
				<!-- <button class="btn  btn-sm btn-primary save_button savebtn" type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()" style="display: block;">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button> -->
			</div>
			</div>
			</div>
		</div>

	</div>
</div>
	
	