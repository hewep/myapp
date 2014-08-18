'use strict';

/* Directives */
define(['angular'], function(){
	var comm = angular.module('CommDirectives',[]);
	
	//面包屑 
	comm.directive('breadCrumbs', function(){
		return function(scope, element, attr, ctrl){
			$(element).jBreadCrumb({
	            endElementsToLeaveOpen: 0,
	            beginingElementsToLeaveOpen: 0,
	            timeExpansionAnimation: 500,
	            timeCompressionAnimation: 500,
	            timeInitialCollapse: 500,
	            previewWidth: 30
	        });
		};
	});
	
	comm.directive('datatable', function($compile){
		return {
			restrict: 'A',
			link: function(scope, element, attrs){
				var options = {
						"pagingType":"full_numbers",
						"lengthMenu":[5,10,15,20,50],
						"processing": true,
						"serverSide": true,
						"dom" : "t<'row'<'span6'l i><'span6'p>>",
						"language":{
							"emptyTable":     "没有数据",
						    "info":           " 从 _START_ 到 _END_  共:_TOTAL_条记录",
						    "infoEmpty":      "",
						    "infoFiltered":   "(filtered from _MAX_ total entries)",
						    "infoPostFix":    "",
						    "thousands":      ",",
						    "lengthMenu":     "每页 _MENU_ 条 ",
						    "loadingRecords": "加载中...",
						    "processing":     "处理中...",
						    "search":         "查询 : ",
						    "zeroRecords":    "没有匹配的记录",
						    "paginate": {
						        "first":      "首页",
						        "last":       "尾页",
						        "next":       "下一页",
						        "previous":   "上一页"
						    },
						    "aria": {
						        "sortAscending":  ": activate to sort column ascending",
						        "sortDescending": ": activate to sort column descending"
						    }
						}
				};
				
				angular.extend(options, scope.dtOptions);
				
				element.on('init.dt',function(){
					/**checkbox 事件**/
					$(this).find("tr th.check_column :checkbox").on("click",function(){
						obj.find("tr td.check_column :checkbox").attr("checked", this.checked);
					});
					/**click row 事件**/
					$(this).find("tbody tr").on("click",function(){
						//var checkbox = $(this).find("td.check_column :checkbox");
						//checkbox.attr("checked", !checkbox.attr("checked"));
						
						$(this).addClass("select_row");
						$(this).siblings().removeClass("select_row");
					});
					
				}).DataTable(options);
				
			}
		};
	});	
});