'use strict';
/* Controllers */
define( function(){
	var MenuListCtrl = ['$scope', '$http','dataTable', function($scope, $http, dataTable){
		
		$scope.menus = {};
		var option = {
			"columnDefs":[{"orderable":false, "targets":[0,4,5,7]}],
			"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
			"columns":[
			      {"data":"id"},
			      {"data":"parent_id"},
			      {"data":"name"},
			      {"data":"title"},
			      {"data":"url"},
			      {"data":"icon"},
			      {"data":"sort"},
			      {"data":"level"},
			      {"data":"remark"},
			      {"data":"showflag","render":function(data, type, full, meta){
			    	  if(data === 1) {
							return "是";
						} else if(data === 2){
							return "否";
						} else {
							return data;
						}
			      }},
			      {"data":"istreemenu","render":function(data, type, full, meta){
			    	  if(data === 1) {
							return "是";
						} else if(data === 2){
							return "否";
						} else {
							return data;
						}
			      }},
			      {"title":"操作", 
			       "className":"center",
			       "data" : function(row, type, val, meta){
			    	   return '<a class="btn btn-mini btn-warning" href="/admin#/menu_info?menu_id='+row.id+'" >'+
                       '<i class="icon-edit icon-white"></i> 编辑</a>';
			       }
			      }
			]
		};
		
		
		
		var params = {"check_box":true, "id":"menu_list", url:"menu/list"};
		var table = dataTable.init(params,option);
		
		$scope.del = function(){
			var values = dataTable.getCheckedValues($("#menu_list"),"menu_id");
			if(values == ""){
				alert("请选择要删除的记录");return;
			}
			
			$http({method:'post',
				  url:"menu/delById",
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
	
	var MenuInfoCtrl = ['$scope', '$http','$routeParams','$location', function($scope, $http, $routeParams, $location){
		$scope.menu = {};
		
		$http({method:'post',
			  url:"menu/findById",
			  params:{menu_id:$routeParams.menu_id}
		}).success(function(result){
			$scope.menu = result.data;
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.addOrUpdate = function(){
			$http({
				  method:'post',
				  url:"menu/addOrUpdate",
				  params:$scope.menu
			}).success(function(result){
				if(result.status == 1){
					$location.path("/menu_list");
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
	}];
	return {list : MenuListCtrl, info : MenuInfoCtrl};
});