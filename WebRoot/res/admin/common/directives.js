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
	// icon
	comm.directive('icons', function(){
		return function(scope, element, attr, ctrl){
			$(element).find('li').css('cursor','pointer').on('click', function(){
	            var ico_title = $(this).attr('title');
	            var ico_class = $(this).find('i').attr('class');
	            
	            var type = attr.icons;
	            if(type == 'class'){
	            	ico_title = ico_class;
	            }
	            $(element).prev().find('span').html('[ <i class="'+ico_class+'"></i> <code>&lt;i class="'+ico_title+'"&gt;&lt;/i&gt;</code> ]');
	        });
		};
	});
	
	comm.directive('prettify', function(){
		return {
			require : '?^ngModel',
			link : function(scope, element, attrs, ngModel){
				if(!ngModel){
					return;
				}
				
				ngModel.$render = function() {
				    //element.text(ngModel.$viewValue || '');
					$("#test").val(ngModel.$viewValue || '');
				};
				
				 // 监听change事件来开启绑定
			    $(element).on('blur keyup change', function(e) {
			    	scope.$apply(read);
			    });
			    read(); // 初始化
			    
			    // 将数据写入model
			    function read() {
			    	var text = $("#test").val();
			        ngModel.$setViewValue(text);
			    }
			}
		}
		
	});
});