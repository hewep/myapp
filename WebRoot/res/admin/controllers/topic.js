'use strict';
/* Controllers */
define( function(){
	var TopicCtrl = ['$scope', '$http', function($scope, $http){
		$scope.topic = {};
		
		$scope.save = function(){
			$scope.topic.content = $scope.editor.html();
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
			$("#type").delegate("a","click",function(){
				$(this).addClass("selected");
				$(this).siblings().each(function(){
					$(this).removeClass("selected");
				});
			});
		}
	}];
	
	return {topic : TopicCtrl};
});