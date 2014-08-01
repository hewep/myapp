'use strict';
/* Controllers */
define( function(){
	/** 用户信息 **/
	var MessageListCtrl = ['$rootScope','$scope', '$http', function($rootScope, $scope, $http){
		$scope.messages = [];
		
		$http({	method:'post',
			url:"topic/findByCateId",
			params:params
		}).success(function(data){
			var option = {page : data, url:"topic/findByCateId",params:params};
			$scope.topics = $.handlePage(option);
		}).error(function(){
			alert("网络连接失败");
		});
		
	}];
	
	return {messageList: MessageListCtrl};
});