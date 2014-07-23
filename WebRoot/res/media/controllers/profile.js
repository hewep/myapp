'use strict';
/* Controllers */
define( function(){
	/** 用户信息 **/
	var UserInfoCtrl = ['$rootScope','$scope', '$http', function($rootScope, $scope, $http){
		// 保存用户 基本信息
		$scope.saveBasicInfo = function(){
			$http.post('user/editUser', $rootScope.currentUser).success(function(result){
				alert(result.msg);
			});
		};
	}];
	
	return {userInfo: UserInfoCtrl};
});