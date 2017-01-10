/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.service;

import iot.dao.entity.CustomerPrice;
import iot.dao.entity.OrderDetail;
import iot.dao.entity.Product;
import iot.dao.repository.CustomerPriceDAO;
import iot.dao.repository.OrderDetailDAO;
import iot.dao.repository.ProductDAO;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.response.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dell
 */
@Service
public class ProductService {

    //創建實體管理工廠的對象
    @Autowired
    private EntityManagerFactory emf;
    
    /*******************************************************************************
     * 建立者：Guardp  建立日期：-  最後修訂日期：-
     * 功能簡述：調用productDAO對象中的queryProductByCondition方法查詢數據，並存入Response對象
     * productQueryResponseList中，然後返回productQueryResponseList
     * 
     * @param productIdMin
     * @param productIdMax
     * @param productName
     * @param productPriceMin
     * @param productPriceMax
     * @param productSpec
     * @param pageNo
     * @return 
     ********************************************************************************/
    public Response ProductQuery(String productIdMin, String productIdMax, String productName, 
            String productPriceMin, String productPriceMax, String productSpec,String pageNo) {
        //創建productDAO對象
        ProductDAO productDAO = new ProductDAO(emf);
        //調用productDAO對象中的queryProductByCondition方法查詢數據，並存入Response對象productQueryResponseList中
        Response productQueryResponseList = productDAO.queryProductByCondition(productIdMin,productIdMax,productName,
                productPriceMin,productPriceMax,productSpec,pageNo);
        //返回對象productQueryResponseList
        return productQueryResponseList;
    }

    public Response ProductCountQuery(String productIdMin, String productIdMax, String productName, 
            String productPriceMin, String productPriceMax, String productSpec) {
        //創建productDAO對象
        ProductDAO productDAO = new ProductDAO(emf);
        //調用productDAO對象中的queryProductCountByCondition方法查詢數據，並存入Response對象productQueryResponseList中
        Response productCountResponseList = productDAO.queryProductCountByCondition(productIdMin,productIdMax,productName,
                productPriceMin,productPriceMax,productSpec);
        //返回對象productQueryResponseList
        return productCountResponseList;
    }

    public HashMap<String, String> addProductMasterService(float productStandardPrice,String productName,boolean discountStatus,
            String productSpec) {
        ProductDAO productDAO = new ProductDAO(emf);
        Product product = new Product();
        product.setDiscountStatus(discountStatus);
        product.setProductName(productName);
        product.setProductSpec(productSpec);
        product.setProductStandardPrice(productStandardPrice);
        product.setProductMasterId(UUID.randomUUID().toString().toUpperCase());
        product.setVersionNumber(0);
        product.setDeleteStatus(false);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        String productIdHead = "PRO" + dateString;
        Integer count = Integer.parseInt(productDAO.getProductCountByProductIdHead(productIdHead));
        count = count + 1;
        String number = String.format("%03d", count);
        product.setProductId(productIdHead+number);
        try {
            productDAO.create(product);
        } catch (Exception ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Product newProduct = (Product)productDAO.findProductByProductId(product.getProductId()).getData();
        HashMap<String,String> customerPriceMap = new HashMap<>();
        if (newProduct == null) {
            customerPriceMap.put("message", "新增產品失敗!");
        } else {
            customerPriceMap.put("productId", newProduct.getProductId());
            customerPriceMap.put("productName", newProduct.getProductName());
            customerPriceMap.put("productMasterId", newProduct.getProductMasterId());
            customerPriceMap.put("message", "新增產品成功!");
        }
        return customerPriceMap;
    }
    
    //創建HashMap，將需要傳遞的數據存入map中
    public HashMap<String,String> modifyHashMap(String productId){
        ProductDAO productDAO = new ProductDAO(emf);
        Product newProduct = (Product)productDAO.findProductByProductId(productId).getData();
        HashMap<String,String> product = new HashMap<>();
        product.put("productId", newProduct.getProductId());
        product.put("productName", newProduct.getProductName());
        product.put("productSpec", newProduct.getProductSpec());
        product.put("productStandardPrice", String.valueOf(newProduct.getProductStandardPrice()));
        product.put("discountStatus", String.valueOf(newProduct.getDiscountStatus()));
        return product;
    }

    public HashMap<String, String> modifyProduct(String productId,float productStandardPrice,boolean discountStatus) throws NonexistentEntityException, Exception {
        ProductDAO productDAO = new ProductDAO(emf);
        Product oldProduct = (Product)productDAO.findProductByProductId(productId).getData();
        oldProduct.setDiscountStatus(discountStatus);
        oldProduct.setProductStandardPrice(productStandardPrice);
        productDAO.edit(oldProduct);
        
        Product newProduct = (Product)productDAO.findProductByProductId(productId).getData();
        HashMap<String,String> customerPriceMap = new HashMap<>();
            if (newProduct == null) {
            customerPriceMap.put("message", "產品修改失敗!");
        } else {
            customerPriceMap.put("productId", newProduct.getProductId());
            customerPriceMap.put("productName", newProduct.getProductName());
            customerPriceMap.put("productMasterId", newProduct.getProductMasterId());
            customerPriceMap.put("message", "產品修改成功!");
        }
        return customerPriceMap;
    }

    public HashMap<String, String> deleteProductByProductId(String productId) throws Exception {
        ProductDAO productDAO = new ProductDAO(emf);
        Product product = (Product)productDAO.findProductByProductId(productId).getData();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO(emf);
        List<OrderDetail> OrderDetail = orderDetailDAO.findOrderDetailByProductMasterId(product);
        HashMap<String,String> deleteMap = new HashMap<>();
        if (OrderDetail.isEmpty()) {
            product.setDeleteStatus(true);
            productDAO.edit(product);
            Integer count =Integer.parseInt(productDAO.getProductCountByStatus());
            String totalPages = String.valueOf((count -1)/10 + 1);
            CustomerPriceDAO customerPriceDAO = new CustomerPriceDAO(emf);
            List<CustomerPrice> customerPriceList = customerPriceDAO.findCustomerPriceByProductMasterId(product);
            for (int i = 0; i < customerPriceList.size(); i++) {
                CustomerPrice customerPrice = customerPriceDAO.findCustomerPriceByCusPriceMasterId(customerPriceList.get(i).getCusPriceMasterId());
                customerPrice.setDeleteStatus(true);
                customerPriceDAO.edit(customerPrice);
            }
            deleteMap.put("message", "產品已經成功刪除");
            deleteMap.put("totalPages", totalPages);
            return deleteMap;
        } else {
            deleteMap.put("message", "該產品已經擁有訂單，所以此產品不能刪除！！！請確認該產品的訂單是否已消除。");
            return deleteMap;
        }
    }
    
}