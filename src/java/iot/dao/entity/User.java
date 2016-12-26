/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hatanococoro
 */
@Entity
@Table(name = "user_master")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserMasterId", query = "SELECT u FROM User u WHERE u.userMasterId = :userMasterId"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByUserPass", query = "SELECT u FROM User u WHERE u.userPass = :userPass"),
    @NamedQuery(name = "User.findByWrongUserpassTimes", query = "SELECT u FROM User u WHERE u.wrongUserpassTimes = :wrongUserpassTimes"),
    @NamedQuery(name = "User.findByIslocked", query = "SELECT u FROM User u WHERE u.islocked = :islocked"),
    @NamedQuery(name = "User.findByLockedTime", query = "SELECT u FROM User u WHERE u.lockedTime = :lockedTime"),
    @NamedQuery(name = "User.findByDeleteStatus", query = "SELECT u FROM User u WHERE u.deleteStatus = :deleteStatus")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_master_id")
    private String userMasterId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @Column(name = "user_pass")
    private String userPass;
    @Basic(optional = false)
    @Column(name = "wrong_userpass_times")
    private int wrongUserpassTimes;
    @Basic(optional = false)
    @Column(name = "islocked")
    private boolean islocked;
    @Column(name = "locked_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedTime;
    @Basic(optional = false)
    @Column(name = "delete_status")
    private boolean deleteStatus;

    public User() {
    }

    public User(String userMasterId) {
        this.userMasterId = userMasterId;
    }

    public User(String userMasterId, String userId, String userName, String userPass, int wrongUserpassTimes, boolean islocked, boolean deleteStatus) {
        this.userMasterId = userMasterId;
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
        this.wrongUserpassTimes = wrongUserpassTimes;
        this.islocked = islocked;
        this.deleteStatus = deleteStatus;
    }

    public String getUserMasterId() {
        return userMasterId;
    }

    public void setUserMasterId(String userMasterId) {
        this.userMasterId = userMasterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getWrongUserpassTimes() {
        return wrongUserpassTimes;
    }

    public void setWrongUserpassTimes(int wrongUserpassTimes) {
        this.wrongUserpassTimes = wrongUserpassTimes;
    }

    public boolean getIslocked() {
        return islocked;
    }

    public void setIslocked(boolean islocked) {
        this.islocked = islocked;
    }

    public Date getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Date lockedTime) {
        this.lockedTime = lockedTime;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userMasterId != null ? userMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userMasterId == null && other.userMasterId != null) || (this.userMasterId != null && !this.userMasterId.equals(other.userMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iot.dao.entity.UserMaster[ userMasterId=" + userMasterId + " ]";
    }
    
}
