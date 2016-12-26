package iot.dao.entity;

import iot.dao.entity.CustomerPrice;
import iot.dao.entity.OrderDetail;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-14T15:47:59")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> productId;
    public static volatile SingularAttribute<Product, Boolean> deleteStatus;
    public static volatile CollectionAttribute<Product, OrderDetail> orderDetailMasterCollection;
    public static volatile SingularAttribute<Product, String> productMasterId;
    public static volatile SingularAttribute<Product, Boolean> discountStatus;
    public static volatile CollectionAttribute<Product, CustomerPrice> customerPriceMasterCollection;
    public static volatile SingularAttribute<Product, String> productSpec;
    public static volatile SingularAttribute<Product, String> productName;
    public static volatile SingularAttribute<Product, Integer> versionNumber;
    public static volatile SingularAttribute<Product, Float> productStandardPrice;

}