'use strict';
/* 代码在线工具 */
define( function(){
	// 话题类别
	var CodeRunnerCtrl = ['$scope', '$http', function($scope, $http){
		require(["cm/lib/codemirror","cm/mode/javascript",
		         "cm/addon/selection/active-line","cm/addon/edit/matchbrackets",
		         "contextmenu", "zclip"
		         ], function(CodeMirror){
					
			var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
			    lineNumbers: true,
			    styleActiveLine: true,
			    matchBrackets: true
			});
			  
			  var menu = $("#result").contextMenu();
			  menu.addItem("清 空", "clear");
			  
			  $("#copy").zclip({
					path:"res/plugins/zclip/ZeroClipboard.swf",
					copy:function(){ return editor.getSelections();}
			  });
			  
			    // 执行代码
				$scope.run = function(){
					var codeType = $("#language").val();
					var code = editor.getValue();
					if($.trim(code) == ""){
						alert("请编写要执行的代码");
					}
				  
					if(codeType == "js"){
						try {
							eval(code);
						} catch (e) {
							log(e);
						}
					}else if(codeType == 'java'){
						  
					}
				};
		});
		
		
		// js 日志打印函数
		function log(result){
			 var text = $("#result").html();
			 if(text){
				 text += "<br/>";
			 } 
			 $("#result").html(text+ "<span style='color:blue;font-size:12px;'><strong>>></strong></span>"+ result);
		}
 	}];
	
	return {codeRuuner: CodeRunnerCtrl};
});
