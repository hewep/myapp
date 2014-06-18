'use strict';
/* Controllers */
define(['plugins/jquery.mediaTable.min'], function(){
	var CategoryListCtrl = ['$scope', '$http', function($scope, $http){
		$scope.categorys = {};
		$scope.category = {};
		
		$http({method:'post',url:"category/list"}).success(function(result){
			$scope.categorys = result.data;
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.add = function(){	// 添加
			$scope.category = {};
		};
		$scope.edit = function(category){
			$scope.temp = category;		//存放原始对象
			$scope.category = angular.copy(category); // 对新对象编辑
		};
		$scope.del = function(index, category){
			if(!category)return;
			if(!confirm("删除所选记录"))return;
			
			$http({	method:'post', 
					url:"category/deleteById", 
					params:{id:category.id}
			}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$scope.categorys.splice(index, 1);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		$scope.save = function(){
			if(angular.equals($scope.temp, $scope.category)) return;  // 未修改则 返回;
			
			$http({ method:'post',
					url:"category/addOrUpdate",
					params:{"category":angular.toJson($scope.category)}
			}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$("#info_modal").modal('hide');
					if(result.data){
						$scope.categorys.push(result.data);
					}else{
						angular.copy($scope.category,$scope.temp);	// src, dest
					}
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	return {list : CategoryListCtrl};
});