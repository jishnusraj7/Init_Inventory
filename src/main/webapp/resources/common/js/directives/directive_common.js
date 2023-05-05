
/*Numeric and Decimal validation*/
mrpCommonApp.directive('validNumber', function() {
    return {
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
        if(!ngModelCtrl) {
          return; 
        }
        ngModelCtrl.$parsers.push(function(val) {
          if (angular.isUndefined(val)) {
              var val = '';
          }
          var clean = val.replace(/[^-0-9\.]/g, '');
          var negativeCheck = clean.split('-');
			var decimalCheck = clean.split('.');
          if(!angular.isUndefined(negativeCheck[1])) {
              negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
              clean =negativeCheck[0] + '-' + negativeCheck[1];
              if(negativeCheck[0].length > 0) {
              	clean =negativeCheck[0];
              }
          }
            
          if(!angular.isUndefined(decimalCheck[1])) {
              decimalCheck[1] = decimalCheck[1].slice(0,settings['decimalPlace']);
              clean =decimalCheck[0] + '.' + decimalCheck[1];
          }
          if(val==".")
        	  {
        	  clean="0.";
        	  }

          if (val !== clean) {
            ngModelCtrl.$setViewValue(clean);
            ngModelCtrl.$render();
          }
          return clean;
        });

        element.bind('keypress', function(event) {
          if(event.keyCode === 32) {
            event.preventDefault();
          }
        });
      }
    };
  });

/*Capitalize Text*/
mrpCommonApp.directive('capitalize', function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, modelCtrl) {
          var capitalize = function(inputValue) {
            if (inputValue == undefined) inputValue = '';
            var capitalized = inputValue.toUpperCase();
            if (capitalized !== inputValue) {
              modelCtrl.$setViewValue(capitalized);
              modelCtrl.$render();
            }
            return capitalized;
          }
          modelCtrl.$parsers.push(capitalize);
          capitalize(scope[attrs.ngModel]); // capitalize initial value
        }
      };
    });

mrpCommonApp.filter('cpmnyDateformat', function($filter) {    
    var angularDateFilter = $filter('date');
    return function(theDate) {
    	var datesaperator=settings['DateSeparator'];
    	
    	var date ="yyyy/MM/dd";
    	
    	if(settings['DateFormat'] == "0"){
    		date = "dd"+datesaperator+"MM"+datesaperator+"yyyy";
    	}else if(settings['DateFormat'] == "1"){
    		date ="MM"+datesaperator+"dd"+datesaperator+"yyyy";
    	}else if(settings['DateFormat'] == "2"){
    		date = "yyyy"+datesaperator+"MM"+datesaperator+"dd";
    	}
       return angularDateFilter(theDate, date);
    }
});

mrpCommonApp.filter('cpmnycurrency', function($filter) {    
    var angularcurrencyFilter = $filter('currency');
    return function(theDate) {
    	
       return angularcurrencyFilter(theDate, settings['currencySymbol']);
    }
});


mrpCommonApp.directive('datePicker', [function() {
	  return {
		  controller: function ($scope,$http) {
	
		    	    return $scope;
		        },
		       
	    link: function(scope, elem, attrs ,controller) {
	  
	    	var calenderPos = "down";
	    	if(attrs.calenderUpDown != undefined){
	    		calenderPos = attrs.calenderUpDown;
	    	}
	    	var dateFormat = get_date_format();
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
	    		"autoApply" : true,
	    		"linkedCalendars" : false,
	    		 "drops" :calenderPos,
	    	}, function(start, end, label) {
	    	});
	    		
	    }
	  };
	}]);



mrpCommonApp.directive('daterangePickerbft', [function() {
	return {
		controller: function ($scope,$http) {

			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var dateFormat = get_date_format();
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);
			var yr=new Date().getFullYear()-10;

			//controller.formData.prodDate = dateForm(curDate);
			$(elem).inputmask({
				mask : dateFormat.mask,
				placeholder : dateFormat.format,
				alias : "date",
			});

			$(elem).daterangepicker({
				"format" : dateFormat.format,
				"drops" : "down",
				"autoclose": true,
				"singleDatePicker" : true,
				"showDropdowns" : true,
				"autoApply" : false,
				"linkedCalendars" : false,
				"maxDate": dateFormat.mindate,
				"minDate": new Date(yr, 0, 1)
			}, function(start, end, label) {
			});
		}
	};
}]);




mrpCommonApp.directive('daterangePicker', [function() {
	  return {
		  controller: function ($scope,$http) {
	
		    	    return $scope;
		        },
		       
	    link: function(scope, elem, attrs ,controller) {
	    	/*elem.on('click',function(event){
	    		if(event!=9){*/
	    	var calenderPos = "down";
	    	if(attrs.calenderUpDown != undefined){
	    		calenderPos = attrs.calenderUpDown;
	    	}
	    	var dateFormat = get_date_format();
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
	    		"autoApply" : true,
	    		"linkedCalendars" : false,
	    		 
	    		 "drops" :calenderPos,
	    	}, function(start, end, label) {
	    	});
	    		/*}});*/	
	    }
	  };
	}]);


mrpCommonApp.directive('selectOnClick', ['$window', function ($window) {
    return function (scope, element, attrs) {
      element.bind('click', function () {
        if (!$window.getSelection().toString()) {
          this.setSelectionRange(0, this.value.length)
        }
      });
    };
  }]);


mrpCommonApp.directive('rowAdd', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 9 || event.which === 43) {
                scope.$apply(function (){
                    scope.$eval(attrs.rowAdd);
                });

                event.preventDefault();
            }
        });
    };
});


mrpCommonApp.directive('rowDelete', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 45) {
                scope.$apply(function (){
                    scope.$eval(attrs.rowDelete);
                });

                event.preventDefault();
            }
        });
    };
});



mrpCommonApp.directive('rowSave', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
        
     if ((event.which == '113' || event.which == '0' ) && (event.ctrlKey || event.metaKey) ){
            
                scope.$apply(function (){
               
                    scope.$eval(attrs.rowSave);
                });

                event.preventDefault();
                
     }
        });
    };
});

mrpCommonApp.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});