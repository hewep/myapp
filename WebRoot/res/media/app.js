'use strict';
// Declare app level module which depends on filters, and services
define(['angular',
        'media/controllers/index',
        'media/controllers/topic',
        'media/controllers/user',
        'media/directives/common'
],function(angular,index, topic){
	var app =  angular.module('myApp', ['ngRoute','UserCtrls','CommDirectives']);
	
		app.config(['$routeProvider','$locationProvider',  function($routeProvider, $locationProvider) {
			 $routeProvider.when('/', {templateUrl: 'core/media/category.html', controller: index.categorys})
			  				.when('/topic', {templateUrl:'core/media/topic.html', controller: topic.topic})
			  				.when('/topic_list/:category_id',{templateUrl:'core/media/topic_list.html', controller:topic.topicList})
			  				.when('/topic_info/:topic_id',{templateUrl:'core/media/topic_info.html', controller:topic.topicInfo})
			  				.when('/register', {templateUrl: 'core/media/register.html', controller: 'RegisterCtrl' })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			}]);
	return app;
});