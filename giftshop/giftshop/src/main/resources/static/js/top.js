$(document).ready(function () {
    
    //top 메뉴바
    $(".menu").after("<div class='bg'></div>");
    $(".sub:not(:first)").after("<span class='ln'></span>");
    $(".bg,.ln,.sub").hide();
    $(".menu").mouseenter(function(){
        $(".bg,.ln,.sub").stop().slideDown("fast");
    }).mouseleave(function(){
        $(".bg,.ln,.sub").stop().slideUp();
    });

    $(".bg").mouseover(function(){
        $(this).stop().show();
        $(".sub").stop().show();
    }).mouseout(function(){
        $(this).stop().slideUp();
        $(".sub").stop().slideUp();
    }); 
});