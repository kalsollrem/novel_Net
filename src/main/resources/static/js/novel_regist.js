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
})


