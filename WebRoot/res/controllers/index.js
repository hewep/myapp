'use strict';

/* Controllers */
var indexCtrls = angular.module('IndexCtrls',[]);
	indexCtrls.controller('QuestionCtrl',['$scope', '$http' ,'$location',function($scope, $http,$location){
			
	}]);
	
	
	indexCtrls.directive('initKindEditor', ['$http',function($http){
		return function($scope, $element, $attr, $ctrl){
			require([
			    'kindEditor'
			],function(){
				KindEditor.create('textarea[name='+$element.attr('name')+']', {
	                /*autoHeightMode : true,
	                afterCreate : function() {
	                    this.loadPlugin('autoheight');
	                },*/
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
			});
		};
	}]);