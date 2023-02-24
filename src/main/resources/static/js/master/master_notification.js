$(function (){

    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    //페이징(글)
    let page;
    try       {page   = url.get('page').toString();}
    catch (e) {page  = 1}

    let carte;
    try       {carte   = url.get('carte').toString();}
    catch (e) {carte  = 'gong'}


    //신고삭제
    $(".delete_gongji").click(function()
    {
        $.ajax({
            url:'/masterMemoDelete.do',
            type:'post',
            data : {"No":$(this).val()},
            success:function(s){
                if (s == 1)    {location.href="/master/notification?carte="+carte+"&page="+page};
                if (s == 0)    {alert("삭제에 실패하였습니다")};
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });

})
