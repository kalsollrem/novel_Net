$(function (){

    //유저 정지 및 정지해제
    $(".mw_UserDelBtn").click(function()
    {
        let switchUD;
        if (this.id == 'mw_UserUp') { switchUD = 0;}
        else                        { switchUD = 1;}

        $.ajax({
            url:'/userStop.do',
            type:'post',
            data : {"u_num":$(this).val(),
                    "switchUD":switchUD},
            success:function(s){
                if (s == 1)    {console.log("수정성공")};
                if (s == 0)    {console.log("수정실패")};
                location.reload();
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });


    //신고삭제
    $(".mw_WarnningDelBtn").click(function()
    {
        $.ajax({
            url:'/warnningDel.do',
            type:'post',
            data : {"mw_num":$(this).val()},
            success:function(s){
                if (s == 1)    {console.log("수정성공")};
                if (s == 0)    {console.log("수정실패")};
                location.reload();
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });


})
