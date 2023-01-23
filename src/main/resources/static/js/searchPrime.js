$(function (){
    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let sort;
    let mainTag;
    let doType;
    let searchTag;
    let monopoly;

    let link = '/novelnet/searchPrime?sort='+sort+'&mainTag='+mainTag+'&doType='+doType+'&searchTag='+searchTag+'&monopoly'+monopoly

    //주소값 없을시
    try         { sort = url.get('sort').toString(); }
    catch (err) { sort = "date";    }

    //주소값 없을시
    try         { doType = url.get('doType').toString(); }
    catch (err) { doType = "title";  }

    //메인태그 없을시
    try         { mainTag  = url.get('mainTag').toString(); }
    catch (err) { mainTag  = "t_01"; }

    //01:전채 , 02:판타지, 03:무협, 04:현대, 05:로맨스, 06:대체역사, 07:공포, 08:SF, 09:스포츠, 10:기타,  00:기타 태그 검색
    const tagArr = ['t_01', 't_02', 't_03', 't_04', 't_05', 't_06', 't_07', 't_08', 't_09', 't_10'];
    if(tagArr.includes(mainTag) == false)   { $("#t_01").addClass('tag_card_choose');    }
    else                                    { $("#"+mainTag).addClass('tag_card_choose') };

    //서브태그 없을시
    try         { searchTag  = url.get('searchTag').toString(); }
    catch (err) { searchTag  = ""; }

    //플랫폼 독점 여부
    try         { monopoly    = url.get('monopoly').toString();   }
    catch (e)   { monopoly    = ""; }

    //연재상태(doType) : 연재중(doNovel), 신작만(newNovel), 완결(finNovel)
    try         { doType    = url.get('doType').toString();   }
    catch (e)   { doType    = ""; }


    //정렬칸 이미지 변경
    if (sort == "view"){
        $('.date_date>img').attr('src','../img/date_off.png');
        $('.date_view>img').attr('src','../img/view_on.png');
        $('.date_vote>img').attr('src','../img/vote_off.png');
    }else if(sort == "vote"){
        $('.date_date>img').attr('src','../img/date_off.png');
        $('.date_view>img').attr('src','../img/view_off.png');
        $('.date_vote>img').attr('src','../img/vote_on.png');
    }else
    {
        $('.date_date>img').attr('src','../img/date_on.png');
        $('.date_view>img').attr('src','../img/view_off.png');
        $('.date_vote>img').attr('src','../img/vote_off.png');
    }

    //서치 타입 css
    if(doType == "newNovel")
    {
        $('.search_all').addClass('search_none');
        $('.search_only').addClass('search_none');
        $('.search_new').addClass('search_choose');
        $('.search_fin').addClass('search_none');
    }
    else if(doType == "finNovel")
    {
        $('.search_all').addClass('search_none');
        $('.search_only').addClass('search_none');
        $('.search_new').addClass('search_none');
        $('.search_fin').addClass('search_choose');
    }
    else if(doType == "onlyNovel")
    {
        $('.search_all').addClass('search_none');
        $('.search_only').addClass('search_choose');
        $('.search_new').addClass('search_none');
        $('.search_fin').addClass('search_none');
    }
    else
    {
        $('.search_all').addClass('search_choose');
        $('.search_only').addClass('search_none');
        $('.search_new').addClass('search_none');
        $('.search_fin').addClass('search_none');
    }

    $('.search_all').click(function  (){location.href = '/novelnet/searchPrime?sort=date&mainTag='+mainTag+'&doType=allNovel&searchTag='+searchTag+'&monopoly=all'});
    $('.search_only').click(function (){location.href = '/novelnet/searchPrime?sort=date&mainTag='+mainTag+'&doType=onlyNovel&searchTag='+searchTag+'&monopoly=only'});
    $('.search_new').click(function  (){location.href = '/novelnet/searchPrime?sort=date&mainTag='+mainTag+'&doType=newNovel&searchTag='+searchTag+'&monopoly=all'});
    $('.search_fin').click(function  (){location.href = '/novelnet/searchPrime?sort=date&mainTag='+mainTag+'&doType=finNovel&searchTag='+searchTag+'&monopoly=all'});

    // 클릭시 링크이동
    $(".date_date").click(function (){ location.href = '/novelnet/searchPrime?sort=date&mainTag='+mainTag+'&doType='+doType+'&searchTag='+searchTag+'&monopoly='+monopoly})
    $(".date_view").click(function (){ location.href = '/novelnet/searchPrime?sort=view&mainTag='+mainTag+'&doType='+doType+'&searchTag='+searchTag+'&monopoly='+monopoly})
    $(".date_vote").click(function (){ location.href = '/novelnet/searchPrime?sort=vote&mainTag='+mainTag+'&doType='+doType+'&searchTag='+searchTag+'&monopoly='+monopoly})

    //클릭시 작성 페이지로 이동
    $('.search_write').click(function (){
        if ($("#goOK").val() == 'none' )
        {
            alert('소설 등록 페이지로 가기 위해서 로그인 부탁드립니다.')
        }
        else
        {
            location.href= '/novelnet/regist'
        }
    })

    //서브 태그 활성화시
    if(searchTag != "")
    {
        $(".tag_search").css("background-color",'#333')
        $(".tag_search").css("color",'white')
    }

    //메인 태그 검색
    $(".tag_card").click(function (){
        location.href = '/novelnet/searchPrime?sort='+sort+'&mainTag='+this.id+'&doType='+doType+'&searchTag='+searchTag+'&monopoly='+monopoly
    })

    //서브 태그 검색창 열기
    $(".tag_search").click(function (){
        const hashtag_finder = $('.hashtag_finder');
        hashtag_finder.css("position", "absolute");
        hashtag_finder.css("top", Math.max(0, (($(window).height() - hashtag_finder.outerHeight()) / 2) + $(window).scrollTop()) + "px");
        hashtag_finder.css("left", Math.max(0, (($(window).width() - hashtag_finder.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
        $(".hashtag_finder").fadeIn(1000);
        $("#hashtag_find").val(searchTag);
    });

    //서브태그 검색창 다른영역 클릭시 창닫기.
    $(document).mouseup(function (e){
        if($(".hashtag_finder").has(e.target).length === 0){
            $(".hashtag_finder").hide();
        }
    });

    //태그 추가 검색 버튼
    $(".subTagSearch").click(function (){
        searchTag = $('#hashtag_find').val();
        location.href = '/novelnet/searchPrime?sort='+sort+'&mainTag='+mainTag+'&doType='+doType+'&searchTag='+searchTag+'&monopoly='+monopoly
    });

    //서브태그 클릭시
    $(".subSearch").click(function (){
        searchTag = this.innerText.substr(1)
        location.href = '/novelnet/searchPrime?sort='+sort+'&mainTag='+mainTag+'&doType='+doType+'&searchTag='+searchTag+'&monopoly='+monopoly
    })
});