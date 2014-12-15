'use strict';
/* Blog Controllers */
define( function(){
	var BlogCtrl = ['$state','$scope', '$http', '$stateParams','ztree',function($state, $scope, $http,$stateParams, ztree){
		$scope.blog = {};
		//编辑博客
		if($stateParams.blog_id){
			
			$http({	method:'post',
				url:"blog/findById",
				params:{blog_id: $stateParams.blog_id}
			}).success(function(result){
				$scope.blog = result.blog;
			}).error(function(){
				alert("网络连接失败");
			});
			
		}
		//获取博客类别
		$http({	method:'post',
			url:"category/findCatesByType",
			params:{type: "blog"}
		}).success(function(result){
			
			var option = {id: 'cates_tree', data: result.data};
			ztree.init(option);     // ztree 服务
			
		}).error(function(){
			alert("网络连接失败");
		});
		
		// 添加博客
		$scope.addBlog = function(){
			
			$scope.blog.category_id = ztree.getSelectNodeId();
			
			$http({	method:'post',
				url:"blog/addOrUpdate",
				params:$scope.blog
			}).success(function(result){
				if(result.status == 1){
					$state.go("space.blog_list");
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	var BlogListCtrl = ['$state','$scope', '$http', function($state, $scope, $http){
		
		$http({	method:'post',
			url:"blog/findByCateId",
			params:{cate_id: 0}
		}).success(function(result){
			$scope.blogs = result.blogs;
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.deleteBlog = function(blog_id){
			
			if(!confirm("文章删除后无法恢复, 确认删除?"))
				return;
			
			$http({	method:'post',
				url:"blog/deleteById",
				params:{blog_id: blog_id}
			}).success(function(result){
				if(result.status == 1){
					alert("00");
					$state.go($state.current.name, $state.params, { reload: true });
				}else {
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	var BlogInfoCtrl = ['$stateParams','$scope', '$http','sh', function($stateParams, $scope, $http, sh){
		
		$http({	method:'post',
			url:"blog/findById",
			params:{blog_id: $stateParams.blog_id}
		}).success(function(result){
			$scope.blog = result.blog;
			
			sh.hightlight($(".blog-content")); // syntaxhighlight 定义在 service 服务中
		}).error(function(){
			alert("网络连接失败");
		});
		
			
	}];
	
	return {add: BlogCtrl, list: BlogListCtrl, info:BlogInfoCtrl};
});