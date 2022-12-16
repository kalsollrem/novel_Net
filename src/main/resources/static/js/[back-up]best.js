$(function(){
    
    $('.category>li').click(function(){
        $(".category>li").css("border", "0.5px solid silver");
        $(".category>li").css("color", "gray");
        $(this).css("border", "0.5px solid rgb(255, 70, 70)");
        $(this).css("color", "rgb(255, 70, 70)");
      });
});

