$(function(){

    //메모옵션박스
    var memo_OptBox = $('.memo_OptBox');
    memo_OptBox.css("position", "absolute");
    memo_OptBox.css("top", Math.max(0, (($(window).height() - login_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
    memo_OptBox.css("left", Math.max(0, (($(window).width() - login_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");

    // 글 수정&삭제창
    $(".write_update_btn>img").click(function(){
        $('.memo_option').fadeIn(500);
    });

    // 글삭제
    $(".memo_delete>img").click(function(){
        $('.optTypeA').hide();
        $('.optTypeB').show();
    });

    // 글삭제
    // 거절
    $(".DeleteNoBtn").click(function(){
        $('.optTypeB').hide();
        $('.optTypeA').show();
    });
    //수락
    $(".DeleteYesBtn").click(function(){
        $.ajax({
            url:'/memoDelete.do',
            type:'post',
            data : {"chapter":chapter},
            success:function(s){
                if (s == 0)     {alert("삭제 권한이 없습니다.")}
                else if (s == 1){alert("삭제되었습니다")}
                else            {alert("삭제에 실패하였습니다.")}
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });

})



