package iot.dao.entity;

import iot.dao.entity.CustomerPrice;
import iot.dao.entity.OrderHead;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-14T15:47:59")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, String> customerPhone;
    public static volatile SingularAttribute<Customer, String> customerMail;
    public static volatile SingularAttribute<Customer, Boolean> deleteStatus;
    public static volatile CollectionAttribute<Customer, OrderHead> orderHeadMasterCollection;
    public static volatile SingularAttribute<Customer, String> customerId;
    public static volatile CollectionAttribute<Customer, CustomerPrice> customerPriceMasterCollection;
    public static volatile SingularAttribute<Customer, String> customerMasterId;
    public static volatile SingularAttribute<Customer, String> customerName;
    public static volatile SingularAttribute<Customer, Integer> versionNumber;

}