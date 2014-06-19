// $('.thumbnail').click(function() {
//    $('.modal-body').empty();
//    var title = $(this).parent('a').attr("title");
//    $('.modal-title').html(title);
//    $($(this).parents('div').html()).appendTo('.modal-body');
//    $('#myModal').modal({
//        show: true
//    });
// });

// // jQuery for page scrolling (to bottom of section) feature - requires jQuery Easing plugin
// $(function() {
//    $('.page-scroll-b a').bind('click', function(event) {
//        var $anchor = $(this);
//        var $section = $($anchor.attr('href'));
//        var scrollPos = $section.offset().top + $section.outerHeight() - $(window).height();

//        $('html, body').stop().animate({
//            scrollTop: scrollPos
//        }, 1500, 'easeInOutExpo');

//        event.preventDefault();
//    });
// });

// // jQuery to collapse the navbar on scroll
// $(window).scroll(function() {
//    if ($(".navbar").offset().top > 50) {
//        $(".navbar-fixed-top").addClass("top-nav-collapse");
//    } else {
//        $(".navbar-fixed-top").removeClass("top-nav-collapse");
//    }
// });

// //jQuery for page scrolling feature - requires jQuery Easing plugin
// $(function() {
//    $('.page-scroll a').bind('click', function(event) {
//        var $anchor = $(this);
//        $('html, body').stop().animate({
//            scrollTop: $($anchor.attr('href')).offset().top
//        }, 1500, 'easeInOutExpo');
//        event.preventDefault();
//    });
// });