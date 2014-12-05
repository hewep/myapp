'use strict';
/* Controllers */
define( function(){
	var TemplateListCtrl = ['$scope', '$http','dataTable','$location', function($scope, $http, dataTable, $location){
		
		$scope.templates = {};
		var option = {
			"columnDefs":[{"orderable":false, "targets":[0]}],
			"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
			"columns":[
			      {"data":"id"},
			      {"data":"name"},
			      {"data":"type"},
			      {"data":"created_time"},
			      {"title":"操作", 
			       "className":"center",
			       "data" : function(row, type, val, meta){
			    	   return '<a class="btn btn-mini btn-warning" href="/admin#/template_info?template_id='+row.id+'" >'+
                       '<i class="icon-edit icon-white"></i> 编辑</a>&nbsp;&nbsp;&nbsp;';
			       }
			      }
			]
		};
		
		var params = {"check_box":true, "id":"template_list", url:"template/list"};
		var table = dataTable.init(params,option);
		
		$scope.del = function(){
			var values = dataTable.getCheckedValues($("#template_list"),"template_id");
			if(values == ""){
				alert("请选择要删除的记录");return;
			}
			
			$http({method:'post',
				  url:"template/delById",
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
		
		$scope.param = {};
		$scope.batchGenerate = function(){
			var values = dataTable.getCheckedValues($("#template_list"),"template_id");
			window.location.href = "template/batchGenerate?ids="+values+"&"+$.param($scope.param);
		};
	}];
	
	var TemplateInfoCtrl = ['$scope', '$http','$routeParams','$location', function($scope, $http, $routeParams, $location){
		$scope.template = {};
		var editor;
		$http({method:'post',
			  url:"template/findById",
			  params:{template_id:$routeParams.template_id}
		}).success(function(result){
			$scope.template = result.data;
			$("#editor").text(result.data.content);
			var suffix = result.data.type;
			require(['ace'],function(){
				editor = ace.edit("editor");
			    editor.setTheme("ace/theme/dawn");
			    editor.getSession().setMode("ace/mode/"+suffix);
			});
		}).error(function(){
			alert("网络连接失败");
		});
		
		$scope.addOrUpdate = function(){
			$scope.template.content = editor.getValue();
			
			$http({
				  method:'post',
				  url:"template/addOrUpdate",
				  params:$scope.template
			}).success(function(result){
				if(result.status == 1){
					$location.path("/template_list");
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
	}];
	
	return {list : TemplateListCtrl, info : TemplateInfoCtrl};
});