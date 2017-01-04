<%-- 
    Document   : productInfo
    Created on : 2016-12-16, 8:41:01
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui/css/H-ui.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>pages/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="ProductQuery" method="post">
            <div id="ss">
                <h3 align="center">产品信息列表</h3>
		    <p align="center">
                        产品编号：<input type="text" name="productIdMin" class="input-text radius" style="width:100px" id="productIdMin" value="${queryCondition.productIdMin}"/>
                                - <input type="text" name="productIdMax" class="input-text radius" style="width:100px" id="productIdMax" value="${queryCondition.productIdMax}"/>
                        产品名称：<input type="text" id="productName" name="productName" class="input-text radius" style="width:100px" value="${queryCondition.productName}"/>
                        产品规格：<input type="text" id="productSpec" name="productSpec" class="input-text radius" style="width:100px" value="${queryCondition.productSpec}"/>
                        标准售价：<input type="text" name="productPriceMin" id="productPriceMin" class="input-text radius" style="width:100px" value="${queryCondition.productPriceMin}"/> - 
                                <input type="text" name="productPriceMax" id="productPriceMax" class="input-text radius" style="width:100px" value="${queryCondition.productPriceMax}"/>
                                <input   type="submit" value="查询" id="query_product"/>
                                <button  type="button" data-toggle="modal" href="#addInfo" class="btn btn-primary radius" >新增</button>
                    </p>
            </div>                        
        </form>
            <table class="table table-border table-bordered table-hover" id="productTable">
                <tr>
                        <td style="width:100px">产品编号</td> 
                        <td style="width:100px">产品名称</td>  
                        <td style="width:100px">产品规格</td> 
                        <td style="width:100px">产品单价</td> 
                        <td hidden="true">产品主键</td>
                        <td hidden="true">優惠屬性</td>
                        <td style="width: 100px">操作</td>
                    </tr>
                    <c:forEach items="${productList}" var ="productMaster">
                        <tr style="height: 38px">
                            <td style="width:100px">${productMaster.productId}</td>
                            <td style="width:100px">${productMaster.productName}</td> 
                            <td style="width:100px">${productMaster.productSpec}</td>
                            <td style="width:100px">${productMaster.productStandardPrice}</td>
                            <td hidden="true">${productMaster.productMasterId}</td>
                            <td hidden="true">${productMaster.discountStatus}</td>
                            <td style="width: 100px">
                                <button class="modifybtn" type="button" style="width: 50px;height: 30px;background-color: #5a98de;color: #FFF;border-color:#5a98de" data-toggle="modal" href="#modifyProductInfo">修改</button>
                                <button class="deletebtn" type="button" style="width: 50px;height: 30px;background-color: #5a98de;color: #FFF;border-color:#5a98de" data-toggle="modal" href="#deleteProductInfo">刪除</button>
                            </td>
                        </tr>
                    </c:forEach>
            </table>
            <div align="center">
                <p> <input id="page_up" type="button" value="上一頁" onclick="per()"/>
                <input class="input-text radius" type="text" id="pageNo" value="${queryCondition.pageNo}" readonly="true" style="width:30px" />/
                <input class="input-text radius" type="text" id="totalPages" value="${totalPages}" readonly="true" style="width:30px" />
                <input id="page_down" type="button" value="下一頁" onclick="next()"/>
                </p>
            </div>   
                
            <div id="addInfo" class="modal2 hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form>
                        <div align="center" class="modal-header">
                            <h2 id="myModalLabel"><small>产品新增管理</small></h2>
                        </div>
                        <div class="modal-body">
                            <div id="productTable">
                                <div class="formControls col-xs-5">
                                    产品名称：<input type="text" name="productNameNew" id="productNameNew" class="input-text radius" />
                                </div>
                                <div class="formControls col-xs-5">
                                    产品规格：<input type="text" name="productSpecNew" id="productSpecNew" class="input-text radius" /> 
                                </div>
                                <div class="formControls col-xs-5">
                                    产品单价：<input type="text" name="productStandardPriceNew" id="productStandardPriceNew" class="input-text radius" />
                                </div>
                                <div class="formControls col-xs-5">
                                    优惠选择：<select class="select" style="width:150px;" size="1" name="discountStatusNew" id="discountStatusNew" placeholder="此产品是否优惠" >
                                            <option value="0">否</option>
                                            <option value="1">是</option> 
                                         </select>
                                </div>
                            </div>    
                        </div>
                        <div class="row cl">
                            <div class="formControls col-xs-5">
                                <input type="button" id="send" value="新增" class="btn btn-primary radius" aria-hidden="true" onclick="addProduct()"/>                    
                            </div>
                        </div>
                    </form>
                </div>
            </div>
                
            <div id="addCustomerPriceInfo" class="modal2 hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form>
                        <div class="formControls col-xs-11">
                            <h3><small>新增客户产品单价信息：</small></h3>
                        </div>
                            <div class="formControls col-xs-5" style="width: 600px">
                                        <span class="select-box" id="a1">
                                            客戶名稱：
                                            <select class="select" size="1" style="width:150px;" name="customerMasterIdAdd" id="customerMasterIdAdd">
                                                <option value="" selected>请选择客户</option>
                                            </select>
                                        </span>
                                    </div>
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <div class="formControls col-xs-5" id="inpt">
                                        產品編號：<input id="productIdAdd" class="input-text disabled radius" readonly="readonly" name="productIdAdd" style="width:150px;height:28px;" value="${customerPriceMap.productId}" />
                                    </div>
                                    <div class="formControls col-xs-5" id="inpt">
                                        產品名稱：<input id="productNameAdd" class="input-text disabled radius" readonly="readonly" name="productNameAdd" style="width:150px;height:28px;" value="${customerPriceMap.productName}"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="formControls col-xs-5" id="inpt" style="width: 600px">
                                        <input type="hidden" id="productMasterIdAdd" name="productMasterIdAdd" value="${customerPriceMap.productMasterId}"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="formControls col-xs-5" style="width: 600px">
                                        <span class="select-box" id="a2">
                                            产品數量最小值：<input type="text" name="rangeMin" id="rangeMin" class="input-text radius" style="width: 160px" />-
                                            產品數量最大值：<input type="text" name="rangeMax" id="rangeMax" class="input-text radius" style="width: 160px"/>
                                        </span> 
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="formControls col-xs-5" style="width: 600px">
                                        <span class="select-box" id="a3">
                                            产品单价：<input type="text" name="rangePrice" id="rangePrice" class="input-text radius" style="width: 300px"/>
                                        </span>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div class="row cl">
                            <div class="formControls col-xs-5">
                                <input type="button" id="add_cust" value="添加" class="btn btn-default radius" onclick="addCusPrice()" />
                            </div>
                        </div>
                        <div class="row cl">
                            <div class="formControls col-xs-8">
                                <table id="priceTableAdd" hidden="true" class="table table-border table-bordered table-striped" style="width: 600px">
                                    <tr><td>客户姓名</td><td>产品编号</td><td>产品最小數量</td><td>产品最大數量</td><td>优惠额度</td></tr>
                                </table>
                            </div>
                        </div>
                        <div class="row cl">
                            <div class="formControls col-xs-5">
                                <input type="button" id="sendCustomerPrice" value="新增" class="btn btn-primary radius" data-dismiss="modal" aria-hidden="true"/>                    
                            </div>
                        </div>
                    </form>
                </div>
            </div>
                
            <div id="modifyProductInfo" class="modal2 hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form>
                        <div id="productTable">
                            <div class="formControls col-xs-5">
                                产品编号：<input type="text" name="productIdOld" id="productIdOld"  readonly="readonly" class="input-text disabled radius"/>
                            </div>
                            <div class="formControls col-xs-5">
                                产品名称：<input type="text" name="productNameOld" id="productNameOld"  readonly="readonly" class="input-text disabled radius"/>
                            </div>
                            <div class="formControls col-xs-5">
                                产品规格：<input type="text" name="productSpecOld" id="productSpecOld"  readonly="readonly" class="input-text disabled radius"/> 
                            </div>
                            <div class="formControls col-xs-5">
                                产品单价：<input type="text" name="productStandardPriceOld" id="productStandardPriceOld"  class="input-text radius"/>
                            </div>
                            <div class="formControls col-xs-5">
                                优惠选择：<select class="select" style="width:150px;" size="1" name="discountStatusOld" id="discountStatusOld" placeholder="此产品是否优惠" ">
                                     <option value="false" >否</option>
                                     <option value="true" >是</option> 
                                </select>
                            </div>
                            <div class="formControls col-xs-5">
                                <input type="text" hidden="true" name="productMasterIdOld" id="productMasterIdOld" class="input-text radius"/>
                            </div>
                        </div>
                        <div class="formControls col-xs-5">
                            <input type="button" id="send" value="修改" class="btn btn-primary radius"  aria-hidden="true" onclick="modifyProduct()"/>                    
                        </div>
                    </form>
                </div>
           </div>
                                    
            <div id="modifyCustomerPriceInfo" class="modal2 hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form>
                        <div class="formControls col-xs-11">
                            <h3><small>修改客户产品单价信息：</small></h3>
                        </div>
                        <div class="row cl">
                            <div class="formControls col-xs-5">
                                <span class="select-box" id="a1">
                                    客戶名稱：<select class="select" size="1" style="width:150px;" name="customerMasterIdModify" id="customerMasterIdModify">
                                                <option value="" selected>请选择客户</option>
                                            </select>
                                </span>
                            </div>
                            <div class="formControls col-xs-5" id="inpt">
                                產品編號：<input id="productIdModify" class="input-text disabled radius" readonly="readonly" name="productIdModify" style="width:150px;height:28px;" value="${customerPriceMap.productId}" />
                            </div>
                            <div class="formControls col-xs-5" id="inpt">
                                產品名稱：<input id="productNameModify" class="input-text disabled radius" readonly="readonly" name="productNameModify" style="width:150px;height:28px;" value="${customerPriceMap.productName}"/>
                            </div>
                            <div class="formControls col-xs-5" id="inpt">
                                <input type="hidden" id="productMasterIdModify" name="productMasterIdModify"/>
                            </div>
                        </div>
                                <div class="row cl">
                                    <div class="formControls col-xs-5">
                                        <span class="select-box" id="a2">
                                            产品數量最小值：<input type="text" name="rangeMinModify" id="rangeMinModify" class="input-text radius" />-
                                            產品數量最大值：<input type="text" name="rangeMaxModify" id="rangeMaxModify" class="input-text radius" />
                                        </span> 
                                    </div>
                                    <div class="formControls col-xs-5">
                                        <span class="select-box" id="a3">
                                            产品单价：<input type="text" name="rangePriceModify" id="rangePriceModify" class="input-text radius" />
                                        </span>
                                    </div>
                                </div>
                                <br/>
                            <div class="row cl">
                                <div class="formControls col-xs-5">
                                    <input type="button" id="add_cust"  value="修改" class="btn btn-default radius" onclick="modifyCusPrice()" />
                                </div>
                            </div>
                            <div class="row cl">
                                <div class="formControls col-xs-8">
                                    <table id="priceTable" hidden="true" class="table table-border table-bordered table-striped">
                                        <tr><td>客户姓名</td><td>产品编号</td><td>产品最小數量</td><td>产品最大數量</td><td>优惠额度</td></tr>
                                    </table>
                                </div>
                            </div>
                        </form>
                </div>
            </div>
            
            <div id="deleteProductInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <h4 id="myModalLabel">信息提示！</h4>
                </div>
                <div class="modal-body">
                    <h4 id="myModalLabel" align="center">确定删除该产品？</h4>
                </div>
                <div class="formControls col-xs-5" id="inpt">
                    <input type="hidden" id="deleteProductId" name="deleteProductId"/>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="deleteProduct()">确定</button>
                    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
                </div>
            </div>
            <script type="text/javascript" src="<%=basePath%>pages/lib/jquery/1.9.1/jquery.js"></script> 
            <script type="text/javascript" src="<%=basePath%>pages/static/h-ui/js/H-ui.js"></script>
            <script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
            <script type="text/javascript" src="<%=basePath%>pages/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
            <script>
                function next(){
                    var ss = document.getElementById("pageNo").value;
                    var pageNo = parseInt(ss);
                    var totalPages = document.getElementById("totalPages").value;
                    var productName = document.getElementById("productName").value;
                    var productIdMin = document.getElementById("productIdMin").value;
                    var productIdMax = document.getElementById("productIdMax").value;
                    var productSpec = document.getElementById("productSpec").value;
                    var productPriceMin = document.getElementById("productPriceMin").value;
                    var productPriceMax = document.getElementById("productPriceMax").value;
                    if(pageNo < totalPages){
                        var pageNo = pageNo + 1;
                        window.location = "<%=basePath%>Product/ProductQuery?pageNo="+pageNo+"&productIdMin="+productIdMin+"&productIdMax="+productIdMax+
                            "&productSpec="+productSpec+"&productName="+ productName +"&productPriceMin=" + productPriceMin + "&productPriceMax=" + productPriceMax + "";
                    }
                }
                
                function per(){
                    var ss = document.getElementById("pageNo").value;
                    var pageNo = parseInt(ss);
                    var productName = document.getElementById("productName").value;
                    var productIdMin = document.getElementById("productIdMin").value;
                    var productIdMax = document.getElementById("productIdMax").value;
                    var productSpec = document.getElementById("productSpec").value;
                    var productPriceMin = document.getElementById("productPriceMin").value;
                    var productPriceMax = document.getElementById("productPriceMax").value;
                    if(pageNo > 1){
                        var pageNo = pageNo - 1;
                        window.location = "<%=basePath%>Product/ProductQuery?pageNo="+pageNo+"&productIdMin="+productIdMin+"&productIdMax="+productIdMax+
                            "&productSpec="+productSpec+"&productName="+ productName +"&productPriceMin=" + productPriceMin + "&productPriceMax=" + productPriceMax + "";
                    }
                }
    
                $(document).ready(function(){
                    $(".modifybtn").click(function(){
                        var productId = $(this).parent().parent().children().eq(0).text();
                        var productName = $(this).parent().parent().children().eq(1).text();
                        var productSpec = $(this).parent().parent().children().eq(2).text();
                        var productStandardPrice = $(this).parent().parent().children().eq(3).text();
                        var productMasterId = $(this).parent().parent().children().eq(4).text();
                        var discountStatus = $(this).parent().parent().children().eq(5).text();
                        $("#productIdOld").val(productId);
                        $("#productNameOld").val(productName);
                        $("#productSpecOld").val(productSpec);
                        $("#productStandardPriceOld").val(productStandardPrice);
                        $("#productMasterIdOld").val(productMasterId);
                        if(discountStatus==="true"){
                            $("#discountStatusOld").children().eq(1).attr("selected","selected");
                        }else{
                            $("#discountStatusOld").children().eq(0).attr("selected","selected");
                        }
                    });
                    
                });
                
                $(document).ready(function(){
                    $(".deletebtn").click(function(){
                        var productId = $(this).parent().parent().children().eq(0).text();
                        $("#deleteProductId").val(productId);
                    });
                    
                });
                
                function deleteProduct(){
                    var productId = $("#deleteProductId").val();
                    window.location = "<%=basePath%>Product/deleteProduct?productId="+productId;
                }
                
                function addProduct(){
                            var productName = $("#productNameNew").val();
                            var productSpec =  $("#productSpecNew").val();
                            var productStandardPrice = $("#productStandardPriceNew").val();
                            var discountStatus = $("#discountStatusNew").val();
                            $.ajax({
                                url:"addProduct",
                                type:"Post",
                                datatype:"Json",
                                data:{productName:""+ productName +"",productSpec:""+ productSpec +"", productStandardPrice:""+ productStandardPrice +"",
                                    discountStatus:"" + discountStatus + ""},  
                                success : function(data, stats) {  
                                    if (stats === "success") {
                                        if(discountStatus==="1"){
                                            $("#addInfo").modal("hide");
                                            $("#addCustomerPriceInfo").modal("show"); 
                                            $("#productIdAdd").val(data.productId);
                                            $("#productNameAdd").val(data.productName);
                                            $("#productMasterIdAdd").val(data.productMasterId);
                                        }else{
                                            $("#addInfo").modal("hide"); 
                                            window.location = "<%=basePath%>Product/ProductQuery";
                                        }
                                        
                                    }  
                                },  
                                error : function(data) {  
                                    alert("查询失败！");  
                                }
                            });
                            
                            $("#customerMasterIdAdd").children().remove();
                            $.ajax({
                                url:"queryCustomer",
                                type:"Post",
                                datatype:"Json", 
                                success : function(data, stats) {  
                                    if (stats === "success") {
                                        for(i = 0;i < data.length;i++){
                                            $("#customerMasterIdAdd").append('<option value="'+data[i].customerMasterId+'">"'+data[i].customerName+'"</option>')
                                        }
                                        
                                    }  
                                },  
                                error : function(data) {  
                                    alert("查询失败！");  
                                }
                            });
                        }        
                function addCusPrice(){
                            var customerMasterId = $("#customerMasterIdAdd")[0].value;
                            var rangeMin = $("#rangeMin")[0].value;
                            var rangeMax =  $("#rangeMax")[0].value;
                            var rangePrice = $("#rangePrice")[0].value;
                            var productId = $("#productIdAdd")[0].value;
                            var productMasterId = $("#productMasterIdAdd")[0].value;
                            $.ajax({
                                url:"setCustomerPrice",
                                type:"Post",
                                datatype:"Json",
                                data:{customerMasterId:""+ customerMasterId +"",rangeMin:""+ rangeMin +"", rangePrice:""+ rangePrice +"",
                                    rangeMax:"" + rangeMax + "",productMasterId:""+ productMasterId + ""},  
                                success : function(data, stats) {  
                                    if (stats === "success") {  
                                        //table中新建行列
                                        $("#priceTableAdd");
                                        tb = document.getElementById("priceTableAdd");
                                        tb.hidden = false;
                                        new_row = tb.insertRow();
                                        new_cell1 = new_row.insertCell();
                                        new_cell2 = new_row.insertCell();
                                        new_cell3 = new_row.insertCell();
                                        new_cell4 = new_row.insertCell();
                                        new_cell5 = new_row.insertCell();
                                        //新建行列中插入信息
                                        new_cell1.innerHTML = customerMasterId;
                                        new_cell2.innerHTML = productId;
                                        new_cell3.innerHTML = rangeMin;
                                        new_cell4.innerHTML = rangeMax;
                                        new_cell5.innerHTML = rangePrice;
                                    }  
                                },  
                                error : function(data) {  
                                    alert("查询失败！");  
                                }
                            });
                        }
                        
                function modifyProduct(){
                    var productId = $("#productIdOld").val();
                    var productStandardPrice = $("#productStandardPriceOld").val();
                    var discountStatus = $("#discountStatusOld").val();
                    $.ajax({
                        url:"modifyProduct",
                        type:"Post",
                        datatype:"Json",
                        data:{productStandardPrice:""+ productStandardPrice +"",discountStatus:"" + discountStatus + "",productId:""+ productId +""},  
                        success : function(data, stats) {  
                            if (stats === "success") {
                                if(discountStatus==="true"){
                                    $("#modifyProductInfo").modal("hide");
                                    $("#modifyCustomerPriceInfo").modal("show"); 
                                    $("#productIdModify").val(data.productId);
                                    $("#productNameModify").val(data.productName);
                                    $("#productMasterIdModify").val(data.productMasterId);
                                }else{
                                    $("#modifyProductInfo").modal("hide");
                                    window.location = "<%=basePath%>Product/ProductQuery";
                                }
                                        
                            }  
                        },  
                        error : function(data) {  
                            alert("查询失败！");  
                        }
                    });
                            
                    $("#customerMasterIdModify").children().remove();
                    $.ajax({
                        url:"queryCustomer",
                        type:"Post",
                        datatype:"Json", 
                        success : function(data, stats) {  
                            if (stats === "success") {
                                for(i = 0;i < data.length;i++){
                                    $("#customerMasterIdModify").append('<option value="'+data[i].customerMasterId+'">"'+data[i].customerName+'"</option>')
                                }
                                        
                            }  
                        },  
                        error : function(data) {  
                            alert("查询失败！");  
                        }
                    });
                }
                
                function modifyCusPrice(){
                            var customerMasterId = $("#customerMasterIdModify")[0].value;
                            var rangeMin = $("#rangeMinModify")[0].value;
                            var rangeMax =  $("#rangeMaxModify")[0].value;
                            var rangePrice = $("#rangePriceModify")[0].value;
                            var productId = $("#productIdModify")[0].value;
                            var productMasterId = $("#productMasterIdModify")[0].value;
                            $.ajax({
                                url:"modifyCustomerPrice",
                                type:"Post",
                                datatype:"Json",
                                data:{customerMasterId:""+ customerMasterId +"",rangeMin:""+ rangeMin +"", rangePrice:""+ rangePrice +"",
                                    rangeMax:"" + rangeMax + "",productMasterId:""+ productMasterId + ""},  
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
                                        new_cell5 = new_row.insertCell();
                                        //新建行列中插入信息
                                        new_cell1.innerHTML = customerMasterId;
                                        new_cell2.innerHTML = productId;
                                        new_cell3.innerHTML = rangeMin;
                                        new_cell4.innerHTML = rangeMax;
                                        new_cell5.innerHTML = rangePrice;
                                    }  
                                },  
                                error : function(data) {  
                                    alert("查询失败！");  
                                }
                            });
                        }
            </script>
            
    </body>
</html>