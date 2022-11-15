function section_0(eval) {
    if (!$(eval).hasClass("show")) {
        $(eval).addClass("show");
        $(eval).animate({
            opacity: "1"
        }, 1000);
    }
    return false;
}

function section_1(eval) {
    //핵심제품 로딩 시
    if (!$(eval).hasClass("show")) {
        $(eval).addClass("show");
        $(eval).animate({
            opacity: "1"
        }, 2000).delay(2000);

        $(".contents .con:nth-child(2n)").css({
            top: "-330px",
            opacity: "0"
        });
        $(".contents .con:nth-child(2n)")
            .animate({
                top: "0px",
                opacity: "1"
            }, 3000, "easeOutQuart");
        $(".contents .con:nth-child(2n+1)").css({
            top: "330px",
            opacity: "0"
        });
        $(".contents .con:nth-child(2n+1)")
            .animate({
                top: "0px",
                opacity: "1"
            }, 3000, "easeOutQuart");
    }
    return false;

}

function section_2(eval) {
    if (!$(eval).hasClass("show")) {
        $(eval).addClass("show");
        $(eval).animate({
            opacity: "1"
        }, 2000).delay(2000);
    }
    return false;
}

function section_3(eval) {
    if (!$(eval).hasClass("show")) {
        $(eval).addClass("show");
        $(eval).animate({
            opacity: "1"
        }, 2000).delay(2000);

        //슬로건
        //Tea & Health Creator(왼쪽에서 오른쪽으로)
        $(".slogan_txt p:nth-of-type(1)").css({
            opacity: "0"
        });
        $(".slogan_txt p:nth-of-type(1)")
            .delay(1000)
            .animate({
                opacity: "1"
            }, 2000, "linear");

        //Tea & Health Creator(위쪽 선)
        $(".slogan_txt p:nth-of-type(1)").prepend("<span class='upper'></span>");
        $(".upper")
            .delay(1000)
            .animate({
                width: "100%",
                opacity: "1"
            }, 2000, "easeOutQuart");
        //Tea & Health Creator(아래쪽 선)
        $(".slogan_txt p:nth-of-type(1)").append("<span class='under'></span>");
        $(".under")
            .delay(1000)
            .animate({
                width: "100%",
                opacity: "1"
            }, 2000, "easeOutQuart");

        //차와 건강한 삶(한글자씩 위에서 아래로)
        $(".slogan_txt p:nth-of-type(2)").css({
            opacity: "0"
        });
        $(".slogan_txt p:nth-of-type(2)")
            .animate({
                opacity: "1"
            }, 4000, "linear");
        $(".slogan_txt p:nth-of-type(2) span").css({
            position: "relative",
            top: "-80px",
            opacity: "0"
        });
        for (var i = 0; i < 8; i++) {
            $(".slogan_txt p:nth-of-type(2) span").eq(i)
                .delay(1000 + 200 * i)
                .animate({
                    top: "-2px",
                    opacity: "1"
                }, 1000, "easeOutQuart");
        }
        //텍스트
        $(".slogan_txt p:nth-of-type(3),.slogan_txt p:nth-of-type(4)")
            .css({
                opacity: "0"
            });
        $(".slogan_txt p:nth-of-type(3)")
            .delay(500)
            .animate({
                opacity: "1"
            }, 1500, "linear");
        $(".slogan_txt p:nth-of-type(4)")
            .delay(1000)
            .animate({
                opacity: "1"
            }, 1000, "linear");

        //link
        $(".link").css({
            opacity: "0"
        });
        $(".link")
            .animate({
                opacity: "1"
            }, 4000, "linear");
        $(".link li").css({
            position: "relative",
            top: "130px",
            opacity: "0"
        });
        for (var i = 0; i < 4; i++) {
            $(".link li").eq(i)
                .delay(500 + 200 * i)
                .animate({
                    top: "0px",
                    opacity: "1"
                }, 1500, "easeOutQuart");
        }

        //이미지
        $(".slogan_img").hide();
        $(".slogan_img").fadeIn(3000);
        $(".slogan_txt p:last-child").hide();
        $(".slogan_txt p:last-child").fadeIn(3000);
    }
    return false;
}

function section_4(eval) {
    if (!$(eval).hasClass("show")) {
        $(eval).addClass("show");
        $(eval).animate({
            opacity: "1"
        }, 2000).delay(2000);
    }
    return false;
}

$(document).ready(function () {
    
    //top 메뉴바
//    $(".menu").after("<div class='bg'></div>");
//    $(".sub:not(:first)").after("<span class='ln'></span>");
//    $(".bg,.ln,.sub").hide();
//    $(".menu").mouseenter(function(){
//        $(".bg,.ln,.sub").stop().slideDown("fast");
//    }).mouseleave(function(){
//        $(".bg,.ln,.sub").stop().slideUp();
//    });
//
//    $(".bg").mouseover(function(){
//        $(this).stop().show();
//        $(".sub").stop().show();
//    }).mouseout(function(){
//        $(this).stop().slideUp();
//        $(".sub").stop().slideUp();
//    });
    
    
    //스크롤 위치에 따라 요소 보이게
    $(".section").not('.show').css({
        opacity: "0"
    });
        
    function showSection() {
        var top = $(document).scrollTop();
        var winH = window.innerHeight;

        for (var i = 0; i < items.length; i++) {
            var posFromTop = items[i].getBoundingClientRect().top;
            if (winH * (6/7) > posFromTop) {
                console.log("dd" + items[1].getBoundingClientRect().top);
                console.log(winH);
                var funcStr = "section_" + i + "(items[" + i + "])";
                eval(funcStr);
            }
        }
    }
    var items = document.querySelectorAll(".section");
    showSection();
    $(window).scroll(function(){
        showSection();
    });
    
    
    
    //핫이슈 상품전 - 선택
    $(".product_tab li:first-child a").addClass("selected");
    $(".product_tab li a").click(function () {
        $(".product_tab li a").removeClass("selected");
        $(this).addClass("selected");
        return false;
    });

    $(".q_top").click(function () {
        $("html, body").animate({
            scrollTop: 0
        }, 1000);
        return false;
    });
    $(".q_bottom").click(function () {
        $("html, body").animate({
            scrollTop: $(document).height()
        }, 1000);
        return false;
    });

    $("#chk_same").change(function(){
        let chk = $("#chk_same").prop("checked");
        if(chk == true){
            $(".chk_box").css({
                border:"2px solid #4c7651",
                background:"url(../img/check_i_on.png) no-repeat center center",
                backgroundSize:"12px 12px"
            });
        }else{
            $(".chk_box").css({
                border:"2px solid #aaaaaa",
                background:"url(../img/check_i_off.png) no-repeat center center",
                backgroundSize:"12px 12px"
            });

        }
    });



});
