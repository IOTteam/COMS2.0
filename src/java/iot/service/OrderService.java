/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.service;

import iot.dao.entity.Customer;
import iot.dao.entity.CustomerPrice;
import iot.dao.entity.OrderDetail;
import iot.dao.entity.OrderHead;
import iot.dao.entity.Product;
import iot.dao.repository.CustomerDAO;
import iot.dao.repository.CustomerPriceDAO;
import iot.dao.repository.OrderDetailDAO;
import iot.dao.repository.OrderHeadDAO;
import iot.dao.repository.ProductDAO;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import javax.persistence.EntityManagerFactory;
import iot.response.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Su
 */
@Service
public class OrderService {

    @Autowired
    private OrderHeadDAO ohdao;
    
    @Autowired
    private OrderDetailDAO oddao;
    
    @Autowired
    private CustomerDAO cdao;
    
    @Autowired
    private ProductDAO pdao;
    
    @Autowired
    private CustomerPriceDAO cpdao;
    
    
    /**
     * 查詢訂單頭檔列表
     *
     * @author David
     * @param orderHeadIdMin
     * @param orderHeadIdMax
     * @param customerName
     * @param orderDateMin
     * @param orderDateMax
     * @param pageNo
     * @return
     * @throws ParseException
     */
    //關於訂單頭檔條件查詢的數據結果
    public Response queryOrderHeadListService(String orderHeadIdMin, String orderHeadIdMax, String customerName, String orderDateMin, String orderDateMax,String pageNo) throws ParseException {
        //OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        //CustomerDAO cdao = new CustomerDAO(emf);
        List<Customer> cbCustomer = cdao.getCustomerByCustomerName(customerName);

        Response queryOrderHeadListResult = ohdao.queryOrderHeadByCondition(orderHeadIdMin, orderHeadIdMax, cbCustomer, orderDateMin, orderDateMax,pageNo);      
        return queryOrderHeadListResult;

    }
    //關於訂單頭檔條件查詢數據數量的總計
    public Response queryCountOrderHeadListService(String orderHeadIdMin, String orderHeadIdMax, String customerName, String orderDateMin, String orderDateMax) throws ParseException {
        //OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        //CustomerDAO cdao = new CustomerDAO(emf);
        List<Customer> cbCustomer = cdao.getCustomerByCustomerName(customerName);

        Response queryOrderHeadListResult = ohdao.queryCountOrderHeadByCondition(orderHeadIdMin, orderHeadIdMax, cbCustomer, orderDateMin, orderDateMax);      
        return queryOrderHeadListResult;

    }

    /**
     * 通過傳入的orderHeadId查找訂單身檔實體
     *
     * @author David
     * @param orderHeadId
     * @return
     */
    /*
    public List queryOrderDetailListService(String orderHeadId) {
        OrderDetailDAO oddao = new OrderDetailDAO(emf);
        OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        //調用OrderHeadDAO中通過orderHeadId查詢orderHead實體的方法查詢到OrderHead實體
        OrderHead oh = ohdao.queryOrderHeadByOrderHeadId(orderHeadId);
//        //從查詢到的orderHead實體中取到符合條件的orderHeadMasterId
//        String ohmId=oh.getOrdheadMasterId();
        //通過orderHeadMasterId去OrderDetailDAO調用中查找相應的OrderDetail實體，存放到list中
        List<OrderDetail> odlList = oddao.queryOrderDetailByOrderHeadMasterId(oh);
        //將存放有orderDetail信息的list返回給controller
        return odlList;
    }
    */
    //查詢訂單身檔列表++++
     public Response queryOrderDetailListService(String orderHeadId,int pageNo) {
        //OrderDetailDAO oddao = new OrderDetailDAO(emf);
        //OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        //調用OrderHeadDAO中通過orderHeadId查詢orderHead實體的方法查詢到OrderHead實體
        OrderHead oh = ohdao.queryOrderHeadByOrderHeadId(orderHeadId);

        List<OrderDetail> odList = (List<OrderDetail>)oddao.queryOrderDetailByOrderHeadMasterId(oh).getData();
        
        int count = 0;
        List responseList = new ArrayList<>();
        int size=odList.size();
        
        for(int j = 1; j <= size; j++){
            OrderDetail od = odList.get(size - j);
            if(od.getDeleteStatus() == false){
                ++count;
                if(count > pageNo*5){
                    List list_row = new ArrayList();
                    list_row.add(od.getOrderDetailId());
                    list_row.add(od.getOrdheadMasterId().getOrderHeadId());
                    list_row.add(od.getProductMasterId().getProductId());
                    list_row.add(od.getProductMasterId().getProductName());
                    list_row.add(od.getOrderQty());
                    list_row.add(od.getOrderPrice());
                    list_row.add(od.getVersionNumber());
                    responseList.add(list_row);
                }
            }
            if(responseList.size() == 5){
                break;
            }
        }
        
        return new Response().success("通過外鍵關聯獲取客戶產品單價",responseList,((size-1)/5) + 1);
    }

   
    /**
     * 通過客戶編號查詢客戶信息列表（新增訂單頭檔時的客戶選擇的顯示）
     *
     * @author David
     * @param customerId
     * @return
     */
    public Response getCustomerListService(String customerId) {

        return cdao.queryCustomer(customerId);
    }

    /**
     * 新增訂單頭檔
     *
     * @author David
     * @param customerId
     * @return
     * @throws Exception
     */
    public Response addOrderHeadService(String customerId) throws Exception {
        // CustomerDAO cdao=new CustomerDAO(emf);
        // Customer customer=cdao.findCustomerByCustomerId(customerId).getData();
        Customer customer = (Customer)cdao.findCustomerByCustomerId(customerId).getData();
        OrderHead oh = new OrderHead();
        oh.setCustomerMasterId(customer);
        oh.setOrderDate(new Date());
        oh.setOrdheadMasterId(UUID.randomUUID().toString().toUpperCase());

        //OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        oh.setOrderHeadId(ohdao.generateOrderHeadId());
        oh.setDeleteStatus(false);

        Response response = ohdao.create(oh);
        return response;
    }

    /**
     * 新增訂單身檔
     * 
     * 2017/01/07 修訂新增訂單時未在客戶產品單價表中查詢到資料的處理
     *
     * @param productId
     * @param orderQty
     * @param orderHeadId
     * @return
     */
    public Response addOrderDetailService(String productId, int orderQty, String orderHeadId) throws Exception {
        //ProductDAO pdao = new ProductDAO(emf);
        //通過產品id查找到product實體，此處的調用方法是Saulden寫的，因爲讓我寫也是一樣的函數
        Product product = (Product) pdao.findProductByProductId(productId).getData();
        //通過訂單頭檔，查詢到OrderHead實體
        //OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        OrderHead oh = ohdao.queryOrderHeadByOrderHeadId(orderHeadId);
        //實例化一個訂單身檔實體OrderDetail，用於存放新增的數據
        OrderDetail od = new OrderDetail();
        od.setOrderQty(orderQty);
        od.setOrdheadMasterId(oh);
        od.setProductMasterId(product);
        od.setOrddetailMasterId(UUID.randomUUID().toString().toUpperCase());
        //如果通過傳入的產品ID查到的產品的優惠狀態是true，即此產品有優惠，獲取的通過傳入的產品ID和數量，查詢到的在客戶產品單價表中的價格
        if (product.getDiscountStatus() == true) {
            //CustomerPriceDAO cpdao = new CustomerPriceDAO(emf);
            CustomerPrice customerPrice = (CustomerPrice) cpdao.queryCustomerPriceByOrderQty(orderQty, product,oh.getCustomerMasterId()).getData();
            if(customerPrice != null){
                od.setOrderPrice(customerPrice.getRangePrice());
            }
            else{
                od.setOrderPrice(product.getProductStandardPrice());
            }
        } else {//否則，就是按標準售價做下單單價
            od.setOrderPrice(product.getProductStandardPrice());
        }
        //OrderDetailDAO oddao = new OrderDetailDAO(emf);
        od.setOrderDetailId(oddao.generateOrderDetailId());
        od.setDeleteStatus(false);

        Response response = oddao.create(od);
        return response;
    }

    /**
     * 通過產品編號獲取產品信息
     *
     * @param productId
     * @return
     */
    public Response getProductListService(String productId) {
        return pdao.findProducts(productId);
    }

    /**
     * 刪除訂單頭檔
     *
     * @param orderHeadId
     * @param versionNumber
     * @return
     * @throws PreexistingEntityException
     */
    public Response deleteOrderHeadService(String orderHeadId,String versionNumber) throws PreexistingEntityException, NonexistentEntityException {
        //OrderHeadDAO ohdao = new OrderHeadDAO(emf);
        OrderHead oh = ohdao.queryOrderHeadByOrderHeadId(orderHeadId);
        if (oh==null) {
            throw new NonexistentEntityException("編號爲:"+oh.getOrderHeadId()+" 的訂單頭檔已不存在！");
        }
        oh.setVersionNumber(Integer.parseInt(versionNumber));
        return ohdao.deleteOrderHead(oh);
    }

    /**
     * 刪除訂單身檔
     *
     * @param orderDetailId
     * @param versionNumber
     * @return
     * @throws PreexistingEntityException
     * @throws iot.dao.repository.exceptions.NonexistentEntityException
     */
    public Response deleteOrderDetailService(String orderDetailId,String versionNumber) throws PreexistingEntityException, NonexistentEntityException {
        //OrderDetailDAO oddao = new OrderDetailDAO(emf);
        OrderDetail od = (OrderDetail) oddao.queryOrderDetailByOrderDetailId(orderDetailId).getData();
         if (od==null) {
            throw new NonexistentEntityException("編號爲:"+od.getOrderDetailId()+" 的訂單身檔已不存在！");
        }
        od.setVersionNumber(Integer.parseInt(versionNumber));
        
        return oddao.deleteOrderDetail(od);
    }

    //獲取訂單身檔信息爲修改做準備
    public Response getOrderDetailForUpdateService(String orderDetailId) {

        return oddao.queryOrderDetailByOrderDetailId(orderDetailId);
    }

    //通過前臺傳入的產品ID和產品數量，查詢出產品價格
    //先通過產品ID查找到產品，如果產品有優惠，再加上數量去查詢到客戶產品單價表信息，獲取優惠價格
    //如果沒有優惠，直接獲取到標準價格
    //把兩個價格傳給產品實體的價格中，把產品實體傳出去
    public Product getPriceByQtyForUpdateService(String orderDetailId,String productId, int orderQty) throws Exception {

        OrderDetail od = (OrderDetail)oddao.queryOrderDetailByOrderDetailId(orderDetailId).getData();
        //ProductDAO pdao = new ProductDAO(emf);
        Product product = (Product) pdao.findProductByProductId(productId).getData();
        if (product.getDiscountStatus() == true) {
            //CustomerPriceDAO cpdao = new CustomerPriceDAO(emf);
            /************ 沒有客戶資訊***************/
            Response cprResponse = cpdao.queryCustomerPriceByOrderQty(orderQty, product, od.getOrdheadMasterId().getCustomerMasterId());
            CustomerPrice cp = (CustomerPrice) cprResponse.getData();
            //如果客戶產品單價表查詢結果爲空，則保留標準售價
            if (cprResponse.isEmpty()) {
                product.setProductStandardPrice(product.getProductStandardPrice());
            } //如果產品是有優惠的，但是輸入的數量級查不到優惠價格，則保留標準售價
            else {
                product.setProductStandardPrice(cp.getRangePrice());
            }
        }
        return product;
    }

    //如果輸入的用戶自定義下單單價  不爲空  時調用
    public Response updateOrderDetailService(String orderDetailId, int orderQty, float userDefinedPrice,int versionNumber) throws Exception {

        //OrderDetailDAO oddao = new OrderDetailDAO(emf);
        //通過訂單身檔編號，插到訂單身檔實體
        OrderDetail od = (OrderDetail) oddao.queryOrderDetailByOrderDetailId(orderDetailId).getData();
        //將前臺啊傳入的下單數量和單價填入該訂單身檔
        od.setOrderQty(orderQty);
        od.setOrderPrice(userDefinedPrice);
        od.setVersionNumber(versionNumber);
        Response response = oddao.edit(od);
        return response;
    }

    //如果輸入的用戶自定義下單單價  爲空  時調用
    public Response updateOrderDetailService(String orderDetailId, String productId, int orderQty,int versionNumber) throws Exception {

        //OrderDetailDAO oddao = new OrderDetailDAO(emf);
        //通過訂單身檔編號，插到訂單身檔實體
        OrderDetail od = (OrderDetail) oddao.queryOrderDetailByOrderDetailId(orderDetailId).getData();

        //ProductDAO pdao = new ProductDAO(emf);
        //通過產品編號查找到產品實體
        Product product = (Product) pdao.findProductByProductId(productId).getData();

        od.setOrderQty(orderQty);

        //CustomerPriceDAO cpdao = new CustomerPriceDAO(emf);

        //如果輸入的產品是有優惠的產品
        if (product.getDiscountStatus() == true) {
            //通過產品實體和下單數量，查找到輸入的下單數量對應的產品價格
            CustomerPrice customerPrice = (CustomerPrice) cpdao.queryCustomerPriceByOrderQty(orderQty, product,od.getOrdheadMasterId().getCustomerMasterId()).getData();
            if(customerPrice != null){
                od.setOrderPrice(customerPrice.getRangePrice());
            }
            else{
                 od.setOrderPrice(product.getProductStandardPrice());
            }
            
        } //否則就是沒有優惠的產品，就取產品實體中的標準售價
        else {
            od.setOrderPrice(product.getProductStandardPrice());
        }
        od.setVersionNumber(versionNumber);
        Response response = oddao.edit(od);
        return response;
    }

}
