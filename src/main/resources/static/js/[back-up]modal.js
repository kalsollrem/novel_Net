$(function(){
    
  //배너
  var slide_index =0;

  setInterval(function(){

      if(slide_index<2){slide_index++;}
      else{slide_index=0;}

      $(".slide-cont").animate({top:(slide_index*-280)},500)    
      $(".b-slide-cont").animate({top:(slide_index*-280)},500)    

  },3000);

  //회원 가입 모달
  var sign_box = $('.sign_box');
  sign_box.css("position", "absolute");
  sign_box.css("top", Math.max(0, (($(window).height() - sign_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
  sign_box.css("left", Math.max(0, (($(window).width() - sign_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
  $('.sign_box').fadeIn(500);
  
  //로그인 모달
  var login_box = $('.login_box');
  login_box.css("position", "absolute");
  login_box.css("top", Math.max(0, (($(window).height() - login_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
  login_box.css("left", Math.max(0, (($(window).width() - login_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
  $('.login_box').fadeIn(500);
  
  //검색모달
  var login_box = $('.hashtag_box');
  login_box.css("position", "absolute");
  login_box.css("top", Math.max(0, (($(window).height() - login_box.outerHeight()) / 2) + $(window).scrollTop()) + "px");
  login_box.css("left", Math.max(0, (($(window).width() - login_box.outerWidth()) / 2) + $(window).scrollLeft()) + "px");

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

});
