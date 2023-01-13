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
        searchTag  = "";
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
})