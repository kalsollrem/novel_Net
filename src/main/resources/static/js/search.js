$(function (){
    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let sort;
    let keyword;
    let searchType;
    let searchTag;

    //주소값 없을시
    try{
        sort            = url.get('sort').toString();
        searchType      = url.get('searchType').toString();
        searchTag       = url.get('searchTag').toString();
    }catch (err)
    {
        sort       = "date";
        searchType = "title";
        searchTag  = "t_01";
    }

    //검색어 설정
    try {
        keyword     = url.get('keyword').toString();
        $("#search").val(keyword);
    }catch (e) {
        keyword = "";
    }

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
    $(".date_date").click(function (){ location.href = '/novelnet/search?sort=date&keyword='+keyword; })
    $(".date_view").click(function (){ location.href = '/novelnet/search?sort=view&keyword='+keyword; })
    $(".date_vote").click(function (){ location.href = '/novelnet/search?sort=vote&keyword='+keyword; })

    //검색
    $(".search_go").click(function (){
        location.href = '/novelnet/search?sort='+sort+'searchType'+searchType+'searchTag'+searchTag+'&keyword='+keyword; })


    //태그
    //01:전채 , 02:판타지, 03:무협, 04:현대, 05:로맨스, 06:대체역사, 07:공포, 08:SF, 09:스포츠, 10:기타,  00:기타 태그 검색
    if(searchTag != "t_00")
    {
        $("#"+searchTag).addClass('tag_card_choose');
    }
})