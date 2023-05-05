'format cjs';
'use strict';

(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    define(['angular', 'jquery-minicolors'], factory);
  } else if (typeof exports === 'object') {
    module.exports = factory(require('angular'), require('jquery-minicolors'));
    module.exports = 'minicolors';
  } else {
    root.angularMinicolors = factory(root.angular, root.jqueryMinicolors);
  }
})(this, function(angular) {

  angular.module('minicolors', []);

  angular.module('minicolors').provider('minicolors', function() {
    this.defaults = {
      theme: 'bootstrap',
      position: 'top left',
      defaultValue: '#ffffff',
      animationSpeed: 50,
      animationEasing: 'swing',
      change: null,
      changeDelay: 0,
      control: 'hue',
      hide: null,
      hideSpeed: 100,
      inline: false,
      letterCase: 'lowercase',
      opacity: false,
      show: null,
      showSpeed: 100
    };

    this.$get = function() {
      return this;
    };

  });

  angular.module('minicolors').directive('minicolors', ['minicolors', '$timeout', function(minicolors, $timeout) {
    return {
      require: '?ngModel',
      restrict: 'A',
      priority: 1, //since we bind on an input element, we have to set a higher priority than angular-default input
      link: function(scope, element, attrs, ngModel) {

        var inititalized = false;

        //gets the settings object
        var getSettings = function() {
          var config = angular.extend({}, minicolors.defaults, scope.$eval(attrs.minicolors));
          return config;
        };

        //what to do if the value changed
        ngModel.$render = function() {

          //we are in digest or apply, and therefore call a timeout function
          $timeout(function() {
            var color = ngModel.$viewValue;
            element.minicolors('value', color);
          }, 0, false);
        };

        //init method
        var initMinicolors = function() {

          if (!ngModel) {
            return;
          }
          var settings = getSettings();
          settings.change = function(hex) {
            scope.$apply(function() {
              ngModel.$setViewValue(hex);
            });
          };

          //destroy the old colorpicker if one already exists
          if (element.hasClass('minicolors-input')) {
            element.minicolors('destroy');
          }

          // Create the new minicolors widget
          element.minicolors(settings);

          // are we inititalized yet ?
          //needs to be wrapped in $timeout, to prevent $apply / $digest errors
          //$scope.$apply will be called by $timeout, so we don't have to handle that case
          if (!inititalized) {
            $timeout(function() {
              var color = ngModel.$viewValue;
              element.minicolors('value', color);
            }, 0);
            inititalized = true;
            return;
          }
        };

        initMinicolors();
        //initital call

        // Watch for changes to the directives options and then call init method again
        scope.$watch(getSettings, initMinicolors, true);
      }
    };
  }]);
});

function domcolors(str){
	var dc={aliceblue:'#F0F8FF',antiquewhite:'#FAEBD7',aqua:'#00FFFF',
			aquamarine:'#7FFFD4',azure:'#F0FFFF',beige:'#F5F5DC',
			bisque:'#FFE4C4',black:'#000000',blanchedalmond:'#FFEBCD',
			blue:'#0000FF',blueviolet:'#8A2BE2',brown:'#A52A2A',
			burlywood:'#DEB887',cadetblue:'#5F9EA0',chartreuse:'#7FFF00',
			chocolate:'#D2691E',coral:'#FF7F50',cornflowerblue:'#6495ED',
			cornsilk:'#FFF8DC',crimson:'#DC143C',cyan:'#00FFFF',
			darkblue:'#00008B',darkcyan:'#008B8B',darkgoldenrod:'#B8860B',
			darkgray:'#A9A9A9',darkgreen:'#006400',darkkhaki:'#BDB76B',
			darkmagenta:'#8B008B',darkolivegreen:'#556B2F',darkorange:'#FF8C00',
			darkorchid:'#9932CC',darkred:'#8B0000',darksalmon:'#E9967A',
			darkseagreen:'#8FBC8F',darkslateblue:'#483D8B',darkslategray:'#2F4F4F',
			darkturquoise:'#00CED1',darkviolet:'#9400D3',deepskyblue:'#00BFFF',
			dimgray:'#696969',dodgerblue:'#1E90FF',firebrick:'#B22222',
			floralwhite:'#FFFAF0',forestgreen:'#228B22',fuchsia:'#FF00FF',
			gainsboro:'#DCDCDC',ghostwhite:'#F8F8FF',gold:'#FFD700',
			goldenrod:'#DAA520',gray:'#808080',green:'#008000',
			greenyellow:'#ADFF2F',honeydew:'#F0FFF0',hotpink:'#FF69B4',
			indianred:'#CD5C5C',indigo:'#4B0082',ivory:'#FFFFF0',
			khaki:'#F0E68C',lavender:'#E6E6FA',lavenderblush:'#FFF0F5',
			lawngreen:'#7CFC00',lemonchiffon:'#FFFACD',lightblue:'#ADD8E6',
			lightcoral:'#F08080',lightcyan:'#E0FFFF',lightgoldenrodyellow:'#FAFAD2',
			lightgreen:'#90EE90',lightgrey:'#D3D3D3',lightpink:'#FFB6C1',
			lightsalmon:'#FFA07A',lightseagreen:'#20B2AA',lightskyblue:'#87CEFA',
			lightslategray:'#778899',lightsteelblue:'#B0C4DE',lightyellow:'#FFFFE0',
			lime:'#00FF00',limegreen:'#32CD32',linen:'#FAF0E6',
			magenta:'#FF00FF',maroon:'#800000',mediumauqamarine:'#66CDAA',
			mediumblue:'#0000CD',mediumorchid:'#BA55D3',mediumpurple:'#9370D8',
			mediumseagreen:'#3CB371',mediumslateblue:'#7B68EE',mediumspringgreen:'#00FA9A',
			mediumturquoise:'#48D1CC',mediumvioletred:'#C71585',midnightblue:'#191970',
			mintcream:'#F5FFFA',mistyrose:'#FFE4E1',moccasin:'#FFE4B5',
			navajowhite:'#FFDEAD',navy:'#000080',oldlace:'#FDF5E6',
			olive:'#808000',olivedrab:'#688E23',orange:'#FFA500',
			orangered:'#FF4500',orchid:'#DA70D6',palegoldenrod:'#EEE8AA',
			palegreen:'#98FB98',paleturquoise:'#AFEEEE',palevioletred:'#D87093',
			papayawhip:'#FFEFD5',peachpuff:'#FFDAB9',peru:'#CD853F',
			pink:'#FFC0CB',plum:'#DDA0DD',powderblue:'#B0E0E6',
			purple:'#800080',red:'#FF0000',rosybrown:'#BC8F8F',
			royalblue:'#4169E1',saddlebrown:'#8B4513',salmon:'#FA8072',
			sandybrown:'#F4A460',seagreen:'#2E8B57',seashell:'#FFF5EE',
			sienna:'#A0522D',silver:'#C0C0C0',skyblue:'#87CEEB',
			slateblue:'#6A5ACD',slategray:'#708090',snow:'#FFFAFA',
			springgreen:'#00FF7F',steelblue:'#4682B4',tan:'#D2B48C',
			teal:'#008080',thistle:'#D8BFD8',tomato:'#FF6347',
			turquoise:'#40E0D0',violet:'#EE82EE',wheat:'#F5DEB3',
			white:'#FFFFFF',whitesmoke:'#F5F5F5',yellow:'#FFFF00',
			green:'#9ACD32'};
    return dc[str.toLowerCase()];
}