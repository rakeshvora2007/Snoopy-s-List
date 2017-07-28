var value = getUrlVars();

urlValue = "./rest/pet-detail/" + value["postId"];
console.log()
// =============================== petposting.html, display pet's info ===============================
$(document).ready(function() {



    $.ajax({
        type: "GET",

        url: urlValue,

        dataType: 'json',

        success: function(data) {
            console.log('Pet posting reached');
            console.log(data);


            $("#postTitle").append("<h1>" + data.thumbnail.title + "</h1>");
            $("#petname").append("<h1>" + data.thumbnail.animalName );
            $("#petname").append( "<div class=\"col-md-4\">Gender: " + data.thumbnail.gender + "</div>");
            $("#petname").append("Age: " + data.thumbnail.age );
            $("#ownername").append("<h5></h5>");
            $("#usernamePet").append("");

            console.log(value);
            //used in userhomepage.html

            console.log("<img class=\"img-responsive\" src=\"" + value.pic + "\" max-width:50px max-height:50px>");
            $("#petpic").append("<img class=\"img-thumbnail\" src=\"" + data.thumbnail.picture + "\">");
            $("#petaddress").append("Email: " + data.contractInfo.email);
            $("#petaddress").append("<br>Phone: " + data.contractInfo.phoneNumber);
            $("#petaddress").append("<br>Address: " + data.contractInfo.address);
            $("#petaddress").append("<br>Zip:  " + data.contractInfo.zipCode );

            $("#petdescription").append( "Size: " + data.thumbnail.size);
            $("#petdescription").append("<br>Description: " + data.description);

        }
    });
});
