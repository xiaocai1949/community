function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify(
            {
                "content": content,
                "parentId": questionId,
                "type": 1
            }
        ),
        success: function (response) {
            if (response.code == 200) {
                /*登录成功*/
                $("#comment_section").hide();
            } else {
                if (response.code == 2003) {
                    /*未登录处理*/
                    var isAccept = confirm(response.message);
                    if (isAccept) {
                        /*确认登录，页面跳转*/
                        window.open("https://github.com/login/oauth/authorize?client_id=03eca46860dbebc33c1c\n" +
                            "                &redirect_uri=http://localhost:8081/callback&scope=user&state=1");
                        /*利用localStorage实现成功登录后登录页面关闭*/
                        window.localStorage.setItem("closeable", true);
                    }
                } else {
                    /*其他错误处理*/
                    alert(response.message);
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}