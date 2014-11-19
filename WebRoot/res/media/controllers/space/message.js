'use strict';
/* Controllers */
define(["app"], function(app){
	/** 用户信息 **/
	var MessageListCtrl = ['$state','$scope', '$http', function($state, $scope, $http){
		$scope.state_messages = [];    //动态消息
		$scope.user_messages = [];	   //用户留言
		$scope.system_messages = [];   //系统留言
		
		//根据消息类型加载
		$scope.loadMessage = function(type){
			// $scope.user 由父controller 传递而来
			var params = {"type":type, "user_id": $scope.user.id};  
			$http({	method:'post',
				url:"message/findByUserId",
				params:params
			}).success(function(result){
				var option = {page : result.datas, url:"message/findByUserId",params:params};
				
				var messages = $.handlePage(option);
				if(type == 1){
					$scope.state_messages = messages;
				}else if(type == 2){
					$scope.user_messages = messages;
				}else if(type == 3){
					$scope.system_messages = messages
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		// 初始化空间动态
		$scope.loadMessage(1);
		
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
			});
		};
		
		//删除动态
		$scope.deleteMessage = function(message){
			if(!confirm("删除此条动态?"))return;
			
			var params = {msg_id:message.id};
			$http({	method:'post',
				url:"message/deleteMessage",
				params:params
			}).success(function(result){
				if(result.status == 1){
					$state.go($state.$current, null, { reload: true });  // 重载当前页面
				}else{
					alert(result.msg);
				}
			});
		};
		
		//显示 评论 窗口
		$scope.toggleComment = function(message){
			var collapse = "#collapse_"+message.id;
			var show =  $(collapse).bindSlide();
			if(show){
				var params = {type_id : message.id, type:2};
				$http({	method:'post',
					url:"comment/findByTypeId",
					params:params
				}).success(function(result){
					if(result.status == 1){
						message.comments = result.comments;
					}
				}).error(function(){
					alert("网络连接失败");
				});
			}
			
			
		};
		
		//添加评论
		$scope.comment = {};
		$scope.addComment = function(message){
			var content = $("#comment_"+message.id).text();
			if(!content){
				alert("请填写回复内容");return;
			}
			$scope.comment.type_id = message.id;
			$scope.comment.type = "2";    		// 消息评论
			$scope.comment.receiver_id = message.sender_id;
			$scope.comment.content = content;
			
			$http({	method:'post',
				url:"comment/addComment",
				params:$scope.comment
			}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					message.comments.push(result.comment);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	return {messageList: MessageListCtrl};
});