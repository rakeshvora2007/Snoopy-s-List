
urlValue = "./rest/animal/";
//Code from: https://www.w3schools.com/js/tryit.asp?filename=tryjs_cookie_username


$(document).ready(function() {
    var urlQuery = getUrlVars();

    console.log(urlQuery["animalType"]);
    urlValue = urlValue + "/" + urlQuery["animalType"];
    $("#type").append("<h1>" + urlQuery["animalType"].toUpperCase() + "</h1>");

    ajaxGetThumbnail(urlQuery["animalType"], workNO);

});

var workNO = function (data) {
    console.log(data);


    var i = 0;
    $.each(data, function(key, value) {


console.log("<div class=\"col-sm-4\"><a href=\"petposting.html?postId="+ value.postId + "\"><h4 style=\"text-align:center\">" + value.animalName + "</h4><img src=" + value.picture + " class=\"img-thumbnail sameImageSize\" ></a><h4 style=\"text-align:center\">" + value.title + "</h4></div>");

        $("#ultype").append("<div class=\"col-sm-4\"><a href=\"petposting.html?postId="+ value.postId + "\"><h4 style=\"text-align:center\">" + value.animalName + "</h4><img src=" + value.picture + " class=\"img-thumbnail sameImageSize\" ></a><h4 style=\"text-align:center\">" + value.title + "</h4></div>");

        i++;
    });


};
