$(function (){
    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let sort;
    let keyword;
    let searchType;
    let mainTag;
    let searchTag;

    //주소값 없을시
    try         { sort = url.get('sort').toString(); }
    catch (err) { sort = "date";    }

    //주소값 없을시
    try         { searchType = url.get('searchType').toString(); }
    catch (err) { searchType = "title";  }

    //메인태그 없을시
    try         { mainTag  = url.get('mainTag').toString(); }
    catch (err) { mainTag  = "t_01"; }

    //서브태그 없을시
    try         { searchTag  = url.get('searchTag').toString(); }
    catch (err) { searchTag  = ""; }

    //검색어 설정
    try         { keyword    = url.get('keyword').toString();   }
    catch (e)   { keyword = ""; }
    $("#search").val(keyword);


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

    // 클릭시 링크이동
    $(".date_date").click(function (){ location.href = '/novelnet/search?sort=date&mainTag='+mainTag+'&searchType='+searchType+'&searchTag='+searchTag+'&keyword='+keyword; })
    $(".date_view").click(function (){ location.href = '/novelnet/search?sort=view&mainTag='+mainTag+'&searchType='+searchType+'&searchTag='+searchTag+'&keyword='+keyword; })
    $(".date_vote").click(function (){ location.href = '/novelnet/search?sort=vote&mainTag='+mainTag+'&searchType='+searchType+'&searchTag='+searchTag+'&keyword='+keyword; })

    //검색
    $(".search_go").click(function (){
        keyword = $("#search").val()
        location.href = '/novelnet/search?sort='+sort+'&mainTag='+mainTag+'&searchType='+searchType+'&searchTag='+searchTag+'&keyword='+keyword; })


    //태그
    //01:전채 , 02:판타지, 03:무협, 04:현대, 05:로맨스, 06:대체역사, 07:공포, 08:SF, 09:스포츠, 10:기타,  00:기타 태그 검색
    const tagArr = ['t_01', 't_02', 't_03', 't_04', 't_05', 't_06', 't_07', 't_08', 't_09', 't_10'];
    if(tagArr.includes(mainTag) == false)
    {
        $(".tag_search").css("background-color",'#333')
        $(".tag_search").css("color",'white')
    }else
    {
        $("#"+mainTag).addClass('tag_card_choose');
    }

    $(".tag_card").click(function (){
        location.href = '/novelnet/search?sort='+sort+'&mainTag='+this.id+'&searchType='+searchType+'&searchTag='+searchTag+'&keyword='+keyword;
    })
})