'use strict';
/* Controllers */
define( function(){
	/** 用户信息 **/
	var UserInfoCtrl = ['$compile','checkUser','$scope', '$http', function($compile, checkUser, $scope, $http){
		
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
                	if(data.status == 1){
                		if($(".jcrop-holder")){
                    		$("#pic_cut").empty();
                    		$("#pic_cut").append(temp);
                    		$compile($("#pic_cut"))($scope);
                    	}
                    	$("#photo").attr("src",data.pic_path+"?"+ new Date().getTime());
                    	$("#preview-pane img").attr("src",data.pic_path+"?"+ new Date().getTime())
                    	
                    	initJcrop();
                    	$("#pic_cut").show();
                	}
                },
                error: function (data, status, e){
                	alert("网络连接失败:"+e);
                }
			});
		};
		// 修改用户头像
		
		$scope.saveImage = function(){
			$scope.size.pic_path = $("#photo").attr("src");
			console.log();
			$http({	method:'post',
				url:"user/saveImage",
				params:$scope.size
			}).success(function(result){
				alert(result.msg);
				checkUser.update(angular.fromJson(result.user))
			}).error(function(){
				alert("网络连接失败");
			});
		};
		
		$scope.size = {}; // 格式:{"x":0,"y":0,"x2":80,"y2":80,"w":80,"h":80} 
		
		require(['ajaxfileupload','jcrop' ], function() {
			//初始化
			initAddress();
			
		});
		
		
		function initAddress(){
			$("#province").val($scope.user.province);
			$("#city").val($scope.user.city);
			$("#district").val($scope.user.district);
		}
		
		var temp = $("#pic_cut").children();  // 临时 图片 剪切dom
		// 初始化 jcrop
		function initJcrop(){
			 var jcrop_api,boundx,boundy,
		        $preview = $('#preview-pane'),
		        $pcnt = $('#preview-pane .preview-container'),
		        $pimg = $('#preview-pane .preview-container img'),

		        xsize = $pcnt.width(),
		        ysize = $pcnt.height();
		    
		    $('#photo').Jcrop({
		        onChange: updatePreview,
		        onSelect: updatePreview,
		        aspectRatio: xsize / ysize,
		        showType:"reference" // 根据参考值 等比缩放
		    },function(){
		        // Use the API to get the real image size
		        var bounds = this.getBounds();
		        
		        $scope.size.boundx = boundx = bounds[0];
		        $scope.size.boundy = boundy = bounds[1];
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
		}
	}];
	
	return {userInfo: UserInfoCtrl};
});


