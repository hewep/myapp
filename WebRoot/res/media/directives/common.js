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
		                        'source', '|', 'undo', 'redo', '|', 'preview', 'code', 'cut', 
		                        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright','justifyfull', '|',
		                        'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent','|', 'subscript',
		                        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		                        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		                        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		                        'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
		                        'link', 'unlink', '|', 'about'
		                ]
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
//		directives.filter('htmlFilter', ['$http',]);
});