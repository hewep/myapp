'use strict';
// Declare app level module which depends on filters, and services
define(['angular','controllers/index','controllers/user'],function(angular, index, user){
	var app =  angular.module('myApp', ['ngRoute']);
		
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/category.html', controller: index})
			  				.when('/user/info/:userId', {templateUrl: 'core/user/info.html', controller: user.info})
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5 to get links working on jsfiddle			
			  	$locationProvider.html5Mode(true);
			}]);
	return app;
});