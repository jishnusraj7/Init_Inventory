//Controller for Table and Form 

mrpApp.controller('prodbalcntrl', prodbalcntrl);

function prodbalcntrl($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window,$filter ) {
	$scope.option=2;
	$('#prod_bom_report_div').hide();
	$("#profit_summary_report_div").hide();
	$scope.stockData = [];
	$scope.selectshopShow=true;
	$scope.shopShow=true;
	set_sub_menu("#report");	
	$scope.companyId="";
	$scope.stock_item_id="";
	$scope.ItmShw=true;
	setMenuSelected("#productionreport_left_menu");		
	$scope.shops=[];
	$scope.AllShops=[];
	$scope.AllCustomers=[];
	$scope.summary=true;
	$scope.is_active=false;
	$scope.is_active_select=false;
	$scope.serch="";
	$scope.user = {
			roles: []
	};

	$scope.userSelect={
			roles:[]
	};

	$scope.checkAll = function() {
		$scope.user.roles = angular.copy($scope.shops);
	};
	$scope.uncheckAll = function() {
		$scope.user.roles = [];
	};

	$scope.checkAllSelect = function() {
		$scope.userSelect.roles = angular.copy($scope.selectedShopList);
	};
	$scope.uncheckAllSelect = function() {
		$scope.userSelect.roles = [];
	};


	$scope.moveShop=function()
	{

		if($scope.is_active==true)
		{

			$scope.checkAll();
		}
		else
		{
			$scope.uncheckAll();
		}
	}

	$scope.moveShopSelect=function()
	{

		if($scope.is_active_select==true)
		{

			$scope.checkAllSelect();
		}
		else
		{
			$scope.uncheckAllSelect();
		}
	}
	
	$scope.selectedShop = []; 
	$scope.selectedShopList = [];
	$scope.btnRight = function () {
		//move selected.
		if($scope.user.roles.length != 0)
		{
			if($scope.is_active == true )
				$scope.is_active = false;

			for(var i=0; i<$scope.user.roles.length; i++)
				$scope.selectedShopList.push($scope.user.roles[i]);

			//remove the ones that were moved from the source container.
			for(var i=0; i<$scope.user.roles.length; i++){
				for(var j=0; j<$scope.shops.length; j++){
					if( $scope.user.roles[i].id == $scope.shops[j].id)
						$scope.shops.splice(j, 1);
				}
			}
		}
		$scope.user.roles = [];
		$scope.is_active_select = false;
	}

	$scope.btnLeft = function () {
		//move selected.
		if($scope.userSelect.roles.length!=0) {

			if($scope.is_active_select == true )
				$scope.is_active_select = false;

			for(var i=0;i<$scope.userSelect.roles.length;i++){
				$scope.shops.push($scope.userSelect.roles[i]);
			}
			//remove the ones that were moved from the source container.

			for(var i=0; i<$scope.userSelect.roles.length; i++){
				for(var j=0; j<$scope.selectedShopList.length; j++){
					if( $scope.userSelect.roles[i].id == $scope.selectedShopList[j].id)
						$scope.selectedShopList.splice(j, 1);
				}
			}
		}
		$scope.userSelect.roles = [];
		$scope.is_active = false;
	}
	
	$scope.labelname="Shops";
	$scope.hideFun = function()
	{
		$scope.is_active_select=false;
		$scope.is_active=false;
		$scope.is_active_stock=false;
		$scope.is_active_select_stock=false;
		$scope.user.roles = [];
		$scope.userSelect.roles=[];
		$scope.userItem.roles=[];
		$scope.userSelectItem.roles=[];

		$scope.serch="";
		if($scope.option==1)
		{

			$scope.labelname="Customer";
			$scope.serch="";
			var date = new Date(), y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);


			$timeout(function () {
				$scope.enddate = dateForm(lastDay);
				$scope.startdate = dateForm(curDate);
			}, 1); 

			$scope.selectedItemList=[];
			angular.copy($scope.itemsData1, $scope.itemsData);
			$scope.serchStock="";
			$scope.filterItemData = [];
			$scope.summary=true;
			angular.copy($scope.AvailablecusItems,$scope.shops);
			$scope.shops=[];
			$scope.shops=$scope.customer;
			$scope.selectedShopList=[];
			$scope.errMsg="";
			$scope.selectshopShow=true;
			$scope.shopShow=true;
			$scope.selectedShop=[];
			$scope.choicesItem=[];
			$("#form_itemData").find(".acontainer input").val("");

			$scope.item_id ="";
			$scope.ItmShw=true;
			angular.copy($scope.AvailablecusItems, $scope.shops);
			angular.copy($scope.AvailablecusItems, $scope.AllCustomers);
		}
		else if($scope.option==2)
		{

			var date = new Date(), y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			$timeout(function () {
				$scope.enddate = dateForm(lastDay);
				$scope.startdate = dateForm(curDate);
			}, 1); 

			$scope.labelname="Shops";

			$scope.summary=true;
			angular.copy($scope.AvailableshpsItems,$scope.shops);
			$scope.selectedShopList=[];

			$scope.errMsg="";
			$scope.choicesItem=[];
			$scope.selectshopShow=true;
			$scope.shopShow=true;
			$scope.selectedShop=[];
			$("#form_itemData").find(".acontainer input").val("");
			$scope.filterItemData = [];
			$scope.selectedItemList=[];
			angular.copy($scope.itemsData1, $scope.itemsData);
			$scope.serchStock="";

			$scope.ItmShw=true;
			$scope.AllShops=$scope.shops;
		}
		/*else if($scope.option==3)
			{


			var date = new Date(), y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			$timeout(function () {
				$scope.enddate = dateForm(lastDay);
				$scope.startdate = dateForm(firstDay);
			}, 1); 
			$scope.summary=false;

			$scope.errMsg="";
			$scope.choicesItem=[];
			$scope.selectshopShow=false;
			$scope.shopShow=false;
			$scope.ItmShw=false;
			$scope.selectedShop=[];
			$scope.itemList.stock_item_id ="";
			$scope.itemList.itemname ="";
			$scope.itemList.code ="";
			$("#form_itemData").find(".acontainer input").val("");


			}
		 */		
		$("#form_div_startdate").removeClass("has-error");
		$("#form_div_startdate_error").hide();

		$("#form_div_enddate").removeClass("has-error");
		$("#form_div_enddate_error").hide();
		$("#divErrormsg2").hide();
	}

	$('#select_prodreport').click(function(){
		if($('#select_prodreport').val()=="number:5")
		{
			var date = new Date(), y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);
			$scope.$apply(function(){
				$("#form_itemData").find(".acontainer input").val("");
				$scope.selectedShop=[];
				$scope.choicesItem=[];
				$scope.errMsg="";
				$scope.startdate = dateForm(curDate);
				$scope.selectedItemList=[];
				angular.copy($scope.itemsData1, $scope.itemsData);
				$scope.serchStock="";
				$scope.serch="";
				$scope.selectedShopList=[];
				angular.copy($scope.AvailableshpsItems, $scope.shops);
				angular.copy($scope.AvailablecusItems, $scope.customer);
				$scope.item_id ="";
				
				if($scope.labelname=="Shops"){
					$scope.option=2;
				}
				else if($scope.labelname=="Customer"){
					$scope.option=1;
				}
				angular.copy($scope.AvailableshpsItems,$scope.shops);
				$scope.selectedShopList=[];
				$scope.serch="";
				/*$scope.enddate = dateForm(lastDay);
			$scope.startdate = dateForm(firstDay);*/

			});
			$('#prod_report_div').hide();
			$('#prod_bom_report_div').hide();
			$("#profit_summary_report_div").hide();
			$("#bom_analysis_report_div").hide();
			$("#bom_rate_comparison_div").hide();
			$("#stockadjustment_report_div").hide();
			$("#prod_bal_report_div").show();

			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();

			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#divErrormsg2").hide();





		}

	});


	$("#form_div_startdate").removeClass("has-error");
	$("#form_div_startdate_error").hide();

	$("#form_div_enddate").removeClass("has-error");
	$("#form_div_enddate_error").hide();
	$("#divErrormsg2").hide();
	$("#profit_summary_report_div").hide();


	$("#exportpdf").click(function(){
		var selectedreport=$('#select_prodreport').val();


		if(selectedreport=="number:5"){
			$("#form_div_startdate_bal").removeClass("has-error");
			$("#form_div_startdate_error_bal").hide();


			console.log( $scope.selectedShop );
			$scope.compnyIds=[];
			$scope.itemIds=[];
			if($scope.selectedShopList.length!=0)
			{
				for(var i=0;i<$scope.selectedShopList.length;i++ )
				{
					
					if($scope.option==2){
						$scope.compnyIds.push($scope.selectedShopList[i].shop_id);
					}
					else if($scope.option==1){
						$scope.compnyIds
						.push($scope.selectedShopList[i].id);
					}
					
				}
			}

			$scope.stock_iem_Ids=[];
			if($scope.selectedItemList.length!=0)
			{
				for(var i=0;i<$scope.selectedItemList.length;i++ )
				{
					$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
				}
			}


			//console.log($scope.compnyIds);

			if($scope.startdate!=undefined && $scope.startdate!="") {
				var strtDate=process($scope.startdate);

				$window.open("Prodbalance Report?&option="+$scope.option+"&companyId="+$scope.compnyIds+"&stock_item_id="+$scope.stock_iem_Ids+"&startdate="+getMysqlFormat($scope.startdate)+""+ "&pdfExcel=pdf", '_blank');
				$("#form_div_startdate").removeClass("has-error");
				$("#form_div_startdate_error").hide();


			}else{

				if($scope.startdate == undefined || $scope.startdate == "" || $scope.startdate == null){
					$("#form_div_startdate_bal").addClass("has-error");
					$("#form_div_startdate_error_bal").show();
					flg = false;
				}else{
					$("#form_div_startdate_bal").removeClass("has-error");
					$("#form_div_startdate_error_bal").hide();
				}





			}
		}
	});
	
	$("#excelView").click(function(){
		var selectedreport=$('#select_prodreport').val();


		if(selectedreport=="number:5"){
			$("#form_div_startdate_bal").removeClass("has-error");
			$("#form_div_startdate_error_bal").hide();


			console.log( $scope.selectedShop );
			$scope.compnyIds=[];
			$scope.itemIds=[];
			if($scope.selectedShopList.length!=0)
			{
				for(var i=0;i<$scope.selectedShopList.length;i++ )
				{
					
					if($scope.option==2){
						$scope.compnyIds.push($scope.selectedShopList[i].shop_id);
					}
					else if($scope.option==1){
						$scope.compnyIds
						.push($scope.selectedShopList[i].id);
					}
					
				}
			}

			$scope.stock_iem_Ids=[];
			if($scope.selectedItemList.length!=0)
			{
				for(var i=0;i<$scope.selectedItemList.length;i++ )
				{
					$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
				}
			}


			//console.log($scope.compnyIds);

			if($scope.startdate!=undefined && $scope.startdate!="") {
				var strtDate=process($scope.startdate);

				$window.open("Prodbalance Report?&option="+$scope.option+"&companyId="+$scope.compnyIds+"&stock_item_id="+$scope.stock_iem_Ids+"&startdate="+getMysqlFormat($scope.startdate)+""+ "&pdfExcel=excel", '_blank');
				$("#form_div_startdate").removeClass("has-error");
				$("#form_div_startdate_error").hide();


			}else{

				if($scope.startdate == undefined || $scope.startdate == "" || $scope.startdate == null){
					$("#form_div_startdate_bal").addClass("has-error");
					$("#form_div_startdate_error_bal").show();
					flg = false;
				}else{
					$("#form_div_startdate_bal").removeClass("has-error");
					$("#form_div_startdate_error_bal").hide();
				}





			}
		}
	});

	$scope.AvailableshpsItems=[];
	$scope.AvailablecusItems=[];
	$scope.customer=[];



	$scope.filterItemData = [];

	$scope.dataFetchStock=function()
	{



		$scope.searchValue();

	}

	$scope.searchValue=function()
	{
		$scope.filterItemData = [];
		if($scope.serchStock!="")
		{
			var searchData = eval("/.*" + $scope.serchStock + "/gi");

			$.each($scope.itemsData , function (i, v) {
				if (v.name.search(new RegExp(searchData)) != -1) {

					if (v.is_active == 1 && v.is_manufactured == 1) {
						$scope.filterItemData.push(v);
					}
				}
			});


		}
	}



//	checkboxlist

	$scope.is_active_stock=false;
	$scope.is_active_select_stock=false;
	$scope.serchStock="";
	$scope.userItem = {
			roles: []
	};

	$scope.userSelectItem={
			roles:[]
	};


	$scope.checkAllStock = function() {
		$scope.userItem.roles = angular.copy( $scope.filterItemData );
	};
	$scope.uncheckAllStock = function() {
		$scope.userItem.roles = [];
	};

	$scope.checkAllSelectStock = function() {
		$scope.userSelectItem.roles = angular.copy($scope.selectedItemList);
	};
	$scope.uncheckAllSelectStock = function() {
		$scope.userSelectItem.roles = [];
	};


	$scope.moveItemStock=function()
	{

		if($scope.is_active_stock==true)
		{

			$scope.checkAllStock();
		}
		else
		{
			$scope.uncheckAllStock();
		}
	}

	$scope.moveItemSelectStock=function()
	{

		if($scope.is_active_select_stock==true)
		{

			$scope.checkAllSelectStock();
		}
		else
		{
			$scope.uncheckAllSelectStock();
		}
	}
	$scope.selectedShop = []; 


	$scope.selectedItemList=[];


	$scope.btnRightStock = function () {
		//move selected.
		if($scope.userItem.roles.length!=0)
		{
			if($scope.is_active_stock==true )
			{
				$scope.is_active_stock=false;
			}

			for(var i=0;i<$scope.userItem.roles.length;i++)
			{
				$scope.selectedItemList.push($scope.userItem.roles[i]);
			}


			//remove the ones that were moved from the source container.

			for(var i=0;i<$scope.userItem.roles.length;i++)
			{
				for(var j=0;j<$scope.itemsData.length;j++)
				{
					if( $scope.userItem.roles[i].id==$scope.itemsData[j].id)
					{
						$scope.itemsData.splice(j, 1);
					}
				}
			}

		}
		$scope.searchValue();
		$scope.userItem.roles=[];



	}



	$scope.btnLeftStock = function () {
		//move selected.
		if($scope.userSelectItem.roles.length!=0)
		{

			if($scope.is_active_select_stock==true )
			{
				$scope.is_active_select_stock=false;
			}


			for(var i=0;i<$scope.userSelectItem.roles.length;i++)
			{
				$scope.itemsData.push($scope.userSelectItem.roles[i]);
			}
			//remove the ones that were moved from the source container.

			for(var i=0;i<$scope.userSelectItem.roles.length;i++)
			{
				for(var j=0;j<$scope.selectedItemList.length;j++)
				{
					if( $scope.userSelectItem.roles[i].id==$scope.selectedItemList[j].id)
					{
						$scope.selectedItemList.splice(j, 1);
					}
				}
			}


		}
		$scope.searchValue();
		$scope.userSelectItem.roles=[];

	}


	$scope.unselectstockItem=function(){
		if($scope.filterItemData.length!=$scope.userItem.roles.length)
		{
			$scope.is_active_stock=false;
		}
		else
		{
			$scope.is_active_stock=true;
		}


	}
	$scope.unselectstockItemSelect=function(){
		if($scope.selectedItemList.length!=$scope.userSelectItem.roles.length)
		{
			$scope.is_active_select_stock=false;
		}
		else
		{
			$scope.is_active_select_stock=true;
		}


	}


	$scope.unselectshopItem=function(){
		//$scope.is_active=false;
		//$scope.is_active_select=false;
		if($scope.shops.length!=$scope.user.roles.length)
		{
			$scope.is_active=false;
		}
		else
		{
			$scope.is_active=true;
		}

	}
	$scope.unselectshopItemSelect=function()
	{
		if($scope.selectedShopList.length!=$scope.userSelect.roles.length)
		{
			$scope.is_active_select=false;
		}
		else
		{
			$scope.is_active_select=true;
		}
	}

	$scope.getFilteredShops=function(shops,serch)
	{
		/*if($scope.option ==2)
				{*/
		var deleteId;
		$scope.is_active=false;
		$scope.user.roles=[];
		if($scope.option ==2)
		{
			$scope.serchDataShops= $filter('filter')($scope.AllShops, serch);


		}else if($scope.option ==1)
		{
			$scope.serchDataShops=$filter('filter')($scope.AllCustomers, serch);
		}
		//console.log("filtered: "+ $scope.serchDataShops);
		$scope.shops=$scope.serchDataShops;
		filterSelectedShops1();

		if(serch=="")
		{
			if($scope.option ==2)
			{
				angular.copy($scope.AllShops, $scope.shops);
			}else if($scope.option ==1)
			{
				angular.copy($scope.AllCustomers, $scope.shops);
			}
			filterSelectedShops1();
		}
		/*}*/
	};
	function filterSelectedShops1()
	{
		for(var i=0;i<$scope.selectedShopList.length;i++)
		{
			deleteId=$scope.selectedShopList[i].id;
			$scope.shops=$scope.shops.filter(function(obj)
					{
				return obj.id != deleteId;
					});
		}}
	//checkbox list end    












	$scope.itemsData = [];
	$scope.itemsData1 = [];
	$http({
		url : '../planning/formJsonData',
		method : "GET",
	}).then(function(response) {

		$scope.AvailableshpsItems = response.data.customerData;
		$scope.stockData = response.data.itemsData;

		$scope.itemsData1 = response.data.itemsData;
		angular.copy($scope.itemsData1, $scope.itemsData);
		$scope.AvailablecusItems=response.data.customerIds;
		angular.copy($scope.AvailableshpsItems, $scope.shops);
		angular.copy($scope.AvailablecusItems, $scope.customer);
		angular.copy($scope.AvailableshpsItems,$scope.AllShops);
		angular.copy($scope.AvailablecusItems,$scope.AllCustomers);
		//$scope.customer.splice(0,0,{id : "" ,name : "select"});
		//$scope.shops.splice(0,0,{id:"",name:"select"})
		$scope.custShow=false;
		$scope.shopShow=true;

	}, function(response) { // optional
	});



}



mrpApp.directive('daterangePicker2', [function() {
	return {
		controller: function ($scope,$http) {

			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var dateFormat = get_date_format();
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			controller.enddate =  dateForm(curDate);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			controller.startdate = dateForm(curDate);

			$(elem).inputmask({
				mask : dateFormat.mask,
				placeholder : dateFormat.format,
				alias : "date",
			});

			$(elem).daterangepicker({
				"format" : dateFormat.format,
				"drops" : "down",
				"singleDatePicker" : true,
				"showDropdowns" : true,
				"autoApply" : false,
				"linkedCalendars" : false,

			}, function(start, end, label) {
			});
		}
	};
}]);



