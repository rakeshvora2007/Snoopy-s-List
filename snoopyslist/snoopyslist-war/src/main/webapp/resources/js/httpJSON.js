/*****************************************************
 *  Ajax #1
 *      Example: /rest/pet-search?location=12345&animal=cat
 *  search pet get:
 *      Input:
 *          Mandatory:
 *              location
 *          Optional:
 *              title
 *              animalName
 *              animalType
 *              age
 *              gender
 *              distance (range)
 *              size
 *      Output:
 *           searchNumber
 *           thumbnailList {
 *              postId
 *              name
 *              animal
 *              age
 *              gender
 *              location
 *              picture
 *          }, {...}
 ******************************************************/
var ajaxGetSearch= function ( searchData, successFunction)
{
    var url = "rest/pet-search" + searchData;
    $.ajax({
        url: url,
        method: 'GET',
        dataType: 'json',
        success: function(result) {
            if (result) {
                successFunction(result);
            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #1')
            }
        },
        error: function() {
            console.log('Error in Ajax #1: fail during get request.');
        }
    });
};

/*****************************************************
 *  Ajax #2
 *      Example: /rest/pet-detail/12345
 *  GET request for Post Detail:
 *      Input:
 *           Mandatory:
 *              postId
 *
 *      Output:
 *          thumbnail {
 *              postId
 *              title
 *              animalType
 *              animalName
 *              age
 *              gender
 *              size
 *              picture
 *          }
 *          contractInfo {
 *              phoneNumber
 *              address
 *              city
 *              zipCode
 *              email
 *          }
 *          description
 ******************************************************/
var ajaxGetPostDetail = function ( postId, successFunction)
{
    $.ajax({
        url: "rest/pet-detail/" + postId,
        method: 'GET',
        dataType: 'json',
        success: function(result) {
            if (result) {
                successFunction(result);
            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #2')
            }
        },
        error: function() {
            console.log('Error in Ajax #2: fail during get request.');
        }
    });
};

/*****************************************************
 * Ajax #3
 *          Example: /rest/user-create
 *      Post Request for creating user account
 *          Input:
 *              firstName
 *              lastName
 *              email
 *              password
 *              userType (0-regular or 1-agent)
 *              phoneNumber
 *          Optional:
 *              website
 *
 *          Output:
 *              result: Success or Failure
 ******************************************************/
var ajaxCreateAccount = function ( jsonData, successFunction)
{
    $.ajax({
        url: "rest/user-create",
        method: 'POST',
        data: JSON.stringify(jsonData),
        contentType:'application/json',
        dataType: 'json',
        success: function(result) {
            if (result) {
                successFunction(result);
            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #3')
            }
        },
        error: function() {
            console.log('Error in Ajax #3: fail during get request.');
        }
    });
};

/*****************************************************
 * Ajax #4 POST and PUT method
 *          Example: /rest/secured/pet-post
 *      Post Request for creating pet post
 *          Input:
 *              jsonData:
 *              {
 *                  title
 *                  animalName
 *                  animalType
 *                  age
 *                  gender
 *                  size
 *                  address
 *                  city
 *                  zipCode
 *                  description
 *               }
 *              picture: optional (image file)
 *
 *          Output:
 *              result: Success or Failure
 ******************************************************/
var sendImage = function(postId, formData, callback) {
    $.ajax({
        url:  "rest/image/" + postId,
        method: "POST",
        data: formData,
        contentType: false,
        cache: false,
        processData: false,
        success: function (response) {
            callback(response);
        }
    });
}

var ajaxPetPost = function (method, jsonData, formData, successFunction)
{
    var status = {postStatus: "failure", imageStatus: "failure"};
    $.ajax({
        url: "rest/secured/pet-post",
        method: method,
        async: false,
        data: JSON.stringify(jsonData),
        contentType:'application/json',
        dataType: 'json',
        success: function(result) {
            console.log(result);
            if (result.result == "success") {
                console.log("get in the send image");
                status.postStatus = "success";
                console.log(formData);
                if(formData != null) {
                    sendImage(result.postId, formData, function (response) {
                        if(response.status == "success") {
                            status.imageStatus = "success";
                        }
                    });
                } else {
                    console.log("There is no image");
                }

            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #4')
            }
            console.log(status);
            successFunction(status);
        },
        error: function() {
            console.log('Error in Ajax #4: fail during get request.');
        }
    });
};

/*****************************************************
 * Ajax #6 DELETE method
 *          Example: /rest/secured/pet-post
 *      Post Request for creating pet post
 *          Input:
 *              Mandatory
 *                  userId
 *
 *          Output:
 *              result: Success or Failure
 ******************************************************/
var ajaxDeletePost = function ( jsonData, successFunction)
{
    $.ajax({
        url: "rest/secured/pet-post",
        method: 'DELETE',
        data: JSON.stringify(jsonData),
        contentType:'application/json',
        dataType: 'json',
        success: function(result) {
            if (result) {
                successFunction(result);
            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #6')
            }
        },
        error: function() {
            console.log('Error in Ajax #6: fail during get request.');
        }
    });
};

/*****************************************************
 * Ajax #7
 *          Example: /rest/user-posts
 *      Post Request for getting user Post list
 *          Input: none
 *
 *          Output: array of post List
 *            {
 *              postId
 *              name
 *              animal (categories)
 *              timestamp (dd/MM/yyyy)
 *             } , {...}
 ******************************************************/
var ajaxUserPostList = function (successFunction)
{
    $.ajax({
        url: "rest/secured/user-posts",
        method: 'POST',
        contentType:'application/json',
        dataType: 'json',
        success: function(result) {
            if (result) {
                successFunction(result);
            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #7')
            }
        },
        error: function() {
            console.log('Error in Ajax #7: fail during get request.');
        }
    });
};

/*****************************************************
 * Ajax #8
 *          Example: /rest/user-login
 *      Post Request for user login
 *          Input:
 *              Mandatory
 *                  email
 *                  password
 *
 *          Output:
 *              Success or Failure
 ******************************************************/
var ajaxPostUserLogin = function ( jsonData, successFunction)
{
    $.ajax({
        url: "rest/user-login",
        method: 'POST',
        data: JSON.stringify(jsonData),
        contentType:'application/json',
        dataType: 'json',
        success: function(result) {
            if (result) {
                successFunction(result);
            } else {
                successFunction(null);
                console.log('Get request for Search is empty look at ajax #8')
            }
        },
        error: function() {
            console.log('Error in Ajax #8: fail during get request.');
        }
    });
};

/*****************************************************
 *  Ajax #9
 *      Example: /rest/animal/{animalType}
 *  GET request for Post Detail:
 *      output:
 *          list<Thumbail>
 ******************************************************/
var ajaxGetThumbnail = function ( animalType, successFunction)
{
    console.log(animalType);
    $.ajax({
        url: "rest/animal/" + animalType,
        method: "GET",
        dataType: "json",
        success: function(result) {
            if (result) {
                console.log("Result: " + result);
                successFunction(result);
            } else {
                successFunction(null);
                console.log("Get request for Search is empty look at ajax #9");
            }
        },
        error: function() {
            console.log("Error in Ajax #9: fail during get request.");
        }
    });
};

