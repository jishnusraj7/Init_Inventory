var mrpApp = angular.module('mrp_app', [ 'datatables', 'ngMaterial','ngMessages','multipleDatePicker',
                                         "common_app", 'minicolors', 'angular.filter', 'moment-picker',
                                         'ui.calendar', 'ui.bootstrap', 'datatables.scroller', 'ngDragDrop' ,'angularjs-dropdown-multiselect','checklist-model']);


mrpApp.controller('ExcellReportController', function($compile, $timeout, $scope,$q,
		$http, $mdDialog, $rootScope, DTOptionsBuilder, DTColumnBuilder,
		MRP_CONSTANT, DATATABLE_CONSTANT, DataTableService) {
	
	
	//Start excel exportData
	
	$scope.displayList=[];
	var vm = this;
	var urlString="getExportData";
	vm.dtInstance1={};
	vm.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			$scope.displayList=[];
			for(var i=0;i<result.data.data.length;i++){
				$scope.displayList.push({SI:parseInt(result.data.data[i].SI),COLUMN_NAME:result.data.data[i].Display});
			}
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers')
	.withDataProp('data')
    .withOption('paging', false)
    .withOption('info', false)
    .withOption('searching', false)
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

	              	var title='<input ng-model="item.selectAll" ng-click="toggleAll(item.selectAll, item.selectedDep)" type="checkbox"></input>';
	vm.selectedDep = {};
	vm.selectAll = true;
	vm.dtColumns1 = [
	                 DTColumnBuilder.newColumn('SI').withTitle('SI').withOption('width','150px')
	                   .renderWith(function(data, type, full, meta) {
		                	    	  
		                	        return parseInt(data);
		                	    })
		                	    ,
		                		DTColumnBuilder.newColumn('Display').withTitle('Display'),
		                		  DTColumnBuilder.newColumn(null).withTitle(title).notSortable()
		                	      .renderWith(function(data, type, full, meta) {
		                	    	  vm.selectedDep[parseInt(full.SI)] = true;
		                	    	  vm.selectAll = true;
		                	        return '<input  ng-model="item.selectedDep[' + parseInt(full.SI) + ']" ng-click="toggleOne(item.selectedDep,' + parseInt(full.SI) + ')" type="checkbox">';
		                	    }).withOption('width','100px'),
	                	];


	$scope.DisplayListRow = {items: []};
	$scope.DisplayListRow.items.push({display:1,order:1});
		$scope.selectItem=[];	
		$scope.toggleOne=function(selectedItems,id) {
			vm.selectAll = true;
			  for (var id in selectedItems) {

				  if(selectedItems.hasOwnProperty(id))
					  {
			if (selectedItems[id]==false)
				{
				vm.selectAll = false;
				}
			
			  }
			  }
	};
		
	
	
	$scope.toggleAll =function(SelectAll, selectedItems) {
        for (var id in selectedItems) {
            if (selectedItems.hasOwnProperty(id)) {
            	selectedItems[id] = SelectAll;
            }
        }
    }
	
	
	
	$scope.exportDatatoExcel=function(){
		
		$scope.supplierfilterData=[];
		$scope.coloumnList=[];
		$scope.orderList=[];
		
		$scope.supplierfilterData.push({seletedColoumn:vm.selectedDep,orderData:$scope.DisplayListRow.items});
	
		
		
		for(var i=0;i<$scope.DisplayListRow.items.length;i++){
			
			
			$scope.orderList.push($scope.displayList[parseInt($scope.DisplayListRow.items[i].display)-1].COLUMN_NAME +" "+(($scope.DisplayListRow.items[i].order==1) ? 'ASC':'DESC') );
			
		}
		
		 for (var key in vm.selectedDep) {
	    	   
	    	   if (vm.selectedDep.hasOwnProperty(key)) {
	    		   if(vm.selectedDep[key]==true){
	    			   $scope.coloumnList.push($scope.displayList[key-1].COLUMN_NAME);
	    	    	      
	    		   }
	    		  }
	    	   }
		 
		 if($scope.coloumnList.length<=0){
			 alert("cannot be empty");
		 }else{
			 var link=document.createElement('a');
	    	    link.href="ExcelReport.xls?ColoumnList="+$scope.coloumnList+"&OrderList="+$scope.orderList+"&ReportName="+$('h1').html()+"";

	    	    link.click();
			 

			  
/*		 window.open("ExcelReport.xls?ColoumnList="+$scope.coloumnList+"&OrderList="+$scope.orderList+"&ReportName="+$('h1').html()+"", '_blank');
*/	}}
	
	$scope.addRow=function(){
if(($scope.DisplayListRow.items.length+1)<$scope.displayList.length){
	
	
	$timeout(function () {$scope.DisplayListRow.items.push({display:1,order:1});}, 1); 
	
}else{
	$("#createsortrow").hide();
	$timeout(function () {$scope.DisplayListRow.items.push({display:1,order:1});}, 1); 
}
		
	}
	
	$scope.removeRow=function(index){
		$("#createsortrow").show();
		if($scope.DisplayListRow.items.length<=1){
			alert("Atleast one item should be needed");
		}else{
			$scope.DisplayListRow.items.splice(index, 1);
				
		}
		
	}
	
	
	
	//end excelReport
});

mrpApp.controller('DatatableController', function($compile, $timeout, $scope,$q,
		$http, $mdDialog, $rootScope, DTOptionsBuilder, DTColumnBuilder,
		MRP_CONSTANT, DATATABLE_CONSTANT, DataTableService) {

	$scope.getMRP_DataTable_dtOptions = function(vms, DataObj) {
		var menuLength = [ [ 5, 10, 15, 20, 50, 100, 250 ],
		                   [ 5, 10, 15, 20, 50, 100, 250 ] ];
		vms.dtOptions = DTOptionsBuilder.newOptions().withOption('ajax', {
			"url" : DataObj.sourceUrl,
			"data" : function(data) {
				if (DataObj.hasOwnProperty("adnlFilters")) {
					data.additionalFilters = DataObj.adnlFilters;
				}
				$scope.planify(data);
			},
			error : function(response) {
				location.href = "errorpage";
			}
		}).withOption('rowCallback', DataObj.rowCallback).withOption(
				'infoCallback', DataObj.infoCallback)
				// .withOption('infoCallback',DataObj.fnFooterCallback)
				
				 /*.withOption('createdRow', function(row, data, dataIndex) {
		              		$compile(angular.element(row).contents())($scope);
		              	})
		              	.withOption('headerCallback', function(header) {
		              		if (!vms.headerCompiled) {
		              			vms.headerCompiled = true;
		              			$compile(angular.element(header).contents())($scope);
		              		}
		              	})*/
				
				.withOption(
						'order',
						[ [
						   1,
						   (DataObj.order == undefined) ? "asc"
								   : DataObj.order ] ])
								   .withPaginationType('page_search').withDataProp('data')
								   .withOption('lengthMenu', menuLength).withOption('deferRender',
										   true).withOption('serverSide', true).withOption(
												   'processing', true).withDataProp('data')
												   .withOption('language', {
													   "sInfo" : DATATABLE_CONSTANT.SINFO,
													   "sInfoEmpty" : DATATABLE_CONSTANT.SINFOEMPTY,
													   "lengthMenu" : DATATABLE_CONSTANT.LENGTHMENU,
													   "search" : DATATABLE_CONSTANT.SEARCH,
													   "searchPlaceholder" : DATATABLE_CONSTANT.SEARCHPLACEHOLDER,
													   "paginate" : {
														   "previous" : DATATABLE_CONSTANT.PREVIOUS,
														   "next" : DATATABLE_CONSTANT.NEXT,
														   "search_page" : DATATABLE_CONSTANT.SEARCH_PAGE,
														   "first" : DATATABLE_CONSTANT.FIRST,
														   "last" : DATATABLE_CONSTANT.LAST
													   },
													   "infoFiltered" : DATATABLE_CONSTANT.INFOFILTERED,
												   }).withPaginationType(DATATABLE_CONSTANT.PAGINATIONTYPE)
												   .withDisplayLength(DATATABLE_CONSTANT.DISPLAYLENGTH).withDOM(
														   DATATABLE_CONSTANT.WITHDOM);
		return vms.dtOptions;
	}

	$scope.planify = planify;
	function planify(data) {

		for (var i = 0; i < data.columns.length; i++) {
			column = data.columns[i];
			column.searchRegex = column.search.regex;
			if (data.hasOwnProperty("additionalFilters")) {
				for (var j = 0; j < data.additionalFilters.length; j++) {
					if (i == data.additionalFilters[j].col) {
						column.searchValue = data.additionalFilters[j].filters;
					}
				}
			} else {
				column.searchValue = column.search.value;
			}
			delete (column.search);
		}
	}

	$scope.isCodeExistis = function(code) {
		$http({
			url : 'codeexisting',
			method : "GET",
			params : {
				code : code
			}
		}).then(function(response) {
			if (response.data == 1) {
				$scope.hide_code_existing_er = false;
				$scope.existing_code = '"' + code + '" Existing';
			} else if (response.data == 0) {
				$scope.hide_code_existing_er = true;
			}
		}, function(response) { // optional
		});
	}

});
