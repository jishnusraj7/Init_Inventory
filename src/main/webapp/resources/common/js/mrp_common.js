var mrpCommonApp = angular.module('common_app', [ 'datatables', 'ngMaterial',
                                                  'angularSpinkit' ]);

mrpCommonApp.constant('MRP_CONSTANT', {

	appName : 'MRP',
	appVersion : '1.0',
	apiUrl : 'http://localhost:8080/mrp/'

});

mrpCommonApp
.constant(
		'DATATABLE_CONSTANT',
		{

			SINFO             : '_END_ / _TOTAL_',
			SINFOEMPTY        : '0/0',
			LENGTHMENU        : 'Show _MENU_ ',
			SEARCH            : '<div class="left-inner-addon "><i class="fa fa-search"></i>_INPUT_<div>',
			SEARCHPLACEHOLDER : 'Search...',
			PREVIOUS          : '<i class="fa fa-angle-left"></i>',
			FIRST 			  : '<i class="fa fa-angle-double-left"></i>',
			LAST              : '<i class="fa fa-angle-double-right"></i>',
			NEXT              : '<i class="fa fa-angle-right"></i>',
			SEARCH_PAGE       : 'Go',
			INFOFILTERED      : 'Filtered from _MAX_',
			PAGINATIONTYPE    : 'page_search',
			DISPLAYLENGTH     : 10,
			WITHDOM           : '<"pull-left"l><"pull-left"f><"pull-left"p>it',
			JSONURL           : 'json',

		});

mrpCommonApp
.constant(
		'FORM_MESSAGES', 
		{

			SAVE_SUC        : 'Data save successfully',
			UPDATE_SUC      : 'Data update successfully',
			DELETE_SUC      : 'Data deleted successfully',
			SAVE_ERR        : 'Data save failed',
			UPDATE_ERR      : 'Data update failed',
			DELETE_ERR      : 'Data delete failed',
			FINALIZE_ERR    : 'Data finalize failed',
			ISSE_ERR        : 'Data issue failed',
			DELETE_WRNG     : 'Are you sure to delete this data?',
			DISCARD_WRNG    : 'Are you sure to discard the changes?',
			ISSUE_WRNG      : 'Are you sure to issue this data?',
			PRINT_WRNG      : 'Are you sure to print this data?',
			FINALIZE_WRNG   : 'Are you sure to finalize this data?',
			ALREADY_USE     : 'in use',
			ROW_DELETE_WRNG : 'Are you sure to delete this Item?',
			UPDATE_PASS     : 'Password changed Successfully',
			UPDATE_PASSERR  : 'Password Is not Updated',
			SHOP_VAL_ERR	: 'Shop selection is mandatory',
			CUST_VAL_ERR    :'Customer selection is mandatory'
				
		});

mrpCommonApp
.constant(
		'ITEM_TABLE_MESSAGES', 
		{

			TABLE_ERR : 'Enter atleast one item',
			ITEM_CODE_ERR : 'Please enter item code',
			QTY_ERR : 'Please enter qty',
			CODE_EXISIT : 'Code exist',
			DATE_ERR : 'Date cannot be blank',
			COST_ERR:'Please Enter Rate',
			COST_PERCNTGE_ERR:'Please Enter Cost Percentage',
			PERCENT_LIMIT_ERR:'Please Enter Valid Percentage'

		});

mrpCommonApp
.constant(
		'RECORD_STATUS', 
		{

			NEW 	   : 'NEW',
			PROCESSING : 'PROCESSING',
			PRINTED    : 'PRINTED',
			APPROVED   : 'APPROVED',
			REJECTED   : 'REJECTED',
			CLOSED     : 'CLOSED',
			REQUESTED  : 'REQUESTED',
			ORDERED    : 'ORDERED',
			FINISHED   : 'FINISHED',
			SUBMITTED  : 'SUBMITTED',
			FINALIZED  : 'FINALIZED',
			PENDING    : 'PENDING',
			ISSUED     : 'ISSUED'

		},
     'PAYMENT_MODE',
        {
    	   0 :'CASH',
    	   1 :'CREDIT'
        });

mrpCommonApp
.constant(
		'STATUS_BTN_TEXT', 
		{

			PRINT    : 'PRINT',
			RE_PRINT : 'RE-PRINT',
			APPROVE_OR_REJECT : 'Approve / Reject',
			ISSUE    : 'ISSUE',
			REJECTED : 'REJECTED',
			FINALIZE : 'FINALIZE',
			CLOSE_PO : 'CLOSE PO',
			APPROVED : 'APPROVED',
		    PENDING : 'PENDING'
		});

mrpCommonApp
.constant(
		'RECORD_STATUS_STYLE', 
		{

			NEW_STYLE        : 'form_list_con pending_bg',
			PROCESSING_STYLE : '',
			PRINTED_STYLE  	 : 'form_list_con print_bg',
			APPROVED_STYLE   : 'form_list_con  approved_bg',
			REJECTED_STYLE   : 'form_list_con rejected_bg',
			CLOSED_STYLE     : 'form_list_con closed_bg',
			REQUESTED_STYLE  : '',
			ORDERED_STYLE    : '',
			FINISHED_STYLE   : '',
			SUBMITTED_STYLE  : '',
			FINALIZED_STYLE  : 'form_list_con  approved_bg',
			PENDING_STYLE    : 'form_list_con pending_bg',
			ISSUED_STYLE     : 'form_list_con recieved_bg'

		});

mrpCommonApp.service('DataTableService', function() {
});

mrpCommonApp.controller('common_ctrl', function($scope, $rootScope) {

});

mrpCommonApp.service('CommonService', function($rootScope) {

});

function geteditDateFormat(date) {
	var currentDateFormat = get_date_format();
	return moment(date, "YYYY-MM-DD").format(currentDateFormat.format);
}



function process(date) {
	var parts = date.split(settings['DateSeparator']);
	if (settings['DateFormat'] == "0") {
		return new Date(parts[2], parts[1] - 1, parts[0]);
	} else if (settings['DateFormat'] == "1") {

		return new Date(parts[2], parts[0] - 1, parts[1]);
	} else if (settings['DateFormat'] == "2") {
		return new Date(parts[0], parts[1] - 1, parts[2]);
	} else {
		return new Date(parts[2], parts[1] - 1, parts[0]);
	}
}




function getMysqlFormat(date) {
	var currentDateFormat = get_date_format();
	return moment(date, currentDateFormat.format).format("YYYY-MM-DD");
}

function dateForm(date) {
	var currentDateFormat = get_date_format();
	return moment(date).format(currentDateFormat.format);
}

function get_date_format() {
	var datesaperator = settings['DateSeparator'];
	var date = {};
	day = moment().format("DD");
	month = moment().format("MM");
	year = moment().format("YYYY");
	if (settings['DateFormat'] == "0") {
		date = {
				format : "DD" + datesaperator + "MM" + datesaperator + "YYYY",
				mask : "d" + datesaperator + "m" + datesaperator + "y",
				mindate : '' + day + datesaperator + (month) + datesaperator + year
				+ ''
		}
	} else if (settings['DateFormat'] == "1") {
		date = {
				format : "MM" + datesaperator + "DD" + datesaperator + "YYYY",
				mask : "m" + datesaperator + "d" + datesaperator + "y",
				mindate : '' + (month) + datesaperator + (day) + datesaperator
				+ year + ''
		}
	} else if (settings['DateFormat'] == "2") {
		date = {
				format : "YYYY" + datesaperator + "MM" + datesaperator + "DD",
				mask : "y" + datesaperator + "m" + datesaperator + "d",
				mindate : '' + year + datesaperator + (month) + datesaperator + day
				+ ''
		}
	}
	return date;
}
function get_date_format_search() {
	var datesaperator = settings['DateSeparator'];
	var date = {};
	day = moment().format("DD");
	month = moment().format("MM");
	year = moment().format("YYYY");
	if (settings['DateFormat'] == "0") {
		date = {
				format : "dd" + datesaperator + "mm" + datesaperator + "yy",
				mask : "d" + datesaperator + "m" + datesaperator + "y",
				mindate : '' + day + datesaperator + (month) + datesaperator + year
				+ ''
		}
	} else if (settings['DateFormat'] == "1") {
		date = {
				format : "mm" + datesaperator + "dd" + datesaperator + "yy",
				mask : "m" + datesaperator + "d" + datesaperator + "y",
				mindate : '' + (month) + datesaperator + (day) + datesaperator
				+ year + ''
		}
	} else if (settings['DateFormat'] == "2") {
		date = {
				format : "yy" + datesaperator + "mm" + datesaperator + "dd",
				mask : "y" + datesaperator + "m" + datesaperator + "d",
				mindate : '' + year + datesaperator + (month) + datesaperator + day
				+ ''
		}
	}
	return date;
}

$('#navbar-collapse ul li').click(function(e) {
	$("#navbar-collapse ul li").removeClass("active");
	$('.sidebar-menu').hide();
	$("#" + e.target.id + "_sub_menu").show();
});

function set_sub_menu(obj) {
	console.log("obj==============>"+JSON.stringify(obj))
	$("#navbar-collapse ul li").removeClass("active");
	$(obj).parents("li").addClass("active")
/*	$('.sidebar-menu').hide();
*/	$("" + obj + "_sub_menu").show();
}

function setMenuSelected(objRef) {

	$(objRef).addClass("active");
	$(objRef).show();
	$(".treeview-menu").addClass("menu-open").hide();
	
	$(objRef).parents(".treeview-menu").addClass("menu-open").show();
	$(objRef).parents(".treeview-menu li").addClass("active");

}



/**
 * @param objRef
 * @param size
 */
function setModalBox(objRef, size) {
	if (size == 'large') {
		$(objRef).attr('class', 'modal fade bs-example-modal-lg').attr(
				'aria-labelledby', 'myLargeModalLabel');
		$('.modal-dialog').attr('class', 'modal-dialog modal-lg');
	}
	if (size == 'standard') {
		$(objRef).attr('class', 'modal fade').attr('aria-labelledby',
		'myModalLabel');
		$('.modal-dialog').attr('class', 'modal-dialog');
	}
	if (size == 'small') {
		$(objRef).attr('class', 'modal fade bs-example-modal-sm').attr(
				'aria-labelledby', 'mySmallModalLabel');
		$('.modal-dialog').attr('class', 'modal-dialog modal-sm');
	}

}

/**
 * @param formId
 * @param action
 */
function manageButtons(action) {
	if (action == "view") {
		$("#div_btn_add").hide();
		$("#div_btn_new").hide();
		$("#div_btn_edit").show();
		$("#div_btn_next").hide();

	}
	if (action == "save") {
		$("#div_btn_add").hide();
		$("#div_btn_new").show();
		$("#div_btn_edit").hide();
		$("#div_btn_next").hide();

	}
	if (action == "add") {
		$("#div_btn_add").show();
		$("#div_btn_new").hide();
		$("#div_btn_edit").hide();
		$("#div_btn_next").hide();

	}
	if (action == "next") {
		$("#div_btn_add").hide();
		$("#div_btn_new").hide();
		$("#div_btn_edit").hide();
		$("#div_btn_next").show();
	}
	if (action == "hideall") {
		$("#div_btn_add").hide();
		$("#div_btn_new").hide();
		$("#div_btn_edit").hide();
		$("#div_btn_next").hide();
	}
}

/**
 * @param parentRef
 * @param status
 */
function disableAllFields(parentRef, status) {
	$(parentRef).find('input, textarea, select').prop("disabled", status);
}

function focus() {
	$(".has-error .form-control").each(function() {
		var filedId = $(this).attr("id");
		$("#" + filedId + "").focus();
		return false;
	});
}

function validation() {
	var flg = true;
	$(".required").each(function() {
		var filedId = $(this).attr("id");
		if ($("#" + filedId + "").val().trim() == "") {
			$("#form_div_" + filedId + "").addClass("has-error");
			$("#form_div_" + filedId + "_error").show();

			flg = false;
		} else {
			$("#form_div_" + filedId + "").removeClass("has-error");
			$("#form_div_" + filedId + "_error").hide();
		}

	});

	return flg;
}

function validateEmail() {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var flg = true;
	$(".email")
	.each(
			function() {
				var filedId = $(this).attr("id");

				if ($("#" + filedId + "").val() != null
						&& $("#" + filedId + "").val() != "") {

					if (re.test($("#" + filedId + "").val().trim()) == false) {
						$("#form_div_" + filedId + "").addClass(
						"has-error");
						$("#form_div_" + filedId + "_error").show();
						flg = false;
					} else {
						$("#form_div_" + filedId + "").removeClass(
						"has-error");
						$("#form_div_" + filedId + "_error").hide();
					}
				}
			});
	return flg;
}

var scrnWidth = parseInt($(window).width());
var sidebar = parseInt($('.main-sidebar').css('width'));
var diff = scrnWidth - sidebar;
$(".list-content-header").css("width", diff);

$("#btnAdd").on("click", null, null, function(e) {
	setTimeout(function() {
		//$('#form_div_stock_item_code .acontainer').find("input").focus();
		$("#code").focus();
		//$("#name").focus();
		$("#login_id").focus();
		$("#supplier_doc_no").focus();
		$("#request_by").focus();
		$("#form_div_supplier_code .acontainer").find("input").focus();
		$("#form_div_desti_code .acontainer").find("input").focus();
		//$("#form_div_source_code .acontainer").find("input").focus();
		$("#form_div_department_code .acontainer").find("input").focus();
		$("#form_div_desti_code .acontainer").find("input").focus();
		//$("#form_div_source_code .acontainer").find("input").focus();
		$('#f_name').focus();
		$("#adjust_by").focus();
		$("#form_div_stock_item_code").find(".acontainer input").focus();



	}, 1);
});

$("#btnNext").on("click", null, null, function(e) {
	setTimeout(function() {

		$("#form_div_supplier_code .acontainer").find("input").focus();
		$("#form_div_source_code .acontainer").find("input").focus();

	}, 1);
});

$("#btnDiscard").on("click", null, null, function(e) {
	setTimeout(function() {
		$("#name").focus();
		$("#login_id").focus();
		$("#supplier_doc_no").focus();
	}, 1);
});

$("#btnEdit").on("click", null, null, function(e) {
	setTimeout(function() {
		$("#name").focus();
		$("#login_id").focus();
		$("#supplier_doc_no").focus();
		$("#request_by").focus();
		$("#form_div_supplier_code .acontainer").find("input").focus();
		$("#form_div_desti_code .acontainer").find("input").focus();
		$("#form_div_source_code .acontainer").find("input").focus();
		$("#form_div_department_code .acontainer").find("input").focus();
		$("#supplier_doc_no").focus();
	}, 1);
});

$("#div_btn_edit #btnSave").on("click", null, null, function(e) {
	setTimeout(function() {
		$("#name").select();
		$("#login_id").select();
	}, 1);
});

function clearform() {
	$(".required").each(function() {
		var filedId = $(this).attr("id");
		$("#form_div_" + filedId + "").removeClass("has-error");
		$("#form_div_" + filedId + "_error").hide();
	});

}
function filterTime() {
	var time = "HH:mm:ss";
	if (settings['time_format'] == "0") {
		time = "h:mm:ss a";

	} else if (settings['time_format'] == "1") {
		time = "HH:mm:ss";
	} else if (settings['time_format'] == "2") {
		time = "h:mm a";
	} else if (settings['time_format'] == "3") {
		time = "h' h' mm a";
	}
	return time;
}

function getMySqlTimeFormat(time) {
	var second = time.split(" ")[1];
	var formatTime;
	var AMPM;
	var hours;
	if (settings['time_format'] == "0") {
		AMPM = time.split(" ")[1];
		hours = parseInt(time.split(" ")[0].split(":")[0]);
		hours = getTwentyFourFormat(AMPM, hours);
		formatTime = hours.toString() + ":" + time.split(" ")[0].split(":")[1]
		+ ":" + time.split(" ")[0].split(":")[2];
	} else if (settings['time_format'] == "1") {
		formatTime = time;
	} else if (settings['time_format'] == "2") {
		AMPM = time.split(" ")[1];
		hours = parseInt(time.split(" ")[0].split(":")[0]);
		hours = getTwentyFourFormat(AMPM, hours);
		formatTime = hours.toString() + ":" + time.split(" ")[0].split(":")[1]
		+ ":00";
	} else if (settings['time_format'] == "3") {
		var first = time.split(" ")[0];
		AMPM = time.split(" ")[3];
		hours = parseInt(first.substring(0, first.length - 1));
		hours = getTwentyFourFormat(AMPM, hours);
		formatTime = hours.toString() + ":" + time.split(" ")[2] + ":00";
	}

	return formatTime;

}
function getTwentyFourFormat(AMPM, hours) {
	if (AMPM.toUpperCase() == "PM" && hours < 12)
		hours = hours + 12;
	if (AMPM.toUpperCase() == "AM" && hours == 12)
		hours = hours - 12;
	return hours;
}

function getDateTime() {
	var time = filterTime();
	var datepick = get_date_format();
	var date = datepick.format;
	var dateTime = date + " " + time;
	return dateTime;
}
