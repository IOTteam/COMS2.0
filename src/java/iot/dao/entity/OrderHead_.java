package iot.dao.entity;

import iot.dao.entity.Customer;
import iot.dao.entity.OrderDetail;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-14T15:47:59")
@StaticMetamodel(OrderHead.class)
public class OrderHead_ { 

    public static volatile SingularAttribute<OrderHead, Boolean> deleteStatus;
    public static volatile CollectionAttribute<OrderHead, OrderDetail> orderDetailMasterCollection;
    public static volatile SingularAttribute<OrderHead, String> orderHeadId;
    public static volatile SingularAttribute<OrderHead, Customer> customerMasterId;
    public static volatile SingularAttribute<OrderHead, Date> orderDate;
    public static volatile SingularAttribute<OrderHead, String> ordheadMasterId;
    public static volatile SingularAttribute<OrderHead, Integer> versionNumber;

}