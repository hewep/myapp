'use strict';

/* Controllers */
define( function(){
	var UserListCtrl = ['$scope', '$http', function($scope, $http){
		/*$http({method:'GET',url:"/test/user"}).success(function(data){
			$scope.users = data;
		}).error(function(){
			
		});*/
	}];
	var UserInfoCtrl = ['$scope', '$http','$routeParams', function($scope, $http, $routeParams){
		
		/*$http({method:'GET', url:"/test/user/info", 
			   params : {userId : $routeParams.userId}}).success(function(data){
			   		$scope.user = data;
			   });*/
	}];
	
	return {list : UserListCtrl, info: UserInfoCtrl};
	
});
