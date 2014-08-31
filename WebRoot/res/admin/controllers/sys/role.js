'use strict';
/* Controllers */
define( function(){
	var RoleListCtrl = ['$compile','$scope', '$http','dataTable', function($compile,$scope, $http,dataTable){
		
		$scope.roles = {};
		$scope.role = {};
		
		var dtOptions = {
			"columnDefs":[{"orderable":false, "targets":[0]}],
			"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
			"columns":[
			      {"data":"id"},
			      {"data":"name"},
			      {"data":"code"},
			      {"data":"sort"},
			      {"data":"remark"},
			      {"title":"操作", 
			       "className":"center",
			       "data" : function(row, type, val, meta){
			    	   return '<a class="btn btn-mini btn-warning" href="#info_modal" data-toggle="modal" ng-click="edit('+row.id+');">'+
                       '<i class="icon-edit icon-white"></i> 编辑</a>&nbsp;&nbsp;'
			    	   	+'<a class="btn btn-mini btn-warning" href="#grant_modal" data-toggle="modal" ng-click="grant('+row.id+');">'+
                       '<i class="icon-edit icon-white"></i> 授权</a>';
			       }
			      }
			],
			"createdRow" : function(row, data, dataIndex){
			    $(row).attr("role_id", data.id);
			    // 重新编译 
			    $compile(row)($scope);
			}
			
		};
		
		var params = {"check_box":true,"id":"role_list", url:"role/list"};
		var table = dataTable.init(params, dtOptions);
		
		$scope.del = function(){
			var values = dataTable.getCheckedValues($("#role_list"),"role_id");
			if(values == ""){
				alert("请选择要删除的记录");return;
			}
			
			$http({method:'post',
				  url:"role/delById",
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
		
		
		$scope.add = function(){	// 添加
			$scope.role = {};
		};
		$scope.edit = function(roleId){
			$http({ method:'post',
				url:"role/findById",
				params:{"role_id":roleId}
			}).success(function(result){
				$scope.role = result.data;
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		$scope.save = function(){
			
			$http({ method:'post',
					url:"role/addOrUpdate",
					params:$scope.role
			}).success(function(result){
				alert(result.msg);
				if(result.status == 1){
					$("#info_modal").modal('hide');
					table.ajax.reload();
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};

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
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("grandAuthority"),
			type = { "Y":"ps", "N":"ps"};
			zTree.setting.check.chkboxType = type;
		}
		
		$scope.grant = function(roleId){
			$scope.role = {"roleId":roleId};
			$http({method:'post',
				  url:"role/findGrantsByRoleId",
				  params:{role_id:roleId}
			}).success(function(result){
				zNodes = result.data;
				//:初始化菜单信息
				zTreeObj = $.fn.zTree.init($("#grandAuthority"), setting, zNodes);
				setCheck();
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
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
			
			$http({method:'post',
				  url:"role/saveGrant",
				  params:{role_id:$scope.role.roleId,desc:desc}
			}).success(function(result){
				if(result.status ==1){
					alert("授权成功");
				} else {
					alert("授权失败");
				}
			}).error(function(){
				alert("授权失败");
			});
			
		};
	}];
	
//	var RoleGrantCtrl = ['$scope', '$http','$routeParams','$location', function($scope, $http, $routeParams, $location){
//		$scope.role = {};
//		var zTreeObj ;
//		var setting = {
//				check: {
//					enable: true
//				},
//				data: {
//					simpleData: {
//						enable: true
//					}
//				}
//			};
//		var zNodes;
//		function setCheck() {
//			var zTree = $.fn.zTree.getZTreeObj("grandAuthority"),
//			type = { "Y":"ps", "N":"ps"};
//			zTree.setting.check.chkboxType = type;
//		}
//			
//		
//		$http({method:'post',
//			  url:"role/findGrantsByRoleId",
//			  params:{role_id:$routeParams.role_id}
//		}).success(function(result){
//			zNodes = result.data;
//			//:初始化菜单信息
//			zTreeObj = $.fn.zTree.init($("#grandAuthority"), setting, zNodes);
//			setCheck();
//		}).error(function(){
//			alert("网络连接失败");
//		});
//		
//		$scope.addOrUpdate = function(){
//			$http({
//				  method:'post',
//				  url:"role/addOrUpdate",
//				  params:$scope.role
//			}).success(function(result){
//				if(result.status == 1){
//					$location.path("/role_list");
//				}else{
//					alert(result.msg);
//				}
//			}).error(function(){
//				alert("网络连接失败");
//			});
//		};
//		
//	}];
	
	return {list : RoleListCtrl};
});