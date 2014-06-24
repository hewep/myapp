'use strict';
/* Controllers */
define( function(){
	//话题
	var TopicCtrl = ['$scope', '$http','$location','$routeParams', function($scope, $http, $location,$routeParams){
		$scope.topic = {};
		$scope.categorys = {};
		
		$scope.changeCate = function(category){
			$scope.category = category;
		};
		
		$scope.save = function(){
			$scope.topic.content = $scope.editor.html();	// 通过kindEditor  指令引用
			$scope.topic.category_id = $("#type a.selected").attr("type");
			
			$http({method:'post',url:"topic/addOrUpdate", params:$scope.topic}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$location.path("/");
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		init();
		
		function init(){
			
			// 初始化 类型选项
			$("#type").delegate("a","click",function(){
				$(this).addClass("selected");
				$(this).closest("li").siblings().each(function(){
					$(this).find("a").removeClass("selected");
				});
			});
			
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
	var TopicInfoCtrl = ['$scope', '$http','$location','$routeParams','$sce',function($scope, $http, $location,$routeParams, $sce){
		$scope.topic;
		$scope.reply = {}; // 回复
		$scope.replies = [];	// 回复列表
		
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
			$scope.reply.content = $scope.editor.html();
			$scope.reply.topic_id = $scope.topic.id;
			if($.trim($scope.reply.content)){
				$http({	method:'post',
						url:"topic/reply",
						params:$scope.reply
				}).success(function(result){
					$scope.replies.push(result.reply);
					$scope.editor.html("");
				}).error(function(){
					alert("网络连接失败");
				});
			}else{
				alert("请输入内容!");
			}
		};
		$scope.addComment = function(){
			alert(angular.toJson($scope.comment));
		};
		$scope.showComment = function(reply){
			$scope.comment = {};		//  评论
			$scope.comment.reply_id = reply.id;
			$scope.user_name = "hhh";
			$("#comment_modal").modal('show');
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