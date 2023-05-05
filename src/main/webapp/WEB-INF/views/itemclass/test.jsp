<div class="" id="itemclass_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="itemclass_form">
	
	<div class="container">
	<div class="row">
	<div class="col-sm-12">
	<div class="tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li class="active">
							<a href="#tab_default_1" data-toggle="tab">
							GENERAL </a>
						</li>
						<!-- <li >
							<a href="#tab_default_2"  data-toggle="tab">
							SUB CLASS</a>
						</li> -->
						 <li class="disabled" id="subcls_tab">
							<a href="#tab_default_2" >
							SUB CLASS</a>
						</li> 
						<li>
							<a href="#tab_default_3" data-toggle="tab">
							IMAGE ICON </a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab_default_1">
						<div class="form-group " id="form_div_code" style="padding-top:10px;">
	                      <label for="code" class="col-sm-3 control-label lb"><spring:message
			                  code="common.code"></spring:message> <i class="text-danger">*</i></label>
	                     <div class="col-sm-3">
		                     <div class="input-group input-group-lg">
			                     <input type="text" class="form-control required code-font-size" ng-change="isCodeExistis(formData.code)"
				                   name="code" id="code" ng-model="" maxlength="10" style="width:260px !important; height:40px"
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
						<label for="name" class="col-sm-3 control-label lb"><spring:message
								code="common.name"></spring:message> <i class="text-danger">*</i></label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control required code-font-size" style="width:260px !important; height:40px"
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
					<div class="form-group" id="form_div_name">
						<label for="name" class="col-sm-3 control-label lb">Alternative name <span><i class="text-danger">*</i></span></label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control required code-font-size" style="width:260px !important; height:40px"
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
					<div class="form-group" id="form_div_name">
						<label for="name" class="col-sm-3 control-label lb">Is subclass</label>
					
						<div class="col-sm-5">
							<div class="col-sm-3">
										<div class="input-group col-sm-1">
											<md-checkbox ng-model="formData.is_subclass"
												ng-disabled="disable_all" aria-label="Checkbox 1" ng-change="enableSubClass()">
											</md-checkbox>
										</div>
									</div>
						</div>
					
					</div>
					<div class="form-group" id="form_div_id">
					<label for="parent_id" class="col-sm-3 control-label lb">Department<span><i class="text-danger">*</i></span></label>
					<div class="col-sm-5">
						
							<select class="form-control required code-font-size" id="departmnt" style="width:260px !important; height:40px"
								name="item_category_id" ng-change="filterStkitm()"
								ng-options="v.id as v.name for v in ItemCtgry"
								ng-model="">
							</select>
						
					</div>
				</div>
				<div class="form-group" id="form_div_description">
	<label for="description" class="col-sm-3 control-label"><spring:message
			code="common.description"></spring:message> <i class="text-danger"></i></label>

	<div class="col-sm-5">
		<div class="input-group">
			<textarea rows="" cols="" type="text" class="form-control "
				name="description" ng-model="formData.description"
				ng-disabled="disable_all" maxlength="250" id="description"
				placeholder="" style="width:260px"> </textarea>
			<span class="input-group-addon" id="form_div_description_error"
				style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="common.error.description"></spring:message>"></i></span>

		</div>
	</div>

</div>
				<div class="form-group" id="form_div_name">
						<label for="name" class="col-sm-3 control-label lb">Account code <span><i class="text-danger">*</i></span></label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control required code-font-size" style="width:260px !important; height:40px"
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
						</div>
						<div class="tab-pane" id="tab_default_2" ng-show="show_subclass_div">
							<div class="form-group" id="form_div_id" style="padding-top:10px">
					         <label for="parent_id" class="col-sm-3 control-label lb">Department<span><i class="text-danger">*</i></span></label>
					         <div class="col-sm-5">
						
							<select class="form-control required code-font-size" id="departmnt" style="width:260px !important; height:40px"
								name="item_category_id" ng-change="filterStkitm()"
								ng-options="v.id as v.name for v in ItemCtgry"
								ng-model="">
							</select>
						
					        </div>
				          </div>
				          
							<div class="form-group" id="form_div_fg_color">
								<label for="fg_color" class="col-sm-3 control-label ">BackGround Color </label>
								<div class=" col-sm-5">
									<div class="input-group col-sm-12">
										<input minicolors="customSettings" id="fg_color"
											name="fg_color" class="form-control fg" type="text"
											ng-model="formData.fgColor" ng-disabled="disable_all" style="width:260px !important; height:40px">
									</div>
								</div>

							</div>

						
							<div class="form-group " id="form_div_bg_color">
								<label for="bg_color" class="col-sm-3 control-label bg">Text Color</label>
								<div class=" col-sm-5">
									<div class="input-group col-sm-12">
										<input minicolors="customSettings" id="bg_color"
											name="bg_color" class="form-control " type="text"
											ng-model="formData.bgColor" ng-disabled="disable_all"  style="width:260px !important; height:40px">
									</div>
								</div>
							</div>
						
				          
					 </div>
						<div class="tab-pane" id="tab_default_3">
							<div class="form-group" id="form_div_item_thumb" style="padding-top:10px">
						<label for="uom_id" class="col-sm-2 control-label lb">Image</label>
					<div class="col-sm-6 img_upload_div">
					
					
					<div id="imgdiv" data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden">
                                                  <div id="defaultImage" style="width: 200px; height: 150px;" class="fileupload-new thumbnail" >
                                                   <!--    <img alt="" src="{{formData.itemThumb}}"> -->
                                                  </div>
                                                  <div style="border-color: rgb(210, 214, 222);" id="imgshw" style="max-width: 200px; max-height: 150px; line-height: 10px;" class="fileupload-preview fileupload-exists thumbnail">
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
						</div>
					</div>
				</div>
			</div>
			</div>
			</div>
			</div>
</form>		
</div>	
	