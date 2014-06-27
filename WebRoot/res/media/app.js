'use strict';
// Declare app level module which depends on filters, and services
define(['angular',
        'media/controllers/index',
        'media/controllers/topic',
        'media/controllers/user',
        'media/directives/common',
        'media/filters'
],function(angular,index, topic){
	var app =  angular.module('myApp', ['ngRoute','ngCookies','UserCtrls','CommDirectives', 'CommFilters']);
	
		app.config(['$routeProvider','$locationProvider', '$httpProvider', function($routeProvider, $locationProvider,$httpProvider) {
			 $routeProvider.when('/', {templateUrl: 'core/media/category.html', controller: index.categorys})
			  				.when('/topic', {templateUrl:'core/media/topic.html', controller: topic.topic})
			  				.when('/topic_list/:category_id',{templateUrl:'core/media/topic_list.html', controller:topic.topicList})
			  				.when('/topic_info/:topic_id',{templateUrl:'core/media/topic_info.html', controller:topic.topicInfo})
			  				.when('/register', {templateUrl: 'core/media/register.html', controller: 'RegisterCtrl' })
			  				.otherwise({redirectTo: '/'});
			  				
			  	// configure html5		
			  	//$locationProvider.html5Mode(true);
			 	
			 	// 配置拦截器
			 	$httpProvider.interceptors.push('anthInterceptor');
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
		// 检测用户是否登录 service
		app.factory('checkUser', ['$rootScope','$cookieStore', function($rootScope, $cookieStore){
			
			if($cookieStore.get("current_user")){	 // 第一次进入或 刷新页面 检查用户是否登录
				$rootScope.isLogin = true;
				$rootScope.currentUser = $cookieStore.get("current_user");
			}else{
				$rootScope.isLogin = false;
				$rootScope.currentUser = {};
			}
			return {
				login:function(user){
					$rootScope.isLogin = true;
					$cookieStore.put("current_user", user);
					$rootScope.currentUser = user;
					
				},logout: function(){
					$cookieStore.remove("current_user");
					$rootScope.isLogin = false;
				},isLogin: function(){
					return $rootScope.isLogin;
				}
			};
			
		}]);
	return app;
});