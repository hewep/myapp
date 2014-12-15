'use strict';
/* 代码在线工具 */
define( function(){
	// 话题类别
	var CodeRunnerCtrl = ['$scope', '$http', function($scope, $http){
					
		$scope.code = "";
		// 执行代码
		$scope.run = function(){
			var codeType = $("#language").val();
			if($.trim($scope.code) == ""){
				alert("请编写要执行的代码");
			}
		  
			if(codeType == "js"){
				try {
					eval($scope.code);
				} catch (e) {
					log(e);
				}
			}else if(codeType == 'java'){
				  
			}
		};
		
		$scope.themes = ['default', 'blackboard','cobalt','eclipse','elegant','twilight'];
		$scope.currTheme = 'default';
		$scope.changeTheme = function(){
			
			if($scope.currTheme != 'default'){
				$("#cm_theme").attr("href", "res/plugins/codemirror/theme/"+$scope.currTheme+".css");
			}
			$scope.cm.setOption("theme", $scope.currTheme);
			$(".runner-panel").find(".runner-result").addClass($(".runner-code .CodeMirror").attr("class"));
		};
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
