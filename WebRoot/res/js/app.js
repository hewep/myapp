'use strict';
// Declare app level module which depends on filters, and services
define(['angular','controllers/user'],function(angular, user){
	var app =  angular.module('registerApp', ['ngRoute']);
		
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/user/list', {templateUrl: 'core/user/list.html', controller: user.list})
			  				.when('/user/info/:userId', {templateUrl: 'core/user/info.html', controller: user.info})
			  				.otherwise({redirectTo: '/user/list'});
			  				
			  	// configure html5 to get links working on jsfiddle			
			  	$locationProvider.html5Mode(true);
			}]);
	return app;
});