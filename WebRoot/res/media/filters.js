'use strict';

define(['angular'], function(){
	var filters = angular.module('CommFilters',[]);
		filters.filter('toHtml', ['$sce', function($sce){
			return function(text){
				return $sce.trustAsHtml(text);
			};
		}]);
		
});