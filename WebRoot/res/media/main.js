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
		prettify : 'plugins/code-prettify/prettify.min'
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'bootstrap' : {deps:['jquery']},
        'prettify' : {deps:['jquery']},
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
	angular.bootstrap(document, ['myApp']);
});
