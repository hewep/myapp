'use strict';
/* Controllers */
define( function(){
	/** 用户信息 **/
	var UserInfoCtrl = ['$rootScope','$scope', '$http', function($rootScope, $scope, $http){
		
		$scope.user = angular.copy($rootScope.currentUser);
		
		// 保存用户 基本信息
		$scope.saveBasicInfo = function(){
			$scope.user.province = $("#province").val();
			$scope.user.city = $("#city").val();
			$scope.user.district = $("#district").val();
			
			$http({	method:'post',
				url:"user/updateUser",
				params:$scope.user
			}).success(function(result){
				if(result.status == 1){
					angular.copy($scope.user, $rootScope.currentUser);
					$("li a[href='#password']").tab('show');
				}else{
					alert(result.msg);
				}
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		// 更新用户密码
		$scope.isConsistent = true;
		$scope.updatePassword = function(){
			var oldPassword = $("#old_password").val();
			var newPassword = $("#new_password").val();
			var rePassword = $("#re_password").val();

			if(rePassword != newPassword){
				$scope.isConsistent = false;
				return;
			}
			
			var params = {"oldPassword":oldPassword, "newPassword":newPassword, "rePassword":rePassword};
			$http({	method:'post',
				url:"user/updatePassword",
				params:params
			}).success(function(result){
				$("#tip_info").text(result.msg);
				$("#tip_info").show();
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		//修改用户头像
		$scope.uploadImage = function(){
			var filePath = $("#head_pic").val();
			if(!filePath){
				alert("请选择要上传的图片");return;
			}
			$.ajaxFileUpload({
				url: "user/uploadImage",
				fileElementId : "head_pic",
				secureuri: false,
				dataType: 'json',
                success: function (data, status) {
                	alert(data.msg);
                },
                error: function (data, status, e){
                	alert("网络连接失败:"+e);
                }
			});
		};
		
		//初始化
		init();
		
		function init(){
			$("#province").val($scope.user.province);
			$("#city").val($scope.user.city);
			$("#district").val($scope.user.district);
		}
		
	}];
	
	return {userInfo: UserInfoCtrl};
});