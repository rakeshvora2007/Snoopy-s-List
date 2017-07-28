urlValue = "./rest/animal/";
urlValue = urlValue + "dog";

function print (data) {
    console.log(data);
}

/* =================================== search on main page============*/
$('#btnSearch').on('click' , function(event)
{
    event.preventDefault();
    var searchData = {
        "animalType" : $('#animalType').val(),
        "zipCode" : $('#location').val(),
        "age" : $('#age').val(),
        "size": $('#size').val()
    };

    var url = '?';
    $.each(searchData, function (key, value) {
        if (value =='Any')
            value = '';
        url = url + key + "=" + value + '&';
    });

    goToTypeGalery(url);
    // ajaxGetSearch( searchData, goToTypeGalery);
});

function goToTypeGalery(results)
{
    window.location.href = 'http://localhost:8080/snoopyslist/search.html' +  results;
};

// =============================== userhonepage.html,   displays user's posts ===============================
$(document).ready(function() {
    $.ajax({
        type: "GET",

        //Note: direct to the right url
        url: urlValue,
        dataType: 'json',

        success: function(data) {


            var i = 0;


            $.each(data, function(key, value) {




                console.log(data);
                console.log(value.title)


                $("#ul").append("<section class=\"row panel-body\"><section class=\"col-md-6\"><h4> <a href=\"/snoopyslist/petposting.html?postId=" + value.postId + "\"><i class=\"glyphicon glyphicon-th-list\"> </i> " + value.title + "</a></h4><hr><h6> " + value.title +" </h6></section><section class=\"col-md-3\"><h4><a href=\"#\"><i class=\"glyphicon glyphicon-pencil\"></i> Edit</a> <a href=\"#\" id=\"deletepost\"><i class=\"glyphicon glyphicon-remove\"></i> Delete</a></h4><hr><a href=\"#\"><i class=\"glyphicon glyphicon-user\"></i> User: "+ key +" </a><br><a href=\"#\"><i class=\"glyphicon glyphicon-calendar\"></i> 2014-09-15 17:57 PM  </a></section></section><hr></section>")

            });
        }
    });
});



$("#deletepost").on('click', function(event){

    console.log('reached.....');

    ajaxDeletePost(event, function(data){
        console.log('hello: ' + data);
    });
});
