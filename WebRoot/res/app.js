'use strict';
// Declare app level module which depends on filters, and services
define(['angular',
        'index',
        'user'
],function(angular){
	var app =  angular.module('myApp', ['ngRoute','IndexCtrls','UserCtrls']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/category.html', controller: null})
			  				.when('/question', {templateUrl:'core/question.html', controller: null})
			  				.when('/register', {templateUrl: 'core/register.html', controller: 'RegisterCtrl' })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});