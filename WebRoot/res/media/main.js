require.config({
	baseUrl: 'res/media/',
	paths : {
		angular : '../lib/angular',
		angularRoute : '../lib/angular-route',
		uiRouter : '../lib/angular-ui-router',
		angularCookie : '../lib/angular-cookies.min',
		jquery : '../common/js/jquery-1.7.2.min',
		bootstrap : '../bootstrap/js/bootstrap.min',
		totop:'../plugins/UItoTop/jquery.ui.totop.min',
		application : 'js/application',
		kindEditor : '../plugins/kindeditor/kindeditor',
		prettify : '../plugins/code-prettify/prettify.min',
		jqueryUtils : '../common/js/jquery-utils'
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'uiRouter' : {deps:['angular']},
        'app' : {deps:['angular']},
        'angularCookie' : {deps:['angular']},
        
        'bootstrap' : {deps:['jquery']},
        'prettify' : {deps:['jquery']},
        'totop' : {deps:['jquery']},
        'jqueryUtils' : {deps:['jquery']}		// jquery 扩展工具
    }
});
	
require(['uiRouter',
		 'angularRoute',
		 'angularCookie',
		 'bootstrap',
		 'totop',
		 'application',
		 'jqueryUtils',
		 'app'
],function(){
	Application.initPortletTools();
	Application.initToTop();
	angular.bootstrap(document, ['myApp']);
});
