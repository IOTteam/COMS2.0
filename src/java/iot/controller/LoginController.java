/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.controller;

import com.google.code.kaptcha.Constants;
import iot.dao.entity.User;
import com.google.code.kaptcha.Producer;
import iot.dao.repository.UserDAO;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.ModelAndView;

/**
 *
 * @author hatanococoro
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    @Autowired  
    private Producer captchaProducer = null;  
    
    @Autowired
    private EntityManagerFactory emf;
    
    //进入登录页面
    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(){ 
        return "login";
    }
    
    //用户登录
    @RequestMapping(method = RequestMethod.POST)
    public String loginAction(@RequestParam("username") String username,@RequestParam("password") String password,
            @RequestParam("kaptcha") String kaptcha,HttpServletRequest request,ModelMap model){
        //由请求获取session
        HttpSession session = request.getSession();  
        //获取验证码文字
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
        //判断验证码是否正确
//        if (!code.equals(kaptcha)) {
//            model.addAttribute("message_k", "验证码错误");
//            return "login";
//        }
        //实例化UserDaoImpl，通过账户名、密码查询用户
        UserDAO ud = new UserDAO(emf);
        User user = ud.getUserByNameAndPassword(username,password);
        
        if (user == null){//未查询到用户，返回错误信息
            model.addAttribute("message", "用户名或密码错误");
            return "login";
        }else{//将user实体存储到session，重定向到首页
            model.addAttribute("user", user);
            return "redirect:/index";
        }
    }
    
   //生成验证码
    @RequestMapping(value = "captcha-image")  
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        //获取session
        HttpSession session = request.getSession();  
        //禁用浏览器缓存
        response.setDateHeader("Expires", 0); 
        //设置不存储请求和响应信息、不缓存页面信息、客户机每次请求验证缓存是否过期
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache"); 
        //响应类型为jpeg图片
        response.setContentType("image/jpeg");          
        //生成验证码文字，存入session
        String capText = captchaProducer.createText();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);         
        //由验证码文字生成图像
        BufferedImage bi = captchaProducer.createImage(capText); 
        //输出图像
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        //强制清空缓冲区，立即输出图像
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }
    
    //查询用户信息
    @RequestMapping(value = "userInfo",method = RequestMethod.GET)
    public String getUserInfo(@ModelAttribute("user") User user, ModelMap model){
        
        model.addAttribute("user", user);
        return "userInfo";
        
    }

    //修改密码
    @RequestMapping(value = "editPassword",method = RequestMethod.POST)
    @ResponseBody
    public String editPassword(@ModelAttribute("user") User user,@RequestParam("passwordOld") String passwordOld,@RequestParam("passwordNew") String passwordNew,
            @RequestParam("passwordConfirm") String passwordConfirm,ModelMap model) throws Exception{

        //原密码是否正确
        if(passwordOld.equals(user.getUserPass())){
            //两次输入的密码是否相同
            if(passwordNew.equals(passwordConfirm)){
                //设置新密码，并更新到数据库
                user.setUserPass(passwordNew);
                UserDAO ud = new UserDAO(emf);
                ud.edit(user);   
            }else{
            //两次输入的密码不同
            return "confirm error";    
            }           
        }else{
            //原密码错误
             return "password error";
        }
        //修改密码成功
        return "success";
    }
    //注销
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request,ModelMap model){
        //获取session中的key
        Enumeration<String> em = request.getSession().getAttributeNames();
        //遍历删除session中的信息
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement());
        }
        //销毁session
        request.getSession().invalidate();
        //清空model
        model.clear();
        return "redirect:/login";
        
    } 
    
}
