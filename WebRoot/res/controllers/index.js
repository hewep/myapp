'use strict';
/* Controllers */
define( function(){
	var TopicCtrl = ['$scope', '$http', function($scope, $http){
		$scope.topic = {content: "hhhhh"};
		
		$scope.save = function(){
			$scope.topic.content = $scope.editor.html();
			
			alert(angular.toJson($scope.topic));
		};
	}];
	return {topic : TopicCtrl};
});