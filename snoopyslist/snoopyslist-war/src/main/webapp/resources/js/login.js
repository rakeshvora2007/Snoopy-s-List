$('document').ready(function()
{
    /* validation */
    $("#login-form").validate({
        rules:
            {
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 8,
                    maxlength: 15
                },

            },
        messages:
            {
                password:{
                    required: "Provide a Password",
                    minlength: "Password Needs To Be Minimum of 8 Characters"
                },
                email: "Enter a Valid Email",
            },
        submitHandler: submitForm
    });
    /* validation */

    /* form submit */
    function submitForm()
    {
        var data = {
            email: $('#email').val(),
            password: $('#password').val(),
            encodedString:$(''),
            userId:$('')
        };
        $.ajax({

            type : 'POST',
            url  : 'rest/user-login',
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
                if(data.status == "success") {
                    document.cookie = 'Authorization' +"=" + data.encodedString;
                    document.cookie = 'username' +"=" + data.firstName;
                    window.location = "userhomepage.html";
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

});