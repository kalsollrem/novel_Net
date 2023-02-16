$(function(){
    
    //배너(Banner)
    var slide_index = 0;
    setInterval(function(){
  
        if(slide_index<2){slide_index++;}
        else{slide_index=0;}
  
        $(".slide-cont").animate({top:(slide_index*-280)},500)    
        $(".b-slide-cont").animate({top:(slide_index*-280)},500)    
  
    },3000);
  
    //회원 가입 모달(resist window)
    var sign_box = $('.sign_box');
    sign_box.css("position", "absolute");
    sign_box.css("top", Math.max(0, (($(window).height() - sign_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
    sign_box.css("left", Math.max(0, (($(window).width() - sign_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    $('.sign_box').fadeIn(500);

    //로그인 모달(Login window)
    var login_box = $('.login_box');
    login_box.css("position", "absolute");
    login_box.css("top", Math.max(0, (($(window).height() - login_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
    login_box.css("left", Math.max(0, (($(window).width() - login_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    $('.login_box').fadeIn(500);
    
    //비밀번호 검색모달(passFinder window)
    var passfinder = $('.passfinder_box');
    passfinder.css("position", "absolute");
    passfinder.css("top", Math.max(0, (($(window).height() - login_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
    passfinder.css("left", Math.max(0, (($(window).width() - login_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    $('.passfinder_box').fadeIn(500);
  
    //우측 상단메뉴(Right menu)
    var memuSwitch = 0;
    $(".info_front>li>span>a").click(function(){
         if(memuSwitch == 0) { $(".info_menu").fadeIn();  memuSwitch=1;}
         else                { $(".info_menu").fadeOut(); memuSwitch=0;}
    });

    //우측상단 메뉴 닫기
    $(document).mouseup(function (e){
        if($(".info_menu").has(e.target).length === 0){
            $(".info_menu").hide();
            memuSwitch = 0;
        }
    });

    //글쓰기 버튼
    $(".writePageGo").click(function()   {
        if($(".login_session_cheaker").val() == 'noLogin')
        {
            $(".login").fadeIn();
        }else
        {
            location.href='/novelnet/goMyProfill';
        }
    })

    //검색페이지 버튼
    $('#searchPage_btn').click(function (){location.href='/novelnet/search'});

    //가입 모달 버튼(Join modal)
    $("#join_on").click(function()       { $(".sign").fadeIn(); })
    $(document).mouseup(function (e){
        if($(".sign").has(e.target).length === 0){
            $(".sign").hide();
        }
    });
    $(".login_change").click(function()  { $(".sign").fadeOut();  $(".login").fadeIn();});
    $(".password_find").click(function() { $(".sign").fadeOut();  $(".passfinder").fadeIn();});

    //회원가입 모달 버튼(Sign modal)
    $("#login_on").click(function()      { $(".login").fadeIn(); });
    $(document).mouseup(function (e){
        if($(".login").has(e.target).length === 0){
            $(".login").hide();
        }
    });
    $(".sign_find").click(function()     { $(".login").fadeOut(); $(".sign").fadeIn();});
    $(".password_find").click(function() { $(".login").fadeOut(); $(".passfinder").fadeIn();});

    //비밀번호 찾기 모달 버튼(Sign modal)
    $("#pass_find_on").click(function()      { $(".passfinder").fadeIn(); });
    $(document).mouseup(function (e){
        if($(".passfinder").has(e.target).length === 0){
            $(".passfinder").hide();
        }
    });
    $(".sign_find").click(function()     { $(".passfinder").fadeOut(); $(".sign").fadeIn();});
    $(".login_change").click(function()  { $(".passfinder").fadeOut(); $(".login").fadeIn();});

    //태그검색(tag search window)
    var tag_search = $('.tag_search');
    var switch_search = 0;
  
    $(tag_search).click(
      function(){
          $(".hashtag_finder").show();
          switch_search = 1;
      });
    
    $(".wrap").mouseup(
      function(){
        var LayerPopup = $(".hashtag_finder");
        if(switch_search == 1)
        {
          $(".hashtag_finder").hide();
        }
    });



    //--------------------------------------------폼체크--------------------------------------------------//
    var id_OK = 0;
    var pass_OK = 0;
    var pass_cheak_OK = 0;
    const az = /^[0-9]+$/;
    const reg = /\s/g;

    //폼체크 - 아이디 중복검사.
    $("#e-mail").keyup(function(){
        var id = $('#e-mail').val();

        if(id.includes("@"))
        {
            $.ajax({
                url:'/idCheck',
                type:'post',
                data : {"id":id},
                success:function(cnt){
                    if(cnt == 0)
                    {
                        $(".id_answer").text("* 사용가능한 아이디 입니다.")
                        $(".id_answer").css("color", "red")
                        id_OK = 1;
                    }
                    else
                    {
                        $(".id_answer").text("* 사용 불가능한 아이디 입니다.")
                        $(".id_answer").css("color", "blue")
                        id_OK = 0;
                    }
                },
                error:function(){
                    $(".id_answer").text(id);
                    id_OK = 0;
                }
            });
        }
        else if(id.length == 0)
        {
            $(".id_answer").text("")
            id_OK = 0;
        }
        else
        {
            $(".id_answer").text("* 이메일이 아닙니다.")
            $(".id_answer").css("color", "blue")
            id_OK = 0;
        }
    });

    //폼체크 - 비밀번호 입력확인
    $("#password").keyup(function(){
        const pass  = $('#password').val();

        if(0 < pass.length && pass.length <= 12)
        {
            $(".pass_length").text("* 비밀번호가 짧습니다")
            $(".pass_length").css("color", "black")
            pass_OK = 0;
        }
        else if(pass.length == 0)
        {
            $(".pass_length").text(" ")
            pass_OK = 0;
        }
        else
        {
            if(az.test(pass))
            {
                $(".pass_length").text("* 보안을 위해 영문이나 한글을 포함해주세요")
                $(".pass_length").css("color", "blue")
                pass_OK = 0;
            }
            else if(pass.match(reg)){
                $(".pass_length").text("* 공백을 제거해주세요")
                $(".pass_length").css("color", "black")
                pass_OK = 0;
            }
            else
            {
                $(".pass_length").text("* 사용 가능한 비밀번호입니다.")
                $(".pass_length").css("color", "black")
                pass_OK = 1;
            }
        }
    })

    //폼체크 - 비밀번호 체크 확인
    $("#password_cheak").keyup(function(){
        const pass       = $('#password').val()
        const pass_cheak = $('#password_cheak').val()

        if(pass == pass_cheak && pass_cheak.length > 0)
        {
            $(".pass_answer").text("* 입력한 비밀번호와 같습니다.")
            $(".pass_answer").css("color", "red")
            pass_cheak_OK = 1;
        }
        else if(pass_cheak.length == 0)
        {
            $(".pass_answer").text("")
            pass_cheak_OK = 0;
        }
        else
        {
            $(".pass_answer").text("* 입력한 비밀번호와 다릅니다.")
            $(".pass_answer").css("color", "blue")
            pass_cheak_OK = 0;
        }
    })

    //폼체크 - 전체확인
    $("#sign_yes").click(function (){
        const joinform      = $('#joinForm')
        const nick_OK       = $('#nick_name').val();
        const contract_ok   = $('#contract_ok').is(':checked');;
        const age_ok        = $('#age_ok').is(':checked');

        if(id_OK == 0)
        {
            $('#e-mail').focus();
            alert("이메일을 확인해주세요.");
            return false
        }
        else if(pass_OK == 0)
        {
            $('#password').focus();
            alert("비밀번호를 확인해주세요.");
            return false
        }
        else if(pass_cheak_OK == 0)
        {
            $('#password_cheak').focus();
            alert("비밀번호가 일치하는지 확인해주세요.");
            return false
        }
        else if(nick_OK == "" || nick_OK == null)
        {
            $('#nick_name').focus();
            alert("닉네임을 입력해주세요");
            return false
        }
        else if(nick_OK.match(reg))
        {
            $('#nick_name').focus();
            alert("공백을 제거해주세요");
            return false
        }
        else if(contract_ok == false )
        {
            alert("약관에 동의해주세요");
            return false
        }
        else if(age_ok == false)
        {
            alert("만 14세 이상이 아닙니다.");
            return false
        }
        else
        {
            joinform.submit();
        }
    })

    //로그인 폼체크
    $(".login_button").click(function (){
        // const loginform = $('#loginform')
        const id        = $('#login_id').val()
        const pw        = $('#login_password').val();

        if(id == "" || id == null)
        {
            $('#login_id').focus();
            alert("아이디를 입력해주세요");
            return false
        }
        else if(pw == "" || pw == null)
        {
            $('#login_password').focus();
            alert("비밀번호를 확인해주세요.");
            return false
        }
        else
        {
            $.ajax({
                url:'/login.do',
                type:'post',
                data : {"u_mail":id,
                        "u_pass":pw
                },
                success:function(s){
                    if (s == "loginFail")
                    {
                        alert("로그인에 실패하였습니다. 메일과 비밀번호를 다시 확인해주세요.");
                    }
                    else if (s == "noMail")
                    {
                        alert("로그인에 실패하였습니다. 메일과 비밀번호를 다시 확인해주세요.");
                    }
                    else
                    {
                        location.reload();
                    }
                },
                error:function(){
                    alert("로그인에 실패하였습니다. 정보를 다시 확인해주세요.");
                }
            });
        }
    })

    $('.mailbox').on('change', function (){
        console.log(this.value);
        if(this.value == 'self')
        {
            $('.sizeOptB').show();
        }
        else
        {
            $('.selfaddr').val('');
            $('.sizeOptB').hide();
        }
    })

    $('.findbtn').click(function (){
        let mailname = $('.mailname').val()
        let mailaddr = $('.mailbox').val()
        let selfaddr = $('.selfaddr').val()
        if(mailname == "" || mailname == null)
        {
            $('.mailname').focus();
            $('.passfinder_answer').text('e-mail을 입력해 주세요')
            return false
        }
        else if(mailaddr == 'none')
        {
            $('.passfinder_answer').text('주소를 선택해주세요')

            alert("주소를 선택해주세요");
            return false
        }
        else if(mailaddr == 'self' && (selfaddr == '' || selfaddr == null))
        {
            $('.selfaddr').focus();
            $('.passfinder_answer').text('주소를 입력해주세요')
            return false
        }
        else {
            var findid;
            if (mailaddr == 'self')
            {
                findid = mailname + '@'+selfaddr;
            }
            else
            {
                findid = mailname + '@'+mailaddr;
            }
            $.ajax({
                url:'/passfinder.do',
                type:'post',
                data : {"findid":findid
                },
                success:function(s){
                    if (s == "findOK")
                    {
                        alert("가입하신 이메일로 비밀번호가 전송되었습니다.");
                        $('.passfinder_answer').text('메일함을 확인해주세요!')
                    }else
                    {
                        alert("입력하신 이메일이 존재하지 않습니다!");
                        $('.passfinder_answer').text('메일주소를 확인해주세요!')
                    }
                },
                error:function(){
                    alert("비밀번호 검색이 실패하였습니다. 잠시후 다시 시도해주세요.");
                }
            });
        }
    });
});

