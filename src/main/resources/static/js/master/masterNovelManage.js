$(function (){


    //검색키
    $("#masterSearchbtn").click(function(){
        let keyword     = $('#searchSpace').val();
        let searchType  = $('#searchOtp').val();
        let sort        = $('#sortOtp').val();
        location.href   = '/master/novelManagement?searchType='+searchType+'&keyword='+keyword+'&sort='+sort+'&page=1';
    });

    //PD픽 메뉴 스위치
    let pdPickListSwitch = 0;
    $(".masterChoiceSwitch").click(function (){
        if(pdPickListSwitch == 0) {$(".masterChoice").show(); pdPickListSwitch =1;}
        else                      {$(".masterChoice").hide(); pdPickListSwitch =0;}
    })

    //소설 정지
    $(".mw_novelStopBtn").click(function()
    {
        let switchUD=1;
        $.ajax({
            url:'/masterNovelSwitch.do',
            type:'post',
            data : {"n_num":$(this).val(), "switchUD":switchUD},
            success:function(s) {location.reload();},
            error:function()    {alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");}
        });
    });


    //소설 정지 해제
    $(".mw_novelOnBtn").click(function()
    {
        let switchUD=0;
        $.ajax({
            url:'/masterNovelSwitch.do',
            type:'post',
            data : {"n_num":$(this).val(), "switchUD":switchUD},
            success:function(s) {location.reload();},
            error:function()    {alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");}
        });
    });



    //유저 정지
    $(".mw_UserDown").click(function()
    {
        let switchUD=1;
        $.ajax({
            url:'/userManage.do',
            type:'post',
            data : {"u_num":$(this).val(),"switchUD":switchUD},
            success:function(s){ location.reload(); },
            error:function(){ alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");}
        });
    });

    //유저 정지해제
    $(".mw_UserUp").click(function()
    {
        let switchUD=0;
        $.ajax({
            url:'/userManage.do',
            type:'post',
            data : {"u_num":$(this).val(), "switchUD":switchUD},
            success:function(s){location.reload(); },
            error:function(){alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다."); }
        });
    });

    //PD픽 선정
    $(".mw_PdPickBtn").click(function(){
        let switchUD=0;
        $.ajax({
            url:'/pdPickChoice.do', type:'post',
            data : {"n_num":$(this).val(), "switchUD":switchUD},
            success:function(s){location.reload(); },
            error:function(){alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다."); }
        });
    });

    //PD픽 삭제
    $(".pdDelBtn").click(function(){
        let switchUD=1;
        $.ajax({
            url:'/pdPickChoice.do', type:'post',
            data : {"n_num":$(this).val(), "switchUD":switchUD},
            success:function(s){location.reload(); },
            error:function(){alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다."); }
        });
    });


})
