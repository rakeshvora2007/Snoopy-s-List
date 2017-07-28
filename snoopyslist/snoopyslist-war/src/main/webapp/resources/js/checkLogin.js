//remove myNavbar content
$('#myNavbar').empty();

//user already login
if (document.cookie.indexOf('Authorization') == -1 && getCookie('Authorization') == "") {
    //add register and login button
    $('#myNavbar').append(
        '<ul class="nav navbar-nav navbar-right">' +
            '<li><a href="signup.html"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>'+
            '<li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>'+
        '</ul>'
    );
} else {
    $('#myNavbar').append(
        '<ul class="nav navbar-nav navbar-right">' +
            '<li><a href="./userhomepage.html">Welcome <span id="username"> ' + getCookie("username") + '</span>!</a></li>' +
            '<li><button id="logout" type="button" class="btn btn-warning">Logout!</button></li>'+
        '</ul>'
    );
}

var main = function () {
    //logout button click
    $('#logout').on('click', function () {
        delete_cookie('Authorization');
        delete_cookie('username');
        window.location = "index.html";
    });
};

$(document).ready(main);
