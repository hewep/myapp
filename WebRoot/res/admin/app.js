'use strict';
// Declare app level module which depends on filters, and services
define([
        'angular',
        'controllers/category',
        'controllers/user',
        'controllers/role',
        'common/directives',
        'common/services'
],function(angular, category, user, role){
	var app =  angular.module('adminApp', ['ngRoute','CommDirectives','CommServices']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/admin/topic/category_list.html', controller: category.list})
			  				.when('/component_icons', {templateUrl:'core/icons.html', controller: null})
			  				.when('/user_list', {templateUrl:'core/admin/sys/user_list.html', controller: user.list})
			  				.when('/user_info', {templateUrl:'core/admin/sys/user_info.html', controller: user.info})
			  				.when('/role_list', {templateUrl:'core/admin/sys/role_list.html', controller: role.list})
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});