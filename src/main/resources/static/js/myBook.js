$(function(){

    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let keyword;
    let newOld;
    let cartegory;
    let page;

    //주소값 없을시
    try{
        keyword     = url.get('keyword').toString();
        newOld      = url.get('newOld').toString();
        cartegory   = url.get('cartegory').toString();
        page        = url.get('page').toString();
    }catch (err)
    {
        keyword      = "";
        newOld       = "desc";
        cartegory    = "all";
        page         = "1";
    }

    //메뉴 css설정
    if(cartegory == "compWrite")
    {
        $("#book_all").addClass('book_none');
        $("#book_new").addClass('book_none');
        $("#book_fin").addClass('book_choose');
    }else if (cartegory == "doWrite"){
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
    {location.href = '/novelnet/mybook?cartegory=all&page='+page+'&newOld='+newOld+'&keyword='+keyword;});
    $(".book_new").click(function()
    {location.href = '/novelnet/mybook?cartegory=doWrite&page='+page+'&newOld='+newOld+'&keyword='+keyword;});
    $(".book_fin").click(function()
    {location.href = '/novelnet/mybook?cartegory=compWrite&page='+page+'&newOld='+newOld+'&keyword='+keyword;});

    $("#sort_what").change(function()
    {
        newOld = $("#sort_what").val();
        location.href = '/novelnet/mybook?cartegory='+cartegory+'&page='+page+'&newOld='+newOld+'&keyword='+keyword;
    });
});
