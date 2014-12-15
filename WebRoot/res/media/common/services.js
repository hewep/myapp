'use strict';

/* service */
define(['angular'], function(){
	var comm = angular.module('CommServices', []);
	
	comm.factory('sh', function(){
		
		return {
			hightlight: function($element){
				require(['shCore'], function(){
					var type = $element.find("pre").attr("class");
					type = type.substring(type.indexOf(':')+1);
					type = type === 'js'? 'JScript' : type.toCapUpperCase();
				    var brush = 'shBrush'+type+".js";
				    loadScript("res/plugins/syntaxhighlighter/scripts/"+brush, function(){
				    	SyntaxHighlighter.highlight();
				    });
					
				});
			}
		};
	});
	
	comm.factory('ztree', function(){
		
		var zTree;
		return {
			init : function(params){
				require(['ztree_core'], function(){
					var setting = {
							view: {
								showIcon :false
							},
							data: {
								simpleData: {
									enable: true
								}
							}
						};
					zTree = $.fn.zTree.init($("#"+params.id), setting, angular.fromJson(params.data));
				});
			},
			getSelectNodeId: function(){
				if(zTree){
					var nodes = zTree.getSelectedNodes();
					if(nodes.length <= 0){
						nodes = zTree.getNodes();
					}
					return nodes[0].id;
				}
				return 0;
			}
		};
	});
});

function loadScript(url, fn) {
	var head = document.getElementsByTagName('head')[0] || (_QUIRKS ? document.body : document.documentElement),
		script = document.createElement('script');
	head.appendChild(script);
	script.src = url;
	script.charset = 'utf-8';
	script.onload = script.onreadystatechange = function() {
		if (!this.readyState || this.readyState === 'loaded') {
			if (fn) {
				fn();
			}
			script.onload = script.onreadystatechange = null;
			head.removeChild(script);
		}
	};
}