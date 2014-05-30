'use strict';
// Declare app level module which depends on filters, and services
define(['angular','controllers/index','controllers/user'],function(angular, index, user){
	var app =  angular.module('myApp', ['ngRoute']);
		
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/category.html', controller: index.categoryList})
			  				.when('/register', {templateUrl: 'core/register.html', controller: index.register })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});