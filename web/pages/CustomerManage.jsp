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


<link rel="shortcut icon"type="image/x-icon" href="<%=basePath%>pages/image/24pxnet.ico" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui/css/H-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/skin/default/skin.css" id="skin" />

<title>客户订单管理系统</title>
<!--设置table行点击背景颜色-->
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
            <div class="container-fluid cl"> <a href="<%=basePath%>index" class="logo navbar-logo f-l mr-10 hidden-xs" style="font-family: LiSu; font-size: 30px">客戶訂單管理系統</a>
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
    
    <!--客户信息主体-->     
    <section class="Hui-article-box">
        <div class="page-container">
            <form action="CustomerQuery" method="post">
                <h3 align="center">客戶管理</h3>
                <br/>
                <p align="center">
                    客戶編號：<input type="text" id="customer_idMin" list="customer_idMin_list" name="customerIdMin" value="${queryCondition.customerIdMin}" autocomplete="off" class="input-text radius" style="width:150px" />
                   <datalist id="customer_idMin_list"> 
                    </datalist>
                    ~<input type="text" id="customer_idMax" name="customerIdMax" list="customer_idMax_list" value="${queryCondition.customerIdMax}" autocomplete="off" class="input-text radius" style="width:150px" />
                    <datalist id="customer_idMax_list"> 
                    </datalist>
                    客戶姓名：<input type="text" id="customer_name_input" list="customer_name_list" name="customerName" value="${queryCondition.customerName}" autocomplete="off" class="input-text radius" style="width:150px" />
                    <datalist id="customer_name_list"> 
                    </datalist>
                    <input class="btn btn-primary radius"  type="submit" value="查詢"/>
                    <a class="btn btn-primary radius" data-toggle="modal" href="#addCustomer">新增</a>
                    <a class="btn btn-default radius" data-toggle="modal"  id="custEditButton" onclick="getCustomerForUpdate()">修改</a>
                    <a data-toggle="modal" class="btn btn-default radius" id="custDelButton">刪除</a>
                </p>
            </form>
            <table class="table table-border table-bordered table-hover" id="customerTable">      
            <tr>
                <th style="width:100px">客戶編號</th>
                <th style="width:100px">客戶姓名</th> 
                <th style="width:100px">客戶郵箱</th>  
                <th style="width:100px">客戶電話</th>   
            </tr>
            <c:forEach items="${customerList}" var ="customer">
                <tr style=" height: 38px">
                    <td style="width:100px"><c:out value="${customer.customerId}"></c:out></td>
                    <td hidden="true" ><c:out value="${customer.versionNumber}"></c:out></td>
                    <td style="width:100px"><c:out value="${customer.customerName}"></c:out></td>
                    <td style="width:100px"><c:out value="${customer.customerMail}"></c:out></td> 
                    <td style="width:100px"><c:out value="${customer.customerPhone}"></c:out></td>
                </tr>
            </c:forEach> 
        </table>
        <p>${message}</p>
        <br/>
        <div align="center">
            <p> <input type="button" value="上一頁" class="btn btn-default radius" onclick="getPageData(this)"/>
            <input class="input-text radius" type="text" id="pageNo" value="1" readonly="true" style="width:30px" />/
            <input class="input-text radius" type="text" id="totalPage" value="${totalPage}" readonly="true" style="width:30px" />
            <input type="button" value="下一頁" class="btn btn-default radius" onclick="getPageData(this)"/>
            </p>
        </div>
    </div>
</section>
            
    <!--删除客户--> 
     <div id="deleteInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel">信息提示！</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <h4 id="myModalLabel" align="center">確定刪除該客戶？</h4>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="deleteCustomer()">確定</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        </div>
    </div>
    
    <!--新增客户-->
    <div id="addCustomer" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>新增客戶信息</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
           <form action="addCustomer" method="post">
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶姓名：</label>
                    <div class="formControls col-xs-5" id="a1">
                        <input type="text" name="customerName" id="customer_name_add" autocomplete="off" class="input-text radius"/>
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶郵箱：</label>
                    <div class="formControls col-xs-5" id="a2">
                        <input type="text" name="customerMail" id="customer_mail_add" autocomplete="off" class="input-text radius"/>
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶電話：</label>
                    <div class="formControls col-xs-5" id="a3">
                        <input type="text" name="customerPhone" id="customer_phone_add" autocomplete="off" class="input-text radius"/>
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" id="send" type="button" value="確定" onclick="addCustomer()"/>
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true"/>
                    </div>
                </div>
           </form>
        </div>
    </div>
    
    <!--新增客户产品单价-->
    <div id="addCustomerPrice" class="modal1 hide fade" backdrop="false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>管理客戶產品單價資訊</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            
            <table class="table">      
            <tr>
                <td>
                <input type="text" name="customerId" id="customer_id" readonly="true" hidden="true" />
                <label >產&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品：</label>
                <input type="text" name="productId" id="product_id" autocomplete="off" class="input-text radius" list="product_list" style="width:80%" />
                    <datalist id="product_list"> 
                    </datalist>
                </td>
                <td >
                <label>當前價格：</label>
                <input type="text" name="productPrice" id="product_price"  readonly="true" class="input-text disabled radius" style="width:40%" />
                </td>  
            </tr>
            <tr >
                <td>
                <label>優惠區間：</label>
                <input type="text" name="rangeMin" id="range_min" autocomplete="off" class="input-text radius" id="preferentialCredit" placeholder="輸入優惠區間" style="width:40%"/>
                <input type="text" name="rangeMax" id="range_max" autocomplete="off" class="input-text radius" id="preferentialCredit" placeholder="輸入優惠區間" style="width:40%"/>
                </td>
                <td >
                <label>優惠價格：</label>
                <input type="text" name="rangePrice" id="range_price" autocomplete="off" class="input-text radius" id="preferentialCredit" placeholder="输入优惠额度" style="width:40%"/>
                </td>
            </tr>
            <tr >
                <td style="width:100px"></td>
                <td style="width:100px" class="text-c">
                    <input type="button" class="btn btn-default" value="添加" onclick="addCustomerPrice()" />
                </td>
            </tr>
        </table>
            <br/>
        <table id="cutomerPriceTable"  class="table table-border table-bordered table-striped">
            <tr>
            <th>客户</th><th>产品</th><th>优惠区间</th><th>优惠價格</th><th>操作</th>
            </tr>
            <tr height="48px"><td hidden="true"></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td hidden="true"></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td hidden="true"></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td hidden="true"></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td hidden="true"></td><td></td><td></td><td></td><td></td><td></td></tr>
	</table>
            <p align="right"> <input type="button" class="btn btn-default radius" value="上一頁" id="pre_cp" />
            <input class="radius" type="text" id="pageNo_cp" value="1" readonly="true" style="width:30px" />/
            <input class="radius" type="text" id="totalPage_cp" value="1"  readonly="true" style="width:30px" />
            <input type="button" class="btn btn-default radius" value="下一頁" id="next_cp" />
            </p>
        </div>   
    </div>
    
    <!--修改客户-->
    <div id="updateCustomer" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>修改客戶資訊</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body" id="CustEditBody">
           <form action="CustEdit" method="post">
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶編號：</label>
                    <div class="formControls col-xs-5">
                         <input type="text" name="customerId" readonly="true" id="customer_id_update" class="input-text disabled radius" />
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶姓名：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" name="customerName" id="customer_name_update" readonly="true" class="input-text disabled  radius" />
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶郵箱：</label>
                    <div class="formControls col-xs-5" id="b1">
                          <input type="text" name="customerMail" id="customer_mail_updatet" class="input-text radius" />
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <label class="form-label col-xs-3">客戶電話：</label>
                    <div class="formControls col-xs-5" id="b2">
                        <input type="text" name="customerPhone" id="customer_phone_update" class="input-text radius" />
                    </div>
		</div>
                <br/>
                <div class="row cl">
                    <input type="text" name="versionNumber" readonly="true" hidden="true" id="version_number_update" class="input-text radius" />
                    <input type="text" name="customerMasterId" readonly="true" hidden="true" id="customer_master_id_update" class="input-text radius" />
		</div>
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <a class="btn btn-default radius" data-toggle="modal" id="addCustomerPriceButton">管理客戶產品單價</a>
                        <input class="btn btn-primary radius" type="button" onclick="updateCustomer()" value="確定">
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
           </form>
        </div>
    </div>
    
         
    
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
     * 功能簡述：客戶名稱輸入框輸入值時，根據值查詢列表
     * 
     ********************************************************************************/
    $("#customer_name_input,#customer_idMin,#customer_idMax").bind('input propertychange focus', function() {

        var customerName = $("#customer_name_input").val();
        var customerIdMin = $("#customer_idMin").val();
        var customerIdMax = $("#customer_idMax").val();
        var inputId = this.id;
        $.ajax({  
                url : "getCustomerIdAndCustomerNameList",  
                type : "post",  
                datatype:"json",  
                data:{inputId:inputId,customerName:customerName,customerIdMin:customerIdMin, customerIdMax:customerIdMax},
                success : function(data) { 
                   var list = data.data;
                   if(list !== null){
                       if(inputId === "customer_name_input"){
                            $("#customer_name_list").html(null);
                            for(var i = 0; i < list.length; i++){
                                $("#customer_name_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                       if(inputId === "customer_idMin"){
                            $("#customer_idMin_list").html(null);                
                            for(var i = 0; i < list.length; i++){
                                $("#customer_idMin_list").append('<option value="' + list[i] + '"></option>');
                            }
                        }
                        if(inputId === "customer_idMax"){
                            $("#customer_idMax_list").html(null);                
                            for(var i = 0; i < list.length; i++){
                                $("#customer_idMax_list").append('<option value="' + list[i] + '"></option>');
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
                        //setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        throw new Error(message);
                        //$("#alter_message").html(message);
                        //$("#modal-message").modal("show");
                        //setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    });
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢某一頁的的資訊
     * 
     ********************************************************************************/
    function getPageData(button){
        var pageNo = parseInt($("#pageNo").val());
        var pageNo_ = "";
        var totalPage = parseInt($("#totalPage").val());
        
        if(button.value === "下一頁"){
            if(pageNo >= totalPage){
                return false;
            }else{
                $("#pageNo").val(pageNo + 1);
                pageNo_ = pageNo;
            }
        }else if(button.value === "上一頁"){
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
  
                    if(data.length < 10){
                    $("#customerTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this).html(null);
                        });
                    })
                    }
                    var i = -1;
                    $("#customerTable").find("tr").each(function(){
                        var j = 0;
                        $(this).find("td").each(function(){
                            $(this).html(data[i][j]);
                            j++;
                        });
                        i++;
                    });
                },  
                error : function(data) {  
                    $("#pageNo").val(pageNo);
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
        }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：新增客戶
     * 
     ********************************************************************************/
    function addCustomer(){
        var customerName = $("#customer_name_add").val();
        var customerMail = $("#customer_mail_add").val();
        var customerPhone = $("#customer_phone_add").val();
        
        $.ajax({  
                url : "addCustomer",  
                type : "post",  
                datatype:"json",  
                data : {customerName:customerName,customerMail:customerMail,customerPhone:customerPhone},  
                success : function(data) { 
                    $("#addCustomer").modal("toggle");
                    
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("show");
                    setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    $("#customer_id").val(data.data.customerId);
                    setTimeout("$(\"#addCustomerPrice\").modal(\"show\")",5000); 
                    $("#addCustomerPrice").bind('hide', function() {
                        window.location = "<%=basePath%>CustomerManage/CustomerQuery";
                    });
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取客戶產品單價資訊列表
     * 
     ********************************************************************************/
    $("#addCustomerPrice").bind('show', function() {
        getCustomerPriceList(this.id,isCross);
    });
    $("#pre_cp,#next_cp").bind('click', function() {
        getCustomerPriceList(this.id,isCross);
    });
    function getCustomerPriceList(value,isCross){
        var customerId = $("#customer_id").val();
        var pageNo = parseInt($("#pageNo_cp").val());
        var pageNo_ = "";
        var totalPage = parseInt($("#totalPage_cp").val());
        var productId = $("#product_id").val();
        var rangeMin = $("#range_min").val();
        var rangeMax = $("#range_max").val();
        if(customerId === ""){
            customerId = customerInfo[0];
            $("#customer_id").val(customerId);
        } 
        
        if(value === "pre_cp"){
            if(pageNo <= 1) return ;
            pageNo_ = pageNo - 2;
            $("#pageNo_cp").val(pageNo - 1);
        }
        if(value === "next_cp"){
           if(pageNo >= totalPage) return ;
           pageNo_ = pageNo;
           $("#pageNo_cp").val(pageNo + 1);
        }
        if(value === "addCustomerPrice"){
            if(pageNo < 1) return ;
            pageNo_ = pageNo - 1;
        }
        
        $.ajax({  
                url : "<%=basePath%>CustomerPriceManage/queryCustomerPriceByCustomerId",  
                type : "post",  
                datatype:"json",  
                data:{customerId:customerId,pageNo:pageNo_,isCross:isCross,productId:productId,rangeMin:rangeMin,rangeMax:rangeMax},
                success : function(data) {
                    
                    $("#totalPage_cp").val(data.count);
                                  
                    var customerPriceList = data.data;
                    if(customerPriceList.length === 0){
                        if(pageNo > 1){
                            $("#pageNo_cp").val(pageNo - 1);
                            getCustomerPriceList("addCustomerPrice",isCross);
                        }
                        else if(isCross === true){
                            isCross = false;
                            getCustomerPriceList("addCustomerPrice",isCross);
                        }
                        return;
                    }
                    
                    $("#cutomerPriceTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this).html(null);
                        });
                    });
                    
                    var i = -1;
                    $("#cutomerPriceTable").find("tr").each(function(){
                        var j = 0;
                        $(this).find("td").each(function(){
                            $(this).html(customerPriceList[i][j]);
                            if(j === 5){
                                $(this).html("<input type=\"button\" id=\"delete_cp\" class=\"btn btn-default radius\" onclick=\"deleteCustomerPrice(this)\" value=\"删除\"/>");
                            }
                            j++;
                        });
                        i++;
                    });
                    
                },  
                error : function(data) { 
                    $("#pageNo_cp").val(pageNo);
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：客戶產品單價新增彈窗框隱藏時清空相關資訊
     * 
     ********************************************************************************/
    $("#addCustomerPrice").bind('hide', function() {
        isCross = false;
        $("#customer_id").val(null);
        $("#product_id").val(null);
        $("#product_price").val(null);
        $("#range_min").val(null);
        $("#range_max").val(null);
        $("#range_price").val(null);
        $("#cutomerPriceTable").find("tr").each(function(){
           $(this).find("td").each(function(){
                 $(this).html(null);
            });
        });
        $("#pageNo_cp").val(1);
        $("#totalPage_cp").val(1);
    });
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取產品列表
     * 
     ********************************************************************************/
    var productList;
    $("#product_id").bind('input propertychange focus', function() {
        var productId = $("#product_id").val();
        $.ajax({  
                url : "getProductList",  
                type : "post",  
                datatype:"json",  
                data:{productId:productId},
                success : function(data) { 
                    productList = data.data;
                    $("#product_list").html(null);
                    $("#product_price").val(null);
                    for(var i = 0; i < productList.length; i++){
                        $("#product_list").append('<option label="' + productList[i].productName  + '" value="' + productList[i].productId + '"></option>');
                    }
                    if(productList.length === 1){
                        $("#product_price").val(productList[0].productStandardPrice);
                    }
  
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    });

    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：新增客戶產品單價資訊
     * 
     ********************************************************************************/
     var isCross = false;
     function addCustomerPrice(){
        var customerId = $("#customer_id").val();
        var productId = $("#product_id").val();
        var productPrice = $("#product_price").val();
        var rangeMin = $("#range_min").val();
        var rangeMax = $("#range_max").val();
        var rangePrice = $("#range_price").val();
        
        $.ajax({  
                url : "<%=basePath%>CustomerPriceManage/addCustomerPrice",  
                type : "post",  
                datatype:"json",  
                data : {customerId:customerId,productId:productId,productPrice:productPrice,rangeMin:rangeMin,rangeMax:rangeMax,rangePrice:rangePrice},  
                success : function(data) {
                    
                    isCross = false;
                    $("#pageNo_cp").val(1);
                    $("#totalPage_cp").val(data.count);
                    $("#product_id").val(null);
                    $("#product_price").val(null);
                    $("#range_min").val(null);
                    $("#range_max").val(null);
                    $("#range_price").val(null);
                    
                    var customerPriceList = data.data;
                    if(customerPriceList.length === 0){
                        return;
                    }
                    $("#cutomerPriceTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this).html(null);
                        });
                    });
                    var i = -1;
                    $("#cutomerPriceTable").find("tr").each(function(){
                        var j = 0;
                        $(this).find("td").each(function(){
                            $(this).html(customerPriceList[i][j]);
                             if(j === 5){
                               $(this).html("<input type=\"button\" id=\"delete_cp\" class=\"btn btn-default radius\" onclick=\"deleteCustomerPrice(this)\" value=\"删除\"/>");
                            }
                            j++;
                        });
                        i++;
                    });
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                        if(/[交|叉|區|域]{4}/.test(response.message)){
                            isCross = true;
                            $("#pageNo_cp").val(1);
                            getCustomerPriceList("addCustomerPrice",isCross);
                        }
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：刪除客戶產品單價資訊
     * 
     ********************************************************************************/
    function deleteCustomerPrice(button){
        var customerPrice = $(button).parent().parent().children("td").get(0).innerHTML.split(":");
        var customerPriceId = customerPrice[0];
        var versionNumber = customerPrice[1];
        $.ajax({  
                url : "<%=basePath%>CustomerPriceManage/deleteCustomerPrice",  
                type : "post",  
                datatype:"json",  
                data:{customerPriceId:customerPriceId,versionNumber:versionNumber},
                success : function(data) { 
                    
                    if(isCross === true){
                        getCustomerPriceList("addCustomerPrice",isCross);
                        return ;
                    }
                    $("#pageNo_cp").val(1);
                    $("#totalPage_cp").val(data.count);
                    
                    var customerPriceList = data.data;
                    $("#cutomerPriceTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this).html(null);
                        });
                    });
                    if(customerPriceList.length === 0){
                        return;
                    }
                    var i = -1;
                    $("#cutomerPriceTable").find("tr").each(function(){
                        var j = 0;
                        $(this).find("td").each(function(){
                            $(this).html(customerPriceList[i][j]);
                             if(j === 5){
                               $(this).html("<input type=\"button\" id=\"delete_cp\" class=\"btn btn-default radius\" onclick=\"deleteCustomerPrice(this)\" value=\"删除\"/>");
                            }
                            j++;
                        });
                        i++;
                    });
                },  
                error : function(data) {
                    getCustomerPriceList("addCustomerPrice",isCross);
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            }); 
    }
     
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取要修改的客戶資訊
     * 
     ********************************************************************************/
    function getCustomerForUpdate(){
        
        var customerId =  customerInfo[0];
        
        $.ajax({  
                url : "getCustomerForUpdate",  
                type : "post",  
                datatype:"json",  
                data:{customerId:customerId},
                success : function(data) { 
                    $("#customer_id_update").val(data.data.customerId);
                    $("#customer_name_update").val(data.data.customerName);
                    $("#customer_mail_updatet").val(data.data.customerMail);
                    $("#customer_phone_update").val(data.data.customerPhone);
                    $("#version_number_update").val(data.data.versionNumber);
                    $("#customer_master_id_update").val(data.data.customerMasterId);
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    }
    
     /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：修改客戶資訊
     * 
     ********************************************************************************/
    function updateCustomer(){
        var customerId = $("#customer_id_update").val();
        var customerName = $("#customer_name_update").val();
        var customerMail = $("#customer_mail_updatet").val();
        var customerPhone = $("#customer_phone_update").val();
        var versionNumber = $("#version_number_update").val();
        var customerMasterId = $("#customer_master_id_update").val();
        
        $.ajax({  
                url : "updateCustomer",  
                type : "post",  
                datatype:"json",  
                data : {customerMasterId:customerMasterId,customerId:customerId,customerName:customerName,customerMail:customerMail,customerPhone:customerPhone,versionNumber:versionNumber},  
                success : function(data) {  

                    $("#updateCustomer").modal("toggle");
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",5000);
                    
                    var customer = new Array(data.data.customerId,data.data.customerName,data.data.customerMail,data.data.customerPhone);
                    
                    $("#customerTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            if($(this).html() === data.data.customerId){
                                var i = 0;
                                $(this).parent("tr").find("td").each(function (){
                                    $(this).html(customer[i]);
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
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/07
     * 功能簡述：刪除客戶資訊
     * 2017/01/07 添加版本號
     * 
     ********************************************************************************/
    function deleteCustomer(){
        var customerId =  customerInfo[0];
        var versionNumber = customerInfo[1];
        $.ajax({  
                url : "deleteCustomer",  
                type : "post",  
                datatype:"json",  
                data : {customerId:customerId,versionNumber:versionNumber},  
                success : function(data) {  
                    
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",5000);
                    setTimeout("window.location = '<%=basePath%>CustomerManage/CustomerQuery'",5000);
                },  
                error : function(data) { 
                    try {
                        var response = JSON.parse(data.responseText.toString());
                        $("#alter_message").html(response.message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                    catch(e){
                        var message = data.responseText.split("<p class=\"error-description\">")[1].split(":")[1];
                        $("#alter_message").html(message);
                        $("#modal-message").modal("show");
                        setTimeout("$(\"#modal-message\").modal(\"hide\")",5000);
                    }
                }  
            });
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：按鈕置灰
     * 
     ********************************************************************************/
    var customerInfo = [];
    $("tr").click(function(){
    
        $(this).find("td").each(function(i){
           var text = $(this).text();
           if(/CUS[0-9]{11}/g.test(text)){
               customerInfo[0] = text;
               customerInfo[1] = $(this).next("td").text();
               $("tr").each(function (){
                    $(this).removeClass("success");
                })
               $(this).parent("tr").attr('class',"success");
           }
           return false;
           });
           
        if(/CUS[0-9]{11}/g.test(customerInfo[0])){   

            $('#custEditButton').attr('href',"#updateCustomer");//添加标签中的href属
            $("#custEditButton").removeClass("btn btn-default radius");
            $("#custEditButton").addClass("btn btn-primary radius");
            
            $('#addCustomerPriceButton').attr('href',"#addCustomerPrice");//添加标签中的href属
            $("#addCustomerPriceButton").removeClass("btn btn-default radius");
            $("#addCustomerPriceButton").addClass("btn btn-primary radius");
        
            $('#custDelButton').attr('href',"#deleteInfo");//添加标签中的href属
            $("#custDelButton").removeClass("btn btn-default radius");
            $("#custDelButton").addClass("btn btn-primary radius");
        }else{
            $('#custEditButton').removeAttr('href');//清除标签中的href属
            $("#custEditButton").removeClass("btn btn-primary radius");
            $("#custEditButton").addClass("btn btn-default radius");
            
            $('#addCustomerPriceButton').removeAttr('href');//清除标签中的href属
            $("#addCustomerPriceButton").removeClass("btn btn-primary radius");
            $("#addCustomerPriceButton").addClass("btn btn-default radius");
        
            $('#custDelButton').removeAttr('href');//清除标签中的href属
            $("#custDelButton").removeClass("btn btn-primary radius");
            $("#custDelButton").addClass("btn btn-default radius");
        }
    });
   
    
        
</script>
</body>
</html>