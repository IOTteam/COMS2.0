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

    @Around(value = "pointCutAddOrderDetail(productId,orderQty,orderHeadId)", argNames = "productId,orderQty,orderHeadId")
    public Object validateTest(ProceedingJoinPoint pjp, String productId, String orderQty, String orderHeadId) throws Throwable{
        Object result;
        Pattern p = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher m = p.matcher(orderQty);
        if (!m.matches()) {
             throw new ValidationException("您輸入的下單數量不符合規範，請輸入正整數！");
        }
        result = pjp.proceed();
        return result;
    }
}
