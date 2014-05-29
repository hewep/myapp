require.config({
	baseUrl: 'res/',
	paths : {
		angular : 'lib/angular',
		angularRoute : 'lib/angular-route',
		jquery : 'common/js/jquery-1.7.2.min',
		bootstrap : 'bootstrap/js/bootstrap.min',
		validate : 'media/js/jquery.validate.min',
		formValidate: 'media/js/form-validation',
		application : 'media/js/application'
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'bootstrap' : {deps:['jquery']},
        'validate' : {deps:['jquery']},
        'formValidate' : {deps:['validate']}
    }
});
	
require(['angular',
		 'angularRoute',
		 'bootstrap',
		 'formValidate',
		 'application',
		 'app'
],function(angular){
	Application.initPortletTools();
	FormValidation.init();
	angular.bootstrap(document, ['myApp']);
});
