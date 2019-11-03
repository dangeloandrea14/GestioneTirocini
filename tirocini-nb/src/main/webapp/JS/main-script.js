/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var baseURL = "http://localhost:8080/tirocini/";

function setupEvents() {
    setupModalsEvents();
    setupSearchEvents();
    setupDynalinkEvents();
}

function setupDynalinkEvents() {
    $('.dynalink').on('click', function (e) {
        e.preventDefault();
        var dataURL = baseURL + $(this).attr('href');
        
        updatePage(dataURL);
        
        history.pushState(undefined, '', dataURL);
        
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

                $("#detailsModalBody").html($($.parseHTML(response)).find("#detailsBody"));
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

function updatePage(URL) {
    $.ajax({
            type: "GET",
            url: URL,
            data: {"dyn": ""},
            beforeSend: function () {
                $("#body").addClass('fadeOut faster');
                
                // non funziona
                //$(this).parent('.nav-item').addClass('active');

                //$('html,body').scrollTop(0);
                $('html, body').animate({scrollTop: 0}, 'fast');
            },
            success: function (response) {
                console.log(response);
                $("#body").removeClass('fadeOut faster');
                $("#body").html(response);
                document.title = $("#body").find("#page_title").text();
                setupEvents();

            },
            error: function (jqXHR, exception) {
                console.log('ERROR: jqXHR, exception', jqXHR, exception);
            }
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
            $(id).addClass('fadeOut faster');
        },
        success: function (response) {
            $(id).removeClass('fadeOut faster');
            $(id).html(response);
            setupEvents();
            //$(".post_submitting").fadeOut(1000);

        },
        error: function (jqXHR, exception) {
            console.log('ERROR: jqXHR, exception', jqXHR, exception);
        }
    });
    e.preventDefault();
}

$(document).ready(function () {

    setupEvents();

    //refresh content on history change
    window.addEventListener('popstate', (e) => {
        //console.log("location: " + document.location + ", state: " + JSON.stringify(e.state));
        updatePage(document.location);
    });

});