mrpApp.controller('items_list', items_list);

function items_list($controller,$compile,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,FORM_MESSAGES,
		DTColumnBuilder, $q, $window , MRP_CONSTANT,DATATABLE_CONSTANT)
{
	$controller('DatatableController', {$scope: $scope});
	
	/*set_sub_menu("#settings");		
	setMenuSelected("#item_master_list_left_menu");	*/		//active leftmenu
	manageButtons("add");
	
	$scope.formData = {};
	$scope.show_table=true;	
	$scope.first=10;
	$scope.selectionData=[];
	$scope.isLoading = true;
	$scope.ItemCtgry=[];
	
	var vm = this;
	vm.dtInstance = {};
	vm.selected={};
	vm.synchableSelect={};
	vm.selectAll=false;
    vm.synchableSelectAll=false;
    vm.selectedBoxes={};
    vm.selectedData={};
    vm.reloadData = reloadData;

    var isActive=true;
    var isSynchable=true;
    var is_active_title = '<input ng-model="showCase.selectAll" ng-click="toggleAll(showCase.selectAll,showCase.selected)" type="checkbox">ACTIVE</input>';
	var is_synchable_title='<input ng-model="showCase.synchableSelectAll" ng-click="synchabletoggleAll(showCase.synchableSelectAll, showCase.synchableSelect)" type="checkbox">Set Selling Items</input>';
    
	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj)
	.withOption('createdRow', function(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive to the DT
            $compile(angular.element(row).contents())($scope);
        })
    .withOption('headerCallback', function(header) {
            if (!vm.headerCompiled) {
                // Use this headerCompiled field to only compile header once
                vm.headerCompiled = true;
                $compile(angular.element(header).contents())($scope);
            }
        })
	
	/*.withPaginationType('page_search').withOption('rowCallback', rowCallback).withOption(
			'language',
			{
				"paginate"          : {
					"previous" : '<i class="fa fa-angle-left"></i>',
					"next"     : '<i class="fa fa-angle-right"></i>'
					"search_page":'Go',
					"first"	: '<i class="fa fa-angle-double-left"></i>',
					"last"	: '<i class="fa fa-angle-double-right"></i>'
				}
			})*/;				
    vm.dtColumns = [DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('width','200px'),
	                DTColumnBuilder.newColumn('name').withTitle('NAME').withOption('width','200px'),
	                DTColumnBuilder.newColumn('item_category_name').withTitle('ITEM CATEGORY').withOption('width','200px'),	
	    			DTColumnBuilder.newColumn(null).withTitle(is_active_title).notSortable()
		    	            .renderWith(function(data, type, full, meta) {
		    	            	if(parseInt(full.is_active)==0){isActive=false;}else{isActive=true;}
		    	                vm.selected[full.id] = isActive;
		    	                $scope.toggleOne(vm.selected);
		    	                return '<input ng-model="showCase.selected[' + full.id + ']"  ng-click="toggleOne(showCase.selected)" type="checkbox">';
		    	            }).withOption('width','100px'),
		    	    DTColumnBuilder.newColumn(null).withTitle(is_synchable_title).notSortable()
		    	            .renderWith(function(data, type, full, meta) {
		    	            	if(parseInt(full.sys_sale_flag)==0){isSynchable=false;}else{isSynchable=true;}
		    	                vm.synchableSelect[full.id] = isSynchable;
		    	                $scope.synchabletoggleOne(vm.synchableSelect)
		    	                return '<input ng-model="showCase.synchableSelect[' + full.id + ']" ng-click="synchabletoggleOne(showCase.synchableSelect)" type="checkbox">';
		    	            }),
	    	            
		    	    ];
    
	
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		
	}
	
	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});
	
	function reloadData() {
		vm.dtInstance.reloadData(null, true);
		

	}
	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		$scope.start = start;
		var api = this.api();
	    var pageInfo = api.page.info();
	    if(pageInfo.pages == 0){
	    	return pageInfo.page +" / "+pageInfo.pages;
	    }else{
	    	return pageInfo.page+1 +" / "+pageInfo.pages;
	    }
	}
	
	
	$scope.toggleAll =function (selectAll, selectedItems) {
        for (var id in selectedItems) {
            if (selectedItems.hasOwnProperty(id)) 
            {
                selectedItems[id] = selectAll;
            }
        }
    }
	
	
	$scope.toggleOne=function(selectedItems) {
		//console.log(selectedItems);
        for (var id in selectedItems) {
            if (selectedItems.hasOwnProperty(id)) {
                if(!selectedItems[id]) {
                	/*$scope.selectionData.push(id);*/
                    vm.selectAll = false;
                    return;
                }
            }
        }
        vm.selectAll = true;
	}
	
	
	$scope.synchabletoggleAll =function(synchableSelectAll, selectedItemsSynch) {
        for (var id in selectedItemsSynch) {
            if (selectedItemsSynch.hasOwnProperty(id)) {
                selectedItemsSynch[id] = synchableSelectAll;
            }
        }
    }
	
	
	$scope.synchabletoggleOne=function(selectedItemsSynch) {
        for (var id in selectedItemsSynch) {
            if (selectedItemsSynch.hasOwnProperty(id)) {
                if(!selectedItemsSynch[id]) {
                    vm.synchableSelectAll = false;
                    return;
                }
            }
        }
        vm.synchableSelectAll = true;
	}
	
	
	
    $scope.updateIsActiveAndSync =function() {
		
	    $scope.activeSelected=[];
	    $scope.inactiveSelected=[];
		$scope.syncSelected=[];
		$scope.nonSyncSelected=[];
		for (var id in vm.synchableSelect) {
	        if (vm.synchableSelect.hasOwnProperty(id)) {
	            if(vm.synchableSelect[id]) {
	            	$scope.syncSelected.push(id);
	            }
	            else if(!vm.synchableSelect[id])
	            	{
	            	$scope.nonSyncSelected.push(id);
	            	}
	        }
	    }
		
		for (var id in vm.selected) {
	        if (vm.selected.hasOwnProperty(id)) {
	            if(vm.selected[id]) {
	            	$scope.activeSelected.push(id);
	            }
	            else if(!vm.selected[id])
	            $scope.inactiveSelected.push(id);
	        }
	    }
	 		
		var data = $.param({
		syncData:JSON.stringify($scope.syncSelected),
		nonSyncData:JSON.stringify($scope.nonSyncSelected),
		activeData:JSON.stringify($scope.activeSelected),
		inactiveData:JSON.stringify($scope.inactiveSelected)
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
		$http.post('updateIsActiveAndSync', data, config)
		.success(function (data, status, headers, config) {
			if(data == 1)
			{
			$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
			} else
				{
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Update failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#dialogContainer')))
					.clickOutsideToClose(true)
					.textContent("Update failed.")
					.ok('Ok!')
					.targetEvent(event)
			);
		});
					
			        
		    }

    //Advanced Search
  $timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 
    
	$rootScope.$on("advSearch",function(event){
		DataObj.adnlFilters=[{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search('').draw();	
		$scope.itmCode=$('#itmCode').val();
		$scope.itmName=$('#itmName').val();
		$scope.itm_cat_name=$('#itm_category_id').find(":selected").text();
		if($scope.itm_cat_name == "select" || $scope.itm_cat_name == undefined)
		{
		$scope.itm_cat_name="";
		}
		
		 $scope.searchTxtItms={1:$scope.itmCode,2:$scope.itmName,3:$scope.itm_cat_name};
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
	                    	  $('#itm_category_id').val(''); 	
	                    	    break;
	                    	 
	                       }
	                       DataObj.adnlFilters=[{col:1,filters:$scope.itmCode},{col:2,filters:$scope.itmName},{col:3,filters:$scope.itm_cat_name}];
	                       vm.dtInstance.reloadData(); 
	                      
	                     };
	    			   
		   				
	    			   }
	    	   }
	    	 }
		 
		 DataObj.adnlFilters=[{col:1,filters:$scope.itmCode},{col:2,filters:$scope.itmName},{col:3,filters:$scope.itm_cat_name}];
			vm.dtInstance.reloadData();
			 $scope.searchTxtItms={};	
	});
	
	
$rootScope.$on("Search",function(event){
	DataObj.adnlFilters=[{}];
	$scope.searchTxtItms={};
/*	vm.dtInstance.reloadData();
*/		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	

});


$("#clear").click(function(){
	DataObj.adnlFilters=[{}];
	$('#SearchText').text("");
	vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
	$scope.searchTxtItms={};
});

DataObj.adnlFilters=[{col:1,filters:$scope.itmCode},{col:2,filters:$scope.itmName},{col:3,filters:$scope.itm_cat_name}];


$http({
	method: 'GET',
	async: false,
	url : "../itemcategory/jsonDropdown",
	data: { applicationId: 1 }
}).success(function (result) {
	
		$scope.ItemCtgry=result.data;

		$scope.ItemCtgry.splice(0,0,{id : 0 ,name : "select"});
		$rootScope.$emit("get_itmCtgry_list",{dep:$scope.ItemCtgry});
});

$scope.filterItmCat=function()
{
	$rootScope.itm_cat_id=$scope.itm_category_id1;
	for(var i=0;i<$scope.ItemCtgry.length;i++)
		{
		if($scope.ItemCtgry[i].id == $scope.itm_category_id1)
			{
			$rootScope.itm_cat_name=$scope.ItemCtgry[i].name;
			break;
			}
		}
	//alert($rootScope.itm_cat_name);
}
	    					
	}


