
let r_rnum = 0;
let icon;

$(function(){
    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let n_num; try{n_num = url.get('n_num').toString();}    catch (e) {window.history.back();}
    let m_num; try{m_num = url.get('chapter').toString();}  catch (e) {window.history.back();}
    let sort;  try{sort = url.get('sort').toString();  }    catch (e) {sort   = "asc"}
    let page;  try{page = url.get('page').toString();  }    catch (e) {page   = "1"}
    let rbt;   try{rbt = url.get('rbt').toString();    }    catch (e) {rbt    = "none"}
    let MnN = n_num+"/"+m_num;

    //유저 권한
    let userOK; try{userOK = $('.user_num').val() }   catch (e) {userOK  = "none"}
    if(userOK == null || userOK == 'none')  {$('#reply_0').hide()}

    //추천 버튼 세션
    if(sessionStorage.getItem("G:"+MnN) == MnN){
        $(".memo_good").css("color","#FFA048");
        $('.good_img').attr('src','../img/good_Up.png');
    } else
    {
        $(".memo_good").css("color","#333333");
        $('.good_img').attr('src','../img/good_Down.png');
    }

    $(".memo_good").click(function(){
        if(userOK != null && userOK!= 'none')
        {
            var updown = "up";
            var Gss = sessionStorage.getItem("G:"+MnN);

            if(Gss == MnN) {
                console.log("추천을 취소합니다.");
                sessionStorage.removeItem("G:"+MnN);
                updown ="down";
                $(".memo_good").css("color","#333333");
                $('.good_img').attr('src','../img/good_Down.png');
            }
            else{
                sessionStorage.setItem("G:"+MnN, MnN);
                updown ="up";
                console.log("추천합니다.");
                $(".memo_good").css("color","#FFA048");
                $('.good_img').attr('src','../img/good_Up.png');
            }

            $.ajax({
                url:'/goodUpDown.do',
                type:'post',
                data : {"m_num":m_num,"updown":updown},
                success:function(s){
                    console.log(s);
                    $(".memo_good_text").text("("+s+"개)")
                },
                error:function(){}
            });
        }
    });

    //댓글버튼
    var memuSwitch = 0;

    //프로필에서 건너왔을경우
    if (rbt != 'none')
    {
        $(".viewer_memo").hide(); $(".reply_zone").show();
        $(".reply_button").css("color","#5B32DF");
        $(".replyB_img").attr("src", "../img/reply_on.png");
        sessionStorage.setItem("R_ZONE", MnN);
        memuSwitch=1;
        var offset = $("#focus_"+rbt).offset();
        $('html').animate({scrollTop : offset.top-200}, 400);
    }

    //댓글버튼 켜짐여부
    if (sessionStorage.getItem("R_ZONE") == MnN)
    {
        $(".viewer_memo").hide(); $(".reply_zone").show();
        $(".reply_button").css("color","#5B32DF");
        $(".replyB_img").attr("src", "../img/reply_on.png");
        memuSwitch = 1;
    }
    else
    {
        $(".reply_zone").hide();  $(".viewer_memo").show();
        $(".reply_button").css("color","#333333");
        $(".replyB_img").attr("src", "../img/reply_off.png");
        sessionStorage.removeItem("R_ZONE");
        memuSwitch=0;
    }

    //댓글버튼 클릭시
    $(".reply_button").click(function(){
        if(userOK != null && userOK!= 'none') {
            if (memuSwitch == 0) {
                $(".viewer_memo").hide();
                $(".reply_zone").show();
                $(".reply_button").css("color", "#5B32DF");
                $(".replyB_img").attr("src", "../img/reply_on.png");
                sessionStorage.setItem("R_ZONE", MnN);
                memuSwitch = 1;
            } else {
                $(".reply_zone").hide();
                $(".viewer_memo").show();
                $(".reply_button").css("color", "#333333");
                $(".replyB_img").attr("src", "../img/reply_off.png");
                sessionStorage.removeItem("R_ZONE");
                memuSwitch = 0;
            }
        }
    });

    //ReplyWrite
    $(".reply_commit").click(function(){
        if(userOK != null && userOK!= 'none')
        {
            let reply_memo = "";

            if (icon ==null || icon ==""){ reply_memo = $("#reply_area").val(); }
            else {reply_memo = icon + $("#reply_area").val();}

            $.ajax({
                url:'/replyWrite.do',
                type:'post',
                data : {"reply_memo":reply_memo,
                        "m_num":m_num,
                        "n_num":n_num,
                        "r_rnum":r_rnum},
                success:function(s){
                    console.log(s);
                    $("#reply_area").val('');
                    location.reload();
                },
                error:function(){
                    alert("댓글 등록에 실패하였습니다.");
                }
            });
        }
    });


    //목록버튼
    var listSwitch = 0;

    $(".chapter_selete").click(function(){
        if(listSwitch == 0) {
            $(".listText").stop().hide();
            $(".c_list").animate({left:200, opacity:"show"}, 250);
            listSwitch=1;
        }
        else
        {
            $(".listText").stop().show();
            $(".c_list").stop().hide();
            listSwitch=0;
        }
    });

    $(".c_list").change(function (){
        var listval = $("select[name=c_sel] option:selected").val();
        location.href = '/novelnet/view?n_num='+n_num+'&sort='+sort+'&page='+page+'&chapter='+listval;
    })

});


//댓글존 이동(Reply zone move)
let whereW = 0;

function answer_r(rum)
{
    if(userOK != null && userOK!= 'none')
    {
        r_rnum = rum;
        console.log(r_rnum);
        // $('#reply_0').empty();
        if (whereW != r_rnum)
        {
            $("#"+r_rnum).css("color", "red");
            $(".reply_write").css("margin-top", "10px");
            whereW = r_rnum;
            console.log("위치는"+whereW)
            $('#reply_'+r_rnum).after($('.reply_write'));
        }
        else
        {
            $("#"+r_rnum).css("color", "#333333");
            $(".reply_write").css("margin-top", "0px");
            whereW = 0;
            console.log("위치는"+whereW);
            $('#reply_0').after($('.reply_write'));
        }
    }
}


//이모티콘 창 관리
function imo_on(e){
    var imo = e;
    console.log(imo);

    if (icon == null || icon == ""){ $(".imoticon_zone").slideDown(); }

    $(".imoticon_zone").empty();
    $(".imoticon_zone").prepend('<img src=\"../imoticon/'+imo+'.jpg\" className=\"imo01\" alt=\"뿅\">')
    icon = '<img src=\"../imoticon/'+imo+'.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\">'+'<br>'
};

$(function (){
    $(".imoticon_delete>img").click(function (){
        icon = "";
        $(".imoticon_zone").empty();
        if (icon == null || icon == ""){ $(".imoticon_zone").slideUp(); }
    });
});


function r_warning(r){
    if(userOK != null && userOK!= 'none')
    {
        $.ajax({
            url:'/stopReply.do',
            type:'post',
            data : {"r_num":r.substring(3)},
            success:function(s){
                if (s == 0)     {alert("신고에 실패하였습니다.")}
                else if (s == 1){alert("이미 신고하셨습니다")}
                else            {alert("신고되었습니다.")}
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    }
};

function updownlike(r){
    if(userOK != null && userOK!= 'none')
    {
        var cheak = 1;
        var word = r.split('_');
        const regex = /[^0-9]/g;

        var type = word[0]
        var r_num = word[1]

        var text;
        var result;

        //세션을 통한 중복 추천 불가
        if(sessionStorage.getItem("Replygood"+r)!=null && type=="good")  {  cheak =0;  }
        if(sessionStorage.getItem("Replybad"+r)!=null && type=="bad")    {  cheak =0;  }

        if(cheak == 1){
            $.ajax({
                url:'/updownlike.do',
                type:'post',
                data : {"r_num":r_num,
                        "type":type
                },
                success:function(s){
                    if (s == 1 && type == "good") {
                        text = $(".goodcho_"+r_num).text();
                        result = parseInt(text.replace(regex,""))+1;
                        $(".goodcho_"+r_num).text("추천("+result+")");
                        alert("추천되었습니다")
                        sessionStorage.setItem("Replygood"+r, "yes");
                    }
                    else if(s == 1 && type == "bad") {
                        text = $(".badcho_"+r_num).text();
                        result = parseInt(text.replace(regex,""))+1;
                        $(".badcho_"+r_num).text("비추천("+result+")");
                        alert("비추되었습니다")
                        sessionStorage.setItem("Replybad"+r, "yes");
                    }
                    else {alert("DB와 통신에 실패하였습니다.")}
                },
                error:function(){
                    alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
                }
            });
        }else
        {
            if(type == "bad"){  alert("이미 비추천 하셨습니다.")  };
            if(type == "good"){  alert("이미 추천 하셨습니다.")  };
        }
    }

}
