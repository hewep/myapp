'use strict';
/* Controllers */
define( function(){
	var RoleListCtrl = ['$compile','$scope', '$http','dataTable', function($compile,$scope, $http,dataTable){
		
		$scope.roles = {};
		$scope.role = {};
		
		$scope.dtOptions = {
			"ajax" : {
				"url":"role/list",
				"data":function(d){
					return {"pageNumber":d.start/d.length+1,"pageSize":d.length};
				}
			},
			"columnDefs":[{"orderable":false, "targets":[0]}],
			"order":[],
			"columns":[
			      {"title":"<input type=checkbox>", "defaultContent":"<input type=checkbox>", "className":"check_column"},
			      {"data":"id"},
			      {"data":"name"},
			      {"data":"code"},
			      {"data":"sort"},
			      {"data":"remark"},
			      {"title":"操作", 
			       "className":"center",
			       "data" : function(row, type, val, meta){
			    	   return '<a class="btn btn-mini btn-warning" href="#info_modal" data-toggle="modal" ng-click="edit('+row.id+');">'+
                       '<i class="icon-edit icon-white"></i> 编辑</a>';
			       }
			      }
			],
			"createdRow" : function(row, data, dataIndex){
			    $(row).attr("role_id", data.id);
			    
			    // 重新编译 
			    $compile(row)($scope);
			}
			
		};
		
		var table = dataTable.init($("#role_list"), $scope.dtOptions);
		
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
			alert(roleId);
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
	}];
	
	return {list : RoleListCtrl};
});