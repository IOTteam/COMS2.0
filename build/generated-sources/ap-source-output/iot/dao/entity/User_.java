package iot.dao.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-14T15:47:59")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Boolean> deleteStatus;
    public static volatile SingularAttribute<User, String> userPass;
    public static volatile SingularAttribute<User, Boolean> islocked;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, String> userId;
    public static volatile SingularAttribute<User, String> userMasterId;
    public static volatile SingularAttribute<User, Date> lockedTime;
    public static volatile SingularAttribute<User, Integer> wrongUserpassTimes;

}