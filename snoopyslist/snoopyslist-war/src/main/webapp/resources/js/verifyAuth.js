if (document.cookie.indexOf('Authorization') == -1 && getCookie('Authorization') == "") {
    alert('401 UNAUTHORIZED. You can\'t access this page');
    window.location = "index.html";
}