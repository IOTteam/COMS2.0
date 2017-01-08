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
        <link rel="shortcut icon"type="image/x-icon" href="<%=basePath%>pages/image/24pxnet.ico" />
        
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
<!--                    <a href="#">訂單管理</a>-->
                    <a href="<%=basePath%>OrderManage/queryOrderHeadList">訂單管理</a>
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
        <article class="page-404 minWP text-c">
            <p class="error-title"><span class="va-m"> 出錯了！</span></p>
		<p class="error-description">${e}:${message}</p>
		<p class="error-description">${cause}</p>
	</article>
        <div class="footer">IOT TEAM</div>
    </section>

    <script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.min.js"></script> 
    <script type="text/javascript" src="<%=basePath%>pages/lib/layer/2.1/layer.js"></script> 
    <script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="<%=basePath%>pages/static/h-ui.admin/js/H-ui.admin.js"></script> 

</body>
</html>