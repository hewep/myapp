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
	
	//用户注册
	var RegisterCtrl = ['$scope', '$http' ,'$state',function($scope, $http,$state){
		$scope.user = {};
		$scope.register = function(){
			var rpassword = $("input[name=rpassword]").val();
			if(rpassword != $scope.user.password){
				alert("密码输入不一致");return;
			}
			
			$http({method:'post',url:"user/register", params:$scope.user}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$state.go("index", null, {reload:true} );
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
	}];
	
	var SpaceCtrl = ['$scope', '$http', function($scope, $http){
		
		
	}];
	return {categorys: CategoryListCtrl, register: RegisterCtrl, space:SpaceCtrl};
});