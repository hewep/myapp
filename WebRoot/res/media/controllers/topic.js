'use strict';
/* Controllers */
define( function(){
	//添加话题
	var TopicCtrl = ['$scope', 'checkUser','$http','$state', function($scope, checkUser, $http, $state){
		$scope.topic = {};
		$scope.categorys = {};
		
		$scope.changeCate = function(category){
			$scope.category = category;
		};
		
		$scope.save = function(){
			$scope.topic.content = $scope.editor.html();	// 通过kindEditor  指令引用
			if(!$scope.topic.content){
				$scope.topic_content_error = true;		// 话题错误信息显示
				return;
			}else{
				$scope.topic_content_error = false;
			}
			$scope.topic.category_id = $("#type a.selected").attr("type");
			
			$http({method:'post',
				   url:"topic/addOrUpdate", 
				   params:$scope.topic}
			).success(function(result){
				
				if(result.status == 1){
					$state.go("index.topic_list",{category_id: result.data.category_id});
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		init();
		
		function init(){
			// 初始化 类别信息
			$http({	method:'post',
				url:"category/list?type=tree"
			}).success(function(result){
				$scope.categorys = angular.fromJson(result.data);
				$scope.category = $scope.categorys[0];
			}).error(function(){
				alert("网络连接失败");
			});
			
		}
	}];
	
	// 话题信息 及 回复
	var TopicInfoCtrl = ['$scope','checkUser', '$http','$stateParams',function($scope, checkUser,$http, $stateParams){
		$scope.topic;
		$scope.replies = [];		// 回复列表
		
		$http({	method:'post',
			url:"topic/info",
			params:{topic_id:$stateParams.topic_id}
		}).success(function(result){
			$scope.topic = result.topic;
			$scope.replies = result.replies;
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.answer = function(){
			$scope.reply = {}; // 回复
			$scope.reply.content = $scope.editor.html();
			$scope.reply.topic_id = $scope.topic.id;
			
			if($.trim($scope.reply.content)){
				$http({	method:'post',
						url:"topic/addReply",
						params:$scope.reply
				}).success(function(result){
					if(result.status == 1){
						$scope.replies.push(result.reply);	// 显示新添加的回复
						$scope.editor.html("");
					}else{
						alert(result.msg);
					}
				}).error(function(){
					alert("网络连接失败");
				});
				
			}else{
				$scope.answer_content_error = true;		// 回复错误信息显示
			}
		};
		
		$scope.currReply;  	// 临时存储要评论的 回复, 便于更新新添加的评论
		$scope.currComment;  	// 临时存储要回复的 评论, 便于更新新添加的评论
		$scope.addComment = function(){
			
			if(!$scope.comment.content){
				$scope.comment_content_error = true;return;
			}
			$http({	method:'post',
				url:"topic/addComment",
				params:$scope.comment
			}).success(function(result){
				if(result.status == 1){
					if($scope.currComment){				// 回复评论
						if(!$scope.currComment.subComments){
							$scope.currComment.subComments = [];
						}
						$scope.currComment.subComments.push(result.comment);
					}else{								// 评论回复
						$scope.currReply.comments.push(result.comment);	// 显示 新添加的 评论
					}
					
					$("#comment_modal").modal('hide');
				}
				$scope.currComment = null;
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		$scope.showComment = function(reply, comment, receiver_id){
			
			if(checkUser.isLogin()){
				$scope.currReply = reply;
				$scope.currComment = comment;
				
				$scope.comment = {};		//  评论
				$scope.comment.type_id = reply.id;
				$scope.comment.type = 1;    
				$scope.comment.pid = 0;
				if(comment){				// 回复评论 的 pid 为 一级评论的 id
					$scope.comment.pid = comment.id;
					if(receiver_id){		// 回复二级评论 则评论的 目标用户 为subComment 的 src_user_id
						$scope.comment.receiver_id = receiver_id;
					}else{
						$scope.comment.receiver_id = comment.sender_id;
					}
				}
				
				$scope.comment_content_error = false;
				
				$("#comment_modal").modal('show');
			}else{
				$("#login_modal").modal('show');
			}
		};
	}];
	
	// 话题列表信息
	var TopicListCtrl = ['$scope', '$http','$stateParams', function($scope, $http, $stateParams){
		$scope.topics = {};
		
		var params = {category_id:$stateParams.category_id};
		$http({	method:'post',
			url:"topic/findByCateId",
			params:params
		}).success(function(result){
			$scope.category_name = result.category_name;
			var option = {page : result.datas, url:"topic/findByCateId",params:params};
			$scope.topics = $.handlePage(option);
		}).error(function(){
			alert("网络连接失败");
		});
	}];
	return {addTopic : TopicCtrl,topicList: TopicListCtrl, topicInfo:TopicInfoCtrl};
});