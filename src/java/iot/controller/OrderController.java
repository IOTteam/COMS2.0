/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.controller;

import iot.dao.entity.OrderDetail;
import iot.dao.entity.OrderHead;
import iot.dao.entity.Product;
import iot.dao.repository.exceptions.PreexistingEntityException;
import iot.service.OrderService;
import java.util.HashMap;
import iot.response.Response;
import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author David Su
 */
@Controller
@RequestMapping("/OrderManage")
@SessionAttributes({"queryCondition"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 進入訂單管理查詢首頁，獲取訂單頭檔列表
     *
     * @author David
     * @param orderHeadIdMin
     * @param orderHeadIdMax
     * @param customerName
     * @param orderDateMin
     * @param orderDateMax
     * @param pageNo
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "queryOrderHeadList")
    public String queryOrderHeadList(
            @RequestParam(value = "orderHeadIdMin", defaultValue = "") String orderHeadIdMin,
            @RequestParam(value = "orderHeadIdMax", defaultValue = "") String orderHeadIdMax,
            @RequestParam(value = "customerName", defaultValue = "") String customerName,
            @RequestParam(value = "orderDateMin", defaultValue = "") String orderDateMin,
            @RequestParam(value = "orderDateMax", defaultValue = "") String orderDateMax,
            @RequestParam(value = "pageNo", defaultValue = "1") String pageNo,
            ModelMap model) throws ParseException {
        //保留查詢條件1970-01-01 00:00:00 2016-12-31 23:59:59
        HashMap<String, String> queryCondition = new HashMap<>();
        queryCondition.put("orderHeadIdMin", orderHeadIdMin);
        queryCondition.put("orderHeadIdMax", orderHeadIdMax);
        queryCondition.put("customerName", customerName);
        queryCondition.put("orderDateMin", orderDateMin);
        queryCondition.put("orderDateMax", orderDateMax);
        queryCondition.put("pageNo", pageNo);

        //調用查詢函數  
        Response queryOrderHeadListResult = orderService.queryOrderHeadListService(orderHeadIdMin, orderHeadIdMax, customerName, orderDateMin, orderDateMax, pageNo);

        Response queryCountOrderHeadListResult = orderService.queryCountOrderHeadListService(orderHeadIdMin, orderHeadIdMax, customerName, orderDateMin, orderDateMax);

        if (queryOrderHeadListResult.isEmpty()) {
            model.addAttribute("message", queryOrderHeadListResult.getMessage());
            return "OrderManage";
        }
        //把條件查詢的數據結果，放到list中，傳回到前臺
        List<OrderHead> orderHeadList = (List<OrderHead>) queryOrderHeadListResult.getData();
        //通過調用條件查詢結果總數的函數，計算出查詢到的數據一共有多少條，可以分爲多少頁
        List<OrderHead> orderCountHeadList = (List<OrderHead>) queryCountOrderHeadListResult.getData();
        int totalPage = (orderCountHeadList.size() - 1) / 10 + 1;

        model.addAttribute("orderHeadList", orderHeadList);
        model.addAttribute("queryCondition", queryCondition);
        model.addAttribute("totalPage", totalPage);
        return "OrderManage";
    }

//    //查詢訂單頭檔的某一頁的的資訊，返回JSON數據
//    @RequestMapping(value = "getPageData",method = RequestMethod.GET)
//    @ResponseBody
//    public List getPageData(@ModelAttribute("queryCondition") HashMap<String,String> queryCondition,@RequestParam("pageNo") int pageNo) throws ParseException{
//        
//        Response customerQueryResult = orderService.queryOrderHeadListService(queryCondition.get("orderHeadIdMin"), 
//                queryCondition.get("orderHeadIdMax"),queryCondition.get("customerName"),
//                queryCondition.get("orderDateMin"),queryCondition.get("orderDateMax"),pageNo);
//        
//        List<OrderHead> orderHeadList = (List<OrderHead>)customerQueryResult.getData();
//
//        List list_table = new ArrayList();
//        //将查询结果保存在LIST中
//        for (int i =0;i<orderHeadList.size();i++) {
//            List list_row = new ArrayList();
//            list_row.add(orderHeadList.get(i).getOrderHeadId());
//            list_row.add(orderHeadList.get(i).getOrderDate());
//            list_row.add(orderHeadList.get(i).getCustomerMasterId().getCustomerName());
//            //list_row.add(orderHeadList.get(i).getOrdheadMasterId());
//            list_table.add(list_row); 
//        }
//        return list_table;
//    }
    /**
     * 點擊訂單頭檔的詳細信息，通過訂單頭檔編號，查詢到訂單身檔信息
     *
     * @author David
     * @param orderHeadId
     * @param pageNo
     * @param model
     * @return
     */
    /*
    @RequestMapping(value = "queryOrderDetailList")
    @ResponseBody
    public List queryOrderDetaillList(@RequestParam("orderHeadId") String orderHeadId) {
        List<OrderDetail> orderDetails = orderService.queryOrderDetailListService(orderHeadId);
        return orderDetails;
    }
     */
    //查詢訂單身檔++++
    @RequestMapping(value = "queryOrderDetailList")
    @ResponseBody
    public Response queryOrderDetaillList(@RequestParam("orderHeadId") String orderHeadId,@RequestParam(value = "pageNo",defaultValue = "0") int pageNo) {
        Response orderDetails = orderService.queryOrderDetailListService(orderHeadId,pageNo);
        return orderDetails;
    }

    /**
     * 獲取客戶信息列表，用於新增訂單頭檔時，選擇客戶
     *
     * @author David
     * @param customerId
     * @return
     */
    @RequestMapping(value = "getCustomerList", method = RequestMethod.POST)
    @ResponseBody
    public Response getCustomerList(@RequestParam("customerId") String customerId) {

        return orderService.getCustomerListService(customerId);

    }

    /**
     * 新增訂單頭檔
     *
     * @author David
     * @param customerId
     * @return
     */
    @RequestMapping(value = "addOrderHead", method = RequestMethod.POST)
    @ResponseBody
    public Response addOrderHead(@RequestParam("customerId") String customerId) throws Exception {

        Response addOrderHeadResult = orderService.addOrderHeadService(customerId);
        return addOrderHeadResult;
    }

    /**
     * 獲取到產品信息列表（此處用於新增時查看選擇）
     *
     * @author David
     * @param productId
     * @return
     */
    @RequestMapping(value = "getProductList", method = RequestMethod.POST)
    @ResponseBody
    public Response getProductList(@RequestParam("productId") String productId) {

        return orderService.getProductListService(productId);

    }

    //新增訂單身檔
    @RequestMapping(value = "addOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public Response addOrderDetail(@RequestParam("productId") String productId, @RequestParam("orderQty") int orderQty, @RequestParam("orderHeadId") String orderHeadId) throws Exception {

        Response addOrderDetailResult = orderService.addOrderDetailService(productId, orderQty, orderHeadId);
        return addOrderDetailResult;
    }

    //刪除訂單頭檔
    @RequestMapping(value = "deleteOrderHead", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteOrderHead(@RequestParam("orderHeadId") String orderHeadId) throws PreexistingEntityException {
        return orderService.deleteOrderHeadService(orderHeadId);
    }

    //刪除訂單身檔
    @RequestMapping(value = "deleteOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteOrderDetail(@RequestParam("orderDetailId") String orderDetailId) throws PreexistingEntityException {
        return orderService.deleteOrderDetailService(orderDetailId);
    }

    //獲取訂單身檔，爲修改時展示修改的該條數據
    @RequestMapping(value = "getOrderDetailForUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Response getOrderDetailForUpdate(@RequestParam("orderDetailId") String orderDetailId) throws Exception {

        return orderService.getOrderDetailForUpdateService(orderDetailId);

    }

    //獲取產品的價格，在修改訂單身檔的時候
    @RequestMapping(value = "getPriceByQtyForUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Product getPriceByQtyForUpdate(@RequestParam("orderDetailId") String orderDetailId,
            @RequestParam("productId") String productId,
            @RequestParam("orderQty") int orderQty) throws Exception {

        return orderService.getPriceByQtyForUpdateService(orderDetailId,productId, orderQty);
    }

    //修改訂單身檔
    @RequestMapping(value = "updateOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public Response updateOrderDetail(@RequestParam("orderDetailId") String orderDetailId,
            @RequestParam("productId") String productId,
            @RequestParam("orderQty") int orderQty,
            @RequestParam("userDefinedPrice") String userDefinedPrice,
            @RequestParam("versionNumber") int versionNumber) throws Exception {

        //如果用戶自定義下單單價輸入值不爲空，就先將輸入值轉化爲float型，再調用修改身檔方法
        if (!"".equals(userDefinedPrice)) {
            float userDefinedPrice_f = Float.parseFloat(userDefinedPrice);
            return orderService.updateOrderDetailService(orderDetailId, orderQty, userDefinedPrice_f,versionNumber);
        }
        //否則就直接調用同樣的方法，但參數不同
        return orderService.updateOrderDetailService(orderDetailId, productId, orderQty,versionNumber);
    }

}
