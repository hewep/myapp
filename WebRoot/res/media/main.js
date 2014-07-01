require.config({
	baseUrl: 'res/',
	paths : {
		angular : 'lib/angular',
		angularRoute : 'lib/angular-route',
		angularCookie : 'lib/angular-cookies.min',
		jquery : 'common/js/jquery-1.7.2.min',
		bootstrap : 'bootstrap/js/bootstrap.min',
		validate : 'media/js/jquery.validate.min',
		formValidate: 'media/js/form-validation',
		application : 'media/js/application',
		kindEditor : 'plugins/kindeditor/kindeditor',
		prettify : 'plugins/code-prettify/prettify.min',
		common : 'common/js/common',
		/** 控制层 js 文件**/
		indexCtrl: 'media/controllers/index',  
		topicCtrl: 'media/controllers/topic',
		userCtrl: 'media/controllers/user',
		directives : 'media/common/directives',
		filters : 'media/common/filters',
		
		app : 'media/app'
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'app' : {deps:['angular']},
        'angularCookie' : {deps:['angular']},
        
        'indexCtrl' : {deps:['angular']},
        'topicCtrl' : {deps:['angular']},
        'userCtrl' : {deps:['angular']},
        'directives' : {deps:['angular']},
        'filters' : {deps:['angular']},
        
        'bootstrap' : {deps:['jquery']},
        'prettify' : {deps:['jquery']},
        'validate' : {deps:['jquery']},
        'formValidate' : {deps:['validate']},
        'common' : {deps:['jquery']}
    }
});
	
require(['angular',
		 'angularRoute',
		 'angularCookie',
		 'bootstrap',
		 'application',
		 'common',
		 'app'
],function(angular){
	Application.initPortletTools();
	angular.bootstrap(document, ['myApp']);
});
