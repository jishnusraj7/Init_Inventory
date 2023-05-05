
<div class="" id="menu_form_div" ng-show="show_form" ng-cloak>
		<div class="col-md-12 spanerrors spanerrorstab1" id="alert_required" ng-show="tableShwerr"  style="color: red;" > *select atleat one department*</div>


	<form class="form-horizontal" id="menu_form">

		<div class="">
			<div class="row">
				<div class="col-sm-12">
					<md-content> <md-tabs md-dynamic-height
						md-border-bottom md-selected="selectedIndexTab"> <md-tab
						label="GENERAL "> <md-content class="md-padding">
					<div class="form-group " id="form_div_code"
						style="padding-top: 10px;">
						<label for="code"
							class="col-sm-3 control-label code-lbl-font-size"><spring:message
								code="common.code"></spring:message> <i class="text-danger">*</i></label>
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control required code-font-size"
									ng-change="isCodeExistis(formData.code)" name="code" id="code"
									ng-model="formData.code" maxlength="10"
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
						<label for="name" class="col-sm-3 control-label "><spring:message
								code="common.name"></spring:message> <i class="text-danger">*</i></label>

						<div class="col-sm-3">
							<div class="input-group ">
								<input type="text" class="form-control required " name="name"
									id="name" ng-model="formData.name" ng-disabled="disable_all"
									maxlength="50" placeholder="Name"> <span
									class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>

					</div>









					<div class="form-group" id="form_div_description">
						<label for="description" class="col-sm-3 control-label"><spring:message
								code="common.description"></spring:message> <i
							class="text-danger"></i></label>

						<div class="col-sm-3">
							<div class="input-group">
								<textarea rows="" cols="" type="text" class="form-control "
									name="description" ng-model="formData.description"
									ng-disabled="disable_all" maxlength="250" id="description"
									placeholder=""> </textarea>
								<span class="input-group-addon" id="form_div_description_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.description"></spring:message>"></i></span>

							</div>
						</div>

					</div>



					<div class="form-group" id="form_div_color">
						<label for="color" class="col-sm-3 control-label "><spring:message
								code="menu.color"></spring:message></label>
						<div class=" col-sm-3">
							<div class="input-group col-sm-12">
								<input minicolors="customSettings" id="color" name="color"
									class="form-control fg" type="text" ng-model="formData.color"
									ng-disabled="disable_all" maxlength="10">
							</div>
						</div>

					</div>



					<div class="form-group" id="form_div_showbtns">
						<label for="showbtns" class="col-sm-3 control-label lb"><spring:message
								code="menu.showbtns"></spring:message></label>
               <div class="col-sm-9 padd ">
               
                    
						<div class="col-sm-1">
                        <label for="showbtns" class=" control-label lb"><spring:message
								code="menu.h1"></spring:message></label>
							<md-checkbox name="enable_h1_button" ng-model="formData.enable_h1_button" id="enable_h1_button"
								aria-label="Checkbox 1" ng-change="" ng-disabled="disable_all">
							</md-checkbox>

						</div>
						
						<div class="col-sm-1 mrglft">
                          <label for="showbtns" class=" control-label lb"><spring:message
								code="menu.h1"></spring:message></label>
							<md-checkbox name="enable_h2_button" ng-model="formData.enable_h2_button" id="enable_h2_button"
								aria-label="Checkbox 1" ng-change="" ng-disabled="disable_all">
							</md-checkbox>

						</div>
						
						<div class="col-sm-1 mrglft">
                        <label for="showbtns" class=" control-label lb"><spring:message
								code="menu.h1"></spring:message></label>
							<md-checkbox name="enable_h3_button" ng-model="formData.enable_h3_button" id="enable_h3_button"
								aria-label="Checkbox 1" ng-change="" ng-disabled="disable_all">
							</md-checkbox>

						</div>
						
					</div>


					</div>





					<div class="form-group" id="form_div_is_default_menu">
						<label for="is_default_menu" class="col-sm-3 control-label lb"><spring:message
								code="menu.is_default_menu"></spring:message></label>

						<div class="col-sm-1">

							<md-checkbox  ng-model="formData.is_default_menu" id="is_default_menu"
								aria-label="Checkbox 1" ng-change="" ng-disabled="disable_all" name="is_default_menu">
							</md-checkbox>

						</div>


					</div>






					<div class="form-group" id="form_div_is_Active">
						<label for="is_active" class="col-sm-3 control-label lb"><spring:message
								code="menu.is_Active"></spring:message></label>

						<div class="col-sm-1">

							<md-checkbox name="is_active" ng-model="formData.is_active" id="is_active"
								aria-label="Checkbox 1" ng-change="" ng-disabled="disable_all">
							</md-checkbox>

						</div>


					</div>






					</md-content> </md-tab> <md-tab label="DEPARTMENTS"> <md-content
						class="md-padding">







<!-- Main content -->
	<section class="content" id="module_content">
		<div class="row">
			<div class="col-xs-12">


				<div class="box" id="msgtop">
					<div class="box-header with-border">
						<h3 class="box-title"></h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="box-header"></div>

						
							<div class="form-group">
		  <table id="table2" datatable="" dt-options="item.dtOptions1"
		   dt-columns="item.dtColumns1" dt-instance="item.dtInstance1" 
		   class=" table dataClass"></table>
		</div>
     	



						

					</div>

					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->

     	






					</md-content> </md-tab> </md-tabs> </md-content>

				</div>
			</div>
		</div>
	</form>
</div>



