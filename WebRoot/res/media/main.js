require.config({
	baseUrl: 'res/media/',
	paths : {
		angular : '../lib/angular',
		angularRoute : '../lib/angular-route',
		uiRouter : '../lib/angular-ui-router',
		angularCookie : '../lib/angular-cookies.min',
		bootstrap : '../lib/bootstrap/js/bootstrap.min',
		
		totop:'../plugins/UItoTop/jquery.ui.totop.min',
		kindEditor : '../plugins/kindeditor/kindeditor',
		emotion:'../plugins/emotions/emotion',		// qq表情
		
		datepicker:'../plugins/datepicker/bootstrap-datepicker',
		
		ajaxfileupload:'../plugins/file-upload/ajaxfileupload',
		fileupload:'../plugins/file-upload/bootstrap-fileupload',
		jcrop:'../plugins/jcrop/jquery.Jcrop',
		
		/**代码着色*/
		prettify : '../plugins/code-prettify/prettify.min',
		shCore: '../plugins/syntaxhighlighter/scripts/shCore',
		shBrushJScript: '../plugins/syntaxhighlighter/scripts/shBrushJScript',
		shBrushJava: '../plugins/syntaxhighlighter/scripts/shBrushJava',
		/**代码编辑器*/
		cm: '../plugins/codemirror',
		
		/**右键菜单*/
		contextmenu:'../plugins/contextmenu/jquery.contextmenu',
		/**复制到剪切板*/
		zclip: '../plugins/zclip/jquery.zclip',
		/**zTree*/
		ztree_core : '../plugins/zTree/js/jquery.ztree.core-3.5',
		ztree_excheck : '../plugins/zTree/js/jquery.ztree.excheck-3.5',
		
		jquery : '../common/js/jquery.min',
		jqueryUtils : '../common/js/jquery-utils',
		region : '../common/js/region',
		
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
        
        'shBrushJScript':{deps:['shCore']},
        'datepicker':{deps:['jquery']},
        'jqueryUtils' : {deps:['jquery']}		// jquery 扩展工具
    }
});
	
require(['uiRouter',
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
