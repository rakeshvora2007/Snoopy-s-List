function print (d) {
    console.log(d);
}
var main = function () {
    var urlQuery = getUrlQuery();
    console.log(urlQuery);

    ajaxGetSearch(urlQuery, displayData);
};

function displayData( data) {
    print(data.thumbnailList);
    var i = 0;
    $.each(data.thumbnailList, function(key, value) {

        $("#ultype").append("<div class=\"col-sm-4\"><a href=\"petposting.html?postId="+ value.postId + "\"><img src=" + value.picture + " class=\"img-thumbnail\"></a><h4>" + value.title + "</h4></div>");
        i++;
    });
}
$(document).ready(main);