'use strict';
/* Controllers */
define( function(){
	var IndexCtrl = ['$state','$scope', '$http', function($state, $scope, $http){
		
		$http({	method:'post',
			url:"account"
		}).success(function(result){
			$scope.info = result.info;
		}).error(function(){
			alert("网络连接失败");
		});
		
		//跳转的转台
		var dest_state = $state.$current.name;
		if($state.$current.name.indexOf(".") < 0){
			dest_state = "index.categorys";
		}
		
		$scope.topics = {};
		$scope.search = function(){
			var search = $("#search").val();
			var params = {search:search};
			$http({	method:'post',
				url:"topic/findBySearch",
				params:params
			}).success(function(result){
				$scope.category_name = result.category_name;
				var option = {page : result.datas, url:"topic/findBySearch",params:params};
				$scope.topics = $.handlePage(option);
				
				$state.go("index.search");
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		$state.go(dest_state);
	}];
	
	return {index:IndexCtrl};
});