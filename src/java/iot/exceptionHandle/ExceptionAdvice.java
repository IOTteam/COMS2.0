/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.exceptionHandle;

import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import iot.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hatanococoro
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理字符串轉換數字異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public Response NumberFormatExceptionHandle(NumberFormatException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理驗證異常
     * 
     * @param e
     * @param result
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Response BindExceptionHandle(BindException e,BindingResult result) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);

        List<FieldError> fieldErrors = result.getFieldErrors();
        //FieldError fieldError = fieldErrors.get(0);
        String message = "";
        for(FieldError fieldError:fieldErrors){
            message = message + fieldError.getDefaultMessage() + "<br>";
        }

        return new Response().failure(message,fieldErrors);


    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：主動拋出的驗證失敗異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response ValidationExceptionHandle(ValidationException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：將已存在的實體作為參數傳入實體，驗證失敗會拋出此異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response ConstraintViolationExceptionHandle(ConstraintViolationException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure("已存在的實體參數驗證未通過");
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理未查詢到實體
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResultException.class)
    public Response NoResultExceptionHandle(NoResultException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理未查詢到實體時手動拋出的異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NonexistentEntityException.class)
    public Response NonexistentEntityExceptionHandle(NonexistentEntityException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理樂觀鎖拋出異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OptimisticLockException.class)
    public Response OptimisticLockExceptionHandle(OptimisticLockException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure( e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理持久化異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PreexistingEntityException.class)
    public Response PreexistingEntityExceptionHandle(PreexistingEntityException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理拋出參數異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchFieldException.class)
    public Response NoSuchFieldExceptionHandle(NoSuchFieldException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：處理字符串轉換數字異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Response NullPointerExceptionHandle(NullPointerException e) {
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        return new Response().failure(e.getMessage());
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：捕獲所有異常
     * 
     * @param e
     * @return 
     ********************************************************************************/
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView ExceptionHandle(Exception e) {
        
        Logger.getLogger("Exception").log(Level.SEVERE, "", e);
        
        //e.printStackTrace();
        Map model = new HashMap();
        model.put("e", e.getClass());
        model.put("message", e.getMessage());
        model.put("cause", e.getCause());
        ModelAndView mv = new ModelAndView("500",model);
        return mv;
        //return new Response().failure(e.getMessage());
    }
}
