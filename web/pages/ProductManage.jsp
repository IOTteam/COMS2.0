<%-- 
    Document   : productInfo
    Created on : 2016-8-18, 11:37:11
    Author     : dell
--%>

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
<!--设置table行点击背景颜色-->
<style type="text/css">
.color {
    background-color: #99ffff;
}
</style>
<script type="text/javascript">
var temptr = $();
$(function(){
    $("#productTable").on("click","tr",function(event){
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
                        <li id="time"></li>	
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
            <h3 id="myModalLabel">用户信息</h3><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
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
    <!--产品信息主体--> 
    <section class="Hui-article-box">
        <div class="page-container">
        <form action="loadProductMaster" method="post">
            <div id="ss">
                <h3 align="center">产品信息列表</h3>
                <br/>
		    <p align="center">
                        产品编号：<input type="text" name="productId" class="input-text radius" style="width:100px" id="productRequiredID"/>
                        产品名称：<input type="text" name="productName" class="input-text radius" style="width:100px" value="${searchCondition.productName}"/>
                        产品规格：<input type="text" name="productSpec" class="input-text radius" style="width:100px" value="${searchCondition.productSpec}"/>
                        标准售价：<input type="text" name="priceMin" id="priceMin" class="input-text radius" style="width:100px" value="${searchCondition.priceMin}"/> - 
                                <input type="text" name="priceMax" id="priceMax" class="input-text radius" style="width:100px" value="${searchCondition.priceMax}"/>
                                <input class="btn btn-primary radius"  type="submit" value="查询" id="query_product"/>
                                <a data-toggle="modal" class="btn btn-primary radius" href="#addInfo" onclick="addProduct()">新增</a>
                                <a data-toggle="modal" id="up_id" class="btn btn-default radius" onclick="updateProduct()">修改</a>
                                <a data-toggle="modal" id="del_id" class="btn btn-default radius">删除</a>
                    </p>
            </div>
        </form>
        <table class="table table-border table-bordered table-hover" id="productTable">
            <tr onclick="">
		    <td style="width:100px">产品编号</td> 
                    <td style="width:100px">产品名称</td>  
                    <td style="width:100px">产品规格</td> 
                    <td style="width:100px">产品单价</td> 
                    <td hidden="true" style="width:100px">产品主键</td>
                </tr>
                <c:forEach items="${productMasterList}" var ="productMaster">
                    <tr style="height: 38px">
                    <td style="width:100px">${productMaster.productId}</td>
                    <td style="width:100px">${productMaster.productName}</td> 
                    <td style="width:100px">${productMaster.productSpec}</td>
                    <td style="width:100px">${productMaster.productPrice}</td>
                    <td hidden="true" style="width:100px">${productMaster.productMasterId}</td>
                </tr>
                </c:forEach>
        </table>
        <br/>
        <div>
            <div id="pager" align="center">
                共<input type="text" class="input-text radius" name="totalpages" style="width:25px;height:25px" id="totalpages" value="${totalpages}"/>页   
                <input type="button" name="" value="上一页" onClick="per()"/>
                <input type="text" class="input-text radius" name="pageNumber" style="width:25px;height:25px" id="pageNumber"  value="${pageNumber}"/>
                <input type="hidden" name="pageSize" id="pageSize" value="10"/>
                <input type="button" value="下一页" onClick="next()"/>
            </div>
            
        </div>
      </div>
    </section>
    <!--删除产品-->
    <div id="deleteInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel">信息提示！</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <h4 id="myModalLabel" align="center">确定删除该产品？</h4>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="del()">确定</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        </div>
    </div>
    <!--新增产品-->
    <div id="addInfo" class="modal2 hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div align="center" class="modal-header">
            <h2 id="myModalLabel"><small>产品新增管理</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();" onclick="refresh()">×</a>
        </div>
        <div class="modal-body">
            <form action="addProductMaster" method="post">
                <div id="productTable">
                    <div class="formControls col-xs-5">
                        产品编号：<input type="text" name="productId" id="productId" value="<%=session.getAttribute("productId1")%>" readonly="readonly" class="input-text disabled radius"/>
                    </div>
                    <div class="formControls col-xs-5">
                        产品名称：<input type="text" name="productName" id="productName" class="input-text radius" />
                    </div>
                    <div class="formControls col-xs-5">
                        产品规格：<input type="text" name="productSpec" id="productSpec" class="input-text radius" /> 
                    </div>
                    <div class="formControls col-xs-5">
                        产品单价：<input type="text" name="productPrice" id="productPrice" class="input-text radius" />
                    </div>
                    <div class="formControls col-xs-5">
                        优惠选择：<select class="select" style="width:150px;" size="1" name="status" id="status" placeholder="此产品是否优惠" onchange="addch(this.options[this.options.selectedIndex].value)">
                                <option value="2">否</option>
                                <option value="1">是</option> 
                             </select>
                    </div>
                    <div class="formControls col-xs-5">
                        <input type="text" hidden="true" name="productMasterId" id="productMasterId" value="<%=session.getAttribute("productMasterId1")%>" class="input-text radius"/>
                    </div>
                </div>
                <div class="formControls col-xs-11">
                    <h3><small>新增客户产品单价信息：</small></h3>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <span class="select-box" id="a1">
                            <select class="select" size="1" style="width:150px;" name="customerMasterId" id="customerMasterId">
                                <option value="" selected>请选择客户</option>
                            </select>
                        </span>
                    </div>
                    <div class="formControls col-xs-5" id="inpt">
                        <input id="proname" class="input-text disabled radius" readonly="readonly" name="proname" style="width:150px;height:28px;" />
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <span class="select-box" id="a2">
                            <select class="select" size="1" name="ranges" id="ranges" placeholder="输入产品数量" >
                                <option value="" selected>产品数量</option>
                                <option value="1">1-1000</option> 
                                <option value="1000">1000-2000</option>
                                <option value="2000">2000-4000</option>
                                <option value="4000">4000以上</option>  
                            </select>
                        </span> 
                    </div>
                    <div class="formControls col-xs-5">
                        <span class="select-box" id="a3">
                            <select class="select" size="1" name="rangePrice" id="rangePrice" placeholder="输入折扣信息">
                                <option value="" selected>请选择折扣</option>
                                <option value="0.9">9折</option>
                                <option value="0.8">8折</option>
                                <option value="0.7">7折</option>
                                <option value="0.6">6折</option>
                                <option value="0.5">5折</option>
                                <option value="0.4">4折</option>
                                <option value="0.3">3折</option>  
                                <option value="0.2">2折</option>
                                <option value="0.1">1折</option> 
                            </select>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <input type="button" id="add_cust" disabled="true" value="+" class="btn btn-default radius" onclick="" />
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-8">
                        <table id="priceTable" hidden="true" class="table table-border table-bordered table-striped">
                            <tr><td>客户姓名</td><td>产品编号</td><td>产品数量</td><td>优惠额度</td></tr>
                        </table>
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <input type="submit" id="send" value="新增" class="btn btn-primary radius"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!--修改产品-->
    <div id="updateInfo" class="modal2 hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div align="center" class="modal-header">
            <h2 id="myModalLabel"><small>产品修改管理</small></h2>
            <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();" onclick="refresh()">×</a>
        </div>
        <div class="modal-body">
            <form action="modifyProductMaster" method="post">
                <div id="productTable">
                    <div class="formControls col-xs-5">
                        产品编号：<input type="text" name="productId" id="product_Id" value="" readonly="readonly" class="input-text disabled radius"/>
                    </div>
                    <div class="formControls col-xs-5">
                        产品名称：<input type="text" name="productName" id="product_Name" readonly="readonly" class="input-text disabled radius"/>
                    </div>
                    <div class="formControls col-xs-5">
                        产品规格：<input type="text" name="productSpec" id="product_Spec" readonly="readonly" class="input-text disabled radius"/> 
                    </div>
                    <div class="formControls col-xs-5">
                        产品单价：<input type="text" name="productPrice" id="product_Price" class="input-text radius"/>
                    </div>
                    <div class="formControls col-xs-5">
                        优惠选择：<select class="select" style="width:150px;" size="1" name="status" id="status" placeholder="此产品是否优惠" onchange="upch(this.options[this.options.selectedIndex].value)">
                                <option value="2">否</option>
                                <option value="1">是</option> 
                             </select>
                    </div>
                    <div class="formControls col-xs-5">
                        <input type="text" hidden="true" name="productMasterId" id="productMaster_Id" class="input-text radius"/>
                    </div>
                </div>
                <div class="formControls col-xs-11">
                    <h3><small>新增客户产品单价信息：</small></h3>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <span class="select-box" id="b1">
                            <select class="select" size="1" style="width:150px;" name="customerMasterId" id="customerMaster_Id">
                                <option value="" selected>请选择客户</option>
                            </select>
                        </span>
                    </div>
                    <div class="formControls col-xs-5" id="inpt">
                        <input class="input-text disabled radius" readonly="readonly" name="proname" id="pro_name" style="width:150px;height:28px;" />
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <span class="select-box" id="b2">
                            <select class="select" size="1" name="ranges" id="_ranges" placeholder="输入产品数量" >
                                <option value="" selected>产品数量</option>
                                <option value="1">1-1000</option> 
                                <option value="1000">1000-2000</option>
                                <option value="2000">2000-4000</option>
                                <option value="4000">4000以上</option>  
                            </select>
                        </span> 
                    </div>
                    <div class="formControls col-xs-5">
                        <span class="select-box" id="b3">
                            <select class="select" size="1" name="rangePrice" id="range_Price" placeholder="输入折扣信息">
                                <option value="" selected>请选择折扣</option>
                                <option value="0.9">9折</option>
                                <option value="0.8">8折</option>
                                <option value="0.7">7折</option>
                                <option value="0.6">6折</option>
                                <option value="0.5">5折</option>
                                <option value="0.4">4折</option>
                                <option value="0.3">3折</option>  
                                <option value="0.2">2折</option>
                                <option value="0.1">1折</option> 
                            </select>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <input type="button" value="+" id="up_cust" disabled="true" class="btn btn-default radius" />
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-8">
                        <table id="up_priceTable" hidden="true" class="table table-border table-bordered table-striped">
                            <tr><td>客户姓名</td><td>产品编号</td><td>产品数量</td><td>优惠额度</td></tr>
                        </table>
                    </div>
                </div>
                <br/>
                <div class="row cl">
                    <div class="formControls col-xs-5">
                        <input type="submit" value="确定" id="_send" class="btn btn-primary radius"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
    
        <!--修改管理员密码-->            
<div id="passwordEdit" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header" align="center">
        <h2 id="myModalLabel"><small>修改密码</small></h2>
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
                    <input class="btn btn-primary radius" type="button" id="savepassword" value="保存" >
                    <input type="button" class="btn btn-primary radius" value="取消" data-dismiss="modal" aria-hidden="true">
		</div>
                
        </form>
    </div>
</div>
    
<!--<script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-Switch/bootstrapSwitch.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/lib/Validform/5.3.2/passwordStrength-min.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/com.js"></script>-->
    
<script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.js"></script> 
<script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/com.js"></script>
    <script>
 
 $(function (){
       var session = "<%=(User)session.getAttribute("user")%>"; 
       hello(session);
    });
       
    var str = [];   
    var selectValue;
    $("tr").click(function(){
        var num = 0;
        $(this).find("td").each(function(i){
            var txt = $(this).text();
            str[num] = txt;
            num++;
        });
        $('#del_id').attr('href',"##deleteInfo");//添加标签中的href属
        $("#del_id").removeClass("btn btn-default radius");
        $("#del_id").addClass("btn btn-primary radius");
        $('#up_id').attr('href',"##updateInfo");//添加标签中的href属
        $("#up_id").removeClass("btn btn-default radius");
        $("#up_id").addClass("btn btn-primary radius");
    });
    
    //产品新增
    function addProduct(){
        $.ajax({  
                url : "addProductMaster",  
                type : "get",  
                success : function(data, stats) {
                    console.dir(data);
                    //获取客户下拉列表值
                    for(var i = 0;i < data.length;i++){
                        $("#customerMasterId").append("<option value="+ data[i].customerMasterId +">"+data[i].customerMasterId+" "+data[i].customerName+"</option>");
                    }
                },  
                error : function(data) {
                    alert("查询失败！");
                }  
            });   
    }
    
    //产品修改
    function updateProduct(){
        console.dir(str);
        $("#product_Id")[0].value = str[0];
        $("#product_Name")[0].value = str[1];
        $("#product_Spec")[0].value = str[2];
        $("#product_Price")[0].value = str[3];
        $("#productMaster_Id")[0].value = str[4];
        $.ajax({  
                url : "addProductMaster",  
                type : "get",  
                success : function(data, stats) {
                    console.dir(data);
                    //获取客户下拉列表值
                    for(var i = 0;i < data.length;i++){
                        $("#customerMaster_Id").append("<option value="+ data[i].customerMasterId +">"+data[i].customerMasterId+" "+data[i].customerName+"</option>");
                    }
                },  
                error : function(data) {
                    alert("查询失败！");
                }  
            });
    }
    
    //产品删除
    function del(){
        $.ajax({  
                url : "isIemoveProductMaster",  
                type : "get",  
                datatype:"json",  
                data:{productId:str[0]},  
                success : function(data, stats) {  
                    if (stats === "success") {  
                       console.dir("data="+data);
                       if(data === 1){
                           alert("该产品有订单不能删除！")
                       }else{
                           window.location = "<%=basePath%>productMaster/removeProductMaster?productId="+str[0]+"";
                       }
                    }  
                },  
                error : function(data) {  
                    alert("删除失败！");  
                }  
        });
    }
    
    //新增客户产品单价
    function addPreferential(){
        var customerMasterId =  $("#customerMasterId")[0].value;
        var proname = $("#proname")[0].value;
        var ranges =  $("#ranges")[0].value;
        var rangePrice = $("#rangePrice")[0].value; 
        
        $.ajax({  
                url : "setCustomerPrice",  
                type : "Post",  
                datatype:"json",  
                data : {customerMasterId:""+ customerMasterId +"",ranges:""+ ranges +"", rangePrice:""+ rangePrice +"",
                    proname:"" + proname + ""},  
                success : function(data, stats) {  
                    if (stats === "success") {  
                        //table中新建行列
                        $("#priceTable");
                        tb = document.getElementById("priceTable");
                        tb.hidden = false;
                        new_row = tb.insertRow();
                        new_cell1 = new_row.insertCell();
                        new_cell2 = new_row.insertCell();
                        new_cell3 = new_row.insertCell();
                        new_cell4 = new_row.insertCell();
                        //新建行列中插入信息
                        new_cell1.innerHTML = customerMasterId;
                        new_cell2.innerHTML = proname;
                        new_cell3.innerHTML = ranges;
                        new_cell4.innerHTML = rangePrice;
                    }  
                },  
                error : function(data) {  
                    alert("查询失败！");  
                }  
            });
    }
    
    function upPreferential(){
        var customerMasterId =  $("#customerMaster_Id")[0].value;
        var proname = $("#pro_name")[0].value;
        var ranges =  $("#_ranges")[0].value;
        var rangePrice = $("#range_Price")[0].value; 
        
        $.ajax({  
                url : "setCustomerPrice",  
                type : "Post",  
                datatype:"json",  
                data : {customerMasterId:""+ customerMasterId +"",ranges:""+ ranges +"", rangePrice:""+ rangePrice +"",
                    proname:"" + proname + ""},  
                success : function(data, stats) {  
                    if (stats === "success") {  
                        //table中新建行列
                        $("#up_priceTable");
                        tb = document.getElementById("up_priceTable");
                        tb.hidden = false;
                        new_row = tb.insertRow();
                        new_cell1 = new_row.insertCell();
                        new_cell2 = new_row.insertCell();
                        new_cell3 = new_row.insertCell();
                        new_cell4 = new_row.insertCell();
                        //新建行列中插入信息
                        new_cell1.innerHTML = customerMasterId;
                        new_cell2.innerHTML = proname;
                        new_cell3.innerHTML = ranges;
                        new_cell4.innerHTML = rangePrice;
                    }  
                },  
                error : function(data) {  
                    alert("查询失败！");  
                }  
            });
    }
    
    //下一页
    function next(){
        var pageNumber = parseInt($("#pageNumber")[0].value);
        var totalpages = parseInt($("#totalpages")[0].value);
        save1 = document.getElementById("ss");
        save2 = save1.getElementsByTagName("input");
        var productSpec = save2[2].getAttribute("value");
        var productName = save2[1].getAttribute("value");
        var priceMax = save2[4].getAttribute("value");
        var priceMin = save2[3].getAttribute("value");
        if(pageNumber < totalpages){
            pageNumber = pageNumber + 1;
            window.location = "<%=basePath%>productMaster/loadProductMaster?pageNumber="+pageNumber+"&priceMin="+priceMin+"&priceMax="+priceMax+"&productSpec="+encodeURI(encodeURI(productSpec))+"&productName="+encodeURI(encodeURI(productName))+"";
        }
        
    }
    
    //上一页       
    function per(){
        var pageNumber = parseInt($("#pageNumber")[0].value);
        save1 = document.getElementById("ss");
        save2 = save1.getElementsByTagName("input");
        var productSpec = save2[2].getAttribute("value");
        var productName = save2[1].getAttribute("value");
        var priceMax = save2[4].getAttribute("value");
        var priceMin = save2[3].getAttribute("value");
        if(pageNumber > 1){
            pageNumber = pageNumber -1;
            window.location = "<%=basePath%>productMaster/loadProductMaster?pageNumber="+pageNumber+"&priceMin="+priceMin+"&priceMax="+priceMax+"&productSpec="+encodeURI(encodeURI(productSpec))+"&productName="+encodeURI(encodeURI(productName))+"";
        }
        
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
    
    //刷新商品列表
    function refresh(){
        window.location = "<%=basePath%>productMaster/loadProductMaster";
    }
    
    //产品级联
    function upch(value){
        if(value==1){
            var pro_Name = $("#product_Name").val(); 
            $("#pro_name")[0].value = pro_Name;
            $('#up_cust').attr("disabled",false); 
            $("#up_cust").addClass("btn btn-primary radius");
        }else{
            $('#up_cust').attr("disabled",true); 
            $("#up_cust").removeClass("btn btn-primary radius");
            $("#up_cust").addClass("btn btn-default radius");
        }
    }
    function addch(value){
        selectValue = value;
        console.dir(selectValue);
        if(value==1){
            var proName = $("#productName").val(); 
            $("#proname")[0].value = proName;
            $('#add_cust').attr("disabled",false); 
            $("#add_cust").addClass("btn btn-primary radius");
        }else{
            $('#add_cust').attr("disabled",true); 
            $("#add_cust").removeClass("btn btn-primary radius");
            $("#add_cust").addClass("btn btn-default radius");
        }
    }
    
    $(document).ready(function(){
        $("input[id='productName']").keydown(function(){
            console.dir(selectValue);
            if( selectValue == 1){
                $("#productName").blur(function (){
                    var proName = $("#productName").val(); 
                    console.dir("proName");
                    $("#proname")[0].value = proName;
                });
            }
        });
    });
    
    //注销
    function logout(){
        window.location.href = "/order/login/logout";
    }
    
    //提交，最终验证。
    $(function(){
        //新增验证
        $('#send').click(function(){
            var productName = $('#productName').parent();
            var productSpec = $('#productSpec').parent();
            var productPrice = $('#productPrice').parent();
            var reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^[0]{1}(\.\d{1,2})?$)/;  
            var price = $('#productPrice').val();
            productName.find(".formtips").remove();
            productSpec.find(".formtips").remove();
            productPrice.find(".formtips").remove();
            if( $('#productName').val() === ""){
                var errorMsg = '*请输入产品名称！';
                productName.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#productSpec').val() === ""){
                var errorMsg = '*请输入产品规格！';
                productSpec.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if ( price === "") {  
                var errorMsg = '*请输入产品价格！';
                productPrice.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;  
            } else {  
                if (!reg.test(price)) {  
                    var errorMsg = '*价格必须为合法数字(正数，最多两位小数)！';
                    productPrice.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                    return false;  
                } else {  
                    return true;  
                }  
            } 
        });
        $('#add_cust').click(function(){
            var customerMasterId = $('#a1').parent();
            var proname = $('#proname').parent();
            var ranges = $('#a2').parent();
            var rangePrice = $('#a3').parent();
            customerMasterId.find(".formtips").remove();
            proname.find(".formtips").remove();
            ranges.find(".formtips").remove();
            rangePrice.find(".formtips").remove();
            if( $('#customerMasterId').val() === ""){
                var errorMsg = '*请选择客户！';
                customerMasterId.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#proname').val() === ""){
                var errorMsg = '*请输入产品名称！';
                proname.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#ranges').val() === ""){
                var errorMsg = '*请选择产品数量！';
                ranges.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#rangePrice').val() === ""){
                var errorMsg = '*请选择产品折扣！';
                rangePrice.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            addPreferential();
        });
        
        //修改验证
        $('#_send').click(function(){
            var product_Price = $('#product_Price').parent();
            var reh = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;  
            var _price = $('#product_Price').val();
            product_Price.find(".formtips").remove();
            if ( _price === "") {  
                var errorMsg = '*请输入产品价格！';
                product_Price.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;  
            } else {  
                if (!reh.test(_price)) {  
                    var errorMsg = '*价格必须为合法数字(正数，最多两位小数)！';
                    product_Price.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                    return false;  
                } else {  
                    return true;  
                }  
            }
        });
        $('#up_cust').click(function(){
            var customerMaster_Id = $('#b1').parent();
            var pro_name = $('#pro_name').parent();
            var _ranges = $('#b2').parent();
            var range_Price = $('#b3').parent();
            customerMaster_Id.find(".formtips").remove();
            pro_name.find(".formtips").remove();
            _ranges.find(".formtips").remove();
            range_Price.find(".formtips").remove();
            if( $('#customerMaster_Id').val() === ""){
                var errorMsg = '*请选择客户！';
                customerMaster_Id.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#pro_name').val() === ""){
                var errorMsg = '*请输入产品名称！';
                pro_name.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#_ranges').val() === ""){
                var errorMsg = '*请选择产品数量！';
                _ranges.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            if( $('#range_Price').val() === ""){
                var errorMsg = '*请选择产品折扣！';
                range_Price.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                return false;
            }
            upPreferential();
        });
    });
    
    //修改密码验证
    $('#savepassword').click(function(){
            var pass1 = $('#passwordOld').parent();
            var pass2 = $('#passwordNew').parent();
            var pass3 = $('#passwordConfirm').parent();
            var passwordold = $("#passwordOld").val();
            //var session = "<%=session.getAttribute("password_Old")%>"; 
            var session = "<%=((User)session.getAttribute("user")).getPassword()%>"; 
            console.dir("密码："+passwordold);
            console.dir("原密码："+session);
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
    
    //产品编号查询验证
    $("#productRequiredID").keyup(function(){   
                    $(this).val($(this).val().replace(/\D|^0/g,''));  
        }).bind("paste",function(){  //CTR+V事件处理    
                    $(this).val($(this).val().replace(/\D|^0/g,''));     
           }).css("-webkit-ime-mode", "disabled"); //CSS设置输入法不可用   
          
//    //产品起始价格查询验证
//    $("#priceMin").keyup(function(){    
//                    $(this).val($(this).val().replace(/(^[1-9]\d*(\.\d{1,2})?$)|(^[0]{1}(\.\d{1,2})?$)/));    
//                }).bind("paste",function(){  //CTR+V事件处理    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));     
//                }).css("ime-mode", "disabled"); //CSS设置输入法不可用 
//    
//    //产品终止价格查询验证
//    $("#priceMax").keyup(function(){    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));    
//                }).bind("paste",function(){  //CTR+V事件处理    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));     
//                }).css("ime-mode", "disabled"); //CSS设置输入法不可用
//                
//    $("#product_Price").keyup(function(){    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));    
//                }).bind("paste",function(){  //CTR+V事件处理    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));     
//                }).css("ime-mode", "disabled"); //CSS设置输入法不可用
//    $("#productPrice").keyup(function(){    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));    
//                }).bind("paste",function(){  //CTR+V事件处理    
//                    $(this).val($(this).val().replace(/[^0-9.]/g,''));     
//                }).css("ime-mode", "disabled"); //CSS设置输入法不可用
  
//修改验证
        $('#query_product').click(function(){
            var minPrice = $('#priceMin').parent();
            var maxPrice = $('#priceMax').parent();
            var reh = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;  
            var min_price = $('#priceMin').val();
            var max_price = $('#priceMax').val();
            minPrice.find(".formtips").remove();
            maxPrice.find(".formtips").remove();
            if (min_price !== "" && !reh.test(min_price)) {  
                    var errorMsg = '*价格起价必须为合法数字(正数，最多两位小数)！';
                    alert(errorMsg);
                    return false;  
            }
            if(max_price !== "" && !reh.test(max_price)){
                    var errorMsg = '*价格终价必须为合法数字(正数，最多两位小数)！';
//                    maxPrice.append('<span style="color:red" class="formtips onError">'+errorMsg+'</span>');
                    alert(errorMsg);
                    return false;  
            }
           
        });
       
    
</script>  
</body>
</html>
