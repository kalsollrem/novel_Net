$(function (){

    var fileForm = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
    var maxSize = 5 * 1024 * 1024;

    $("#cover").on("change", function(event) {

        var imgFile = $("#cover").val();
        var target = event.target.files[0];
        var fileSize;


        if(imgFile != "" && imgFile != null)
        {
            fileSize = document.getElementById("cover").files[0].size;

            if(!imgFile.match(fileForm))
            {
                alert("이미지 파일만 업로드 가능합니다. \n" + "지원확장자(jpg, jpeg, png, gif, bmp)");
                return;

            }
            else if(fileSize >= maxSize)
            {
                alert("파일 사이즈는 5MB까지 가능합니다.");
                return;
            }
            else
            {
                var reader = new FileReader();
                reader.onload = function(e) {
                    $("#novel_cover").attr("src", e.target.result);
                }

                reader.readAsDataURL(target);
            }
        }
    });


    //수정 버튼
    $(".resist_yes").click(function (){
        let title  = $('#regist_title').val();
        title = title.replace(' ','');
        if(title.length>1)
        {
            $('#novelRegist').submit();
        }
        else { alert('제목을 입력해주세요')}
    });

    //수정 버튼
    $(".resist_noveldel").click(function (){

        $.ajax({
            url:'/noveDelete.do',
            type:'post',
            data : {"n_num":$(this).val()},
            success:function(s){
                if (s == 1) { location.href = "/novelnet"; }
                else        { alert('삭제에 실패하였습니다.'); }
            },
            error:function(){
                alert("통신에 문제가 있습니다..");
            }
        });
    });

    $(".resist_no").click(function (){
        window.history.back();
    })

})


