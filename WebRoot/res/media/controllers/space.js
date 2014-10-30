'use strict';
/* Controllers */
define(['app'], function(app){
	
	var IndexCtrl = ['$scope', '$http', function( $scope, $http){
		
		
	}];
	
	app.filter('gender', ['$sce', function($sce){
		return function(text){
			return $sce.trustAsHtml(text);
		};
	}]);
	return {index: IndexCtrl};
});