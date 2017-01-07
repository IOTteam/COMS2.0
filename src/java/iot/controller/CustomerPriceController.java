/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.controller;

import iot.dao.entity.CustomerPrice;
import iot.dao.repository.exceptions.JPAQueryException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.response.Response;
import iot.service.CustomerPriceService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
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
 * @author hatanococoro
 */
@Controller
@RequestMapping("/CustomerPriceManage")
@SessionAttributes({"queryCondition"})
public class CustomerPriceController {

    @Autowired
    private CustomerPriceService customerPriceService;
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢客戶產品單價第一頁資訊
     * 
     * @param customerName
     * @param productName
     * @param priceMin
     * @param rangeMin
     * @param model
     * @param priceMax
     * @param rangeMax
     * @return 
     ********************************************************************************/
    @RequestMapping(value = "queryCustomerPrice")
    public String queryCustomerPrice(@RequestParam(value = "customerName",defaultValue = "") String customerName, 
                                 @RequestParam(value = "productName",defaultValue = "") String productName ,
                                 @RequestParam(value = "priceMin",defaultValue = "") String priceMin ,
                                 @RequestParam(value = "priceMax",defaultValue = "") String priceMax ,
                                 @RequestParam(value = "rangeMin",defaultValue = "") String rangeMin ,
                                 @RequestParam(value = "rangeMax",defaultValue = "") String rangeMax,ModelMap model) throws JPAQueryException{
        //将查询条件保存到Map中 
        HashMap<String,String> queryCondition = new HashMap<>();
        queryCondition.put("customerName", customerName);
        queryCondition.put("productName", productName);
        queryCondition.put("priceMin", priceMin);
        queryCondition.put("priceMax", priceMax);
        queryCondition.put("rangeMin", rangeMin);
        queryCondition.put("rangeMax", rangeMax);
        model.addAttribute("queryCondition",queryCondition); 
        
        Response customerPriceQueryResult = customerPriceService.queryCustomerPriceService(customerName, productName, priceMin,priceMax,rangeMin,rangeMax,0);
        
        if(customerPriceQueryResult.isEmpty()){//查詢結果為空，返回頁面
            model.addAttribute("message", customerPriceQueryResult.getMessage());
        }else{
            List<CustomerPrice> customerPriceList = (List<CustomerPrice>)customerPriceQueryResult.getData();
            int totalPage = (customerPriceQueryResult.getCount() - 1)/10 + 1;
        
            model.addAttribute("customerPriceList", customerPriceList);
            model.addAttribute("totalPage", totalPage);
        }

        return "CustomerPriceManage";
    } 
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢某一頁的的資訊，返回JSON數據
     * 
     * @param queryCondition
     * @param pageNo
     * @return 
     ********************************************************************************/
    @RequestMapping(value = "getPageData",method = RequestMethod.GET)
    @ResponseBody
    public List getPageData(@ModelAttribute("queryCondition") HashMap<String,String> queryCondition,@RequestParam("pageNo") int pageNo) throws JPAQueryException{
        
        Response customerPriceQueryResult = customerPriceService.queryCustomerPriceService(queryCondition.get("customerName"), queryCondition.get("productName"),queryCondition.get("priceMin") , 
                                      queryCondition.get("priceMax"), queryCondition.get("rangeMin"), queryCondition.get("rangeMax"), pageNo);
        
        List<CustomerPrice> customerPriceList = (List<CustomerPrice>)customerPriceQueryResult.getData();

        List list_table = new ArrayList();
        //将查询结果保存在LIST中
        for (CustomerPrice customerPrice:customerPriceList) {
            List list_row = new ArrayList();
            list_row.add(customerPrice.getCustomerPriceId());
             list_row.add(customerPrice.getCustomerMasterId().getCustomerId());
             list_row.add(customerPrice.getCustomerMasterId().getCustomerName());
             list_row.add(customerPrice.getProductMasterId().getProductId());
             list_row.add(customerPrice.getProductMasterId().getProductName());
             list_row.add(customerPrice.getRangeMin() + "~" + customerPrice.getRangeMax());
             list_row.add(customerPrice.getRangePrice());
            list_table.add(list_row); 
        }
        return list_table;
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過客戶編號查詢客戶產品單價資訊
     * 
     * @param customerId
     * @param pageNo
     * @return 
     ********************************************************************************/
    @RequestMapping(value = "queryCustomerPriceByCustomerId")
    @ResponseBody
    public Response queryCustomerPrice( @RequestParam("customerId") String customerId, @RequestParam(value = "pageNo",defaultValue = "0") int pageNo) throws NonexistentEntityException{
        
        return customerPriceService.queryCustomerPriceService(customerId, pageNo);
    } 
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢客戶姓名、產品名稱輸入框下拉列表數據
     * 
     * @param inputId
     * @param customerName
     * @param productName
     * @return 
     * @throws java.lang.NoSuchFieldException 
     ********************************************************************************/
    @RequestMapping(value = "getCustomerAndProductList")
    @ResponseBody
    public Response getCustomerAndProductList(@RequestParam(value = "inputId",defaultValue = "") String inputId,
                                                 @RequestParam(value = "customerName",defaultValue = "") String customerName, 
                                                 @RequestParam(value = "productName",defaultValue = "") String productName) throws NoSuchFieldException{
        
        return customerPriceService.getCustomerAndProductListService(inputId,customerName,productName);
    } 
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢數量級、價格輸入框下拉列表數據
     * 
     * @param inputId
     * @param customerName
     * @param productName
     * @param priceMin
     * @param rangeMin
     * @param rangeMax
     * @return 
     * @throws java.lang.NoSuchFieldException 
     ********************************************************************************/
    @RequestMapping(value = "getRangesAndPriceList")
    @ResponseBody
    public Response getRangesAndPriceList(@RequestParam(value = "inputId",defaultValue = "") String inputId,
                                            @RequestParam(value = "customerName",defaultValue = "") String customerName, 
                                            @RequestParam(value = "productName",defaultValue = "") String productName ,
                                            @RequestParam(value = "priceMin",defaultValue = "0") String priceMin ,
                                            @RequestParam(value = "rangeMin",defaultValue = "0") String rangeMin ,
                                            @RequestParam(value = "rangeMax",defaultValue = "429496729") String rangeMax) throws NoSuchFieldException{
        
        return customerPriceService.getRangesAndPriceListService(inputId, customerName, productName, priceMin, rangeMin, rangeMax);
    }
    
     /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：新增客戶產品單價
     * 
     * @param customerId
     * @param productId
     * @param customerPrice
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    @RequestMapping(value = "addCustomerPrice",method = RequestMethod.POST)
    @ResponseBody
    public Response addCustomerPrice(@RequestParam("customerId") String customerId, @RequestParam("productId") String productId, @Valid CustomerPrice customerPrice) throws Exception{
        
        return customerPriceService.addCustomerPriceService(customerId, productId, customerPrice);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取要修改的客戶產品單價資訊
     * 
     * @param customerPriceId
     * @return 
     ********************************************************************************/
    @RequestMapping(value = "getCustomerPriceForUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Response getCustomerPriceForUpdate(@RequestParam("customerPriceId") String customerPriceId) throws NonexistentEntityException{
        
       return customerPriceService.getCustomerPriceForUpdateService(customerPriceId);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：修改客戶產品單價資訊
     * 
     * @param customerPrice
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    @RequestMapping(value = "updateCustomerPrice",method = RequestMethod.POST)
    @ResponseBody
    public Response updateCustomerPrice(@Valid CustomerPrice customerPrice) throws Exception{
        
       return customerPriceService.updateCustomerPriceService(customerPrice);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：邏輯刪除客戶產品單價資訊
     * 
     * @param customerPriceId
     * @param versionNumber
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    @RequestMapping(value = "deleteCustomerPrice",method = RequestMethod.POST)
    @ResponseBody
    public Response deleteCustomerPrice(@RequestParam("customerPriceId") String customerPriceId,@RequestParam(value = "versionNumber",defaultValue = "0") int versionNumber) throws Exception{
        
        return customerPriceService.deleteCustomerPriceService(customerPriceId,versionNumber);
        
    }


}
