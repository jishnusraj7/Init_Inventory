/**
 * 
 */
var myapp=angular.module('login',['ngCookies']);
myapp.controller("login_controller",loginController);
function loginController($scope,$cookies,$cookieStore)
{
	
	setTimeout(function () {
		$('.main-footer').hide();
		
	}, 1500);
	

	
	if(cmpny!="")
	{
        $('input:radio').removeAttr('disabled');
	}
	$scope.setCookies=setCookies;
	setCookies();
	var usernamestatus = " ";
	var passwordstatus = " ";
	if(version==1)
		{
		$("#companyId").show();
		$('#companyId option:eq(0)').prop('selected', true);
		}
/*	else
		{
		$("#companyId").hide();
		}*/
	if (err_msg == 1) {
		$(".validation_label").show();
		passwordstatus = "password incorrect";
	}else if (err_msg == 2) {
		usernamestatus = "username incorrect";
	} else if (err_msg == 3) {
		passwordstatus = "password can not be null";
	} else if (err_msg == 4) {
		usernamestatus = "username can not be null";
	} else if (err_msg == 5) {
		passwordstatus = "password can not be null";
		usernamestatus = "username can not be null";
	} else if (err_msg == "") {
		passwordstatus = "";
		usernamestatus = "";
	}
	$("#itemLists").hide();
	$("#usernamestatus").html(usernamestatus);
	$("#passwordstatus").html(passwordstatus);
	$("input[type='radio']").click(function(e){
		if(e.target.id == "isHq"){
			$("#companyId").hide();
			$("#loginId").val("");
			$("#password").val("");
		}else if(e.target.id == "isCmpny"){
			$("#companyId").show();
			$("#loginId").val("");
			$("#password").val("");
		}
		$('#companyId option:eq(0)').prop('selected', true);
		$("#usernamestatus").html("");
		$("#passwordstatus").html("");
		$cookieStore.put('currCmpnyType',e.target.id);		
	});
	
	$('#companyId').change(function(){ 
	    var value = $(this).val();
	    $cookieStore.put('currCmpnyId',value);
	});
	$("#signIn").click(function(){
		 $cookieStore.put('currUser',$("#loginId").val());
		 
	});
	function setCookies()
	{
		 $scope.currCmpnyType = $cookieStore.get('currCmpnyType');
		 $scope.currCmpnyId = $cookieStore.get('currCmpnyId');
		 $scope.currUser = $cookieStore.get('currUser');
		 if($scope.currCmpnyType=="isCmpny")
			{
			$("#isCmpny").prop('checked', true);
			$("#companyId").show();
			if($scope.currCmpnyId!=undefined)
				{
				$("#companyId").val( $scope.currCmpnyId);
				}
			else
				{
				$('#companyId option:eq(0)').prop('selected', true);
				}
			$("#loginId").val($scope.currUser);
			}
		 else if($scope.currCmpnyType=="isHq")
			 {
			 $("#isHq").prop('checked', true);
			 $("#companyId").hide();
			 $('#companyId option:eq(0)').prop('selected', true);
			 $("#loginId").val($scope.currUser);
			 }
		else
			{
			$("#isHq").prop('checked', true);
			 $("#companyId").hide();
			$("#loginId").val($scope.currUser);
			$('#companyId option:eq(0)').prop('selected', true);
			}
	}
}