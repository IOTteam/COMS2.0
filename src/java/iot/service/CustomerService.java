/*******************************************************************************
* 建立者：Saulden  建立日期：2016/12/13  最後修訂日期：2017/01/09
* 功能簡述：客戶管理Service
* 
********************************************************************************/

package iot.service;

import iot.dao.entity.Customer;
import iot.dao.repository.CustomerDAO;
import iot.dao.repository.ProductDAO;
import iot.dao.repository.exceptions.JPAQueryException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import iot.response.Response;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerDAO customerDAO;
    
    @Autowired
    private ProductDAO productDAO;
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：客戶條件查詢
     * 
     * @param customerIdMin  客戶編號起始
     * @param customerIdMax  客戶編號終值
     * @param customerName   客戶姓名
     * @param pageNo         要查詢的頁數
     * @return 
     * @throws iot.dao.repository.exceptions.JPAQueryException 
     ********************************************************************************/
    public Response customerQueryService(String customerIdMin, String customerIdMax ,String customerName,int pageNo) throws JPAQueryException{
    
        //CustomerDAO customerDAO = new CustomerDAO(emf);
        Response<List<Customer>> customerQueryResult = customerDAO.queryCustomerByCondition(customerIdMin, customerIdMax, customerName, pageNo);
        return customerQueryResult;
    }
   
   /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：查詢客戶姓名、產品名稱輸入框下拉列表數據
     * 
     * @param inputId        前台聚焦的輸入框id
     * @param customerName   客戶姓名
     * @param customerIdMin  客戶編號起始
     * @param customerIdMax  客戶編號終值
     * @return 
     * @throws java.lang.NoSuchFieldException 
     ********************************************************************************/
    public Response getCustomerIdAndCustomerNameListService(String inputId, String customerName,  String customerIdMin, String customerIdMax) throws NoSuchFieldException{
    
        //CustomerDAO customerDAO = new CustomerDAO(emf);
        
        if("customer_name_input".equals(inputId)){//輸入框為客戶姓名輸入框
            List customerNameList = (List) customerDAO.findCustomerNameListByCustomerName(customerName, false, 0, 10).getData();
            return new Response().success("查詢客戶姓名列表成功", customerNameList);
        }
        
        if("customer_idMin".equals(inputId) || "customer_idMax".equals(inputId)){//輸入框為客戶編號起始輸入框
            List customerIdList = (List) customerDAO.findCustomerIdListByCustomerId(inputId, customerIdMin, customerIdMax, false, 0, 10).getData();
            return new Response().success("查詢客戶編號列表成功", customerIdList);
        }

        throw new NoSuchFieldException("輸入框ID非法");
    }
   
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：新增客戶
     * 
     * @param customer  客戶實體
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    public Response addCustomerService(Customer customer) throws Exception{
    
        //CustomerDAO customerDAO = new CustomerDAO(emf);
        
        customer.setCustomerMasterId(UUID.randomUUID().toString().toUpperCase());
        customer.setCustomerId(customerDAO.getCustomerId());
        
        return customerDAO.create(customer);
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：獲取產品列表信息，用於新增客戶產品單價
     * 
     * @param productId  產品編號
     * @return 
     ********************************************************************************/
    public Response getProductListService(String productId){

        return productDAO.findProductListByProductId(productId);
    }
    
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：通過客戶編號獲取客戶資訊
     * 
     * @param customerId  客戶編號
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response getCustomerForUpdateService(String customerId) throws NonexistentEntityException{
        
        Response result =  customerDAO.findCustomerByCustomerId(customerId);
        
        if(result.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶資料不存在");
        }

        return result;
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：修改客戶資訊
     * 
     * @param customer  客戶實體
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response updateCustomerService(Customer customer) throws NonexistentEntityException, Exception{

        //CustomerDAO customerDAO = new CustomerDAO(emf);
        
        Response queryCustomer = customerDAO.findCustomerByCustomerId(customer.getCustomerId());
        
        if(queryCustomer.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶資料不存在");
        }
        
        //設置新客戶實體中一對多關係的映射集合
        Customer customerOld =  (Customer)queryCustomer.getData();
        customer.setCustomerPriceMasterCollection(customerOld.getCustomerPriceMasterCollection());
        customer.setOrderHeadMasterCollection(customerOld.getOrderHeadMasterCollection());
        
        return customerDAO.edit(customer);
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：刪除客戶資訊
     * 
     * @param customerId     客戶編號
     * @param versionNumber  資料刪除前的版本號
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response deleteCustomer(String customerId,int versionNumber) throws NonexistentEntityException, Exception{

        //CustomerDAO customerDAO = new CustomerDAO(emf);
        
        Response queryCustomer = customerDAO.findCustomerByCustomerId(customerId);
        
        if(queryCustomer.isEmpty()){
            throw new NonexistentEntityException("要刪除的客戶資料不存在");
        }
        Customer customer = (Customer) queryCustomer.getData();
        
        if(customer.getOrderHeadMasterCollection().size() > 0){
            throw new ValidationException("該客戶有訂單，不能刪除");
        }
        customer.setVersionNumber(versionNumber);
        return customerDAO.deleteCustomer(customer);
    }
   
}
