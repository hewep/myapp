'use strict';
/* Controllers */
define( function(){
	/** 用户信息 **/
	var UserInfoCtrl = ['checkUser','$scope', '$http', function(checkUser, $scope, $http){
		
		$scope.user = angular.copy(checkUser.getUser());
		
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
					checkUser.update($scope.user);
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
		
		//上传用户头像
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
                	checkUser.update(angular.fromJson(data.user))
                },
                error: function (data, status, e){
                	alert("网络连接失败:"+e);
                }
			});
		};
		// 修改用户头像
		
		$scope.saveImage = function(){
			
			$http({	method:'post',
				url:"user/saveImage",
				params:$scope.size
			}).success(function(result){
				alert(result.msg);
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		$scope.size = {}; // 格式:{"x":0,"y":0,"x2":80,"y2":80,"w":80,"h":80} 
		
		require([ 'fileupload','ajaxfileupload','jcrop' ], function() {
			//初始化
			init();
			
		    var jcrop_api,
		        boundx,
		        boundy,

		        // Grab some information about the preview pane
		        $preview = $('#preview-pane'),
		        $pcnt = $('#preview-pane .preview-container'),
		        $pimg = $('#preview-pane .preview-container img'),

		        xsize = $pcnt.width(),
		        ysize = $pcnt.height();
		    
		    $('#photo').Jcrop({
		        onChange: updatePreview,
		        onSelect: updatePreview,
		        aspectRatio: xsize / ysize
		    },function(){
		        // Use the API to get the real image size
		        var bounds = this.getBounds();
		        
		        $scope.size.boundx = boundx = bounds[0];
		        $scope.size.boundy = boundy = bounds[1];
		        console.log($scope.size)
		        jcrop_api = this;
		        jcrop_api.animateTo([0,0,100,100]);
		      
		        $preview.appendTo(jcrop_api.ui.holder);
		    });
		    
		    
		    function updatePreview(c){
		      if (parseInt(c.w) > 0){
		        var rx = xsize / c.w;
		        var ry = ysize / c.h;
	
		        $(".preview-container img").css({
		          width: Math.round(rx * boundx) + 'px',
		          height: Math.round(ry * boundy) + 'px',
		          marginLeft: '-' + Math.round(rx * c.x) + 'px',
		          marginTop: '-' + Math.round(ry * c.y) + 'px'
		        });
		        
		        $.extend($scope.size, c); //  预览大小
		      }
		    };
			
		});
		
		
		function init(){
			$("#province").val($scope.user.province);
			$("#city").val($scope.user.city);
			$("#district").val($scope.user.district);
		}
		

	}];
	
	return {userInfo: UserInfoCtrl};
});


