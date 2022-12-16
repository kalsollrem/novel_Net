$(function (){
    //써머노트
    jsonArray = [];
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
            onImageUpload : function(files) {
                uploadSummernoteImageFile(files[0],this);
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

    function uploadSummernoteImageFile(file, editor) {
        data = new FormData();
        data.append("file", file);
        $.ajax({
            data : data,
            type : "POST",
            url : "/novelnet/writeImg.do",
            contentType : false,
            processData : false,
            success : function(data) {
                //항상 업로드된 파일의 url이 있어야 한다.
                $(editor).summernote('insertImage', data.url);
                console.log(data.url);
            }
        });
    }



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
