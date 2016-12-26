<%-- 
    Document   : CustInfo
    Created on : 2016-8-10, 15:04:43
    Author     : lxp
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
<title>客户订单管理系统</title>
 <style type="text/css">
.color {
    background-color: #99ffff;
}
</style>
<script type="text/javascript">
var temptr = $();
$(function(){
    $("#orderTable,#orderDetailTable").on("click","tr",function(event){
        temptr.removeClass("color");
        temptr = $(this);
        temptr.addClass("color")
    });
});
</script>       
</head>
<body>
    <!--导航栏-->
    <header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
            <div class="container-fluid cl"> <a href="<%=basePath%>index" class="logo navbar-logo f-l mr-10 hidden-xs" style="font-family: LiSu; font-size: 30px">客户订单管理系统</a>
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                    <ul class="cl">
			<li id="time">欢迎登陆,</li>
			<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A" style="color: red">${user.userName}</a>
                            <ul class="dropDown-menu menu radius box-shadow">
                                <li><a data-toggle="modal" href="#userInfo">个人信息</a></li>
                                <li><a data-toggle="modal" href="#passwordEdit">修改密码</a></li>
				<li><a href="<%=basePath%>login/logout">退出</a></li>
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
                    <a href="<%=basePath%>CustInfo/CustQuery">客户信息</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="<%=basePath%>orderList/queryList">订单列表</a>
                </li>
            </ul>
              <ul>
                <li>
                    <a href="<%=basePath%>productMaster/loadProductMaster">产品信息</a>
                </li>
            </ul>
              <ul>
                <li>
                    <a href="<%=basePath%>CustPrice/queryCustPrice">客户产品单价</a>
                </li>
            </ul>
	</div>
    </aside>
    <!--管理员信息-->
    <div id="userInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h3 id="myModalLabel">用户信息</h3>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="" class="form form-horizontal responsive">
            	<div class="row cl">
                    <label class="form-label col-xs-3">用户编号：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text" autocomplete="off" value="${user.userId}" name="username" />
                    </div>
		</div>
		<div class="row cl">
                    <label class="form-label col-xs-3">用户姓名：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text" autocomplete="off"  value="${user.userName}" name="password" />
                    </div>
		</div>
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">确定</button>
        </div>
    </div>

    <!--菜单缩进--> 
    <div class="dislpayArrow"> 
        <a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
    </div>
    <!--订单信息列表-->           
    <section class="Hui-article-box">
        <div class="page-container">
            <form action="orderQuery" method="post">
		<h3 align="center">订单信息列表</h3>
                <br/>
                <p align="center">
                    订单编号：<input type="text" name="orderId" class="input-text radius" value="${queryCondition.orderId}" style="width:100px"  id="orderRequiredID" />
                    下单日期：<input type="date" name="firstOrderDate" class="input-text radius" value="${queryCondition.firstDate}" style="width:150px" />
                    下单日期：<input type="date" name="lastOrderDate" class="input-text radius" value="${queryCondition.lastDate}" style="width:150px" />
                    <input class="btn btn-primary radius"  type="submit" value="查询"/>
                    <a data-toggle="modal" class="btn btn-primary radius" href="#add_list" onclick="ADD()"/>新增</a>
                    <a data-toggle="modal" id="del_list" class="btn btn-default radius">删除</a>
                </p>
            </form>
            <table class="table table-border table-bordered table-hover" id="orderTable">
		<tr>
                    <th style="width:100px">订单编号</th> 
                    <th style="width:100px">下单日期</th>  
                    <th style="width:100px">下单客户</th> 
                    <th style="width:100px">操作</th> 
		</tr>
                <c:forEach items="${orderList}" var ="order">
                <c:set var="date" value="${order.orderDate}" />
                <tr class="orderInfo" style=" height: 38px">
                    <td style="width:100px"><c:out value="${order.orderId}"></c:out></td>
                    <td style="width:100px"><fmt:formatDate type="both" value="${date}"></fmt:formatDate></td> 
                    <td style="width:100px"><c:out value="${order.customerName}"></c:out></td>
                    <td>
                        <a data-toggle="modal" class="radius" href="#orderDetail">订单详细</a>
                    </td>
                    <td hidden="true"><c:out value="${order.orderMasterId}"></c:out></td>
                </tr>
                </c:forEach> 
            </table>
            <br/>
            <div align="center">
            <p> <input type="button" value="上一页" onclick="order_pre()"/>
            <input class="input-text radius" type="text" id="pageNo" value="${pageNo}" readonly="true" style="width:30px" />/
            <input class="input-text radius" type="text" id="totalPage" value="${pageNum}" readonly="true" style="width:30px" />
            <input type="button" value="下一页" onclick="order_next()"/>
            </p>
        </div>
        </div>
        </section> 
    <!--删除订单头档-->
    <div id="deleteInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel">信息提示！</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <h4 id="myModalLabel" align="center">确定删除该订单？</h4>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="del()">确定</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        </div>
    </div>
    <!--新增订单头档-->            
    <div id="add_list" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>新增订单</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="orderAdd" method="post" class="form form-horizontal responsive">
            	<div class="row cl">
                    <label class="form-label col-xs-3">订单编号：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text disabled" autocomplete="off" readonly="true" name="orderId" id="order_id" value=""/>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">客户名称：</label>
                        <div class="formControls col-xs-5"  id="master_div">
                        <span class="select-box">
                            <select class="select" size="1" name="customername" id="customerSelect" onchange="getCustomer(this.options[this.options.selectedIndex].value)">
                                <option  id="customerOption" value="" selected>请选择客户</option>
                            </select>
                        </span>
                    <div class="formControls col-xs-5">
                        <input type="text" id="customer_Id" style="width:100px" class="input-text" hidden="true" utocomplete="off"name="customerId" value=""/>
                    </div>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">下单日期：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" class="input-text disabled" readonly="true" autocomplete="off"  name="orderDate" id="orderdate_id" value=""/>
                    </div>
		</div>
                <div class="row cl">
                    
		</div>
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" type="submit"  id="master_required" value="确定" >
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>
        </div>
    </div>   
    <!--订单详细列表-->  
    <div id="orderDetail" class="modal1 hide fade"style="width:500px" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h3 align="center" id="myModalLabel">订单详细信息列表</h3>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();" onclick="refresh()">×</a>
        </div>
        <div class="modal-body">
            <table id="orderDetailTable" class="table table-border table-bordered table-hover">
		<tr>
                    <th style="width:100px">订单详细编号</th>
                    <th style="width:100px">订单编号</th> 
                    <th style="width:100px">产品ID</th>
                    <th style="width:100px">产品名称</th> 
                    <th style="width:100px">订单数量</th>  
                    <th style="width:100px">订单单价</th>   
		</tr>
            </table>
            <div align="center">
            <p> <input type="button" value="上一页" onclick="detail_pre()"/>
            <input class="input-text radius" type="text" id="detailPageNo" value="1" readonly="true" style="width:30px" />/
            <input class="input-text radius" type="text" id="detailTotalPage" readonly="true" style="width:30px" />
            <input type="button" value="下一页" onclick="detail_next()"/>
            </p>
        </div>
        </div>
        <div class="modal-footer">
            <a data-toggle="modal" class="btn btn-primary radius" href="#add_detaillist" onclick="addDetail()"/>新增</a>
        <a data-toggle="modal" id="up_detailList" class="btn btn-default radius" onclick="updateDetail()"/>修改</a>
        </div>
    </div>
    <!--新增订单身档-->     
     <div id="add_detaillist" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>新增订单详细</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="orderDetailAdd" method="post" class="form form-horizontal responsive">
            	<div class="row cl" hidden="true">
                    <label class="form-label col-xs-3">订单编号：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" style="width:300px" id="order_id1" value="" readonly="true" name="orderMasterId" class="input-text disabled radius" />
                    </div>
		</div>
		<div class="row cl">
                    <label class="form-label col-xs-3">产品名称：</label>
                    <div class="formControls col-xs-5" id="detail_div">
                        <span class="select-box">
                        <select class="select" name="productId" id="productId_add" onchange="getProduct(this.options[this.options.selectedIndex].value)">
                            <option value="" selected>请选择产品</option>
                            <c:forEach items="${productMaster}" var ="product">
                                <option value="${product.productId}" ><c:out value="${product.productId}"></c:out><c:out value="${product.productName}"></c:out></option>
                            </c:forEach> 
                        </select>
                        </span>
                         
                    </div>
		</div>
                <div class="row cl" >
                    <label class="form-label col-xs-3">下单数量：</label>
                    <div class="formControls col-xs-5" id="add_div">
                        <input type="text"  value="" id="orderQty_add" name="orderQty" class="input-text radius"/>
                    </div>
		</div>
                
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" type="button" value="确定" id="detail_required">
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>
        </div>
    </div>   
    <!--修改订单身档-->     
     <div id="up_detaillist" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header" align="center">
            <h2 id="myModalLabel"><small>修改订单详细</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <form action="orderDetailUpdate" method="post" class="form form-horizontal responsive">
            	<div class="row cl">
                    <label class="form-label col-xs-3">订单编号：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="order_id2" value="" readonly="true" name="orderMasterId" class="input-text disabled radius" />
                    </div>
		</div>
		<div class="row cl">
                    <label class="form-label col-xs-3">产品名称：</label>
                    <div class="formControls col-xs-5">
                        <input type="text" id="productName" value="" readonly="true" name="productName" class="input-text disabled radius"/>
                    </div>
		</div>
                <div class="row cl">
                    <label class="form-label col-xs-3">下单数量：</label>
                    <div class="formControls col-xs-5" id="qty_div">
                        <input type="text" id="orderQty" value="" name="orderQty" class="input-text radius"/>
                    </div>
		</div>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <input type="text" id="orderDetailId" hidden="true" value="" name="orderDetailId" readonly="true" />
                    </div>
		</div>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <input type="text" id="productId" hidden="true" value="" name="productId" readonly="true" />
                    </div>
		</div>
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" align="right">
                        <input class="btn btn-primary radius" type="button" id="editdetail" value="确定">
                        <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
                    </div>
                </div>
            </form>
        </div>
    </div> 
    
    <!--修改管理员密码-->            
<div id="passwordEdit" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header" align="center">
        <h3 id="myModalLabel"><small>修改密码</small></h3>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
    	<form action="login/editPassword" method="post" class="form form-horizontal responsive">
            	<div class="row cl">
		<label class="form-label col-xs-3">原密码：</label>
		<div class="formControls col-xs-5">
                   <input type="password" class="input-text" id="passwordOld" autocomplete="off" name="passwordOld" />
		</div>
		</div>
		<div class="row cl">
		<label class="form-label col-xs-3">新密码：</label>
		<div class="formControls col-xs-5">
                    <input type="password" class="input-text" id="passwordNew" autocomplete="off" name="passwordNew" />
		</div>
		</div>
                <div class="row cl">
		<label class="form-label col-xs-3">确认新密码：</label>
		<div class="formControls col-xs-5">
                    <input type="password" class="input-text" id="passwordConfirm" autocomplete="off"  name="passwordConfirm" />
                     <p id="message" class="c-error text-l"></p>
		</div>
		</div>
                
                <div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                    <input class="btn btn-primary radius" type="button" id="savepassword" value="保存" />
                    <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true"/>
		</div>
                
        </form>
    </div>
</div>
<script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/com.js"></script>

<script>
    
    var str = [];
    var id;
    var up = [];
    //查询订单详细信息
    $(function (){
        $(".orderInfo").each(function (){
            var tem = $(this).children().eq(3);
            var button = tem.children();
            button.bind("click",function(){ 
                var val = button.parent().parent().children("td").get(0).innerHTML;
                id = button.parent().parent().children("td").get(4).innerHTML;
                
                if($(this)[0].innerHTML === "订单详细"){
                    $.ajax({
                        url:"detailQuery",
                        type:"get",
                        datatype:"json",  
                        data : {orderId:""+ val +""}, 
                        success : function(data, stats) {  
                            if (stats === "success") { 
                                var orderDetailInfo = data.orderDetailInfo;
                                $("#detailTotalPage")[0].value = data.count;
                                
                                for(var i=0;i<orderDetailInfo.length;i++){
                                    //table中新建行列
                                    $("#orderDetailTable");
                                    tb = document.getElementById("orderDetailTable");
                                    tb.hidden = false;
                                    new_row = tb.insertRow();
                                    new_cell1 = new_row.insertCell();
                                    new_cell2 = new_row.insertCell();
                                    new_cell3 = new_row.insertCell();
                                    new_cell4 = new_row.insertCell();
                                    new_cell5 = new_row.insertCell();
                                    new_cell6 = new_row.insertCell();
                                    //新建行列中插入信息
                                    new_cell1.innerHTML = orderDetailInfo[i].orderDetailId;
                                    new_cell2.innerHTML = orderDetailInfo[i].orderMasterId_int;
                                    new_cell3.innerHTML = orderDetailInfo[i].productId_int;
                                    new_cell4.innerHTML = orderDetailInfo[i].productName;
                                    new_cell5.innerHTML = orderDetailInfo[i].orderQty;
                                    new_cell6.innerHTML = orderDetailInfo[i].orderPrice;
                                } 
                            }
                        },  
                        error : function(data) {  
                            alert("查询产品单价失败");  
                        }   
                    });
                }
                
            });
        });  
        //去掉a标签中的href属性
        //$('#del_list').removeAttr('href');
        //获取点击所在一行的数据并放入数组str中
        $("#orderDetailTable").on("click","tr",function(){
            up = [];
            num = 0;
            $(this).find("td").each(function(i){
                var txt = $(this).text();
                up[num] = txt;
                num++;
            });
            if(up[0] !== ""){
                $('#up_detailList').attr('href',"#up_detaillist");//添加标签中的href属
                $("#up_detailList").removeClass("btn btn-default radius");
                $("#up_detailList").addClass("btn btn-primary radius");
            }else{
                $('#up_detailList').removeAttr('href');//清除标签中的href属
                $("#up_detailList").removeClass("btn btn-primary radius");
                $("#up_detailList").addClass("btn btn-default radius");
            }
        });
        $("tr").click(function(){
            var num = 0;
            $(this).find("td").each(function(i){
                var txt = $(this).text();
                str[num] = txt;
                num++;
            });
            $('#del_list').attr('href',"#deleteInfo");//添加标签中的href属
            $("#del_list").removeClass("btn btn-default radius");
            $("#del_list").addClass("btn btn-primary radius");
        });
    });
    //显示欢迎
      $(function (){
       var session = "<%=(User)session.getAttribute("user")%>"; 
       hello(session);
    });
    
    //删除订单头档
    function del(){
        var orderid = str[0];
        if(orderid === ""){
            alert("未选中订单，不能删除！");
        }else{
            window.location = "<%=basePath%>orderList/orderDelete?orderId="+orderid+"";
        }
    }
    
    //新增订单头档
    function ADD(){
        $.ajax({
            url:"orderAdd",
            type:"get",
            success:function(data,stats){
                if(stats === "success"){
                    //获取订单编号
                    $("#order_id")[0].value = data[0];
                    //获取下单日期
                    $("#orderdate_id")[0].value = data[1];
                    //获取下拉列表值
                    for(var i = 0;i < data[2].length;i++){
                        $("#customerSelect").append("<option value="+ data[2][i].customerId +">"+data[2][i].customerName+"</option>");
                    }
                }
            },
            error:function(data) {  
                alert("查询失败");  
            }  
        });
    }
    
    //新增订单身档
    function addDetail(){
        $("#order_id1")[0].value = id ;
    }
    
    //修改订单身档
    function updateDetail(){
        $("#order_id2")[0].value = up[1] ;
        $("#productName")[0].value = up[3];
        $("#orderQty")[0].value = up[4];
        $("#orderDetailId")[0].value = up[0] ;
        $("#productId")[0].value = up[2];
        $.ajax({
            url:"orderDetailUpdate",
            type:"get",
            dataType: "json",
            data:{orderDetailId:""+up[0]+""},
            success:function(data,stats){
            },
            error:function() {  
            }  
        });
    }
    
    //将修改后的订单身档数据传给Controller
    function orderDetailEdit(){
        var orderMasterId = $("#order_id2")[0].value;
        var productName = $("#productName")[0].value;
        var orderQty = $("#orderQty")[0].value;
        var orderDetailId = $("#orderDetailId")[0].value;
        var productId = $("#productId")[0].value;  
        
        $.ajax({  
                url : "orderDetailUpdate",  
                type : "POST",  
                datatype:"json",  
                data : {orderMasterId:orderMasterId,productName:productName,orderQty:orderQty,orderDetailId:orderDetailId,productId:productId},  
                success : function(data, stats) {  
                    if (stats === "success") {  

                        $("#up_detaillist").modal("hide");
                        var list = data.orderDetailList;
                        
                        var count = -1;
                        $("#orderDetailTable").find("tr").each(function(){
                            count++;
                        });
                        
                        var row_count = 10 - count;
                        for(var m = 0;m < row_count;m++){
                            tb = document.getElementById("orderDetailTable");
                            new_row = tb.insertRow();
                            for(var cell_count=0;cell_count<6;cell_count++){
                                new_row.insertCell();
                            }
                        }
                        
                    $("#orderDetailTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this)[0].innerHTML = null;
                        });
                    });
                        
                        var i = -1;
                        $("#orderDetailTable").find("tr").each(function(){

                            var j = 0;
                            $(this).find("td").each(function(){
                                $(this)[0].innerHTML = list[i][j];
                                j++;
                            });
                            i++;
                        });
                        $("#detailPageNo")[0].value = 1;
                        $("#detailTotalPage")[0].value = data.totalPage;
                    }  
                },  
                error : function(data) {  
                    alert("失败");  
                }  
            });
        
    }
    //新增订单详细信息
    function orderDetailAdd(){
        var productId = $("#productId_add").val();
        var orderQty = $("#orderQty_add")[0].value;
        
        $.ajax({  
                url : "orderDetailAdd",  
                type : "POST",  
                datatype:"json",  
                data : {productId:productId,orderQty:orderQty},  
                success : function(data, stats) {  
                    if (stats === "success") {  
                        
                        $("#add_detaillist").modal("hide");
                        var list = data.orderDetailList;
                        
                        var count = -1;
                        $("#orderDetailTable").find("tr").each(function(){

                            count++;
                        });
                        
                        var row_count = 10 - count;
                        for(var m = 0;m < row_count;m++){
                            
                            tb = document.getElementById("orderDetailTable");
                            new_row = tb.insertRow();
                            for(var cell_count=0;cell_count<6;cell_count++){
                                new_row.insertCell();
                            }
                        }


                    $("#orderDetailTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this)[0].innerHTML = null;
                        });
                    });
                        
                        var i = -1;
                        $("#orderDetailTable").find("tr").each(function(){

                            var j = 0;
                            $(this).find("td").each(function(){
                                $(this)[0].innerHTML = list[i][j];
                                j++;
                            });
                            i++;
                        });
                        $("#detailPageNo")[0].value = 1;
                        $("#detailTotalPage")[0].value = data.totalPage;
                    }  
                },  
                error : function(data) {  
                    alert("失败");  
                }  
            });
        
    }
    
    //获取客户ID
    function getCustomer(value){
        $("#customer_Id").val(value);
    }
    
    //返回订单列表
    function refresh(){
        window.location = "<%=basePath%>orderList/queryList";
    }
    
    //订单获取下一页数据
    function order_next(){

        var pageNo = parseInt($("#pageNo")[0].value);
        var totalPage = parseInt($("#totalPage")[0].value);
        
        if(pageNo >= totalPage){
            return false;
        }
        window.location = "<%=basePath%>orderList/orderQueryNext?pageNo="+ pageNo +"";
        }
        
    //订单上一页 
    function order_pre(){
        
        var pageNo = parseInt($("#pageNo")[0].value);
        if(pageNo <= 1){
            return false;
        }
         window.location = "<%=basePath%>orderList/orderQueryPre?pageNo="+ pageNo +"";
    }
    
    //获取订单详细下一页数据
    function detail_next(){
        var pageNo = parseInt($("#detailPageNo")[0].value);
        var totalPage = parseInt($("#detailTotalPage")[0].value);
        
        if(pageNo >= totalPage){
            return false;
        }else{
            $("#detailPageNo")[0].value = pageNo + 1; 
        }
        
        $.ajax({  
                url : "queryNextOD",  
                type : "get",  
                datatype:"json",  
                data : {pageNo:pageNo},  
                success : function(data, stats) {  
                    if (stats === "success") {  
                        
                    if(data.length < 10){
                    $("#orderDetailTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this)[0].innerHTML = null;
                        });
                    })
                    }
                        var i = -1;
                        $("#orderDetailTable").find("tr").each(function(){

                            var j = 0;
                            $(this).find("td").each(function(){

                                    $(this)[0].innerHTML = null;
                                    $(this)[0].innerHTML = data[i][j];
                                     j++;
                            });
                            i++;
                        });
                    }  
                },  
                error : function(data) {  
                    alert("失败");  
                }  
            });
        }
    
    //订单详细上一页 
    function detail_pre(){
        
        var pageNo = parseInt($("#detailPageNo")[0].value);
        if(pageNo <= 1){
            return false;
        }else{
            $("#detailPageNo")[0].value = pageNo - 1; 
        }
        
         $.ajax({  
                url : "queryPreOD",  
                type : "get",  
                datatype:"json",  
                data : {pageNo:pageNo},  
                success : function(data, stats) {  
                    if (stats === "success") {  
                        
                        var i = -1;
                        $("#orderDetailTable").find("tr").each(function(){

                            var j = 0;
                            $(this).find("td").each(function(){
                                $(this)[0].innerHTML = data[i][j];
                                j++;
                            });
                            i++;
                        });
                    }  
                },  
                error : function(data) {  
                    alert("失败");  
                }  
            });
    }
    
//    //修改管理员密码
//    function editPassword(){
//        var passwordOld = $("#passwordOld")[0].value;
//        var passwordNew = $("#passwordNew")[0].value;
//        var passwordConfirm = $("#passwordConfirm")[0].value;
//    
//        $.ajax({  
//            url : "/coms1.2/login/editPassword",  
//            type : "post",  
//            datatype:"json",  
//            data : {passwordOld:passwordOld,passwordNew:passwordNew,passwordConfirm:passwordConfirm},  
//            success : function(data, stats) {  
//                if (stats === "success") {  
//                    if(data === "success"){
//                        $("#passwordEdit").modal("hide");
//                        alert("密码修改成功");
//                        window.location = "<%=basePath%>login";
//                    }else if(data === "confirm error"){
//                        $("#message").html("两次输入的密码不一致");
//                    }
//                else{
//                    $("#message").html("原密码错误");
//                }
//                }  
//            },  
//            error : function(data) {  
//                alert("失败");  
//            }  
//        });
//    }
    
    //注销
    function logout(){
    window.location.href = "/order/login/logout";
    }
    
    //检查Session
    $(function (){
        var session = "<%=(User)session.getAttribute("user")%>";
        if(session === null){
             window.location = "<%=basePath%>login";
        }
    });
    
    //订单头档新增表单验证
    $("#master_required").click(function(){
        var masterParent = $("#master_div").parent();
        masterParent.find(".formtips").remove();
        var selectValue = $("#customerSelect").find("option:selected").text();
        if(selectValue ==="请选择客户" || selectValue ===""){
            var errorMsg = '*请选择客户！';
            masterParent.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
            return false;
        }   
    });
    
    //订单详细新增表单验证
    $("#detail_required").click(function(){
        var reg=/^[1-9]\d*$|^0$/;
        var addParent = $("#add_div").parent();
        var detailParent = $("#detail_div").parent();
        detailParent.find(".formtips").remove();
        addParent.find(".formtips").remove();
        var selectValue = $("#productId_add").find("option:selected").text();
        var numValue = $("#orderQty_add").val();
        if(selectValue ==="请选择产品" || selectValue ===""){
            var errorMsg = '*请选择产品！';
            detailParent.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
            return false;
        } 
        if(numValue === "" || !reg.test(numValue)){
            var errorMsg = '*请正确输入产品数量！';
            addParent.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
            return false;
        }
        orderDetailAdd();
    });
    
    //订单详细修改表单验证
    $("#editdetail").click(function(){
        var reg=/^[1-9]\d*$|^0$/;
        var qtyValue = $("#orderQty").val();
        var upParent = $("#qty_div").parent();
        upParent.find(".formtips").remove();
        if(qtyValue === "" || !reg.test(qtyValue)){
            var errorMsg = '*请正确输入产品数量！';
            upParent.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
            return false;
        }
        orderDetailEdit();
    });
    
     //修改密码验证
    $('#savepassword').click(function(){
            var pass1 = $('#passwordOld').parent();
            var pass2 = $('#passwordNew').parent();
            var pass3 = $('#passwordConfirm').parent();
            var passwordold = $("#passwordOld").val();
            //var session = "<%=session.getAttribute("password_Old")%>"; 
            var session = "<%=((User)session.getAttribute("user")).getPassword()%>"; 
            pass1.find(".formtips").remove();
            pass2.find(".formtips").remove();
            pass3.find(".formtips").remove();
            if( passwordold === ""){
                var errorMsg = '*请输入原密码！';
                pass1.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }else{
                if(passwordold !== session){
                    var errorMsg = '原密码输入错误！';
                    pass1.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                    return false;
                }
            }
            if($("#passwordNew").val() === ""){
                var errorMsg = '*请输入新密码！';
                pass2.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }else{
                if( $("#passwordNew").val().length < 6 || $("#passwordNew").val().length > 12){
                    var errorMsg = '*密码长度为6到12位！';
                    pass2.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                    return false;
                }
            }
            if($("#passwordConfirm").val() === ""){
                var errorMsg = '*请再次输入新密码！';
                pass3.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }else{
                if( $("#passwordNew").val() !== $("#passwordConfirm").val()){
                    var errorMsg = '*2次输入的密码不一致！';
                    pass3.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                    return false;
                }
            }
            editPassword();
        });
    $("#orderRequiredID").keyup(function(){   
                    $(this).val($(this).val().replace(/\D|^0/g,''));  
        }).bind("paste",function(){  //CTR+V事件处理    
                    $(this).val($(this).val().replace(/\D|^0/g,''));     
           }).css("-webkit-ime-mode", "disabled"); //CSS设置输入法不可用    
           
   $("#orderQty_add").keyup(function(){   
                    $(this).val($(this).val().replace(/\D|^0/g,''));  
        }).bind("paste",function(){  //CTR+V事件处理    
                    $(this).val($(this).val().replace(/\D|^0/g,''));     
           }).css("-webkit-ime-mode", "disabled"); //CSS设置输入法不可用  
    $("#orderQty").keyup(function(){   
                    $(this).val($(this).val().replace(/\D|^0/g,''));  
        }).bind("paste",function(){  //CTR+V事件处理    
                    $(this).val($(this).val().replace(/\D|^0/g,''));     
           }).css("-webkit-ime-mode", "disabled"); //CSS设置输入法不可用 
</script>
</body>
</html>