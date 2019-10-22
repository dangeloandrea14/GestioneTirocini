/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var baseURL = "http://localhost:8080/tirocini/";

function setupEvents() {
    setupModalsEvents();
    setupSearchEvents();
    
    $('.dynalink').on('click', function (e) {
        e.preventDefault();
        var dataURL = baseURL + $(this).attr('href');
        history.pushState(undefined, '', dataURL);
        $.ajax({
            type: "GET",
            url: dataURL,
            data: {"dyn": ""},
            beforeSend: function () {
                //$(".post_submitting").show().html("<center><img src='images/loading.gif'/></center>");
                $("#body").addClass('fadeOut faster');
                $(this).parent('.nav-item').addClass('active');
                
                $('html, body').animate({ scrollTop: 0 }, 'fast');
            },
            success: function (response) {
                $("#body").removeClass('fadeOut faster');
                $("#body").html(response);
                setupEvents();
                //$(".post_submitting").fadeOut(1000);

            },
            error: function (jqXHR, exception) {
                // Note: Often ie will give no error msg. Aren't they helpful?
                console.log('ERROR: jqXHR, exception', jqXHR, exception);
            }
        });
        e.preventDefault();
    });
}

function setupModalsEvents() {
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

                $("#detailsModalBody").html($($.parseHTML(response)).find("#body"));
                $('#detailsModal').modal('show');

                //Sposta il titolo nell'header ed elimina il primo <hr>
                $('.modal-title').html($('#details-title').html());
                $('#detailsModalLabel').addClass('col-lg-10 mx-auto mt-0 pt-0');
                $('#details-title').remove();
                $("#hr1").remove();
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

function setupSearchEvents() {
    $('#search-aziende').on('keypress', function (e) {
        /*console.log(document.getElementById('search-aziende').value);
         console.log($(this));
         length = document.getElementById('search-aziende').textLength + 1;*/
    });
}

function updateContent(id, dataURL, q) {
    history.pushState(undefined, '', dataURL);
    $.ajax({
        type: "GET",
        url: dataURL,
        data: {"dyn": "",
            "q": q},
        beforeSend: function () {
            //$(".post_submitting").show().html("<center><img src='images/loading.gif'/></center>");
            $(id).addClass('fadeOut faster');
        },
        success: function (response) {
            $(id).removeClass('fadeOut faster');
            $(id).html(response);
            setupEvents();
            //$(".post_submitting").fadeOut(1000);

        },
        error: function (jqXHR, exception) {
            // Note: Often ie will give no error msg. Aren't they helpful?
            console.log('ERROR: jqXHR, exception', jqXHR, exception);
        }
    });
    e.preventDefault();
}

$(document).ready(function () {

    setupEvents();

    /*
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
     */

    
});