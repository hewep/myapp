'use strict';
/* Controllers */
define(['app'], function(app){
	
	var IndexCtrl = ['$location', '$scope', '$http','$stateParams','checkUser', function($location, $scope, $http, $stateParams, checkUser){
		
		$scope.user = angular.copy(checkUser.getUser());
		
		//默认 进入自己的空间
		$scope.self = true;
		if($stateParams.user_id != $scope.user.id){  
			$scope.self = false;
			
			//访问别人的空间
			$http({	method:'post',
				url:"user/findById",
				params:{user_id: $stateParams.user_id}
			}).success(function(result){
				$scope.user = result.data;
			}).error(function(){
				alert("网络连接失败");
			});
		}
		
		$location.path("/space/info_center").search({"self":true});
		
	}];
	
	return {index: IndexCtrl};
});