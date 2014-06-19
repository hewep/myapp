'use strict';
/* Controllers */
define( function(){
	// 话题类别
	var CategoryListCtrl = ['$scope', '$http', function($scope, $http){
		$scope.categorys = {};
		
		$http({	method:'post',
				url:"category/list?type=tree"
		}).success(function(result){
			$scope.categorys = angular.fromJson(result.data);
		}).error(function(){
			alert("网络连接失败");
		});
	}];
	return {categorys: CategoryListCtrl};
});