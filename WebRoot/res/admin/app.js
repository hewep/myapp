'use strict';
// Declare app level module which depends on filters, and services
define([
        'angular',
        'controllers/category',
        'controllers/user',
        'common/directives'
],function(angular, category, user){
	var app =  angular.module('adminApp', ['ngRoute','CommDirectives']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/admin/topic/category_list.html', controller: category.list})
			  				.when('/user_list', {templateUrl:'core/admin/user_list.html', controller: user.list})
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});