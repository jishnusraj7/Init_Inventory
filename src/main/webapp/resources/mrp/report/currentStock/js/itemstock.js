var myApp = angular.module('stock_report_app', [ 'ngMaterial' , 'ngMessages' , 'common_app','checklist-model' ]);

myApp.controller('stock_report_ctrlr', stock_report_ctrlr);

function stock_report_ctrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ,$filter) {
	$scope.formData = {};
	$scope.getValue = {};
	$scope.categoryList = [];
	$scope.itemsData = [];
	$scope.stockId = [];
	$scope.hideit=false;
	$scope.categoryName="";
	$scope.categoryData=[];
	/*set_sub_menu("#report");						
	setMenuSelected("#currentstock_left_menu");	*/
	
	var vm = this;
	
	$("#msg").hide();
	
	 $scope.itemsData = [];
	 //$scope.itemsData1=[];
	/* 
	 $http({
		 url : '../itemmaster/json',
			method : "GET",
		}).then(function(itemresponse) {
			//$scope.itemsData1 = itemresponse.data.data;
			$scope.itemsData = itemresponse.data.data;
			//angular.copy($scope.itemsData1, $scope.itemsData);
		}, function(itemresponse) { // optional
	
		});*/
	 
	/*	$http({
			url : '../stockin/formJsonData',
			method : "GET",
		}).then(function(itemresponse) {

			$scope.itemsData = itemresponse.data.stockItmData;
			console.log($scope.itemsData);
		}, function(itemresponse) { // optional

		});*/
		
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
	    var searchData = eval("/^" + $scope.serch + "/gi");

	    $.each($scope.itemsData , function (i, v) {
	        if (v.name.search(new RegExp(searchData)) != -1) {
	        	
	        		$scope.filterItemData.push(v);
	        
	        }
	    });

	  
	  }
	}
	
	$scope.sampleItemCtgry =[{id : 0 ,name : "select" }];
	    $http({
         method: 'GET',
         url : "../itemcategory/json",
         data: { applicationId: 1 }
     }).success(function (result) {
    		for(var i=0; i<result.data.length;i++){
      	    	$scope.sampleItemCtgry.push(result.data[i]);
      	    	
      	    }
     	
     	
 });
	    
	$scope.department=[];    
	    $http({
			url : '../stockin/formJsonData',
			method : "GET",
		}).then(function(response) {
			$scope.department_data = response.data.depData;	
			$scope.itemsData = response.data.stockItmData;
			$scope.categoryData=response.data.itemCategoryData;
			angular.copy($scope.department_data, $scope.department);
		
				var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
				$scope.formData.department_id=depList.id;
				$scope.formData.department_name=depList.name;
				$scope.formData.department_code=depList.code;
				$("#form_div_department_code").find(".acontainer input").val(depList.code);

		}, function(response) { // optional
			});
	    
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
	    
	
		 
		 
//checkList for Department
		 $scope.is_active_dep=false;
			$scope.is_active_select_dept=false;
			$scope.serchDep="";
			 $scope.userDep = {
					    roles: []
					  };
			 
			 $scope.userSelectDep={
					 roles:[]
			 };
			
			 $scope.checkAllDep = function() {
				  $scope.userDep.roles = angular.copy($scope.department);
				  };
				  $scope.uncheckAllDep = function() {
					  $scope.userDep.roles = [];
				  };
				  
					 $scope.checkAllSelectDep = function() {
						  $scope.userSelectDep.roles = angular.copy($scope.selectedDepList);
						  };
						  $scope.uncheckAllSelectDep = function() {
							  $scope.userSelectDep.roles = [];
						  };
			
			
			$scope.moveDep=function()
			{
				
				if($scope.is_active_dep==true)
					{
					
					$scope.checkAllDep();
								}
				else
					{
					 $scope.uncheckAllDep();
					}
			}
			
			$scope.moveDepSelect=function()
			{
				
				if($scope.is_active_select_dept==true)
					{
					
					$scope.checkAllSelectDep();
								}
				else
					{
					 $scope.uncheckAllSelectDep();
					}
			}
			$scope.selectedDep = []; 
			
			
			$scope.selectedDepList=[];

			 
			 $scope.btnRightDept = function () {
			        //move selected.
				if($scope.userDep.roles.length!=0)
					{
					if($scope.is_active_dep==true )
					{
					$scope.is_active_dep=false;
					}
				
				  for(var i=0;i<$scope.userDep.roles.length;i++)
					  {
					  $scope.selectedDepList.push($scope.userDep.roles[i]);
					  }
				 
				  
				  //remove the ones that were moved from the source container.
				  
				  for(var i=0;i<$scope.userDep.roles.length;i++)
					  {
					  for(var j=0;j<$scope.department.length;j++)
						  {
					 if( $scope.userDep.roles[i].id==$scope.department[j].id)
						 {
						 $scope.department.splice(j, 1);
						 }
					  }
					  }
				  
					}
				$scope.userDep.roles=[];
				$scope.is_active_select_dept=false;
				
				  
			  }
			
			
			
			 $scope.btnLeftDept = function () {
			        //move selected.
				 if($scope.userSelectDep.roles.length!=0)
					 {
					 
					 if($scope.is_active_select_dept==true )
						{
						$scope.is_active_select_dept=false;
						}
				
					 
				 for(var i=0;i<$scope.userSelectDep.roles.length;i++)
				  {
				  $scope.department.push($scope.userSelectDep.roles[i]);
				  }
				 //remove the ones that were moved from the source container.
				  
				  for(var i=0;i<$scope.userSelectDep.roles.length;i++)
					  {
					  for(var j=0;j<$scope.selectedDepList.length;j++)
						  {
					 if( $scope.userSelectDep.roles[i].id==$scope.selectedDepList[j].id)
						 {
						 $scope.selectedDepList.splice(j, 1);
						 }
					  }
					  }
				  
				
					 }
				 $scope.userSelectDep.roles=[];
				 $scope.is_active_dep=false;
			 }
			
		 
			 $scope.getFilteredDept=function(shops,serchDep)
				{
					var deleteId;
					$scope.is_active_dep=false;
					$scope.userDep.roles=[];
					
					$scope.serchDataDep= $filter('filter')($scope.department_data, serchDep);
					
					
					$scope.department=$scope.serchDataDep;
					filterSelectedDept();
					
					if(serchDep=="")
						{
						
						angular.copy($scope.department_data, $scope.department);
							
						filterSelectedDept();
						}
				};
				function filterSelectedDept()
				{
				for(var i=0;i<$scope.selectedDepList.length;i++)
				{
				deleteId=$scope.selectedDepList[i].id;
				$scope.department=$scope.department.filter(function(obj)
						{
					     return obj.id != deleteId;
						});
				}}
		 
		 
		 
		 
		 
		 
//CheckList dep end		 
	    
	    
	   
	    $(document).on('keyup','#form_div_department_code input',function(e){
		
			if(e.which != 9 && e.which != 13){
				$scope.$apply(function(){
					$scope.formData.department_id =  "";
					$scope.formData.department_code =  "";
					$scope.formData.department_name = "";
					$("#department_code").val("");

				});
			}
		});
		


		
		
$scope.formData.item_category_id = $scope.sampleItemCtgry[0].id;   	 
	
	$("#optradio0").prop('checked',false);
	$("#optradio1").prop('checked',false);
	$("#optradio2").prop('checked',true);
	//$("#form_div_item_category_id").hide();
	$("#form_div_bom_item_id").show();
	$('#form_div_bom_item_id #itemid,#itemname').val('');
 	$scope.formData.itemid="";
	$scope.formData.itemname="";
	$("#form_div_bom_item_id .acontainer input").val('');
	$scope.formData.item_category_id = $scope.sampleItemCtgry[0].id;   	 
	
	$("#select_report").change(function(){
		if($("#select_report").val() == "number:0"){
			$("#msg").hide();
			$("#btnTally").hide();
			$("#optradio0").prop('checked',false);
			$("#optradio1").prop('checked',false);
			$("#optradio2").prop('checked',true);
			$scope.$apply(function(){
			$scope.hideit=false;
			});
			$("#form_div_item_category_id").show();
			$("#form_div_bom_item_id").show();
			$('#form_div_bom_item_id #itemid,#itemname').val('');
		 	$scope.formData.itemid="";
			$scope.formData.itemname="";
			$("#form_div_bom_item_id .acontainer input").val('');
			$scope.formData.item_category_id = $scope.sampleItemCtgry[0].id;
			
			$scope.formData.department_id = parseInt(strings['isDefDepartment']);
			var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
			$scope.formData.department_id=depList.id;
			$scope.formData.department_name=depList.name;
			$scope.formData.department_code=depList.code;
			$("#department_name").val(depList.name);	
			
			$("#form_div_department_code").find(".acontainer input").val(depList.code);
			
			$scope.selectedItemList=[];
			
		}
	});
	
	$("#optradio0").click(function(){
		
		$("#msg").hide();
		if($("#optradio0").attr('checked',true)){
			
			$("#form_div_item_category_id").hide();
			$("#form_div_stock_item").hide();
			$("#form_div_department").hide();
			
		}/*else{
			$("#form_div_item_category_id").show();
			$("#form_div_stock_item").show();
			$("#form_div_department").show();
		}*/
	});
	
	$("#optradio1").click(function(){
		
		$("#msg").hide();
		if($("#optradio1").attr('checked',true)){
			
			$scope.$apply(function(){
				$scope.selectedItemList=[];
				$scope.serch="";
				$scope.filterItemData = [];
				angular.copy($scope.itemsData);
				$scope.user.roles=[];
				$scope.userSelect.roles = [];
				 $scope.is_active=false;
				$scope.is_active_select=false;
				$scope.hideit=true;
				
				$scope.selectedDepList=[];
				$scope.serchDep="";
				//$scope.filterItemData = [];
				angular.copy($scope.department_data,$scope.department);
				$scope.userDep.roles=[];
				$scope.userSelectDep.roles = [];
				 $scope.is_active_dep=false;
				$scope.is_active_select_dept=false;
				

			});
			
			
			 
			
			$("#form_div_item_category_id").hide();
			$("#form_div_stock_item").show();
			$("#form_div_department").show();
			$("#form_div_bom_item_id").show();
			$('#form_div_bom_item_id #itemid,#itemname').val('');
			$scope.formData.itemid="";
			$scope.formData.itemname="";
			$("#form_div_bom_item_id .acontainer input").val('');
			$scope.formData.item_category_id = $scope.sampleItemCtgry[0].id;   	
			
			var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
			$scope.formData.department_id=depList.id;
			$scope.formData.department_name=depList.name;
			$scope.formData.department_code=depList.code;
			$("#form_div_department_code").find(".acontainer input").val(depList.code);
			$("#department_name").val(depList.name);
		}
	});
	
	$("#optradio2").click(function(){
		$scope.$apply(function(){
			//$scope.selectedItemList=[];
			//$scope.serch="";
			//$scope.filterItemData = [];
			// angular.copy($scope.sampleItemCtgry,$scope.itemsData);
			 //$scope.is_active=false;
			//$scope.is_active_select=false;
			$scope.hideit=false;
			
			
			$scope.selectedDepList=[];
			$scope.serchDep="";
			//$scope.filterItemData = [];
			angular.copy($scope.department_data,$scope.department);
			$scope.userDep.roles=[];
			$scope.userSelectDep.roles = [];
			 $scope.is_active_dep=false;
			$scope.is_active_select_dept=false;
			



		});
		$("#msg").hide();
		if($("#optradio2").attr('checked',true)){
			
			$("#form_div_item_category_id").show();
			$("#form_div_stock_item").hide();
			$("#form_div_department").show();
			$("#form_div_bom_item_id").hide();
			$('#form_div_bom_item_id #itemid,#itemname').val('');
			$scope.formData.item_category_id = $scope.sampleItemCtgry[0].id;   	
			$scope.formData.itemid="";
			$scope.formData.itemname="";
			$("#form_div_bom_item_id .acontainer input").val('');
			  $("#item_category_id").prop('selectedIndex',0);
			 
			
		}
		
		var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
		$scope.formData.department_id=depList.id;
		$scope.formData.department_name=depList.name;
		$scope.formData.department_code=depList.code;
		$("#form_div_department_code").find(".acontainer input").val(depList.code);
		$("#department_name").val(depList.name);	
		
		
	});
	
	var catgoryids = [];
/*	
	$http({
		 url : '../itemmaster/json',
			method : "GET",
		}).then(function(itemresponse) {
			$scope.itemsData = itemresponse.data.data;
			
		}, function(itemresponse) { // optional
	
		});*/
	 
	
	
	$scope.setDatas=function()
	{
		$scope.departmentIds=[];
		if($scope.selectedDepList.length!=0)
			{
		for(var i=0;i<$scope.selectedDepList.length;i++ )
			{
			$scope.departmentIds.push($scope.selectedDepList[i].id);
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
	}
	
	
$("#btn_finalize").click(function(){
	
	$scope.setDatas();
	
	
	
		//$scope.categoryName="";
		$("#msg").hide();
		var stk;
		
		
		
		if($("#select_report").val() == "number:0"){
			
			
				  $("#msg").hide();
			  
				if($("#optradio0").prop('checked') == true){
						
					 $window.open("../itemstock/Stock Report?itemstockReportType=true", '_blank');
						
				}	  
				  
			if($("#optradio1").prop('checked') == true){
			
			
			/*if($scope.stock_iem_Ids.length != 0 ){*/
				$scope.formData.item_category_id=0;
				$window.open("../itemstock/Stock Report?department_name="+$scope.formData.department_name+"&department_id="+$scope.departmentIds+"&itemstockReportType=true&item_category_id="+$scope.formData.item_category_id+"&stock_item_id="+$scope.stock_iem_Ids+"", '_blank');
			//}
			
			
			/*else{
				setErr_AlertMessage('setErr_AlertMessage',"Please Select Atleast an Item.");
			  }*/
			}
			
			if($("#optradio2").prop('checked') == true){

			
			if($scope.formData.item_category_id != undefined && $scope.formData.item_category_id != 0){	
				if($scope.formData.itemid == undefined || $scope.formData.itemid == ""){
				  $scope.stockId=[];
				  for(var i=0; i<$scope.categoryData.length; i++){
					 if($scope.categoryData[i].id == $scope.formData.item_category_id){
						  $scope.categoryName=$scope.categoryData[i].name;
						  $scope.stockId.push($scope.categoryName[i].id);
						  stk=$scope.stockId;
						  
						  break;
				
					  }
					
				    }
				
				  if($scope.stockId !=undefined || $scope.stockId != null){
					 
						$window.open("../itemstock/Stock Report?1&department_id="+$scope.departmentIds+"&itemstockReportType=false&item_category_id="+$scope.formData.item_category_id+"&categoryName="+$scope.categoryName+"&department_name="+$scope.formData.department_name+"", '_blank');

				  }
				  else{
					  $("#msg").text('Data Is Insufficient To Show The Report ');
					  $("#msg").show();
				  }
				  
				}
			}else{
				  $("#msg").text('Please select an item category ');
				  $("#msg").show();
			  }
			}
			
		}
		
	});
	
$("#excelView").click(function(){
	
	if($("#select_report").val() == "number:0"){
		
		$scope.setDatas();
		$scope.categoryName="";
		$("#msg").hide();
		var stk;
		 $("#msg").hide();

			if($("#optradio0").prop('checked') == true){
				var link=document.createElement('a');	
				link.href="../itemstock/Stock Report Excel?itemstockReportType=true";
				link.click();	
			}	 
		if($("#optradio1").prop('checked') == true){
			/*if($scope.stock_iem_Ids.length != 0 )
			{*/
				$scope.formData.item_category_id=0;
				
				//$window.open("../itemstock/Stock Report Excel?department_name="+$scope.formData.department_name+"&department_id="+$scope.departmentIds+"&itemstockReportType=true&item_category_id="+$scope.formData.item_category_id+"&stock_item_id="+$scope.stock_iem_Ids+"", '_blank');
				 var link=document.createElement('a');
		    	    link.href="../itemstock/Stock Report Excel?department_name="+$scope.formData.department_name+"&department_id="+$scope.departmentIds+"&itemstockReportType=true&item_category_id="+$scope.formData.item_category_id+"&stock_item_id="+$scope.stock_iem_Ids+"";

		    	    link.click();
			
		/*	}
			else{
				setErr_AlertMessage('setErr_AlertMessage',"Please Select Atleast an Item.");
			  }*/
			}
		
      if($("#optradio2").prop('checked') == true){
			if($scope.formData.item_category_id != undefined && $scope.formData.item_category_id != 0){	
				if($scope.formData.itemid == undefined || $scope.formData.itemid == ""){
				  $scope.stockId=[];
				  for(var i=0; i<$scope.categoryData.length; i++){
					  if($scope.categoryData[i].id == $scope.formData.item_category_id){
						  $scope.categoryName=$scope.categoryData[i].name;
						  $scope.stockId.push($scope.categoryName[i].id);
						  stk=$scope.stockId;
						  break;
					  }
				    }
				
				  if($scope.stockId !=undefined){
					//$window.open("../itemstock/Stock Report Excel?department_id="+$scope.departmentIds+"&itemstockReportType=false&item_category_id="+$scope.formData.item_category_id+"&categoryName="+$scope.categoryName+"&department_name="+$scope.formData.department_name+"", '_blank');
				 
					  var link=document.createElement('a');
			    	    link.href="../itemstock/Stock Report Excel?department_id="+$scope.departmentIds+"&itemstockReportType=false&item_category_id="+$scope.formData.item_category_id+"&categoryName="+$scope.categoryName+"&department_name="+$scope.formData.department_name+"";

			    	    link.click();
				  
				  
				  }else{
					  $("#msg").text('Data Is Insufficient To Show The Report ');
					  $("#msg").show();
				  }
				}
			}else{
				  $("#msg").text('Please select an item category ');
				  $("#msg").show();
			  }}
		
		}
		
		
	
	
	
});
	
	

	 	             	    
	 	   $scope.elementcode=function(codevalue){ 
	 	      $scope.stkcode;
	 	           	    	
	 	           for(var j=0; j < $scope.stockListDtlList.length; j++){
	 	           	if($scope.stockListDtlList[j].id==codevalue){
	 	           					$scope.stkcode = $scope.stockListDtlList[j].code;
	 	           			} 
	 	           		}
	 	           	    return 	$scope.stkcode;
	 	           	}	 	             	    

	 $(document).on('keyup','#form_div_bom_item_id .acontainer input', function(e) {
		 if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
	 	$('#form_div_bom_item_id #itemid,#itemname,#Item').val('');
	 	$scope.formData.itemid="";
		$scope.formData.itemname="";}
		
	 });
	 
  
	 
	  $scope.department_data = [];

	
	  
	  
	
	  
	  
	  
		function fun_get_dep_name(id){
			var depList =[];
			for(var i=0;i < $scope.department_data.length;i++){
				if($scope.department_data[i].id == id){
					depList = $scope.department_data[i];
				}
			}

			return depList;
		}
	 
	/*
	 //autocompelete source department 
		var DepartmentData = $("#department_code").tautocomplete({
			columns: ['id','code','name'],
			hide: [false,true,true,true],
			placeholder: "search..",
			highlight: "hightlight-classname",
			norecord: "No Records Found",
			delay : 10,
			onchange: function () {
				var selected_department_data =  DepartmentData.all();
				$scope.$apply(function(){
					$scope.formData.department_id =  selected_department_data.id;
					$scope.formData.department_code =  selected_department_data.code;
					$scope.formData.department_name =  selected_department_data.name;
				});},
				data: function () {
					var data = $scope.department_data;
					var filterData = [];
					var searchData = eval("/^" + DepartmentData.searchdata() + "/gi");
					$.each(data, function (i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);}});
					return filterData;}});

*/	 
	 
}
$("#err_alertMessageId").show();
function setErr_AlertMessage(event,msg){
	$("#err_alertMessageId").show();
	$("#err_alertMessageId").html( msg );
	 //$scope.err_alertMessageStatus=false; 
    //$scope.err_alertMeaasge    =  msg;
	setTimeout(function(){ $("#err_alertMessageId").hide(); }, 1500);
   // $timeout(function () {$("#err_alertMessageId").hide(); }, 1500); 		 	
}



myApp.directive('tableAutocomplete', [function() {
	  return {
		 
	    controller: function ($scope,$http) {
	  
		    
	    	    return $scope;
	        },
	    link: function($scope, elem, attrs ,controller) {
	    	var strl_scope = controller;
	    	var items = $(elem).tautocomplete({
	    		
	    		columns: ['id' , 'code', 'name'],
	    		hide: [false,true,true],
	    		placeholder: "Search Code",
	    		highlight: "hightlight-classname",
	    		norecord: "No Records Found",
	    		delay : 10,
	    		onchange: function () {
	    			var selected_item_data =  items.all();
	    			
	    			$scope.$apply(function(){
	    				
	    					$scope.formData.itemid=selected_item_data.id;
	    					$scope.formData.itemname=selected_item_data.name;
	    			});
	    		},
	    		data: function () {
	    			
	    			var strl_scope = controller;
	    			var data;
	    			
	    				data = strl_scope.itemsData;
	    			
	    		    var filterData = [];

	    		    var searchData = eval("/^" + items.searchdata() + "/gi");

	    		    $.each(data, function (i, v) {
	    		        if (v.name.search(new RegExp(searchData)) != -1) {
	    		        	if(v.is_active==1){
								filterData.push(v);}
	    		        
	    		        }
	    		    });

	    		    return filterData;
	    		}
	    		
	         });
	    	
	        }
	   };
}]);

$("#optradio1").prop('checked',false);
$("#optradio2").prop('checked',true);
$('#form_div_bom_item_id #itemid,#itemname').val('');
$("#form_div_bom_item_id .acontainer input").val('');

angular.bootstrap(document.getElementById("app3"), ['stock_report_app']);
