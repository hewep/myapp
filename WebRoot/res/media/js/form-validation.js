var FormValidation = function () {


    return {
        //main function to initiate the module
        init: function () {

            // for more info visit the official plugin documentation: 
            // http://docs.jquery.com/Plugins/Validation

            var form1 = $('#submit_form');
            var error1 = $('.alert-error', form1);
            var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                /* 验证规则 */
                rules: {
                    //account
                    username: {
                        minlength: 5,
                        required: true
                    },
                    password: {
                        minlength: 5,
                        required: true
                    },
                    rpassword: {
                        minlength: 5,
                        required: true,
                        equalTo: "#submit_form_password"
                    },
                    email: {
                        required: true,
                        email: true
                    }
                },

                messages:{
                    username: {
                        required: "请输入用户名",
                        minlength: "长度不小于5"
                    },
                    password: {
                        minlength: "长度不小于5",
                        required: "请输入密码"
                    },
                    email:{
                        required: "请输入电子邮箱",
                        email: "邮箱地址无效"
                    },
                    rpassword:{
                        minlength: "长度不小于5",
                        required: "请输入密码",
                        equalTo: "密码输入不一致"
                    }
                },
                invalidHandler: function (event, validator) { //display error alert on form submit              
                    error1.show();
                    App.scrollTo(error1, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.help-inline').removeClass('ok'); // display OK icon
                    $(element)
                        .closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change dony by hightlight
                    $(element)
                        .closest('.control-group').removeClass('error'); // set error class to the control group
                },

                success: function (label) {
                    label
                        .addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
                    .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
                },

                submitHandler: function (form) {
                    success1.show();
                    error1.hide();
                },
                /* 设置错误信息提示DOM */  
                errorPlacement: function(error, element) {     
                    element.next().hide();
                    error.appendTo( element.parent());       
                }
            });

        }

    };

}();