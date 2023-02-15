$(function (){

    let profill     =  $('.profile_main')
    let info =  $('.serialized_info')
    let novel=  $('.serialized_novel_switch')
    let reply=  $('.serialized_reply')

    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);

    //유저값
    let user;
    try       {user   = url.get('user').toString();}
    catch (e) {location.href='/novelnet'}

    //메뉴 카테고리
    let type;
    try       {type   = url.get('type').toString();}
    catch (e) {type  = 'card'}

    //페이징(글)
    let page;
    try       {page   = url.get('page').toString();}
    catch (e) {page  = 1}
    $("#cardNum_"+page+">a").css('font-weight', 'bold');
    $("#cardNum_"+page+">a").css('color', 'red');

    //페이징(댓글)
    let pageR;
    try       {pageR   = url.get('pageR').toString();}
    catch (e) {pageR  = 1}
    $("#cardNumR_"+pageR+">a").css('font-weight', 'bold');
    $("#cardNumR_"+pageR+">a").css('color', 'red');

    //현재주소 명칭
    var renewURL = location.pathname ;

    //type값에 따른 메뉴 오픈
    if (type == 'reply')     {profill.hide(); info.hide(); novel.hide(); reply.show(); }
    else if (type =='date')  {profill.hide(); info.show(); novel.hide(); reply.hide(); }
    else if (type =='novels'){profill.hide(); info.hide(); novel.show(); reply.hide(); }
    else                     {profill.show(); info.hide(); novel.hide(); reply.hide(); }

    //프로필 on
    $('.btn_profill').click(function ()       {profill.show(); info.hide(); novel.hide(); reply.hide(); history.pushState(null,null, renewURL+'?user='+user+'&type=card');})
    //정보관리 on
    $('.btn_profillRewrite').click(function (){profill.hide(); info.show(); novel.hide(); reply.hide(); history.pushState(null,null, renewURL+'?user='+user+'&type=date');})
    //연재소설 on
    $('.btn_writeNovel').click(function ()    {profill.hide(); info.hide(); novel.show(); reply.hide(); history.pushState(null,null, renewURL+'?user='+user+'&type=novels');})
    //댓글 on
    $('.btn_myReply').click(function ()       {profill.hide(); info.hide(); novel.hide(); reply.show(); history.pushState(null,null, renewURL+'?user='+user+'&type=reply');})

    //댓글 삭제
    $('.myReply_delete').click(function ()
    {
        let id = (this.id).substr(3);

        $.ajax({
            url:'/replyDelete.do',
            type:'post',
            data : {"r_num":id},
            success:function(s){
                if (s == 'ok') { alert('댓글이 삭제되었습니다.'); }
                else           { alert('댓글 삭제에 실패하였습니다.'); }

                location.reload();
            },
            error:function(){
                alert("댓글 삭제에 실패하였습니다.");
            }
        });
    });


    $('.change_ok').click(function ()
    {

        let passC        = $("#passwordChange").val();  //비밀번호
        let passCheakC   = $(".passcheck").val();       //비밀번호 확인
        let nickC        = $("#nickChange").val();      //닉네임.

        //텍스트 에어리어 엔터 <br>로 변환
        let intro        = $('#intro').val();
        intro = intro.replace(/(?:\r\n|\r|\n)/g, '<br>');


        var message = '';
        const reg = /\s/g; //공백판별
        var conformNick  = 0;
        var conformPass = 0;

        //비밀번호 확인
        if (passC.length != 0)
        {
            if (passC.length < 12)
            {
                message = '비밀번호가 너무 짧습니다.';
                conformPass = 0;
            }
            else if (passC.length > 20)
            {
                message = '비밀번호가 너무 깁니다.'
                conformPass = 0;
            }
            else if (passC.match(reg))
            {
                message = '공백을 제거해주세요.'
                conformPass = 0;
            }
            else if (passC != passCheakC)
            {
                message = '비밀번호를 다시 확인해주세요'
                conformPass = 0;
            }
        }
        else
        {
            conformPass = 1;
        }

        //닉네임확인
        if(nickC != "")
        {
            if(nickC.match(reg))
            {
                message = '공백을 제거해주세요.'
                conformNick = 0;
            }
        }
        else
        {
            conformNick = 1;
        }
        alert(message)
    })

});