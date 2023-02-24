$(function (){

    //신고삭제
    $(".delete").click(function()
    {
        $.ajax({
            url:'/masterMemoDelete.do',
            type:'post',
            data : {"No":$(this).val()},
            success:function(s){
                if (s == 1)    {location.href="master/notification"};
                if (s == 0)    {alert("삭제에 실패하였습니다")};
            },
            error:function(){
                alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
            }
        });
    });

})
