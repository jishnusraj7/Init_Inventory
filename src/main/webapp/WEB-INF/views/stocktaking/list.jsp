<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%-- <%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockDisposalReasonType"%>
<c:set var="enumValues" value="<%=stockDisposalReasonType.values()%>" /> --%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />

<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/stocktaking/css/stocktaking.css' />">

</head>
<body>
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
		<div class="list-content-header" ng-controller="btn_ctrl">
			<!-- Content Header (Page header) -->
			<section class="content-header content-header_second">
				<h1>STOCK TAKING</h1>
				<div ng-hide="succ_alertMessageStatus" class="alert-box"
					id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>

			</section>
		</div>
		<!-- Main content -->
		<section class="content" id="module_content">
			<div class="row">

					<div class="box">
						<div class="box-header with-border">
							<h3 class="box-title"></h3>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div ng-controller="stocktaking as item"
								style="margin-top: 56px;">
								
								<div class="box-header">
									<h1>STOCK TAKING</h1>
								</div>
								<div  class="page-content-header stock-taking-header">
									<div class="row ">
									<!-- Done by anandu on 22-01-2020-->
										<div class="col-md-12">
											<div class="col-md-2" id="stock_date">
												<label  class="col-md-12 no-padding">Stock Taking Date</label>
												<div class="col-md-12 no-padding right-inner-addon">
													<input class="form-control prod-date" type="text" style="width: 70%; float: left;"
													id="production_date" daterange-picker ng-model="prod_date"
													ng-change="getData()">
													<i class="fa fa-calendar" id="calender_icon" style="margin-left: -23px;" ></i>
												</div>
											</div>
											<!-- Done by anandu on 22-01-2020-->
											<div class="col-md-2" id="dep_cat">
											<label class="col-md-12 no-padding">Department</label>
											<select class="col-md-12 no-padding form-control prod-date" ng-model="department_id" ng-change="getData()">
												<option ng-repeat="dept in departments" value="{{dept.id}}">{{dept.name}}</option>
											</select>
											</div>
											<!-- Done by anandu on 22-01-2020-->
											<div class="col-md-2" id="dep_cat">
											<label class="col-md-12 no-padding">Category</label>
											<select class="col-md-12 no-padding form-control prod-date" ng-model="item_category_id" ng-change="getData()" id="cat_id">
												<option ng-repeat="catg in item_category" value="{{catg.id}}">{{catg.name}}</option>
											</select>
											</div>
											<div class="col-md-3 no-padding" id="search_div">
											<div class="col-md-10">
												<input class="form-control search_item" id="search_item" type="text" ng-model="searchValue" placeholder="search">
											</div>
											<button ng-click="focusRow(searchValue)" class="col-md-2 search_btns btn" ><i class="fa fa-search" aria-hidden="true"></i></button>
											</div>
											
											<div class="col-md-2 pull-right">
												<button type="button" id="btn_update" class="btn btn-success"
													ng-click="updateStock()"
													ng-disabled="isDisabled">UPDATE STOCK</button>
											</div>
											
										
										</div>
										<!-- <div class="col-md-4">
											<div class="pull-right">
												<button type="button" id="btn_update" class="btn btn-success"
													ng-click="updateStock()"
													ng-disabled="isDisabled">UPDATE STOCK</button>
											</div>
										</div> --> 
									</div>
								</div>
								
								<circle-spinner ng-show="prograssing"></circle-spinner>
								
								<div class="form-group  stock-taking-content" id="div_table_show" >								
									<!-- <table datatable="" dt-options="item.dtOptions"
											dt-columns="item.dtColumns" dt-instance="item.dtInstance"
											class="table dataClass"></table> --> 
											 <div style="width:100%; overflow:auto;">
											 <div id="div_search_data">
									<table class="table table-bordered table-fixed" ng-hide="prograssing" 
									id="items_table">
										<thead id="stockTaking_hdr">
										 <div class="form-group">
											<tr class="active" style="background-color: #3498db;">
												<!-- <th>#</th> -->
												<th style="text-align: center;" class="col-xs-2">CODE</th>
												<th style="text-align: center;" class="col-xs-3">ITEM NAME</th>
												<th style="text-align: center;" class="col-xs-1">UOM</th>
												<th style="text-align: center;" class="col-xs-2">SYSTEM STOCK</th>
												<!-- <th style="text-align: center;" class="col-xs-2">RETURN STOCK</th> -->
												<th style="text-align: center;" class="col-xs-2">VARIANCE</th>
												<th style="text-align: center;" class="col-xs-2">ACTUAL STOCK</th>
												
											</tr>
											</div>
										</thead>

										<tbody>
											<tr ng-if="stock_details.length === 0">
												<td colspan="10">No data available</td>
											</tr>
											
											<tr ng-repeat="item in stock_details" ng-class="tr_{{$index}}"  id="search_data">
                                                  
									 		<!-- <td class="col-xs-1"><div class="tbl_tx_bx">
														<input type="text" ng-model="item.code"
															id="stock_item_code" name="name" class="form-control"
															ng-disabled="true" style="text-align: left;">
													</div>
												</td> -->
                                                 <td class="col-xs-2" id="stock_item_code"><div class="tbl_tx_bx">{{item.code}}</div></td>
                                                 <td class="col-xs-3"  id="stock_item_name"><div class="tbl_tx_bx" id="scroll_item">{{item.name}}</div></td>
												 <td class="col-xs-1"  id="uom_code"><div class="tbl_tx_bx">{{item.uom_code}}</div></td>
												  <td class="col-xs-2" id="current_stock"><div class="tbl_tx_bx" style="text-align: right;width: 103px;">{{item.current_stock}}</div></td>
												<!-- <td class="col-xs-2"><div class="tbl_tx_bx">
														<input type="text" ng-model="item.return_qty"
															style="text-align: right;width: 98px;" id="return_qty" name="return_qty"
															class="form-control" ng-disabled="true" >
													</div></td> -->
														<td class="col-xs-2" ><div class="tbl_tx_bx" style="text-align: right;width: 63px;">{{differenceView(item.actual_stock,item.current_stock)}}</div></td> 
												<td class="col-xs-2">
													<div class="tbl_tx_bx">
														<input type="text" ng-model="item.actual_stock"
															style="text-align: right" 
															name="actual_stock" class="form-control actual_stock"
															ng-disabled="false" id="actual_stock"
															onkeypress="return isNumberKey(event,this)"
															onpaste="return false" oncopy="return false"
															ng-keyup="EnableDisable(this)"
															maxlength="${10+settings['decimalPlace']+1}" valid-number id="id_{{$index}}">
													</div>
												</td>
											</tr>
												<!-- <td class="col-xs-3"><div class="tbl_tx_bx" id="scroll_item">
														<input type="text" ng-model="item.name"  id="stock_item_name"
															class="form-control stock_item_name" ng-disabled="true" style="text-align: left;">
													</div>
												</td> -->
												<!-- <td class="col-xs-2"><div class="tbl_tx_bx">
														<input type="text" ng-model="item.uom_code"
														 id="uom_code" name="uom_code"
															class="form-control" ng-disabled="true" style="text-align: left;">
													</div></td> -->
													
													<!-- <td class="col-xs-2"><div class="tbl_tx_bx">
														<input type="text" ng-model="item.current_stock"
															style="text-align: right;width: 98px;" id="current_stock" name="current_stock"
															class="form-control" ng-disabled="true" >
													</div></td> -->
													
													<!-- <td class="col-xs-1"><div class="tbl_tx_bx">
														<input type="text" value="{{differenceView(item.actual_stock,item.current_stock)}}"
															style="text-align: right; width: 63px;"  name="variance"
															class="form-control variance_td" ng-disabled="true">
													</div></td>   -->

<!--                                                <td class="col-xs-1" id="stock_item_code"><div class="tbl_tx_bx">{{item.code}}</div></td>
                                               <td class="col-xs-4"  id="stock_item_name"><div class="tbl_tx_bx" id="scroll_item">{{item.name}}</div></td>
                                                <td class="col-xs-2"  id="uom_code"><div class="tbl_tx_bx">{{item.uom_code}}</div></td>
                                                <td class="col-xs-2" id="current_stock"><div class="tbl_tx_bx" style="text-align: right;width: 103px;">{{item.current_stock}}</div></td>
                                                <td class="col-xs-1" ><div class="tbl_tx_bx" style="text-align: right;width: 63px;">{{differenceView(item.actual_stock,item.current_stock)}}</div></td> --> 
											
										
										</tbody>
										<tfoot></tfoot>
									</table>
										</div>
									</div>
								</div>

							</div>

							<!-- 	</div> -->

							<!-- /.box-body -->
						</div>
						<!-- /.box -->
				</div>
				<!-- /.row -->
			</div>
		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<!-- include form-->

	<!-- common js include below -->
	<jsp:directive.include file="../common/includes/footer.jsp" />
    <jsp:directive.include file="../common/includes/stockitemdata.jsp" />
	<!-- page script -->

	<!-- custom js include below -->
	<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
	<%-- <script src="<c:url value='/resources/common/js/mrp_common.js?n=1' />"></script> --%>

	<script
		src="<c:url value='/resources/mrp/stocktaking/js/stocktaking.js?n=1' />"></script>
	<script
		src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
	<script>
		angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
	</script>
	<script type="text/javascript">
		jQuery(document).ready(function($) {

			if (window.history && window.history.pushState) {

				window.history.pushState('forward', null, './list');

				$(window).on('popstate', function() {

					if ($('#show_form').val() == 1) {

						window.location.href = "list";
						$('#show_form').val(0);
					} else {
						window.history.back();
					}

				});

			}
		});
//gana
		function isNumberKey(evt, element) {
			  var charCode = (evt.which) ? evt.which : event.keyCode
			  if (charCode > 31 && (charCode < 48 || charCode > 57) && !(charCode == 46 || charCode == 8))
			    return false;
			  else {
			    var len = $(element).val().length;
			    var index = $(element).val().indexOf('.');
			    if (index > 0 && charCode == 46) {
			      return false;
			    }
			    if (index > 0) {
			      var CharAfterdot = (len - 1) - index;
			      if (CharAfterdot >= settings['decimalPlace']) {
			        return false;
			      }
			    }

			  }
			  return true;
			}
		
		
	</script>
</body>
</html>
