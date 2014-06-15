'use strict';
// Declare app level module which depends on filters, and services
define([
        'angular'
],function(angular){
	var app =  angular.module('adminApp', ['ngRoute']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/category.html', controller: null})
			  				.when('/topic', {templateUrl:'core/topic.html', controller: null})
			  				.when('/topic_list',{templateUrl:'core/topic_list.html', controller:null})
			  				.when('/register', {templateUrl: 'core/register.html', controller: null })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});