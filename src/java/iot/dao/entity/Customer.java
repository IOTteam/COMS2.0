/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;

/**
 *
 * @author hatanococoro
 */
@Entity
@Table(name = "customer_master")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerMasterId", query = "SELECT c FROM Customer c WHERE c.customerMasterId = :customerMasterId"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByCustomerName", query = "SELECT c FROM Customer c WHERE c.customerName = :customerName"),
    @NamedQuery(name = "Customer.findByCustomerMail", query = "SELECT c FROM Customer c WHERE c.customerMail = :customerMail"),
    @NamedQuery(name = "Customer.findByCustomerPhone", query = "SELECT c FROM Customer c WHERE c.customerPhone = :customerPhone"),
    @NamedQuery(name = "Customer.findByVersionNumber", query = "SELECT c FROM Customer c WHERE c.versionNumber = :versionNumber"),
    @NamedQuery(name = "Customer.findByDeleteStatus", query = "SELECT c FROM Customer c WHERE c.deleteStatus = :deleteStatus")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_master_id")
    private String customerMasterId;
    @Basic(optional = false)
    @Pattern(regexp = "^CUS20[0-9]{2}[0|1]{1}[0-9]{1}[0|1|2|3]{1}[0-9]{4}$", message = "客戶編號格式不正確")
    @Column(name = "customer_id")
    private String customerId;
    @Basic(optional = false)
    @Pattern(regexp = "^(?!_)(?!.*?_$)[a-zA-Z0-9\\u4e00-\\u9fa5]{1,10}+$", message = "您輸入的客戶名稱有誤，客戶名稱只能由漢字、字母、數字組成,最長為10位")
    @Column(name = "customer_name")
    private String customerName;
    @Basic(optional = false)
    @Pattern(regexp = "^([\\w-_]+(?:\\.[\\w-_]+)*)@((?:[a-z0-9]+(?:-[a-zA-Z0-9]+)*)+\\.[a-z]{2,6})$", message = "您輸入的客戶郵箱資訊有誤，郵箱格式不正確")
    @Column(name = "customer_mail")
    private String customerMail;
    @Basic(optional = false)
    @Pattern(regexp = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$", message = "您輸入的電話號碼有誤，請確認您的電話號碼是否正確，並且是11位的手機號碼")
    @Column(name = "customer_phone")
    private String customerPhone;
    @Version
    @Basic(optional = false)
    @Column(name = "version_number")
    private int versionNumber;
    @Basic(optional = false)
    @Column(name = "delete_status")
    private boolean deleteStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerMasterId")
    private Collection<CustomerPrice> customerPriceMasterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerMasterId")
    private Collection<OrderHead> orderHeadMasterCollection;

    public Customer() {
    }

    public Customer(String customerMasterId) {
        this.customerMasterId = customerMasterId;
    }

    public Customer(String customerMasterId, String customerId, String customerName, String customerMail, String customerPhone, int versionNumber, boolean deleteStatus) {
        this.customerMasterId = customerMasterId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerMail = customerMail;
        this.customerPhone = customerPhone;
        this.versionNumber = versionNumber;
        this.deleteStatus = deleteStatus;
    }

    public String getCustomerMasterId() {
        return customerMasterId;
    }

    public void setCustomerMasterId(String customerMasterId) {
        this.customerMasterId = customerMasterId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @JsonIgnore
    public Collection<CustomerPrice> getCustomerPriceMasterCollection() {
        
        if(customerPriceMasterCollection == null){
            return customerPriceMasterCollection;
        }
        
        Iterator iterator = customerPriceMasterCollection.iterator();
        while(iterator.hasNext()){
            if(((CustomerPrice) iterator.next()).getDeleteStatus() == true){
                 iterator.remove();
            }
        }
        return customerPriceMasterCollection;
    }

    public void setCustomerPriceMasterCollection(Collection<CustomerPrice> customerPriceMasterCollection) {
        this.customerPriceMasterCollection = customerPriceMasterCollection;
    }

    @JsonIgnore
    public Collection<OrderHead> getOrderHeadMasterCollection() {
        return orderHeadMasterCollection;
    }

    public void setOrderHeadMasterCollection(Collection<OrderHead> orderHeadMasterCollection) {
        this.orderHeadMasterCollection = orderHeadMasterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerMasterId != null ? customerMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerMasterId == null && other.customerMasterId != null) || (this.customerMasterId != null && !this.customerMasterId.equals(other.customerMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iot.dao.entity.CustomerMaster[ customerMasterId=" + customerMasterId + " ]";
    }
    
}
