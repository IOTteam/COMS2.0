/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.controller;

import iot.dao.entity.Customer;
import iot.dao.entity.CustomerPrice;
import iot.dao.entity.Product;
import iot.dao.repository.CustomerDAO;
import iot.response.Response;
import iot.service.CustomerPriceService;
import iot.service.ProductService;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author dell
 */
@Controller
@RequestMapping("/Product")
public class ProductController {
    
    @Autowired 
    private ProductService productService;
    
    //創建實體管理工廠的對象
    @Autowired
    private EntityManagerFactory emf;
    
    @Autowired
    private CustomerPriceService customerPriceService;
    
    /*******************************************************************************
     * 建立者：Guardp  建立日期：-  最後修訂日期：-
     * 功能簡述：跳轉到產品頁面，查詢第一頁的資訊
     * 
     * @param productIdMin
     * @param productIdMax
     * @param productName
     * @param productPriceMin
     * @param productPriceMax
     * @param productSpec
     * @param pageNo
     * @param modelMap
     * @return 
     ********************************************************************************/
    @RequestMapping("ProductQuery")
    public String productMasterQuery(@RequestParam(value = "productIdMin",defaultValue = "") String productIdMin,
        @RequestParam(value = "productIdMax",defaultValue = "") String productIdMax ,
        @RequestParam(value = "productName",defaultValue = "")String productName, 
        @RequestParam(value = "productPriceMin",defaultValue = "")String productPriceMin,
        @RequestParam(value = "productPriceMax",defaultValue = "")String productPriceMax,
        @RequestParam(value = "productSpec",defaultValue = "")String productSpec,
        @RequestParam(value = "pageNo",defaultValue = "1")String pageNo,ModelMap modelMap){
        //將查詢條件保存到map中
        HashMap<String,String> queryCondition = new HashMap<>();
        queryCondition.put("productIdMin", productIdMin);
        queryCondition.put("productIdMax", productIdMax);
        queryCondition.put("productName", productName);
        queryCondition.put("productPriceMin", productPriceMin);
        queryCondition.put("productPriceMax", productPriceMax);
        queryCondition.put("productSpec", productSpec);
        queryCondition.put("pageNo", pageNo);
        
        //調用Service中的ProductQuery查詢方法，並將結果返回到Response的對象中
        Response productQueryResponseList = productService.ProductQuery(productIdMin,productIdMax,productName,
                productPriceMin,productPriceMax,productSpec,pageNo);
        Response productCountResponseList = productService.ProductCountQuery(productIdMin,productIdMax,productName,
                productPriceMin,productPriceMax,productSpec);
        
        List<Product> productList = (List<Product>) productQueryResponseList.getData();
        List<Product> pList = (List<Product>) productCountResponseList.getData();
        int totalPages = (pList.size()-1)/10 + 1;
        modelMap.addAttribute("productList", productList);
        modelMap.addAttribute("queryCondition", queryCondition);
        modelMap.addAttribute("totalPages", totalPages);
        
        return "ProductManage";        
    }
    
    @RequestMapping("addProduct")
    @ResponseBody
    public HashMap<String, String> addProduct(@RequestParam("productStandardPrice") float productStandardPrice ,
        @RequestParam("productName")String productName,@RequestParam("discountStatus")boolean discountStatus,
        @RequestParam("productSpec")String productSpec,ModelMap modelMap){
        HashMap<String,String> customerPriceMap = productService.addProductMasterService(productStandardPrice, productName, discountStatus, productSpec);
        return customerPriceMap;     
    }
    
    @RequestMapping("queryCustomer")
    @ResponseBody
    public List<Customer> queryCustomer(){
        CustomerDAO customerDAO = new CustomerDAO(emf);
        List<Customer> customerList = customerDAO.findCustomerEntities();
        return customerList;
    }
    
    @RequestMapping("setCustomerPrice")
    @ResponseBody
    public CustomerPrice setCustomerPrice(@RequestParam("productMasterId") Product productMasterId,@RequestParam("customerMasterId") Customer customerMasterId,
            @RequestParam("rangeMin") Integer rangeMin,@RequestParam("rangeMax") Integer rangeMax,@RequestParam("rangePrice") float rangePrice) throws Exception{
        return customerPriceService.setCustomerPrice(productMasterId,customerMasterId,rangeMin,rangeMax,rangePrice);
    }
    
    @RequestMapping("modifyProduct")
    @ResponseBody
    public HashMap<String, String> modifyProduct(@RequestParam("productId") String productId,@RequestParam("productStandardPrice") float productStandardPrice,
    @RequestParam("discountStatus") boolean discountStatus,ModelMap modelMap) throws Exception{
        HashMap<String,String> customerPriceMap = productService.modifyProduct(productId,productStandardPrice,discountStatus);
        return customerPriceMap; 
    }
    
    @RequestMapping("modifyCustomerPrice")
    @ResponseBody
    public List<CustomerPrice> modifyCustomerPrice(@RequestParam("productMasterId") Product productMasterId,@RequestParam("customerMasterId") Customer customerMasterId,
            @RequestParam("rangeMin") Integer rangeMin,@RequestParam("rangeMax") Integer rangeMax,@RequestParam("rangePrice") float rangePrice) throws Exception{
        
        return customerPriceService.modifyCustomerPrice(productMasterId, customerMasterId, rangeMin, rangeMax, rangePrice);
    }
    
    @RequestMapping("deleteProduct")
    public String deleteProduct(@RequestParam String productId,ModelMap modelMap) throws Exception{
        String message = productService.deleteProductByProductId(productId);
        modelMap.addAttribute("message", message);
        return "redirect:ProductQuery";
    }
}