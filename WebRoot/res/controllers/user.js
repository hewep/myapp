'use strict';
var UserCtrls = angular.module('UserCtrls',[]);
	UserCtrls.controller('RegisterCtrl',['$scope', '$http' ,'$location',function($scope, $http,$location){
		$scope.user = {};
		$scope.register = function(){
			var rpassword = $("input[name=rpassword]").val();
			if(rpassword != $scope.user.password){
				alert("密码输入不一致");return;
			}
			
			$http({method:'post',url:"user/addOrUpdate", params:$scope.user}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$location.path("/");
				}
			}).error(function(){
				alert("网络连接失败");
			});
			
		};
		
	}]);
	
	UserCtrls.directive('uniqueEmail', ['$http',function($http){
		return function($scope, $element, $attr, $ctrl){
			$element.on('focusout', function(){
				var email = $scope.user.email;
				if(email){
					$http({
						method : 'POST',
						url : '/user/getByEmail',
						params : {
							'email' : $("input[name=email]").val()
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
	
/* Controllers 
define( function(){
	var UserListCtrl = ['$scope', '$http', function($scope, $http){
		/*$http({method:'GET',url:"/test/user"}).success(function(data){
			$scope.users = data;
		}).error(function(){
			
		});
	}];
	var UserInfoCtrl = ['$scope', '$http','$routeParams', function($scope, $http, $routeParams){
		
		/*$http({method:'GET', url:"/test/user/info", 
			   params : {userId : $routeParams.userId}}).success(function(data){
			   		$scope.user = data;
			   });
	}];
	return {list : UserListCtrl, info: UserInfoCtrl};
	
});*/
