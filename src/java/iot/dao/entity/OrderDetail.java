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

/**
 *
 * @author hatanococoro
 */
@Entity
@Table(name = "order_detail_master")
@NamedQueries({
    @NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o"),
    @NamedQuery(name = "OrderDetail.findByOrddetailMasterId", query = "SELECT o FROM OrderDetail o WHERE o.orddetailMasterId = :orddetailMasterId"),
    @NamedQuery(name = "OrderDetail.findByOrderDetailId", query = "SELECT o FROM OrderDetail o WHERE o.orderDetailId = :orderDetailId"),
    @NamedQuery(name = "OrderDetail.findByOrderQty", query = "SELECT o FROM OrderDetail o WHERE o.orderQty = :orderQty"),
    @NamedQuery(name = "OrderDetail.findByOrderPrice", query = "SELECT o FROM OrderDetail o WHERE o.orderPrice = :orderPrice"),
    @NamedQuery(name = "OrderDetail.findByVersionNumber", query = "SELECT o FROM OrderDetail o WHERE o.versionNumber = :versionNumber"),
    @NamedQuery(name = "OrderDetail.findByDeleteStatus", query = "SELECT o FROM OrderDetail o WHERE o.deleteStatus = :deleteStatus")})
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "orddetail_master_id")
    private String orddetailMasterId;
    @Basic(optional = false)
    @Column(name = "order_detail_id")
    private String orderDetailId;
    @Basic(optional = false)
    @Column(name = "order_qty")
    private int orderQty;
    @Basic(optional = false)
    @Column(name = "order_price")
    private float orderPrice;
    @Version
    @Column(name = "version_number")
    private Integer versionNumber;
    @Basic(optional = false)
    @Column(name = "delete_status")
    private boolean deleteStatus;
    @JoinColumn(name = "ordhead_master_id", referencedColumnName = "ordhead_master_id")
    @ManyToOne(optional = false)
    private OrderHead ordheadMasterId;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private Product productMasterId;

    public OrderDetail() {
    }

    public OrderDetail(String orddetailMasterId) {
        this.orddetailMasterId = orddetailMasterId;
    }

    public OrderDetail(String orddetailMasterId, String orderDetailId, int orderQty, float orderPrice, boolean deleteStatus) {
        this.orddetailMasterId = orddetailMasterId;
        this.orderDetailId = orderDetailId;
        this.orderQty = orderQty;
        this.orderPrice = orderPrice;
        this.deleteStatus = deleteStatus;
    }

    public String getOrddetailMasterId() {
        return orddetailMasterId;
    }

    public void setOrddetailMasterId(String orddetailMasterId) {
        this.orddetailMasterId = orddetailMasterId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
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

    public OrderHead getOrdheadMasterId() {
        return ordheadMasterId;
    }

    public void setOrdheadMasterId(OrderHead ordheadMasterId) {
        this.ordheadMasterId = ordheadMasterId;
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
        hash += (orddetailMasterId != null ? orddetailMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetail)) {
            return false;
        }
        OrderDetail other = (OrderDetail) object;
        if ((this.orddetailMasterId == null && other.orddetailMasterId != null) || (this.orddetailMasterId != null && !this.orddetailMasterId.equals(other.orddetailMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iot.dao.entity.OrderDetailMaster[ orddetailMasterId=" + orddetailMasterId + " ]";
    }
    
}
