$(function (){

    //이메일로보기 & 닉네임으로보기 전환
    $(".typeNick").click(function(){ $(".typeNick").hide(); $(".typeMail").show(); });
    $(".typeMail").click(function(){ $(".typeMail").hide(); $(".typeNick").show(); });


    //검색키
    $("#masterSearchbtn").click(function(){
        let keyword     = $('.searchSpace').val();
        let searchType  = $('#searchOtp').val();
        let sort        = $('#sortOtp').val();
        location.href   = '/master/userManagement?searchType='+searchType+'&keyword='+keyword+'&sort='+sort+'&page=1';
    });



    //유저 삭제
    $(".mw_UserOutBtn").click(function()
    {
        $.ajax({
            url:'/userOut.do',
            type:'post',
            data : {"u_num":$(this).val()},
            success:function(s){
                if (s == 1)    {location.reload();};
                if (s == 0)    {alert("삭제에 실패하였습니다.")};
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });



    //유저 정지 및 정지해제
    $(".mw_UserDelBtn").click(function()
    {
        let switchUD;
        if (this.id == 'mw_UserUp') { switchUD = 0;}
        else                        { switchUD = 1;}

        $.ajax({
            url:'/userManage.do',
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

    //유저 정지 및 정지해제
    $(".levbtn").click(function()
    {
        let u_num = $(this).val()
        let switchUD = $(".levelChange_"+u_num).val();

        $.ajax({
            url:'/userManage.do',
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


})
