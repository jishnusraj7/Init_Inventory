//Controller for Table and Form 
mrpApp.controller('item_master', item_master);


function item_master($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder, $q, $window , MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES) {

	/* ##  extend DatatableController ##  */
	$controller('DatatableController', {$scope: $scope});

	/*set_sub_menu("#settings");						
	setMenuSelected("#item_master_left_menu");*/			//active leftmenu
	manageButtons("add");

	$scope.selectedIndex = 0;
//	$scope.imagehide=true;
	$scope.isLiteVersion=false;
	if(isLiteVersion == true)
		{
	
		$scope.isLiteVersion=true;
		$scope.selectedIndex = 1;
		}
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {

			$scope.formData.code = response;});}

	var disable = 0;
	$scope.taxhide=false;
	$scope.ppHint=0;
	
	
	
	$scope.modalShow=false;
	$scope.dateTime="";
    $scope.sysSaleFlag=true;
	$scope.formData1={};
	$scope.formData = {};
	$scope.Quetable={};
	$scope.categoryList = [];
	$scope.kitchen=[];
	$scope.superClassList=[];
	$scope.itmID=0;
	$scope.supDataList = [];
	$scope.taxList=[];
	$scope.stkdata = [];
	$scope.profitList = [];
	$scope.choices=[];
	$scope.itemClass=[];
	$scope.groupItem=[];
	$scope.batchData = {barCode:""};
	$scope.bomDetailsList =[];
	$scope.itemDetails={};
	$scope.show_table=true;
	//$scope.syncNow=0;
	$scope.show_form2=false;
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.disablebatch = true;
	$scope.Quetable={id:"",dateTime:"",sysSaleFlag:true,syncNow:0,shopId:"",origin:"",curdAction:""};
	
	
	$scope.bomList=[];
	$scope.prodCostList=[];
	$scope.selectedIndex = 0;
	var vm = this;
	vm.edit = edit;
	vm.showTable = showTable;
	vm.showTableGroupItm=showTableGroupItm;
	vm.func_set_choiceIds=func_set_choiceIds;
	vm.getGroupItemData = getGroupItemData;
	vm.saleItemValidation=saleItemValidation;
	vm.fun_clear_modal=fun_clear_modal;
	vm.next=next;
	vm.prev=prev;
	$scope.dateTimeValidation=dateTimeValidation;
	vm.fun_editData_choice=fun_editData_choice;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.dtInstance = {};


	vm.selected = {};
	vm.itemPrice={};
	$scope.selectedItemPrice={};
	vm.itemisPc={};
	$scope.seletedItems=[];
	$scope.itemData={};
	$scope.selectedSaleItems=[];
	
	
	$scope.sampleSellable =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.sampleMovement =[{id : 0 ,name : "FIFO",},{id : 1 ,name : "LIFO",},];
	$scope.sampleGrpitm =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.sampleCombo =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.sampleValid =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.sampleOpen =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.sampleIswhlsPc =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	
	$scope.sampleWeighing =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.sampleBatch =[{id : 0 ,name : "No",},{id : 1 ,name : "Yes",},];
	$scope.taxBased =[{id : "0" ,name : "Sale Item",},{id : "1" ,name : "Service+Sale Item",},];
	//$scope.formData.taxation_based_on=$scope.taxation[0].id;

	$scope.customSettings = {control: 'brightness',theme: 'bootstrap',position: 'top right'};
	var isSynchable=true;

	$scope.salecombocontentData = {items: []};
	
	$scope.salecombocontentData.items.push({
		id:0,
		sale_item_combo_content_id:"",
		substitution_sale_item_id:"",
		quantity:0,
		price_difference:0,
		is_default:true,
		is_applicable:true
	});
	
	$scope.pricesettings = {items: []};
	$scope.pricesettings.items.push({
		id:0,
		
		stock_item_id:'',
		stock_item_code:'',
		stock_item_name:'',
		qty:0,});
		
		
	
	
	$scope.formData = {is_valid:0,is_whls_price_pc:0,name:"",code:"",description:"",itemCategoryId:0,kitchenId:0,subClassId:"",isBatch:0,profitCategoryId:0,isManufactured:"0",isSellable:0,uomId:0,movementMethod:0,valuationMethod:"0",isComboItem:0,isOpen:0,isRequireWeighing:0,subClassId:"",
			is_active:false,isSynchable:false,packContains:0,shelfLife:0,minStock:0,maxStock:0,stdPurchaseQty:0,leadTime:0,bomQty:0,inputTaxId:"",alternativeName:"",nameToPrint:"",alternativeNameToPrint:"",outputTaxId:"",outputTaxIdHomeService:"",outputTaxIdTableService:"",
			outputTaxIdTakeAwayService:"",fgColor:'#ffffff',bgColor:'#ffffff',choiceIds:"",sysSaleFlag:""};
	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	vm.dtColumns = [DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('width','100px').withOption('type', 'natural').renderWith(
	                		function(data, type, full, meta) {
		               			if(full.quetableId!=""  && full.quetableId!=undefined){css="queTableInColor"}else{css="queTableOutColor"}
	                			if(parseInt(full.is_synchable)==0){isSynchable=false; }else{isSynchable=true;}

	                			//if(parseInt(full.is_synchable)==0){isSynchable=false; css="synchColor"}else{isSynchable=true;css="getPointer"}
	                			return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" class='+css+' ng-href="#">'
	                			+ data + '</a>';}),
	                			DTColumnBuilder.newColumn('name').withTitle('NAME').withOption('width','350px'),
	                			DTColumnBuilder.newColumn('item_category_name').withTitle('ITEM CATEGORY').withOption('width','180px'),
	                			
	                			DTColumnBuilder.newColumn('is_combo_item').withTitle('COMBO').withOption('width','280px').renderWith(
	        	                		function(data, type, full, meta) {
	        	         
	        	                		
	        	                			var data1="";
	        	                		
	        	                			if(data==1){
	        	                				data1="<a  name="+full.name+" class='editcombo' data-toggle='modal' data-target='#comboitem'  ><i class='fa fa-plus-circle' id='editcombo' aria-hidden='true' ng-click=\"editcombo("+full+")\" ></i></a>";
	        	                			}
	        	                			return  data1;
	        	                			
	        	                		}),
	                			
	                			
	                			DTColumnBuilder.newColumn('is_manufactured').withTitle('ITEM TYPE').withOption('width','200px').renderWith(function(data, type, full, meta) {
	                				var type;
	                				if(data==0){
	                					type="Purchased";
	                				}else{
	                					type="Manufactured";
	                				}return type;})];


	function urlFormater(data) {
		
		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
		+ data + '</a>';   
	}

	
	
	
		var urlString="../customertypes/json";
	vm.dtInstance1={};
	vm.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers')
	.withDataProp('data')
    .withOption('paging', false)
    .withOption('info', false)
    .withOption('searching', true)
     .withOption('scrollY', 200)
     .withDisplayLength(10000)
 
    .withOption('createdRow', function(row, data, dataIndex) {
	              		$compile(angular.element(row).contents())($scope);
	              	})
	              	.withOption('headerCallback', function(header) {
	              		if (!vm.headerCompiled) {
	              			vm.headerCompiled = true;
	              			$compile(angular.element(header).contents())($scope);
	              		}
	              	})

	
	
	              	
	              	
	              	
	
	
		

	vm.dtColumns1 = [
	                 
	                 
	                 
DTColumnBuilder.newColumn('default_price_variance_pc').withTitle('#').notSortable().withOption('width','41px')
.renderWith(function(data, type, full, meta) {
    vm.selected[full.id] = false;
  
    return '<input ng-disabled="disable_all" ng-model="item.selected[' + full.id + ']" ng-click="toggleOne(item.selected);clearPrice(item.selected[' + full.id + '],' + full.id + ',' + full.default_price_variance_pc + ');" type="checkbox">';


}),


			
	                 DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','122px'),
		                		DTColumnBuilder.newColumn('name').withTitle('NAME').withOption('width','137px'),
		                		
		                		DTColumnBuilder.newColumn('default_price_variance_pc').withTitle('VARIANCE').withOption('width','156px').renderWith(
		                				function(data, type, full, meta) {
		                					if(full.default_price_variance_pc != null )
		                						{
	            							data=parseFloat(data).toFixed(settings['decimalPlace']);
		                						}
	            							return data	}),
	            							
	            							DTColumnBuilder.newColumn('default_price_variance_pc').withTitle('ITEM WISE VARIANCE').withOption('width','250px')
	            							.notSortable()
	                  .renderWith(function(data, type, full, meta) {
	                /*	  vm.itemPrice[full.id] = "";
	                	  if(full.default_price_variance_pc !="")
	                		{
	                	    parseFloat(full.default_price_variance_pc).toFixed(settings['decimalPlace'])
	                		vm.itemPrice[full.id] = parseFloat(full.default_price_variance_pc).toFixed(settings['decimalPlace']).toString() ;
	                		}*/
	                      return '<input ng-disabled="!item.selected[' + full.id + ']" ng-model="item.itemPrice[' + full.id + ']"'  
	                      +'type="input" ng-change="getSelectedPrice(item.itemPrice,item.selected)" valid-number>';
	                  }),
	                  
	                  
	        	            							
	        	            							DTColumnBuilder.newColumn('default_price_variance_pc').withTitle('PERCENTAGE').withOption('width','171px').renderWith(
	        	        		                				function(data, type, full, meta) {
	        	        		                					
	        	        		                					
	        	        		                					   vm.itemisPc[full.id] = false;
	        	        		                					 /*   if(full.default_price_variance_pc !="")
	        	        		                							{
	        	        		                					    	vm.itemisPc[full.id] = true;
	        	        		                							}
	        	        		                					*/
	        	        		                					
	        	        		                					
	        	        		                					data ='<input type="checkbox"  ng-disabled="disable_all"  id='+full.code+'   ng-model="item.itemisPc['+ full.id + ']">';
	        	        	            							return data	}),				
		                		];


	
	
	$scope.populateCombo=function(itmId){
		

		$http({
			method: 'GET',
			url : "getComboContent",
			params:{itemId:itmId},
			async:false,
		}).success(function (result) {
		

			$scope.salecombocontentData = {items: []};
			
			
			$scope.salecombocontentData.items=result.comboContentData;
			

			for(var i = 0;i<result.comboContentData.length;i++){
				
				$scope.salecombocontentData.items[i].is_applicable=true;
				$scope.salecombocontentData.items[i].is_default=(result.comboContentData[i].is_default==1)?true:false;
				$scope.salecombocontentData.items[i].sale_item_combo_content_id=result.comboContentData[i].combo_content_id;
				$scope.salecombocontentData.items[i].substitution_sale_item_id=result.comboContentData[i].substitution_sale_item_id;
				$scope.salecombocontentData.items[i].quantity=result.comboContentData[i].qty;
				$scope.salecombocontentData.items[i].price_difference=result.comboContentData[i].price_diff;
				}
			
			if($scope.salecombocontentData.items.length==0){
				
				$scope.salecombocontentData.items.push({
					id:0,
					sale_item_combo_content_id:"",
					substitution_sale_item_id:"",
					quantity:0,
					price_difference:0,
					is_default:true,
					is_applicable:true
				});
			}
			
		});	
	}
	

	
	$scope.editcombo=function(full){
		$scope.comboConData={};
		var itemid=full.id;
		var code=full.code;

		$scope.comboConData.combo_sale_item_id = itemid;
	
		
		$( "#saleitemomboitemname" ).empty();
		
		var div = document.getElementById('saleitemomboitemname');

		div.innerHTML += code;
		
	}
	
	
	$scope.fun_save_combo_item=function(){

		$scope.comboConData.combo_content_item_id=$scope.formData.combo_content_id;
		
		$scope.comboConData.max_items=$scope.salecombocontentData.items[0].quantity;
		
		
		

		$scope.combocontentData = [];
		
		$scope.combocontentData.push($scope.salecombocontentData.items);
		
		
		for(var i = 0;i<$scope.salecombocontentData.items.length;i++){
			
			
			
			$scope.salecombocontentData.items[i].is_applicable=($scope.salecombocontentData.items[i].is_applicable==true)?1:0;
			$scope.salecombocontentData.items[i].is_default=($scope.salecombocontentData.items[i].is_default==true)?1:0;
			
			if($scope.salecombocontentData.items[i].quantity > $scope.comboConData.max_items){
			
				$scope.comboConData.max_items=	$scope.salecombocontentData.items[i].quantity;
			
			}
		}
		
		$scope.comboConData.salecomboListItem=JSON.stringify($scope.salecombocontentData.items);
		

		
		$http({
			method: 'POST',
			url : "../saleitemcombocontents/saveComBo",
			data:$scope.comboConData,
			async:false,
		}).success(function (result) {
			
			$scope.salecombocontentData = {items: []};
			$scope.salecombocontentData.items.push({
				id:0,
				sale_item_combo_content_id:"",
				substitution_sale_item_id:"",
				quantity:0,
				price_difference:0,
				is_default:true,
				is_applicable:true
			});
			$scope.formData.combo_content_id="";
			
			
			$("#comboitem").toggle("modal");

		
			
			
		});	
		
		
		
	}
	
	
	 $scope.toggleOne=function(selectedItems) {
		 
			$scope.seletedItems=selectedItems;
			}
	 
	 $scope.clearPrice=function(isDisble,saleId,variance)
	 {
		 if(isDisble == false)
			 {
			 vm.itemPrice[saleId]="";

			 vm.itemisPc[saleId]=false;
			 
			 $scope.selectedItemPrice[saleId]="";
			
			 
			 }else{
				 vm.itemisPc[saleId]=true;
				 
				 vm.itemPrice[saleId]=variance.toFixed(settings['decimalPlace']);	
			 }
	 }
	 
	 
	 $scope.getSelectedPrice=function(selectedPrice,selectedItems)
	 {
		 $scope.selectedItemPrice=selectedPrice;
		 $scope.seletedItems=selectedItems;
	 }
	 
	 
	
	 
	 $scope.setItemSpecificData=function()
	 {
		 //get true values only:
		 $scope.selectedSaleItems=[];
		 for (var key in $scope.seletedItems) { 
			  if ($scope.seletedItems.hasOwnProperty(key)){ 
			    if (!$scope.seletedItems[key]) { 
			      delete $scope.seletedItems[key];
			    }
			  }
			}
		 for (var key in $scope.selectedItemPrice) { 
			  if ($scope.selectedItemPrice.hasOwnProperty(key)){ 
			    if ($scope.selectedItemPrice[key]=="") { 
			      delete $scope.seletedItems[key];
			      delete $scope.selectedItemPrice[key];
			    }
			  }
			}
		 var count=Object.keys($scope.seletedItems).length;
		 
		 for(var i=0;i<count;i++)
		 { 
			$scope.itemData={}; 
		  $scope.itemData.sale_item_id=Object.keys($scope.seletedItems)[i];
		  $scope.itemData.price=$scope.selectedItemPrice[Object.keys($scope.seletedItems)[i]];
		  $scope.itemData.customer_type_id=Object.keys($scope.seletedItems)[i];
			  $scope.itemData.sale_item_id= $scope.formData.id;
				  $scope.itemData.price_variance_pc=  vm.itemPrice[Object.keys($scope.seletedItems)[i]];
					  $scope.itemData.is_percentage=((vm.itemisPc[Object.keys($scope.seletedItems)[i]])==true) ? 1:0;

			  
		  $scope.selectedSaleItems.push($scope.itemData);
		 }
	 }
	

	 
	 $scope.setItemSpecificEditData=function()
	 
	 {
		 
			vm.dtInstance.reloadData();
		 
		 for(var i=0;i<$scope.customertypeEditData.length;i++){
			 
			 vm.selected[$scope.customertypeEditData[i].customer_type_id] = true;

			 				$scope.seletedItems[$scope.customertypeEditData[i].customer_type_id]=true;
						  vm.itemPrice[$scope.customertypeEditData[i].customer_type_id]=parseFloat($scope.customertypeEditData[i].price_variance_pc).toFixed(settings['decimalPlace']);
						  vm.itemisPc[$scope.customertypeEditData[i].customer_type_id]=($scope.customertypeEditData[i].is_percentage==1) ? true:false;
						  
			 
		 }
		 
	
		 
	 }
	 
	 $scope.tab8_isdisabled=true;
	 
	
	 $scope.isCombo=function(){

	if($scope.formData.isComboItem==1){
		 $scope.tab8_isdisabled=false;
	}else{
		$scope.tab8_isdisabled=true;
	}
		
	 }
	 
	 
	 
	 
	 
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					//Rowcallback fun for Get Edit Data when clk on Code
		$('a', nRow).unbind('click');
		$('a', nRow).bind('click', function(e) {
			$scope.$apply(function(){
				$('tr.selected').removeClass('selected');
				if (e.target.id == "rcd_edit") {
					var rowData = aData;
					$(nRow).addClass('selected');	
					var current_row_index = 0;
					var test = vm.dtInstance.DataTable.rows();
					for(var i = 0;i<test[0].length;i++){
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;
						}
					}
					$("#show_form").val(1);
					edit(rowData.id,current_row_index,aData.is_synchable,e);
				} 	if (e.target.id == "editcombo") {
				
					$scope.editcombo(aData);
					
				}
			});
		});
		return nRow;
	}

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}
	}

	$rootScope.$on('hide_table',function(event){
		showTable(event);
		fun_clear_form();
	});

	$rootScope.$on('hide_form',function(event){
		$("#show_form").val(0);
		$scope.show_table=true;
		 $("#advsearchbox").show();
		$scope.show_form=false;
		$scope.show_form2=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
		$scope.disable_bom=true;
		$scope.selectedIndex=0;
		$timeout(function () {				
			  $("#stockHead .acontainer input").attr('disabled',true);
			  $("#prodCost .acontainer input").attr('disabled',true);
	  	}, 1);
		
		
	});

	function showTable(event){
		$scope.show_table=false;
		 $("#advsearchbox").hide();
		$scope.show_form=true;
		$scope.show_form2=false;
		$scope.tab2_isdisabled = true;
		$scope.tab3_isdisabled = true;
		$scope.tab4_isdisabled = true;
		$scope.tab8_isdisabled=true;
		$scope.bomList=[];
		$scope.prodCostList=[];
		$scope.itmID=0;

	}
	
    $scope.getCostPrice = function(index,id){
		var url="getLastCostPrice";
		/*var costPrice =0.00;
		$.each(data, function (i, v) {
			if(v.id == id){

				
					url = "getLastCostPrice";
				
				

			}
		});*/

		$http({url :url,method : "GET",params:{itemId:id},async:false,
		}).then(function(response) {
			
			$scope.bomList[index].unit_price = parseFloat(response.data).toFixed(settings['decimalPlace']);
		}, function(response) { // optional

		});
	}
    
	
    $scope.selectedData=function(data,itemvariance){
    	
    	alert(data +" "+ itemvariance);
    	
    }
    
	function showTableGroupItm()
	{
		$scope.show_table=true;
		 $("#advsearchbox").show();
		$scope.show_form2=false;
		$scope.show_form=false;	
		$scope.disable_all_popup=true;
		$("#form_div_code_modal").removeClass("has-error");
		$("#form_div_code_modal_error").hide();
		$("#form_div_name_modal").removeClass("has-error");
		$("#form_div_name_modal_error").hide();
		
		
	}

	
	
	$scope.selectData=function(data1,code1)
{
		
if($('#chck' + code1).is(":checked"))
		{
	
	$('#'+code1).prop('checked', true);
	$('#text'+code1).val(data1);
	$('#text'+code1).prop('disabled', false);

		}else{
			
		
		$('#'+code1).prop('checked', false);
		$('#text'+code1).val('');
		$('#text'+code1).prop('disabled', true);
		}
}	


	$("#advsearchbox").hide();

	$scope.show_table=false;
	$scope.prograssing1=true;
	$('#btnAdd').hide();

	
	$http({
		method: 'GET',
		url : "getDropDownData"
	}).success(function (result) {
		$scope.displayOrder=result.itemasterclass;
		$scope.profitList = result.profitCategory;
		$scope.categoryList=result.itemCategory;
		$scope.kitchen=result.kitchen;
		$scope.superClassList=result.superClassList;
		$scope.filluom=result.uom;
		
		$scope.filluompack=result.uom1;
		
		$scope.supDataList=result.supplier;
		$scope.taxList=result.tax;
		$scope.stkdata = result.item;
		$scope.choices=result.choices;
		//$scope.itemClass=result.itemClass;
		$scope.groupItem=result.groupItem;
		$scope.displayOrder.splice(0,0,{id : "" ,name : "select"});
		$scope.filluom.splice(0,0,{id : "" ,name : "select"});
		$scope.categoryList.splice(0,0,{id : "" ,name : "select"});
		$scope.kitchen.splice(0,0,{id : 0 ,name : "select"});
		$scope.profitList.splice(0,0,{id : 0 ,name : "select"});
		$scope.superClassList.splice(0,0,{id : "" ,name : "select"});
		$scope.taxList.splice(0,0,{id:"",code:"select"});
		
		$rootScope.$emit("get_itmCtgry_list",{dep:$scope.categoryList});
		
		if(version_value==1)
		 {
		 //$("#btnEdit").hide();
		 $("#btnDelete").hide();
		 $("#btnAdd").hide();
		 
		 }
                  else
			{
			 $("#btnDelete").show();
			  $('#btnAdd').show();
			}
		
		 $("#advsearchbox").show();
			$scope.prograssing1=false;
			$scope.show_table=true;
	}); 

	function getGroupItemData()
	{
		$http({
			method: 'GET',
			url : "getGroupItmData"
		}).success(function (result) {

			$scope.groupItem=result.groupItem;


		}); 

	}
//	choices
	$scope.choicesItem = [];
	$scope.newChoice={"id":"","name":"","code":""};
	$scope.choiceIdd=[];
	var flag=1;;
//	add new choice

	$scope.addNewChoice = function() {
		if($scope.formData.choiceIds!="" && $scope.formData.choiceIds!=undefined)
		{
			flag=1;
			$scope.newChoice={};
			for(var i=0;i<$scope.choices.length;i++ )
			{
				if ($scope.choices[i].id == $scope.formData.choiceIds) {

					if($scope.choicesItem.length==0)
					{
						flag=1;
					}
					if($scope.choicesItem.length!=0)
					{
						for(var j in $scope.choicesItem){
							if($scope.choicesItem[j].id ==$scope.formData.choiceIds){
								flag=0;	
							}
						}
					}

					if(flag==1)
					{
						$scope.newChoice.id=$scope.choices[i].id;
						$scope.newChoice.code=$scope.choices[i].code;
						$scope.newChoice.name=$scope.choices[i].name;
						$scope.choicesItem.push($scope.newChoice);	
						flag=1;	
						break;
					}
				}


			}



		}


	};



//	remove new choice
	$scope.removeChoice = function(i) {

		$scope.choicesItem.splice(i,1);
	};


	//for modal
	$scope.open = function() {
		$scope.showModal = true;
	};

	$scope.ok = function() {
		$scope.showModal = false;
	};

	$scope.cancel = function() {
		$scope.showModal = false;
	};

	//change tax

	$scope.changeTAX=function()
	{
  
     if($scope.formData.taxationBasedOn==0)
    	 {
    	 $scope.formData.outputTaxIdHomeService="";
    	 $scope.formData.outputTaxIdTableService="";
    	 $scope.formData.outputTaxIdTakeAwayService="";
    	 $scope.taxhide=false;
    	 }
     else
    	 {
    	 $scope.formData.outputTaxId="";
    	 $scope.taxhide=true;
    	 }

		//$scope.taxhide=($scope.formData.taxationBasedOn==1) ? true : false;

	}

	$scope.chkItemType = function() {
		if($scope.formData.isManufactured==1){
			$("#bom_qty").css("border-color","#d2d6de"); 
			$scope.tab2_isdisabled = false;
			if($scope.bomList.length == 0){
				$scope.bomList.push({id : "",bom_item_id :"",qty: "",bom_item_name:""});
				$("#rowAdd").show();
				$("#AddrowHd").hide();
				}else{
				$("#rowAdd").hide();
				$("#AddrowHd").show();
			}
			if($scope.prodCostList.length == 0)
				{
				$scope.prodCostList.push({id : "",prod_cost_id :"",prod_cost_code:"",prod_cost_name:"",
					prod_cost_type:"",isPercentage: false,rate:""});
				}

		}else{
			$scope.tab2_isdisabled = true;
			$scope.formData.bomQty="";
			
		}
	}

	$scope.chkSellable = function() {
	
		
		$scope.tab8_isdisabled=true;
		if($scope.formData.isSellable==1){
			$scope.tab3_isdisabled = false;
			
		}else{
			$scope.tab3_isdisabled = true;
			$scope.formData.isComboItem=0;
			$scope.formData.isOpen=0;
			$scope.formData.isRequireWeighing=0;
			$scope.formData.groupItemId="";
			$scope.formData.taxCalculationMethod="";
			$scope.formData.taxationBasedOn="0";
			$scope.formData.outputTaxId="";
			$scope.formData.outputTaxIdHomeService="";
			$scope.formData.outputTaxIdTableService="";
			$scope.formData.outputTaxIdTakeAwayService="";
			$scope.formData.fixedPrice="";
			$scope.formData.alternativeName="";
			$scope.formData.nameToPrint="";
			$scope.formData.alternativeNameToPrint="";
			$scope.formData.fgColor="#ffffff";
			$scope.formData.bgColor="#ffffff";
			
			
		}
	}

	function fun_editData_choice(chIds)
	{
		
		if(chIds=="")
			{
			$scope.choicesItem=[];
			}
		else
			{
			$http({
				method: 'GET',
				url : "getDataToEditChoice",
				params:{itemId:chIds}
			}).success(function (result) {

				$scope.choicesItem=result.choiceDet;
			});
			}
		
	}


	
	$scope.fun_edit_groupItm=function()
	{
		$scope.popbtnhide=true;
		$scope.disable_all_popup=false;

	}

	$scope.fun_discard_groupItm=function()
	{
		
		if($scope.formData1.id!=undefined && $scope.formData1.id!="")
		{
	fun_edit_modal($scope.formData.groupItemId);
	$scope.disable_all_popup=true;
	$scope.popbtnhide=false;
	//	fun_edit_modal($scope.formData1.id);
		}
		else
			{
			fun_clear_modal();
			}

	}
	
	$scope.fun_delete_groupItm=function(){
		//$('#myModal').modal('toggle');
		
		/*var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}
		).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok(
		'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);*/
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData1.id},
			}).then(function(response) {

				if(response.data==1)
				{
					
					
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					$("#show_form").val(0);
					$scope.formData.groupItemId="";
					$( "#form_div_group_item i" ).removeClass( "fa-plus" ).addClass( "fa-pencil" );
					$( "#form_div_group_item i" ).addClass( "fa-pencil" ).addClass( "fa-plus" );

					getGroupItemData();
					
					vm.dtInstance.reloadData();
					
					
				}
				else if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Stock Item "+FORM_MESSAGES.ALREADY_USE+"");
				}
				else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}
			},function(response) { // optional
				{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok')
							.targetEvent(event)
					);
					$('#myModal').modal('toggle');
				}
			});
		/*});*/
		
		$('#myModal').modal('toggle');
	}
	
	
	function edit(itmId,cur_row_index,is_synchable) {
		
		$scope.selectedIndex=0;
		$("#div_table_bom").find(".acontainer input").attr("disabled", true);
		$timeout(function () {				
			  $("#stockHead .acontainer input").attr('disabled',true);
			  $("#prodCost .acontainer input").attr('disabled',true);
	  	}, 1);

		if(isLiteVersion == true)
			{
			$scope.selectedIndex=1;	  	

			}
		
		 
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function	
		//$rootScope.$emit("show_edit_btn_div",cur_row_index);
		/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;*/
		var row_count = vm.dtInstance.DataTable.rows().data();
		row_count=row_count.length;			
		if(row_count == 1){
			$rootScope.$emit("disable_next_btn");
			$rootScope.$emit("disable_prev_btn");
		}else{
			if(cur_row_index == 0){
				$rootScope.$emit("enable_next_btn");
				$rootScope.$emit("disable_prev_btn");
			}else if(row_count-1 == cur_row_index){
				$rootScope.$emit("disable_next_btn");
				$rootScope.$emit("enable_prev_btn");
			}
		}

      

		/*if (companytype != 0) {
			if (is_synchable != 0) {
				$("#btnEdit").hide();
			} else if (is_synchable == 0) {
				$("#btnEdit").show();
			}
		}*/

		$http({
			method: 'GET',
			url : "getDataToEdit",
			params:{itemId:itmId},
			async:false,
		}).success(function (result) {
			$scope.batchDatas=result.batch;
			$scope.bomDetailsList=result.bom;
			$scope.productionCostList=result.prd_costdata;
			$scope.itemDetails=result.itemDetails;
			$scope.customertypeEditData=result.customerTypeList;
			
			$scope.salecombocontentItem=result.comboContentData;
			$scope.pricesettings = {items: []};
		
			$scope.pricesettings.items=$scope.salecombocontentItem;
			for(var i=0;i<$scope.salecombocontentItem.length;i++){
				
				$scope.pricesettings.items[i].stock_item_id=$scope.salecombocontentItem[i].combo_sale_item_id;
				$scope.pricesettings.items[i].stock_item_code=$scope.salecombocontentItem[i].stock_item_code;
				$scope.pricesettings.items[i].stock_item_name=$scope.salecombocontentItem[i].stock_item_name;
				$scope.pricesettings.items[i].qty=$scope.salecombocontentItem[i].max_items;
			
			}
			
			if($scope.salecombocontentItem.length==0){
				$scope.pricesettings.items.push({
					id:0,
					
					stock_item_id:'',
					stock_item_code:'',
					stock_item_name:'',
					qty:0,});
					
			}
		
			
			//$scope.QuetableDet=result.Quetable;
			$scope.itemDetails.sys_sale_flag=($scope.itemDetails.sys_sale_flag=="1")?true:false;
			//$scope.Quetable={id:$scope.QuetableDet.id,dateTime:$scope.QuetableDet.publishing_date,syncNow:$scope.QuetableDet.sync_status,shopId:$scope.QuetableDet.shop_id,origin:$scope.QuetableDet.origin,curdAction:$scope.QuetableDet.crud_action,sysSaleFlag:""};
			//$scope.Quetable.sysSaleFlag=$scope.itemDetails.sys_sale_flag;
			
			if($scope.itemDetails.is_group_item==0)
				{
				
				$scope.setItemSpecificEditData();
			
				$scope.ppHint==0;
				disable = 1;
				showTable();
				$scope.itmID=itmId;
				$scope.disable_all=true;
				$scope.disable_bom=true;
				 $("#imgdiv a").addClass("activeLink");
				
				$scope.disable_code = true;
				$("#form_div_pref_supplier_id").find(".acontainer input").attr("disabled", true);
				
				
				
				
				
				
				
				
				
				

			$scope.formData = {is_whls_price_pc:parseInt($scope.itemDetails.is_whls_price_pc),hsn_code:$scope.itemDetails.hsn_code,
					whls_price:parseFloat($scope.itemDetails.whls_price).toFixed(settings['decimalPlace']),display_order:$scope.itemDetails.display_order,is_hot_item_3:(($scope.itemDetails.is_hot_item_3==1)?true:false),
					hot_item_3_display_order:$scope.itemDetails.hot_item_3_display_order,is_hot_item_2:(($scope.itemDetails.is_hot_item_2==1)?true:false),
					hot_item_2_display_order:$scope.itemDetails.hot_item_2_display_order ,hot_item_1_display_order:$scope.itemDetails.hot_item_1_display_order,
					is_hot_item_1:(($scope.itemDetails.is_hot_item_1==1)?true:false),is_valid:parseInt($scope.itemDetails.is_valid),id:itmId,
					pack_uom_id:$scope.itemDetails.pack_uom_id,is_barcode_print:parseInt($scope.itemDetails.is_barcode_print),name:$scope.itemDetails.name,
					code:$scope.itemDetails.code,description:$scope.itemDetails.description,itemCategoryId:$scope.itemDetails.item_category_id,
					kitchenId:$scope.itemDetails.kitchen_id,isBatch:parseInt($scope.itemDetails.is_batch),profitCategoryId:$scope.itemDetails.profit_category_id,
					isManufactured:$scope.itemDetails.is_manufactured,isSellable:parseInt($scope.itemDetails.is_sellable),uomId:$scope.itemDetails.uom_id,
					movementMethod:parseInt($scope.itemDetails.movement_method),valuationMethod:$scope.itemDetails.valuation_method,
					isComboItem:parseInt($scope.itemDetails.is_combo_item),isOpen:parseInt($scope.itemDetails.is_open),
					isRequireWeighing:parseInt($scope.itemDetails.is_require_weighing),subClassId:$scope.itemDetails.sub_class_id,
					is_active:$scope.itemDetails.is_active,isSynchable:$scope.itemDetails.is_synchable,packContains:$scope.itemDetails.pack_contains,
					shelfLife:$scope.itemDetails.shelf_life,minStock:parseFloat($scope.itemDetails.min_stock).toFixed(settings['decimalPlace']),maxStock:parseFloat($scope.itemDetails.max_stock).toFixed(settings['decimalPlace']),
					stdPurchaseQty:parseFloat($scope.itemDetails.std_purchase_qty).toFixed(settings['decimalPlace']),leadTime:$scope.itemDetails.lead_time,bomQty:parseFloat($scope.itemDetails.bom_qty).toFixed(settings['decimalPlace']),
					inputTaxId:$scope.itemDetails.input_tax_id,alternativeName:$scope.itemDetails.alternative_name,
					nameToPrint:$scope.itemDetails.name_to_print,alternativeNameToPrint:$scope.itemDetails.alternative_name_to_print,
					fgColor:$scope.itemDetails.fg_color,bgColor:$scope.itemDetails.bg_color,prefSupplierId:$scope.itemDetails.pref_supplier_id,
					taxCalculationMethod:$scope.itemDetails.tax_calculation_method,groupItemId:$scope.itemDetails.group_item_id,
					taxationBasedOn:$scope.itemDetails.taxation_based_on,outputTaxId:$scope.itemDetails.output_tax_id,
					outputTaxIdHomeService:$scope.itemDetails.output_tax_id_home_service,outputTaxIdTableService:$scope.itemDetails.output_tax_id_table_service,
					outputTaxIdTakeAwayService:$scope.itemDetails.output_tax_id_take_away_service,attrib1Name:$scope.itemDetails.attrib1_name,
					attrib1Options:$scope.itemDetails.attrib1_options,attrib2Name:$scope.itemDetails.attrib2_name,attrib2Options:$scope.itemDetails.attrib2_options,
					attrib3Name:$scope.itemDetails.attrib3_name,attrib3Options:$scope.itemDetails.attrib3_options,attrib4Name:$scope.itemDetails.attrib4_name,
					attrib4Options:$scope.itemDetails.attrib4_options,attrib5Name:$scope.itemDetails.attrib5_name,attrib5Options:$scope.itemDetails.attrib5_options,
					choiceIds:$scope.itemDetails.choice_ids,itemCost:parseFloat($scope.itemDetails.item_cost).toFixed(settings['decimalPlace']),fixedPrice:parseFloat($scope.itemDetails.fixed_price).toFixed(settings['decimalPlace']),sysSaleFlag:""};
			
			$scope.formData.barcode=$scope.itemDetails.barcode;
			$scope.formData.itemThumb=$scope.itemDetails.item_thumb;
			
			if($scope.formData.itemThumb!="")
				{
				var time=new Date().getTime() / 1000;
				
				     $("#imgshw").empty();
                     $("#imgshw").html('<img src="'+$scope.formData.itemThumb+'?'+time+'" style="max_height:150px">');
                  	 $("#imgdiv").addClass('fileupload-exists').removeClass('fileupload-new');

                  	
                  /* $("span.fileupload-new").css('display', 'none');
                   $("span.fileupload-exists").css('display', 'inline');
                 $("a.btn.btn-danger.fileupload-exists").css('display', 'inline');*/
                 
				}
			else
				{
				 $("#imgshw").empty();
				 $("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');

				}
		
			$scope.changeTAX();
			fun_editData_choice($scope.formData.choiceIds);


			$scope.bomList=$scope.bomDetailsList;
			$scope.prodCostList=$scope.productionCostList;
			if($scope.bomList.length == 0 && $scope.prodCostList.length == 0)
				{
				$timeout(function () {				
					  $("#stockHead .acontainer input").attr('disabled',true);
					  $("#prodCost .acontainer input").attr('disabled',true);
			  	}, 1);
				}
			if($scope.batchDatas!=undefined){
				
			
			$scope.batchData={barCode:$scope.batchDatas.bar_code,id:$scope.batchDatas.id};
			}else{
				$scope.batchData={barCode:"",id:""};
					
				
			}
			if($scope.formData.is_active==1){$scope.formData.is_active=false}else{$scope.formData.is_active=true};
			if($scope.formData.isSynchable==1){$scope.formData.isSynchable=true}else{$scope.formData.isSynchable=false};
			for(var i=0;i<$scope.bomList.length;i++){
				$scope.bomList[i].qty=parseFloat($scope.bomList[i].qty).toFixed(settings['decimalPlace']);
				$scope.bomList[i].unit_price=parseFloat($scope.bomList[i].unit_price).toFixed(settings['decimalPlace']);
				$scope.bomList[i].isSet=false;
			}
			for(var i=0;i<$scope.prodCostList.length;i++){
				$scope.prodCostList[i].rate=parseFloat($scope.prodCostList[i].rate).toFixed(settings['decimalPlace']);
				if($scope.prodCostList[i].is_percentage == 1){$scope.prodCostList[i].isPercentage = true;}else
					{
					$scope.prodCostList[i].isPercentage =false;
					}
				$scope.prodCostList[i].isSet=false;
			}
			$scope.chkItemType();
			$scope.chkSellable();
			if($scope.bomList.length == 0){
				$("#rowAdd").hide();
				$("#AddrowHd").show();
			}else{
				$("#rowAdd").show();
				$("#AddrowHd").hide();
			}	
			//setSubclass($scope.formData.subClassId);
			setSupplier($scope.formData.prefSupplierId);
			
		/*	if (companytype != 0) {
				if ($scope.itemDetails.is_synchable != 0) {
					$("#btnEdit").hide();
				} else if ($scope.itemDetails.is_synchable == 0) {
					$("#btnEdit").show();
				}
			}*/
			
			$scope.buttonChange();
			$("#imgshw").css("border-color","#d2d6de");
			
			$("#form_div_item_category_id").removeClass("has-error");
			$("#form_div_item_category_id_error").hide();
			$("#form_div_is_manufactured").removeClass("has-error");
			$("#form_div_is_manufactured_error").hide()
		    $("#form_div_uom_id").removeClass("has-error");
			$("#form_div_uom_id_error").hide();
			$("#form_div_valuation_method").removeClass("has-error");
			$("#form_div_valuation_method_error").hide();
			$("#form_div_name").removeClass("has-error");
			$("#form_div_name_error").hide();
			
			
			$("#form_div_taxation_method").removeClass("has-error");
			$("#form_div_taxation_method_error").hide();
			$("#form_div_tax").removeClass("has-error");
			$("#form_div_tax_error").hide();
			$("#form_div_tax_id_home_service").removeClass("has-error");
			$("#form_div_tax_id_home_service_error").hide();
			$("#form_div_tax_id_table_service").removeClass("has-error");
			$("#form_div_tax_id_table_service_error").hide();
			$("#form_div_tax_id_take_away_service").removeClass("has-error");
			$("#form_div_tax_id_take_away_service_error").hide();
			
			 $scope.isCombo();
	}	else if($scope.itemDetails.is_group_item==1)
	{/*
		fun_clear_modal();
		//showTableGroupItm();
		$scope.formData1={id:$scope.itemDetails.id,name:$scope.itemDetails.name,code:$scope.itemDetails.code,group_item_id:$scope.itemDetails.group_item_id,fg_color:$scope.itemDetails.fg_color,bg_color:$scope.itemDetails.bg_color,is_batch:0,is_combo_item:0,is_manufactured:0,is_open:0,is_require_weighing:0,is_sellable:1,item_category_id:0,movement_method:0,uom_id:0,valuation_method:0}
		
		
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
		
			$scope.popbtnhide=false;
			$scope.disable_all_popup=true;
			
		
			if (companytype != 0) {
				if ($scope.itemDetails.is_synchable != 0) {
					$("#popEdit").hide();
				} else if ($scope.itemDetails.is_synchable == 0) {
					$("#popEdit").show();
				}
			}
			
			
			if($scope.show_form==false){
				$('#myModal').modal('toggle');
				
				
			}
			$scope.ppHint=1;
		$("#form_div_code_modal").removeClass("has-error");
		$("#form_div_code_modal_error").hide();
		$("#form_div_name_modal").removeClass("has-error");
		$("#form_div_name_modal_error").hide();
		
		if ($scope.bttn=="prev" && $scope.show_form==true)
		{
	prev(cur_row_index);
		}
	else if($scope.bttn=="next" && $scope.show_form==true)
			{
		next(cur_row_index);
			}
		
		*/
		fun_clear_modal();
		$scope.disable_all_popup=true;
	$scope.show_form2=true;
	$scope.show_form=false;
	$scope.show_table=false;
	 $("#advsearchbox").hide();
	$("#btnEdit").hide();
	$("#btnDelete").hide();
	
	$scope.formData1={id:$scope.itemDetails.id,name:$scope.itemDetails.name,code:$scope.itemDetails.code,group_item_id:$scope.itemDetails.group_item_id,fg_color:$scope.itemDetails.fg_color,bg_color:$scope.itemDetails.bg_color,is_batch:0,is_combo_item:0,is_manufactured:0,is_open:0,is_require_weighing:0,is_sellable:1,item_category_id:0,movement_method:0,uom_id:0,valuation_method:0}

	}
	
			
		
			
			
			$rootScope.$emit("show_edit_btn_div",cur_row_index);
	
	
		
		
		});	
		
		
		$("#div_table_bom").find(".acontainer input").attr("disabled", true);
	
	}
	
 $scope.$watch('disable_bom', function() { 
		
		if($scope.disable_bom== true && $scope.isLiteVersion == true)
			{
			  $timeout(function () {				
				  $("#stockHead .acontainer input").attr('disabled',true);
				  $("#prodCost .acontainer input").attr('disabled',true);
		  	}, 1);
			}else if($scope.isLiteVersion == true)
				{
				$timeout(function () {				
					  $("#stockHead .acontainer input").attr('disabled',false);
					  $("#prodCost .acontainer input").attr('disabled',false);
			  	}, 1);
				}
		
	}, true);
	
	$scope.$watch('selectedIndex', function() { 
		
		if(($scope.disable_all== true && $scope.selectedIndex ==1 && isLiteVersion == false)
				|| ($scope.disable_bom== true && $scope.isLiteVersion == true))
			{
			  $timeout(function () {				
				  $("#stockHead .acontainer input").attr('disabled',true);
				  $("#prodCost .acontainer input").attr('disabled',true);
		  	}, 1);
			}else 
				{
				$timeout(function () {				
					  $("#stockHead .acontainer input").attr('disabled',false);
					  $("#prodCost .acontainer input").attr('disabled',false);
			  	}, 1);
				}
		
	}, true);
	$scope.removeBomRow = function(index) {
		if($scope.disable_all == false || $scope.disable_bom ==false){
			
			if($scope.bomList.length==1)
				{
				
				
			if(index==0)
			{
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);

			}
				}
			else
				{
				$scope.bomList.splice(index, 1);

				}
		}
		
		
		
	/*	$timeout(function () { $("#div_table_bom").find(".acontainer input").attr("disabled", true);
		}, 1); */

	}
	$scope.removeProdCostRow = function(index) {
		if($scope.disable_all == false || $scope.disable_bom ==false){
			
			if($scope.prodCostList.length==1)
				{	
			if(index==0)
			{
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);

			}
				}
			else
				{
				$scope.prodCostList.splice(index, 1);

				}
		}
	}
	
	$scope.addProdCostRow = function(index) {
	      
		if($scope.disable_all == false || $scope.disable_bom ==false){
			if($scope.prodCostList.length >=1){
				if($scope.prodCostList[$scope.prodCostList.length-1].prod_cost_id=="")
					{
					
					$("#prodCost tbody tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
			
					}
				else if($scope.prodCostList[$scope.prodCostList.length-1].rate=="")
				{
				
				$("#prodCost tbody tr:nth-child("+(index)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
		
				}
				else
					{
					$scope.prodCostList.push({id : "",prod_cost_id :"",prod_cost_code:"",prod_cost_name:"",prod_cost_type:"",isPercentage: false,rate:""});

					}
				
					
		}
			else
				{
				$scope.prodCostList.push({id : "",prod_cost_id :"",prod_cost_code:"",prod_cost_name:"",prod_cost_type:"",isPercentage: false,rate:"",flag1:1});
				
				}
		
			
		}
		
	}
	

	
	$scope.addBomRow = function(index) {
      
		if($scope.disable_all == false || $scope.disable_bom ==false){
			if(index<$scope.bomList.length-1){
				$("#stockHead tbody tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
    		}else{
    		
			
			if($scope.bomList.length >=1){
				if($scope.bomList[$scope.bomList.length-1].bom_item_id=="")
					{
					
					$("#stockHead tbody tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
			
					}
				else if($scope.bomList[$scope.bomList.length-1].qty=="")
				{
				
				$("#stockHead tbody tr:nth-child("+(index)+") td:nth-child("+(4)+")").find("#qty").focus();
		
				}
				else
					{
					$scope.bomList.push({id : "",bom_item_id :"",qty: "",bom_item_name:""});

					}
				
					
		}
			else
				{
				$scope.bomList.push({id : "",bom_item_id :"",qty: "",bom_item_name:""});
				}
		
			
		}}
		
	}
			
	function bomValidation(){
		var flg = true;
		$("#bom_qty").css("border-color","#d2d6de");
		
		
		
		 if($scope.bomList.length==1)
			{
			 
			 if(($scope.formData.bomQty == "" || $scope.formData.bomQty == parseFloat("0").toFixed(settings['decimalPlace']))
					 && $scope.bomList[0].bom_item_id !="")
				{
				 $("#bom_qty").css("border-color","red");
				 $rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');
				
					flg = false;
				
				}
			
			 else if($scope.formData.bomQty != "" && $scope.formData.bomQty != parseFloat("0").toFixed(settings['decimalPlace']) 
				 && $scope.bomList[0].bom_item_id !="" && $scope.bomList[0].qty =="")
				 {
				 
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
					$scope.selectedIndex = 1;
					$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(4)+")").find("#qty").focus();

					flg=false;
				 }
			 
			 else if($scope.formData.bomQty != "" && $scope.formData.bomQty != parseFloat("0").toFixed(settings['decimalPlace'])
				 && $scope.bomList[0].bom_item_id =="" && $scope.bomList[0].qty =="" && $scope.prodCostList[0].prod_cost_id =="")
			 {
				 $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

					$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

					flg=false;
				 
			 }
			 else if($scope.bomList[0].bom_item_id !="" && $scope.bomList[0].bom_item_id !=undefined && $scope.bomList[0].qty =="" )
				{
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
				$scope.selectedIndex = 1;
				$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(4)+")").find("#qty").focus();
				flg=false;
				}
			else if($scope.bomList[0].bom_item_id =="" && $scope.bomList[0].qty !="")
				{
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$scope.selectedIndex = 1;
				$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
				flg=false;
				}
			
			else if($scope.bomList[0].bom_item_id !="" && $scope.bomList[0].bom_item_id!=undefined )
				{
				if($scope.formData.bomQty == "" || $scope.formData.bomQty == parseFloat("0").toFixed(settings['decimalPlace'])){
					if($scope.formData.itemCategoryId != 0 && $scope.formData.uomId != 0 && $scope.formData.valuationMethod != ""){
						$scope.selectedIndex = 1;
					}
					$("#bom_qty").css("border-color","red");
					$rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');
					$scope.disable_all = false;
					$scope.disable_bom =false;
					flg = false;

				}	
			}
				
	   }
		
		else if($scope.bomList.length >1)
			{
			for(var i = 0;i<$scope.bomList.length;i++){
				if($scope.bomList[i].bom_item_id =="" || $scope.bomList[i].bom_item_id ==undefined  )
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$("#stockHead tbody tr:nth-child("+(i+1)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
					$scope.selectedIndex = 1;
					flg=false;
					break;
				}
				
				else if($scope.bomList[i].qty =="" || $scope.bomList[i].qty ==undefined  )
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);					
					$("#stockHead tbody tr:nth-child("+(i+1)+") td:nth-child("+(4)+")").find("#qty").focus();
					$scope.selectedIndex = 1;
					flg=false;
					break;
				}
				
				
					
				else if($scope.formData.bomQty == "" || $scope.formData.bomQty == parseFloat("0").toFixed(settings['decimalPlace'])){
					if($scope.formData.itemCategoryId != 0 && $scope.formData.uomId != 0 && $scope.formData.valuationMethod != ""){
						$scope.selectedIndex = 1;
					}
					$("#bom_qty").css("border-color","red");
					$rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');
					$scope.disable_all = false;
					$scope.disable_bom =false;
					flg = false;
					break;

				
			}
				
				
				
				
				
			}
			
			
		}
		return flg;	
	}
	
	function prodCostValidation()
	{

		var flg = true;	
		 if($scope.prodCostList.length==1)
			{
			 
			if($scope.prodCostList[0].prod_cost_id !="" && $scope.prodCostList[0].rate == "")
				 {
				 
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(5)+")").find("#cost_rate").focus();

					flg=false;
					
				 }
			else if($scope.prodCostList[0].prod_cost_id !="" && 
					($scope.formData.bomQty == "" || $scope.formData.bomQty == parseFloat("0").toFixed(settings['decimalPlace']))) 
				{
				 $("#bom_qty").css("border-color","red");
				 $rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');
			     flg = false
				}
			else if($scope.prodCostList[0].prod_cost_id =="" && $scope.prodCostList[0].rate !="")
				{
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$scope.selectedIndex = 1;
				$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
				flg=false;
				
				}
			 if(($scope.prodCostList[0].rate =="" || $scope.prodCostList[0].rate ==undefined )&& $scope.prodCostList[0].prod_cost_id !="" )
			{
				if($scope.prodCostList[0].isPercentage == false){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);			
				}else 
					{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);	
					}
				flg=false;
				$scope.selectedIndex = 1;
				$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
				
				
			}
			 else if(($scope.prodCostList[0].isPercentage == true && $scope.prodCostList[0].rate > 100 && $scope.prodCostList[0].prod_cost_id !="") || 
					($scope.prodCostList[0].isPercentage == false && $scope.prodCostList[0].rate.split(".")[0].length > 10))
				{
				   $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.PERCENT_LIMIT_ERR);
				   flg=false;
				   $scope.selectedIndex = 1;
				   $("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
		
					
				}

			}
		else if($scope.prodCostList.length >1)
			{
			for(var i = 0;i<$scope.prodCostList.length;i++){
				if(($scope.prodCostList[i].prod_cost_id =="" || $scope.prodCostList[i].prod_cost_id ==undefined)  
						&& $scope.bomList[0].bom_item_id=="")
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+(i+1)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
       
					flg=false;
					break;
				}
				
				else if(($scope.prodCostList[i].rate =="" || $scope.prodCostList[i].rate ==undefined) && $scope.prodCostList[0].prod_cost_id !="" )
				{
					if($scope.prodCostList[i].isPercentage == false){
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);			
					}else 
						{
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);	
						}
					flg=false;
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+(i+1)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
					break;
					
				}
				if((($scope.prodCostList[i].isPercentage == true && $scope.prodCostList[i].rate > 100) || 
						($scope.prodCostList[i].isPercentage == false && $scope.prodCostList[i].rate.split(".")[0].length > 10))
						&& $scope.prodCostList[0].prod_cost_id !="")
					{
					   $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.PERCENT_LIMIT_ERR);
					   flg=false;
					   $scope.selectedIndex = 1;
						$("#prodCost tbody tr:nth-child("+(i+1)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
						break;
						
					}
				else if($scope.formData.bomQty == "" || $scope.formData.bomQty == parseFloat("0").toFixed(settings['decimalPlace'])){
					
					$("#bom_qty").css("border-color","red");
					$rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');
					$scope.disable_all = false;
					$scope.disable_bom =false;
					flg = false;
					break;

				
			}
					
			}
			
			
		}
		return flg;	
		
	}
	
	
	
	
	
	
	

	$rootScope.$on("fun_delete_current_data",function(event){			//Delete Function
		

	
		
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}
		).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok(
		'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id,batchId:$scope.batchData.id},
			}).then(function(response) {

				if(response.data==1)
				{
					$scope.disable_all = true;
					$scope.disable_bom =true;
					$scope.disable_code = true;
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					manageButtons("add");
					vm.dtInstance.reloadData();
					$scope.show_table=true;
					 $("#advsearchbox").show();
					$scope.show_form2=false;
					$scope.show_form=false;

				}
				else if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Stock Item "+FORM_MESSAGES.ALREADY_USE+"");
				}
				else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}
			},function(response) { // optional
				{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok')
							.targetEvent(event)
					);
				}
			});
		
				
			});
	});
	//function save group item
	
	
	
	
	
	
	
	
	
	
	
	

	$scope.fun_save_groupItm=function()
	{
		
		
		
		
		
/*		save old model
*/		
		
		var date =get_date_format();
		$scope.formData1.created_by=strings['userID'];
		$scope.formData1.created_at=getMysqlFormat(date.mindate);
		if(code_existing_validation($scope.formData1,1)){
			
			
			$scope.modalShow=true;
			
			$scope.allshopCheck=(companytype==0)?true:false;
			$scope.allshopCheck=($scope.formData1.id!="" )?false:true;
			if($scope.formData1.id==undefined )
				{
				$scope.allshopCheck=true;
				}
			
			
			if($scope.formData1.id!="" && $scope.formData1.id!=undefined )
				{
			$scope.funGetQueDet($scope.formData1.id);
			$scope.Quetable.sysSaleFlag=$scope.modalDetails.sys_sale_flag;
			
				}
			if($scope.isLiteVersion == false)
			{
			$("#myModal2").modal('toggle');
			}
			/*		
	
			
			
			
			$scope.formData1.is_group_item=1;
			$scope.formData1.is_active=1;
			$http({
				url : 'save',
				method : "POST",
				params : $scope.formData1,

			}).then(function(response) 

					{

				if(response.data == 1){
					if ($scope.formData1.id != undefined) {
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
						getGroupItemData();


					} else {
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
						$scope.formData1 = {};
						getGroupItemData();

						//$scope.fun_get_pono();
					}
					// reloadData();
					// $scope.hide_code_existing_er = true;
				}else
				{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}
				vm.dtInstance.reloadData();
				$('#myModal').modal('toggle');
					
					},  function(response) { // optional

					{
						$mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Save failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					}

				});
			


*/

		}
	}

	
	
	$scope.removeItem = function(index,event) {
		if($scope.disable_all==false ){	
			var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
				var $dialog = angular.element(document.querySelector('md-dialog'));
				var $actionsSection = $dialog.find('md-dialog-actions');
				var $cancelButton = $actionsSection.children()[0];
				var $confirmButton = $actionsSection.children()[1];
				angular.element($confirmButton).removeClass('md-focused');
				angular.element($cancelButton).addClass('md-focused');
				$cancelButton.focus();
			}}).title(FORM_MESSAGES.ROW_DELETE_WRNG).targetEvent(event).cancel('No').ok(
			'Yes')
			;
			$mdDialog.show(confirm).then(function() {
			
			
			if($scope.pricesettings.items.length!=1){
			$scope.pricesettings.items.splice(index, 1);$("#items_table tr:nth-child("+($scope.pricesettings.items.length+1)+")").find("#itemqty").select();	}
		else{
			$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
		}});}};
		
		
		
	
	
	$scope.buttonChange=function(){
		$scope.formData1.id=$scope.formData.groupItemId;
		if($scope.formData1.id!=undefined && $scope.formData1.id!="")
		{
			$( "#form_div_group_item i" ).removeClass( "fa-plus" ).addClass( "fa-pencil" );
		}
		else
		{
			$( "#form_div_group_item i" ).removeClass( "fa-pencil" ).addClass( "fa-plus" );
		}
		
	}

	//func_set_choiceIds
	function func_set_choiceIds()
	{
		if($scope.choicesItem.length==0)
		{
			$scope.formData.choiceIds="";
		}
		else if($scope.choicesItem.length==1)
		{

			$scope.formData.choiceIds=$scope.choicesItem[0].id

		}
		else if($scope.choicesItem.length!=0)
		{
			for(var k in $scope.choicesItem)
			{
				$scope.formData.choiceIds+=$scope.choicesItem[k].id+",";
			}
		}
		$scope.formData.choiceIds = $scope.formData.choiceIds.replace(/,\s*$/, "");
		



	}

	
	$scope.removeImage=function()
	{
		$scope.myFile="";
		$scope.formData.itemThumb="";
		$scope.itemDetails.item_thumb="";
		$("#imgshw img").remove();
		$("#defaultImage img").remove();
		$("#item_thumb").addClass('fileupload-new').removeClass('fileupload-exists');
		
	}
	

	
//	function popup
	$scope.popup=function()
	{
		$scope.disable_all_popup=false;
		$scope.popbtnhide=true;
		fun_clear_modal();
		$scope.formData1.id=$scope.formData.groupItemId;
		if($scope.formData1.id!=undefined && $scope.formData1.id!="")
		{
			$scope.popbtnhide=false;
			$scope.disable_all_popup=true;
			fun_edit_modal($scope.formData1.id);
		}

	}

//	function clear modal
	function fun_clear_modal()
	{
		$scope.formData1= {id:"",name:"",code:"",group_item_id:"",fg_color:'#ffffff',bg_color:'#ffffff',is_batch:0,is_combo_item:0,is_manufactured:0,is_open:0,is_require_weighing:0,is_sellable:1,item_category_id:0,movement_method:0,uom_id:0,valuation_method:0};
	
		$("#form_div_code_modal").removeClass("has-error");
		$("#form_div_code_modal_error").hide();
		$("#form_div_name_modal").removeClass("has-error");
		$("#form_div_name_modal_error").hide();
	}
	function next(cur_row_index)
	{
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = cur_row_index;
		if(current_row_index == 0){
			$rootScope.$emit("enable_prev_btn");
		}
		/*if($scope.ppHint==1)
			{
			var next_row_index=current_row_index+2;
			}
		else
			{
			var next_row_index = current_row_index+1;

			}*/
		var next_row_index = current_row_index+1;

		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(next_row_index).data();
			edit(selcted_row_data[0].id,next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}
		if(row_count-1 == current_row_index+1){
			$rootScope.$emit("disable_next_btn");
		}	
	}

	function prev(cur_row_index)
	{
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = cur_row_index;
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		/*if($scope.ppHint==1)
		{
		var prev_row_index=current_row_index-2;
		}
		else
			{
			var prev_row_index = current_row_index-1;
			}*/
		var prev_row_index = current_row_index-1;

		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(prev_row_index).data();
			edit(selcted_row_data[0].id,prev_row_index);
			$rootScope.$emit("next_formdata_set",prev_row_index);
		}
		if(current_row_index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}
	}
	function fun_edit_modal(modalId)
	{
		$http({
			url:"editModal",
			method:"POST",
			params:{id:modalId}

		}).success(function(result){
			$scope.modalDetails=result.modalDetails;
			$scope.formData1={id:modalId,code:$scope.modalDetails.code,name:$scope.modalDetails.name,group_item_id:$scope.modalDetails.group_item_id,fg_color:$scope.modalDetails.fg_color,bg_color:$scope.modalDetails.bg_color,is_batch:0,is_combo_item:0,is_manufactured:0,is_open:0,is_require_weighing:0,is_sellable:1,item_category_id:0,movement_method:0,uom_id:0,valuation_method:0}

		});
		
		$("#form_div_code_modal").removeClass("has-error");
		$("#form_div_code_modal_error").hide();
		$("#form_div_name_modal").removeClass("has-error");
		$("#form_div_name_modal_error").hide();


	}

	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_bom=true;
		$("#div_table_bom").find(".acontainer input").attr("disabled", true);
		  $("#prodCost .acontainer input").attr('disabled',true);
		$(".acontainer input").attr('disabled', true);
		 $timeout(function () {				
			  $("#stockHead .acontainer input").attr('disabled',true);
			  $("#prodCost .acontainer input").attr('disabled',true);
	  	}, 1);
		
	}

	
	$scope.clearCheck=function()
	{
		$scope.Quetable.syncNow=1;
		var dateNow =get_date_format();
	    var dt = new Date();
		var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
		$scope.Time=time;
		$scope.Quetable.dateTime=dateNow.mindate+" "+$scope.Time;
	}

	$scope.syncfunction=function(){
		
		};
	//get format of date time	
		
		$scope.filterTimes1=getDateTime();
	$scope.getDateTime=function(){
		
		
		
		return getDateTime();
	}
	
	//clear dateTime feild
		
	$scope.clearDate=function()
	{
		$scope.Quetable.dateTime="";
		$("#dateTime").find("input").css("border-color","#d2d6de");

		
	}
	
	
	
	//save
	$scope.functionSaveData=function()
	{
		
		 $scope.setItemSpecificData();
		 
		 
	if(dateTimeValidation())
	{
			
		  if($scope.modalShow==true){
			
			
			  if($scope.formData1.id!="" && $scope.formData1.id!=undefined)
				{
				$scope.Quetable.sysSaleFlag=$scope.modalDetails.sys_sale_flag;

				}
		$scope.formData1.sysSaleFlag=($scope.Quetable.sysSaleFlag==true)?1:0;
		if($scope.Quetable.dateTime!="" && $scope.Quetable.dateTime!=undefined)
			{
			var dateTimess=$scope.Quetable.dateTime.split(" ");
			$scope.date=getMysqlFormat(dateTimess[0]);
			var time=getMySqlTimeFormat(dateTimess[1]+""+dateTimess[2]);
			$scope.Quetable.dateTime=$scope.date+" "+time;
			}
		else
			{
			$scope.Quetable.dateTime="";
			}
		
		
		$scope.Quetable.sysSaleFlag=$scope.formData1.sysSaleFlag;
		
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction=($scope.formData1.id=="" || $scope.formData1.id==undefined )?"C":"U";
			  
			  
			  
		
		//save old modal
		
		
		
			
			
			
			
			
	
			
			
			
			$scope.formData1.is_group_item=1;
			$scope.formData1.is_active=1;
			$scope.formData1.bgColor=$scope.formData1.bg_color;
			$scope.formData.Quetable=JSON.stringify($scope.Quetable);
			
			
			$http({
				url : 'saveModal',
				method : "POST",
				params : $scope.formData1,
				data : {Quetable:$scope.Quetable},

			}).then(function(response) 

					{

				if(response.data == 1){
					if ($scope.formData1.id != undefined) {
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
						getGroupItemData();



					} else {
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
						$scope.formData1 = {};
						getGroupItemData();

						//$scope.fun_get_pono();
					}
					// reloadData();
					// $scope.hide_code_existing_er = true;
				}else
				{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}
				vm.dtInstance.reloadData();
				$scope.Quetable={id:"",dateTime:"",sysSaleFlag:true,syncNow:0,shopId:"",origin:"",curdAction:""};

				if($scope.isLiteVersion==false)
					{
				$("#myModal2").modal('toggle');
					}
				$scope.modalShow=false;
				if($scope.isLiteVersion==false)
				{
				$('#myModal').modal('toggle');
				}
					},  function(response) { // optional

					{
						$mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Save failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					}

				});
			




		
			  
			  
			  
			
	  }
			
			
		else
			{
			
			
			
			if($scope.formData.id!="" && $scope.formData1.id!=undefined)
				{
				$scope.Quetable.sysSaleFlag=$scope.itemDetails.sys_sale_flag;

				}
		$scope.formData.sysSaleFlag=($scope.Quetable.sysSaleFlag==true)?1:0;
		if($scope.Quetable.dateTime!="" && $scope.Quetable.dateTime!=undefined)
			{
			var dateTimess=$scope.Quetable.dateTime.split(" ");
			$scope.date=getMysqlFormat(dateTimess[0]);
			
	       var time=getMySqlTimeFormat(dateTimess[1]+" "+dateTimess[2]);
			
			$scope.Quetable.dateTime=$scope.date+" "+time;
			}
		else
			{
			$scope.Quetable.dateTime="";
			}
		
		
		$scope.Quetable.sysSaleFlag=$scope.formData.sysSaleFlag;
		
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			
		
		
		
	//	save old
		
		var date =get_date_format();
		$scope.formData.created_by=strings['userID'];
		$scope.formData.created_at=getMysqlFormat(date.mindate);
		
		$scope.formData.choiceIds="";

		func_set_choiceIds();
		$scope.formData.barcode=$scope.batchData.barCode;
		$scope.formData.isManufactured=parseInt($scope.formData.isManufactured);
		if($scope.formData.is_active==true){$scope.formData.is_active=0}else{$scope.formData.is_active=1};
		if($scope.formData.isSynchable==true){$scope.formData.isSynchable=1}else{$scope.formData.isSynchable=0};
		
		if($scope.formData.is_hot_item_1==true){$scope.formData.is_hot_item_1=1}else{$scope.formData.is_hot_item_1=0};
		if($scope.formData.is_hot_item_2==true){$scope.formData.is_hot_item_2=1}else{$scope.formData.is_hot_item_2=0};
		if($scope.formData.is_hot_item_3==true){$scope.formData.is_hot_item_3=1}else{$scope.formData.is_hot_item_3=0};

		
		if($scope.formData.packContains=="")
			{
			$scope.formData.packContains=0;

			}
		if($scope.formData.shelfLife=="")
			{
			$scope.formData.shelfLife=0;
			}
	if($scope.formData.minStock=="")
		{
		$scope.formData.maxStock=0;
		}
	if($scope.formData.stdPurchaseQty=="")
		{
		$scope.formData.stdPurchaseQty=0;
		}
	if($scope.formData.leadTime==""){
		$scope.formData.leadTime=0;
	}
	
		$scope.formData.isGroupItem=0;
		
		$scope.customerTypeList=[];
/*		$scope.customerTypeList=JSON.stringify($scope.selectedSaleItems);*/
		
		$scope.saleComboContents=[];
		$scope.saleComboContents=JSON.stringify($scope.pricesettings.items);

		
	
		if($scope.pricesettings.items.length !=0 && $scope.pricesettings.items[0].id==0)
		{
			$scope.pricesettings = {items: []};
			
		}
           var file = $scope.myFile;
       	   var fdata = new FormData();
       	   var data = JSON.stringify({itemData:$scope.formData,bom:$scope.bomList,batchItem:$scope.batchData,
       		   Quetable:$scope.Quetable,file:$scope.myFile,prodCostList:$scope.prodCostList,customerTypeList:$scope.selectedSaleItems,saleitemcombocontent:$scope.pricesettings.items});
       	fdata.append("imgFile",file);
       	fdata.append("data",data);
       	

       	$http.post("saveItem",fdata,{
			transformRequest : angular.identity,
			headers : {
			'Content-Type' : undefined
			}
			}).success(function(response) {
			$scope.formData.isManufactured=$scope.formData.isManufactured.toString();
			
			if($scope.formData.is_active==1){$scope.formData.is_active=true}else{$scope.formData.is_active=false};
			if($scope.formData.isSynchable==1){$scope.formData.isSynchable=true}else{$scope.formData.isSynchable=false};

			if(response.data !=0){
				
				if($scope.itmID!=0){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					
					view_mode_aftr_edit();	
					if($scope.formData.is_active==0){$scope.formData.is_active=true}else{$scope.formData.is_active=false};
					/*if($scope.formData.isManufactured == 1)
					{
					$scope.bomList=response.bomData1;
					}
					$scope.prodCostList=response.proddata1;*/
					if($scope.formData.isManufactured==1){
						setBomAndProdData(response);	
					}
					
					
				}else{
					$("#form_div_pref_supplier_id").find(".acontainer input").val("");
					clearform();
					fun_clear_form();
					$scope.fun_get_pono();
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					$scope.chkItemType();
					$scope.bomList=[];
					$scope.prodCostList=[];
					$("#imgshw").empty();
					$scope.tab2_isdisabled=true;
					$scope.tab3_isdisabled=true;
					$scope.Quetable={id:"",dateTime:"",sysSaleFlag:true,syncNow:0,shopId:"",origin:"",curdAction:""};

					
				}
				if( $scope.selectedIndex != 0)
					{
					 $scope.selectedIndex = 0;
					}
				if(isLiteVersion == true)
					{
					 $scope.selectedIndex = 1;
					}
				vm.dtInstance.reloadData();
				$timeout(function () {				
					  $("#stockHead .acontainer input").attr('disabled',true);
					  $("#prodCost .acontainer input").attr('disabled',true);
			  	}, 1);
				$("#bom_qty").css("border-color","#d2d6de");
				if($scope.isLiteVersion == false)
					{
				$("#myModal2").modal('toggle');
					}
			}
			else
			{
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
			}
		clearform();
		
		vm.dtInstance1.reloadData();
		
		}).error(function(response) { // optional

			
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
			

		});
		
		
		
		
		
		
			}
		  
		  
		  
	}	  
}
	function setBomAndProdData(rsponse)
	{
		if($scope.bomList[0].bom_item_id !=undefined && $scope.bomList[0].bom_item_id!=null)
			{
			for(var i=0;i<$scope.bomList.length;i++)
				{
				 if($scope.bomList[i].id == "" && $scope.bomList[i].bom_item_id !="")
					 {
					 $scope.bomList[i].id=rsponse.bomData1[i].id;
					 }
				}
			}
		if($scope.prodCostList[0].prod_cost_id !=undefined && $scope.prodCostList[0].prod_cost_id!=null)
		{
		for(var i=0;i<$scope.prodCostList.length;i++)
			{
			 if($scope.prodCostList[i].id == "" && $scope.prodCostList[i].prod_cost_id !="")
				 {
				 $scope.prodCostList[i].id=rsponse.proddata1[i].id;
				 }
			}
		}
	}

	$scope.funGetQueDet=function(id)
	{
		

		$http({
			method: 'GET',
			url : "getDataQue",
			params:{itemId:id},
			async:false,
		}).success(function (result) {
			
			$scope.QuetableDet=result.Quetable;
			$scope.QuetableDet=result.Quetable;
			$scope.Quetable={id:$scope.QuetableDet.id,dateTime:$scope.QuetableDet.publishing_date,syncNow:$scope.QuetableDet.sync_status,shopId:$scope.QuetableDet.shop_id,origin:$scope.QuetableDet.origin,curdAction:$scope.QuetableDet.crud_action,sysSaleFlag:""};
			if($scope.Quetable.id=="")
			{
			$scope.Quetable.syncNow=0;
			}
		
	
			
			
			
		});
	
	}
	
	
	
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		
			if(code_existing_validation($scope.formData,0)){
				$scope.allshopCheck=(companytype==0)?true:false;
				$scope.allshopCheck=($scope.formData.id!="" )?false:true;
				if($scope.formData.id==undefined )
					{
					$scope.allshopCheck=true;
					}
				
				
				if($scope.formData.id!="" && $scope.formData.id!=undefined )
					{
				$scope.funGetQueDet($scope.formData.id);
				$scope.Quetable.sysSaleFlag=$scope.itemDetails.sys_sale_flag;
				
					}
				if($scope.isLiteVersion == false)
					{
				     $("#myModal2").modal('toggle');
					}else
						{
						$scope.functionSaveData();
						}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				/*
				var date =get_date_format();
				$scope.formData.created_by=strings['userID'];
				$scope.formData.created_at=getMysqlFormat(date.mindate);
				
				$scope.formData.choiceIds="";

				func_set_choiceIds();
				$scope.formData.barcode=$scope.batchData.barCode;
				$scope.formData.isManufactured=parseInt($scope.formData.isManufactured);
				if($scope.formData.is_active==true){$scope.formData.is_active=0}else{$scope.formData.is_active=1};
				if($scope.formData.isSynchable==true){$scope.formData.isSynchable=1}else{$scope.formData.isSynchable=0};
				
				if($scope.formData.packContains=="")
					{
					$scope.formData.packContains=0;

					}
				if($scope.formData.shelfLife=="")
					{
					$scope.formData.shelfLife=0;
					}
			if($scope.formData.minStock=="")
				{
				$scope.formData.maxStock=0;
				}
			if($scope.formData.stdPurchaseQty=="")
				{
				$scope.formData.stdPurchaseQty=0;
				}
			if($scope.formData.leadTime==""){
				$scope.formData.leadTime=0;
			}
			
				$scope.formData.isGroupItem=0;
				
				
		
				
		           var file = $scope.myFile;
		       	   var fdata = new FormData();
		       	   var data = JSON.stringify({itemData:$scope.formData,bom:$scope.bomList,batchItem:$scope.batchData,file:$scope.myFile});
		       	fdata.append("imgFile",file);
		       	fdata.append("data",data);
		       	

		       	$http.post("saveItem",fdata,{
					transformRequest : angular.identity,
					headers : {
					'Content-Type' : undefined
					}
					}).success(function(response) {
					$scope.formData.isManufactured=$scope.formData.isManufactured.toString();
					if($scope.formData.is_active==1){$scope.formData.is_active=true}else{$scope.formData.is_active=false};
					if($scope.formData.isSynchable==1){$scope.formData.isSynchable=true}else{$scope.formData.isSynchable=false};

					if(response == "status:success"){
						
						if($scope.itmID!=0){
							$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
							
							view_mode_aftr_edit();	
							if($scope.formData.is_active==0){$scope.formData.is_active=true}else{$scope.formData.is_active=false};

						}else{
							$("#form_div_pref_supplier_id").find(".acontainer input").val("");
							clearform();
							fun_clear_form();
							$scope.fun_get_pono();
							$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
							$scope.chkItemType();
							$scope.bomList=[];
							$("#imgshw").empty();
							$scope.tab2_isdisabled=true;
							$scope.tab3_isdisabled=true;
							
						}
						if( $scope.selectedIndex != 0)
							{
							 $scope.selectedIndex = 0;
							}
						
						vm.dtInstance.reloadData();
						$("#bom_qty").css("border-color","#d2d6de");
					}
					else
					{
						$mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Save failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					}
				clearform();
				}).error(function(response) { // optional

					
						$mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Save failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					

				});*/
			}

			
		
	});

	$rootScope.$on("fun_discard_form",function(event){				//Discard function
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}
		).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).ok(
		'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			
		
			$("#form_div_pref_supplier_id").find(".acontainer input").val("");
			clearform();
			fun_clear_form();
			
			if($scope.itmID!=0){
				edit($scope.itmID,parseInt($(".btn_prev").attr("id").split("_")[1]));
				$scope.disable_all=true;
				$timeout(function () {				
					  $("#stockHead .acontainer input").attr('disabled',true);
					  $("#prodCost .acontainer input").attr('disabled',true);
			  	}, 1);
				$scope.disable_bom=true;
			}else{$scope.fun_get_pono();}
				

		}, function() {

		});

	
		
		
		});

	$rootScope.$on("fun_enable_inputs",function(){
		vm.dtInstance1.reloadData();
		$("#show_form").val(1);
		
		if(isLiteVersion==false)
			{
			
			$scope.disable_all=false;
			$("#form_div_pref_supplier_id").find(".acontainer input").attr("disabled", false);
			$(".acontainer input").attr("disabled", false);
			}else
				{
				$scope.disable_bom=false;
				}
			 $("#imgdiv a").removeClass("activeLink");
			 $("#div_table_bom").find(".acontainer input").attr("disabled", false);
			
			
		/*	$scope.pricesettings = {items: []};
			$scope.pricesettings.items.push({
				id:0,
				
				stock_item_id:'',
				stock_item_code:'',
				stock_item_name:'',
				qty:0,});*/
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});

	

	
	
	

	$rootScope.$on("fun_clear_form",function(){
		vm.dtInstance1.reloadData();
		$("#form_div_pref_supplier_id").find(".acontainer input").val("");
		clearform();
		fun_clear_form();
		$scope.fun_get_pono();
		$("#div_table_bom").find(".acontainer input").attr("disabled", true);
		$scope.selectedIndex=0;
		
		$scope.pricesettings = {items: []};
		$scope.pricesettings.items.push({
			id:0,
			
			stock_item_id:'',
			stock_item_code:'',
			stock_item_name:'',
			qty:0,});
	});


	
	
	

	function fun_clear_form(){
		$scope.allshopCheck=true;
		 $("#imgdiv a").removeClass("activeLink");
		// $scope.syncNow=0;
		// $scope.sysSaleFlag=true;
	
		// $scope.dateTime="";
		disable = 0;
		$scope.selectedIndex = 0;
		$scope.Quetable={id:"",dateTime:"",sysSaleFlag:true,syncNow:0,shopId:"",origin:"",curdAction:""};
	
		$scope.formData = {is_valid:0,is_whls_price_pc:0,whls_price:0,is_barcode_print:0,pack_uom_id:$scope.filluompack[0].id,name:"",code:"",description:"",itemCategoryId:0,kitchenId:0,isBatch:0,profitCategoryId:0,isManufactured:"0",isSellable:0,taxationBasedOn:"0",uomId:$scope.filluom[0].id,movementMethod:0,valuationMethod:"0",isComboItem:0,isOpen:0,isRequireWeighing:0,subClassId:"",
				is_active:false,isSynchable:false,packContains:0,shelfLife:0,minStock:0,maxStock:0,stdPurchaseQty:0,leadTime:0,bomQty:0,inputTaxId:"",alternativeName:"",nameToPrint:"",alternativeNameToPrint:"",
				fgColor:'#ffffff',bgColor:'#ffffff',itemThumb:"",outputTaxId:"",outputTaxIdHomeService:"",outputTaxIdTableService:"",outputTaxIdTakeAwayService:"",sysSaleFlag:""};
		$scope.batchData={};
		$("#form_div_pref_supplier_id").find(".acontainer input").val("");
		$scope.hide_code_existing_er = true;
		$scope.choicesItem=[];
		
		$("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');
		 $("#imgshw").empty();
		
		/* $("span.fileupload-new").css('display', 'inline');
	     $("span.fileupload-exists").css('display', 'none');
	 $("a.btn.btn-danger.fileupload-exists").css('display', 'none');*/
		
		
	}


	//Manupulate Formdata when Edit mode - Prev-Next feature add
	$rootScope.$on("fun_next_rowData",function(e,id){
     $scope.bttn="next";
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(current_row_index == 0){
			$rootScope.$emit("enable_prev_btn");
		}
		/*if($scope.ppHint==1)
			{
			var next_row_index=current_row_index+2;
			}
		else
			{
			var next_row_index = current_row_index+1;

			}*/
		var next_row_index = current_row_index+1;

		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(next_row_index).data();
			edit(selcted_row_data[0].id,next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}
		if(row_count-1 == current_row_index+1){
			$rootScope.$emit("disable_next_btn");
		}
	});

	$rootScope.$on("fun_prev_rowData",function(e,id){
		 $scope.bttn="prev";
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		/*if($scope.ppHint==1)
		{
		var prev_row_index=current_row_index-2;
		}
		else
			{
			var prev_row_index = current_row_index-1;
			}*/
		var prev_row_index = current_row_index-1;

		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(prev_row_index).data();
			edit(selcted_row_data[0].id,prev_row_index);
			$rootScope.$emit("next_formdata_set",prev_row_index);
		}
		if(current_row_index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}
	});



	$scope.disable_search_text=function(elemenvalue){
		if($scope.disable_all==true){
			$(elemenvalue).attr("disabled", true);
		}if($scope.disable_bom ==true)
			{
			$timeout(function () {				
				  $("#stockHead .acontainer input").attr('disabled',true);
				  $("#prodCost .acontainer input").attr('disabled',true);
		  	}, 1);
			}
	}

	$scope.alert_for_codeexisting = function(flg){

		if(flg == 1){
			$("#table_validation_alert").show();
			$("#table_validation_alert").addClass("in");
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}else {
			$("#table_validation_alert").hide();
			$("#table_validation_alert").removeClass("in");
		}

	}


	$scope.isCodeExistis1 = function(code){
		$http({
			url : 'codeexisting',method : "GET",params : {code:code}
		}).then(
				function(response) {
					if (response.data == 1) {
						$scope.hide_code_existing_er1 = false;
						$scope.existing_code1 = '"' + code + '" Existing';
						
					}else if(response.data == 0){
						$scope.hide_code_existing_er1 = true;
					}
				}, function(response) { // optional
				});
	}


	// Validation 
	
	
	function dateTimeValidation(){
		var flg = true;
		if($scope.Quetable.syncNow==1)
			{
			if($scope.Quetable.dateTime=="" || $scope.Quetable.dateTime==undefined)
				{
				 $("#dateTime").find("input").css("border-color","red");
				 flg=false;
				}
			else
				{
				$("#dateTime").find("input").css("border-color","#d2d6de");
				
				}
			}
	return flg;	
	}
	
	
	
	

	function code_existing_validation(data,formFlag){
		var flg = true;
		
		
		
		
		
		
		if(formFlag==1){
			
			if($scope.formData1.id == undefined || $scope.formData1.id == ""){
				
				
				if (!$scope.hide_code_existing_er1) {
					flg = false;
					$("#code_modal").select();
				}
			}
			
			
			
			$("#form_div_code_modal").removeClass("has-error");
			$("#form_div_code_modal_error").hide();
			$("#form_div_name_modal").removeClass("has-error");
			$("#form_div_name_modal_error").hide();
			if(data.code== undefined || data.code==""){
				flg = false;
				$("#form_div_code_modal").addClass("has-error");
				$("#form_div_code_modal_error").show();
			} if(data.name== undefined || data.name==""){
				$("#form_div_name_modal").addClass("has-error");
				$("#form_div_name_modal_error").show();
				flg = false;	
			}
		}
		
		else{
			
					var row_data = vm.dtInstance.DataTable.rows().data();
			if(data.id == undefined || data.id == ""){
				if (!$scope.hide_code_existing_er) {
					flg = false;
					$("#code").select();
				}
			}
			
			if(form_validation(data) == false){
				flg = false;
			}
		}
		
		
		

	
		

		return flg;
	}

	function form_validation(data){
		var flg = true;
		
		if($scope.isLiteVersion == false)
		{
		if(validation() == false){

			flg = false;
		}
		
		 $("#imgshw").css("border-color","#d2d6de");
		
		
		 if($scope.formData.itemCategoryId == undefined || $scope.formData.itemCategoryId == 0){
			$("#form_div_item_category_id").addClass("has-error");
			$("#form_div_item_category_id_error").show();
			$scope.selectedIndex = 0;
			flg = false;
		}
		if($scope.formData.isManufactured != 0 && $scope.formData.isManufactured != 1){
			$("#form_div_is_manufactured").addClass("has-error");
			$("#form_div_is_manufactured_error").show();
			flg = false;
			$scope.selectedIndex = 0;


		}


		 if($scope.formData.uomId == undefined || $scope.formData.uomId == 0){
			$("#form_div_uom_id").addClass("has-error");
			$("#form_div_uom_id_error").show();
			flg = false;
			$scope.selectedIndex = 0;

		}

		 if($scope.formData.valuationMethod == undefined || $scope.formData.valuationMethod == ""){
			$("#form_div_valuation_method").addClass("has-error");
			$("#form_div_valuation_method_error").show();
			flg = false;
			$scope.selectedIndex = 0;

		}
		 if( $scope.myFile!=undefined)
			 {
		 if($scope.myFile.size>500*1024)
			 {
			 $("#imgshw").css("border-color","red");
				$rootScope.$broadcast('on_AlertMessage_ERR','Select image size less than 500kb');
				flg = false;
			 }

			 }
	}
      if($scope.formData.isManufactured==1 && flg!=false){
    	  
			if(!bomValidation()){
				$scope.selectedIndex = 1;
				flg=false;
				
			}
			else if(!prodCostValidation()){
				$scope.selectedIndex = 1;
				flg=false;
			}
			if($scope.bomList.length == 0){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);
				
				
				flg = false;
				$scope.selectedIndex = 1;
			}
			
		}
	
	 if($scope.formData.isSellable==1 && flg!=false && $scope.isLiteVersion==false) {
			if(!saleItemValidation()){
				 if($scope.formData.itemCategoryId != 0 && $scope.formData.uomId != 0 && $scope.formData.valuationMethod != ""){
						$scope.selectedIndex = 2;
					}
				flg=false;
			}
		}
		
	/*	else if(flg==false)
		{
			focus();
		}
*/
		return flg;
	}
	
	function saleItemValidation()
	{
		var flg = true;
		
		
		
	
		if($scope.formData.taxCalculationMethod == undefined || $scope.formData.taxCalculationMethod == ""){
			$("#form_div_taxation_method").addClass("has-error");
			$("#form_div_taxation_method_error").show();
			flg = false;
		}else{
			$("#form_div_taxation_method").removeClass("has-error");
			$("#form_div_taxation_method_error").hide();
		}
		
	 if($scope.formData.taxationBasedOn==0)
	
	{	
		if($scope.formData.outputTaxId == undefined || $scope.formData.outputTaxId == ""){
			$("#form_div_tax").addClass("has-error");
			$("#form_div_tax_error").show();
			flg = false;
		}else{
			$("#form_div_tax").removeClass("has-error");
			$("#form_div_tax_error").hide();
		}
		
	}
	 else if($scope.formData.taxationBasedOn==1)
		 {
		if($scope.formData.outputTaxIdHomeService == undefined || $scope.formData.outputTaxIdHomeService == ""){
			$("#form_div_tax_id_home_service").addClass("has-error");
			$("#form_div_tax_id_home_service_error").show();
			flg = false;
		}else{
			$("#form_div_tax_id_home_service").removeClass("has-error");
			$("#form_div_tax_id_home_service_service_error").hide();
		}
		if($scope.formData.outputTaxIdTableService == undefined || $scope.formData.outputTaxIdTableService == ""){
			$("#form_div_tax_id_table_service").addClass("has-error");
			$("#form_div_tax_id_table_service_error").show();
			flg = false;
		}else{
			$("#form_div_tax_id_table_service").removeClass("has-error");
			$("#form_div_tax_id_table_service_error").hide();
		}
		
		if($scope.formData.outputTaxIdTakeAwayService == undefined || $scope.formData.outputTaxIdTakeAwayService == ""){
			$("#form_div_tax_id_take_away_service").addClass("has-error");
			$("#form_div_tax_id_take_away_service_error").show();
			flg = false;
		}else{
			$("#form_div_tax_id_take_away_service").removeClass("has-error");
			$("#form_div_tax_id_take_away_service_error").hide();
		}
		
	}	
	
	
	 
		return flg;
	}
	
	function setSupplier(id){
		for(var i=0;i<$scope.supDataList.length;i++){
			if($scope.supDataList[i].id == id){
				$("#form_div_pref_supplier_id").find(".acontainer input").val($scope.supDataList[i].code);
				if($scope.disable_all==true){
					$("#form_div_pref_supplier_id").find(".acontainer input").attr("disabled", true);
				}
				$scope.prefSupplierName=$scope.supDataList[i].name;	
				break;
			}
		}
	}

	/*function setSubclass(id){
		for(var i=0;i<$scope.stkdata.length;i++){
			if($scope.stkdata[i].id == id){
				$("#form_div_sub_class_id").find(".acontainer input").val($scope.stkdata[i].code);
				if($scope.disable_all==true){
					$("#form_div_sub_class_id").find(".acontainer input").attr("disabled", true);
				}
				$scope.subClassName=$scope.stkdata[i].name;	
				break;
			}
		}
	}*/

	$(document).on('keyup','#form_div_pref_supplier_id .acontainer input', function(event) {
		$('#form_div_pref_supplier_id #pref_supplier_id,#prefSupplierName').val('');
		$scope.formData.prefSupplierId="";
		$scope.prefSupplierName="";
	});
	
	
	  $scope.clear_stock_details_editmode =  function(event){
		    $scope.bomList[event.currentTarget.parentElement.rowIndex-1].id = "";
			$scope.bomList[event.currentTarget.parentElement.rowIndex-1].bom_item_id = "";
			$scope.bomList[event.currentTarget.parentElement.rowIndex-1].bom_item_code = "";
			$scope.bomList[event.currentTarget.parentElement.rowIndex-1].bom_item_name = "";
			$scope.bomList[event.currentTarget.parentElement.rowIndex-1].qty ="";
			$scope.bomList[event.currentTarget.parentElement.rowIndex-1].uomcode ="";
			$scope.bomList[event.currentTarget.parentElement.rowIndex-1].unit_price ="";	
  }
	  $scope.clear_cost_details_editmode =  function(event){
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].id = "";
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_id = "";
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_code = "";
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_name = "";
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_type ="";
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].isPercentage =false;
		    $scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].rate ="";	
}
	  
	  //Advanced Search
	 $timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 
	    
		$rootScope.$on("advSearch",function(event){
			DataObj.adnlFilters=[{}];
			$('#SearchText').text("");
			vm.dtInstance.DataTable.search('').draw();	
			$scope.itmCode=$('#itmCode').val();
			$scope.itmName=$('#itmName').val();
			$scope.itmType=$("#itemtype").val();
			$scope.itm_cat_name=$('#itm_category_id').find(":selected").text();
			
			if($scope.itm_cat_name == "select" || $scope.itm_cat_name == undefined)
				{
				$scope.itm_cat_name="";
				}
			if($scope.itmType != "")
			{
		      var selectedItemTyp=$('#itemtype').find(":selected").text();
			}
			 $scope.searchTxtItms={1:$scope.itmCode,2:$scope.itmName,3:$scope.itm_cat_name,4:selectedItemTyp};
			 for (var key in $scope.searchTxtItms) {
		    	   
		    	   if ($scope.searchTxtItms.hasOwnProperty(key)) {
		    		   if($scope.searchTxtItms[key] !=null && $scope.searchTxtItms[key] !=undefined && $scope.searchTxtItms[key] !="")
		    			   {
		    		
		    				angular.element(document.getElementById('SearchText')).append($compile("<div id="+key+"  class='advseacrh '  contenteditable='false'>"+$scope.searchTxtItms[key]+"<span class='close-thik' contenteditable='false'  ng-click='deleteOptn("+key+"); '></span></div>")($scope))
		    				$scope.deleteOptn = function (key) {
		                   // alert(key);
		    					delete $scope.searchTxtItms[key];	
		                        $('#'+key).remove();
		                       switch(key)
		                       {
		                       case 1:
		                    	   $scope.itmCode="";
		                    	   $('#itmCode').val("")
		                    	   break;
		                       case 2:
		                    	   $scope.itmName="";
		                    	   $('#itmName').val("");
		                           break;
		                      case 3:
		                    	  $scope.itm_cat_name="";
		                    	  $("#itm_category_id").val('');
		                           break;
		                      case 4:
		                    	  $scope.itmType="";
		                    	  selectedItemTyp="";
		                    	  $("#itemtype").val('');
		                       }
		                       DataObj.adnlFilters=[{col:1,filters:$scope.itmCode},{col:2,filters:$scope.itmName},{col:3,filters:$scope.itm_cat_name},{col:4,filters:$scope.itmType}];
		                       vm.dtInstance.reloadData(); 
		                      
		                     };
		    			   
			   				
		    			   }
		    	   }
		    	 }
			 
			
			 DataObj.adnlFilters=[{col:1,filters:$scope.itmCode},{col:2,filters:$scope.itmName},{col:3,filters:$scope.itm_cat_name},{col:4,filters:$scope.itmType}];
				vm.dtInstance.reloadData();
				 $scope.searchTxtItms={};
				 
		});
		
		
	$rootScope.$on("Search",function(event){
		DataObj.adnlFilters=[{}];
		$scope.searchTxtItms={};
	/*	vm.dtInstance.reloadData();*/
			vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	

	});


	$("#clear").click(function(){
		DataObj.adnlFilters=[{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
		$scope.searchTxtItms={};
		
	});

	DataObj.adnlFilters=[{col:1,filters:$scope.itmCode},{col:2,filters:$scope.itmName},{col:3,filters:$scope.itm_cat_name},{col:4,filters:$scope.itmType}];

	
	
	  
	 /* var super_list=[];
		$scope.superClassList=[{id:-1,name:"select"}];
		$scope.superClassList=[{id:0,name:"select"}];
		$http({
			method: 'GET',
			async: false,
			url : "../itemclass/superclassList",
			data: { applicationId: 1 }
		}).success(function (result) {
			super_list = result.data;
			$scope.suprList = super_list;
			for(var i=0; i<result.data.length;i++){
				$scope.superClassList.push(result.data[i]);

			}
			
		});
		if($scope.superClassList[0].id != 0 && $scope.superClassList[0].id !=undefined && $scope.superClassList[0].id != null)
			{
		$scope.formData.subClassId = $scope.superClassList[0].id; 
			}*/

	
	var prodcost_data = [];
	$scope.prodcost_data=[];
	
	$http({	url : '../prodcost/costTypeList',	method : "GET",async:false,
	}).then(function(response) {
		prodcost_data = response.data.data;
		$scope.prodcost_data=response.data.data;
		
	}, function(response) { // optional
	});

}




$(document).on('keyup','#form_div_supplier_code input',function(e){
	if( e.which==13){
		$("#supplier_doc_no").focus();
	}
	if(e.which != 9 && e.which != 13){
		$scope.$apply(function(){
			$scope.formData.supplierId =  "";
			$scope.formData.supplierCode =  "";
			$scope.formData.supplierName = "";
		});
	}
});



mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	  return {
	    controller: function ($scope,$http) {
	    	$scope.currentIndex = 0;
	    	 $("#stockHead tr td").keyup('input',function(e){
					$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

	    		 if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
	     			if(e.currentTarget.cellIndex == 1){
	     				$scope.$apply(function(){
	     					$scope.clear_stock_details_editmode(e);
	     					$scope.alert_for_codeexisting(0);
	     				});
	     			}
	     		}else if(e.which == 13 ){
	     			if(e.currentTarget.cellIndex == 1){
	     				if($scope.bomList[$scope.bomList.length-1].bom_item_id!=""){
	     				{ $("#stockHead tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td:nth-child("+(4)+")").find("#qty").select();}
	     		}}
				}else if(e.which == 9 ){
	     			if(e.currentTarget.cellIndex == 4){
	     			
	     					
	     				{$("#stockHead tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

	     					
	     					/*$("#items_table tbody tr:nth-child("+e.currentTarget.cellIndex+") td").find(".acontainer input").focus();*/}
	     		}
				}
				/*if(e.which == 9){
	     			if(e.currentTarget.cellIndex == 3){
	     				$scope.$apply(function(){
	     					 $("#items_table tbody tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td").find("#po_qty").select();
	     				});
	     			}
	     		}*/
	     	});
	    	 
	    	 $scope.itemsData = $scope.stkdata;
	    	 $scope.element = [];
		     $scope.table_itemsearch_rowindex=0;
		     $scope.tableClicked = function (index) {
		     $scope.table_itemsearch_rowindex= index;
		    };
	    	    return $scope;
	        },
	    link: function(scope, elem, attrs ,controller) {
	    	var strl_scope = controller;
	    	var items = $(elem).tautocomplete({
	    		 
	    		columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname','unit_price'],
	    		hide: [false,true,true,false,false,false,false,false,false,false,false,false,false],
	    		/*columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname'],
				hide: [false,true,true,false,false,false,false,false,true,false],*/
	    		placeholder: "search ..",
	    		highlight: "hightlight-classname",
	    		norecord: "No Records Found",
	    		delay : 10,
	    		onchange: function () {
	    			var selected_item_data =  items.all();
	    		strl_scope.$apply(function(){
	    			var count =0;
	    			for(var i=0;i<strl_scope.bomList.length;i++){
	    				if(!strl_scope.bomList[i].isDeleted){
	    					if(selected_item_data.id != ""){
		    					if(i != strl_scope.currentIndex){
		    							if(selected_item_data.id == strl_scope.bomList[i].bom_item_id){
					    					count=1;
					    				}
		    					}
		    				}strl_scope
	    				}
	    			}
		    			if(count != 1){
		    				 strl_scope.bomList[strl_scope.currentIndex].bom_item_id = selected_item_data.id;
		    				 strl_scope.bomList[strl_scope.currentIndex].bom_item_code = selected_item_data.code;
		    				 strl_scope.bomList[strl_scope.currentIndex].bom_item_name = selected_item_data.name;
		    	
		    				 strl_scope.bomList[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
		    				 strl_scope.bomList[strl_scope.currentIndex].unit_price=parseFloat(selected_item_data.unit_price).toFixed(settings['decimalPlace']);
		    				/* strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
		    				*/ 
		    				 
		    				 $timeout(function () { $("#stockHead tbody tr:nth-child("+strl_scope.currentIndex+1+") td").find("#qty").focus();}, 1); 

		    				 strl_scope.alert_for_codeexisting(0);
		    				 $("#stockHead tbody tr:nth-child("+strl_scope.table_itemsearch_rowindex+1+") td").find("#po_qty").focus();
		    			}else{
		    				 elem[0].parentNode.lastChild.value="";
		    				 strl_scope.bomList[strl_scope.currentIndex].bom_item_id = "";
		    				 strl_scope.bomList[strl_scope.currentIndex].bom_item_code = "";
		    				 strl_scope.bomList[strl_scope.currentIndex].bom_item_name = "";
		    				 strl_scope.bomList[strl_scope.currentIndex].uomcode = "";
		    				 strl_scope.alert_for_codeexisting(1);
		    			}
	    				 
	    			});
	    		},
	    		data: function () {
	    		
	    			var data = strl_scope.itemsData;
	    	
	    		    var filterData = [];
	    		    var searchData = eval("/^" + items.searchdata() + "/gi");
	    		    $.each(data, function (i, v) {
	    		        if ( v.name.search(new RegExp(searchData)) != -1) {
	    		        		filterData.push(v);
	    		        }
	    		    });
	    		    
	    		    return filterData;
	    		}
	    });
	    	
	    	for(var i = 0;i<strl_scope.bomList.length;i++){
				if(strl_scope.formData.id!=undefined && strl_scope.formData.id!='' ){
					if(strl_scope.bomList[i].isSet == false){
						elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.bomList[i].isSet=true;break;
						
					}
				}else{
					if(strl_scope.bomList[i].isSet == false){
						if(strl_scope.bomList[i].po_no != undefined && strl_scope.bomList[i].po_no != ""){
							elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
							strl_scope.bomList[i].isSet=true;break;
						}
					}
				}
			}
	    	$timeout(function () { $("#stockHead tr:nth-child("+(strl_scope.bomList.length)+") td:nth-child("+(2)+")").find(".acontainer input").select();
	    	}, 1); 
	    }
	  };
	}]);


mrpApp.directive('tableProductioncost', ['$timeout',function($timeout) {
	  return {
	    controller: function ($scope,$http) {
	    	$scope.currentIndex1 = 0;
	    	 $("#prodCost tr td").keyup('input',function(e){
					$scope.currentIndex1 = e.currentTarget.parentElement.rowIndex-1;

	    		 if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
	     			if(e.currentTarget.cellIndex == 1){
	     				$scope.$apply(function(){
	     					$scope.clear_cost_details_editmode(e);
	     					$scope.alert_for_codeexisting(0);
	     				});
	     			}
	     		}else if(e.which == 13 ){
	     			if(e.currentTarget.cellIndex == 1){
	     				if($scope.prodCostList[$scope.prodCostList.length-1].prod_cost_id!=""){
	     				{ $("#prodCost tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td:nth-child("+(5)+")").find("#cost_rate").select();}
	     		}}
				}else if(e.which == 9 ){
	     			if(e.currentTarget.cellIndex == 1){				
	     				{$("#prodCost tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
          				/*$("#items_table tbody tr:nth-child("+e.currentTarget.cellIndex+") td").find(".acontainer input").focus();*/}
	     		}
				}
				
	     	});
	    	 
	    	
	    	 $scope.element = [];
		     $scope.table_itemsearch_rowindex1=0;
		     $scope.tableClicked1 = function (index) {
		     $scope.table_itemsearch_rowindex1= index;
		    };
	    	    return $scope;
	        },
	    link: function(scope, elem, attrs ,controller) {
	    	var strl_scope = controller;
	    	var items = $(elem).tautocomplete({
	    		 
	    		columns: ['id' , 'code', 'name','cost_type_name'],
	    		hide: [false,true,true,false],
	    		
	    		placeholder: "search ..",
	    		highlight: "hightlight-classname",
	    		norecord: "No Records Found",
	    		delay : 10,
	    		onchange: function () {
	    			var selected_cost_data =  items.all();
	    		strl_scope.$apply(function(){
	    			var count =0;
	    			for(var i=0;i<strl_scope.prodCostList.length;i++){
	    				if(!strl_scope.prodCostList[i].isDeleted){
	    					if(selected_cost_data.id != ""){
		    					if(i != strl_scope.currentIndex1){
		    							if(selected_cost_data.id == strl_scope.prodCostList[i].prod_cost_id){
					    					count=1;
					    				}
		    					}
		    				}strl_scope
	    				}
	    			}
		    			if(count != 1){

							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id=selected_cost_data.id;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code =  selected_cost_data.code;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name=  selected_cost_data.name;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type=  selected_cost_data.cost_type_name;
		    				/* strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
		    				*/ 

		    				 strl_scope.alert_for_codeexisting(0);
		    				 $timeout(function () { $("#prodCost tbody tr:nth-child("+strl_scope.currentIndex1+1+") td").find("#cost_rate").focus();}, 1); 
		    			}else{
		    				 elem[0].parentNode.lastChild.value="";
		    				 strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id = "";
		    				 strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code = "";
		    				 strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name = "";
		    				 strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type = "";
		    				 
		    				 strl_scope.alert_for_codeexisting(1);
		    			}
	    				 
	    			});
	    		},
	    		data: function () {
	    		
	    			var data = strl_scope.prodcost_data;
	    	
	    		    var filterData = [];
	    		    var searchData = eval("/^" + items.searchdata() + "/gi");
	    		    $.each(data, function (i, v) {
	    		        if ( v.name.search(new RegExp(searchData)) != -1) {
	    		        		filterData.push(v);
	    		        }
	    		    });
	    		    
	    		    return filterData;
	    		}
	    });
	    	
	    	for(var i = 0;i<strl_scope.prodCostList.length;i++){
				if(strl_scope.formData.id!=undefined && strl_scope.formData.id!='' ){
					if(strl_scope.prodCostList[i].isSet == false){
						elem[0].parentNode.lastChild.value = strl_scope.prodCostList[i].prod_cost_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.prodCostList[i].isSet=true;break;
						
					}
				}else{
					if(strl_scope.prodCostList[i].isSet == false){
						if(strl_scope.prodCostList[i].po_no != undefined && strl_scope.prodCostList[i].po_no != ""){
							elem[0].parentNode.lastChild.value = strl_scope.prodCostList[i].prod_cost_code;
							strl_scope.prodCostList[i].isSet=true;break;
						}
					}
				}
			}
	    	$timeout(function () { $("#prodCost tr:nth-child("+(strl_scope.prodCostList.length)+") td:nth-child("+(2)+")").find(".acontainer input").select();
	    	}, 1); 
	    }
	  };
	}]);














mrpApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);







//Clear validation Addons
function clearform() {
	$(".required").each(function(){
		var filedId = $(this).attr("id");
		$("#form_div_"+filedId+"").removeClass("has-error");
		$("#form_div_"+filedId+"_error").hide();
	});
	$("#table_validation_alert").removeClass("in");
	$("#form_div_pref_supplier_id").css("border-color","#d2d6de");
	$("#form_div_pref_supplier_id,#form_div_item_category_id,#form_div_is_manufactured,#form_div_uom_id,#form_div_department_id,#form_div_movement_method,#form_div_valuation_method,#form_div_fg_color,#form_div_bg_color").removeClass("has-error");
	$("#form_div_pref_supplier_id_error,#form_div_item_category_id_error,#form_div_is_manufactured_error,#form_div_uom_id_error,#form_div_department_id_error,#form_div_movement_method_error,#form_div_valuation_method_error,#form_div_fg_color_error,#form_div_bg_color_error").hide();

	
	$("#form_div_item_category_id").removeClass("has-error");
	$("#form_div_item_category_id_error").hide();
	$("#form_div_is_manufactured").removeClass("has-error");
	$("#form_div_is_manufactured_error").hide();
	$("#form_div_uom_id").removeClass("has-error");
	$("#form_div_uom_id_error").hide();
	$("#form_div_valuation_method").removeClass("has-error");
	$("#form_div_valuation_method_error").hide();
	$("#form_div_taxation_method").removeClass("has-error");
	$("#form_div_taxation_method_error").hide();
	$("#form_div_tax").removeClass("has-error");
	$("#form_div_tax_error").hide();
	$("#form_div_tax_id_home_service").removeClass("has-error");
	$("#form_div_tax_id_home_service_error").hide();
	$("#form_div_tax_id_table_service").removeClass("has-error");
	$("#form_div_tax_id_table_service_error").hide();
	$("#form_div_tax_id_take_away_service").removeClass("has-error");
	$("#form_div_tax_id_take_away_service_error").hide();
	
	
	$("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');
	 $("#imgdiv a").removeClass("activeLink");
	 $("#imgshw").empty();
	 $("#imgshw").css("border-color","#d2d6de");

	/*$("span.fileupload-new").css('display', 'inline');
   $("span.fileupload-exists").css('display', 'none');
   $("a.btn.btn-danger.fileupload-exists").css('display', 'none');*/
	
	}


