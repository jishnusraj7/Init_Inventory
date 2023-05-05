
<div class="" id="itemclass_form_div" ng-show="show_form" ng-cloak>  
<form class="form-horizontal" id="itemclass_form">
	
	<div class="">
	<div class="row">
	<div class="col-sm-12">
  <md-content>
    <md-tabs md-dynamic-height md-border-bottom md-selected="selectedIndexTab" >
      <md-tab label="GENERAL">
        <md-content class="md-padding">
         <div class="form-group " id="form_div_code" style="padding-top:10px;">
	                      <label for="code" class="col-sm-3 control-label code-lbl-font-size"><spring:message
			                  code="common.code"></spring:message> <i class="text-danger">*</i></label>
	                     <div class="col-sm-3">
		                     <div class="input-group input-group-lg">
			                     <input type="text" class="form-control required code-font-size" ng-change="isCodeExistis(formData.code)"
				                   name="code" id="code" ng-model="formData.code" maxlength="10" 
				                   ng-disabled="disable_code" capitalize placeholder=""> <span
				                  class="input-group-addon" min="0" max="99" number-mask=""
				                  id="form_div_code_error" style="display: none;"><i
				                  class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				                  data-placement="bottom" title=""
		                          data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
		                     </div>
		                 </div>
	                   <div class="col-sm-4">
		                    <span ng-bind="existing_code" class="existing_code_lbl"
				              ng-hide="hide_code_existing_er"></span>
	                   </div>
                     </div>
					<div class="form-group" id="form_div_name">
						<label for="name" class="col-sm-3 control-label code-lbl-font-size"><spring:message
								code="common.name"></spring:message> <i class="text-danger">*</i></label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control required code-font-size" 
									name="name" id="name" ng-model="formData.name"
									ng-disabled="disable_all" maxlength="50" placeholder="">
								<span class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>
						<div class="form-group" id="form_div_description">
	<label for="description" class="col-sm-3 control-label"><spring:message
			code="common.description"></spring:message> </label>

	<div class="col-sm-5">
		<div class="input-group">
			<textarea rows="" cols="" type="text" class="form-control "
				name="description" ng-model="formData.description"
				ng-disabled="disable_all" maxlength="250" id="description"
				placeholder="" > </textarea>
			<span class="input-group-addon" id="form_div_description_error"
				style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="common.error.description"></spring:message>"></i></span>

		</div>
	</div>

</div>
					<div class="form-group" id="form_div_alter_name">
						<label for="alter_name" class="col-sm-3 control-label lb">Alternative name </label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" 
									name="alter_name" id="alter_name" ng-model="formData.alternative_name"
									ng-disabled="disable_all" maxlength="50" placeholder="">
								<span class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>
					<div class="form-group" id="form_div_subclass">
						<label for="name" class="col-sm-3 control-label lb">Is subclass</label>
					
						<div class="col-sm-5">
							
									<md-checkbox name="fe" ng-model="is_subclass" 		
										aria-label="Checkbox 1" ng-change="changeToSubClss()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
					
					</div>
				
					<div class="form-group " id="form_div_department_code" ng-show="!is_subclass">
					<label for="department_code" class="col-sm-3 control-label"><spring:message
							code="stockin.department"></spring:message> <i
						class="text-danger">*</i></label>
					<div class="col-sm-3">
						<div class="input-group">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.departmentId"> <input type="text"
								class="form-control " name="department_code"
								id="department_code" ng-model="department_code"
								ng-disabled="disable_all" placeholder="Code"><span
								class="input-group-addon" id="form_div_department_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockin.error.dep"></spring:message>"></i></span>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="input-group">
							<input type="text" class="form-control "
								name="department_name" id="department_name"
								ng-model="department_name" ng-disabled="true"
								placeholder="">
						</div>
					</div>
				</div>
				
				
				<!-- 	<div class="form-group" id="form_div_department" ng-show="!formData.is_subclass">
					<label for="parent_id" class="col-sm-3 control-label lb">Department</label>
					<div class="col-sm-5">
						
							<select class="form-control required code-font-size" id="departmnt" style="width:260px !important; height:40px"
								name="item_category_id" 
								ng-options="v.id as v.name for v in departmentList"
								ng-model="formData.department_id" ng-change="filterDepartment()">
							</select>
						
					</div>
				</div> -->
			
				<div class="form-group" id="form_div_acc_code">
						<label for="accnt_code" class="col-sm-3 control-label lb">Account code </label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="accnt_code" id="accnt_code" ng-model="formData.account_code"
									ng-disabled="disable_all" maxlength="50" placeholder="">
								<span class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>
          
        </md-content>
      </md-tab>
      <md-tab label="SUB CLASS" ng-disabled="!is_subclass">
        <md-content class="md-padding">
         
        <div class="form-group" id="form_div_supr_class" >
					<label for="parent_id" class="col-sm-3 control-label lb">Super Class<i class="text-danger">*</i></label>
					<div class="col-sm-5">
						
							<select class="form-control  code-font-size" id="super_class"
								name="super_class_name" 
								ng-options="v.id as v.name for v in superClassList"
								ng-model="formData.super_class_id" ng-change="filterSuperClass()" ng-disabled="disable_all">
							</select>
									<span class="input-group-addon" id="form_div_super_class_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemclass.error.superclass"></spring:message>"></i></span>
							
						
					</div>
				</div>
				          
				          
				  
				
				
				      <div class="form-group" id="form_div_hsn_code" >
					<label for="parent_id" class="col-sm-3 control-label lb">HSN Code</label>
					<div class="col-sm-4">
						
							<input  id="hsn_code"
											name="hsn_code" class="form-control fg" type="text"
											ng-model="formData.hsn_code" ng-disabled="disable_all" >
							
						
					</div>
				</div>
				
				
				
				        <div class="form-group" id="form_div_print_order" >
					<label for="parent_id" class="col-sm-3 control-label lb">Print Order</label>
					<div class="col-sm-4">
						 
							<input  id="print_order"  valid-number
											name="print_order" class="form-control fg" type="text"
											ng-model="formData.print_order" ng-disabled="disable_all" >
							
						
					</div>
				</div>
				
				          
							<div class="form-group" id="form_div_bg_color">
								<label for="bg_color" class="col-sm-3 control-label ">BackGround Color </label>
								<div class=" col-sm-5">
									<div class="input-group col-sm-12">
										<input minicolors="customSettings" id="bg_color"
											name="bg_color" class="form-control fg" type="text"
											ng-model="formData.bgColor" ng-disabled="disable_all" >
									</div>
								</div>

							</div>

						
							<div class="form-group " id="form_div_text_color">
								<label for="text_color" class="col-sm-3 control-label bg">Text Color</label>
								<div class=" col-sm-5">
									<div class="input-group col-sm-12">
										<input minicolors="customSettings" id="text_color"
											name="text_color" class="form-control " type="text"
											ng-model="formData.textColor" ng-disabled="disable_all"  >
									</div>
								</div>
							</div>
							
							<div class="form-group" id="form_div_apply_theme">
						<label for="name" class="col-sm-3 control-label lb">Apply These Colors To Sale Themes </label>
					
						<div class="col-sm-5">
						
										
											<md-checkbox name="apply_theme" ng-model="is_apply_theme" 		
												 aria-label="Checkbox 2"  ng-disabled="disable_all" >
											</md-checkbox>
										
									
						</div>
					
					</div>
        </md-content>
      </md-tab>
      <md-tab label="IMAGE ICON">
        <md-content class="md-padding">
          
          <div class="form-group" id="form_div_item_thumb" style="padding-top:10px">
						<label for="uom_id" class="col-sm-2 control-label lb">Image</label>
					<div class="col-sm-6 img_upload_div">
					
					
					<div id="imgdiv" data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden">
                                                  <div id="defaultImage" style="width: 200px; height: 150px;" class="fileupload-new thumbnail" >
                                                   <!--    <img alt="" src="{{formData.itemThumb}}"> -->
                                       <img id="preview" src="data:image/jpg;base64,/mrp/resources/common/images/img.png" style="display: block;">
                                       
                                                  </div>
                                                  <div style="border-color: rgb(210, 214, 222);" id="imgshw" style="max-width: 200px; max-height: 150px; line-height: 10px;" class="fileupload-preview fileupload-exists thumbnail">
                                                 <!--  <img data-ng-src="data:image/jpg;base64,{{formData.itemThumb}}" data-err-src="images/png/avatar.png"/> -->
                                                 </div>
                                                 
                                                
                                                  <div>
                                                   <span class="btn btn-white btn-file">
                                                   <span class="fileupload-new" ><i class="fa fa-paper-clip" ></i> Select image</span>
                                                   <span class="fileupload-exists" ng-disabled="disable_all"><i class="fa fa-undo"></i> Change</span>
                                                   <input type="file" class="default"  name="item_thumb"
							                            id="item_thumb" ng-disabled="disable_all" file-model="myFile" >
                                                   </span>
                                                      <a data-dismiss="fileupload" ng-disabled="disable_all" class="btn btn-danger fileupload-exists" href="#" ng-click="removeImage()"><i class="fa fa-trash"></i> Remove</a>
                                                  </div>
                                              </div>
					
				
				
						</div>
					
					</div>
        </md-content>
      </md-tab>
    </md-tabs>
  </md-content>

</div>
</div>
</div>
</form>
</div>




 
	