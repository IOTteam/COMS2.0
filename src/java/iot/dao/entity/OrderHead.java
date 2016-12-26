/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author hatanococoro
 */
@Entity
@Table(name = "order_head_master")
@NamedQueries({
    @NamedQuery(name = "OrderHead.findAll", query = "SELECT o FROM OrderHead o"),
    @NamedQuery(name = "OrderHead.findByOrdheadMasterId", query = "SELECT o FROM OrderHead o WHERE o.ordheadMasterId = :ordheadMasterId"),
    @NamedQuery(name = "OrderHead.findByOrderHeadId", query = "SELECT o FROM OrderHead o WHERE o.orderHeadId = :orderHeadId"),
    @NamedQuery(name = "OrderHead.findByOrderDate", query = "SELECT o FROM OrderHead o WHERE o.orderDate = :orderDate"),
    @NamedQuery(name = "OrderHead.findByVersionNumber", query = "SELECT o FROM OrderHead o WHERE o.versionNumber = :versionNumber"),
    @NamedQuery(name = "OrderHead.findByDeleteStatus", query = "SELECT o FROM OrderHead o WHERE o.deleteStatus = :deleteStatus")})
public class OrderHead implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ordhead_master_id")
    private String ordheadMasterId;
    @Basic(optional = false)
    @Column(name = "order_head_id")
    private String orderHeadId;
    @Basic(optional = false)
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Version
    @Column(name = "version_number")
    private Integer versionNumber;
    @Basic(optional = false)
    @Column(name = "delete_status")
    private boolean deleteStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordheadMasterId")
    private Collection<OrderDetail> orderDetailMasterCollection;
    @JoinColumn(name = "customer_master_id", referencedColumnName = "customer_master_id")
    @ManyToOne(optional = false)
    private Customer customerMasterId;

    public OrderHead() {
    }

    public OrderHead(String ordheadMasterId) {
        this.ordheadMasterId = ordheadMasterId;
    }

    public OrderHead(String ordheadMasterId, String orderHeadId, Date orderDate, boolean deleteStatus) {
        this.ordheadMasterId = ordheadMasterId;
        this.orderHeadId = orderHeadId;
        this.orderDate = orderDate;
        this.deleteStatus = deleteStatus;
    }

    public String getOrdheadMasterId() {
        return ordheadMasterId;
    }

    public void setOrdheadMasterId(String ordheadMasterId) {
        this.ordheadMasterId = ordheadMasterId;
    }

    public String getOrderHeadId() {
        return orderHeadId;
    }

    public void setOrderHeadId(String orderHeadId) {
        this.orderHeadId = orderHeadId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
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

    public Customer getCustomerMasterId() {
        return customerMasterId;
    }

    public void setCustomerMasterId(Customer customerMasterId) {
        this.customerMasterId = customerMasterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordheadMasterId != null ? ordheadMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderHead)) {
            return false;
        }
        OrderHead other = (OrderHead) object;
        if ((this.ordheadMasterId == null && other.ordheadMasterId != null) || (this.ordheadMasterId != null && !this.ordheadMasterId.equals(other.ordheadMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iot.dao.entity.OrderHeadMaster[ ordheadMasterId=" + ordheadMasterId + " ]";
    }
    
}
