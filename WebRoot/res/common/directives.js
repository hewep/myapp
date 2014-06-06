'use strict';

/* Directives */
var directives = angular.module('CommDirectives',[]);
	directives.directive('ensureUnique', ['$http',function($http){
		return function($scope, $element, $attr, $ctrl){
			$element.on('focusout', function(){
				var item = $attr.ensureUnique;
				var key = item.splite(':')[0];
				var url = item.splite(':')[1];
				if(email){
					$http({
						method : 'POST',
						url : url,
						params : {
							key : $("input[name=email]").val()
						}
					}).success(function(result, status, headers, cfg) {
						if(result.exist){
							$scope.unique = true;
						}else{
							$scope.unique = false;
						}
					});
				}
				
			});
		};
	}]);
