'use strict';
// Declare app level module which depends on filters, and services
define(['controllers/index',
        'controllers/topic',
        'common/directives',
        'common/filters'
],function(index, topic){
	var app =  angular.module('myApp', ['ngRoute','ui.router','ngCookies','CommDirectives', 'CommFilters']);
		/*
		app.config(['$routeProvider','$locationProvider', '$httpProvider', function($routeProvider, $locationProvider,$httpProvider) {
			 $routeProvider.when('/', {templateUrl: 'core/media/category.html', controller: index.categorys})
							.when('/register', {templateUrl: 'core/media/register.html', controller: index.register })
							.when('/space', {templateUrl: 'core/media/admin/space.html', controller: index.space })
							
			  				.when('/topic', {templateUrl:'core/media/topic/topic.html', controller: topic.topic})
			  				.when('/topic_list/:category_id',{templateUrl:'core/media/topic/topic_list.html', controller:topic.topicList})
			  				.when('/topic_info/:topic_id',{templateUrl:'core/media/topic/topic_info.html', controller:topic.topicInfo})
			  				
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			 	
			 	// 配置拦截器
			 	//$httpProvider.interceptors.push('anthInterceptor');
			}]);*/
		
		app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
			$urlRouterProvider.otherwise('/home');
			$stateProvider.state('home',{ url:'/home', templateUrl:'core/media/home.html',controller: index.categorys})
						  .state('home.register',{url:'/register', templateUrl: 'core/media/register.html', controller: index.register})
						  .state('home.topic',{url:'/topic',templateUrl:'core/media/topic/topic.html',controller: topic.topic})
						  .state('home.topic_list',{url:'/topic_list?category_id&category_name', templateUrl:'core/media/topic/topic_list.html', controller:topic.topicList})
						  .state('home.topic_info',{url:'/topic_info/:topic_id', templateUrl:'core/media/topic/topic_info.html', controller:topic.topicInfo})
						  
						  .state('space',{url:'/space',templateUrl: 'core/media/space.html'})
						  .state('space.message_list',{url:'/message_list',templateUrl:'core/media/admin/message_list.html'});
						  
						  
		}]);
		
		// 处理返回后的数据 , 如果未登录 再次统一处理
		app.factory('anthInterceptor', function(checkUser){		
			return {
			      'response': function(response) {
			    	  var url = response.config.url;
			    	  var method = url.substring(url.lastIndexOf("/")+1);
			    	  var reg = /.*(add|del|edit|comment).*/;
			    	  if(reg.test(method) && response.data.type == "not_login"){
			    		  checkUser.logout();
			    	  }
			    	  return response;
			      }
			};
		});
		
	return app;
});