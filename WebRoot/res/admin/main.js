require.config({
	baseUrl: 'res/admin',
	paths : {
		angular : '../lib/angular',
		angularRoute : '../lib/angular-route',
		jquery : '../common/js/jquery.min',
		bootstrap : '../bootstrap/js/bootstrap.min',
		qtip: '../plugins/qtip2/jquery.qtip.min',	
		colorbox: '../plugins/colorbox/jquery.colorbox.min',
		cookie:'../common/js/jquery.cookie.min',
		actual:'../common/js/jquery.actual.min',
		antiscroll:'../plugins/antiscroll/antiscroll',
		totop:'../plugins/UItoTop/jquery.ui.totop.min',
		breadcrumb: '../plugins/jBreadcrumbs/js/jquery.jBreadCrumb.1.1.min',
		
		application:'js/application'
		
	},
	shim: {
		'angular' : {'exports' : 'angular'},
        'angularRoute' : {deps:['angular']},
        'bootstrap' : {deps:['jquery']},
        'qtip' : {deps:['jquery']},
        'cookie' : {deps:['jquery']},
        'actual' : {deps:['jquery']},
        'antiscroll' : {deps:['jquery']},
        'totop' : {deps:['jquery']},
        'breadcrumb' : {deps:['jquery']}
    }
});
	
require(['angular','angularRoute',
		 'bootstrap','qtip','actual',
		 'antiscroll','totop','breadcrumb',
		 'cookie','application','app'
],function(angular){
	Application.init();
	angular.bootstrap(document, ['adminApp']);
});
