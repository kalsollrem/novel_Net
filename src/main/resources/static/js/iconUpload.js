$(function(){

    if($('.switch').val() =='on')
    {
        $(".img_zone").hide();
        $(".imgTarget").show();
    }


    var fileForm = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
    var maxSize = 5 * 1024 * 1024;

    $(".fileInput").on("change", function(event) {
        var file = event.target.files[0];
        var reader = new FileReader();
        $(".img_zone").hide();
        $(".imgTarget").show();
        reader.onload = function(e) {
            $(".imgTarget").attr("src", e.target.result);
        }

        reader.readAsDataURL(file);
    });

    $(".empty").click(function(){
        $('.fileInput').val(null);
        $(".img_zone").show();
        $(".imgTarget").hide();
    })



    //전송
    $('.chang_Btn').click(function ()
    {
        let form = $('#iconChange')[0];
        let fromDate = new FormData(form)

        $.ajax({
            url:'/iconChange.do',
            type:'post',
            data : fromDate,
            processData: false,
            contentType: false,
            success:function(s){
                opener.parent.location.reload();
                close();
            },
            error:function(){
                alert("프로필 등록에 실패하였습니다.");
            }
        });
    });
});


