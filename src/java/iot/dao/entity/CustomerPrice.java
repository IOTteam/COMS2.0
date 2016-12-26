/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;

/**
 *
 * @author hatanococoro
 */
@Entity
@Table(name = "customer_price_master")
@NamedQueries({
    @NamedQuery(name = "CustomerPrice.findAll", query = "SELECT c FROM CustomerPrice c"),
    @NamedQuery(name = "CustomerPrice.findByCusPriceMasterId", query = "SELECT c FROM CustomerPrice c WHERE c.cusPriceMasterId = :cusPriceMasterId"),
    @NamedQuery(name = "CustomerPrice.findByCustomerPriceId", query = "SELECT c FROM CustomerPrice c WHERE c.customerPriceId = :customerPriceId"),
    @NamedQuery(name = "CustomerPrice.findByRangeMin", query = "SELECT c FROM CustomerPrice c WHERE c.rangeMin = :rangeMin"),
    @NamedQuery(name = "CustomerPrice.findByRangeMax", query = "SELECT c FROM CustomerPrice c WHERE c.rangeMax = :rangeMax"),
    @NamedQuery(name = "CustomerPrice.findByRangePrice", query = "SELECT c FROM CustomerPrice c WHERE c.rangePrice = :rangePrice"),
    @NamedQuery(name = "CustomerPrice.findByDeleteStatus", query = "SELECT c FROM CustomerPrice c WHERE c.deleteStatus = :deleteStatus")})
public class CustomerPrice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cus_price_master_id")
    private String cusPriceMasterId;
    @Basic(optional = false)
    @Column(name = "customer_price_id")
    private String customerPriceId;
    @Basic(optional = false)
    @Digits(integer = 10,fraction = 0,message = "數據格式只能是整數，且長度最多10位")
    @Column(name = "range_min")
    private int rangeMin;
    @Basic(optional = false)
    @Digits(integer = 10,fraction = 0,message = "數據格式只能是整數，且長度最多10位")
    @Column(name = "range_max")
    private int rangeMax;
    @Basic(optional = false)
    @Digits(integer = 8,fraction = 2,message = "整數字元最多只能有8位，小數部分最多只能有2位")
    @Column(name = "range_price")
    private float rangePrice;
    @Version
    @Basic(optional = false)
    @Column(name = "version_number")
    private int versionNumber;
    @Basic(optional = false)
    @Column(name = "delete_status")
    private boolean deleteStatus;
    @JoinColumn(name = "customer_master_id", referencedColumnName = "customer_master_id")
    @ManyToOne(optional = false)
    private Customer customerMasterId;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private Product productMasterId;

    public CustomerPrice() {
    }

    public CustomerPrice(String cusPriceMasterId) {
        this.cusPriceMasterId = cusPriceMasterId;
    }

    public CustomerPrice(String cusPriceMasterId, String customerPriceId, int rangeMin, int rangeMax, float rangePrice, int versionNumber, boolean deleteStatus) {
        this.cusPriceMasterId = cusPriceMasterId;
        this.customerPriceId = customerPriceId;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.rangePrice = rangePrice;
        this.versionNumber = versionNumber;
        this.deleteStatus = deleteStatus;
    }

    public String getCusPriceMasterId() {
        return cusPriceMasterId;
    }

    public void setCusPriceMasterId(String cusPriceMasterId) {
        this.cusPriceMasterId = cusPriceMasterId;
    }

    public String getCustomerPriceId() {
        return customerPriceId;
    }

    public void setCustomerPriceId(String customerPriceId) {
        this.customerPriceId = customerPriceId;
    }

    public int getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(int rangeMin) {
        this.rangeMin = rangeMin;
    }

    public int getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(int rangeMax) {
        this.rangeMax = rangeMax;
    }

    public float getRangePrice() {
        return rangePrice;
    }

    public void setRangePrice(float rangePrice) {
        this.rangePrice = rangePrice;
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

    public Customer getCustomerMasterId() {
        return customerMasterId;
    }

    public void setCustomerMasterId(Customer customerMasterId) {
        this.customerMasterId = customerMasterId;
    }

    public Product getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(Product productMasterId) {
        this.productMasterId = productMasterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cusPriceMasterId != null ? cusPriceMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerPrice)) {
            return false;
        }
        CustomerPrice other = (CustomerPrice) object;
        if ((this.cusPriceMasterId == null && other.cusPriceMasterId != null) || (this.cusPriceMasterId != null && !this.cusPriceMasterId.equals(other.cusPriceMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iot.dao.entity.CustomerPriceMaster[ cusPriceMasterId=" + cusPriceMasterId + " ]";
    }
    
}
