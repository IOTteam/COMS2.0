package iot.dao.entity;

import iot.dao.entity.Customer;
import iot.dao.entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-14T15:47:59")
@StaticMetamodel(CustomerPrice.class)
public class CustomerPrice_ { 

    public static volatile SingularAttribute<CustomerPrice, String> customerPriceId;
    public static volatile SingularAttribute<CustomerPrice, Integer> rangeMax;
    public static volatile SingularAttribute<CustomerPrice, Boolean> deleteStatus;
    public static volatile SingularAttribute<CustomerPrice, Integer> rangeMin;
    public static volatile SingularAttribute<CustomerPrice, Float> rangePrice;
    public static volatile SingularAttribute<CustomerPrice, Product> productMasterId;
    public static volatile SingularAttribute<CustomerPrice, Customer> customerMasterId;
    public static volatile SingularAttribute<CustomerPrice, String> cusPriceMasterId;
    public static volatile SingularAttribute<CustomerPrice, Integer> versionNumber;

}