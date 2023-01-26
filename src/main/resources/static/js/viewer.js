$(function(){
    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let n_num;
    let chapter;
    let sort;
    let page;

    try         { n_num = url.get('n_num').toString(); }
    catch (err) { n_num = "";    }

    try         { chapter = url.get('chapter').toString(); }
    catch (err) { chapter = "";    }

    try         { sort = url.get('sort').toString(); }
    catch (err) { sort = "date";    }

    try         { page = url.get('page').toString(); }
    catch (err) { page = "1";    }

    //메모옵션박스
    var memo_OptBox = $('.memo_OptBox');
    memo_OptBox.css("position", "absolute");
    memo_OptBox.css("top", Math.max(0, (($(window).height() - memo_OptBox.outerHeight()) / 2) + $(window).scrollTop()) + "px");
    memo_OptBox.css("left", Math.max(0, (($(window).width() - memo_OptBox.outerWidth()) / 2) + $(window).scrollLeft()) + "px");

    // 글 수정&삭제창
    $(".update_btn").click(function(){
        $('.memo_option').fadeIn(500);
        $('.optTypeA').fadeIn(500);
    });

    //다른영역 클릭시 창닫기.
    $(document).mouseup(function (e){
        if($(".memo_option").has(e.target).length === 0){
            $(".memo_option").hide();
            $(".optTypeA").hide();
            $(".optTypeB").hide();
            $(".optTypeC").hide();
        }
    });

    // 글수정
    $(".memo_rewrite").click(function(){
        location.href = "/novelnet/reWrite?n_num="+n_num+"&chapter="+chapter;
    });

    // 신고
    $(".nebiOpt>img").click(function(){
        $('.memo_option').fadeIn(500);
        $('.optTypeC').fadeIn(500);
        $('.shingoBefore').fadeIn(500);
        $('.shingoAfter').hide();
    });

    // 신고버튼
    $(".singoBtn").click(function(){
        let w_why = $(".whySingo").val()
        $.ajax({
            url:'/memoWarning.do',
            type:'post',
            data : {"chapter":chapter,
                    "w_why":w_why,
                    "n_num":n_num},
            success:function(s){
                if      (s == 0){$(".singodone>p").text("신고에 실패하였습니다")}
                else if (s == 1){$(".singodone>p").text("신고되었습니다")      }
                else            {$(".singodone>p").text("이미 신고하신 소설입니다")}
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
        $('.shingoBefore').hide();
        $('.shingoAfter').show();
    });


    // 신고완료
    $(".singodoneBtn").click(function(){
        $('.memo_option').hide();
        $('.shingoAfter').hide();
        $('.shingoBefore').hide();
    });


    // 글삭제
    $(".memo_delete").click(function(){
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
            data : {"chapter":chapter,
                    "n_num":n_num},
            success:function(s){
                if (s == 0)     {alert("삭제 권한이 없습니다.")}
                else if (s == 1){alert("삭제되었습니다")
                                 location.href = "/novelnet/novel?n_num="+n_num+"&sort="+sort+"&page="+page
                                }
                else            {alert("삭제에 실패하였습니다.")}
                $('.memo_option').hide();
                $('.optTypeB').hide();
                $('.optTypeA').show();
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });

})



