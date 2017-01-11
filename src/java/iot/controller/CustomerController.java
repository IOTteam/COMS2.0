/*******************************************************************************
* 建立者：Saulden  建立日期：2016/12/13  最後修訂日期：2017/01/09
* 功能簡述：客戶管理Controller
* 
********************************************************************************/

package iot.controller;

import iot.dao.entity.Customer;
import iot.dao.repository.exceptions.JPAQueryException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.service.CustomerService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import iot.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/CustomerManage")
@SessionAttributes({"queryCondition"})
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：跳轉到客戶頁面，查詢第一頁的資訊
     * 
     * @param customerIdMin  客戶編號起始
     * @param customerIdMax  客戶編號終值
     * @param customerName   客戶姓名
     * @param model  
     * @return 
     ********************************************************************************/
    @RequestMapping(value = "CustomerQuery")
    public String customerQuery(@RequestParam(value = "customerIdMin",defaultValue = "") String customerIdMin, 
                                 @RequestParam(value = "customerIdMax",defaultValue = "") String customerIdMax ,
                                 @RequestParam(value = "customerName",defaultValue = "") String customerName,ModelMap model) throws JPAQueryException{
        //將查詢條件保存到Map中
        HashMap<String,String> queryCondition = new HashMap<>();
        queryCondition.put("customerIdMin", customerIdMin);
        queryCondition.put("customerIdMax", customerIdMax);
        queryCondition.put("customerName", customerName);
        model.addAttribute("queryCondition",queryCondition); 
        
        //查詢客戶資訊
        Response<List<Customer>> customerQueryResult = customerService.customerQueryService(customerIdMin, customerIdMax, customerName,0);
        
        if(customerQueryResult.isEmpty()){//查詢結果為空，返回頁面
            model.addAttribute("message", customerQueryResult.getMessage());
            return "CustomerManage";
        }
        
        //取得客戶資訊列表和數據總量，將其傳遞到前台
        List<Customer> customerList = customerQueryResult.getData();
        int totalPage = (customerQueryResult.getCount() - 1)/10 + 1;
        model.addAttribute("customerList", customerList);
        model.addAttribute("totalPage", totalPage);
        
        //定向到客戶管理頁面
        return "CustomerManage";
    } 
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2016/01/09
     * 功能簡述：查詢客戶姓名、產品名稱輸入框下拉列表數據
     * 
     * @param inputId       前台聚焦的輸入框id
     * @param customerName  客戶姓名
     * @param customerIdMin 客戶編號起始
     * @param customerIdMax 客戶編號終值
     * @return 
     * @throws java.lang.NoSuchFieldException 
     ********************************************************************************/
    @RequestMapping(value = "getCustomerIdAndCustomerNameList")
    @ResponseBody
    public Response getCustomerIdAndCustomerNameList(@RequestParam(value = "inputId",defaultValue = "") String inputId,
                                                        @RequestParam(value = "customerName",defaultValue = "") String customerName, 
                                                        @RequestParam(value = "customerIdMin",defaultValue = "") String customerIdMin,
                                                        @RequestParam(value = "customerIdMax",defaultValue = "") String customerIdMax) throws NoSuchFieldException{
        
        return customerService.getCustomerIdAndCustomerNameListService(inputId,customerName, customerIdMin, customerIdMax);
    } 
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢某一頁的的資訊，返回JSON數據
     * 
     * @param queryCondition  查詢條件
     * @param pageNo          要查詢的頁數
     * @return 
     * @throws iot.dao.repository.exceptions.JPAQueryException 
     ********************************************************************************/
    @RequestMapping(value = "getPageData",method = RequestMethod.GET)
    @ResponseBody
    public List getPageData(@ModelAttribute("queryCondition") HashMap<String,String> queryCondition,@RequestParam(value = "pageNo",defaultValue = "0") int pageNo) throws JPAQueryException{
        
        Response customerQueryResult = customerService.customerQueryService(queryCondition.get("customerIdMin"), 
                queryCondition.get("customerIdMax"),queryCondition.get("customerName"),pageNo);
        
        List<Customer> customerList = (List<Customer>)customerQueryResult.getData();

        List list_table = new ArrayList();
        //将查询结果保存在LIST中
        for (Customer customer:customerList) {
            List list_row = new ArrayList();
            list_row.add(customer.getCustomerId());
            list_row.add(customer.getVersionNumber());
            list_row.add(customer.getCustomerName());
            list_row.add(customer.getCustomerMail());
            list_row.add(customer.getCustomerPhone());
            list_row.add(customer.getCustomerMasterId());
            list_table.add(list_row); 
        }
        return list_table;
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：新增客戶
     * 
     * @param customer  客戶實體
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    @RequestMapping(value = "addCustomer",method = RequestMethod.POST)
    @ResponseBody
    public Response addCustomer(@Valid Customer customer) throws Exception{
        
       return customerService.addCustomerService(customer);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取產品列表信息，用於新增客戶產品單價
     * 
     * @param productId  產品編號
     * @return 
     ********************************************************************************/
    @RequestMapping(value = "getProductList",method = RequestMethod.POST)
    @ResponseBody
    public Response getProductList(@RequestParam("productId") String productId){
        
       return customerService.getProductListService(productId);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取要修改的客戶資訊
     * 
     * @param customerId  客戶編號
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    @RequestMapping(value = "getCustomerForUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Response getCustomerForUpdate(@RequestParam("customerId") String customerId) throws NonexistentEntityException{
        
       return customerService.getCustomerForUpdateService(customerId);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：修改客戶資訊
     * 
     * @param customer  客戶實體
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    @RequestMapping(value = "updateCustomer",method = RequestMethod.POST)
    @ResponseBody
    public Response updateCustomer(@Valid Customer customer) throws Exception{
        
       return customerService.updateCustomerService(customer);
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：刪除客戶資訊
     * 
     * @param customerId    客戶編號
     * @param versionNumber 客戶點擊刪除前資料的版本號
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    @RequestMapping(value = "deleteCustomer",method = RequestMethod.POST)
    @ResponseBody
    public Response deleteCustomer(@RequestParam("customerId") String customerId,@RequestParam(value = "versionNumber",defaultValue = "0") int versionNumber) throws Exception{
        
       return customerService.deleteCustomer(customerId,versionNumber);
        
    }
    
}