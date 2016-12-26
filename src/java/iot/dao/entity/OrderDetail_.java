package iot.dao.entity;

import iot.dao.entity.OrderHead;
import iot.dao.entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-14T15:47:59")
@StaticMetamodel(OrderDetail.class)
public class OrderDetail_ { 

    public static volatile SingularAttribute<OrderDetail, Boolean> deleteStatus;
    public static volatile SingularAttribute<OrderDetail, Product> productMasterId;
    public static volatile SingularAttribute<OrderDetail, Integer> orderQty;
    public static volatile SingularAttribute<OrderDetail, Float> orderPrice;
    public static volatile SingularAttribute<OrderDetail, String> orddetailMasterId;
    public static volatile SingularAttribute<OrderDetail, String> orderDetailId;
    public static volatile SingularAttribute<OrderDetail, Integer> versionNumber;
    public static volatile SingularAttribute<OrderDetail, OrderHead> ordheadMasterId;

}