$(function (){
    const urlStr = location.search;
    const url = new URLSearchParams(urlStr);
    let sort;
    let page;
    let carte;


    let link = '/novelnet/best?sort='+sort+'&carte='+carte+'&page='+page;

    //정렬 없을시
    try         { sort = url.get('sort').toString(); }
    catch (err) { sort = "good";    }

    //카테고리 없을시
    try         { carte = url.get('carte').toString(); }
    catch (err) { carte = "allNovel";  }

    //페이지 없을시
    try         { page  = url.get('page').toString(); }
    catch (err) { page  = 1; }
    if(Number.isInteger(page) == false)
    {
        page = 1
    }

    //상단메뉴 CSS
    if (carte == 'freeNovel' ||carte == 'primeNovel' || carte == 'finNovel')
    {
        $('#'+carte).css("background-color",'#333')
        $('#'+carte).css("color",'white')
    }else
    {
        $('#allnovel').css("background-color",'#333')
        $('#allnovel').css("color",'white')
    }


    //상단 메뉴 클릭
    $(".categoryMark").click(function (){
        location.href = '/novelnet/best?sort='+sort+'&carte='+this.id+'&page='+page;
    })

    //상단 메뉴 클릭
    $(".ranking_load_button").click(function (){
        page = page+1
        location.href = '/novelnet/best?sort='+sort+'&carte='+carte+'&page='+page;
    })


});