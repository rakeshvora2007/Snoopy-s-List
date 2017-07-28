
var main = function () {
    $('#submitButton').on('click', function (event) {
        event.preventDefault();
        var data = {"title" : $('#title').val(),
            "animalName" : $('#animalName').val(),
            "animalType" : $('#animalType').val(),
            "gender" : "male",
            "age" : $('#age').val(),
            "size": $('#size').val(),
            "description": $('#description').val(),
            "address": $('#address').val(),
            "city": $('#city').val(),
            "zipCode": $('#zipCode').val()
        };

        var formData = new FormData();
        formData.append('section', 'general');
        formData.append('action', 'previewImg');
        // Attach file
        formData.append('file', $('input[type=file]')[0].files[0]);

        ajaxPetPost("POST", data, formData, function (result) {
            console.log(result);
            window.location ="userhomepage.html";
        })
    });

};

$(document).ready(main);