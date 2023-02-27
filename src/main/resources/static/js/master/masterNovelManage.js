$(function (){


    //검색키
    $("#masterSearchbtn").click(function(){
        let keyword     = $('#searchSpace').val();
        let searchType  = $('#searchOtp').val();
        let sort        = $('#sortOtp').val();
        location.href   = '/master/novelManagement?searchType='+searchType+'&keyword='+keyword+'&sort='+sort+'&page=1';
    });

    let pdPickListSwitch = 0;
    let bnPickListSwitch = 0;

    //PD픽 메뉴 스위치
    $(".masterChoiceSwitch").click(function (){
        if(pdPickListSwitch == 0) {$(".masterChoice").show(); pdPickListSwitch =1; $(".masterBanner").hide(); bnPickListSwitch =0;}
        else                      {$(".masterChoice").hide(); pdPickListSwitch =0;}
    })

    //배너픽 메뉴 스위치
    $(".masterBannerSwitch").click(function (){
        if(bnPickListSwitch == 0) {$(".masterBanner").show(); bnPickListSwitch =1; $(".masterChoice").hide(); pdPickListSwitch =0;}
        else                      {$(".masterBanner").hide(); bnPickListSwitch =0;}
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

    $('.mw_bannerPickBtn').click(function (){
        let value = $(this).val();
        let url = "/master/novelManagement/banner?num="+value;
        let name = "popup";
        let option = "width = 1240, height = 280, location = no, status= no, toolbars= no, _blank"
        window.open(url, name, option);
    })

    //PD픽 삭제
    $(".bnDelBtn").click(function(){
        $.ajax({
            url:'/BannerDelete.do', type:'post',
            data : {"n_num":$(this).val()},
            success:function(s){location.reload(); },
            error:function(){alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다."); }
        });
    });
})
