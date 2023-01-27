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


});