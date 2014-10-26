require.config({
	baseUrl: 'res/media/',
	paths : {
		angular : '../lib/angular',
		angularRoute : '../lib/angular-route',
		uiRouter : '../lib/angular-ui-router',
		angularCookie : '../lib/angular-cookies.min',
		
		jquery : '../common/js/jquery.min',
		jqueryUtils : '../common/js/jquery-utils',
		region : '../common/js/region',
		bootstrap : '../bootstrap/js/bootstrap.min',
		
		totop:'../plugins/UItoTop/jquery.ui.totop.min',
		kindEditor : '../plugins/kindeditor/kindeditor',
		prettify : '../plugins/code-prettify/prettify.min',
		emotion:'../plugins/emotions/emotion',		// qq表情
		
		datepicker:'../plugins/datepicker/bootstrap-datepicker',
		
		ajaxfileupload:'../plugins/file-upload/ajaxfileupload',
		fileupload:'../plugins/file-upload/bootstrap-fileupload',
		jcrop:'../plugins/jcrop/jquery.Jcrop',
		
		application : 'js/application'
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'uiRouter' : {deps:['angular']},
        'angularCookie' : {deps:['angular']},
        'app' : {deps:['angular']},
        
        'bootstrap' : {deps:['jquery']},
        'prettify' : {deps:['jquery']},
        'totop' : {deps:['jquery']},
        'emotion':{deps:['jquery']},
        
        'datepicker':{deps:['jquery']},
        
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
