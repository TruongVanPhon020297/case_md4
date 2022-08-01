$(function (){
    page.elements.frmCreateUser.validate({
        rules: {
            fullName : {
                required : true,
                minlength : 3,
                maxlength : 255,
            },
            email : {
                required : true,
                maxlength :255,
                isEmail : true
            },
            phone : {
                required: true,
                isPhone : true,
            },
            password : {
                required : true,
                // isPassword : true
            },
            address : {
                required : true
            }
        },
        messages: {
            fullName : {
                required : "Vui Lòng Nhập Tên Khách Hàng",
                minlength : "Tên Phải Ít Nhất 3 Ký Tự",
                maxlength : "Tên Không Được Vượt Quá 255 Ký Tự",
            },
            email : {
                required : "Vui Lòng Nhập Email",
                maxlength : "Email Tối Đa 255 Ký Tự"
            },
            phone : {
                required: "Vui Lòng Nhập Số Điện Thoại",
            },
            password : {
                required : "Vui Lòng Nhập Mật Khẩu"

            },
            address : {
                required : "Vui Lòng Nhập Địa Chỉ Khách Hàng"
            }
        },
        errorLabelContainer: page.elements.showError,
        errorPlacement: function (error, element) {
            error.appendTo(page.elements.showError);
        },
        showErrors: function(errorMap, errorList) {
            if (this.numberOfInvalids() > 0) {
                page.elements.showError.removeClass("hide").addClass("show");
            } else {
                page.elements.showError.removeClass("show").addClass("hide").empty();
                $("#frmCreateUser input.error").removeClass("error");
            }
            this.defaultShowErrors();
        },
        submitHandler: function () {
            page.commands.doCreateUser();
        }
    })

    $.validator.addMethod("isEmail",function(value,element){
        return this.optional(element) || /^\w+([-+.'][^\s]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/i.test(value);
    }, "Vui Lòng Nhập Đúng Định Dạng Email");

    // $.validator.addMethod("isPassword",function(value,element){
    //     return this.optional(element) || /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*])(?!.*['"`]).{6,}/i.test(value);
    // }, "Mật Khẩu Ít Nhất 6 Ký Tự , Bao Gồm : Thường - Hoa - Số - Ký Tự Đặc Biệt");

    $.validator.addMethod("isPhone",function(value,element){
        return this.optional(element) || /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/i.test(value);
    },"Vui Lòng Nhập Đúng Định Dạng Số Điện Thoại Việt Nam");
})