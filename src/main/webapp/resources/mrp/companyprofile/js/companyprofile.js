
//Controller for Table and Form 
mrpApp.controller('companyprofilectrl', companyprofilectrl);

function companyprofilectrl($scope, $http, $mdDialog ,$rootScope,$controller,MRP_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	$controller('DatatableController', {$scope: $scope});

	clearform();
/*	set_sub_menu("#settings");						
	setMenuSelected("#companyprofile");	*/	
	manageButtons("view");
	$('.position_prev_next_btn_form').hide();
	$(".btnBack").hide();
	$(".btnDiscard").hide();
	$scope.disable_all=true;
	$scope.formData = {};
	

//	get company details
	 $scope.companyprofileListItem=function()
	{$scope.formData = {};
	$http({
		url : 'json',
		method : "GET",
	}).then(function(response) {
		$scope.formData = response.data.data[0];});}
	 $scope.companyprofileListItem();
//	Save Function	
	$rootScope.$on('fun_save_data',function(event){		
		if (code_existing_validation($scope.formData)) {			
			$http({
				url : 'save',
				method : "POST",
				params : $scope.formData,
			}).then(function(response) {
				 $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);

				manageButtons("view");					
				$scope.disable_all = true;				
				$('.btnDiscard').hide();
				$http({
					url : '../systemsettings/getsystemsetting',
					method : "GET",
				});
			});}});

//	Discard function	
	$rootScope.$on("fun_discard_form",function(event){					
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).cancel('No').ok('Yes');
		$mdDialog.show(confirm).then(function() {	
			manageButtons("view");
			$('.position_prev_next_btn_form').hide();
			$(".btnDiscard").hide();			
			$scope.disable_all=true;
			$scope.companyprofileListItem();	
			clearform();
		});});

	$rootScope.$on("fun_enable_inputs",function(){
		$scope.disable_all = false;
		$(".btnBackr").hide();
		$(".btnDiscard").show();});


	// Validation 

	function code_existing_validation(data){
		var flg = true;			 
		if(validation() == false){
			flg = false;}			
		return flg;}}





