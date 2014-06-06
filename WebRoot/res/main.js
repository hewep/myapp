require.config({
	baseUrl: 'res/',
	paths : {
		angular : 'lib/angular',
		angularRoute : 'lib/angular-route',
		jquery : 'common/js/jquery-1.7.2.min',
		bootstrap : 'bootstrap/js/bootstrap.min',
		validate : 'media/js/jquery.validate.min',
		formValidate: 'media/js/form-validation',
		application : 'media/js/application',
		kindEditor : 'plugins/kindeditor/kindeditor',
		
		directives : 'common/directives',
		index : 'controllers/index',
		user : 'controllers/user',
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'index' : {deps:['angular']},
        'user' : {deps:['angular']},
        'directives':{deps:['angular']},
        'bootstrap' : {deps:['jquery']},
        'validate' : {deps:['jquery']},
        'formValidate' : {deps:['validate']}
    }
});
	
require(['angular',
		 'angularRoute',
		 'bootstrap',
		 'application',
		 'app'
],function(angular){
	Application.initPortletTools();
	//FormValidation.init();
	angular.bootstrap(document, ['myApp']);
});
