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


    //폼체크
    let nick;      let pass;      let passCheak;  let intro;
    let passN = 0; let passC = 0; let passD = 0;

    $('.change_ok').click(function ()
    {
        let reg = /\s/g; //공백판별

        nick        = $("#nickChange").val();
        if (nick.match(reg)){ alert('공백은 사용하실 수 없습니다.') }
        if (nick.length != 0){passN = 1}

        pass        = $("#passwordChange").val();
        if (pass.length < 12)       { passC = 0; alert('비밀번호가 너무 짧습니다.');}
        else if (pass.match(reg))   { passC = 0; alert('비밀번호의 공백을 제거해주세요');}
        else                        { passC = 1;}

        passCheak   = $(".passcheck").val();
        if (passCheak.match(reg))        { passD = 0; alert('비밀번호 확인에 공백을 제거해주세요');}
        else if (passCheak != pass)      { passD = 0; alert('비밀번호와 일치하지 않습니다');}
        else                             { passD = 1}

        intro   = $("#intro").val();

        if(passN == 1 && passC==1 && passD==1){
            $.ajax({
                url:'/profillUpdate.do',
                type:'post',
                data : {"nick":nick,
                        "pass":pass,
                        "intro":intro},
                success:function(s){
                    if (s == 1) { location.reload(); }
                    else        { alert('갱신에 실패하였습니다.'); }
                },
                error:function(){
                    alert("수정에 실패하였습니다.");
                }
            });
        }

    })

    $('.pic_edit').click(function (){
        var url = "/novelnet/profill/myImg";
        var name = "popup";
        var option = "width = 535, height = 850, location = no, status= no, toolbars= no, _blank"
        window.open(url, name, option);    })
});