/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.service;


import iot.dao.entity.Customer;
import iot.dao.entity.CustomerPrice;
import iot.dao.entity.Product;
import iot.dao.repository.CustomerDAO;
import iot.dao.repository.CustomerPriceDAO;
import iot.dao.repository.ProductDAO;
import iot.dao.repository.exceptions.JPAQueryException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hatanococoro
 */
@Service
public class CustomerPriceService {

    @Autowired
    private EntityManagerFactory emf;//声明一个实体管理工厂，创建实体管理，使用实体管理操作数据库
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：條件查詢客戶產品單價
     * 
     * @param productName
     * @param priceMin
     * @param rangeMin
     * @param priceMax
     * @param rangeMax
     * @param customerName
     * @param pageNo
     * @return 
     ********************************************************************************/
    public Response queryCustomerPriceService(String customerName, String productName, String priceMin,String priceMax, String rangeMin, String rangeMax, int pageNo) throws JPAQueryException{
    
        CustomerDAO customerDAO = new CustomerDAO(emf);
        List<Customer> customerList = (List<Customer>) customerDAO.findCustomerByCustomerName(customerName).getData();
        
        //通过模糊查询产品名称找到产品实体
        ProductDAO productDAO = new ProductDAO(emf);
        List<Product> productList = (List<Product>) productDAO.findProductByProductName(productName).getData();
        
        
        CustomerPriceDAO customerPriceDAO = new CustomerPriceDAO(emf);
        Response customerPriceQueryResult = customerPriceDAO.findCustomerPricesByCondition(customerList,productList,priceMin,priceMax,rangeMin,rangeMax,pageNo);

        return customerPriceQueryResult;
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過客戶編號查詢客戶產品單價資訊
     * 
     * @param customerId
     * @param pageNo
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response queryCustomerPriceService(String customerId,int pageNo) throws NonexistentEntityException{
    
        CustomerDAO customerDAO = new CustomerDAO(emf);
        Response response = customerDAO.findCustomerByCustomerId(customerId);
        
        if(response.isEmpty()){
            throw new NonexistentEntityException(response.getMessage());
        }
        
        Customer customer = (Customer)response.getData();
        
        List<CustomerPrice> customerPriceList = (List<CustomerPrice>)customer.getCustomerPriceMasterCollection();
        
        int count = 0;
        List responseList = new ArrayList<>();
        
        //使用迭代器移除被邏輯刪除的客戶產品單價并順序取5條數據
//        Iterator iterator = customerPriceList.listIterator();
//        while(iterator.hasNext()){
//            CustomerPrice customerPrice = (CustomerPrice) iterator.next();
//            
//            if(customerPrice.getDeleteStatus() == false){
//                ++count;
//                if(count > pageNo*5 && responseList.size() < 5){
//                    responseList.add(customerPrice);
//                }
//            }
//            if(customerPrice.getDeleteStatus() == true){
//                 iterator.remove();
//            }
//        }
        int size = customerPriceList.size();
        
        //倒序取customerPriceList中的數據，每次5條
        for(int j = 1; j <= size; j++){
            CustomerPrice customerPrice = customerPriceList.get(size - j);
            if(customerPrice.getDeleteStatus() == false){
                ++count;
                if(count > pageNo*5){
                    List list_row = new ArrayList();
                    list_row.add(customerPrice.getCustomerPriceId());
                    list_row.add(customerPrice.getCustomerMasterId().getCustomerId() + " : " + customerPrice.getCustomerMasterId().getCustomerName());
                    list_row.add(customerPrice.getProductMasterId().getProductId() + " : " + customerPrice.getProductMasterId().getProductName());
                    list_row.add(customerPrice.getRangeMin() + "~" + customerPrice.getRangeMax());
                    list_row.add(customerPrice.getRangePrice());
                    responseList.add(list_row);
                }
            }
            if(responseList.size() == 5){
                break;
            }
        }

        return new Response().success("通過外鍵關聯獲取客戶產品單價",responseList,((size-1)/5) + 1);
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
    public Response getCustomerAndProductListService(String inputId, String customerName, String productName) throws NoSuchFieldException{
    
        if("customer_name_input".equals(inputId)){
            CustomerDAO customerDAO = new CustomerDAO(emf);
            List customerNameList = (List) customerDAO.findCustomerNameListByCustomerName(customerName, false, 0, 10).getData();
            return new Response().success("查詢產品名列表成功", customerNameList);
        }
        
        if("product_name_input".equals(inputId)){
            ProductDAO productDAO = new ProductDAO(emf);
            List productNameList = (List) productDAO.findProductNameListByProductName(productName, true, 0, 10).getData();
            return new Response().success("查詢產品名列表成功", productNameList);
        }

        throw new NoSuchFieldException("輸入框ID非法");
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
    public Response getRangesAndPriceListService(String inputId, String customerName, String productName, String priceMin, String rangeMin, String rangeMax) throws NoSuchFieldException{
        
        CustomerDAO customerDAO = new CustomerDAO(emf);
        List<Customer> customerList = (List<Customer>) customerDAO.findCustomerByCustomerName(customerName).getData();
        
        ProductDAO productDAO = new ProductDAO(emf);
        List<Product> productList = (List<Product>) productDAO.findProductByProductName(productName).getData();
        
        return new CustomerPriceDAO(emf).findRangesAndPriceListByCondition(inputId, customerList, productList, priceMin, rangeMin, rangeMax, false, 0, 10);
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過客戶產品單價編號獲取客戶資訊
     * 
     * @param customerPriceId
     * @return 
     ********************************************************************************/
    public Response getCustomerPriceForUpdateService(String customerPriceId) throws NonexistentEntityException{
        
        Response response = new CustomerPriceDAO(emf).findCustomerPriceByCustomerPriceId(customerPriceId);
        
        if(response.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶產品單價資訊不存在");
        }

        return response;
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
    public Response addCustomerPriceService(String customerId,String productId, CustomerPrice customerPrice) throws Exception{
    
        Customer customer = (Customer)new CustomerDAO(emf).findCustomerByCustomerId(customerId).getData();
        if(customer == null){
            throw new NonexistentEntityException("編號為【"+ customerId +"】的客戶不存在");
        }
        Product product = (Product)new ProductDAO(emf).findProductByProductId(productId).getData();
        if(product == null){
            throw new NonexistentEntityException("編號為【"+ productId +"】的產品不存在");
        }
        CustomerPriceDAO customerPriceDAO = new CustomerPriceDAO(emf);
        //檢驗區間是否有交叉或覆蓋
        customerPriceDAO.validateRanges(customer, product, customerPrice.getRangeMin(), customerPrice.getRangeMax());
        
        customerPrice.setCustomerMasterId(customer);
        customerPrice.setProductMasterId(product);
        customerPrice.setCusPriceMasterId(UUID.randomUUID().toString());
        customerPrice.setCustomerPriceId(customerPriceDAO.getCustomerPriceId());
        
        Response customerPriceAddResult = customerPriceDAO.create(customerPrice);
        Response customerPriceQueryResult = queryCustomerPriceService(customerId,0);
        
        return new Response().success(customerPriceAddResult.getMessage(), customerPriceQueryResult.getData(), customerPriceQueryResult.getCount());
        
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：修改客戶產品單價資訊
     * 
     * @param customerPrice
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response updateCustomerPriceService(CustomerPrice customerPrice) throws NonexistentEntityException, Exception{

        CustomerPriceDAO customerPriceDAO = new CustomerPriceDAO(emf);
        Response response = customerPriceDAO.findCustomerPriceByCustomerPriceId(customerPrice.getCustomerPriceId());
        
        if(response.isEmpty()){
            throw new NonexistentEntityException("要修改的客戶產品單價資訊不存在");
        }
        
        CustomerPrice customerPriceOld =  (CustomerPrice)response.getData();
        
        customerPrice.setCustomerMasterId(customerPriceOld.getCustomerMasterId());
        customerPrice.setProductMasterId(customerPriceOld.getProductMasterId());
        
        return customerPriceDAO.edit(customerPrice);
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：邏輯刪除客戶產品單價資訊
     * 
     * @param customerPriceId
     * @return 
     * @throws iot.dao.repository.exceptions.NonexistentEntityException 
     ********************************************************************************/
    public Response deleteCustomerPriceService(String customerPriceId) throws NonexistentEntityException, Exception{

        CustomerPriceDAO customerPriceDAO = new CustomerPriceDAO(emf);
         
        Response response= customerPriceDAO.findCustomerPriceByCustomerPriceId(customerPriceId);
                
        if(response.isEmpty()){
            throw new NonexistentEntityException("編號為【"+ customerPriceId +"】的客戶產品單價資訊不存在");
        }
        
        CustomerPrice customerPrice = (CustomerPrice)response.getData();
        
        customerPrice.setDeleteStatus(true);
        
        customerPriceDAO.edit(customerPrice);
        Response customerPriceQueryResult = queryCustomerPriceService(customerPrice.getCustomerMasterId().getCustomerId(),0);
        
        return new Response().success("刪除客戶產品單價資訊成功", customerPriceQueryResult.getData(), customerPriceQueryResult.getCount());
    }

}
