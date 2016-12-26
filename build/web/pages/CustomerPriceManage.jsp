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
                <a href="<%=basePath%>index" class="logo navbar-logo f-l mr-10 hidden-xs" style="font-family: LiSu; font-size: 30px">客户订单管理系统</a>
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
                    <a href="<%=basePath%>orderList/queryList">订单列表</a>
                </li>
            </ul>
              <ul>
                <li>
                    <a href="<%=basePath%>productMaster/loadProductMaster">产品信息</a>
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
    
    <!--客户单价主体-->
    <section class="Hui-article-box">
        <div class="page-container">
            <form action="queryCustomerPrice" method="post">
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
                    <a class="btn btn-default radius" type="button" id="custPriceEditBotton" onclick="getCustomerPriceForUpdate()" data-toggle="modal" style="display: inline-block;" >修改</a>    
                </p>
            </form>
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
<!--                    <td style="width:100px"><fmt:formatNumber type="number" value="${custPrice.rangePrice}" pattern="0.000" maxFractionDigits="3"/></td>-->
                </tr>
             </c:forEach> 
            </table>
        <p>${message}</p>
        <br/>
        <div align="center">
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
<script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script>
    
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
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
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
                    var response = JSON.parse(data.responseText.toString());
                    $("#alter_message").html(response.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
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
                    var response = JSON.parse(data.responseText.toString());
                    $("#alter_message").html(response.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
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
  
                    if(data.length < 10){
                    $("#CusPriceTable").find("tr").each(function(){
                        $(this).find("td").each(function(){
                            $(this).html(null);
                        });
                    })
                    }
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
                },  
                error : function(data) {  
                    var response = JSON.parse(data.responseText.toString());
                    $("#alter_message").html(response.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                }  
            });
        }
        
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取要修改的客戶產品單價資訊
     * 
     ********************************************************************************/
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
                    var response = JSON.parse(data.responseText.toString());
                    $("#alter_message").html(response.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
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

                    $("#updateCustomerPrice").modal("toggle");
                    $("#alter_message").html(data.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
                    
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

                    var response = JSON.parse(data.responseText.toString());
                    $("#alter_message").html(response.message);
                    $("#modal-message").modal("toggle");
                    setTimeout("$(\"#modal-message\").modal(\"toggle\")",2000);
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
            })
            $(this).parents("tr").attr('class',"success");
            }
            customerPrice[0] = text;
            return false;
        });

        if(/CUSPRO[0-9]{6}/g.test(customerPrice[0])){   
            
            $('#custPriceEditBotton').attr('href',"#updateCustomerPrice");//添加标签中的href属
            $("#custPriceEditBotton").removeClass("btn btn-default radius");
            $("#custPriceEditBotton").addClass("btn btn-primary radius");
        }else{
            $('#custPriceEditBotton').removeAttr('href');
            $("#custPriceEditBotton").removeClass("btn btn-primary radius");
            $("#custPriceEditBotton").addClass("btn btn-default radius");
        }
    });

</script>
</body>
</html>