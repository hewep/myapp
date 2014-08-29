'use strict';
/* Controllers */
define( function(){
	var TopicListCtrl = ['$scope', '$http', function($scope, $http){
		$scope.topics = {};
		$scope.topic = {};
		
		$http({method:'post',url:"topic/list"}).success(function(result){
			$scope.topics = result.data;
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.add = function(){	// 添加
			$scope.topic = {};
		};
		$scope.edit = function(topic){
			$scope.temp = topic;		//存放原始对象
			$scope.topic = angular.copy(topic); // 对新对象编辑
		};
		$scope.del = function(index, topic){
			if(!topic)return;
			if(!confirm("删除所选记录"))return;
			
			$http({	method:'post', 
					url:"topic/deleteById", 
					params:{id:topic.id}
			}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$scope.topics.splice(index, 1);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		$scope.save = function(){
			if(angular.equals($scope.temp, $scope.topic)) return;  // 未修改则 返回;
			
			$http({ method:'post',
					url:"topic/addOrUpdate",
					params:{"topic":angular.toJson($scope.topic)}
			}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$("#info_modal").modal('hide');
					if(result.data){
						$scope.topics.push(result.data);
					}else{
						angular.copy($scope.topic,$scope.temp);	// src, dest
					}
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	return {list : TopicListCtrl};
});