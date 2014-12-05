'use strict';
// Declare app level module which depends on filters, and services
define([
        'route-config',
        'common/directives',
        'common/filters'
],function(routeConfig){
	var app =  angular.module('myApp', ['ui.router','ngCookies','CommDirectives', 'CommFilters']);
		
		app.config(['$stateProvider', '$urlRouterProvider','$compileProvider', '$controllerProvider', 
		            function($stateProvider, $urlRouterProvider,$compileProvider, $controllerProvider){
			/** 初始化routeConfig **/
			routeConfig.setCompileProvider($compileProvider);
	        routeConfig.setControllerProvider($controllerProvider);
	        
			$urlRouterProvider.otherwise('/index');
			$stateProvider.state('index',routeConfig.config({ url:'/index', templateUrl:'core/media/index.html',controllerName: "controllers/index", method:"index"}))
						  .state('index.register',routeConfig.config({url:'/register', templateUrl: 'core/media/register.html', controllerName: "controllers/column",method:"register"}))
						  .state('index.categorys',routeConfig.config({url:'/categorys', templateUrl: 'core/media/topic/category_list.html', controllerName: "controllers/column",method:"categorys"}))
						  .state('index.search',routeConfig.config({url:'/search', templateUrl: 'core/media/topic/topic_list.html'}))
						  
						  .state('index.topic',routeConfig.config({url:'/topic',templateUrl:'core/media/topic/topic.html',controllerName: "controllers/topic",method:"addTopic"}))
						  .state('index.topic_list',
								  routeConfig.config({url:'/topic_list?category_id',templateUrl:'core/media/topic/topic_list.html', controllerName:"controllers/topic",method:"topicList"}))
						  .state('index.topic_info',routeConfig.config({url:'/topic_info/:topic_id', templateUrl:'core/media/topic/topic_info.html', controllerName:"controllers/topic",method:"topicInfo"}))
						  /**在线工具*/
						  .state('tools',routeConfig.config({url:'/tools', templateUrl:'core/media/tools/tools.html'}))
						  .state('tools.code_runner',routeConfig.config({url:'/code_runner', templateUrl:'core/media/tools/code_runner.html',controllerName:"controllers/tools/code_runner", method:"codeRuuner"}))
						  /**我的空间*/
						  .state('space',routeConfig.config({url:'/space/:user_id',templateUrl: 'core/media/space/space.html', controllerName:"controllers/space/space", method:"index"}))
						  .state('space.info_center',routeConfig.config({url:'/info_center/:self',
							  									 templateUrl: function(stateParams){
							  										 
							  										 if(stateParams.self == 'true'){
							  											 return "core/media/space/info_center.html";
							  										 }else{
							  											 return "core/media/space/info_center1.html";
							  										 }
							  									 }, 
							  									 controllerName:"controllers/space/message", 
							  									 method:"messageList"}))
						  
						  .state('space.profile',routeConfig.config({url:'/profile', templateUrl:'core/media/space/profile.html', controllerName: "controllers/space/profile",method:"userInfo"}))
						  .state('space.message_list',routeConfig.config({url:'/message_list',templateUrl:'core/media/space/message_list.html', controllerName:"controllers/space/message",method:"messageList"}))
						  .state('space.blog',routeConfig.config({url:'/blog',templateUrl:'core/media/space/blog.html', controllerName:"controllers/space/blog",method:"addBlog"}));
						  
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

