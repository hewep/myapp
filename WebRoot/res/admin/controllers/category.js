'use strict';
/* Controllers */
define(['dataTable'], function(){
	var CategoryListCtrl = ['$scope', '$http', function($scope, $http){
		$scope.categorys = {};
		alert($("#categorys").attr("id"));
		$("#categorys").dataTable({ 
			"ajax": "category/list",
			"columns" : [
			       {"data" : "id"},
			       {"data" : "name"},
			       {"data" : "pid"},
			       {"data" : "type"},
			       {"data" : "content"}
			 ]
        });
		
		
		/*$http({method:'post',url:"category/list"}).success(function(data){
			
		}).error(function(){
			alert("网络连接失败");
		});*/
		
	}];
	
	return {list : CategoryListCtrl};
});