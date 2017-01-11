function hello(session) {

    var d = new Date();
    var h = parseInt(d.getHours());

    var str = "上午好！";

    if (h > 12 && h < 18) {
        str = "下午好！";
    }
    if (h > 18 && h < 24) {
        str = "晚上好！";
    }

    $("#time")[0].innerHTML = str;

    if (session === "null") {
        window.location = "/coms2.0-master/login";
    }

}

function editPassword() {

    var userPassOld = $("#userPassOld")[0].value;
    var userPassNew = $("#userPassNew")[0].value;
    var userPassConfirm = $("#userPassConfirm")[0].value;

    $.ajax({
        url: "/coms2.0-master/login/editPassword",
        type: "post",
        datatype: "json",
        data: {userPassOld: userPassOld, userPassNew: userPassNew, userPassConfirm: userPassConfirm},
        success: function (data, stats) {
            confirm(data.message);
//            $("#alter_message").html(data.message);
//            $("#modal-message").modal("show");
//            setTimeout("$(\"#modal-message\").modal(\"hide\")", 2000);
            window.location = "/coms2.0i/login/logout";
//            if (stats === "success") {
//                if (data === "success") {
//                    $("#passwordEdit").modal("hide");
//                    alert("密碼修改成功！");
//                    window.location = "<%=basePath%>login";
//                } else if (data === "confirm error") {
//                    $("#message").html("兩次輸入的密碼不一致！");
//                } else if (data === "same old new") {
//                    $("#message").html("新舊密碼不能一樣！");
//                } else {
//                    $("#message").html("原密碼輸入錯誤！");
//                }
//            }

        },
        error: function (data) {
            var response = JSON.parse(data.responseText.toString());
            $("#message").html(response.message);
        }
    });
}

$(function () {
    var d = new Date();
    var week;

    function add_zero(temp) {
        if (temp < 10)
            return "0" + temp;
        else
            return temp;
    }

    switch (d.getDay()) {
        case 1:
            week = "星期一";
            break;
        case 2:
            week = "星期二";
            break;
        case 3:
            week = "星期三";
            break;
        case 4:
            week = "星期四";
            break;
        case 5:
            week = "星期五";
            break;
        case 6:
            week = "星期六";
            break;
        default:
            week = "星期天";
    }
    var years = d.getFullYear();
    var month = add_zero(d.getMonth() + 1);
    var days = add_zero(d.getDate());
    var hours = add_zero(d.getHours());
    var minutes = add_zero(d.getMinutes());
    var seconds = add_zero(d.getSeconds());
    var ndate = "現在是  " + years + "年" + month + "月" + days + "日 " + hours + ":" + minutes + ":" + seconds + " " + week;
    var ndate2 = years + "年" + month + "月" + days + "日 " + " " + week + "  ";

    $("#timeday")[0].innerHTML = ndate2;

});
