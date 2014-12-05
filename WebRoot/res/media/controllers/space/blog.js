'use strict';
/* Blog Controllers */
define( function(){
	var BlogCtrl = ['$state','$scope', '$http', function($state, $scope, $http){
		$scope.blog = {};
		// 添加博客
		$scope.addBlog = function(){
			
		};
	}];
	
	return {addBlog: BlogCtrl};
});