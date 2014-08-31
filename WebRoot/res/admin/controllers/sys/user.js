'use strict';
/* Controllers */
define( function(){
	var UserListCtrl = ['$scope', '$http','dataTable', function($scope, $http, dataTable){
		
		$scope.users = {};
		var option = {
			"columnDefs":[{"orderable":false, "targets":[0,4,5,7]}],
			"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
			"columns":[
			      {"data":"id"},
			      {"data":"user_name"},
			      {"data":"gender"},
			      {"data":"phone"},
			      {"data":"qq"},
			      {"data":"birthday"},
			      {"title":"操作", 
			       "className":"center",
			       "data" : function(row, type, val, meta){
			    	   return '<a class="btn btn-mini btn-warning" href="/admin#/user_info?user_id='+row.id+'" >'+
                       '<i class="icon-edit icon-white"></i> 编辑</a>&nbsp;&nbsp;&nbsp;'
			    	   +'<a class="btn btn-mini btn-warning" href="/admin#/user_grant?user_id='+row.id+'" >'+
                       '<i class="icon-edit icon-white"></i> 授权</a>';
			       }
			      }
			]
		};
		
		var params = {"check_box":true, "id":"user_list", url:"user/list"};
		var table = dataTable.init(params,option);
		
		$scope.del = function(){
			var values = dataTable.getCheckedValues($("#user_list"),"user_id");
			if(values == ""){
				alert("请选择要删除的记录");return;
			}
			
			$http({method:'post',
				  url:"user/delById",
				  params:{ids:values}
			}).success(function(result){
				if(result.status == 1){
					table.ajax.reload();
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
	}];
	
	var UserInfoCtrl = ['$scope', '$http','$routeParams','$location', function($scope, $http, $routeParams, $location){
		$scope.user = {};
		
		$http({method:'post',
			  url:"user/findById",
			  params:{user_id:$routeParams.user_id}
		}).success(function(result){
			$scope.user = result.data;
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.addOrUpdate = function(){
			$http({
				  method:'post',
				  url:"user/addOrUpdate",
				  params:$scope.user
			}).success(function(result){
				if(result.status == 1){
					$location.path("/user_list");
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
	}];
	
	var UserGrantCtrl = ['$scope', '$http','$routeParams','$location', function($scope, $http, $routeParams, $location){
		$scope.user = {};
		
		var zTreeObj ;
		var setting = {
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
		var zNodes = null;
		
		$http({method:'post',
			  url:"user/findGrantsByUserId",
			  params:{user_id:$routeParams.user_id}
		}).success(function(result){
			zNodes = result.data;
			//:初始化菜单信息
			zTreeObj = $.fn.zTree.init($("#grandAuthority"), setting, zNodes);
			setCheck();
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.executeGrant = function(){
			if(zTreeObj == null || typeof zTreeObj == "undefined"){
				alert("请重新授权");
				return;
			}
			var nodes = zTreeObj.getCheckedNodes();
			if(nodes == null || nodes.leng < 1) {
				alert("请选择权限信息");
			}
			var desc = "";
			for(var i=0,len=nodes.length; i<nodes.length; i++){
				if(i == len - 1){
					desc += nodes[i]["id"];
				} else{
					desc += nodes[i]["id"]+",";
				}
			}
			$http({
				  method:'post',
				  url:"user/saveGrant",
				  params:{user_id:$routeParams.user_id,desc:desc}
			}).success(function(result){
				if(result.status == 1){
					$location.path("/user_list");
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
	}];
	return {list : UserListCtrl, info : UserInfoCtrl, grant : UserGrantCtrl};
});