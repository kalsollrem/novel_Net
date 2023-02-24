$(function (){
    //써머노트
    $('#summernote').summernote({
        height: 600,
        lang : "ko-KR",
        focus: true,
        toolbar:
            [
                // [groupName, [list of button]]
                ['fontname', ['fontname']],
                ['fontsize', ['fontsize']],
                ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
                ['color', ['forecolor','color']],
                ['para', ['paragraph']],
                ['height', ['height']],
                ['insert',['picture']],
            ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
        callbacks: {
            //여기 부분이 이미지를 첨부하는 부분
            onImageUpload : function(files, editor, welEditable) {
                for(var i = files.length -1 ; i>=0; i--)
                {
                    uploadSummernoteImageFile(files[i],this);
                }
            },
            onPaste: function (e) {
                var clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    var item = clipboardData.items[0];
                    if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                        e.preventDefault();
                    }
                }
            }
        }
    });

    jsonArray = [];

    function uploadSummernoteImageFile(file, el) {
        data = new FormData();
        data.append("file", file);
        $.ajax({
            data : data,
            type : "POST",
            enctype: 'multipart/form-data',
            url : "/novelnet/writeImg.do",
            contentType : false,
            processData : false,
            success : function(data) {
                //항상 업로드된 파일의 url이 있어야 한다.
                $(el).summernote('editor.insertImage', data.url);
                console.log(data.url);
            },
            error : function(e) {
                alert("이미지를 첨부하는데 실패하였습니다.")
            }
        });
    }


    // //이미지 첨부
    // $(".gongType").on("change", function(event)
    // {
    //     //selected value
    //     if($(this).val() == 'event') {$('.eventImgFrame').stop().slideDown(); }
    //     else
    //     {
    //         var reader = new FileReader();
    //
    //         var imgFile = $(".fileUp").val();
    //         var target = event.target.files[0];
    //         var fileSize;
    //
    //
    //         if (imgFile != "" && imgFile != null) {
    //             fileSize = document.getElementById("cover").files[0].size;
    //
    //             if (!imgFile.match(fileForm))
    //             {
    //                 alert("이미지 파일만 업로드 가능합니다. \n" + "지원확장자(jpg, jpeg, png, gif, bmp)");
    //                 return;
    //             }
    //             else if (fileSize >= maxSize)
    //             {
    //                 alert("파일 사이즈는 5MB까지 가능합니다.");
    //                 return;
    //             }
    //             else
    //             {
    //                 var reader = new FileReader();
    //                 reader.onload = function (e)
    //                 {
    //                     $(".img_zone>span").hide();
    //                     $(".displyImg").show();
    //                     $(".displyImg").attr("src", e.target.result);
    //                 }
    //                 reader.readAsDataURL(target);
    //             }
    //         }
    //     }
    // });
    $(".fileUp").on("change", function(event) {
        var file = event.target.files[0];
        var reader = new FileReader();

        reader.onload = function(e) {
            $(".img_zone>span").hide();
            $(".displyImg").show();
            $(".displyImg").attr("src", e.target.result);
        }

        reader.readAsDataURL(file);
    });


    //셀렉트 박스 이벤트 선택시 이미지 첨부 오픈
    $(".gongType").on("change", function(){
        //selected value
        if($(this).val() == 'event') {$('.eventImgFrame').stop().slideDown(); }
        else                         {$('.eventImgFrame').stop().slideUp();
            $(".fileUp").replaceWith( $(".fileUp").clone(true) ); //윈도우용
            $(".fileUp").val("");
            $(".img_zone>span").show();
            $(".displyImg").hide();}
    });


    $(".write_uploads").click(function (){
        let title  = $('.master_title').val();
        title = title.replace(' ','');

        if(title.length>4)
        {
            if($(".gongType").val()=='event'){
                if($('.fileUp').val() != null && $('.fileUp').val() != "") {$('#novelRegist').submit();}
                else { alert("이미지 파일이 없습니다")}
            }
            else{$('#novelRegist').submit();}
        }
        else { alert('제목이 너무 짧습니다.')}

    });

    //글작성
    // $('.write_uploads').click(function (){
    //     var form = $('.fileUp')[0].files[0];
    //     var formData = new FormData();

        // let title = $('#write_title2').val();
        // let text  = $('#summernote').val();
        // let type  = $('.gongType').val();
        // let imgfile = $(".fileUp").val();


        // $.ajax({
        //     url:'/masterWrite.do',
        //     type:'post',
        //     enctype : 'multipart/form-data', //반드시 enctype 을 멀티파트로 설정
        //     contentType : false, //false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
        //     processData : false, //false로 선언 시 formData를 string으로 변환하지 않음
        //     data : {formData},
        //     success:function(s){
        //         if      (s == 0){console.log('작성 성공'); location.href = '';}
        //         else            {alert('작성에 실패하였습니다')}
        //     },
        //     error:function(){
        //         alert("로그인되지 않았거나 혹은 서버와의 통신에 문제가 있습니다.");
        //     }
        // });
    // })


    //글수정 기능
    const title = "";
    const memo = "";
    if(title != "" && title != null)   //제목에 값 넣기
    {
        $('#write_title').attr('value', title);
    }
    if(memo != ""  && memo != null)  //썸머노트 본문에 글넣기
    {
        $('#summernote').summernote('editor.insertText', memo);
    }
})
