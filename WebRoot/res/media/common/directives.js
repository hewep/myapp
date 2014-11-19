'use strict';

/* Directives */
define(['angular'], function(){
	var directives = angular.module('CommDirectives',[]);
		/**唯一验证*/
		directives.directive('ensureUnique', ['$http',function($http){
			return function($scope, $element, $attr, $ctrl){
				$element.on('focusout', function(){
					var item = $attr.ensureUnique;
					var key = item.split(':')[0];
					var url = item.split(':')[1];
					var params = {};
					var value = $("input[name="+key+"]").val();
					if(!value){return;}
					params[key] = value;
					$http({
						method : 'POST',
						url : url,
						params : params
					}).success(function(result, status, headers, cfg) {
						if(result.exist){
							$scope[key+"_unique"] = true;
						}else{
							$scope[key+"_unique"] = false;
						}
					});
					
				});
			};
		}]);
		
		/**初始化 编辑器**/
		directives.directive('initKindEditor', ['$http',function($http){
			return function($scope, $element, $attr, $ctrl){
				require([
				    'kindEditor'
				],function(){
					var editor = KindEditor.create('textarea[name='+$element.attr('name')+']', {
		                cssPath : ['/res/plugins/kindeditor/prettify.css'],
		                items : [
		                        'source', 'code', 'cut', '|',
		                        'justifyleft', 'justifycenter', 'justifyright', '|',
		                        'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent','|', 
		                         'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		                        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'bold',
		                        'italic', 'underline', '|', 'image', 'multiimage',
		                        'insertfile',  'hr', 'emoticons', 'link' 
		                ],
		                minWidth:400
		            });
					$scope.editor = editor;
				});
			};
		}]);
		
		directives.directive('prettify', function(){
			return function($scope, $element, $attr, $ctrl){
				require([
				     'prettify'    
				],function(){
					prettyPrint();
				});
			};
		});
		
		directives.directive('topicType', function(){
			return {
		        restrict: "A",
		            link: function( scope, element, attrs ) {
		            	if(scope.$first){
		            		element.find("a").addClass("selected");
		            	}
		            	
		            	$(element).delegate("a","click",function(){
		    				$(this).addClass("selected");
		    				$(this).closest("li").siblings().each(function(){
		    					$(this).find("a").removeClass("selected");
		    				});
		    			});
		        }
		    };
		});
		
		/** 前端页面的分页
		 *  返回数据格式: {datas:{}}
		 *  请求路径: url/pageNumber
		 **/
		directives.directive('mediaPagination', ['$http',function($http){
			return {
				restrict: "AE",
				scope : {page : '=page'},
				templateUrl : "core/media/_pagination.html",
				link : function(scope, element, attrs){
					$(element).delegate("li", "click", function(){
						if($(this).hasClass("disabled") || $(this).hasClass(".active")){
							return;
						}
						var url = $(this).find("a").attr("url");
						var param = $(this).find("a").attr("param");
						$http({	method:'post',
							url:url+"/"+param,
							params:scope.page.params
						}).success(function(result){
							var option = {page : result.datas, url:url, params:scope.page.params};
							scope.page = $.handlePage(option);
						}).error(function(){
							alert("网络连接失败");
						});
					});
				}
			};
		}]);
		/** 日历 **/
		directives.directive('datePicker', function(){
			return {
		        restrict: "A",
		            link: function( scope, element, attrs ) {
		            	require([
	    				     'datepicker'    
	    				],function(){
	    					$(element).datepicker();
	    				});
		        }
		    };
		});
		/**区域联动 (select 顺序固定)**/
		directives.directive('region', function(){
			return {
		        restrict: "A",
		            link: function( scope, element, attrs ) {
		            	require([
	    				     'region'    
	    				],function(){
		            		var selects = $(element).find("select");
		            		var province = $(selects[0]).attr("name"),
		            			city = $(selects[1]).attr("name"),
		            			area = $(selects[2]).attr("name");
		            		new PCAS(province,city,area, scope.user.province, scope.user.city, scope.user.district);
	    				});
		        }
		    };
		});
		
		
		directives.directive('contenteditable', function(){
			return {
		        restrict: "A",
		        require: '?ngModel',
		            link: function( scope, element, attrs, ngModel) {
		            	if(!ngModel){
							return;
						}
		            	ngModel.$render = function() {
	            	      element.html(ngModel.$viewValue || '');
	            	    };

	            	    // 监听change事件来开启绑定
	            	    element.on('blur keyup change', function() {
	            	      scope.$apply(read);
	            	    });
	            	    read(); // 初始化

	            	    // 将数据写入model
	            	    function read() {
	            	      var html = element.html();
	            	      // 当我们清空div时浏览器会留下一个<br>标签
	            	      // 如果制定了strip-br属性，那么<br>标签会被清空
	            	      if( attrs.stripBr && html == '<br>' ) {
	            	        html = '';
	            	      }
	            	      ngModel.$setViewValue(html);
	            	    }
		        }
		    };
		});
		
});