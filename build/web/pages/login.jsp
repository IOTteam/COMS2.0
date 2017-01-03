<%-- 
    Document   : login
    Created on : 2016-12-15, 10:14:42
    Author     : David
--%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>登陆</title>
        <link rel="stylesheet" href="<%=basePath%>pages/bootstrap-3.3.0/css/bootstrap.css">
        <link rel="stylesheet" href="<%=basePath%>pages/bootstrap-3.3.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=basePath%>pages/bootstrap-3.3.0/js/jquery.css">
        <link rel="stylesheet" href="<%=basePath%>pages/css/loginbox.css">
    </head>

    <body>

        <div class="box">
            <div class="login-box">

                <h1 class="text-center">
                    <span>客戶訂單管理系統</span>
                </h1>

                <h3 style="color:blueviolet" class="text-center" id="time">           
                </h3>
                <div class="login-title text-center">
                    <h1><small>請輸入登錄信息</small></h1>
                </div>
                <div class="login-content ">
                    <div class="form">
                        <form action="#" method="post">
                            <div class="form-group">
                                <div class="col-xs-12  ">
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                        <input type="text" id="username" name="userName" autocomplete="off" value="admin2016" class="form-control" placeholder="用戶名">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-12  ">
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                        <input type="text" id="password"  name="userPass"  autocomplete="off" value="123" class="form-control" placeholder="密碼">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-12  ">
                                    <div class="input-group  input-group-lg">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                        <input style="width:120px" type="text" id="password" name="kaptcha" autocomplete="off" class="form-control" placeholder="驗證碼">
                                        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                        <span>&nbsp;&nbsp;&nbsp;</span>
                                        <img src="<%=basePath%>/login/captcha-image" id="kaptchaImage"/>       
                                        <a onclick="changeCode()">看不清?&nbsp;换一张</a>  
                                    </div>
                                    <div class="message-info text-center">
                                        <p>${message_k}</p>
                                        <p>${message}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group form-actions">
                                <div class="col-xs-4 col-xs-offset-4 ">
                                    <button type="submit" class="btn btn-sm btn-info"><span class="glyphicon glyphicon-off"></span> 登录</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- jQuery first, then Bootstrap JS. -->
        <script src="<%=basePath%>pages/bootstrap-3.3.0/js/jquery.js"></script>
<!--        <script src="<%=basePath%>pages/Bootstrap/js/bootstrap.min.js"></script>-->
        <script>


            $(function () {  //生成验证码         
                $('#kaptchaImage').click(function () {
                    $(this).hide().attr('src', '<%=basePath%>/login/captcha-image?' + Math.floor(Math.random() * 100)).fadeIn();
                });
            });
            function changeCode() {  //刷新
                $('#kaptchaImage').hide().attr('src', '<%=basePath%>/login/captcha-image?' + Math.floor(Math.random() * 100)).fadeIn();
                event.cancelBubble = true;
            }

            window.onbeforeunload = function () {
                //关闭窗口时自动退出  
                if (event.clientX > 360 && event.clientY < 0 || event.altKey) {
                    alert(parent.document.location);
                }
            };

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
                var ndate2 = "今天是  " + years + "年" + month + "月" + days + "日 "  + " " + week;

                $("#time")[0].innerHTML = ndate2;

            });
        </script>

    </body>
</html>
