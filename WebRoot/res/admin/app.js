'use strict';
// Declare app level module which depends on filters, and services
define([
        'angular',
        'controllers/topic/category',
        'controllers/sys/user',
        'controllers/sys/role',
        'controllers/sys/menu',
        'controllers/topic/topic',
        'common/directives',
        'common/services'
],function(angular, category, user, role, menu, topic){
	var app =  angular.module('adminApp', ['ngRoute','CommDirectives','CommServices']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/admin/topic/category_list.html', controller: category.list})
			  				.when('/component_icons', {templateUrl:'core/icons.html', controller: null})
			  				.when('/component_font_icons', {templateUrl:'core/font_icons.html', controller: null})
			  				
			  				.when('/user_list', {templateUrl:'core/admin/sys/user_list.html', controller: user.list})
			  				.when('/user_info', {templateUrl:'core/admin/sys/user_info.html', controller: user.info})
			  				.when('/user_grant', {templateUrl:'core/admin/sys/user_grant.html', controller: user.grant})
			  				.when('/role_list', {templateUrl:'core/admin/sys/role_list.html', controller: role.list})
			  				
			  				.when('/menu_list', {templateUrl:'core/admin/sys/menu_list.html', controller: menu.list})
			  				.when('/menu_info', {templateUrl:'core/admin/sys/menu_info.html', controller: menu.info})
			  				
			  				.when('/category_list', {templateUrl: 'core/admin/topic/category_list.html', controller: category.list})
			  				.when('/topic_list', {templateUrl:'core/admin/topic/topic_list.html', controller: topic.list})
			  				
			  				
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});