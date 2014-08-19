'use strict';

/* Directives */
define(['angular'], function(){
	var comm = angular.module('CommServices',[]);
	
	/**datatables 相关操作**/
	comm.factory('dataTable', function(){
		var dataTable =  {
			init : function(params, option){	   // 初始化
				
				var obj = $("#"+params.id);  
				var opt  = {
						"ajax" : {
							"url":params.url,
							"data":function(d){
								return {"draw":d.draw, "pageNumber":d.start/d.length+1,"pageSize":d.length};
							}
						},
						"order":[],
						"pagingType":"full_numbers",
						"lengthMenu":[1,5,10,15,20,50],
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
						},
						"createdRow" : function(row, data, dataIndex){
						    $(row).attr(params.id, data.id);
						}

				};
				$.extend(opt, option);
				
				if(params.check_box){  // 如果有复选框则添加复选框列
					opt.columns.unshift({"title":"<input type=checkbox>", "defaultContent":"<input type=checkbox>", "className":"check_column"});
				}
				var table = obj.on('init.dt',function(){
								
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
								
							}).DataTable(opt);
				
				return table;
				
			},
			getSelected : function(obj){
				return obj.find(".select_row");
			},
			getChecked : function(obj){
				return obj.find("tr td.check_column :checked").closest("tr");
			},
			getCheckedValues : function(obj, name){
				var rows = dataTable.getChecked(obj);
				var values = "";
				rows.each(function(){
					var value = $(this).attr(name);
					values += (values == "" ? value:","+value);
				});
				return values;
			}
		};
		
		return dataTable;
	});
	
		
});