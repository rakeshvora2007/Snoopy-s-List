$('document').ready(function()
{
    //hide website
    $('#website').hide();

    /* validation */
    $("#register-form").validate({
        rules:
            {
                firstName: {
                    required: true,
                    minlength: 2
                },

                lastName: {
                    required: true,
                    minlength: 3
                },

                phoneNumber:{
                    required:true,
                    minlength: 10
                },

                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 8,
                    maxlength: 15
                },
                password_confirmation: {
                    required: true,
                    equalTo: '#password'
                },

            },
        messages:
            {
                firstName: "Enter a Valid First Name",
                lastName: "Enter a Valid Last Name",
                phoneNumber: "Enter a Valid Phone Number",
                password:{
                    required: "Provide a Password",
                    minlength: "Password Needs To Be Minimum of 8 Characters"
                },
                email: "Enter a Valid Email",
                cpassword:{
                    required: "Retype Your Password",
                    equalTo: "Password Mismatch! Retype"
                }
            },
        submitHandler: submitForm
    });
    /* validation */

    /* form submit */
    function submitForm()
    {
        console.log($('#userType').val());

        var data = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            userType: parseInt($('#userType').val()),
            phoneNumber : $('#phoneNumber').val(),
            website: $('#website').val()
        };
        console.log(JSON.stringify(data));
        $.ajax({

            type : 'POST',
            url  : 'rest/user-create',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data : JSON.stringify(data),
            beforeSend: function()
            {
                $("#error").fadeOut();
                $("#btn-submit").html('<span class="glyphicon glyphicon-transfer"></span> &nbsp; sending ...');
            },
            success :  function(data)
            {
                console.log(data);
                if(data.status == "success") {
                    window.location = "http://localhost:8080/snoopyslist";
                } else {
                    $('#errorMessage').fadeIn();
                }

                return false;
            }
        });
        return false;
    }
    /* form submit */

    //cancel button press
    $('#cancelButton').on('click', function () {
        window.location = "index.html";
    });

    //hidden or display website input when usertpye select
    $('#userType').change(function () {
        if($(this).val() == '1') {
            $('#website').show();
        } else {
            $('#website').hide();
        }
    });

});