

<div class="btm_itemmaster" id="item_master_form_div"
	ng-show="show_form2">
	<input type="hidden" id="id_modal" name="id_modal" value=""
		ng-model="formData1.id">
	<div class="form-group " id="form_div_code_modal_list">
		<label for="code_modal" class="col-sm-3 control-label "><spring:message
				code="common.code"></spring:message> <i class="text-danger">*</i></label>
		<div class="col-sm-6">
			<div class="input-group">
				<input type="text" class="form-control bold" maxlength="10"
					name="code_modal" id="code_modal" ng-model="formData1.code"
					ng-disabled="disable_all_popup" capitalize placeholder=""
					ng-change="isCodeExistis1(formData1.code)">
				<%-- <span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_code_modal_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
								<span ng-bind="existing_code1" class="existing_code_lbl"
									ng-hide="hide_code_existing_er1"></span> --%>
			</div>
		</div>
	</div>
	<div class="form-group" id="form_div_name_modal_list">
		<label for="name_modal" class="col-sm-3 control-label "><spring:message
				code="common.name"></spring:message> <i class="text-danger">*</i></label>

		<div class="col-sm-6">
			<div class="input-group">
				<input type="text" class="form-control" name="name_modal"
					id="name_modal" ng-model="formData1.name" maxlength="50"
					ng-disabled="disable_all_popup" placeholder="">
				<%-- <span
									class="input-group-addon" id="form_div_name_modal_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span> --%>
			</div>
		</div>

	</div>


	<!-- 	<div class="form-group" id="form_div_item_class_modal">
					<label for="item_class_modal" class="col-sm-3 control-label "><spring:message
							code="itemmaster.itemClass"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control" id="item_class_modal"
								name="item_class_modal"
								ng-options="v.id as v.name for v in itemClass"
								 ng-model="formData1.item_class_id"
								ng-disabled="disable_all">
								
								<option value="" selected="selected">select</option>
							</select>
						</div>
					</div>
				</div> -->




	<div class="form-group" id="form_div_group_item_modal_list">
		<label for="group_item_modal" class="col-sm-3 control-label "><spring:message
				code="itemmaster.groupItemModal"></spring:message></label>
		<div class="col-sm-4">
			<div class="input-group col-sm-12">
				<select class="form-control" id="group_item_modal"
					name="group_item_modal"
					ng-options="v.id as v.name for v in groupItem"
					ng-model="formData1.group_item_id" ng-disabled="disable_all_popup">

					<option value="" selected="selected">select</option>
				</select>
			</div>
		</div>
	</div>





	<div class="form-group" id="form_div_fg_color_modal_list">
		<label for="fg_color_modal" class="col-sm-3 control-label "><spring:message
				code="itemmaster.fg_color"></spring:message> <!-- <i
									class="text-danger">*</i> --></label>
		<div class=" col-sm-5">
			<div class="input-group col-sm-12">
				<input minicolors="customSettings" id="fg_color_modal"
					name="fg_color_modal" class="form-control fg" type="text"
					ng-model="formData1.fg_color" ng-disabled="disable_all_popup">
			</div>
		</div>

	</div>


	<div class="form-group " id="form_div_bg_color_modal_list">
		<label for="bg_color_modal" class="col-sm-3 control-label bg"><spring:message
				code="itemmaster.bg_color"></spring:message></label>
		<div class=" col-sm-5">
			<div class="input-group col-sm-12">
				<input minicolors="customSettings" id="bg_color_modal"
					name="bg_color_modal" class="form-control " type="text"
					ng-model="formData1.bg_color" ng-disabled="disable_all_popup">
			</div>
		</div>
	</div>







</div>


<div class="" id="item_master_form_div" ng-show="show_form">
	<form class="form-horizontal" id="item_master_form">
		<div class="">
			<%-- <jsp:directive.include file="../common/includes/common_form.jsp" /> --%>

			<div class="col-sm-12">
				<div class="col-sm-6">
					<input type="hidden" id="batchid" name="batchid" value=""
						ng-model="formData.batchid">
					<div class="form-group " id="form_div_code">
						<label for="code"
							class="col-sm-3 control-label  code-lbl-font-size"><spring:message
								code="common.code"></spring:message> <i class="text-danger">*</i></label>
						<div class="col-sm-6">
							<div class="input-group">
								<input type="text" class="form-control required bold "
									ng-change="isCodeExistis(formData.code)" capitalize
									maxlength="10" name="code" id="code" ng-model="formData.code"
									ng-disabled="disable_code" placeholder=""> <span
									class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_code_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
								<span ng-bind="existing_code" class="existing_code_lbl"
									ng-hide="hide_code_existing_er"></span>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_name">
						<label for="name"
							class="col-sm-3 control-label lb code-l-font-size"><spring:message
								code="common.name"></spring:message> <i class="text-danger">*</i></label>

						<div class="col-sm-9">
							<div class="input-group"  style="width:100%">
								<input type="text" class="form-control required bold"
									name="name" id="name" ng-model="formData.name" maxlength="50"
									ng-disabled="disable_all" placeholder=""> <span
									class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>

					</div>

					<div class="form-group" id="form_div_is_manufactured">
						<label for="itemType" class="col-sm-3 control-label lb"><spring:message
								code="itemmaster.itemType"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-9">
							<div class="input-group col-sm-12">
								<select class="form-control" id="is_manufactured"
									name="is_manufactured" ng-change="chkItemType()"
									ng-model="formData.isManufactured" ng-disabled="disable_all">
									<option value="" ng-disabled="true">Select</option>
									<c:forEach items="${enumitemType}" var="enumitemType">
										<option value="${enumitemType.getenumitemTypeId()}">${enumitemType.getenumitemTypeName() }</option>
									</c:forEach>
								</select> <span class="input-group-addon"
									id="form_div_is_manufactured_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.is_manufactured.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_profit_is_sellable">
						<label for="is_sellable" class="col-sm-3 control-label lb"><spring:message
								code="itemmaster.is_sellable"></spring:message></label>
						<div class="col-sm-9">
							<div class="input-group col-sm-12">
								<select class="form-control" id="is_sellable" name="is_sellable"
									ng-change="chkSellable()"
									ng-options="v.id as v.name for v in sampleSellable"
									id="is_sellable" ng-model="formData.isSellable"
									ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>

				</div>
				<div class="col-sm-6">

					<div class="container col-sm-12">
						<div class="row">
							<div class="col-sm-4" style="max-width: 155px;">
								<div class="form-group" id="form_div_min_stock">
									<label for="min_stock" class="col-sm-10 control-label lb"><spring:message
											code="itemmaster.min_stock"></spring:message> <!-- <i
										class="text-danger">*</i> --></label>
									<div class="col-sm-9">
										<div class="input-group col-sm-12">
											<input type="text" class="form-control algn_rgt newFont"
												name="min_stock" id="min_stock" ng-model="formData.minStock"
												ng-disabled="disable_all" placeholder="" valid-number
												select-on-click maxlength="9">
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-4" style="max-width: 155px;">
								<div class="form-group" id="form_div_max_stock" >
									<label for="max_stock" class="col-sm-10 control-label lblmax lb"><spring:message
											code="itemmaster.max_stock"></spring:message> <!-- <i
										class="text-danger">*</i> --></label>
									<div class="col-sm-9">
										<div class="input-group col-sm-12">
											<input type="text" class="form-control algn_rgt newFont"
												name="max_stock" id="max_stock" ng-model="formData.maxStock"
												ng-disabled="disable_all" placeholder="" valid-number
												maxlength="9" select-on-click>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-4" style="max-width: 155px;">
								<div class="form-group" id="form_div_std_purchase_qty">
									<label for="std_purchase_qty"
										class="col-sm-0 control-label lblpurchase_qty lb"><spring:message
											code="itemmaster.std_purchase_qty"></spring:message> <!-- <i
										class="text-danger">*</i> --></label>
									<div class="col-sm-11">
										<div class="input-group col-sm-12">
											<input type="text" class="form-control algn_rgt newFont"
												name="std_purchase_qty" id="std_purchase_qty"
												ng-model="formData.stdPurchaseQty" ng-disabled="disable_all"
												placeholder="" valid-number select-on-click maxlength="9">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-8" style="
    max-width: 305px;
">

								<div class="form-group" id="form_div_pack_contains">
									<label for="weight" class="col-sm-5 control-label lb"><spring:message
											code="itemmaster.weight"></spring:message> <!-- <i
										class="text-danger">*</i> --> </label>
									<div class="col-sm-10" style="
    margin-left: -10px;
">
										<div class=" col-sm-6" >
											<input type="text" class="form-control algn_rgt newFont"
												name="pack_contains" id="pack_contains"
												ng-model="formData.packContains" ng-disabled="disable_all"
												placeholder="" valid-number maxlength="5" select-on-click>
										</div>
										<div class=" col-sm-5" style="margin-left: -31px;     max-width: 115px;">
										
										<select class="form-control measurementClass" id="" name="uo_id"
									ng-options="v.id as v.uom_symbol for v in filluompack" id=""
									ng-model="formData.pack_uom_id" ng-disabled="disable_all">
								</select> <span class="input-group-addon" id="form_div_uomSybmol_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title=" Pack Uom Id cannot e blank<!-- <spring:message code="itemmaster.uom_id.error"></spring:message> -->"></i></span>
										
										
										</div>
										
									</div>
								</div>

							</div>
							
		<%-- 					<div class="col-sm-4">

								<div class="form-group" id="form_div_uomSybmol">
									<label for="weight" class="col-sm-8 control-label lb">UOM<!-- <spring:message
											code="itemmaster.weight"></spring:message> --> <!-- <i
										class="text-danger">*</i> --> </label>
									<div class="col-sm-14">
										<div class="input-group col-sm-12">
										<select class="form-control" id="uom_id" name="uom_id"
									ng-options="v.id as v.name for v in filluom" id="uom_id"
									ng-model="formData.pack_uom_id" ng-disabled="disable_all">
								</select> <span class="input-group-addon" id="form_div_uomSybmol_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title=" Pack Uom Id cannot e blank<!-- <spring:message code="itemmaster.uom_id.error"></spring:message> -->"></i></span>
										</div>
									</div>
								</div>

							</div> --%>
							
							
							
									
									
							<div class="col-sm-4" style=" margin-left: -147px;max-width: 155px;" >
								<div class="form-group" id="form_div_shelf_life">
									<label for="shelf_life" class="col-sm-10 control-label lb" style="
    margin-left: -10px;
"><spring:message
											code="itemmaster.shelf_life"></spring:message> <!-- <i
										class="text-danger">*</i> --></label>
									<div class="col-sm-9">
										<div class="input-group col-sm-12">
											<input type="text" class="form-control algn_rgt newFont"
												name="shelf_life" id="shelf_life"
												ng-model="formData.shelfLife" ng-disabled="disable_all"
												placeholder="" valid-number maxlength="9" select-on-click>
										</div>
									</div>
								</div>
	
							</div>
						
						<div class="col-sm-3" style="max-width: 110px;">
								<div class="form-group" id="form_div_lead_time">
									<label for="lead_time" class="col-sm-15 control-label lb"><spring:message
											code="itemmaster.lead_time"></spring:message> <!-- <i
										class="text-danger">*</i> --></label>
									<div class="col-sm-15">
										<div class="input-group col-sm-12">
											<input type="text" class="form-control algn_rgt newFont"
												name="lead_time" id="lead_time" ng-model="formData.leadTime"
												ng-disabled="disable_all" placeholder="" valid-number
												maxlength="9" select-on-click>
										</div>
									</div>
								</div>
							</div>
						
						</div>
						
						
						<div class="row">
						
						<!-- 	<div class="col-sm-4">
								<div class="form-group" id="form_div_lead_time">
									<label for="lead_time" class="col-sm-10 control-label lb"><spring:message
											code="itemmaster.lead_time"></spring:message> <i
										class="text-danger">*</i></label>
									<div class="col-sm-11">
										<div class="input-group col-sm-12">
											<input type="text" class="form-control algn_rgt newFont"
												name="lead_time" id="lead_time" ng-model="formData.leadTime"
												ng-disabled="disable_all" placeholder="" valid-number
												maxlength="9" select-on-click>
										</div>
									</div>
								</div>
							</div> -->
							
							
							<div class="col-sm-4">
								<div class="form-group" id="form_div_is_active">
									<label for="is_active" class="col-sm-0 control-label lbactive"><spring:message
											code="itemmaster.isInactive"></spring:message></label>
									<div class="col-sm-3">
										<div class="input-group col-sm-1">
											<md-checkbox ng-model="formData.is_active"
												ng-disabled="disable_all" aria-label="Checkbox 1">
											</md-checkbox>
										</div>
									</div>
								</div>
							</div>
							
							
								<div class="col-sm-4">
								<div class="form-group" id="form_div_is_synchable">
									<label for="is_active" class="col-sm-0 control-label lbactive"><spring:message
											code="itemmaster.isSynchable"></spring:message></label>
									<div class="col-sm-3">
										<div class="input-group col-sm-1">
											<md-checkbox ng-model="formData.isSynchable"
												ng-disabled="disable_all" aria-label="Checkbox 1">
											</md-checkbox>
										</div>
									</div>
								</div>
							</div>
							
							
<!-- 
							<div class="col-sm-5">
								<div class="form-group" id="form_div_is_synchable">
									<label for="is_active" class="col-sm-0 control-label lbactive"><spring:message
											code="itemmaster.isSynchable"></spring:message></label>
									<div class="col-sm-3">
										<div class="input-group col-sm-1">
											<md-checkbox ng-model="formData.isSynchable"
												ng-disabled="disable_all" aria-label="Checkbox 1">
											</md-checkbox>
										</div>
									</div>
								</div>
							</div> -->

						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-12">

				<md-content> <md-tabs md-selected="selectedIndex"
					md-dynamic-height md-border-bottom> <md-tab
					label="General" id="tab1"> <md-content class="md-padding">
				<!-- <h4>GENERAL</h4> --> <!-- <div class="divgnrl"> -->
				<div class="divGeneral">

					<div class="form-group" id="form_div_item_category_id">
						<label for="parent_id" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.item_category_id"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="item_category_id"
									name="item_category_id"
									ng-options="v.id as v.name for v in categoryList"
									id="item_category_id" ng-model="formData.itemCategoryId"
									ng-disabled="disable_all">
								</select> <span class="input-group-addon"
									id="form_div_item_category_id_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.item_category_id.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>



					<div class="form-group" id="form_div_kitchen_id" ng-show="true">
						<label for="kitchen_id" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.kitchen_id"></spring:message></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="kitchen_id" name="kitchen_id"
									ng-options="v.id as v.name for v in kitchen"
									ng-model="formData.kitchenId" ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>







					<div class="form-group" id="form_div_is_batch">
						<label for="is_batch" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.is_batch"></spring:message> <!-- <i
							class="text-danger">*</i> --></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="is_batch" name="is_batch"
									ng-options="v.id as v.name for v in sampleBatch" id="is_batch"
									ng-model="formData.isBatch" ng-disabled="disablebatch">
								</select>
							</div>
						</div>
					</div>

					<div class="form-group" id="form_div_profit_category_id lb">
						<label for="profit_category_id" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.profit_category_id"></spring:message> </label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="profit_category_id"
									name="profit_category_id"
									ng-options="v.id as v.name for v in profitList"
									id="profit_category_id" ng-model="formData.profitCategoryId"
									ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_description">
						<label for="description" class="col-sm-2 control-label lb"><spring:message
								code="common.description"></spring:message> <!-- <i
							class="text-danger">*</i> --> </label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<textarea type="text" class="form-control" name="description"
									id="description" ng-model="formData.description"
									ng-disabled="disable_all" placeholder="" maxlength="250"></textarea>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_uom_id">
						<label for="uom_id" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.uom_id"></spring:message> <i
							class="text-danger">*</i></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="uom_id" name="uom_id"
									ng-options="v.id as v.name for v in filluom" id="uom_id"
									ng-model="formData.uomId" ng-disabled="disable_all">
								</select> <span class="input-group-addon" id="form_div_uom_id_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.uom_id.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>




					<div class="form-group" id="form_div_item_thumb">
						<label for="uom_id" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.item_thumb"></spring:message></label>
						<div class="col-sm-6 img_upload_div">


							<div id="imgdiv" data-provides="fileupload"
								class="fileupload fileupload-new">
								<input type="hidden">
								<div id="defaultImage" style="width: 200px; height: 150px;"
									class="fileupload-new thumbnail">
									<!--    <img alt="" src="{{formData.itemThumb}}"> -->
								</div>
								<div style="border-color: rgb(210, 214, 222);" id="imgshw"
									style="max-width: 200px; max-height: 150px; line-height: 10px;"
									class="fileupload-preview fileupload-exists thumbnail"></div>


								<div>
									<span class="btn btn-white btn-file"> <span
										class="fileupload-new"><i class="fa fa-paper-clip"></i>
											Select image</span> <span class="fileupload-exists"
										ng-disabled="disable_all"><i class="fa fa-undo"></i>
											Change</span> <input type="file" class="default" name="item_thumb"
										id="item_thumb" ng-disabled="disable_all" file-model="myFile">
									</span> <a data-dismiss="fileupload" ng-disabled="disable_all"
										class="btn btn-danger fileupload-exists" href="#"
										ng-click="removeImage()"><i class="fa fa-trash"></i>
										Remove</a>
								</div>
							</div>


							<!-- 	<div class=col-sm-6">
											<div data-provides="fileupload"
												class="fileupload fileupload-new">
												<input type="hidden">
												<div style="width: 200px; height: 150px;"
													class="fileupload-new thumbnail">
													<img alt=""
														src="{{roomType.image2}}">
												</div>
												<div
													style="max-width: 200px; max-height: 150px; line-height: 10px;"
													class="fileupload-preview fileupload-exists thumbnail"></div>
												<div>
													<span class="btn btn-white btn-file"> <span
														class="fileupload-new"><i class="fa fa-paper-clip"></i>
															Select image</span> <span class="fileupload-exists"><i
															class="fa fa-undo"></i> Change</span> <input type="file"
														file-model="myFile2" name="image" value=""
														id="fileToUpload2" class="default">
													</span> <a data-dismiss="fileupload"
														class="btn btn-danger fileupload-exists" href="#"><i
														class="fa fa-trash"></i> Remove</a>
												</div>
											</div>
											</div>
											
					
					 -->



							<!-- <div class="imag_upload_input_div">
							<input type="file" file-model="myFile" name="item_thumb" value=""
							id="item_thumb" class="form-control" ng-disabled="disable_all" >
							</div>
							<div class="itemImage">
							<img src="{{formData.itemThumb}}" >
							</div> -->
							<!-- <input type = "file"  class="form-control" name="item_thumb"
							 id="item_thumb" ng-disabled="disable_all" ng-model="formData.item_thumb"/> -->

							<%-- <span
									class="input-group-addon" id="form_div_item_thumb_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.item_thumb.error">
									</spring:message>"></i></span> --%>
							<%-- <input type="file" class="form-control "
									name="item_thumb" id="item_thumb" ng-model="formData.item_thumb" 
									ng-disabled="disable_all" placeholder=""> <span
									class="input-group-addon" id="form_div_item_thumb_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.item_thumb.error"></spring:message>"></i></span> --%>

						</div>

					</div>




					<div class="form-group" id="form_div_barcode">
						<label for="bar_code" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.barcode"></spring:message> </label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<input type="text" class="form-control" name="bar_code"
									id="bar_code" ng-model="batchData.barCode"
									ng-disabled="disable_all" placeholder="">
							</div>
						</div>
					</div>



		<div class="form-group" id="form_div_display_order" >
					<label for="parent_id" class="col-sm-2 control-label lb">Display Order</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<input  id="display_order" valid-number
											name="display_order" class="form-control fg required" type="text"
											ng-model="formData.display_order" ng-disabled="disable_all" > 
											
											 <span class="input-group-addon" id="form_div_display_order_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Display Order cannot be Blank"></i></span>
						</div>
					</div>
				</div>


<div class="form-group" id="form_div_displayOrder" >
					<label for="parent_id" class="col-sm-2 control-label lb" id="hot_items_label">Hot Items</label>
					<div class="col-sm-3">
						<div class="input-group col-sm-12">
							 <label for="parent_id" class="col-sm-6 control-label lb">H1</label> 	
						
							<div class="input-group col-sm-12">
							<div class="col-sm-3">
						
							<md-checkbox type="checkbox" ng-model="formData.is_hot_item_1"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="">

						</md-checkbox></div>
							<div class="col-sm-9"><input  id="hot_item_1_display_order" valid-number
											name="hot_item_1_display_order" class="form-control fg" type="text"
											ng-model="formData.hot_item_1_display_order" ng-disabled="disable_all" > 
							</div></div>		
						</div>
						
					</div>
					
						<div class="col-sm-3">
						<div class="input-group col-sm-12">
							 <label for="parent_id" class="col-sm-6 control-label lb">H2</label> 	
						
							<div class="input-group col-sm-12">
							<div class="col-sm-3">
						
							<md-checkbox type="checkbox" ng-model="formData.is_hot_item_2"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="">

						</md-checkbox></div>
							<div class="col-sm-9"><input  id="hot_item_2_display_order" valid-number
											name="hot_item_2_display_order" class="form-control fg" type="text"
											ng-model="formData.hot_item_2_display_order" ng-disabled="disable_all" > 
							</div></div>		
						</div>
						
					</div>
					
					
						<div class="col-sm-3">
						<div class="input-group col-sm-12">
							 <label for="parent_id" class="col-sm-6 control-label lb">H3</label> 	
						
							<div class="input-group col-sm-12">
							<div class="col-sm-3">
						
							<md-checkbox type="checkbox" ng-model="formData.is_hot_item_3"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="">

						</md-checkbox></div>
							<div class="col-sm-9"><input  id="hot_item_3_display_order"  valid-number
											name="hot_item_3_display_order" class="form-control fg" type="text"
											ng-model="formData.hot_item_3_display_order" ng-disabled="disable_all" > 
							</div></div>		
						</div>
						
					</div>
				</div>



					<div class="form-group" id="form_div_is_batch">
						<label for="is_batch" class="col-sm-2 control-label lb">Barcode
							Printing<!-- <spring:message
								code="itemmaster.is_batch"></spring:message>  -->
							<!-- <i
							class="text-danger">*</i> -->
						</label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="is_batch" name="is_batch"
									ng-options="v.id as v.name for v in sampleBatch" id="is_batch"
									ng-model="formData.is_barcode_print" ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>

					<div class="form-group" id="form_div_movement_method">
						<label for="movement_method" class="col-sm-2 control-label lbLeft"><spring:message
								code="itemmaster.movement_method"></spring:message> <!-- <i
							class="text-danger">*</i> --></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="movement_method"
									name="movement_method"
									ng-options="v.id as v.name for v in sampleMovement"
									ng-model="formData.movementMethod" ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_valuation_method">
						<label for="valuation_method"
							class="col-sm-2 control-label lbLeft"><spring:message
								code="itemmaster.valuation_method"></spring:message> <i
							class="text-danger">*</i></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control" id="valuation_method"
									name="valuation_method" ng-model="formData.valuationMethod"
									ng-disabled="disable_all">
									<option value="" ng-disabled="true">Select</option>
									<c:forEach items="${enumValuationMethod}"
										var="enumValuationMethod">
										<option
											value="${enumValuationMethod.getenumValuationMethodId()}">${enumValuationMethod.getenumValuationMethodName() }</option>
									</c:forEach>
								</select> <span class="input-group-addon"
									id="form_div_valuation_method_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.valuation_method.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>
					<!-- 	<div class="form-group" id="form_div_pref_supplier_id">
						<label for="pref_supplier_id"
							class="col-sm-2 control-label lbLeft"><spring:message
								code="itemmaster.pref_supplier_id"></spring:message> <i
							class="text-danger">*</i></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12 suplier">
								<input type="text" class="form-control" name="pref_supplier_id"
										id="pref_supplier_id" ng-model="formData.pref_supplier_id"
										ng-disabled="disable_code" placeholder="pref_supplier_id">
								<input type="text" class="form-control" id="prefSupplierId"
									name="prefSupplierId" ng-disabled="disable_code"
									ng-model="formData.prefSupplierId" ng-change="Supplierchange()"
									table-autocomplete> <input type="hidden"
									class="form-control" id="pref_supplier_id"
									name="pref_supplier_id" ng-disabled="disable_code"
									ng-model="formData.prefSupplierId">
							</div>
							<div class="input-group col-sm-12 suplier">
								<input type="text" id="prefSupplierName" name="prefSupplierName"
									class="form-control searchName" ng-model="prefSupplierName"
									disabled="disabled">
							</div>
						</div>

					</div> -->
				</div>
				<div class="form-group" id="form_div_tax_id">
					<label for="tax_id" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.input_tax_id"></spring:message> <!-- <i class="text-danger">*</i> -->
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control" name="input_tax_id"
								ng-options="v.id as v.code for v in taxList" id="inputTaxId"
								ng-model="formData.inputTaxId" ng-disabled="disable_all">
							</select>


							<!-- <select class="form-control" id="movement_method"
								name="movement_method"
								ng-options="v.id as v.name for v in taxList"
								ng-model="formData.inputTaxId" ng-disabled="disable_all">
							</select> -->
						</div>
					</div>
				</div>
				<!-- 	<div class="form-group" id="form_div_item_cost">
					<label for="item_cost" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.itemCost"></spring:message> </label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<input type="text" class="form-control" name="item_cost"
								id="item_cost" ng-model="formData.itemCost"
								ng-disabled="disable_all" placeholder="" valid_number>
						</div>
					</div>
				</div> --> <!-- <div class="form-group" id="form_div_tax_id">
					<label for="tax_id" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.output_tax_id"></spring:message> <i class="text-danger">*</i>
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control" id="movement_method"
								name="movement_method"
								ng-options="v.id as v.name for v in taxList"
								ng-model="formData.outputTaxId" ng-disabled="disable_all">
							</select>
						</div>
					</div>
				</div> --> </md-content> </md-tab> <md-tab label="bom" ng-disabled="tab2_isdisabled"
					id="tab2"> <md-content class="md-padding"> <!-- <h4>BOM</h4> -->

				<div class="form-group " id="form_div_bom_qty">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="itemmaster.standardquantity"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<!-- <input type="text" id="bom_qty" name="bom_qty"
								class="form-control"
								onkeypress="return NumericValidation(this, event, false, false);"> -->
							<input type="text" class="form-control algn_rgt" name="bom_qty"
								id="bom_qty" ng-model="formData.bomQty"
								ng-disabled="disable_bom" placeholder="" valid-number
								maxlength="11">
						</div>
					</div>
				</div>
				<div class="form-group" id="div_table_bom">
					<table id="stockHead" style="background-color: white;"
						class="table table-bordered tableSection scroll">
						<thead style="background-color: #f1f1f1;" class="stockHead">

							<tr>
								<th>#</th>
								<th><spring:message code="itemmaster.itemcode"></spring:message></th>
								<th>Uom</th>
								<th><spring:message code="itemmaster.qty"></spring:message></th>
								<th>Rate</th>

								<th></th>
							</tr>
						</thead>
						<tr ng:repeat="item in bomList">

							<td>{{$index + 1}}</td>

							<td ng-keyup="sendId($index)" ng-click="tableClicked($index)"
								class=""><div style="width: 100%">
									<div style="float: left; width: 25%; padding-right: 4px;">
										<input type="text" class="" id="ItemId" name="ItemId"
											autocompete-text ng-model="item.bom_item_code">
									</div>
									<div style="display: inline-block; width: 75%;">
										<input type="text" class="form-control itmname searchName"
											id="bom_item_name" name="bom_item_name" disabled="disabled"
											ng-model="item.bom_item_name"> <input type="hidden"
											class="prdCode" id="bom_item_id" name="bom_item_id"
											ng-model="item.bom_item_id"> <input type="hidden"
											id="id" name="id" ng-model="item.id">
									</div></td>

							<!-- <td ng-keyup="sendId($index)" ng-click="tableClicked($index)"
									class=""><input type="text" class="" id="ItemId"
									name="ItemId" autocompete-text ng-model="item.bom_item_code"> <input type="text"
									class="form-control itmname searchName" id="bom_item_name"
									name="bom_item_name" disabled="disabled"
									ng-model="item.bom_item_name"> <input type="hidden"
									class="prdCode" id="bom_item_id" name="bom_item_id"
									ng-model="item.bom_item_id"> <input type="hidden"
									id="id" name="id" ng-model="item.id"></td> -->
							<td style="width: 100px;"><input type="text"
								ng-disabled="true" id="uomcode" ng-model="item.uomcode"
								name="uomcode" class="input-mini form-control"></td>
							<td class="itmqty" style="width: 110px"><input type="text"
								class="form-control algn_rgt" id="qty" name="qty"
								ng-model="item.qty" ng-disabled="disable_bom" valid-number
								ng-style="validation_qty" maxlength="15" row-add="addBomRow($index)"></td>

							<td style="width: 110px"><input type="text" 
								ng:model="item.unit_price"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="unit_price" name="unit_price"
								 row-delete="removeBomRow($index)" 
								row-save="saveData()" class="input-mini form-control algn_rgt"
								ng-disabled="true" ng-style="validation_unit_price"></td>



							<td class="itemrmv"><button type="button"
									class="btn btn-link" ng-disabled="disable_bom"
									ng:click="removeBomRow($index)" id="rowdel">
									<i class="fa fa-minus" aria-hidden="true"></i>
								</button></td>

						</tr>
						<tr>

							<td colspan="5" class="font-style algn_rgt"><label class=""></td>

							<td><a href ng:click="addBomRow($index)"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>


					</table>

					<!-- 	<table id="stockHead"
						class="table table-bordered tableSection scroll">
						<thead style="background-color: #f9f9f9" class="stockHead">
							<tr>

								<th><spring:message code="itemmaster.itemcode"></spring:message></th>
								<th><spring:message code="itemmaster.qty"></spring:message></th>
								<th><spring:message code="itemmaster.additionalqty"></spring:message></th>
								<th id="AddrowHd"><button type="button"
										class="btn btn-link" ng:click="addBomRow($index)" ng-disabled="disable_all" id="AddbtnHead">
										<i class="addrow fa fa-plus btn" aria-hidden="true"></i>
									</button></th>
							</tr>

							<tr ng:repeat="item in bomList">
								<td ng-keyup="sendId($index)" ng-click="tableClicked($index)"
									class=""><input type="text" class="" id="ItemId"
									name="ItemId" autocompete-text ng-model="item.bom_item_code"> <input type="text"
									class="form-control itmname searchName" id="bom_item_name"
									name="bom_item_name" disabled="disabled"
									ng-model="item.bom_item_name"> <input type="hidden"
									class="prdCode" id="bom_item_id" name="bom_item_id"
									ng-model="item.bom_item_id"> <input type="hidden"
									id="id" name="id" ng-model="item.id"></td>
								<td class="itmqty"><input type="text"
									class="form-control algn_rgt" id="qty" name="qty"
									ng-model="item.qty" ng-disabled="disable_all" valid-number
									ng-style="validation_qty" maxlength="15"></td>
								<td class="itemrmv"><button type="button"
										class="btn btn-link" ng-disabled="disable_all"  ng:click="removeBomRow($index)"
										id="rowdel">
										<i class="fa fa-minus" aria-hidden="true"></i>
									</button></td>


							</tr>

						</thead>
						<tbody>
							<tr id="rowAdd">
								<td></td>
								<td></td>
								<td><button type="button" class="btn btn-link"
										ng:click="addBomRow($index)" id="AddbtnBody" ng-disabled="disable_all" >
										<i class="addrow fa fa-plus btn" aria-hidden="true"></i>
									</button></td>
							</tr>
						</tbody>
					</table>
					
 -->
					<table id="prodCost" style="background-color: white;"
						class="table table-bordered tableSection scroll">
						<thead style="background-color: #f1f1f1;" class="">

							<tr>
								<th>#</th>
								<th>Production Cost Type</th>
								<th>Cost Type</th>
								<th ng-hide="true">Percentage</th>
								<th>Rate</th>

								<th></th>
							</tr>
						</thead>
						<tr ng:repeat="item in prodCostList">

							<td>{{$index + 1}}</td>

							<td ng-keyup="sendId($index)" ng-click="tableClicked1($index)"
								class=""><div style="width: 100%">
									<div style="float: left; width: 25%; padding-right: 4px;">
										<input type="text" class="" id="prodItemId" name="prodItemId"
											table-productioncost ng-model="item.prod_cost_code">
									</div>
									<div style="display: inline-block; width: 75%;">
										<input type="text" class="form-control itmname searchName"
											id="prod_cost_name" name="prod_cost_name" disabled="disabled"
											ng-model="item.prod_cost_name"> <input type="hidden"
											class="prdId" id="prod_cost_id" name="prod_cost_id"
											ng-model="item.prod_cost_id"> <input type="hidden"
											id="id" name="id" ng-model="item.id">
									</div></td>
							<td style="width: 120px;"><input type="text"
								ng-disabled="true" id="prdcosttype"
								ng-model="item.prod_cost_type" name="prdcosttype"
								class="input-mini form-control"></td>
							<!-- <td ng-keyup="sendId($index)" ng-click="tableClicked($index)"
									class=""><input type="text" class="" id="ItemId"
									name="ItemId" autocompete-text ng-model="item.bom_item_code"> <input type="text"
									class="form-control itmname searchName" id="bom_item_name"
									name="bom_item_name" disabled="disabled"
									ng-model="item.bom_item_name"> <input type="hidden"
									class="prdCode" id="bom_item_id" name="bom_item_id"
									ng-model="item.bom_item_id"> <input type="hidden"
									id="id" name="id" ng-model="item.id"></td> -->
							<!-- <td class="itmqty" style="width: 110px"><input type="text"
								class="form-control algn_rgt" id="qty" name="qty"
								ng-model="item.percentage" ng-disabled="disable_all" valid-number
								ng-style="validation_qty" maxlength="15"></td> -->
							<td ng-hide="true" style="width: 70px"><input type="checkbox"
								ng-model="item.isPercentage" ng-change="selectPercntg()" /></td>

							<td style="width: 110px"><input type="text"
								ng:model="item.rate"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="cost_rate" name="cost_rate"
								row-add="addProdCostRow($index)"
								row-delete="removeBomRow($index)" row-save="saveData()"
								class="input-mini form-control algn_rgt"
								ng-disabled="disable_bom" ng-style="validation_unit_price"></td>



							<td class="itemrmv"><button type="button"
									class="btn btn-link" ng-disabled="disable_bom"
									ng:click="removeProdCostRow($index)" id="rowdel">
									<i class="fa fa-minus" aria-hidden="true"></i>
								</button></td>

						</tr>
						<tr>

							<td colspan="4" class="font-style algn_rgt"><label class=""></td>

							<td><a href ng:click="addProdCostRow($index)"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>


					</table>
				</div>

				</md-content> </md-tab> <md-tab label="sale item" ng-disabled="tab3_isdisabled" id="tab3">
				<md-content class="md-padding salitmtab">
				<div class="form-group" id="form_div_is_combo_item">
					<label for="is_combo_item" class="col-sm-2 control-label "><spring:message
							code="itemmaster.is_combo_item"></spring:message> </label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control" id="is_combo_item" ng-change="isCombo()"
								name="is_combo_item"
								ng-options="v.id as v.name for v in sampleCombo"
								id="is_combo_item" ng-model="formData.isComboItem"
								ng-disabled="disable_all">
							</select>
						</div>
					</div>
				</div>
					<div class="form-group" id="form_div_is_valid">
					<label for="is_combo_item" class="col-sm-2 control-label ">Is Valid<!-- <spring:message
							code="itemmaster.is_combo_item"></spring:message> --> </label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control" id="is_valid"
								name="is_valid"
								ng-options="v.id as v.name for v in sampleValid"
								id="is_valid" ng-model="formData.is_valid" 
								ng-disabled="disable_all">
							</select>
						</div>
					</div>
				</div>
				
				
				
				<!-- 	<div class="form-group" id="form_div_is_open">
					<label for="is_open" class="col-sm-2 control-label "><spring:message
							code="itemmaster.is_open"></spring:message> <i class="text-danger">*</i>
					</label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control" id="is_open" name="is_open"
								ng-options="v.id as v.name for v in sampleOpen" id="is_open"
								ng-model="formData.isOpen" ng-disabled="disable_all">
							</select>
						</div>
					</div>
				</div> -->
				<div class="form-group" id="form_div_is_require_weighing">
					<label for="is_require_weighing" class="col-sm-2 control-label "><spring:message
							code="itemmaster.is_require_weighing"></spring:message></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control" id="is_require_weighing"
								name="is_require_weighing"
								ng-options="v.id as v.name for v in sampleWeighing"
								id="is_require_weighing" ng-model="formData.isRequireWeighing"
								ng-disabled="disable_all">
							</select>
						</div>
					</div>
				</div>

				<div class="form-group" id="form_div_group_item">
					<label for="group_item" class="col-sm-2 control-label "><spring:message
							code="itemmaster.groupItem"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-6 taxselct">
							<select class="form-control" id="group_item" name="group_item"
								ng-options="v.id as v.name for v in groupItem" id="group_item"
								ng-model="formData.groupItemId" ng-disabled="disable_all"
								ng-change="buttonChange()">

								<option value="" selected="selected">select</option>
							</select>
						</div>
						<div class="addbtn">
							<button id="bt" data-toggle="modal" ng-disabled="disable_all"
								data-target="#myModal" ng-click="popup()">
								<i class="fa fa-plus"></i>
							</button>
						</div>
					</div>
				</div>
				<!-- <i class="fa fa-pencil"></i> 
 --> <!-- <div class="form-group" id="form_div_item_class">
					<label for="item_class" class="col-sm-2 control-label "><spring:message
							code="itemmaster.itemClass"></spring:message></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control" id="item_class"
								name="item_class"
								ng-options="v.id as v.name for v in itemClass"
								id="item_class" ng-model="formData.item_class_id"
								ng-disabled="disable_all">
								
								<option value="" selected="selected">select</option>
							</select>
						</div>
					</div>
				</div> --> <%-- <div class="form-group" id="form_div_taxation_method">
					<label for="taxation_method" class="col-sm-2 control-label "><spring:message
							code="itemmaster.taxation_method"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control" id="taxation_method"
								name="taxation_method" ng-model="formData.taxCalculationMethod"
								ng-disabled="disable_all">

								<option value="" selected="selected">select</option>
								<option value="0">Inclusive of Tax</option>
								<option value="1">Exclusive of Tax</option>

							</select> <span class="input-group-addon"
								id="form_div_taxation_method_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itemmaster.taxation_method.error"></spring:message>"></i></span>


						</div>
					</div>
				</div>



				<div class="form-group" id="form_div_taxation_based_on">
					<label for="taxation_based_on" class="col-sm-2 control-label "><spring:message
							code="itemmaster.taxation_based_on"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-6 ">

							<select class="form-control" name="taxation_based_on"
								ng-options="v.id as v.name for v in taxBased"
								id="taxation_based_on" ng-model="formData.taxationBasedOn"
								ng-disabled="disable_all" ng-change="changeTAX()">
							</select>



						</div>

					</div>
				</div>







				<div class="form-group" id="form_div_tax" ng-hide="taxhide">
					<label for="tax" class="col-sm-2 control-label "><spring:message
							code="itemmaster.tax"></spring:message><i class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">

							<select class="form-control" name="tax"
								ng-options="v.id as v.code for v in taxList" id="outputTaxId"
								ng-model="formData.outputTaxId" ng-disabled="disable_all">
							</select> <span class="input-group-addon" id="form_div_tax_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itemmaster.tax.error"></spring:message>"></i></span>


						</div>
					</div>
				</div> --%>



				<div class="form-group" id="form_div_tax_id_home_service"
					ng-show="taxhide">
					<label for="tax_id_home_service" class="col-sm-2 control-label "><spring:message
							code="itemmaster.tax_id_home_service"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">

							<select class="form-control" name="tax_id_home_service"
								ng-options="v.id as v.code for v in taxList"
								id="tax_id_home_service"
								ng-model="formData.outputTaxIdHomeService"
								ng-disabled="disable_all">
							</select> <span class="input-group-addon"
								id="form_div_tax_id_home_service_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itemmaster.tax_id_home_service.error"></spring:message>"></i></span>


						</div>
					</div>
				</div>



				<div class="form-group" id="form_div_tax_id_table_service"
					ng-show="taxhide">
					<label for="tax_id_table_service" class="col-sm-2 control-label "><spring:message
							code="itemmaster.tax_id_table_service"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">

							<select class="form-control" name="tax_id_table_service"
								ng-options="v.id as v.code for v in taxList"
								id="tax_id_table_service"
								ng-model="formData.outputTaxIdTableService"
								ng-disabled="disable_all">
							</select>



							<!-- <select class="form-control" id="tax_id_table_service"
								name="tax_id_table_service"
								 ng-model="formData.outputTaxIdTableService"
								ng-disabled="disable_all">
								
								<option value="" selected="selected">select</option>
								<option value="1" >No Tax</option>
								
									
							</select> -->
							<span class="input-group-addon"
								id="form_div_tax_id_table_service_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itemmaster.tax_id_table_service.error"></spring:message>"></i></span>


						</div>
					</div>
				</div>


				<div class="form-group" id="form_div_tax_id_take_away_service"
					ng-show="taxhide">
					<label for="tax_id_take_away_service"
						class="col-sm-2 control-label "><spring:message
							code="itemmaster.tax_id_take_away_service"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">


							<select class="form-control" name="tax_id_take_away_service"
								ng-options="v.id as v.code for v in taxList"
								id="tax_id_take_away_service"
								ng-model="formData.outputTaxIdTakeAwayService"
								ng-disabled="disable_all">
							</select>
							<!-- <select class="form-control" id="tax_id_take_away_service"
								name="tax_id_take_away_service"
								 ng-model="formData.outputTaxIdTakeAwayService"
								ng-disabled="disable_all">
								
								<option value="" selected="selected">select</option>
								<option value="1" >No Tax</option>
								
									
							</select> -->
							<span class="input-group-addon"
								id="form_div_tax_id_take_away_service_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itemmaster.tax_id_take_away_service.error"></spring:message>"></i></span>


						</div>
					</div>
				</div>







				<!-- <div class="form-group" id="form_div_sub_class_id">
					<label for="sub_class_id" class="col-sm-2 control-label "><spring:message
							code="itemmaster.sub_class_id"></spring:message> </label>
					<div class="col-sm-5">
						<div class="input-group col-sm-11">
							<input type="text" class="form-control" id="subClassId"
								name="subClassId" ng-disabled="disable_code" table-autocomplete>
							<input type="hidden" class="form-control" id="sub_class_id"
								name="sub_class_id" ng-disabled="disable_code"
								ng-model="formData.subClassId">
						</div>
						<div class="input-group col-sm-12 suplier">
							<input type="text" id="subClassName" name="subClassName"
								class="form-control searchName" ng-model="subClassName"
								disabled="disabled">
						</div>
					</div>
				</div> -->
				<div class="form-group" id="form_div_super_class_id" ng-show="true">
					<label for="sub_class_id" class="col-sm-2 control-label "><spring:message
							code="itemmaster.sub_class_id"></spring:message> </label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<select class="form-control " id="super_class"
								name="super_class_name"
								ng-options="v.id as v.name for v in superClassList"
								ng-model="formData.subClassId" ng-disabled="disable_all">
							</select> <span class="input-group-addon" id="form_div_super_class_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itemclass.error.superclass"></spring:message>"></i></span>
						</div>
					</div>
				</div>
				
					<div class="form-group" id="form_div_hsn_code" >
					<label for="parent_id" class="col-sm-2 control-label lb">HSN Code</label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<input  id="hsn_code"
											name="hsn_code" class="form-control fg" type="text"
											ng-model="formData.hsn_code" ng-disabled="disable_all" > 
						</div>
					</div>
				</div>
				
				
				
			

				<!-- 	<div class="form-group" id="form_div_fixed_price">
					<label for="fixed_price" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.fixedPrice"></spring:message> </label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<input type="text" class="form-control" name="fixed_price"
								id="fixed_price" ng-model="formData.fixedPrice"
								ng-disabled="disable_all" placeholder="" valid_number>
						</div>
					</div>
				</div> --> <!-- <div class="form-group" id="form_div_choice_ids">
					<label for="choice_ids" class="col-sm-2 control-label "><spring:message
							code="itemmaster.choice_ids"></spring:message> </label>
					<div class="col-sm-5">
						<div class="input-group col-sm-11">
							<input type="text" class="form-control" name="choice_ids"
								id="choice_ids" ng-model="formData.choiceIds"
								ng-disabled="disable_all" placeholder="" maxlength="50">
						</div>
					</div>
				</div> -->
				<div class="form-group" id="form_div_alternative_name">
					<label for="alternative_name" class="col-sm-2 control-label "><spring:message
							code="itemmaster.alternative_name"></spring:message> </label>
					<div class=" col-sm-5">
						<div class="input-group col-sm-11">
							<input type="text" class="form-control" name="alternative_name"
								id="alternative_name" ng-model="formData.alternativeName"
								ng-disabled="disable_all" placeholder="" maxlength="50">
						</div>
					</div>
				</div>
				<!-- 	<div class="form-group" id="form_div_name_to_print">
					<label for="name_to_print" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.name_to_print"></spring:message> </label>
					<div class=" col-sm-5">
						<div class="input-group col-sm-11">
							<input type="text" class="form-control" name="name_to_print"
								id="name_to_print" ng-model="formData.nameToPrint"
								ng-disabled="disable_all" placeholder="" maxlength="50">
						</div>
					</div>
				</div>
				<div class="form-group" id="form_div_alternative_name_to_print">
					<label for="alternative_name_to_print"
						class="col-sm-2 control-label "><spring:message
							code="itemmaster.alternative_name_to_print"></spring:message> </label>
					<div class=" col-sm-5">
						<div class="input-group col-sm-11">
							<input type="text" class="form-control"
								name="alternative_name_to_print" id="alternative_name_to_print"
								ng-model="formData.alternativeNameToPrint"
								ng-disabled="disable_all" placeholder="" maxlength="50">
						</div>
					</div>
				</div> -->

				<div class="container col-sm-12">
					<div class="row col-sm-11">
						<div class="col-sm-4">
							<div class="form-group" id="form_div_fg_color">
								<label for="fg_color" class="col-sm-6 control-label "><spring:message
										code="itemmaster.fg_color"></spring:message> <!-- <i
									class="text-danger">*</i> --></label>
								<div class=" col-sm-6">
									<div class="input-group col-sm-12">
										<input minicolors="customSettings" id="fg_color"
											name="fg_color" class="form-control fg" type="text"
											ng-model="formData.fgColor" ng-disabled="disable_all">
									</div>
								</div>

							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group " id="form_div_bg_color">
								<label for="bg_color" class="col-sm-5 control-label bg"><spring:message
										code="itemmaster.bg_color"></spring:message></label>
								<div class=" col-sm-6">
									<div class="input-group col-sm-12">
										<input minicolors="customSettings" id="bg_color"
											name="bg_color" class="form-control " type="text"
											ng-model="formData.bgColor" ng-disabled="disable_all">
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				</md-content> </md-tab> <md-tab label="attributes" ng-disabled="tab3_isdisabled" id="tab4">
				<md-content class="md-padding">

				<div class="col-sm-12" id="attri">
					<div class="col-sm-4">
						<div class="col-sm-12">
							<label><spring:message code="itemmaster.attribute"></spring:message></label>
						</div>
						<input type="text" id="attrib1_name" name="attrib1_name"
							class="form-control " ng-model="formData.attrib1Name"
							ng-disabled="disable_all"> <input type="text"
							id="attrib2_name" name="attrib2_name" class="form-control "
							ng-model="formData.attrib2Name" ng-disabled="disable_all">
						<input type="text" id="attrib3_name" name="attrib3_name"
							class="form-control " ng-model="formData.attrib3Name"
							ng-disabled="disable_all"> <input type="text"
							id="attrib4_name" name="attrib4_name" class="form-control "
							ng-model="formData.attrib4Name" ng-disabled="disable_all">
						<input type="text" id="attrib5_name" name="attrib5_name"
							class="form-control " ng-model="formData.attrib5Name"
							ng-disabled="disable_all">

					</div>
					<div class="col-sm-8">
						<div class="col-sm-12">
							<label><spring:message code="itemmaster.options"></spring:message></label>
						</div>
						<input type="text" id="attrib1_option" name="attrib1_option"
							class="form-control " ng-model="formData.attrib1Options"
							ng-disabled="disable_all"> <input type="text"
							id="attrib2_option" name="attrib2_option" class="form-control "
							ng-model="formData.attrib2Options" ng-disabled="disable_all">
						<input type="text" id="attrib3_option" name="attrib3_option"
							class="form-control " ng-model="formData.attrib3Options"
							ng-disabled="disable_all"> <input type="text"
							id="attrib4_option" name="attrib4_option" class="form-control "
							ng-model="formData.attrib4Options" ng-disabled="disable_all">
						<input type="text" id="attrib5_option" name="attrib5_option"
							class="form-control " ng-model="formData.attrib5Options"
							ng-disabled="disable_all">


					</div>

				</div>




				</md-content></md-tab> <md-tab label="print" ng-disabled="tab3_isdisabled" id="tab5">
				<md-content class="md-padding">
				<div class="col-sm-12">

					<div class="form-group" id="form_div_name_to_print">
						<label for="name_to_print" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.name_to_print"></spring:message> </label>
						<div class=" col-sm-5">
							<div class="input-group col-sm-11">
								<input type="text" class="form-control" name="name_to_print"
									id="name_to_print" ng-model="formData.nameToPrint"
									ng-disabled="disable_all" placeholder="" maxlength="50">
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_alternative_name_to_print">
						<label for="alternative_name_to_print"
							class="col-sm-2 control-label "><spring:message
								code="itemmaster.alternative_name_to_print"></spring:message> </label>
						<div class=" col-sm-5">
							<div class="input-group col-sm-11">
								<input type="text" class="form-control"
									name="alternative_name_to_print" id="alternative_name_to_print"
									ng-model="formData.alternativeNameToPrint"
									ng-disabled="disable_all" placeholder="" maxlength="50">
							</div>
						</div>
					</div>

				</div>



				</md-content></md-tab> <md-tab label="choices" ng-disabled="tab3_isdisabled" id="tab5">
				<md-content class="md-padding">

				<div class="form-group" id="form_div_choices">
					<label for="choices" class="col-sm-2 control-label "><spring:message
							code="itemmaster.choices"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-6 taxselct">
							<select class="form-control" id="choices" name="choices"
								ng-options="v.id as v.name for v in choices"
								ng-model="formData.choiceIds" ng-disabled="disable_all">
								<option value="" selected="selected">select</option>
							</select>
						</div>
						<div class="addbtn">
							<button id="btnn" ng-click="addNewChoice()"
								ng-disabled="disable_all">
								<i class="fa fa-plus"></i>
							</button>
						</div>

					</div>
				</div>
				<b>Items Added Below</b>
				<div class="main_choice">
					<div ng-repeat='choItm in choicesItem'>
						<input type="hidden" name="choice_id" ng-model="choItm.id"
							ng-disabled="true"> <input type="text" name="choice_code"
							ng-model="choItm.code" ng-disabled="true"> <input
							type="text" name="choice_name" ng-model="choItm.name"
							ng-disabled="true">
						<button ng-click='removeChoice($index)' ng-disabled="disable_all"
							class="delete_icon_div">
							<i class="fa fa-minus-circle" aria-hidden="true"></i>
						</button>
					</div>
				</div>



				</md-content></md-tab> <md-tab label="Price Settings" ng-disabled="tab3_isdisabled"
					id="tab5"> <md-content class="md-padding">
				<div class="col-sm-12">

					<div class="form-group" id="form_div_taxation_method">
						<label for="taxation_method" class="col-sm-2 control-label "><spring:message
								code="itemmaster.taxation_method"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-3">
							<div class="input-group col-sm-12">
								<select class="form-control" id="taxation_method"
									name="taxation_method" ng-model="formData.taxCalculationMethod"
									ng-disabled="disable_all">

									<option value="" selected="selected">select</option>
									<option value="0">Inclusive of Tax</option>
									<option value="1">Exclusive of Tax</option>

								</select> <span class="input-group-addon"
									id="form_div_taxation_method_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.taxation_method.error"></spring:message>"></i></span>


							</div>
						</div>
					</div>



					<div class="form-group" id="form_div_taxation_based_on">
						<label for="taxation_based_on" class="col-sm-2 control-label "><spring:message
								code="itemmaster.taxation_based_on"></spring:message></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-9 ">

								<select class="form-control" name="taxation_based_on"
									ng-options="v.id as v.name for v in taxBased"
									id="taxation_based_on" ng-model="formData.taxationBasedOn"
									ng-disabled="disable_all" ng-change="changeTAX()">
								</select>



							</div>

						</div>
					</div>







					<div class="form-group" id="form_div_tax" ng-hide="taxhide">
						<label for="tax" class="col-sm-2 control-label "><spring:message
								code="itemmaster.tax"></spring:message><i class="text-danger">*</i></label>
						<div class="col-sm-3">
							<div class="input-group col-sm-12">

								<select class="form-control" name="tax"
									ng-options="v.id as v.code for v in taxList" id="outputTaxId"
									ng-model="formData.outputTaxId" ng-disabled="disable_all">
								</select> <span class="input-group-addon" id="form_div_tax_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.tax.error"></spring:message>"></i></span>


							</div>
						</div>
					</div>

					<div class="form-group" id="form_div_is_open">
						<label for="is_open" class="col-sm-2 control-label "><spring:message
								code="itemmaster.is_open"></spring:message> <!-- <i class="text-danger">*</i> -->
						</label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<select class="form-control" id="is_open" name="is_open"
									ng-options="v.id as v.name for v in sampleOpen" id="is_open"
									ng-model="formData.isOpen" ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>

					<div class="form-group" id="form_div_fixed_price">
						<label for="fixed_price" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.fixedPrice"></spring:message> </label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<input type="text" class="form-control" name="fixed_price"
									id="fixed_price" ng-model="formData.fixedPrice"
									ng-disabled="disable_all" placeholder="" valid_number>
							</div>
						</div>
					</div>

					<div class="form-group" id="form_div_whls_price">
						<label for="whls_price" class="col-sm-2 control-label lb">
							<!-- <spring:message
							code="itemmaster.fixedPrice"></spring:message> --> Wholesale
							Price
						</label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<input type="text" class="form-control " name="whls_price"
									id="whls_price" ng-model="formData.whls_price"
									ng-disabled="disable_all" placeholder="" valid_number><span class="input-group-addon" id="form_div_whls_price_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="whole Price cannot be blank"></i></span>
							</div>
						</div>
					</div>


					<div class="form-group" id="form_div_is_whls_price_pc">
						<label for="is_open" class="col-sm-2 control-label ">Is
							Percentage<!-- <spring:message
							code="itemmaster.is_open"></spring:message>  -->
							<!-- <i class="text-danger">*</i> -->
						</label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<select class="form-control" id="is_whls_price_pc"
									name="is_whls_price_pc"
									ng-options="v.id as v.name for v in sampleIswhlsPc"
									id="is_whls_price_pc" ng-model="formData.is_whls_price_pc"
									ng-disabled="disable_all">
								</select>
							</div>
						</div>
					</div>




					<div class="form-group" id="form_div_item_cost">
						<label for="item_cost" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.itemCost"></spring:message> </label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<input type="text" class="form-control" name="item_cost"
									id="item_cost" ng-model="formData.itemCost"
									ng-disabled="disable_all" placeholder="" valid_number>
							</div>
						</div>
					</div>



					<div class="form-group" id="form_div_cust">
						<label for="item_cost" class="col-sm-6 code-lbl-font-size control-label lb">Customer
							Types </label>
						<div class="col-sm-12" >

							<table datatable="" dt-options="item.dtOptions1" style="width:100%"
								dt-columns="item.dtColumns1" dt-instance="item.dtInstance1"
								class=" table dataClass customer_table_main"></table>

						</div>
					</div>
		


			</md-content></md-tab> 
			
			<md-tab label="Combo Contents" ng-disabled="tab8_isdisabled" id="tab8">
				<md-content class="md-padding">

				
				
				<div class="form-group " id="form_div_items_tbl">

							
			<div class="cmmn_maain_tbl">
				<circle-spinner ng-show="prograssing"></circle-spinner>
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">

					<tr class="active">
						<th>SINO</th>
						<th>Combo Content Items</th>
				
						<th>Max Qty</th>
						<th></th>
					</tr>
					<tr ng:repeat="item in pricesettings.items">
						<td style="width: 20px;">{{$index}}  </td>
						<td  style="width:403px"><div style="float:left;width:25%; padding-right:4px;"><input
							type="text" ng:model="item.stock_item_code" id="stock_item_code"
							name="stock_item_code" ng:required
							class="input-mini form-control" ng-disabled="true"
							> </div><div style="display: inline-block; width:75%;"> <input type="hidden"
							ng:model="item.substitution_sale_item_id" id="substitution_sale_item_id"
							name="substitution_sale_item_id"> <input type="text"
							class="form-control " ng-disabled="true" style="height: 35px;"
							ng:model="item.stock_item_name" id="stock_item_name"
							name="stock_item_name"></div></td>
							<!-- <td style="width: 109px;"><input type="text" ng-disabled="disable_all"  valid-number
								id="price_diff" ng-model="item.price_diff" name="uomcode" class="input-mini form-control"></td> -->
						<td style="width: 96px;"><input type="text"
							ng:model="item.qty" select-on-click id="itemqty"  row-delete="removeItem($index)"  row-save="save_data()"
							ng-disabled="true" valid-number maxlength="${10+settings['decimalPlace']+1}" 
							class="input-mini form-control algn_rgt"></td>

					
					

						<td style="width: 50px;"><a href
							ng:click="removeItem($index)" class="btn btn-small"><i
								class="fa fa-minus "></i> </a></td>
					</tr>
					<tr>
					
						<td></td>
						<td></td>
				
						<td></td>
						<td style="width: 50px;"><!-- <a href ng:click="addItem()"
							class="btn btn-small"><i class="fa fa-plus"></i> </a> --></td>
					</tr>
				</table>
				</div>
			</div>
				
				

				</md-content></md-tab>
				
				
				
			
				
				</md-tabs> </md-content>
			</div>

		</div>








	</form>

</div>



<!-- modal2 -->
<div id="myModal2" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Synchronization</h4>
			</div>
			<div class="modal-body " id="popbeforsave">


				<div class="col-sm-12">
					<input type="hidden" ng-model="Quetable.id">
					<md-radio-group ng-model="Quetable.syncNow" ng-disabled="">
					<div class="col-sm-12">

						<md-radio-button value="0" class="md-primary"
							ng-click="clearDate()">Publish Now</md-radio-button>

					</div>

					<div class="col-sm-12">
						<div class="col-sm-6" style="padding-left: 0;">

							<md-radio-button value="1">Scheduled Publish </md-radio-button>

						</div>
						<div class="col-sm-6">
							<label>Date and time</label>
							<div class="right-inner-addon" moment-picker="Quetable.dateTime"
								id="dateTime" format="{{filterTimes1}}">
								<i class="fa fa-clock-o fa-5x" id="calender_icon"
									style="left: 81%; z-index: 6;"></i> <input class="form-control"
									placeholder="Date & Time" ng-model="Quetable.dateTime"
									ng-disabled="disable_all" ng-click="clearCheck()">
							</div>

						</div>
					</div>


					</md-radio-group>

					<div class="col-sm-12" ng-show="allshopCheck">

						<md-checkbox type="checkbox" ng-model="Quetable.sysSaleFlag"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="">

						</md-checkbox>
						<label>Apply for All Shops</label>

					</div>
				</div>
			</div>
			<div class="box-footer">
				<button type="button" class="btn btn-success"
					ng-click="functionSaveData()">OK</button>


				<button type="button" class="btn btn-default pull-right"
					data-dismiss="modal" ng-click="clearDate()">Cancel</button>
			</div>
		</div>

	</div>
</div>

<!--end modal -->






							
