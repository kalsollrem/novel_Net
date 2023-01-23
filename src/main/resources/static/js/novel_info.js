$(function (){

    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let n_num   = url.get('n_num').toString();

    //ReplyWrite
    $(".book_mark_zone>img").click(function()
    {
        var book_switch = $(".bookmark_switch").val();

        $.ajax({
            url:'/bookmark.do',
            type:'post',
            data : {"n_num":n_num,
                    "book_switch":book_switch},
            success:function(s){
                if (s == 'on'){
                    $(".book_mark_zone").removeClass('border_gray');
                    $(".book_mark_zone").addClass('border_pink');
                    $('.book_mark_zone>img').attr('src','../img/Bookmark_on.png');
                    $(".bookmark_switch").val("1")
                    alert("북마크 되었습니다.")
                }
                else if(s =='off'){
                    $(".book_mark_zone").removeClass('border_pink');
                    $(".book_mark_zone").addClass('border_gray');
                    $('.book_mark_zone>img').attr('src','../img/Bookmark_off.png');
                    $(".bookmark_switch").val("0")
                    alert("북마크를 삭제하였습니다.")
                }else
                {
                    alert("로그인 상태를 확인해주세요.")
                }
            },
            error:function(){
                alert("북마크에 실패하였습니다.");
            }
        });
    });

    $(".write_mark_zone").click(function (){
        location.href = '';
    });
})