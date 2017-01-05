<%-- 
    Document   : OrderManage
    Created on : 2016-12-21, 15:04:43
    Author     : David
--%>
<%@page import="iot.dao.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/skin/default/skin.css" id="skin" />
<!--[if lt IE 9]>
<link href="static/h-ui/css/H-ui.ie.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>客戶訂單管理系統</title>  
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
                    
                    
    <!--菜单缩进--> 
    <div class="dislpayArrow"> 
        <a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
    </div>
    <!--订单信息列表-->           
    <section class="Hui-article-box">
        <div class="page-container">
            <form action="queryOrderHeadList" method="post">
		<h3 align="center">訂單頭檔信息列表</h3>
                <br/>
                <p align="center">
                    訂單編號起點：<input type="text" name="orderHeadIdMin" class="input-text radius" value="${queryCondition.orderHeadIdMin}" style="width:100px"  id="orderHeadIdMin" autocomplete="off"/>
                    訂單編號終點：<input type="text" name="orderHeadIdMax" class="input-text radius" value="${queryCondition.orderHeadIdMax}" style="width:100px"  id="orderHeadIdMax" autocomplete="off" />
                    下單日期起點：<input type="date" name="orderDateMin" class="input-text radius" value="${queryCondition.orderDateMin}" style="width:150px" id="orderDateMin" autocomplete="off"/>
                    下單日期終點：<input type="date" name="orderDateMax" class="input-text radius" value="${queryCondition.orderDateMax}" style="width:150px" id="orderDateMax" autocomplete="off"/>
                    下單客戶名字：<input type="text" name="customerName" class="input-text radius" value="${queryCondition.customerName}" style="width:100px"  id="customerName" autocomplete="off"/>
                    <input class="btn btn-primary radius"  type="submit" value="查询"/>
                    <a data-toggle="modal" class="btn btn-primary radius" href="#addOrderHeadWindow"/>新增</a>
                    <a data-toggle="modal" id="orderHeadDelBtn" class="btn btn-default radius">删除</a>
                </p>
            </form>
            <table class="table table-border table-bordered table-hover" id="orderHeadTable">
		<tr>
                    <th style="width:100px">訂單頭檔編號</th> 
                    <th style="width:100px">下單日期</th>  
                    <th style="width:100px">下單客戶</th> 
                    <th style="width:100px">操作</th> 
		</tr>
                <c:forEach items="${orderHeadList}" var ="orderHead"> 
                    <c:set var="date" value="${orderHead.orderDate}" />
                <tr style=" height: 38px">
                    <td style="width:100px"><c:out value="${orderHead.orderHeadId}"></c:out></td>
                    <td style="width:100px"><fmt:formatDate type="both" value="${date}"></fmt:formatDate></td> 
                    <td style="width:100px"><c:out value="${orderHead.customerMasterId.customerName}"></c:out></td>
                    <td>
                        <a data-toggle="modal" class="showOrderDetail" class="radius" >查看訂單身檔</a>
                    </td>
                    <td hidden="true"><c:out value="${orderHead.orderHeadId}"></c:out></td>
                </tr>
                
                </c:forEach> 
            </table>
            <br/>
           <div align="center">
            <p> <input type="button" value="上一頁" onclick="OHprePage()"/>
            <input class="input-text radius" type="text" id="pageNo" value="${queryCondition.pageNo}" readonly="true" style="width:30px" />/
            <input class="input-text radius" type="text" id="totalPage" value="${totalPage}" readonly="true" style="width:30px" />
            <input type="button" value="下一頁" onclick="OHnextPage()"/>
            </p>
        </div>
        </div>
        </section> 
            
            
    <!--删除订单头档-->
    <div id="deleteOHInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel">訂單頭檔刪除信息提示！</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <h4 id="myModalLabel" align="center">確定刪除該訂單頭檔？</h4>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"  onclick="deleteOrderHead()">確定</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        </div>
    </div>
    
    
    
    <!--新增订单头档-->            
    <div id="addOrderHeadWindow" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>新增訂單頭檔</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="addOrderHead" method="post" class="form form-horizontal responsive">
            	<div class="row cl">
                    <label class="form-label col-xs-3" style=" width: 150px">訂單頭檔編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text disabled" autocomplete="off" readonly="true" placeholder="自動生成訂單頭檔編號" name="orderHeadId" id="orderHeadId_4add"/>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3" style=" width: 150px">客戶信息：</label>
                        <div class="formControls col-xs-5"  id="master_div">
                        <span class="select-box">
                            <input class="select" size="1" name="customerId" id="customerId_4add" list="customer_list" /> <!--onchange="setupCustomer()" -->
                                <datalist id="customer_list"> 
                                </datalist>
                        </span>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3" style=" width: 150px">下單日期：</label>
                    <div class="formControls col-xs-5">
                        <input   type="text" class="input-text disabled" readonly="true"placeholder="系統自動獲取當前時間" autocomplete="off" />
                    </div>
		</div>
                <div class="row cl">
                    
		</div>
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" type="button"  value="確定"  onclick="addOrderHead()">
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>
        </div>
    </div>   
    
    
    <!--订单详细列表-->  
    <div id="orderDetail" class="modal1 hide fade"style="width:500px" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h3 align="center" id="myModalLabel">訂單身檔信息列表</h3>
       
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
            <div align="right">
                <a data-toggle="modal" class="btn btn-primary radius" onclick="refreshOrderDetailList()" />刷新</a>  
            </div>
        </div>
        <div class="modal-body">
            <table id="orderDetailTable" class="table table-border table-bordered table-hover">
		<tr>
                    <th style="width:150px">訂單身檔編號</th>
                    <th style="width:150px">訂單頭檔編號</th> 
                    <th style="width:150px">產品編號</th>
                    <th style="width:150px">產品名稱</th> 
                    <th style="width:60px">下單數量</th>  <!--訂單產品下單數量-->
                    <th style="width:60px">下單單價</th>   <!--訂單產品下單單價-->
		</tr>
                
                <tr>
                    <c:forEach items="${orderDetails}" var ="orderDetails"> 

                <tr style=" height: 38px">
                    <td style="width:150px"><c:out value="${orderDetails.orderHeadMasterId.orderHeadId}"></c:out></td>
                    <td style="width:150px"><c:out value="${orderDetails.orderDetailId}"></c:out></td> 
                    <td style="width:150px"><c:out value="${orderDetails.productMasterId.productId}"></c:out></td>
                    <td style="width:150px"><c:out value="${orderDetails.productMasterId.productName}"></c:out></td>
                    <td style="width:60px"><c:out value="${orderDetails.orderQty}"></c:out></td> 
                    <td style="width:60px"><c:out value="${orderDetails.orderPrice}"></c:out></td>
                    
                    <td hidden="true"><c:out value="${orderDetails.orderDetailMasterId}"></c:out></td>
                </tr>
                </c:forEach> 
                </tr>

<!--            <tr height="48px"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
            <tr height="48px"><td></td><td></td><td></td><td></td><td></td><td></td></tr>-->
            </table>
            <div align="center">
            <p> <input type="button" value="上一頁" id="odPrePage" />
            <input class="input-text radius" type="text" id="odpageNo" value="1" readonly="true" style="width:30px" />/
            <input class="input-text radius" type="text" id="odtotalPage"  readonly="true" style="width:30px" />
            <input type="button" value="下一頁" id="odNextPage"/>
            </p>
        </div>
        </div>
        <div class="modal-footer">
            <a data-toggle="modal" class="btn btn-primary radius" href="#addODInfo"/>新增</a>
            <a data-toggle="modal" class="btn btn-default radius" id="orderDetailUpBtn" onclick="getOrderDetailForUpdate()"/>修改</a>
            <a data-toggle="modal" class="btn btn-default radius" id="orderDetailDelBtn"/>刪除</a>
        </div> 
    </div>
    
    
    <!--新增订单身档-->     
     <div id="addODInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>新增訂單身檔</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="addOrderDetail" method="post" class="form form-horizontal responsive">
            	 <div class="row cl">
                    <label class="form-label col-xs-3">訂單頭檔編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text"  id="orderHeadId_4addDetail" name="orderHeadId" class="input-text disabled" readonly="true"/>
                    </div>
		</div>
		<div class="row cl">
                    <label class="form-label col-xs-3">產品信息：</label>
                    <div class="formControls col-xs-5">
                        <span class="select-box">
                        <input class="select" name="productId" id="productId_4add" list="product_list" />
                           <datalist id="product_list"> 
                           </datalist>
                        </span>   
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">下單數量：</label>
                    <div class="formControls col-xs-5" id="add_div">
                        <input type="text"  value="" id="orderQty_4add" name="orderQty" class="input-text radius"/>
                    </div>
		</div>
                
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" type="button" value="确dada定" onclick="addOrderDetail()">
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>
        </div>
    </div>   
    <!--修改订单身档-->     
     <div id="updateODInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>修改訂單身檔</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="orderDetailUpdate" method="post" class="form form-horizontal responsive">
            	<div class="row cl" hidden="true">
                    <label class="form-label col-xs-3">訂單頭檔編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="OrderHeadId4Update"  readonly="true" name="orderHeadId" class="input-text disabled radius"/>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">訂單身檔編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="orderDetailId4Update"   name="orderDetailId" readonly="true" class="input-text disabled radius" />
                    </div>
		</div>
                <div class="row cl" hidden="true">
                    <label class="form-label col-xs-3">產品編號：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="productId4Update"  readonly="true" name="productId" class="input-text disabled radius"/>
                    </div>
		</div>
		<div class="row cl">
                    <label class="form-label col-xs-3">產品名稱：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="productName4Update"  readonly="true" name="productName" class="input-text disabled radius"/>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">下單數量：</label>
                    <div class="formControls col-xs-5">
                        <input type="text"  id="orderQty4Update"  name="orderQty" class="input-text radius" onblur="getPriceByQtyForUpdate()"/>
                    </div>                    
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">產品價格：</label>
                    <div class="formControls col-xs-5">
                        <input type="text"  id="productPrice4Update"  name="productPrice"  readonly="true" class="input-text disabled radius"/>
                    </div>
                </div>
               <div class="row cl">
                   <label class="form-label col-xs-3" style="color: red">自定義下單價格：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="userDefinedPrice4Update"  name="userDefinedPrice" class="input-text radius"/>
                    </div>
                   <label class="form-label col-xs-3" style="color: red">*管理員權限*</label>
		</div>
                <div class="row cl" hidden="true">
                    <label class="form-label col-xs-3">修改次數：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="versionNumber4Update"  name="versionNumber" class="input-text radius"/>
                    </div>
		</div>
             
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" type="button" onclick="updateOrderDetail()" value="确定">
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>
        </div>
    </div> 
    
    <!--刪除訂單身檔-->
    <div id="deleteODInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel">訂單身檔刪除信息提示！</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <h4 id="myModalLabel" align="center">確定刪除該訂單身檔？</h4>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"  onclick="deleteOrderDetail()">確定</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
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
<script type="text/javascript">
    
     //獲取當前使用者的session信息
    $(function (){
       var session = "<%=(User)session.getAttribute("user")%>"; 
       hello(session);
    });
    
    
//單獨使點擊id=orderHeadTable的表格的行時，使底色變色
//$(document).ready(function(){
//    $("#orderHeadTable tr").click(function(){
//        $(this).css({"background":"#99ffff"}).siblings().css({"background":"white"});
//    });
//});

</script>    
<script>
  
  /**
     * 顯示訂單頭檔信息列表
     * @@author David
     */
    //在class=showOrderDetail觸發此函數   
    $(document).ready(function(){
                $(".showOrderDetail").click(function(){ //只能是class，不能用id
                  //把showOrderDetail這個class的上一級的上一級的下一級的第一列的值付給orderHeadId（<a> -> <td> -> <tr>,再 -> <td>的第一個）
                    orderHeadId4OD = $(this).parent().parent()
                            .children().eq(0).text(); 
                    dealWithOD();
                });
              });
     

//點擊訂單詳細時，分頁顯示訂單身檔內容
    function dealWithOD(value){
         //把取到的orderHeadId的值賦值給id=orderHeadId_4addDetail的標籤位置
                 $("#orderHeadId_4addDetail").val(orderHeadId4OD);                   
                 
                $.ajax({  
                url : "queryOrderDetailList",  
                type : "post",  
                datatype:"json",  
                data:{orderHeadId:orderHeadId4OD,pageNo:value},
                success : function(data) {
                 
                $("#odtotalPage").val(data.count);               
                
                var list=data.data;               
                
                $("#orderDetail").modal("show");  
                
                var i=0;
                        $("#orderDetailTable").find("tr").each(function (){       
                            if(i>0){
                                $(this).remove();
                                }
                            i++;   
                            });
                
                for(var i=0;i<list.length;i++){
                    //table中新建行列
                    //繪製顯示訂單詳細的表格
                    //
                    //$("#orderDetailTable").append("<tr><td>"+list[i].orderDetailId+"</td><td>"+list[i].ordheadMasterId.orderHeadId+"</td><td>"+list[i].productMasterId.productId+"</td><td>"+list[i].productMasterId.productName+"</td><td>"+list[i].orderQty+"</td><td>"+list[i].orderPrice+"</td></tr>");
                      $("#orderDetailTable").append("<tr><td>"+list[i][0]+"</td><td>"+list[i][1]+"</td><td>"+list[i][2]+"</td><td>"+list[i][3]+"</td><td>"+list[i][4]+"</td><td>"+list[i][5]+"</td></tr>");
                    //在訂單身檔信息列中，點擊行事件獲取到第一列的值（OrderDetailId），並把值放到了 orderInfo 變量中
                    orderHeadId4Refresh=list[i][1];
                    
                    //在訂單身檔信息列表中，點擊行，將行底色加深
//                    $(document).ready(function(){
//                        $("#orderDetailTable tr").click(function(){
//                        $(this).css({"background":"#99ffff"}).siblings().css({"background":"white"});
//                        });
//                    });
                                                            
                    $("tr").click(function(){
                        $(this).find("td").each(function(i){
                            var text = $(this).text();
                            if(/ORDD[0-9]{11}/g.test(text)){
                                console.dir(text+"hahahah");
                            orderInfo[0] = text;
                            $(this).parent("tr").css({"background":"#99ffff"}).siblings().css({"background":"white"});
                            return false;
                        }
                    }); 
                    
                        //訂單身檔彈窗按鈕置灰                        
                        if(/ORDD[0-9]{11}/g.test(orderInfo[0])){   
                        $('#orderDetailDelBtn').attr('href',"#deleteODInfo");//添加标签中的href属
                        $("#orderDetailDelBtn").removeClass("btn btn-default radius");
                        $("#orderDetailDelBtn").addClass("btn btn-primary radius");
                        
                        $('#orderDetailUpBtn').attr('href',"#updateODInfo");//添加标签中的href属
                        $("#orderDetailUpBtn").removeClass("btn btn-default radius");
                        $("#orderDetailUpBtn").addClass("btn btn-primary radius");
                        }else{
                        $('#orderDetailDelBtn').removeAttr('href');//清除标签中的href属
                        $("#orderDetailDelBtn").removeClass("btn btn-primary radius");
                        $("#orderDetailDelBtn").addClass("btn btn-default radius");
                        
                        $('#orderDetailUpBtn').removeAttr('href');//清除标签中的href属
                        $("#orderDetailUpBtn").removeClass("btn btn-primary radius");
                        $("#orderDetailUpBtn").addClass("btn btn-default radius");
                        }
                    });
                    }
   
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });                        
     }
     
     //刷新訂單身檔列表
      function refreshOrderDetailList(){
        //var orderHeadId=orderInfo[1];
        var orderHeadId= orderHeadId4Refresh;
        var pageNo = parseInt( $("#odpageNo").val())-1;
        console.dir(orderHeadId+"在公共");
        console.dir(pageNo+"在公共");
        dealWithOD(pageNo);
        //刷新之後把功能按鈕顏色重置
                        $('#orderDetailDelBtn').removeAttr('href');//清除标签中的href属
                        $("#orderDetailDelBtn").removeClass("btn btn-primary radius");
                        $("#orderDetailDelBtn").addClass("btn btn-default radius");
                        
                        $('#orderDetailUpBtn').removeAttr('href');//清除标签中的href属
                        $("#orderDetailUpBtn").removeClass("btn btn-primary radius");
                        $("#orderDetailUpBtn").addClass("btn btn-default radius");
    }
     
     //綁定id點擊觸發函數
      $("#odPrePage,#odNextPage").bind('click', function() {
        getODPageData(this.id);
    });
    //獲取分頁信息
    function getODPageData(value){
       
        var pageNo = parseInt( $("#odpageNo").val());
        var pageNo_ = "";
        var totalPage = parseInt($("#odtotalPage").val());
        if(value === "odPrePage"){
            if(pageNo <= 1) return ;
            pageNo_ = pageNo - 2;
            $("#odpageNo").val(pageNo - 1);
        }
        if(value === "odNextPage"){
           if(pageNo >= totalPage) return ;
           pageNo_ = pageNo;
           $("#odpageNo").val(pageNo + 1);
        }
        dealWithOD(pageNo_);
    }
    
    /**
     * 將顯示訂單詳細時動態繪製的表格行刪除，避免在點擊其他選項時，還保留之前的數據      
     * @@author David
     */
    $("#orderDetail").bind("hide",function() {
        var i=0;
    $("#orderDetailTable").find("tr").each(function (){       
        if(i>0){
            $(this).remove();
            }
        i++;   
        });
    });

    /**
     * 獲取客戶信息列表,顯示選擇
     * @@author David
     * @type type
     */
    var customerList;
    $("#customerId_4add").bind('input propertychange focus', function() {
        var customerId = $("#customerId_4add").val();
        $.ajax({  
                url : "getCustomerList",  
                type : "post",  
                datatype:"json",  
                data:{customerId:customerId},
                success : function(data) { 
                    customerList = data.data;
                    $("#customer_list").html(null);
                    for(var i = 0; i < customerList.length; i++){
                        $("#customer_list").append('<option label="' + customerList[i].customerName  + '" value="' + customerList[i].customerId + '"></option>');
                    }  
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
    });
    
    /**
     * 獲取產品信息，用於顯示，讓用戶選擇
     */
     var productList;
    $("#productId_4add").bind('input propertychange focus', function() {
        var productId = $("#productId_4add").val();
        $.ajax({  
                url : "getProductList",  
                type : "post",  
                datatype:"json",  
                data:{productId:productId},
                success : function(data) { 
                    productList = data.data;
                    $("#product_list").html(null);
                    for(var i = 0; i < productList.length; i++){
                        $("#product_list").append('<option label="' + productList[i].productName  + '" value="' + productList[i].productId + '"></option>');
                    }
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
    });


     
     /**
      * 訂單頭檔頁面選中有數據的行時，功能按鈕置灰
      * @type Array
      */
     var orderInfo = [];
    $("tr").click(function(){
        var num = 0;
        //訂單頭檔頁面,行點擊事件讀取到行的值
        $(this).find("td").each(function(i){
           var text = $(this).text();
           if(/ORDH[0-9]{11}/g.test(text)){
           orderInfo[num] = text;
            $(this).parent("tr").css({"background":"#99ffff"}).siblings().css({"background":"white"});
           num++;
            }
        });
           
        if(/ORDH[0-9]{11}/g.test(orderInfo[0])){   
            $('#orderHeadDelBtn').attr('href',"#deleteOHInfo");//添加标签中的href属
            $("#orderHeadDelBtn").removeClass("btn btn-default radius");
            $("#orderHeadDelBtn").addClass("btn btn-primary radius");     
           
        }else{
            $('#orderHeadDelBtn').removeAttr('href');//清除标签中的href属
            $("#orderHeadDelBtn").removeClass("btn btn-primary radius");
            $("#orderHeadDelBtn").addClass("btn btn-default radius");                        
        }
       
    });
     
     //订单获取下一页数据
    function OHnextPage(){

        var pageNo = parseInt($("#pageNo")[0].value);
        var totalPage = parseInt($("#totalPage")[0].value);
        
        var orderHeadIdMin=document.getElementById("orderHeadIdMin").value;
        var orderHeadIdMax=document.getElementById("orderHeadIdMax").value;
        var orderDateMin=document.getElementById("orderDateMin").value;
        var orderDateMax=document.getElementById("orderDateMax").value;
        var customerName=document.getElementById("customerName").value;
        
        
        console.dir(pageNo+"pageNo");
        console.dir(totalPage+"totalPage");
        
        
        if(pageNo < totalPage){
           var pageNo = pageNo+1;
        window.location = "<%=basePath%>OrderManage/queryOrderHeadList?pageNo="+ pageNo +"&orderHeadIdMin="+orderHeadIdMin+
            "&orderHeadIdMax="+orderHeadIdMax+"&orderDateMin="+orderDateMin+"&orderDateMax="+orderDateMax+"&customerName="+customerName+""    ;
        }   
    }
        
    //订单上一页 
    function OHprePage(){
        
        var pageNo = parseInt($("#pageNo")[0].value);
        var orderHeadIdMin=document.getElementById("orderHeadIdMin").value;
        var orderHeadIdMax=document.getElementById("orderHeadIdMax").value;
        var orderDateMin=document.getElementById("orderDateMin").value;
        var orderDateMax=document.getElementById("orderDateMax").value;
        var customerName=document.getElementById("customerName").value;
        
        if(pageNo > 1){
            var pageNo=pageNo-1;
             window.location = "<%=basePath%>OrderManage/queryOrderHeadList?pageNo="+ pageNo +"&orderHeadIdMin="+orderHeadIdMin+
            "&orderHeadIdMax="+orderHeadIdMax+"&orderDateMin="+orderDateMin+"&orderDateMax="+orderDateMax+"&customerName="+customerName+""    ;
        }        
    }
     
     function addOrderHead(){
         var customerId=$("#customerId_4add").val();
         $.ajax({  
                url : "addOrderHead",  
                type : "post",  
                datatype:"json",  
                data : {customerId:customerId},  
                success : function(data) {                     
                 alert(data.message);
                 window.location="<%=basePath%>OrderManage/queryOrderHeadList" ;
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
     }
    
    /**
     * 新增訂單頭檔函數
     * 前臺使用者傳入的，只有產品信息和下單數量（這裏取產品編號customerId和orderQty），其它的編號在後臺生成
     *
     */
    function addOrderDetail(){
        var orderHeadId=$("#orderHeadId_4addDetail").val();
        var productId=$("#productId_4add").val();
        var orderQty=$("#orderQty_4add").val();
            $.ajax({  
                url : "addOrderDetail",  
                type : "post",  
                datatype:"json",  
                data : {orderHeadId:orderHeadId,orderQty:orderQty,productId:productId},  
                success : function(data) {
                 $("#addODInfo").modal("toggle");
                 $("#alter_message").html(data.message);
                 $("#modal-message").modal("toggle");
                 setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
//                 alert(data.message);
//                 window.location="<%=basePath%>OrderManage/queryOrderHeadList" ;
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });  
    }
    //用戶在修改訂單詳細時，輸入下單數量和獲取到的產品Id，去取得產品價格
    function getPriceByQtyForUpdate(){
        var productId=$("#productId4Update").val();
        var orderQty=$("#orderQty4Update").val();
            $.ajax({  
                url : "getPriceByQtyForUpdate",  
                type : "post",  
                datatype:"json",  
                data : {orderQty:orderQty,productId:productId},  
                success : function(data) {  
                 $("#productPrice4Update").val(data.productStandardPrice);
                 $("#userDefinedPrice4Update").val(data.productStandardPrice);                
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });  
    }
    
    
    /**
     * 刪除訂單頭檔
     *
     */
    function deleteOrderHead(){
    var orderHeadId=orderInfo[0];
    $.ajax({  
                url : "deleteOrderHead",  
                type : "post",  
                datatype:"json",  
                data : {orderHeadId:orderHeadId},  
                success : function(data) {                   
                    alert("刪除訂單頭檔成功！");
                    window.location = "<%=basePath%>OrderManage/queryOrderHeadList";
//                    $("#modal-message").modal("toggle");
//                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
//                    //window.location = "<%=basePath%>OrderManage/queryOrderHeadList";
//                    setTimeout("window.location = '<%=basePath%>OrderManage/queryOrderHeadList'",2000);
                },  
                error : function(data) { 

                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
    }    
    
     
    function deleteOrderDetail(){

    var orderDetailId=orderInfo[0];
    $.ajax({  
                url : "deleteOrderDetail",  
                type : "post",  
                datatype:"json",  
                data : {orderDetailId:orderDetailId},  
                success : function(data) {                           
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
//                    $("#alter_message").html(data.message);
//                    alert("刪除訂單身檔成功！");
//                    window.location = "<%=basePath%>OrderManage/queryOrderHeadList";
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
    }
    
        function getOrderDetailForUpdate(){
        
        var orderDetailId =  orderInfo[0];
        $.ajax({  
                url : "getOrderDetailForUpdate",  
                type : "post",  
                datatype:"json",  
                data:{orderDetailId:orderDetailId},
                success : function(data) { 
                    var list=data.data;
                    $("#OrderHeadId4Update").val(list.ordheadMasterId.orderHeadId);
                    $("#orderDetailId4Update").val(list.orderDetailId);
                    $("#productId4Update").val(list.productMasterId.productId);                    
                    $("#productName4Update").val(list.productMasterId.productName);
                    $("#orderQty4Update").val(list.orderQty);
                    $("#versionNumber4Update").val(list.versionNumber);
                    $("#productPrice4Update").val(list.orderPrice);                    
                },  
                error : function(data) { 
                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
    }
    function updateOrderDetail(){
        
        var orderDetailId = $("#orderDetailId4Update").val();
        var productId = $("#productId4Update").val();
        var orderQty = $("#orderQty4Update").val();
        var userDefinedPrice = $("#userDefinedPrice4Update").val();
        
        $.ajax({  
                url : "updateOrderDetail",  
                type : "post",  
                datatype:"json",  
                data : {orderDetailId:orderDetailId,productId:productId,orderQty:orderQty,userDefinedPrice:userDefinedPrice},  
                success : function(data) {
                    $("#updateODInfo").modal("toggle");
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                    //alert(data.message);                   
                },  
                error : function(data) { 

                    var str = data.responseText.toString();
                    $("#alter_message").html(str.split("##")[1]);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
    }
    
//   /**
//    * 以下3個函數，分別是獲取當前時間信息並顯示，顯示問候語和修改密碼函數，在每個頁面都是有的
//    *  
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
//    
//    
//    
//    暫時無法成功正確獲取到自動繪製的表格中對應的列中的值
//    function refreshOrderDetailList(){
//        //var orderHeadId=orderInfo[1];
//        var orderHeadId= orderHeadId4Refresh;
//        var pageNo = parseInt( $("#odpageNo").val())-1;
//        console.dir(orderHeadId+"在公共");
//        console.dir(pageNo+"在公共");
//        dealWithOD();
        
        
//            $.ajax({  
//                url : "queryOrderDetailList",  
//                type : "post",  
//                datatype:"json",  
//                data:{orderHeadId:orderHeadId,pageNo:pageNo},
//                success : function(data) { 
//                   var list = data;              
//                   
//                   var i=0;
//                        $("#orderDetailTable").find("tr").each(function (){       
//                            if(i>0){
//                                $(this).remove();
//                                }
//                            i++;   
//                            });   
//               
//                for(var i=0;i<list.length;i++){
//                    //table中新建行列
//                    //繪製顯示訂單詳細的表格
//                    $("#orderDetailTable").append("<tr><td>"+list[i][0]+"</td><td>"+list[i][1]+"</td><td>"+list[i][2]+"</td><td>"+list[i][3]+"</td><td>"+list[i][4]+"</td><td>"+list[i][5]+"</td></tr>");
//                    
//                    //在訂單身檔信息列表中，點擊行，將行底色加深
//                    $(document).ready(function(){
//                        $("#orderDetailTable tr").click(function(){
//                        $(this).css({"background":"#99ffff"}).siblings().css({"background":"white"});
//                        });
//                    });
//                    
//                    //在訂單身檔信息列中，點擊行事件獲取到第一列的值（OrderDetailId），並把值放到了 orderInfo 變量中                                       
//                    $("tr").click(function(){
//                        $(this).find("td").each(function(i){
//                            var text = $(this).text();
//                            orderInfo[0] = text;
//                            console.dir(orderInfo[0]);
//                            return false;
//                        }); 
//                         //訂單身檔彈窗按鈕置灰
//                        if(orderInfo[0] !== ""){   
//                        $('#orderDetailDelBtn').attr('href',"#deleteODInfo");//添加标签中的href属
//                        $("#orderDetailDelBtn").removeClass("btn btn-default radius");
//                        $("#orderDetailDelBtn").addClass("btn btn-primary radius");
//                        
//                        $('#orderDetailUpBtn').attr('href',"#updateODInfo");//添加标签中的href属
//                        $("#orderDetailUpBtn").removeClass("btn btn-default radius");
//                        $("#orderDetailUpBtn").addClass("btn btn-primary radius");
//                        }else{
//                        $('#orderDetailDelBtn').removeAttr('href');//清除标签中的href属
//                        $("#orderDetailDelBtn").removeClass("btn btn-primary radius");
//                        $("#orderDetailDelBtn").addClass("btn btn-default radius");
//                        
//                        $('#orderDetailUpBtn').removeAttr('href');//清除标签中的href属
//                        $("#orderDetailUpBtn").removeClass("btn btn-primary radius");
//                        $("#orderDetailUpBtn").addClass("btn btn-default radius");
//                        }
//                    });
//                    }    
//                },  
//                error : function(data) { 
//                    var str = data.responseText.toString();
//                    $("#alter_message").html(str.split("##")[1]);
//                    $("#modal-message").modal("toggle");
//                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
//                }  
//            });
//       }
   
</script>
</body>
</html>