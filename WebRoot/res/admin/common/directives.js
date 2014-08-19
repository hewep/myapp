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
	
});