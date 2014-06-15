'use strict';
// Declare app level module which depends on filters, and services
define(['angular',
        'index',
        'user',
        'directives'
],function(angular,index){
	var app =  angular.module('myApp', ['ngRoute','UserCtrls','CommDirectives']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			  $routeProvider.when('/', {templateUrl: 'core/media/category.html', controller: null})
			  				.when('/topic', {templateUrl:'core/media/topic.html', controller: index.topic})
			  				.when('/topic_list',{templateUrl:'core/media/topic_list.html', controller:null})
			  				.when('/register', {templateUrl: 'core/media/register.html', controller: 'RegisterCtrl' })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});