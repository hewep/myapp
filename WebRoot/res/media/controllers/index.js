'use strict';
/* Controllers */
define( function(){
	// 话题类别
	var CategoryListCtrl = ['$scope', '$http', function($scope, $http){
		$scope.categorys = {};
		
		$http({	method:'post',
				url:"category/list?type=tree"
		}).success(function(result){
			$scope.categorys = angular.fromJson(result.data);
		}).error(function(){
			alert("网络连接失败");
		});
	}];
	
	//话题
	var TopicCtrl = ['$scope', '$http', function($scope, $http){
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
	
	return {topic : TopicCtrl, categorys: CategoryListCtrl};
});