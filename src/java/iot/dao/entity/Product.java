/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
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

/**
 *
 * @author hatanococoro
 */
@Entity
@Table(name = "product_master")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductMasterId", query = "SELECT p FROM Product p WHERE p.productMasterId = :productMasterId"),
    @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId"),
    @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName"),
    @NamedQuery(name = "Product.findByProductSpec", query = "SELECT p FROM Product p WHERE p.productSpec = :productSpec"),
    @NamedQuery(name = "Product.findByProductStandardPrice", query = "SELECT p FROM Product p WHERE p.productStandardPrice = :productStandardPrice"),
    @NamedQuery(name = "Product.findByVersionNumber", query = "SELECT p FROM Product p WHERE p.versionNumber = :versionNumber"),
    @NamedQuery(name = "Product.findByDiscountStatus", query = "SELECT p FROM Product p WHERE p.discountStatus = :discountStatus"),
    @NamedQuery(name = "Product.findByDeleteStatus", query = "SELECT p FROM Product p WHERE p.deleteStatus = :deleteStatus")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "product_master_id")
    private String productMasterId;
    @Basic(optional = false)
    @Column(name = "product_id")
    private String productId;
    @Basic(optional = false)
    @Column(name = "product_name")
    private String productName;
    @Basic(optional = false)
    @Column(name = "product_spec")
    private String productSpec;
    @Basic(optional = false)
    @Column(name = "product_standard_price")
    private float productStandardPrice;
    @Version
    @Column(name = "version_number")
    private Integer versionNumber;
    @Basic(optional = false)
    @Column(name = "discount_status")
    private boolean discountStatus;
    @Basic(optional = false)
    @Column(name = "delete_status")
    private boolean deleteStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<OrderDetail> orderDetailMasterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<CustomerPrice> customerPriceMasterCollection;

    public Product() {
    }

    public Product(String productMasterId) {
        this.productMasterId = productMasterId;
    }

    public Product(String productMasterId, String productId, String productName, String productSpec, float productStandardPrice, boolean discountStatus, boolean deleteStatus) {
        this.productMasterId = productMasterId;
        this.productId = productId;
        this.productName = productName;
        this.productSpec = productSpec;
        this.productStandardPrice = productStandardPrice;
        this.discountStatus = discountStatus;
        this.deleteStatus = deleteStatus;
    }

    public String getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(String productMasterId) {
        this.productMasterId = productMasterId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public float getProductStandardPrice() {
        return productStandardPrice;
    }

    public void setProductStandardPrice(float productStandardPrice) {
        this.productStandardPrice = productStandardPrice;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(boolean discountStatus) {
        this.discountStatus = discountStatus;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @JsonIgnore
    public Collection<OrderDetail> getOrderDetailMasterCollection() {
        return orderDetailMasterCollection;
    }

    public void setOrderDetailMasterCollection(Collection<OrderDetail> orderDetailMasterCollection) {
        this.orderDetailMasterCollection = orderDetailMasterCollection;
    }

    @JsonIgnore
    public Collection<CustomerPrice> getCustomerPriceMasterCollection() {
        return customerPriceMasterCollection;
    }

    public void setCustomerPriceMasterCollection(Collection<CustomerPrice> customerPriceMasterCollection) {
        this.customerPriceMasterCollection = customerPriceMasterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productMasterId != null ? productMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productMasterId == null && other.productMasterId != null) || (this.productMasterId != null && !this.productMasterId.equals(other.productMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iot.dao.entity.ProductMaster[ productMasterId=" + productMasterId + " ]";
    }
    
}
