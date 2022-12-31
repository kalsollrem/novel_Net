$(function(){

    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let keyword;
    let newOld;
    let category;
    let page;

    //주소값 없을시
    try{
        newOld      = url.get('newOld').toString();
        category    = url.get('category').toString();
        page        = url.get('page').toString();
    }catch (err)
    {
        newOld       = "desc";
        category     = "all";
        page         = "1";
    }

    //검색어칸 설정
    try {
        keyword     = url.get('keyword').toString();
        $(".booksearch").val(keyword);
    }catch (e) {
        keyword = "";
    }

    console.log(keyword);
    console.log(newOld);
    console.log(category);
    console.log(page);


    //메뉴 css설정
    if(category == "compWrite")
    {
        $("#book_all").addClass('book_none');
        $("#book_new").addClass('book_none');
        $("#book_fin").addClass('book_choose');
    }else if (category == "doWrite"){
        $("#book_all").addClass('book_none');
        $("#book_new").addClass('book_choose');
        $("#book_fin").addClass('book_none');
    }else
    {
        $("#book_all").addClass('book_choose');
        $("#book_new").addClass('book_none');
        $("#book_fin").addClass('book_none');
    }

    //메뉴 클릭
    $(".book_all").click(function()
    {
        keyword = "";
        location.href = '/novelnet/mybook?category=all&page='+page+'&newOld='+newOld;
    });
    $(".book_new").click(function()
    {
        keyword = "";
        location.href = '/novelnet/mybook?category=doWrite&page='+page+'&newOld='+newOld;
    });
    $(".book_fin").click(function()
    {
        keyword = "";
        location.href = '/novelnet/mybook?category=compWrite&page='+page+'&newOld='+newOld;
    });

    //검색 기능
    $(".search_btn").click(function()
    {
        keyword = $(".booksearch").val()
        location.href = '/novelnet/mybook?category='+category+'&page='+page+'&newOld='+newOld+'&keyword='+keyword;
    });

    $("#sort_what").change(function()
    {
        newOld = $("#sort_what").val();
        location.href = '/novelnet/mybook?category='+category+'&page='+page+'&newOld='+newOld+'&keyword='+keyword;
    });
});
