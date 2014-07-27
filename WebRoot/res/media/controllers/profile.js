'use strict';
/* Controllers */
define( function(){
	/** 用户信息 **/
	var UserInfoCtrl = ['$rootScope','$scope', '$http', function($rootScope, $scope, $http){
		
		$scope.user = angular.copy($rootScope.currentUser);
		// 保存用户 基本信息
		$scope.saveBasicInfo = function(){

			$http({	method:'post',
				url:"user/updateUser",
				params:$scope.user
				
			}).success(function(result){
				if(result.status == 1){
					angular.copy($scope.user, $rootScope.currentUser);
					$("li a[href='#contact_info']").tab('show');
				}else{
					alert("保存失败");
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	return {userInfo: UserInfoCtrl};
});