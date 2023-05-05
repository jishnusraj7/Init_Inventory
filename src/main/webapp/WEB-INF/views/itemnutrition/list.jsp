
<jsp:directive.include file="../common/includes/page_directives.jsp" />

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
	href="<c:url value="/resources/mrp/itemnutrition/css/itemnutrition.css" />">

<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/momentpicker/angular-moment-picker.css" />">


<style type="text/css">
.dataTables_scrollHeadInner thead {
	position: fixed !important;
	width: 100% !important;
	height: 100% !important;
	z-index: 1;
}

.nutri_datatable {
	text-align: center;
}

.d-flex {
	display: flex;
}

.flex-row {
	display: flex;
	flex-direction: row;
}

.pl-1 {
	padding-left: 1em;
}

.pl-2 {
	padding-left: 2em;
}

.pl-3 {
	padding-left: 3em;
}

.pl-4 {
	padding-left: 4em;
}

.pr-1 {
	padding-right: 1em;
}

.pr-2 {
	padding-right: 2em;
}

.pr-3 {
	padding-right: 3em;
}

.pr-4 {
	padding-right: 4em;
}

.mt-1 {
	margin-top: 1em;
}

.mt-2 {
	margin-top: 2em;
}

.mt-3 {
	margin-top: 3em;
}

.mt-4 {
	margin-top: 4em;
}

.mb-1 {
	margin-bottom: 1em;
}

.mb-2 {
	margin-bottom: 2em;
}

.mb-3 {
	margin-bottom: 3em;
}

.mb-4 {
	margin-bottom: 4em;
}

.w-100 {
	min-width: 100%;
}
</style>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script> -->
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
	<div class="list-content-header" ng-controller="itemMasterNutrition">
		<section class="content-header content-header_second">
			<h1>
				<%-- <spring:message code="itemnutrition.header">new</spring:message> --%>
				ITEM NUTRITION
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box"
				id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box"
				id="err_alertMessageId">{{ err_alertMeaasge }}</div>
		</section>

		<div class="new_box">
			<div class="box cont_div_box">
				<jsp:directive.include file="../common/includes/common_buttons.jsp" />

			</div>
		</div>
		<div class="row">
			<div>
				<div class="col-lg-3 d-flex flex-row pl-2">
					<label for="dish">Dish</label>

					<div class="form-group pl-1 w-100">
						<select class="form-control" id="DishId" class="required"
							name="DishId" ng-disabled="disable_all" required
							ng-model="dishData[0].DishId"
							ng-options="v.DishId as v.DishName for v in dishData"
							ng-change="">
						</select>
					</div>
				</div>

				<div class="col-lg-3 d-flex flex-row pl-2">
					<label for="size">Size</label>

					<div class="form-group pl-1 w-100">
						<input type="text" class="dish_siz form-control" name="size"
							id="size">
					</div>
				</div>

				<div class="col-lg-3 d-flex flex-row pl-2">
					<label for="uom">UOM</label>
					<div class="form-group pl-1 w-100">
						<select class="form-control" id="UnitID" class="required"
							name="UnitID" ng-disabled="disable_all" required
							ng-model="dishDataUOM[0].UnitID"
							ng-options="v.UnitID as v.UnitName for v in dishDataUOM"
							ng-change="">
						</select>
					</div>
				</div>

				<div class="col-lg-3 d-flex flex-row pl-2">
					<div class="form-group pl-1">
						<input type="button" id="fing" name="find" value="FIND"
							ng-click="findbtnClick()">
					</div>
				</div>
			</div>
		</div>
		<br>
		<form ng-hide="NutriInfo.length === 0">
			<div class="row">
				<div class="col-lg-10 d-flex flex-row pl-1">
					<div class="scroll-height scrollY">
						<table id="nutri" class="table table-bordered "
							ng-hide="NutriInfo.length === 0">
							<thead class="dataTables_scrollHeadInner">
								<tr>
									<th>SlNo</th>
									<th>Ingredients</th>
									<th>Market Quantity</th>
									<th>RTC</th>
									<th>RTS</th>
									<th>Unit</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="info in NutriInfo" class="nutri_datatable">
									<td>{{info.SlNo}}</td>
									<td>{{info.IngredientName}}</td>
									<td>{{info.MarketQty}}</td>
									<td>{{info.RTC}}</td>
									<td>{{info.RTS}}</td>
									<td>{{info.Unit}}</td>

								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-lg-2 pl-1">
					<div class="form-group pl-1 col-sm-12">
						<input type="button" id="report" name="report"
							value="Generate Report" ng-click="NutriReportClick()">
					</div>
					<br>
					<div class="form-group pl-1 col-sm-12">
						<input type="button" id="label" name="label"
							value="Generate Label" ng-click="NutriLabelClick()">
					</div>
				</div>
			</div>
		</form>
	</div>


	<!-- Main content -->



	<!-- /.content -->
</div>
<!-- /.content-wrapper -->



<!-- include form-->

<!-- common js include below -->
<jsp:directive.include file="../common/includes/footer.jsp" />


<!-- page script -->

<!-- custom js include below -->

<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
<!-- <script type="text/javascript">
	var isLiteVersion = $
	{
		lite_version
	};
</script> -->
<%-- <script
	src="<c:url value="/resources/mrp/itemnutrition/js/itemnutrition.js?n=1" />">
	</script> --%>

<script type="text/javascript">
	//Define a new AngularJS module named "myApp"
	var myApp = angular.module('mrp_app', []);

	// Define a controller for the "myApp" module
	myApp.controller('itemMasterNutrition', function($scope, $http) {

		$http({
			method : 'GET',
			url : '/mrp/itemnutrition/formJsonData'
		}).then(function(response) {

			//var dishData = response.data.dishData;
			$scope.parnetItem = response.data.dishData;
			$scope.dishData = [ {
				DishId : 0,
				DishName : "-- Select --"
			} ];

			for (var i = 0; i < $scope.parnetItem.length; i++) {
				$scope.dishData.push($scope.parnetItem[i]);
			}

			// TODO: Do something with the dishData
		}, function(error) {
			// Handle error response
			console.log('Error: ' + error.status + ' - ' + error.statusText);
		});

		$http({
			method : 'GET',
			url : '/mrp/itemnutrition/formJsonDataUOM'
		}).then(function(response) {

			//var dishDataUOM = response.data.dishDataUOM;
			$scope.parnetItem = response.data.dishDataUOM;
			$scope.dishDataUOM = [ {
				UnitID : 0,
				UnitName : "-- Select --"
			} ];

			for (var i = 0; i < $scope.parnetItem.length; i++) {
				$scope.dishDataUOM.push($scope.parnetItem[i]);
			}
			// TODO: Do something with the dishData
		}, function(error) {
			// Handle error response
			console.log('Error: ' + error.status + ' - ' + error.statusText);
		});

		var dishid = 0, unitid = 0, size = 0;
		// Define a function to handle button click events
		$scope.findbtnClick = function() {

			//dishid = parseInt(document.querySelector('#DishId').value);
			dishid = parseInt(document.querySelector('#DishId').value.replace(
					/^\D+/g, ''));
			//unitid = parseInt(document.querySelector('#UnitID').value);
			unitid = parseInt(document.querySelector('#UnitID').value.replace(
					/^\D+/g, ''));
			size = parseInt(document.getElementById("size").value);

			//Validation

			var params = {
				"dishid" : dishid,
				"unitid" : unitid,
				"size" : size
			}

			// Use $http to send an HTTP POST request to the Java controller
			$http.post('/mrp/itemnutrition/ShowNutritionInfo', {
				/* data : params */
				data : JSON.stringify(params)
			}).then(function(response) {

				// Handle the response from the Java controller
				$scope.NutriInfo = response.data.jsonArray;

			});

		};

		$scope.NutriLabelClick = function() {
			debugger
			// Use $http to send an HTTP POST request to the Java controller
			
			$http.post('/mrp/itemnutrition/GenerateNutriLabel', {
				/* data : params */
				data : JSON.stringify(0)
			}).then(function(response) {

				
			});

		};

	});
</script>
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
</script>

</body>
</html>

