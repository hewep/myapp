'use strict';
// Declare app level module which depends on filters, and services
define([
        'route-config',
        'common/directives',
        'common/filters'
],function(routeConfig){
	var app =  angular.module('myApp', ['ngRoute','ui.router','ngCookies','CommDirectives', 'CommFilters']);
		
		app.config(['$stateProvider', '$urlRouterProvider','$compileProvider', '$controllerProvider', function($stateProvider, $urlRouterProvider,$compileProvider, $controllerProvider){
			/** 初始化routeConfig **/
			routeConfig.setCompileProvider($compileProvider);
	        routeConfig.setControllerProvider($controllerProvider);
	        
			$urlRouterProvider.otherwise('/home');
			$stateProvider.state('home',routeConfig.config({ url:'/home', templateUrl:'core/media/home.html',controllerName: "controllers/index", method:"categorys"}))
						  .state('home.register',{url:'/register', templateUrl: 'core/media/register.html', controllerName: "controllers/index",method:"register"})
						  .state('home.topic',routeConfig.config({url:'/topic',templateUrl:'core/media/topic/topic.html',controllerName: "controllers/topic",method:"topic"}))
						  .state('home.topic_list',
								  routeConfig.config({url:'/topic_list?category_id&category_name',templateUrl:'core/media/topic/topic_list.html', controllerName:"controllers/topic",method:"topicList"}))
						  .state('home.topic_info',routeConfig.config({url:'/topic_info/:topic_id', templateUrl:'core/media/topic/topic_info.html', controllerName:"controllers/topic",method:"topicInfo"}))
						  
						  .state('space',routeConfig.config({url:'/space',templateUrl: 'core/media/space.html', controllerName:"controllers/space", method:"index"}))
						  .state('space.profile',routeConfig.config({url:'/profile', templateUrl:'core/media/space/profile.html', controllerName: "controllers/space/profile",method:"userInfo"}))
						  .state('space.message_list',routeConfig.config({url:'/message_list',templateUrl:'core/media/space/message_list.html', controllerName:"controllers/space/message",method:"messageList"}));;
						  
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

