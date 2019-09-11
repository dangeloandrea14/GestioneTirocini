/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var baseURL = "http://localhost:8080/tirocini/";

function setModalsEvents() {
    $('.details').on('click', function (e) {
        e.preventDefault();
        var dataURL = baseURL + $(this).attr('href');
        $.ajax({
            type: "GET",
            url: dataURL,
            data: {"dyn": ""},
            beforeSend: function () {
                //$(".post_submitting").show().html("<center><img src='images/loading.gif'/></center>");
            },
            success: function (response) {
                //$(".post_submitting").fadeOut(1000);
                $("#detailsModalBody").html(response);
                $('#detailsModal').modal('show');
                //$('.modal-title').replaceWith($('.details-title'));
            },
            error: function (jqXHR, exception) {
                // Note: Often ie will give no error msg. Aren't they helpful?
                console.log('ERROR: jqXHR, exception', jqXHR, exception);
            }
        });
        e.preventDefault();
    });
}
;

$(document).ready(function () {

    setModalsEvents();

    $('.nav-link, .navbar-brand').on('click', function (e) {
        e.preventDefault();
        var dataURL = baseURL + $(this).attr('href');
        history.pushState(undefined, '', dataURL);
        $.ajax({
            type: "GET",
            url: dataURL,
            data: {"dyn": ""},
            beforeSend: function () {
                //$(".post_submitting").show().html("<center><img src='images/loading.gif'/></center>");
                $("#body").fadeOut(10);
                $(".footer").fadeOut(10);
                $('.nav-item').removeClass('active');
                $(this).parent('.nav-item').addClass('active');
            },
            success: function (response) {
                $("#body").html(response);
                $("#body").fadeIn(500);
                $(".footer").fadeIn(500);
                setModalsEvents();
                //$(".post_submitting").fadeOut(1000);

            },
            error: function (jqXHR, exception) {
                // Note: Often ie will give no error msg. Aren't they helpful?
                console.log('ERROR: jqXHR, exception', jqXHR, exception);
            }
        });
        e.preventDefault();
    });
    
    $('#search-aziende').on('keypress', function(e) {
        console.log(document.getElementById('search-aziende').value);
    });
});