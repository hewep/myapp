'use strict';

/* Controllers */
define( function(){
	var RegisterCtrl = ['$scope', '$http', function($scope, $http){
		$scope.user = {};
		$scope.submit = function(){
			/*$http({method:'post',url:"/register"}).success(function(data){
				$scope.users = data;
			}).error(function(){
				
			});*/
			alert("submit");
		};
		
	}];
	
	var CategoryCtrl = ['$scope', '$http', function(scope, http){
		
	}];
	
	var resolve = {
		delay: function($q){
			var delay = $q.defer(),
		    load = function(){
				/*require([
						'formValidate'
				],function(angular){
					FormValidation.init();
					delay.resolve();
				});*/
				delay.resolve();
		    };
		    load();
		    return delay.promise;
		}
	};
	return {categoryList: CategoryCtrl, register: RegisterCtrl, resolve: resolve};
	
});