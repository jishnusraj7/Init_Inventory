var myApp = angular.module('stock-register_report_app', [ 'ngMaterial',
		'ngMessages', 'common_app','checklist-model' ]);

myApp.controller('stockregisterCtrlr', stockregisterCtrlr);

function stockregisterCtrlr($scope, $http, $mdDialog, $rootScope,
		DTOptionsBuilder, DTColumnBuilder, $timeout, $q, $window, FORM_MESSAGES) {
	$scope.formData = {};
	$scope.startmonth = "";
	$scope.endmonth = "";
	$scope.catidList = [];
	$scope.stockData = [];
	$scope.stkfilterData = [];
	$scope.selectMonth = "";
	$scope.department_data = [];
	var vm = this;

	$("#optnDate").prop('checked', true);
	$("#date_div").show();
	$("#month_div").hide();
	$("#form_div_transaction_type").show();
	
	$("#select_report").change(function() {
		
		if ($("#select_report").val() == "number:1") 
		{
			$("#divErrormsg2").hide();
			var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
//			$scope.formData.department_id = depList.id;
//			$scope.formData.department_name1 = depList.name;
//			$scope.formData.department_code1 = depList.code;
			$scope.formData.department_id = parseInt(strings['isDefDepartment']);
			$scope.formData.department_name1 = strings['isDefDepartmentname'];
			$scope.formData.department_code1 = strings['isDefDepartmentcode'];
			$scope.formData.trans_type="";
			$scope.formData.item_category_id = $scope.ItemCtgry[0].id;
//			$("#div_department_code1").find(".acontainer input").val(depList.code);
//			$("#department_name1").val(depList.name);
			$("#div_department_code1").find(".acontainer input").val(strings['isDefDepartmentcode']);
			$("#department_name1").val(strings['isDefDepartmentname']);
			$scope.$apply(function()
			{
				$scope.selectedItemList=[];
				$scope.filterItemData = [];
				//angular.copy($scope.itemsData1,$scope.itemsData);
				angular.copy($scope.itemsData);
				$scope.serch="";
				$scope.user.roles=[];
				$scope.userSelect.roles = [];
				$scope.is_active=false;
				$scope.is_active_select=false;
			});

			$("#form_div_startdate_stockreg").removeClass("has-error");
			$("#form_div_startdate_error_stockreg").hide();
			$("#form_div_enddate_stockreg").removeClass("has-error");
			$("#form_div_enddate_error_stockreg").hide();
			$("#divErrormsg2").hide();

		}
	});

	$("#optnDate").click(function() {

				
		$scope.formData.item_category_id = $scope.ItemCtgry[0].id;
		$scope.$apply(function(){
			$scope.selectedItemList=[];
			$scope.serch="";
			$scope.filterItemData = [];
			//angular.copy($scope.itemsData1,$scope.itemsData);
			angular.copy($scope.itemsData);
			$scope.user.roles=[];
			$scope.userSelect.roles = [];
			 $scope.is_active=false;
			$scope.is_active_select=false;
			
			
		});

		if ($("#optnDate").prop('checked') == true) {
			$("#form_div_transaction_type").show();
			$('#form_div_item_id #itemname').val("");
			var dateFormat = get_date_format();
			var date = new Date();
			var y = date.getFullYear();
			var m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

		

			$timeout(function() {
				$scope.formData.enddate = dateForm(lastDay);
				$scope.formData.startdate = dateForm(firstDay);
			}, 1);
			$scope.formData.itemname = "";

			$("#date_div").show();
			$("#month_div").hide();
			// $("#department_id").prop('selectedIndex',0);
			$(".itmCat").prop('selectedIndex', 0);
			$("#trans_type").prop('selectedIndex', 0);
			$(".itmname").val('');
			$scope.formData.stock_item_id = '';
			$scope.formData.itemname = '';
			$('#form_div_item_id .acontainer input').val('');
			$('#form_div_item_id .acontainer itemname').val('');
		}

		var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
		$scope.formData.department_id = depList.id;
		$scope.formData.department_name1 = depList.name;
		$scope.formData.department_code1 = depList.code;
		$("#div_department_code1").find(".acontainer input").val(depList.code);
		$("#department_name1").val(depList.name);
		$("#form_div_startmonth").removeClass("has-error");
	});

	// $("#form_div_department").hide();

	$("#optnMonth").click(function() {
		$scope.formData.item_category_id = $scope.ItemCtgry[0].id;
		$scope.$apply(function(){
			$scope.selectedItemList=[];
			$scope.serch="";
			$scope.filterItemData = [];
			//angular.copy($scope.itemsData1,$scope.itemsData);
			angular.copy($scope.itemsData);
			$scope.user.roles=[];
			$scope.userSelect.roles = [];
			 $scope.is_active=false;
			$scope.is_active_select=false;
			
		});
		if ($("#optnMonth").prop('checked') == true) {
			$("#form_div_transaction_type").hide();
			$('#form_div_item_id #itemname').val("");
			$scope.formData.itemname = "";
			$('#itemname').val("");
			month = moment().format("MMM");
			date = moment().format('YYYY-MMM');
			$('#startmonth1').val(date);

			$("#month_div").show();
			$("#date_div").hide();
			$("#department_id").prop('selectedIndex', 0);
			$(".itmCat").prop('selectedIndex', 0);
			$("#trans_type").prop('selectedIndex', 0);
			$(".itemname").val('');
			$("#item_id").val('');
			$scope.formData.stock_item_id = '';
			$scope.formData.itemname = '';
			$('#form_div_item_id .acontainer input').val('');
		}
		var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
		$scope.formData.department_id = depList.id;
		$scope.formData.department_name1 = depList.name;
		$scope.formData.department_code1 = depList.code;
		//$("#div_department_code1").find(".acontainer input").val(depList.code);
		$("#department_name1").val(depList.name);
		$("#form_div_startdate_stockreg").removeClass("has-error");
        $("#form_div_startdate_error_stockreg").hide();
        $("#form_div_enddate_stockreg").removeClass("has-error");
        $("#form_div_enddate_error_stockreg").hide();
        $("#divErrormsg2").hide();
        
		
	});

	$("#divErrormsg").hide();
	$('#form_div_item_id .acontainer input').val('');

	/*$scope.sampleDepartment = [ {
		id : 0,
		name : "select"
	} ];*/

	var catid = [];
	$scope.ItemCtgry = [ {
		id : 0,
		name : "select"
	} ];
	$http({
		method : 'GET',
		async : false,
		url : "../itemcategory/json",
		data : {
			applicationId : 1
		}
	}).success(function(result) {
		catid = result.data;
		$scope.catidList = catid;
		var itemcategry = result.data;
		itemcategry.unshift({
			id : 0,
			name : "select"
		});
		$scope.ItemCtgry = itemcategry;

	});
	$scope.formData.item_category_id = $scope.ItemCtgry[0].id;

	$scope.elementcode = function(codevalue) {

		$scope.stkcode;

		for (var j = 0; j < $scope.stockListDtlList.length; j++) {
			if ($scope.stockListDtlList[j].id == codevalue) {
				$scope.stkcode = $scope.stockListDtlList[j].code;
			}
		}
		return $scope.stkcode;
	}

	$(document).on('keyup', '#form_div_item_id .acontainer input',
			function(event) {
				if (event.which != 9 && event.which != 13) {
					$scope.$apply(function() {

						$scope.formData.stock_item_id = '';
						$scope.formData.itemname = '';
						$("#itemname").val("");

					});

				}
			});

	$scope.filterStkitm = function() {
		$scope.stkfilterData = $scope.stockData;

		if ($scope.formData.item_category_id != "") {
			$scope.stkfilterData = [];

			for (var i = 0; i < $scope.stockData.length; i++) {
				if ($scope.stockData[i].item_category_id == $scope.formData.item_category_id) {
					$scope.stkfilterData.push($scope.stockData[i]);

				}
			}
		}
	}
	
	
	 $scope.filterItemData = [];
	 
		$scope.dataFetch=function()
		{
			 
			
			
	      $scope.searchValue();
			
		}
		
		$scope.searchValue=function()
		{
			$scope.filterItemData = [];
		if($scope.serch!="")
		  {
		    var searchData = eval("/.*" + $scope.serch + "/gi");

		    $.each($scope.itemsData , function (i, v) {
		        if (v.name.search(new RegExp(searchData)) != -1) {
		        	
		        		$scope.filterItemData.push(v);
		        
		        }
		    });

		  
		  }
		}
	
//checkboxlist
	    
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
			  $scope.user.roles = angular.copy( $scope.filterItemData );
			  };
			  $scope.uncheckAll = function() {
				  $scope.user.roles = [];
			  };
			  
				 $scope.checkAllSelect = function() {
					  $scope.userSelect.roles = angular.copy($scope.selectedItemList);
					  };
					  $scope.uncheckAllSelect = function() {
						  $scope.userSelect.roles = [];
					  };
		
		
		$scope.moveItem=function()
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
		
		$scope.moveItemSelect=function()
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
		
		
		$scope.selectedItemList=[];

		 
		 $scope.btnRight = function () {
		        //move selected.
			if($scope.user.roles.length!=0)
				{
				if($scope.is_active==true )
				{
				$scope.is_active=false;
				
				
				}
				$scope.is_active_select=false;
			  for(var i=0;i<$scope.user.roles.length;i++)
				  {
				  $scope.selectedItemList.push($scope.user.roles[i]);
				  }
			 
			  
			  //remove the ones that were moved from the source container.
			  
			  for(var i=0;i<$scope.user.roles.length;i++)
				  {
				  for(var j=0;j<$scope.itemsData.length;j++)
					  {
				 if( $scope.user.roles[i].id==$scope.itemsData[j].id)
					 {
					 $scope.itemsData.splice(j, 1);
					 }
				  }
				  }
			  
				}
			 $scope.searchValue();
			$scope.user.roles=[];
			
			
			  
		  }
		
		
		
		 $scope.btnLeft = function () {
		        //move selected.
			 if($scope.userSelect.roles.length!=0)
				 {
				 
				 if($scope.is_active_select==true )
					{
					$scope.is_active_select=false;
					 
					}
				 $scope.is_active=false;
				 
			 for(var i=0;i<$scope.userSelect.roles.length;i++)
			  {
			  $scope.itemsData.push($scope.userSelect.roles[i]);
			  }
			 //remove the ones that were moved from the source container.
			  
			  for(var i=0;i<$scope.userSelect.roles.length;i++)
				  {
				  for(var j=0;j<$scope.selectedItemList.length;j++)
					  {
				 if( $scope.userSelect.roles[i].id==$scope.selectedItemList[j].id)
					 {
					 $scope.selectedItemList.splice(j, 1);
					 }
				  }
				  }
			  
			
				 }
			 $scope.searchValue();
			 $scope.userSelect.roles=[];
				
		 }
	
		 
		 $scope.unselectstockItem=function(){
				if($scope.filterItemData.length!=$scope.user.roles.length)
					{
					$scope.is_active=false;
					}
				else
					{
					$scope.is_active=true;
					}
			}
			
			
		 $scope.unselectstockItemSelect=function(){
			 if($scope.selectedItemList.length!=$scope.userSelect.roles.length)
				{
				$scope.is_active_select=false;
				}
			else
				{
				$scope.is_active_select=true;
				}	
			}
			 
		 
	//checkbox list end    
	    
	

	$("#btn_finalize")
			.click(
					function() {
						
						
						$scope.stock_iem_Ids=[];
						if($scope.selectedItemList.length!=0)
						{
					for(var i=0;i<$scope.selectedItemList.length;i++ )
						{
						$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
						}
						}
						
						
						var Itemid = "";
						var dept;
						var transtype;
						var itmctgry = "";
						var itmids = "";

						if ($("#select_report").val() == "number:1") {
							
							
							var monthNo = "";
							var startdate = "";
							var enddate = "";
							var option = "";
							var firstday = "";
							var lastday = "";
							var startmonth = "";
							if ($("#optnDate").prop('checked') == true) {
								startdate = "";
								enddate = "";
								startdate = getMysqlFormat($scope.formData.startdate);
								enddate = getMysqlFormat($scope.formData.enddate);

								option = "1";
							}
							if ($("#optnMonth").prop('checked') == true) {
								startdate = "";
								enddate = "";
								startdate = $("#startmonth").val();
								startmonth = $("#startmonth1").val();

								var fields = startmonth.split('-');
								var year1 = fields[0];
								var monthName1 = fields[1];

								var date = new Date(), yr = date.getFullYear(), mnth = date
										.getMonth();

								startdate1 = new Date(Date.parse(startdate
										+ " 1," + yr));

								startdate = convertDate(startdate1);

								var monthNo = getMonthFromString(monthName1);

								startdate1 = new Date(Date.parse(monthNo
										+ " 1," + year1));

								startdate = convertDate(startdate1);
								option = "0";
							}

							var department_id = $scope.formData.department_id;
							var item_category_id = $scope.formData.item_category_id;

							var item_category_name = "";
							for (var i = 0; i < $scope.ItemCtgry.length; i++) {

								if ($scope.ItemCtgry[i].id == $scope.formData.item_category_id
										&& $scope.formData.item_category_id != 0) {
									item_category_name = $scope.ItemCtgry[i].name;
									break;
								}
							}

							var stock_item_id = 0;

							if ($scope.formData.department_id != undefined
									&& $scope.formData.department_id != "") {
								department_id = $scope.formData.department_id;
							} else {
								department_id = parseInt(strings['isDefDepartment']);
								var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
								$scope.formData.department_id = depList.id;
								$scope.formData.department_name1 = depList.name;
								$scope.formData.department_code1 = depList.code;
								$("#div_department_code1").find(
										".acontainer input").val(depList.code);
								$("#department_name1").val(depList.name);
							}

							if ($scope.formData.stock_item_id != undefined
									&& $scope.formData.stock_item_id != "") {
								stock_item_id = $scope.formData.stock_item_id;
							} else {
								stock_item_id = 0;
							}
							var trans_type = "";
							if ($scope.formData.trans_type != undefined && $scope.formData.trans_type != "") {
								trans_type = $scope.formData.trans_type;
							} else {
								trans_type = 0;
							}

							if ($("#optnMonth").prop('checked') == true) {

								if ($("#startmonth1").val() != ""
										&& $("#startmonth1").val() != undefined) {
									$("#form_div_startdate_stockreg")
											.removeClass("has-error");
									$("#form_div_startdate_error_stockreg")
											.hide();
									$("#form_div_enddate_stockreg")
											.removeClass("has-error");
									$("#form_div_enddate_error_stockreg")
											.hide();
									$("#form_div_startmonth").removeClass(
											"has-error");
									$("#form_div_startmonth_error").hide();
									$window
											.open(
													"../stockregisterreport/Stock Register?&option="
															+ option
															+ "&startdate="
															+ startdate
															+ "&enddate="
															+ monthNo
															+ "&item_category_id="
															+ item_category_id
															+ "&item_category_name="
															+ item_category_name
															+ "&department_id="
															+ department_id
															+ "&department_name="
															+ $scope.formData.department_name1
															+ "&stock_item_id="
															+ $scope.stock_iem_Ids
															+ "&trans_type="
															+ trans_type + "",
													'_blank');
								} else {
									$("#form_div_startmonth").addClass(
											"has-error");
									$("#form_div_startmonth_error").show();
								}

							}

							if ($("#optnDate").prop('checked') == true) {
								var strtDate2=process($scope.formData.startdate);
								var endDate2=process($scope.formData.enddate);

								if ($scope.formData.startdate != undefined
										&& $scope.formData.startdate != ""
										&& $scope.formData.enddate != ""
										&& $scope.formData.startdate != undefined
										&& strtDate2 < endDate2) {
									$("#form_div_startdate_stockreg")
											.removeClass("has-error");
									$("#form_div_startdate_error_stockreg")
											.hide();
									$("#form_div_enddate_stockreg")
											.removeClass("has-error");
									$("#form_div_enddate_error_stockreg")
											.hide();
									$("#form_div_startmonth").removeClass(
											"has-error");
									$("#form_div_startmonth_error").hide();
									$window
											.open(
													"../stockregisterreport/Stock Register?&option="
															+ option
															+ "&startdate="
															+ startdate
															+ "&enddate="
															+ enddate
															+ "&item_category_id="
															+ item_category_id
															+ "&item_category_name="
															+ item_category_name
															+ "&department_id="
															+ department_id
															+ "&department_name="
															+ $scope.formData.department_name1
															+ "&stock_item_id="
															+ $scope.stock_iem_Ids
															+ "&trans_type="
															+ trans_type + "",
													'_blank');

								} else {
									if ($scope.formData.startdate == undefined
											|| $scope.formData.startdate == "") {

										$("#form_div_startdate_stockreg")
												.addClass("has-error");
										$("#form_div_startdate_error_stockreg")
												.show();
									}
									if ($scope.formData.enddate == undefined
											|| $scope.formData.enddate == "") {
										$("#form_div_enddate_stockreg")
												.addClass("has-error");
										$("#form_div_enddate_error_stockreg")
												.show();
									}
								}

							}

						}

					});
	
	
	
	
	$("#excelView").click(function() {
		$scope.stock_iem_Ids=[];
		if($scope.selectedItemList.length!=0)
		{
			for(var i=0;i<$scope.selectedItemList.length;i++ )
			{
				$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
			}
		}
		var Itemid = "";
		var dept;
		var transtype;
		var itmctgry = "";
		var itmids = "";
		if ($("#select_report").val() == "number:1") {
			var monthNo = "";
			var startdate = "";
			var enddate = "";
			var option = "";
			var firstday = "";
			var lastday = "";
			var startmonth = "";
			if ($("#optnDate").prop('checked') == true) {
				startdate = "";
				enddate = "";
				startdate = getMysqlFormat($scope.formData.startdate);
				enddate = getMysqlFormat($scope.formData.enddate);

				option = "1";
			}
			if ($("#optnMonth").prop('checked') == true) {
				startdate = "";
				enddate = "";
				startdate = $("#startmonth").val();
				startmonth = $("#startmonth1").val();

				var fields = startmonth.split('-');
				var year1 = fields[0];
				var monthName1 = fields[1];

				var date = new Date(), yr = date.getFullYear(), mnth = date
				.getMonth();
				startdate1 = new Date(Date.parse(startdate
						+ " 1," + yr));

				startdate = convertDate(startdate1);

				var monthNo = getMonthFromString(monthName1);

				startdate1 = new Date(Date.parse(monthNo
						+ " 1," + year1));

				startdate = convertDate(startdate1);
				option = "0";
			}

			var department_id = $scope.formData.department_id;
			var item_category_id = $scope.formData.item_category_id;

			var item_category_name = "";
			for (var i = 0; i < $scope.ItemCtgry.length; i++) {

				if ($scope.ItemCtgry[i].id == $scope.formData.item_category_id
				&& $scope.formData.item_category_id != 0) {
					item_category_name = $scope.ItemCtgry[i].name;
					break;
				}
			}

			var stock_item_id = 0;

			if ($scope.formData.department_id != undefined
					&& $scope.formData.department_id != "") {
				department_id = $scope.formData.department_id;
			} else {
				department_id = parseInt(strings['isDefDepartment']);
				var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
				$scope.formData.department_id = depList.id;
				$scope.formData.department_name1 = depList.name;
				$scope.formData.department_code1 = depList.code;
				$("#div_department_code1").find(
				".acontainer input").val(depList.code);
				$("#department_name1").val(depList.name);
			}

			if ($scope.formData.stock_item_id != undefined
					&& $scope.formData.stock_item_id != "") {
				stock_item_id = $scope.formData.stock_item_id;
			} else {
				stock_item_id = 0;
		}
			var trans_type = "";
			if ($scope.formData.trans_type != undefined && $scope.formData.trans_type != "") {
				trans_type = $scope.formData.trans_type;
			} else {
				trans_type = 0;
			}

			if ($("#optnMonth").prop('checked') == true) {

				if ($("#startmonth1").val() != "" && $("#startmonth1").val() != undefined) {
					$("#form_div_startdate_stockreg").removeClass("has-error");
					$("#form_div_startdate_error_stockreg").hide();
					$("#form_div_enddate_stockreg").removeClass("has-error");
					$("#form_div_enddate_error_stockreg").hide();
					$("#form_div_startmonth").removeClass("has-error");
					$("#form_div_startmonth_error").hide();
					var link=document.createElement('a');
					link.href="../stockregisterreport/Stock Register Excel?&option="+ option+ "&startdate="
					+ startdate+ "&enddate="+ monthNo+ "&item_category_id="+ item_category_id+ "&item_category_name="
					+ item_category_name+ "&department_id="+ department_id+ "&department_name="+ $scope.formData.department_name1
					+ "&stock_item_id="+ $scope.stock_iem_Ids+ "&trans_type="+ trans_type + "";

					link.click();	



				} else {
					$("#form_div_startmonth").addClass(
					"has-error");
					$("#form_div_startmonth_error").show();
				}

			}

			if ($("#optnDate").prop('checked') == true) {
				var strtDate2=process($scope.formData.startdate);
				var endDate2=process($scope.formData.enddate);

				if ($scope.formData.startdate != undefined && $scope.formData.startdate != "" && $scope.formData.enddate != ""
					&& $scope.formData.startdate != undefined && strtDate2 < endDate2) {
					$("#form_div_startdate_stockreg").removeClass("has-error");
					$("#form_div_startdate_error_stockreg").hide();
					$("#form_div_enddate_stockreg").removeClass("has-error");
					$("#form_div_enddate_error_stockreg").hide();
					$("#form_div_startmonth").removeClass("has-error");
					$("#form_div_startmonth_error").hide();
					var link=document.createElement('a');
					link.href="../stockregisterreport/Stock Register Excel?&option="+ option+ "&startdate="+ startdate
					+ "&enddate="+ enddate+ "&item_category_id="+ item_category_id+ "&item_category_name="
					+ item_category_name+ "&department_id="+ department_id+ "&department_name="+ $scope.formData.department_name1
					+ "&stock_item_id="+ $scope.stock_iem_Ids+ "&trans_type="+ trans_type + "";

					link.click();	

				} else {
					if ($scope.formData.startdate == undefined
							|| $scope.formData.startdate == "") {

						$("#form_div_startdate_stockreg")
						.addClass("has-error");
						$("#form_div_startdate_error_stockreg")
						.show();
					}
					if ($scope.formData.enddate == undefined
							|| $scope.formData.enddate == "") {
						$("#form_div_enddate_stockreg")
						.addClass("has-error");
						$("#form_div_enddate_error_stockreg")
						.show();
					}
				}

			}

		}

	});
	

	

	$scope.tableDatevalidation = function() {
		if ($scope.formData.enddate != "") {
			var strtDate2=process($scope.formData.startdate);
			var endDate2=process($scope.formData.enddate);
			if (strtDate2 > endDate2) {

				$("#divErrormsg2")
						.text('Start date must be less than Enddate!');
				$("#divErrormsg2").show();
			} else
			if(strtDate2-endDate2 == 0)
				{
				$("#divErrormsg2")
				.text('Start date and End date cannot be same!');
				$("#divErrormsg2").show();
				}
			else
				{
				
				$("#divErrormsg2").hide();
				}
		}
	}

	function convertDate(inputFormat) {

		function pad(s) {
			return (s < 10) ? '0' + s : s;
		}
		var d = new Date(inputFormat);

		return [ d.getFullYear(), pad(d.getMonth() + 1), pad(d.getDate()) ]
				.join('-');

	}

	function getMonthFromString(mon) {
		return new Date(Date.parse(mon + " 1, 2012")).getMonth() + 1
	}

	$scope.getFirstDateInMonth = function(str) {
		var date = new Date(), yr = date.getFullYear(), mnth = date.getMonth();
		var strtNum = new Date(Date.parse(str + " 1," + yr)).getMonth() + 1;

		var strtDt = new Date(Date.parse(str + " 1," + yr));
		var formatedFirstDay = dateForm(strtDt);
		return formatedFirstDay;
	}

	$scope.getLastDateInMonth = function(str) {
		var date = new Date(), yr = date.getFullYear();
		var monthNum = new Date(Date.parse(str + " 1," + yr)).getMonth();
		var monthStart = new Date(yr, monthNum, 1);
		var monthEnd = new Date(year, monthNum + 1, 1);
		var monthLength = (monthEnd - monthStart) / (1000 * 60 * 60 * 24);

		var lastDay = new Date(Date.parse(str + " " + monthLength + "," + yr));
		var formatedLastDay = dateForm(lastDay);
		// alert(formatedLastDay);
		return formatedLastDay;
	}

	function validation() {
		var flg = true;

		if ($scope.formData.startdate == undefined
				|| $scope.formData.startdate == ""
				|| $scope.formData.startdate == null) {
			$("#form_div_startdate").addClass("has-error");
			$("#form_div_startdate_error").show();
			flg = false;
		} else {
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
		}

		if ($scope.formData.enddate == undefined
				|| $scope.formData.enddate == ""
				|| $scope.formData.enddate == null) {
			$("#form_div_enddate").addClass("has-error");
			$("#form_div_enddate_error").show();
			flg = false;
		} else {
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
		}

		return flg;
	}

	$(function() {
		var bindDatePicker = function() {
			$(".date").datetimepicker({
				format : 'YYYY-MMM',
				icons : {
					time : "fa fa-clock-o",
					date : "fa fa-calendar",

				},
				viewMode : "months",

			})
		}

		bindDatePicker();
	});

	$(document).on('keyup', '#div_department_code1 input', function(e) {

		if (e.which != 9 && e.which != 13) {
			$scope.$apply(function() {
				$scope.formData.department_id = "";

				$scope.formData.department_code1 = "";
				$scope.formData.department_name1 = "";

			});
		}
	});

	$scope.formData.department_id = strings['isDefDepartment'];
	$scope.formData.department_code1 = strings['isDefDepartmentcode'];
	$scope.formData.department_name1 = strings['isDefDepartmentname'];

	$("#div_department_code1").find(".acontainer input").val(
			$scope.formData.department_code1);
	$("#department_code1").val($scope.formData.department_code1);

	$scope.itemsData = [];
	 
	
	//commneted by gana on 20-07-2019
/*	 $http({
		 url : '../itemmaster/json',
			method : "GET",
		}).then(function(itemresponse) {
			
			$scope.itemsData = itemresponse.data.data;
		
		}, function(itemresponse) { // optional
	
		});
	*/
	 /*$scope.itemsData=[];
	 $scope.itemsData1=[];*/
	$http({
		url : '../stockin/formJsonData',
		method : "GET",
	}).then(function(response) {
		$scope.department_data = response.data.depData;

		$scope.itemsData1 = response.data.stockItmData;
		angular.copy($scope.itemsData1, $scope.itemsData);
		/*$scope.stockData = $scope.itemsData1;
		$scope.stkfilterData = $scope.stockData;*/
		var department1 = $scope.department_data;
		/*department1.unshift({
			id : 0,
			name : "select"
		});*/
		$scope.sampleDepartment = department1;
	}, function(response) { // optional
	});

	function fun_get_dep_name(id) {
		var depList = [];
		for (var i = 0; i < $scope.department_data.length; i++) {
			if ($scope.department_data[i].id == id) {
				depList = $scope.department_data[i];
			}
		}

		return depList;
	}

	var DepartmentData1 = $("#department_code1")
			.tautocomplete(
					{
						columns : [ 'id', 'code', 'name' ],
						hide : [ false, true, true ],
						placeholder : "search..",
						highlight : "hightlight-classname",
						norecord : "No Records Found",
						delay : 10,
						onchange : function() {
							var selected_department_data = DepartmentData1
									.all();
							$scope
									.$apply(function() {
										$scope.formData.department_id = selected_department_data.id;
										$scope.formData.department_code1 = selected_department_data.code;
										$scope.formData.department_name1 = selected_department_data.name;
									});
						},
						data : function() {
							var data = $scope.department_data;
							var filterData = [];
							var searchData = eval("/^"
									+ DepartmentData1.searchdata() + "/gi");
							$
									.each(data,
											function(i, v) {
												if (v.name.search(new RegExp(
														searchData)) != -1) {
													filterData.push(v);
												}
											});
							return filterData;
						}
					});

}

myApp.directive('tableAutocomplete', [ function() {
	return {

		controller : function($scope, $http) {

			return $scope;
		},
		link : function($scope, elem, attrs, controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns : [ 'id', 'code', 'name' ],
				hide : [ false, true, true ],
				placeholder : "Search Code",
				highlight : "hightlight-classname",
				norecord : "No Records Found",
				delay : 10,
				onchange : function() {
					var selected_item_data = items.all();

					$scope.$apply(function() {

						$scope.formData.stock_item_id = selected_item_data.id;
						$scope.formData.itemname = selected_item_data.name;

					});
				},
				data : function() {

					var strl_scope = controller;
					var data;

					data = strl_scope.stockData;

					var filterData = [];

					var searchData = eval("/^" + items.searchdata() + "/gi");

					$.each(data, function(i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							if (v.is_active == 1) {
								filterData.push(v);
							}
						}
					});

					return filterData;
				}

			});

		}
	};
} ]);

myApp.directive('daterangePicker1', [ function() {
	return {
		controller : function($scope, $http) {

			return $scope;
		},
		link : function(scope, elem, attrs, controller) {
			var dateFormat = get_date_format();
			var date = new Date(), y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			controller.formData.enddate = dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			controller.formData.startdate = dateForm(firstDay);

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
} ]);

myApp.directive('daterangePicker2', [ function() {
	return {
		controller1 : function($scope, $http) {

			return $scope;
		},
		link : function(scope, elem, attrs, controller1) {
			var dateFormat = get_date_format();
			$(elem).datetimepicker({
				"format" : 'MMM',
				viewMode : "months",
			}, function(start, end, label) {
			});

		}
	};
} ]);

angular.bootstrap(document.getElementById("app_stock-register"),
		[ 'stock-register_report_app' ]);