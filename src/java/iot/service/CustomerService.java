/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.service;

import iot.dao.entity.Customer;
import iot.dao.repository.CustomerDAO;
import iot.dao.repository.OrderHeadDAO;
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

/**
 *
 * @author hatanococoro
 */

@Service
public class CustomerService {
    
    @Autowired
    private EntityManagerFactory emf;
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：客戶條件查詢
     * 
     * @param customerIdMin
     * @param customerIdMax
     * @param customerName
     * @param pageNo
     * @return 
     * @throws iot.dao.repository.exceptions.JPAQueryException 
     ********************************************************************************/
    public Response customerQueryService(String customerIdMin, String customerIdMax ,String customerName,int pageNo) throws JPAQueryException{
    
        CustomerDAO customerDAO = new CustomerDAO(emf);
        Response<List<Customer>> customerQueryResult = customerDAO.queryCustomerByCondition(customerIdMin, customerIdMax, customerName, pageNo);
        return customerQueryResult;
    }
   
   /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：查詢客戶姓名、產品名稱輸入框下拉列表數據
     * 
     * @param inputId
     * @param customerName
     * @param customerIdMin
     * @param customerIdMax
     * @return 
     * @throws java.lang.NoSuchFieldException 
     ********************************************************************************/
    public Response getCustomerIdAndCustomerNameListService(String inputId, String customerName,  String customerIdMin, String customerIdMax) throws NoSuchFieldException{
    
        CustomerDAO customerDAO = new CustomerDAO(emf);
        
        if("customer_name_input".equals(inputId)){
            List customerNameList = (List) customerDAO.findCustomerNameListByCustomerName(customerName, false, 0, 10).getData();
            return new Response().success("查詢客戶姓名列表成功", customerNameList);
        }
        
        if("customer_idMin".equals(inputId) || "customer_idMax".equals(inputId)){
            List customerIdList = (List) customerDAO.findCustomerIdListByCustomerId(inputId, customerIdMin, customerIdMax, false, 0, 10).getData();
            return new Response().success("查詢客戶編號列表成功", customerIdList);
        }

        throw new NoSuchFieldException("輸入框ID非法");
    }
   
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：新增客戶
     * 
     * @param customer
     * @return 
     * @throws java.lang.Exception 
     ********************************************************************************/
    public Response addCustomerService(Customer customer) throws Exception{
    
        CustomerDAO customerDAO = new CustomerDAO(emf);
        
        customer.setCustomerMasterId(UUID.randomUUID().toString().toUpperCase());
        customer.setCustomerId(customerDAO.getCustomerId());
        
        return customerDAO.create(customer);
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取產品列表信息，用於新增客戶產品單價
     * 
     * @param productId
     * @return 
     ********************************************************************************/
    public Response getProductListService(String productId){

        return new ProductDAO(emf).findProductListByProductId(productId);
    }
    
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過客戶編號獲取客戶資訊
     * 
     * @param customerId
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response getCustomerForUpdateService(String customerId) throws NonexistentEntityException{
        
        Response result =  new CustomerDAO(emf).findCustomerByCustomerId(customerId);
        
        if(result.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶資料不存在");
        }

        return result;
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：修改客戶資訊
     * 
     * @param customer
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response updateCustomerService(Customer customer) throws NonexistentEntityException, Exception{

        CustomerDAO customerDAO = new CustomerDAO(emf);
        
        Response queryCustomer = customerDAO.findCustomerByCustomerId(customer.getCustomerId());
        
        if(queryCustomer.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶資料不存在");
        }
        
        Customer customerOld =  (Customer)queryCustomer.getData();
        customer.setCustomerPriceMasterCollection(customerOld.getCustomerPriceMasterCollection());
        customer.setOrderHeadMasterCollection(customerOld.getOrderHeadMasterCollection());
        
        return customerDAO.edit(customer);
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：刪除客戶資訊
     * 
     * @param customerId
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response deleteCustomer(String customerId) throws NonexistentEntityException, Exception{

        CustomerDAO customerDAO = new CustomerDAO(emf);
        
        Response queryCustomer = customerDAO.findCustomerByCustomerId(customerId);
        
        if(queryCustomer.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶資料不存在");
        }
        Customer customer = (Customer) queryCustomer.getData();
        
        if(customer.getOrderHeadMasterCollection().size() > 0){
            throw new ValidationException("該客戶有訂單，不能刪除");
        }

        return new CustomerDAO(emf).deleteCustomer(customer);
    }
   
}
