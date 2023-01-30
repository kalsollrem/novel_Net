$(function (){

    let profill     =  $('.profile_main')
    let info =  $('.serialized_info')
    let novel=  $('.serialized_novel')
    let reply=  $('.serialized_reply')

    //프로필 on
    $('.btn_profill').click(function ()       {profill.show(); info.hide(); novel.hide(); reply.hide();})
    //정보관리 on
    $('.btn_profillRewrite').click(function (){profill.hide(); info.show(); novel.hide(); reply.hide();})
    //연재소설 on
    $('.btn_writeNovel').click(function ()    {profill.hide(); info.hide(); novel.show(); reply.hide();})
    //댓글 on
    $('.btn_myReply').click(function ()       {profill.hide(); info.hide(); novel.hide(); reply.show();})



    $('.change_ok').click(function ()
    {

        let passC        = $("#passwordChange").val();
        let passCheakC   = $(".passcheck").val();
        let nickC        = $("#nickChange").val();
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