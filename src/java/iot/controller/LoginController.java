/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.controller;

import iot.dao.entity.User;
import iot.response.Response;
import iot.service.LoginService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author SWM Lee
 */
@Controller
@RequestMapping("/login")
@SessionAttributes({"user"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    //生成驗證碼方法的參數
    private final int width = 130;//定義圖片的width
    private final int height = 44;//定義圖片的height
    private final int codeCount = 4;//定義圖片上顯示驗證碼的個數
    private final int xx = 30;//圖片驗證碼數字x軸間隔
    private final int fontHeight = 40;//字體大小
    private final int codeY = 36;//y軸
    //驗證碼由哪些內容組成（大寫英文字母和數字）
    char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    //載入登陸介面
    @RequestMapping(method = RequestMethod.GET)
    public String loadLoginPage() {
        return "login";
    }

    //用戶登入
    @RequestMapping(method = RequestMethod.POST)
    public String userLogin(@RequestParam("userName") String username,
            @RequestParam("userPass") String userpass,
            @RequestParam("kaptcha") String kaptcha,
            HttpServletRequest request, ModelMap model) throws Exception {
        //由請求獲取session
        HttpSession session = request.getSession();
        //獲取驗證碼文字
        String code = (String) session.getAttribute("code");
        //判斷驗證碼是否正確，區分大小寫   
//        if (!code.equals(kaptcha)) {
//            model.addAttribute("message_k", "验证码错误");
//            return "login";
//        }
        //調用service層的UserLogin方法
        String umn = loginService.UserLogin(username, userpass);

        //根據UserLogin中返回的不同字串，打印不同的反饋內容給前臺
        if ("isNull".equals(umn)) { //賬號不存在
            model.addAttribute("message", "賬號不存在");
            return "login";
        } else if ("isLocked".equals(umn)) { //帳號被鎖定了
            model.addAttribute("message", "帳號目前是被鎖定的，請稍候再試");
            return "login";
        } else if ("willBeLocked".equals(umn)) { //密碼輸錯次數過多，賬號將被鎖定
            model.addAttribute("message", "密碼輸錯次數過多，帳號將被鎖定1小時");
            return "login";
        } else if ("tryAgain".equals(umn)) { //密碼輸入錯誤，請重新輸入
            model.addAttribute("message", "密碼輸入錯誤");
            return "login";
        } else {//將user實體存儲到session，重定向到首頁
            User um = loginService.getUserInfo(username, userpass);
            model.addAttribute("user", um);
            return "redirect:/index";
        }
    }

    //生成驗證碼方法
    @RequestMapping("/captcha-image")
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        //定義圖像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //Graphics2D gd = buffImg.createGraphics();
        //Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();

        // 創建一個隨機數產生器類
        Random random = new Random();

        //將圖像填充為白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        //創建字體，字體的大小應該根據圖片的高度來定。
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);

        //設置字體。
        gd.setFont(font);

        //畫邊框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        //隨機產生10條干擾線，使圖像中的認證碼不易被其它程式探測到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        //randomCode用於保存隨機產生的驗證碼，以便用戶登錄後進行驗證。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        //隨機產生codeCount數字的驗證碼。
        for (int i = 0; i < codeCount; i++) {

            //得到隨機產生的驗證碼數位。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);

            //產生隨機的顏色分量來構造顏色值，這樣輸出的每位數字的顏色值都將不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用隨機產生的顏色將驗證碼繪製到圖像中
            gd.setColor(new Color(red, green, blue));
            //gd.drawString(code, (i + 1) * xx, codeY);
            gd.drawString(code, (i) * xx + 10, codeY);

            // 將產生的4個隨機數結合到一起
            randomCode.append(code);
        }

        // 將4位數的驗證碼保存到Session中
        HttpSession session = req.getSession();
        System.out.print(randomCode);
        session.setAttribute("code", randomCode.toString());

        //禁止圖像緩存
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        //將圖像輸出到Servlet輸出流中
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

    //修改密碼
    @RequestMapping(value = "editPassword", method = RequestMethod.POST)
    //@ResponseBody註解，將return的值轉化爲json格式
    @ResponseBody
    public Response editPassword(@ModelAttribute("user") User user,
            @RequestParam("userPassOld") String userPassOld,
            @RequestParam("userPassNew") String userPassNew,
            @RequestParam("userPassConfirm") String userPassConfirm, ModelMap model) throws Exception {

        //如果原密碼輸入正確
        if (userPassOld.equals(user.getUserPass())) {
            //如果兩次輸入的密碼一致
            if (userPassNew.equals(userPassConfirm)) {
                //如果新舊密碼不一致
                if (!userPassNew.equals(userPassOld)) {
                    //設置新密碼，並更新到數據庫
                    user.setUserPass(userPassNew);
                    loginService.editPasswordService(user);
                } //新舊密碼不一致的否則，即新舊密碼一致 
                else {
                    throw new ValidationException("新舊密碼一致!");
                    //return new Response().failure("same old new");
                }
            }//兩次輸入密碼一致的否則，兩次輸入的密碼不一致 
            else {
                //兩次輸入密碼不一致
                throw new ValidationException("兩次輸入密碼不一致!");                
                //return new Response().failure("sconfirm error");
            }
        }//原密碼輸入正確的否則，即原密碼輸入 
        else {
            //原密碼錯誤
            throw new ValidationException("原密碼錯誤!");
            //return new Response().failure("password error");
        }
        //修改密碼成功

        return new Response().success("修改密碼成功!");
    }

    //登出
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, ModelMap model) {
        //獲取session中的key
        Enumeration<String> em = request.getSession().getAttributeNames();
        //遍歷刪除session中的信息
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement());
        }
        //銷燬session
        request.getSession().invalidate();
        //清空model
        model.clear();
        return "redirect:/login";

    }
}
