'use strict';
// Declare app level module which depends on filters, and services
define([
        'angular',
        'category'
],function(angular, category){
	var app =  angular.module('adminApp', ['ngRoute']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/admin/topic/category_list.html', controller: category.list})
			  				.when('/topic', {templateUrl:'core/admin/topic.html', controller: null})
			  				.when('/topic_list',{templateUrl:'core/admin/topic_list.html', controller:null})
			  				.when('/register', {templateUrl: 'core/admin/register.html', controller: null })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});