
//Controller for Table and Form 
mrpApp.controller('choices', choices);


function choices($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {

			$scope.formData.code = response;});}

	/*set_sub_menu("#settings");		
	setMenuSelected("#shopshift_left_menu");*/			//active leftmenu
	manageButtons("add");

	$scope.formData = {};
	$scope.show_table=true;

	$scope.show_form=false;
	$scope.hide_code_existing_er = true;


	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.function_clear_select=function_clear_select;

	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order="desc";

	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('id').withTitle('ID').notVisible().withOption('searchable', false),

	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').renderWith(
	                		function(data, type, full, meta) {
	                			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

	                			return '<a id="rcd_edit" class='+css+' ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';  
	                		}),
	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		DTColumnBuilder.newColumn('shift_type').withTitle('SHIFT TYPE').renderWith(
	                				function(data,type,full,meta){
	                					if(data=="1")
	                					{
	                						data='Continues';
	                					}
	                					else if(data=="2")
	                					{
	                						data='Split shift';
	                					}
	                					return data;
	                				}),
	                				DTColumnBuilder.newColumn('start_time').withTitle('START TIME'),
	                				DTColumnBuilder.newColumn('end_time').withTitle('END TIME'),
	                				DTColumnBuilder.newColumn('total_hours').withTitle('TOTAL HOURS').withOption('width','240px'),
	                				
	                				DTColumnBuilder.newColumn('is_active').withTitle('IS ACTIVE').renderWith(
	                						function(data,type,full,meta){
	                							if(data=="0")
	                							{
	                								data='<i class="fa fa-times-circle-o empresigned" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="Active"></i>';
	                							}
	                							else if(data=="1")
	                							{
	                								data='<i class="fa fa-check-circle-o empactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="inactive"></i>';
	                							}
	                							return data;
	                						}),
	                						];

	function urlFormater(data) {

		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
		+ data + '</a>';   
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
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

	/*function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();

	    return pageInfo.page+1 +" / "+pageInfo.pages;

	}*/

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}


	}


	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});

	$rootScope.$on('hide_form',function(event){
		 $("#show_form").val(0);
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});

	function showTable(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}


	function reloadData() {
		vm.dtInstance.reloadData(null, true);
	}


	function edit(row_data,cur_row_index,event) {									

		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
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



		$http({
			url : 'getShopshiftData',method : "GET",params:{id:row_data.id},async:false,
		}).then(function(response) {

			$scope.formData = response.data.shopshift[0];
			$scope.formData.is_global= ($scope.formData.is_global == 0) ? false:true;
			$scope.formData.allow_unscheduled_unpaid_breaks= ($scope.formData.allow_unscheduled_unpaid_breaks == 0) ? false:true;
			$scope.formData.overtime_is_payable= ($scope.formData.overtime_is_payable == 0) ? false:true;
			$scope.formData.is_active= ($scope.formData.is_active  == 0) ? false:true;
			$scope.formData.interval_is_payable= ($scope.formData.interval_is_payable  == 0) ? false:true;
			$scope.formData.created_at=response.data.shopshift[0].created_at;
	
			if(response.data.shopshift[0].minimum_overtime_limit!=null)
			{
				var overTime = 	response.data.shopshift[0].minimum_overtime_limit.split(":");
				$("#minimum_overtime_limit1").val(overTime[0]);
				$("#minimum_overtime_limit2").val(overTime[1]);
			}

			if(response.data.shopshift[0].start_time!=null)
			{

				var startTime 				= 	response.data.shopshift[0].start_time.split(":");
				$("#start_time").val(startTime[0]);
				$("#start_time_min").val(startTime[1]);
			}

			if(response.data.shopshift[0].allowed_time_before_start!=null)
			{

				var beforeStart 			= 	response.data.shopshift[0].allowed_time_before_start.split(":");
				$("#start3").val(beforeStart[0]);
				$("#start4").val(beforeStart[1]);
			}

			if(response.data.shopshift[0].allowed_time_after_start!=null)
			{
				var afterStart 			= 	response.data.shopshift[0].allowed_time_after_start.split(":");
				$("#start5").val(afterStart[0]);
				$("#start6").val(afterStart[1]);
			}


			if(response.data.shopshift[0].end_time!=null)
			{

				var endTime 			= 	response.data.shopshift[0].end_time.split(":");	            	
				$("#end_time").val(endTime[0]);
				$("#end_time_min").val(endTime[1]);
			}

			if(response.data.shopshift[0].allowed_time_before_end!=null)
			{  

				var beforeEnd 			= 	response.data.shopshift[0].allowed_time_before_end.split(":");
				$("#start13").val(beforeEnd[0]);
				$("#start14").val(beforeEnd[1]);
			}

			if(response.data.shopshift[0].allowed_time_after_end!=null)
			{
				var afterEnd 			= 	response.data.shopshift[0].allowed_time_after_end.split(":");
				$("#start15").val(afterEnd[0]);
				$("#start16").val(afterEnd[1]);
			}

			if(response.data.shopshift[0].interval_start_time!=null)
			{

				var interStartTime 	= 	response.data.shopshift[0].interval_start_time.split(":");
				$("#start7").val(interStartTime[0]);
				$("#start8").val(interStartTime[1]);
			}

			if(response.data.shopshift[0].interval_end_time!=null)
			{
				var interEndTime 	= 	response.data.shopshift[0].interval_end_time.split(":");
				$("#start9").val(interEndTime[0]);
				$("#start10").val(interEndTime[1]);
			}



		});


		showTable();
		clearform();
		function_clear_select();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description,dept_type:row_data.dept_type};
		$scope.disable_all = true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;

		$scope.formData.is_global= (row_data.is_global == 0) ? false:true;

	}

//	Delete Function
	$rootScope.$on("fun_delete_current_data",function(event){			
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {
				if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Department "+FORM_MESSAGES.ALREADY_USE+"");
				}

				else if(response.data == 1)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					 $("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
					vm.dtInstance1.reloadData(null, true);
					$(".acontainer input").attr('disabled', true);
					reloadData();
				}
				else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);}

			}, function(response) { // optional

				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Delete failed.")
						.ok('Ok!')
						.targetEvent(event)
				);

			});

		}, function() {

		});
	});


	function select_next_prev_row(index){										//set NEXT-PREV Data In Form Atfer Deleteion

		var cur_index=index;
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		if(row_count != 1){
			var row_data = vm.dtInstance.DataTable.rows().data();
			if(index == row_count-1){
				index = index-1;
			}else{
				index = index+1;
			}
			var selcted_row_data = vm.dtInstance.DataTable.rows(index).data();
			edit(selcted_row_data[0],index);
			$rootScope.$emit("next_formdata_set",index);
			if(cur_index !=0){
				if(cur_index-1 == 0){
					$rootScope.$emit("disable_prev_btn");

				}else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}else if(cur_index == 0){
				if(index-1 == 0){
					$rootScope.$emit("disable_prev_btn");

				}else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}


		}else if(row_count == 1){
			$scope.show_table=true;
			$scope.show_form=false;
			manageButtons("add");
		}
		return index;

	}


	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}

	function getTimeLimit()
	{

		var start_time_first			=	$("#start_time").val();
		var start_time_min_first		=	$("#start_time_min").val();
		var str1						=	start_time_first+":"+start_time_min_first+":"+"00";

		var start_time_second			=	$("#start3").val();
		var start_time_min_second		=	$("#start4").val();
		var str2						=	start_time_second+":"+start_time_min_second;

		var time1  						= 	 HMStoSec1("00:01:00");
		var time2  						=	 HMStoSec1(str1);
		var diff  				   		= 	convertSecondsToHHMMSS(time2 - time1);
		if(diff.length==4)
			var diff						= 	"0"+diff
			if(diff=='-1:-1')
				var diff						= 	"00:00"

					var after_start_time_second		=	$("#start5").val();
		var after_start_time_min_second	=	$("#start6").val();
		var str3						=	after_start_time_second+":"+after_start_time_min_second;

		var time3  						=   HMStoSec1(str1);
		var time4 					    =   HMStoSec1("23:59:00");
		var diff_1 						=   convertSecondsToHHMMSS(time4 - time3);

		if(diff_1.length<=4)
			var diff_1						= 	"0"+diff_1

			if (str2 > diff)
			{   
				$("#alertbefore_start").show();	
				$("#alertbefore_end").hide(); 
				$("#alertafter_end").hide(); 
				$("#alertafter_start").hide(); 
				document.getElementById('alertbefore_start').innerHTML = "Lenience Duration Before Shift Start Time Exceeded the limit";
				return false;		
			}
			else
			{

				$("#alertbefore_start").hide(); 
			}

		if (str3 > diff_1)
		{   
			$("#alertafter_start").show();
			$("#alertbefore_end").hide(); 
			$("#alertafter_end").hide(); 
			$("#alertbefore_start").hide(); 
			document.getElementById('alertafter_start').innerHTML = "Lenience Duration After Shift Start Time Exceeded the limit";
			return false		
		}
		else
		{
			$("#alertafter_start").hide(); 
		}

		//////////////////// end time part valdiation section

		var end_time_first				=	$("#end_time").val();
		var end_time_min_first			=	$("#end_time_min").val();
		var str1						=	end_time_first+":"+end_time_min_first+":"+"00";

		var end_time_second				=	$("#start13").val();
		var end_time_min_second			=	$("#start14").val();
		var str2						=	end_time_second+":"+end_time_min_second;

		var time1  						= 	 HMStoSec1("00:01:00");
		var time2  						=	 HMStoSec1(str1);
		var diff  				   		= 	 convertSecondsToHHMMSS(time2 - time1);

		if(diff.length==4)
			var diff						= 	"0"+diff;
		if(diff=='-1:-1')
			var diff						= 	"00:00"

				var after_start_time_second		=	$("#start15").val();
		var after_start_time_min_second	=	$("#start16").val();
		var str3						=	after_start_time_second+":"+after_start_time_min_second+":"+"00";

		var time3  						=   HMStoSec1(str1);
		var time4 					    =   HMStoSec1("23:59:00");
		var diff_1 						=   convertSecondsToHHMMSS(time4 - time3)+":"+"00";
		var diff_1						= 	formatTime(diff_1);	
		if (str2 > diff)
		{   
			$("#alertbefore_end").show();
			$("#alertafter_end").hide(); 
			$("#alertbefore_start").hide(); 
			$("#alertafter_start").hide(); 
			document.getElementById('alertbefore_end').innerHTML = "Maximum Allowed hours before end is "+diff;	
			return false;	
		}
		else
		{
			$("#alertbefore_end").hide();

		}
		if(str3 > diff_1)
		{
			$("#alertafter_end").show();
			$("#alertbefore_end").hide(); 
			$("#alertbefore_start").hide(); 
			$("#alertafter_start").hide(); 
			document.getElementById('alertafter_end').innerHTML = "Maximum Allowed hours after end is "+diff_1;	
			return false
		}

		else
		{
			$("#alertafter_end").hide(); 
		}
		b=compareEndTime();
		return b;
	}

	function compareEndTime()
	{

		var result											=	true;
		var end_time_first									=	$("#end_time").val();
		var end_time_min_first								=	$("#end_time_min").val();
		var end_time										=	end_time_first+":"+end_time_min_first+":"+"00";
		var lenience_duration_after_shift_end_time			=   $("#start15").val()+":"+$("#start16").val()+":"+"00";

		var start_time_first								=	$("#start_time").val();
		var start_time_min_first							=	$("#start_time_min").val();
		var start_time										=	start_time_first+":"+start_time_min_first+":"+"00";


		var after_start_time_second							=	$("#start5").val();
		var after_start_time_min_second						=	$("#start6").val();
		var lenience_duration_after_shift_start_time		=	after_start_time_second+":"+after_start_time_min_second;


		if(end_time!='00:00:00' || lenience_duration_after_shift_end_time!='00:00:00')
		{
			//alert("Reached");
			var lenience_duration_after_shift_end_time		=	 $("#start15").val()+":"+$("#start16").val()+":"+"00";

			var total_duration							 	=	 addTime(end_time,lenience_duration_after_shift_end_time)+":"+"00";
			var start_time  								=    HMStoSec1(start_time);
			var total_duration 					    		=    HMStoSec1(total_duration);
			var diff_btw_total_and_start_time 				=    convertSecondsToHHMMSS(total_duration - start_time)+":"+"00";
			var diff_btw_total_and_start_time				= 	 formatTime(diff_btw_total_and_start_time);	

			$scope.formData.total_hours=diff_btw_total_and_start_time;
			//alert("FIrst value is"+ lenience_duration_after_shift_start_time);
			//alert("Second value is"+ diff_btw_total_and_start_time);
			if (lenience_duration_after_shift_start_time > diff_btw_total_and_start_time)
			{ 
				//alert("Greater");  
				$("#alertafter_start").show();
				document.getElementById('alertafter_start').innerHTML = "Lenience Duration After Shift Start Time Exceeded the limit"			
					result=false;
			}
			else
			{
				$("#alertafter_start").hide();
				result=true
			}
		}
		return result;	
	}

	function HMStoSec1(T) { // h:m:s
		var A = T.split(/\D+/) ; return (A[0]*60 + +A[1])*60 + +A[2] }	




	var secondsPerMinute = 60;
	var minutesPerHour = 60;

	function convertSecondsToHHMMSS(intSecondsToConvert) {
		var hours = convertHours(intSecondsToConvert);
		var minutes = getRemainingMinutes(intSecondsToConvert);
		minutes = (minutes == 60) ? "00" : minutes;
		var seconds = getRemainingSeconds(intSecondsToConvert);
		return hours+":"+minutes;
	}

	function convertHours(intSeconds) {
		var minutes = convertMinutes(intSeconds);
		var hours = Math.floor(minutes/minutesPerHour);
		return hours;
	}
	function convertMinutes(intSeconds) {
		return Math.floor(intSeconds/secondsPerMinute);
	}
	function getRemainingSeconds(intTotalSeconds) {
		return (intTotalSeconds%secondsPerMinute);
	}
	function getRemainingMinutes(intSeconds) {
		var intTotalMinutes = convertMinutes(intSeconds);
		return (intTotalMinutes%minutesPerHour);
	}

	function HMStoSec1(T) { // h:m:s
		var A = T.split(/\D+/) ; return (A[0]*60 + +A[1])*60 + +A[2] }


	function formatTime(str)
	{
		var n				=	str.split(":");
		var hh				= 	n[0];
		var mm				= 	n[1];
		var ss				= 	n[2];
		var hh 				=	(hh.length==1) ? "0"+hh : hh;
		var mm 				= 	(mm.length==1) ? "0"+mm : mm;
		var ss 				= 	(ss.length==1) ? "0"+ss : ss;
		var formated_time	=	hh+":"+mm+":"+ss;
		return formated_time;
	}


	//function two add two times

	var splitTimeStr = function(t){
		var t = t.split(":");
		t[0] = Number(t[0]);
		t[1] = Number(t[1]);
		return t;
	};

	var addTime = function(t1, t2){
		var t1Hr = splitTimeStr(t1)[0];
		var t1Min = splitTimeStr(t1)[1];
		var t2Hr = splitTimeStr(t2)[0];
		var t2Min = splitTimeStr(t2)[1];
		var rHr = t1Hr + t2Hr;
		var rMin = t1Min + t2Min;
		if (rMin >= 60)
		{
			rMin = rMin - 60;
			rHr = rHr + 1;
		}
		if (rMin < 10) rMin = "0" + rMin;
		if (rHr < 10) rHr = "0" + rHr;
		return "" + rHr + ":" + rMin;
	};


	$rootScope.$on('fun_save_data',function(event){		//Save Function

		if (code_existing_validation($scope.formData)) {
			var currentdate = new Date(); 
			var datetime = 	+ currentdate.getFullYear() + "-"
			+ (currentdate.getMonth()+1)  + "-"
			+ currentdate.getDate() + " "
			+ currentdate.getHours() + ":"  
			+ currentdate.getMinutes() + ":" 
			+ currentdate.getSeconds();

			$scope.formData.allow_unscheduled_unpaid_breaks= ($scope.formData.allow_unscheduled_unpaid_breaks == true) ? 1:0;
			$scope.formData.overtime_is_payable= ($scope.formData.overtime_is_payable == true) ? 1:0;
			$scope.formData.is_active= ($scope.formData.is_active == true) ? 1:0;
			$scope.formData.interval_is_payable= ($scope.formData.interval_is_payable == true) ? 1:0;
			/*$scope.formData.total_hours="12";
			 */		
			
			if($scope.formData.id==undefined){
				$scope.formData.created_at=datetime;
			}
			
			
			$scope.formData.created_by=strings['userID'];
			 $scope.formData.is_synchable=0;
			 var minimum_overtime_limit=  $("#minimum_overtime_limit1").val()+":"+$("#minimum_overtime_limit2").val()+":"+"00";
			 var start_time=  $("#start_time").val()+":"+$("#start_time_min").val()+":"+"00";
			 var allowed_time_before_start=  $("#start3").val()+":"+$("#start4").val()+":"+"00";
			 var allowed_time_after_start=  $("#start5").val()+":"+$("#start6").val()+":"+"00";
			 var end_time=  $("#end_time").val()+":"+$("#end_time_min").val()+":"+"00";
			 var allowed_time_before_end=  $("#start13").val()+":"+$("#start14").val()+":"+"00";
			 var allowed_time_after_end=  $("#start15").val()+":"+$("#start16").val()+":"+"00";
			 var interval_start_time=  $("#start7").val()+":"+$("#start8").val()+":"+"00";
			 var interval_end_time=  $("#start9").val()+":"+$("#start10").val()+":"+"00";
			 $scope.formData.minimum_overtime_limit=minimum_overtime_limit;
			 $scope.formData.start_time=start_time;
			 $scope.formData.allowed_time_before_start=allowed_time_before_start;
			 $scope.formData.allowed_time_after_start=allowed_time_after_start;
			 $scope.formData.end_time=end_time;
			 $scope.formData.allowed_time_before_end=allowed_time_before_end;
			 $scope.formData.allowed_time_after_end=allowed_time_after_end;
			 $scope.formData.interval_start_time=interval_start_time;
			 $scope.formData.interval_end_time=interval_end_time;

			 $scope.Quetable.shopId=settings['currentcompanyid1'];
			 $scope.Quetable.origin=settings['currentcompanycode1'];
			 $scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";

			 $http({
				 url : 'saveShopShift',
				 method : "POST",
				 params : $scope.formData,
				 data : {Quetable:$scope.Quetable},

			 }).then(function(response){
				 if(response.data == 1){

					 $scope.formData.is_global= ($scope.formData.is_global == 0) ? false:true;
					 $scope.formData.allow_unscheduled_unpaid_breaks= ($scope.formData.allow_unscheduled_unpaid_breaks == 0) ? false:true;
					 $scope.formData.overtime_is_payable= ($scope.formData.overtime_is_payable == 0) ? false:true;
					 $scope.formData.is_active= ($scope.formData.is_active  == 0) ? false:true;
					 $scope.formData.interval_is_payable= ($scope.formData.interval_is_payable  == 0) ? false:true;

					 var DataObj = {};	
					 DataObj.alertClass   = "alert-box";
					 DataObj.alertStatus  = false;
					 DataObj.divId        = "#alertMessageId";	

					 if($scope.formData.id !=undefined)
					 {
						 $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					 }
					 else
					 {	
						 function_clear_select();
						 $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

					 }

					 if($scope.formData.id !=undefined){
						 view_mode_aftr_edit();
					 }
					 else{
						 $scope.formData ={};
						 $scope.fun_get_pono();
					 }
					 reloadData();
					 $scope.hide_code_existing_er = true;
				 }else{

					 $mdDialog.show($mdDialog.alert()
							 .parent(angular.element(document.querySelector('#dialogContainer')))
							 .clickOutsideToClose(true)
							 .textContent("Under Construction.")
							 .ok('Ok!')
							 .targetEvent(event)
					 );
				 }

			 }, function(response) { // optional

				 $mdDialog.show($mdDialog.alert()
						 .parent(angular.element(document.querySelector('#dialogContainer')))
						 .clickOutsideToClose(true)
						 .textContent("Under Construction.")
						 .ok('Ok!')
						 .targetEvent(event)
				 );

			 });
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
		}}).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			if($scope.formData.id == undefined){
				$scope.formData ={};
				$scope.fun_get_pono();
				//$scope.formData.parent_id = $scope.sample[0].id;
				$scope.hide_code_existing_er = true;

			}else{
				var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
			function_clear_select();
		});
	});

	$rootScope.$on("fun_enable_inputs",function(){
		 $("#show_form").val(1);
		$scope.disable_all = false;
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});

	$rootScope.$on("fun_clear_form",function(){

		function_clear_select();
		$scope.formData = {};
		$scope.fun_get_pono();
		$scope.hide_code_existing_er = true;
		//$scope.formData.parent_id = $scope.sample[0].id;
	});

	$rootScope.$on("fun_next_rowData",function(e,id){

		var current_row_index = 0;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(current_row_index == 0){
			$rootScope.$emit("enable_prev_btn");
		}

		if(row_data.length == current_row_index+1){
			$rootScope.$emit("disable_next_btn");
		}
		var next_row_index = current_row_index+1;
		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0],next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}

	});

	$rootScope.$on("fun_prev_rowData",function(e,id){
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index-1;
		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][prev_row_index]).data();
			edit(selcted_row_data[0],prev_row_index);
			$rootScope.$emit("next_formdata_set",prev_row_index);
		}
		if(current_row_index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}

	});

	// Validation 

	function code_existing_validation(data){
		var flg = true;

		var row_data = vm.dtInstance.DataTable.rows().data();
		if(data.id == undefined || data.id == ""){
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}


		}

		if(validation() == false){
			flg = false;	
			$('#alert_required').show();		
		}
		else if(getTimeLimit()==false)
		{
			flg = false;
		}




		if(flg==false)
		{
			focus();
		}else{
			$('#alert_required').hide();	
		}

		if($scope.formData.name==undefined || $scope.formData.shift_type==undefined){
			$scope.selectedIndexTab=0;
		}else if(flg==false){
			$scope.selectedIndexTab=1;
		}

		return flg;

	}


	function function_clear_select(){
		$('#alert_required').hide();	
		$("#alertbefore_end").hide();
		$("#alertafter_end").hide(); 
		$("#alertbefore_start").hide(); 
		$("#alertafter_start").hide(); 


		$scope.selectedIndexTab=0;
		$("#minimum_overtime_limit1").val("00");
		$("#minimum_overtime_limit2").val("00");
		$("#start_time").val("");
		$("#start_time_min").val("00");
		$("#start3").val("00");
		$("#start4").val("00");

		$("#start5").val("00");
		$("#start6").val("00");

		$("#end_time").val("");
		$("#end_time_min").val("00");
		$("#start13").val("00");
		$("#start14").val("00");
		$("#start15").val("00");
		$("#start16").val("00");
		$("#start7").val("00");
		$("#start8").val("00");
		$("#start9").val("00");
		$("#start10").val("00");


		$("#form_div_dept_type").removeClass("has-error");
		$("#form_div_dept_type_error").hide();


	}

	$rootScope.$on("excel_Print",function(){
		$scope.ExcelSheet();

	});

	$scope.ExcelSheet=function()
	{
		window.open("ShopShiftReport.xls");
	}

}


