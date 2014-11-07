'use strict';
/* Controllers */
define(['app'], function(app){
	
	var IndexCtrl = ['$state', '$scope', '$http','$stateParams','checkUser', function($state, $scope, $http, $stateParams, checkUser){
		$scope.user = angular.copy(checkUser.getUser());
		//默认 进入自己的空间
		$scope.self = true;
		
		//跳转的转台
		var dest_state = $state.$current.name;
		if($state.$current.name.indexOf(".") < 0){
			dest_state = "space.info_center";
		}
		
		if($stateParams.user_id != $scope.user.id){  
			
			$scope.self = false;
			
			//访问别人的空间
			$http({	method:'post',
				url:"user/findById",
				params:{user_id: $stateParams.user_id}
			}).success(function(result){
				$scope.user = result.data;
				
				$state.go(dest_state,{self: false});
			}).error(function(){
				alert("网络连接失败");
			});
		}else{
			$state.go(dest_state,{self: true});
		}
		
		//用户留言
		$scope.message = {};
		$scope.addMessage = function(type){
			$scope.message.receiver_id = $scope.user.id;
			$scope.message.msg_type = type;
			
			$http({	method:'post',
				url:"message/addMessage",
				params:$scope.message
			}).success(function(result){
				$("#message_modal").modal('hide');
				alert(result.msg);
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	return {index: IndexCtrl};
});