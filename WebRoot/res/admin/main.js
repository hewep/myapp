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
		prettify : '../plugins/code-prettify/prettify.min',
		/**datatables**/
		datatables : '../plugins/datatables/js/jquery.dataTables',
		datatables_bootstrap : '../plugins/datatables/dataTables.bootstrap',
		/**zTree*/
		ztree_core : '../plugins/zTree/js/jquery.ztree.core-3.5',
		ztree_excheck : '../plugins/zTree/js/jquery.ztree.excheck-3.5',
		/**validate**/
		validate : '../plugins/validation/jquery.validate',
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
        'breadcrumb' : {deps:['jquery']},
        'datatables' : {deps:['jquery']},
        'datatables_bootstrap':{deps:['datatables']},
        'ztree_core' : {deps:['jquery']},
        'ztree_excheck' : {deps:['ztree_core']},
        'validate' : {deps:['jquery']}
    }
});
	
require(['angular','angularRoute',
		 'bootstrap','qtip','actual',
		 'antiscroll','totop','breadcrumb',
		 'datatables_bootstrap','validate',
		 'cookie','application','app','ztree_excheck','ztree_core'
],function(angular){
	Application.init();
	angular.bootstrap(document, ['adminApp']);
});
