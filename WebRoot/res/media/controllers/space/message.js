'use strict';
/* Controllers */
define(["app"], function(app){
	/** 用户信息 **/
	var MessageListCtrl = ['$state','$scope', '$http', function($state, $scope, $http){
		$scope.messages = [];
		
		//根据消息类型加载
		$scope.loadMessage = function(type){
			var params = {"type":type};
			
			$http({	method:'post',
				url:"message/findByUserId",
				params:params
			}).success(function(result){
				var option = {page : result.datas, url:"message/findByUserId",params:params};
				$scope.messages = $.handlePage(option);
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		// 发表 说说
		$scope.message = {};
		$scope.addMessage = function(type){
			var content = $("#state").text();
			if(!content){
				alert("请填写内容");return;
			}
			$scope.message.content = content;
			$scope.message.msg_type = type;
			$http({	method:'post',
				url:"message/addMessage",
				params:$scope.message
			}).success(function(result){
				if(result.status == 1){
					//$state.reload();   这个方法没用 , 不知道存在的价值 
					$state.go($state.$current, null, { reload: true });  // 重载当前页面
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		// 初始化空间动态
		$scope.loadMessage(1);
	}];
	
	return {messageList: MessageListCtrl};
});