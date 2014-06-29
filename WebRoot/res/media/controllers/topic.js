'use strict';
/* Controllers */
define( function(){
	//添加话题
	var TopicCtrl = ['$scope', 'checkUser','$http','$location','$routeParams', function($scope, checkUser, $http, $location,$routeParams){
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
					$location.path("topic_list/"+result.data.category_id);
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
	var TopicInfoCtrl = ['$scope','checkUser', '$http','$location','$routeParams','$sce',function($scope, checkUser,$http, $location,$routeParams, $sce){
		$scope.topic;
		$scope.replies = [];		// 回复列表
		
		$http({	method:'post',
			url:"topic/info",
			params:{topic_id:$routeParams.topic_id}
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
						url:"topic/reply",
						params:$scope.reply
				}).success(function(result){
					if(result.status == 1){
						$scope.replies.push(result.reply);	// 显示新添加的回复
						$scope.editor.html("");
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
				url:"topic/comment",
				params:$scope.comment
			}).success(function(result){
				if(result.status == 1){
					if($scope.currComment){
						if(!$scope.currComment.subComments){
							$scop.currComment.subComments = [];
						}
						$scope.currComment.subComments.push(result.comment);
					}else{
						$scope.currReply.comments.push(result.comment);	// 显示 新添加的 评论
					}
					
					$("#comment_modal").modal('hide');
				}
				$scope.currComment = null;
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		$scope.showComment = function(reply, comment, dest_user_id){
			
			if(checkUser.isLogin()){
				$scope.currReply = reply;
				$scope.currComment = comment;
				
				$scope.comment = {};		//  评论
				$scope.comment.reply_id = reply.id;
				if(comment){				// 回复评论 的 pid 为 一级评论的 id
					$scope.comment.pid = comment.id;
					if(dest_user_id){		// 回复二级评论 则评论的 目标用户 为subComment 的 src_user_id
						$scope.comment.dest_user_id = dest_user_id;
					}else{
						$scope.comment.dest_user_id = comment.src_user_id;
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
	var TopicListCtrl = ['$scope', '$http','$location','$routeParams', function($scope, $http, $location,$routeParams){
		$scope.topics = {};
		
		$http({	method:'post',
			url:"topic/list",
			params:{category_id:$routeParams.category_id}
		}).success(function(data){
			$scope.topics = data;
		}).error(function(){
			alert("网络连接失败");
		});
	}];
	return {topic : TopicCtrl,topicList: TopicListCtrl, topicInfo:TopicInfoCtrl};
});