/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.service;

import iot.dao.entity.User;
import iot.dao.repository.UserDAO;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SWM Lee
 */
@Service
public class LoginService {

    @Autowired
    private UserDAO umdao;

    //判斷輸入的賬號密碼各種情況
    public String UserLogin(String username, String userpass) throws Exception {
//        //創建loginService對象，便於訪問類內的方法
//        LoginService loginservice = new LoginService();

        //UserDAO umdao = new UserDAO(emf);
        //調用方法，根據帳戶名查詢數據,數據存在於對象 userMaster中
        User umn = umdao.getUserByUserName(username);
        //如果帳號不存在
        if (umn == null) {
            return "isNull";
        }//如果帳號不存在的否則，即帳號存在 
        else {
            //如果賬號被鎖定
            if (umn.getIslocked() == true) {

                return "isLocked";
            } //“如果賬號被鎖定”的否則，即帳號未被鎖定
            else {
                //如果密碼不匹配
                if (!umn.getUserPass().equals(userpass)) {
                    //取得該賬戶中“wrongUserPassTimes”的數值
                    int wut = umn.getWrongUserpassTimes();
                    //如果該數值+1=3，說明到了該鎖定的時間了
                    if ((wut + 1) == 3) {
                        //將islocked設爲true
                        umn.setIslocked(true);
                        //將數據庫中wrongUserpassTimes設爲3
                        umn.setWrongUserpassTimes(3);
                        //設置鎖定時間
                        umn.setLockedTime(new Date());

                        //保存中修改後設置的數值
                        umdao.edit(umn);

                        //此處調用自動解鎖函數
                        umdao.autoUnlock(umn);

                        //密碼輸錯次數過多，賬號將被鎖定
                        return "willBeLocked";
                    } //“如果該數值+1=3”的否則，即wut+1<3
                    else {
                        //把wronguserpasstimes次數+1，
                        umn.setWrongUserpassTimes(wut + 1);

                        //保存修改的數值存到數據庫
                        umdao.edit(umn);

                        //密碼輸入錯誤，請重新輸入
                        return "tryAgain";
                    }
                } //"如果密碼不匹配"的否則，即密碼匹配
                else {
                    //把鎖定狀態置爲false
                    umn.setIslocked(false);
                    //把wut重置爲0
                    umn.setWrongUserpassTimes(0);

                    //保存修改
                    umdao.edit(umn);

                    return "isMatch";
                }
            }
        }
    }

    //通過賬號密碼匹配數據
    public User getUserInfo(String username, String userpass) {
        //UserDAO umdao = new UserDAO(emf);
        User um = umdao.getUserByNameAndPassword(username, userpass);
        return um;
    }

    //修改賬號的密碼
    public void editPasswordService(User user) throws Exception {
        //UserDAO umdao = new UserDAO(emf);
        umdao.edit(user);
    }
}
