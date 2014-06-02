'use strict';

/* Directives */
define( function(){
	
	var ensureUnique = ['$http',function($http){
		return {
			require : 'ngModel',
			link : function(scope, element, attrs, controller){
				scope.$watch(attrs.ngModel, function() {
					alert(attrs.ensureUnique);return;
					$http({
						method : 'POST',
						url : '/user/getByEmail',
						params : {
							'email' : attrs.ensureUnique
						}
					}).success(function(result, status, headers, cfg) {
						alert(result.exist);
						controller.$setValidity('unique', !result.exist);
					}).error(function(data, status, headers, cfg) {
						controller.$setValidity('unique', false);
					});
				});
			}
		};
	}];
	
	return {ensureUnique: ensureUnique};
});