<%@page import="iot.dao.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="shortcut icon"type="image/x-icon" href="<%=basePath%>pages/image/24pxnet.ico" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui/css/H-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/skin/default/skin.css" id="skin" />
<!--[if lt IE 9]>
<link href="static/h-ui/css/H-ui.ie.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>客户订单管理系统</title>
 <style type="text/css">
.color {
    background-color: #99ffff;
}
</style>       
</head>
<body>
     <!--导航栏-->
    <header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
            <div class="container-fluid cl"> 
                <a href="<%=basePath%>index" class="logo navbar-logo f-l mr-10 hidden-xs" style="font-family: LiSu; font-size: 30px">客戶訂單管理系統</a>
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                    <ul class="cl">
                        <li>今天是</li>
                        <li>&nbsp;&nbsp;&nbsp;</li>
                        <li id="timeday" style=" color: red"></li>
                        <li>&nbsp;&nbsp;&nbsp;</li>
			<li id="time"></li>                        
			<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A" style="color: red">${user.userName}</a>
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
                            
    <!--系统菜单目录-->                       
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
                </li>
            </ul>
            <ul>
                <li>
                    <a href="<%=basePath%>Product/ProductQuery">產品管理</a>
                </li>
            </ul>
        </div>
    </aside>
                
        <!--管理员信息-->
    <div id="userInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h3 id="myModalLabel">個人資訊</h3>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="" class="form form-horizontal responsive">
            	<div class="row cl">
                    <label class="form-label col-xs-3">用戶編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text" readonly="true" autocomplete="off" value="${user.userId}" name="username" />
                    </div>
		</div>
		<div class="row cl">
                    <label class="form-label col-xs-3">用戶姓名：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text" readonly="true" autocomplete="off"  value="${user.userName}" name="password" />
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
    
    <!--菜单缩进--> 
    <div class="dislpayArrow">
        <a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
    </div>
    
    <!--客户单价主体-->
    <section class="Hui-article-box" id = "customerPriceSection">
        <div class="page-container">
            <form id = "queryCustomerPriceForm" action="queryCustomerPrice" method="post">
                <h3 align="center">客戶產品單價管理</h3>
                <br/>
                <p align="center">
                    客戶姓名： <input type="text" id="customer_name_input" name="customerName" value="${queryCondition.customerName}" autocomplete="off" class="input-text radius" style="width:85px" list="customer_list" />
                    <datalist id="customer_list"> 
                    </datalist>
                    產品名稱： <input type="text" id="product_name_input" name="productName" value="${queryCondition.productName}" autocomplete="off" class="input-text radius" style="width:85px" list="product_list"/>
                    <datalist id="product_list"> 
                    </datalist>
                    數量： <input type="text" id="range_min_input" list="rangeMin_list" name="rangeMin" value="${queryCondition.rangeMin}" autocomplete="off" class="input-text radius" style="width:85px" id="numStart"/>
                    <datalist id="rangeMin_list"> 
                    </datalist>
                    ~<input type="text" id="range_max_input" list="rangeMax_list" name="rangeMax" value="${queryCondition.rangeMax}" autocomplete="off" class="input-text radius" style="width:85px" id="numEnd"/>
                    <datalist id="rangeMax_list">  
                    </datalist>
                    價格： <input type="text" id="price_min_input" list="priceMin_list" name="priceMin" value="${queryCondition.priceMin}" autocomplete="off" class="input-text radius" style="width:85px" id="priceStart"/>
                    <datalist id="priceMin_list"> 
                    </datalist>
                    ~<input type="text" id="price_max_input" list="priceMax_list" name="priceMax" value="${queryCondition.priceMax}" autocomplete="off" class="input-text radius" style="width:85px" id="priceEnd"/>
                    <datalist id="priceMax_list"> 
                    </datalist>
                    <input class="btn btn-primary radius"  type="submit" value="查询" id="query_Cust_Price"/>
                    <a class="btn btn-default radius" type="button" id="custPriceEditBotton" data-toggle="modal" style="display: inline-block;" >修改</a>    
                </p>
            </form>
            <div id="CusPriceTableDiv">
            <table class="table table-border table-bordered table-hover" id="CusPriceTable" >
                <tr>
                    <th style="width:100px">客戶產品單價編號</th>  
                    <th style="width:100px">客戶編號</th>  
                    <th style="width:100px">客戶姓名</th> 
                    <th style="width:100px">產品編號</th>  
                    <th style="width:100px">產品名稱</th>   
                    <th style="width:100px">數量區間</th>    
                    <th style="width:100px">價格</th>
                </tr>
            <c:forEach items="${customerPriceList}" var ="customerPrice">
                <tr style="height: 38px">
                    <td style="width:100px">${customerPrice.customerPriceId}</td>
                    <td style="width:100px"><c:out value="${customerPrice.customerMasterId.customerId}"></c:out></td>
                    <td style="width:100px"><c:out value="${customerPrice.customerMasterId.customerName}"></c:out></td>
                    <td style="width:100px"><c:out value="${customerPrice.productMasterId.productId}"></c:out></td> 
                    <td style="width:100px"><c:out value="${customerPrice.productMasterId.productName}"></c:out></td>
                    <td style="width:100px"><c:out value="${customerPrice.rangeMin} ~ ${customerPrice.rangeMax}"></c:out></td>
                    <td style="width:100px"><c:out value="${customerPrice.rangePrice}"></c:out></td>
<!--                <td style="width:100px"><fmt:formatNumber type="number" value="${custPrice.rangePrice}" pattern="0.000" maxFractionDigits="3"/></td>-->
                </tr>
             </c:forEach> 
            </table>
            <p>${message}</p>
        </div>
        <br/>
        <div id="page" align="center">
            <p> <input type="button" class="btn btn-default radius" value="上一頁" onclick="getPageData(this)"/>
            <input class="input-text radius" type="text" id="pageNo" value="1" readonly="true" style="width:30px" />/
            <input class="input-text radius" type="text" id="totalPage" value="${totalPage}" readonly="true" style="width:30px" />
            <input type="button" class="btn btn-default radius" value="下一頁" onclick="getPageData(this)"/>
            <!--<a>总计${dataCount}条数据</a>-->
            </p>
        </div>
    </div>    
            
    <div id="updateCustomerPrice" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h2 id="myModalLabel" align="center"><small>修改客戶產品單價</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="" method="post">
                    <div class="formControls col-xs-4">
                        客戶產品單價編號：<input type="text" name="customerPriceId"  class="input-text disabled radius" readonly="true" id="customerPriceId_update"/>
                    </div>
                    <div class="formControls col-xs-4">
                        客戶姓名：<input type="text" name="customerName"  class="input-text disabled radius" readonly="true" id="customerName_update"/>
                    </div>
                    <div class="formControls col-xs-4">
                        產品名稱：<input type="text" name="productName"  class="input-text disabled radius" readonly="true" id="productName_update"/>         
                    </div>
                    <div class="formControls col-xs-4">
                        數量起量：<input type="text" name="rangeMin"  class="input-text disabled radius" readonly="true" id="rangeMin_update"/>
                    </div>
                    <div class="formControls col-xs-4">
                        數量終量：<input type="text" name="rangeMax"  class="input-text disabled radius" readonly="true" id="rangeMax_update"/>
                    </div>
                    <div class="formControls col-xs-4">
                        當前價格：<input type="text" name="presentPrice"  class="input-text disabled radius" readonly="true" id="presentPrice_update"/>
                    </div>
                    <div class="formControls col-xs-4" id="price">
                        修改價格：<input type="text" id="updatedPrice" class="input-text radius" name="editPrice"/>
                        <input type="text" hidden="true" readonly="true" id="cusPriceMasterId_update"/>
                        <input type="text" hidden="true" readonly="true" id="versionNumber_update"/>
                    </div>
                <br/>
                <div class="formControls col-xs-5 right" >
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;</label>
                    
                    <input type="button" class="btn btn-primary radius" onclick="updateCustomerPrice()" value="确定"></input>
                    <input type="button" class="btn btn-primary radius" data-dismiss="modal" aria-hidden="true" value="取消"></input>
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;</label>
                </div>
                <br/>
                <br/>
            </form>     
            <br></br>
        </div>
        </div>        
</section>
            
    <!--彈出消息-->
    <div id="modal-message" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
            <div class="modal-content radius">
            <div class="modal-body">
                <p id="alter_message"></p>
            </div>
            </div>
	</div>
    </div>

<script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/jquery/jquery.form.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/commonAction.js"></script>
<script>
    //獲取當前使用者的session信息
    $(function (){
       var session = "<%=(User)session.getAttribute("user")%>"; 
       hello(session);
    });
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：將查詢到的客戶資訊顯示在表格
     * 
     ********************************************************************************/
    $(document).ready(function() { 
        $('#queryCustomerPriceForm').ajaxForm({ 
            success:function (data){
               var html =  data.split("<div id=\"CusPriceTableDiv\">")[1];
               var html_table = html.split("</div>")[0];
               var html_page = html.split("<div id=\"page\" align=\"center\">")[1].split("</div>")[0];
               $("#page").html(html_page);
                $("#CusPriceTableDiv").html(html_table);
                $('#custPriceEditBotton').removeAttr('href');
                $("#custPriceEditBotton").removeClass("btn btn-primary radius");
                $("#custPriceEditBotton").addClass("btn btn-default radius");
                
                $("tr").click(function(){
                $(this).find("td").each(function(){
                    var text = $(this).text();
                    if(/CUSPRO[0-9]{6}/g.test(text)){
                        $("tr").each(function (){
                        $(this).removeClass("success");
                    })
                    $(this).parents("tr").attr('class',"success");
                    $('#custPriceEditBotton').attr('href',"#updateCustomerPrice");//添加标签中的href属
                    $("#custPriceEditBotton").removeClass("btn btn-default radius");
                    $("#custPriceEditBotton").addClass("btn btn-primary radius");
                     customerPrice[0] = text;
                    }
                    return false;
                });
            });
            },
            error:function (data){
                try {
                    var response = JSON.parse(data.responseText.toString());
                    $("#alter_message").html(response.message);
                    $("#modal-message").modal("show");
                    setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                }
                catch(e){
                    var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                    $("#alter_message").html(message);
                    $("#modal-message").modal("show");
                    setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                }
            }
         }); 
    })
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：客戶、產品輸入框獲得焦點時查詢相應資訊列表
     * 
     ********************************************************************************/
    /*$("#customer_name_input,#product_name_input").focus(function (){
        
        var len = this.list.options.length;
        var inputId = this.id;
        if(len === 0){
            $.ajax({  
                url : "getCustomerAndProductList",  
                type : "post",  
                datatype:"json",  
                 data:{inputId:inputId},
                success : function(data) { 
                   var list = data.data;
                   if(inputId === "customer_name_input"){
                        $("#customer_list").html(null);
                        for(var i = 0; i < list.length; i++){
                            $("#customer_list").append('<option value="' + list[i] + '"></option>');
                        }
                   }
                   if(inputId === "product_name_input"){
                       $("#product_list").html(null);                
                       for(var i = 0; i < list.length; i++){
                            $("#product_list").append('<option value="' + list[i] + '"></option>');
                        }
                   }  
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",4500);
                }  
            });
        }
    });*/
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：客戶、產品輸入框輸入值時，根據值查詢列表
     * 
     ********************************************************************************/
    $("#customer_name_input,#product_name_input").bind('input propertychange focus', function() {

        var customerName = $("#customer_name_input").val();
        var productName = $("#product_name_input").val();
        var inputId = this.id;
        $.ajax({  
                url : "getCustomerAndProductList",  
                type : "post",  
                datatype:"json",  
                data:{inputId:inputId,customerName:customerName,productName:productName},
                success : function(data) { 
                   var list = data.data;
                   if(list !== null){
                       if(inputId === "customer_name_input"){
                            $("#customer_list").html(null);
                            for(var i = 0; i < list.length; i++){
                                $("#customer_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                       if(inputId === "product_name_input"){
                            $("#product_list").html(null);                
                            for(var i = 0; i < list.length; i++){
                                $("#product_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                   }
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        throw new Error(response.message);
                        //$("#alter_message").html(response.message);
                        //$("#modal-message").modal("show");
                        //setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        throw new Error(message);
                        //$("#alter_message").html(message);
                        //$("#modal-message").modal("show");
                        //setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                }  
            });
    });
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：數量級、價格輸入框輸入值時，根據值查詢列表
     * 
     ********************************************************************************/
    $("#range_min_input,#range_max_input,#price_min_input").bind('input propertychange focus', function() {

        var customerName = $("#customer_name_input").val();
        var productName = $("#product_name_input").val();
        var rangeMin = $("#range_min_input").val();
        var rangeMax = $("#range_max_input").val();
        var priceMin = $("#price_min_input").val();
        var inputId = this.id;
        
        $.ajax({  
                url : "getRangesAndPriceList",  
                type : "post",  
                datatype:"json",  
                data:{inputId:inputId,customerName:customerName,productName:productName,rangeMin:rangeMin,rangeMax:rangeMax,priceMin:priceMin},
                success : function(data) { 
                  var list = data.data;
                  if(list !== null){
                      if(inputId === "range_min_input"){
                            $("#rangeMin_list").html(null);
                            for(var i = 0; i < list.length; i++){
                                $("#rangeMin_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                        if(inputId === "range_max_input"){
                            $("#rangeMax_list").html(null);
                            for(var i = 0; i < list.length; i++){
                                $("#rangeMax_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                        if(inputId === "price_min_input"){
                            $("#priceMin_list").html(null);
                            $("#priceMax_list").html(null);
                            for(var i = 0; i < list.length; i++){
                                $("#priceMin_list").append('<option value="' + list[i] + '"></option>');
                                $("#priceMax_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                  }
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        throw new Error(response.message);
                        //$("#alter_message").html(response.message);
                        //$("#modal-message").modal("show");
                        //setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        throw new Error(message);
                        //$("#alter_message").html(message);
                        //$("#modal-message").modal("show");
                        //setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                }  
            });
    });
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢某一頁的的資訊
     * 
     ********************************************************************************/
    function getPageData(input){
        var pageNo = parseInt($("#pageNo").val());
        var totalPage = parseInt($("#totalPage").val());
        
        if(input.value === "下一頁"){
            if(pageNo >= totalPage){
                return false;
            }else{
                $("#pageNo").val(pageNo + 1);
                pageNo_ = pageNo;
            }
        }else if(input.value === "上一頁"){
            if(pageNo <= 1){
                return false;
            }else{
                $("#pageNo").val(pageNo - 1);
                pageNo_ = pageNo - 2;
            }
        }

       $.ajax({  
                url : "getPageData",  
                type : "get",  
                datatype:"json",  
                data : {pageNo:pageNo_},  
                success : function(data) {  
  
                    $("#CusPriceTable").find("tr").each(function(){
                        $(this).removeClass("success");
                        $('#custPriceEditBotton').removeAttr('href');
                        $("#custPriceEditBotton").removeClass("btn btn-primary radius");
                        $("#custPriceEditBotton").addClass("btn btn-default radius");
                        $(this).find("td").each(function(){
                            $(this).html(null);
                        });
                    });
                    try {
                        var i = -1;
                        $("#CusPriceTable").find("tr").each(function(){
                            var j = 0;
                            $(this).find("td").each(function(){
                                $(this).html(data[i][j]);
                                if(j === 6){
                                    $(this).html(parseFloat(data[i][j]).toFixed(1));
                                }
                                j++;
                            });
                            i++;
                        });
                    } catch (e) {}
                },  
                error : function(data) {  
                    $("#pageNo").val(pageNo);
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                }  
            });
        }
        
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取要修改的客戶產品單價資訊
     * 
     ********************************************************************************/
    $("#updateCustomerPrice").bind('show', function() {
        getCustomerPriceForUpdate();
    });
    $("#updateCustomerPrice").bind("hide",function (){
        $("#presentPrice_update").val(null);
    });
    function getCustomerPriceForUpdate(){
        
        var customerPriceId =  customerPrice[0];
        
        $.ajax({  
                url : "getCustomerPriceForUpdate",  
                type : "post",  
                datatype:"json",  
                data:{customerPriceId:customerPriceId},
                success : function(data) { 
                    $("#customerPriceId_update").val(data.data.customerPriceId);
                    $("#customerName_update").val(data.data.customerMasterId.customerName);
                    $("#productName_update").val(data.data.productMasterId.productName);
                    $("#rangeMin_update").val(data.data.rangeMin);
                    $("#rangeMax_update").val(data.data.rangeMax);
                    $("#presentPrice_update").val(parseFloat(data.data.rangePrice).toFixed(1));
                    
                    $("#versionNumber_update").val(data.data.versionNumber);
                    $("#cusPriceMasterId_update").val(data.data.cusPriceMasterId);
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                        $("#modal-message").bind("hide",function (){
                            if(/[請重試|不存在]{3}/.test(response.message)){
                                window.location = "<%=basePath%>CustomerPriceManage/queryCustomerPrice";
                            }
                        });
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                }  
            });
    }
    
     /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：修改客戶產品單價資訊
     * 
     ********************************************************************************/
    function updateCustomerPrice(){
        var customerPriceId = $("#customerPriceId_update").val();
        //$("#customerName_update").val();
        //$("#productName_update").val();
        var rangeMin = $("#rangeMin_update").val();
        var rangeMax = $("#rangeMax_update").val();
        var rangePrice = $("#updatedPrice").val(); 
        var versionNumber = $("#versionNumber_update").val();
        var cusPriceMasterId = $("#cusPriceMasterId_update").val();
        
        $.ajax({  
                url : "updateCustomerPrice",  
                type : "post",  
                datatype:"json",  
                data : {customerPriceId:customerPriceId,rangeMin:rangeMin,rangeMax:rangeMax,rangePrice:rangePrice,versionNumber:versionNumber,cusPriceMasterId:cusPriceMasterId},  
                success : function(data) {  

                    $("#updateCustomerPrice").modal("hide");
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("show");
                    setTimeout("$(\"#modal-message\").modal(\"hide\")",3000);
                    
                    var customerPrice = new Array(data.data.customerPriceId,data.data.customerMasterId.customerId,data.data.customerMasterId.customerName,
                                                  data.data.productMasterId.productId,data.data.productMasterId.productName,data.data.rangeMin +"~"+data.data.rangeMax,
                                                  data.data.rangePrice);
                    
                    $("#CusPriceTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            if($(this).html() === data.data.customerPriceId){
                                var i = 0;
                                $(this).parent("tr").find("td").each(function (){
                                    $(this).html(customerPrice[i]);
                                    if(i === 6){
                                        $(this).html(parseFloat(customerPrice[i]).toFixed(1));
                                    }
                                    i++;
                                })
                                return ;
                            }
                        });
                        });
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                         $("#modal-message").bind("hide",function (){
                            if(/[請重試|不存在]{3}/.test(response.message)){
                                window.location = "<%=basePath%>CustomerPriceManage/queryCustomerPrice";
                            }
                        });
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",4500);
                    }
                }  
            });
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：按鈕置灰
     * 
     ********************************************************************************/
    var customerPrice = [];
    $("tr").click(function(){
        
        $(this).find("td").each(function(){
            var text = $(this).text();
            if(/CUSPRO[0-9]{6}/g.test(text)){
                $("tr").each(function (){
                $(this).removeClass("success");
                $('#custPriceEditBotton').attr('href',"#updateCustomerPrice");//添加标签中的href属
                $("#custPriceEditBotton").removeClass("btn btn-default radius");
                $("#custPriceEditBotton").addClass("btn btn-primary radius");
            });
            $(this).parents("tr").attr('class',"success");
            customerPrice[0] = text;
            }
            return false;
        });
    });

// /**
//    * 以下3個函數，分別是獲取當前時間信息並顯示，顯示問候語和修改密碼函數，在每個頁面都是有的
//    *  現在放在了commonAction.js裏面了
//    */      
//    $(function () {
//                var d = new Date();
//                var week;
//
//                function add_zero(temp) {
//                    if (temp < 10)
//                        return "0" + temp;
//                    else
//                        return temp;
//                }
//
//                switch (d.getDay()) {
//                    case 1:
//                        week = "星期一";
//                        break;
//                    case 2:
//                        week = "星期二";
//                        break;
//                    case 3:
//                        week = "星期三";
//                        break;
//                    case 4:
//                        week = "星期四";
//                        break;
//                    case 5:
//                        week = "星期五";
//                        break;
//                    case 6:
//                        week = "星期六";
//                        break;
//                    default:
//                        week = "星期天";
//                }
//                var years = d.getFullYear();
//                var month = add_zero(d.getMonth() + 1);
//                var days = add_zero(d.getDate());
//                var hours = add_zero(d.getHours());
//                var minutes = add_zero(d.getMinutes());
//                var seconds = add_zero(d.getSeconds());
//                var ndate = "現在是  " + years + "年" + month + "月" + days + "日 " + hours + ":" + minutes + ":" + seconds + " " + week;
//                var ndate2 = years + "年" + month + "月" + days + "日 "  + " " + week+"  ";
//
//                $("#timeday")[0].innerHTML = ndate2;
//
//            });
//            
//            $(function () {
//
//            var d = new Date();
//            var h = parseInt(d.getHours());
//
//            var str = "上午好！";
//
//            if (h > 12 && h < 18) {
//                str = "下午好！";
//            }
//            if (h > 18 && h < 24) {
//                str = "晚上好！";
//            }
//
//            $("#time")[0].innerHTML = str;
//            var session = "<%=(User) session.getAttribute("user")%>";
//            if (session === null) {
//                window.location = "<%=basePath%>login";
//            }
//        });
//        
//        function editPassword() {
//
//            var userPassOld = $("#userPassOld")[0].value;
//            var userPassNew = $("#userPassNew")[0].value;
//            var userPassConfirm = $("#userPassConfirm")[0].value;
//
//            $.ajax({
//                url: "<%=basePath%>login/editPassword",
//                type: "post",
//                datatype: "json",
//                data: {userPassOld: userPassOld, userPassNew: userPassNew, userPassConfirm: userPassConfirm},
//                success: function (data, stats) {
//                    if (stats === "success") {
//                        if (data === "success") {
//                            $("#passwordEdit").modal("hide");
//                            alert("密碼修改成功！");
//                            window.location = "<%=basePath%>login";
//                        } else if (data === "confirm error") {
//                            $("#message").html("兩次輸入的密碼不一致！");
//                        } else if (data === "same old new") {
//                            $("#message").html("新舊密碼不能一樣！");
//                        } else {
//                            $("#message").html("原密碼輸入錯誤！");
//                        }
//                    }
//                },
//                error: function (data) {
//                    alert("密碼修改失败！");
//                }
//            });
//
//        }


</script>
</body>
</html>