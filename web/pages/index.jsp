<%-- 
    Document   : index
    Created on : 2016-12-15, 11:01:25
    Author     : David
--%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="iot.dao.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="renderer" content="webkit|ie-comp|ie-stand">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta http-equiv="Cache-Control" content="no-siteapp" />
<!--        更改左上角圖標-->
        <link rel="shortcut icon" href="http://www.easyicon.net/api/resizeApi.php?id=1174456&size=24" type="image/x-icon" />
        
        <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/css/myStyle.css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui/css/H-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/css/H-ui.admin.css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/skin/default/skin.css" id="skin" />
        <title>管理系統主頁</title>
        <!--        <meta name="keywords" content="表格">-->
    </head>
    <body>

        <header class="navbar-wrapper">
            <div class="navbar navbar-fixed-top"> 
                <a href="<%=basePath%>index" class="logo navbar-logo f-l mr-10 hidden-xs" style="font-family: LiSu; font-size: 30px">客戶訂單管理系統</a>
                <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                    <ul class="cl">
                        <li>今天是&nbsp;&nbsp;<a style="color: red"><% out.print(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date())); %></a>&nbsp;&nbsp;</li>
                        <li id="time"></li>	
                        <li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A"  style="color: red">${user.userName}</a>
                            <ul class="dropDown-menu menu radius box-shadow">
                                <li><a data-toggle="modal" href="#userInfo">個人資訊</a></li>
                                <li><a data-toggle="modal" href="#passwordEdit">修改密碼</a></li>
                                <li><a href="<%=basePath%>login/logout">登出</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </header>

    <aside class="Hui-aside">
        <div class="menu_dropdown">
            <ul>
                <li>
                    <a href="<%=basePath%>CustomerManage/CustomerQuery">客戶管理</a>
                </li>
            </ul>
            <ul>
                <li>
                    
                    <a href="<%=basePath%>CustomerPriceManage/queryCustomerPrice">客戶產品單價管理</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="#">訂單管理</a>
                    <!--<a href="<%=basePath%>OrderManage/queryOrderHeadList">訂單管理</a>-->
                </li>
            </ul>
            <ul>
                <li>
                    <a href="<%=basePath%>Product/ProductQuery">產品管理</a>
                </li>
            </ul>
        </div>
    </aside>

    <div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
    <section class="Hui-article-box">
        <div class="page-container" style="background-image: url(bg2.png);background-repeat: no-repeat;background-size: 100%">

        </div>
        <div class="footer">IOT TEAM</div>
    </section>

    <div id="userInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h3 id="myModalLabel">個人資訊</h3><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="" class="form form-horizontal responsive">
                <div class="row cl">
                    <label class="form-label col-xs-3">用戶編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text" autocomplete="off" value="${user.userId}" name="username" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3">用戶姓名：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text" autocomplete="off"  value="${user.userName}" name="password" />
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">確定</button>
        </div>
    </div>

    <!--使用者修改密碼-->
    <div id="passwordEdit" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h3 id="myModalLabel">修改密碼</h3><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="login/editPassword" method="post" class="form form-horizontal responsive">
                <div class="row cl">
                    <label class="form-label col-xs-3">原密碼：</label>
                    <div class="formControls col-xs-5">
                        <input type="password" class="input-text" id="userPassOld" autocomplete="off" name="userPassOld" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3">新密碼：</label>
                    <div class="formControls col-xs-5">
                        <input type="password" class="input-text" id="userPassNew" autocomplete="off" name="userPassNew" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3">確認新密碼：</label>
                    <div class="formControls col-xs-5">
                        <input type="password" class="input-text" id="userPassConfirm" autocomplete="off"  name="userPassConfirm" />
                        <p id="message" class="c-error text-l"></p>
                    </div>
                </div>              
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                        <input class="btn btn-primary radius"  type="button"  id="savePassword" onclick="editPassword()" value="確定" >    
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>        
        </div>
    </div>

    <script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.min.js"></script> 
    <script type="text/javascript" src="<%=basePath%>pages/lib/layer/2.1/layer.js"></script> 
    <script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="<%=basePath%>pages/static/h-ui.admin/js/H-ui.admin.js"></script> 
    <script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
    <script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
    <!--<script type="text/javascript" src="<%=basePath%>pages/com.js"></script>-->
    <script>

        //顯示登錄成功後時間問候
        $(function () {

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
            var session = "<%=(User) session.getAttribute("user")%>";
            if (session === null) {
                window.location = "<%=basePath%>login";
            }
        });

        function editPassword() {

            var userPassOld = $("#userPassOld")[0].value;
            var userPassNew = $("#userPassNew")[0].value;
            var userPassConfirm = $("#userPassConfirm")[0].value;

            $.ajax({
                url: "login/editPassword",
                type: "post",
                datatype: "json",
                data: {userPassOld: userPassOld, userPassNew: userPassNew, userPassConfirm: userPassConfirm},
                success: function (data, stats) {
                    if (stats === "success") {
                        if (data === "success") {
                            $("#passwordEdit").modal("hide");
                            alert("密碼修改成功！");
                            window.location = "<%=basePath%>login";
                        } else if (data === "confirm error") {
                            $("#message").html("兩次輸入的密碼不一致！");
                        } else if (data === "same old new") {
                            $("#message").html("新舊密碼不能一樣！");
                        } else {
                            $("#message").html("原密碼輸入錯誤！");
                        }
                    }
                },
                error: function (data) {
                    alert("密碼修改失败！");
                }
            });

        }
    </script>
</body>
</html>
