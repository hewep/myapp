require.config({
	baseUrl: 'res/js',
	paths : {
		angular : 'lib/angular',
		angularRoute : 'lib/angular-route',
		jquery : '/js/jquery-1.7.2.min',
		bootstrap : '/plugins/bootstrap/js/bootstrap'
		
	},
	shim: {
        'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'bootstrap' : {deps:['jquery']},
        'common' :['jquery']
    }
});
require(['core/js/config'], function(main){
	
	require(['angular',
			 'controllers/user',
			 'common',
			 'angularRoute',
			 'bootstrap'
	],function(angular){
		
		angular.bootstrap(document, ['registerApp']);
	});
});
