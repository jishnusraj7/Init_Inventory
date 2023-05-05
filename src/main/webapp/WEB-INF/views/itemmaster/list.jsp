
<jsp:directive.include file="../common/includes/page_directives.jsp" />

<%-- <c:set var="formPageUrl" value="../../looksup/itemcategory/form.jsp"
	scope="request" /> --%>
<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumitemType"%>

<c:set var="enumitemType" value="<%=enumitemType.values()%>"></c:set>
<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumValuationMethod"%>

<c:set var="enumValuationMethod"
	value="<%=enumValuationMethod.values()%>"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>

<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />

<c:set var="canAdd"
	value="${permission.getCanAdd()&& permission.getIsAddApplicable()}"></c:set>
<c:set var="canEdit"
	value="${permission.getCanEdit() && permission.getIsEditApplicable()}"></c:set>
<c:set var="CanDelete"
	value="${permission.getCanDelete()&& permission.getIsDeleteApplicable()}"></c:set>
<c:set var="CanExcute"
	value="${permission.getCanExecute() && permission.getIsExecuteApplicable()}"></c:set>
<c:set var="CanExport"
	value="${permission.getCanExport() && permission.getIsExportApplicable()}"></c:set>

<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/itemmaster/css/itemmaster.css" />">
	
	<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/momentpicker/angular-moment-picker.css" />">
	<style type="text/css">
	
.dataTables_scrollHeadInner thead {
    position: fixed !important;
     width: 100% !important;
     height:100% !important;
    z-index: 1;
}
	</style>
</head>

<!-- design header template include below -->
<jsp:directive.include file="../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->
<input type="hidden" value="0" id="show_form">
<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak>
	<!-- Content Header (Page header) -->
	<div class="list-content-header" ng-controller="btn_ctrl">
		<section class="content-header content-header_second">
			<h1>
				<spring:message code="itemmaster.header"></spring:message>
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box" id="succ_alertMessageId">
				 {{ succ_alertMeaasge }}
			</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box" id="err_alertMessageId">
				 {{ err_alertMeaasge }}
			</div>


			<%-- <ol class="breadcrumb breadcrumb_position">
				<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li>
				<li><a href="#"><spring:message code="common.menu.settings"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.products"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory.itemmaster"></spring:message></a></li>
			</ol> --%>
		</section>

		<div class="new_box">
			<div class="box cont_div_box">
				<jsp:directive.include file="../common/includes/common_buttons.jsp" />

			</div>
		</div>
		<div class="container" style="padding-left: 150px;" id="advsearchbox" ng-show="true">
	<div class="row">
		<div class="col-md-3">
            <div class="input-group" id="adv-search" >
                <!-- <input style="height: 31px;" type="text" id="SearchText" class="form-control" placeholder="Search ..." contenteditable="true"  /> -->
               		<!-- <input type="text" name="search" style="width: 260px;" id="SearchText" value="" ng-model="searchTxt"/> -->
               		<div id="SearchText"  name="search" contenteditable="true" style="width: 260px;"></div>
               		<a href="" id="clear"></a>
					<div class="input-group-btn">
                
                    <div class="btn-group searchbttn" role="group">
                  
                        <div class="dropdown dropdown-lg">
                            <button type="button" class="btn btn-default dropdown-toggle" style="height: 30px;" data-toggle="dropdown" aria-expanded="false"><span class="caret"></span></button>
                            <div class="dropdown-menu dropdown-menu-right dropsize" role="menu">
                                <form class="form-horizontal" role="form">
                                 
                                  <div class="form-group">
                                    <label for="contain">Code :</label>
                                    <input class="form-control" id ="itmCode" type="text" />
                                  </div>
                                  <div class="form-group">
                                    <label for="contain">Name :</label>
                                    <input class="form-control" id ="itmName" type="text" />
                                  </div>
                                 
                                  
                                  <div class="form-group" >
					                <label for="filter">Item Category</label>
										<select id="itm_category_id"  class="form-control" 
											name="itm_category_id" ng-init="itm_category_id=itmCatgryList1[0]"
											ng-options="v.id as v.name for v in itmCatgryList1"
											ng-model="itm_category_id">
										</select>			
				                 </div>
				                   <div class="form-group">
                                    <label for="filter">Item Type</label>
                                    <select class="form-control" id="itemtype">
                                      <option value="" selected>ALL</option>
                                        <option value="0" >PURCHASED</option>
                                        <option value="1">MANUFACTURED</option>
                                    </select>
                                  </div>
                                  
                                  <button style="float: right;  margin-left: 10px;" type="button" class="btn btn-default" data-dismiss="modal" data-toggle="dropdown" >Close</button>
                                  
                                   <button  style="float: right;" type="submit"  ng-click="advSearch();" class="btn btn-primary" data-dismiss="modal" data-toggle="dropdown"  style="  height: 29px;margin-top: 1px;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                                  
                                </form>
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" ><span class="glyphicon glyphicon-search" aria-hidden="true" ng-click="Search();" data-dismiss="modal" data-toggle="dropdown"></span></button>
                    </div>
                </div>
            </div>
          </div>
        </div>
	</div>

	</div>


	<!-- Main content -->
	<section class="content" id="module_content">
		<div class="row">
			<div class="col-xs-12">


				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title"></h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="box-header"></div>
						
<jsp:directive.include file="../common/includes/common_popup_form.jsp" />
						<div ng-controller="item_master as item">
							
							<circle-spinner ng-show="prograssing1"></circle-spinner>
							
							 <div class=" modal fade" id="comboitem" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content ">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Sale Combo Stock Item</h4>
        </div>
        <div class="modal-body">
         <div class="col-sm-12" >
         
       
         
         
         	<div class="form-group" id="form_div_Sale Item ">
				<label for="pincode" class="col-sm-3 control-label">Sale Item </label>
				<div class="col-sm-3">
					<div class="">
						
						<div class="col-md-6" id="saleitemomboitemname"></div>
						
					</div>
				</div>
			</div>
			 </div>
			 
			       <div class="col-sm-12" style=" margin-bottom: 15px;">
				<div class="form-group" id="form_div_Sale Item ">
				<label for="pincode" class="col-sm-3 control-label">Sale Combo Content Item  </label>
				<div class="col-sm-3">
					<div class="">
						
						<select class="form-control" id="combo_content_id"
									name="valuation_method" ng-model="formData.combo_content_id"
									 ng-change="populateCombo(formData.combo_content_id)">
									<option value="" >Select</option>
									<c:forEach items="${combocontent}"
										var="combocontent">
										<option
											value="${combocontent.getId()}">${combocontent.getName()}</option>
									</c:forEach>
								</select>
								
						<span
							class="input-group-addon" id="form_div_name_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
					</div>
				</div>
			</div>
			
			</div>
			
			
         
         
         <div class="form-group " id="form_div_items_tbl">
							
			<div class="cmmn_maain_tbl">
				<circle-spinner ng-show="prograssing"></circle-spinner>
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">

					<tr class="active">
						<th>Default</th>
						<th>Item Code/Item Name</th>
						<th>Price Difference</th>
						<th>Qty</th>
						<th>Applicable</th>
					</tr>
					<tr ng:repeat="item in salecombocontentData.items">
						<td style="width: 20px;"><input type="checkbox"
							ng-model="item.is_default" />    </td>
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
							<td style="width: 109px;"><input type="text" ng-disabled="disable_all"  valid-number
								id="price_diff" ng-model="item.price_diff" name="uomcode" class="input-mini form-control"></td>
						<td style="width: 96px;"><input type="text"
							ng:model="item.price_difference" select-on-click id="itemqty"  row-delete="removeItem($index)"  row-save="save_data()"
							ng-disabled="disable_all" valid-number maxlength="${10+settings['decimalPlace']+1}" 
							class="input-mini form-control algn_rgt"></td>

					
						<td style="width: 101px; display: none;"><input type="text"
							ng:model="item.request_status" ng-disabled="true" valid-number
							maxlength="10" class="input-mini form-control algn_rgt"></td>

						<td style="width: 50px;"><input type="checkbox"
							ng-model="item.is_applicable" /> </td>
					</tr>
					<tr>
					
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td ></td>
					</tr>
				</table>
				</div>
			</div>
         
         
          
          
       
        
        <div class="modal-footer">

<button type="button" id="popEdit" class="btn btn-primary" ng-click="fun_save_combo_item()" ng-hide="popbtnhide" style="background-color: #00c0ef;">Submit</button>
                   <button type="button" class="btn btn-default" data-dismiss="modal" style="background: #de9504;">Cancel</button>





           
        </div>
        
            </div>
      </div>
      
    </div>
  </div>
  
  
  
  
  
							
							
							<div class="form-group" ng-show="show_table" id="div_table_show">
								<table datatable="" dt-options="item.dtOptions"
									dt-columns="item.dtColumns" dt-instance="item.dtInstance"
									class="table dataClass"></table>
							</div>
							<jsp:directive.include file="../itemmaster/form.jsp" />
						
								 <!-- Modal -->
  <div class="item_master_mdl_div modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content grpitmformmdl">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Group Item Details</h4>
        </div>
        <div class="modal-body">
          <!-- <p>Some text in the modal.</p> -->
          
          <input type="hidden" id="id_modal" name="id_modal" value=""
				ng-model="formData1.id">
         <div class="form-group " id="form_div_code_modal">
						<label for="code_modal"
							class="col-sm-3 control-label "><spring:message
								code="common.code"></spring:message> <i class="text-danger">*</i></label>
						<div class="col-sm-6">
							<div class="input-group">
								<input type="text" class="form-control bold"
									
									maxlength="10" name="code_modal" id="code_modal" ng-model="formData1.code"
									ng-disabled="disable_all_popup" capitalize placeholder="" ng-change="isCodeExistis1(formData1.code)"> 
									<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_code_modal_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
								<span ng-bind="existing_code1" class="existing_code_lbl"
									ng-hide="hide_code_existing_er1"></span>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_name_modal">
						<label for="name_modal"
							class="col-sm-3 control-label "><spring:message
								code="common.name"></spring:message> <i class="text-danger">*</i></label>

						<div class="col-sm-6">
							<div class="input-group">
								<input type="text" class="form-control"
									name="name_modal"  id="name_modal" ng-model="formData1.name" maxlength="50"
									ng-disabled="disable_all_popup" placeholder=""> <span
									class="input-group-addon" id="form_div_name_modal_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
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
				
<div class="form-group" id="form_div_grsub_class_id">
		<label for="group_item_modal" class="col-sm-3 control-label ">  Item Class <!-- <spring:message
				code="itemmaster.groupItemModal"></spring:message> --></label>
		<div class="col-sm-4">
			<div class="input-group col-sm-12">
				<select class="form-control" id="grsub_class_id"
					name="grsub_class_id"
					ng-options="v.id as v.name for v in displayOrder"
					ng-model="formData1.sub_class_id" ng-disabled="disable_all_popup">

				</select>
			</div>
		</div>
	</div>
	
		<div class="form-group" id="form_grdisplay_order">
		<label for="grdisplay_order" class="col-sm-3 control-label ">Display Order <!-- <spring:message
				code="itemmaster.fg_color"></spring:message> --> <!-- <i
									class="text-danger">*</i> --></label>
		<div class=" col-sm-5">
			<div class="input-group col-sm-12">
				<input id="grdisplay_order" valid-number
					name="grdisplay_order" class="grdisplay_order fg" type="text"
					ng-model="formData1.display_order" ng-disabled="disable_all_popup">
			</div>
		</div>

	</div>
				
				<div class="form-group" id="form_div_group_item_modal">
					<label for="group_item_modal" class="col-sm-3 control-label "><spring:message
							code="itemmaster.groupItemModal"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control" id="group_item_modal"
								name="group_item_modal"
								ng-options="v.id as v.name for v in groupItem"
								 ng-model="formData1.group_item_id"
								ng-disabled="disable_all_popup">
								
								<option value="" selected="selected">select</option>
							</select>
						</div>
					</div>
				</div>
				
				
				
				
				
							<div class="form-group" id="form_div_fg_color_modal">
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
						
						
							<div class="form-group " id="form_div_bg_color_modal">
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
        
        <div class="modal-footer">
          <button type="button" class="btn btn-primary"  style="background: #6e9737;" ng-click="fun_save_groupItm()" ng-show="popbtnhide">Save</button>
           <button type="button" class="btn btn-danger" ng-click="fun_discard_groupItm()" ng-show="popbtnhide">Discard</button>
           <button type="button" class="btn btn-default" data-dismiss="modal" style="background: #de9504;">Close</button>
            <button type="button" id="popEdit" class="btn btn-primary" ng-click="fun_edit_groupItm()" ng-hide="popbtnhide" style="background-color: #00c0ef;">Edit</button>
          <button type="button" class="btn btn-primary" ng-click="fun_delete_groupItm()" ng-hide="popbtnhide" style="background-color: #d73925;">Delete</button>

           
        </div>
      </div>
      
    </div>
  </div>
  

  
  
  
 
  
  
  
  
  
  
  
  
  
  
  
  
  


  
  
  
  
  
  
  
  
  
  
  
  
							<%-- <jsp:directive.include
								file="../common/includes/common_popup_form.jsp" /> --%>



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
</div>
<!-- /.content-wrapper -->



<!-- include form-->

<!-- common js include below -->
<jsp:directive.include file="../common/includes/footer.jsp" />


<!-- page script -->

<!-- custom js include below -->

<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
<script type="text/javascript">
 var isLiteVersion=${lite_version};
 
</script>
<script
	src="<c:url value="/resources/mrp/itemmaster/js/itemmaster.js?n=1" />"></script>
<script
	src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
<script>
	angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
/* 	var settings = new Array();
	settings['currentcompanyid'] = '<c:out value="${currentcompanydetails.id}"/>'; */
</script>
<script type="text/javascript">

jQuery(document).ready(function($) {

	if (window.history && window.history.pushState) {

	window.history.pushState('forward', null, './list');

	$(window).on('popstate', function() {
		  
	
		if($('#show_form').val()==1){  
			
			window.location.href = "list";
			$('#show_form').val(0); 
		}else{
			 window.history.back();
		 }  

	});

	}
	});

</script>
</body>
</html>
