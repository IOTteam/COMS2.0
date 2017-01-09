/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 *
 * @author David Su
 */
@Aspect
@Component
public class OrderValidate {

    @Pointcut(value = "execution(* iot.controller.OrderController.addOrderDetail(..))&&args(productId,orderQty,orderHeadId)", argNames = "productId,orderQty,orderHeadId")
    public void pointCutAddOrderDetail(String productId, String orderQty, String orderHeadId) {
    }

    @Pointcut(value = "execution(* iot.controller.OrderController.queryOrderHeadList(..))&&args(orderHeadIdMin,orderHeadIdMax,customerName,orderDateMin,orderDateMax,pageNo,model)",
            argNames = "orderHeadIdMin,orderHeadIdMax,customerName,orderDateMin,orderDateMax,pageNo,model")
    public void pointCutQueryOrderHeadList(String orderHeadIdMin, String orderHeadIdMax, String customerName, String orderDateMin, String orderDateMax, String pageNo,ModelMap model) {
    }

    @Around(value = "pointCutAddOrderDetail(productId,orderQty,orderHeadId)", argNames = "productId,orderQty,orderHeadId")
    public Object validateAddOrderDetail(ProceedingJoinPoint pjp, String productId, String orderQty, String orderHeadId) throws Throwable {
        Object result;
        Pattern p1 = Pattern.compile("PRO[0-9]{11}");
        Matcher m1 = p1.matcher(productId);
        if (!m1.matches()) {
            throw new ValidationException("您輸入的產品編號不對！示例如:PRO20160101001。");
        }
        Pattern p2 = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher m2 = p2.matcher(orderQty);
        if (!m2.matches()) {
            throw new ValidationException("您輸入的下單數量不符合規範，請輸入正整數。");
        }
        Pattern p3 = Pattern.compile("^ORDH[0-9]{11}");
        Matcher m3 = p3.matcher(orderHeadId);
        if (!m3.matches()) {
            throw new ValidationException("傳入的訂單頭檔編號格式不對，請檢查。");
        }
        result = pjp.proceed();
        return result;
    }

    @Around(value = "pointCutQueryOrderHeadList(orderHeadIdMin,orderHeadIdMax,customerName,orderDateMin,orderDateMax,pageNo,model)",
            argNames = "orderHeadIdMin,orderHeadIdMax,customerName,orderDateMin,orderDateMax,pageNo,model")
    public Object validateQueryOrderHeadList(ProceedingJoinPoint pjp, String orderHeadIdMin, String orderHeadIdMax,
            String customerName, String orderDateMin, String orderDateMax, String pageNo,ModelMap model) throws Throwable {
        Object result;
        Pattern p = Pattern.compile("^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$");
        Matcher m1 = p.matcher(orderDateMax);
        Matcher m2 = p.matcher(orderDateMin);
        if (!m2.matches()) {
            throw new ValidationException("您輸入的下單日期*起點*不符合規範！");
        }
        if (!m1.matches()) {
            throw new ValidationException("您輸入的下單日期*終點*不符合規範！");
        }
        result = pjp.proceed();
        return result;
    }

}
