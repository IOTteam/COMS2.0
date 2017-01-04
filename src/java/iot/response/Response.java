/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.response;

/**
 *
 * @author hatanococoro
 */
public class Response<T> {

    private Boolean success;
    private Boolean isEmpty; 
    private String message;
    private T data;
    private int count = 0;
    
    public Response success() {
        this.success = true;
        this.message = "成功";
        return this;
    }
    
    public Response success(String message) {
        this.success = true;
        this.isEmpty = true;
        this.message = message;
        return this;
    }
    
    public Response Empty(String message) {
        this.success = true;
        this.isEmpty = true;
        this.message = message;
        return this;
    }
 
    public Response success(String message, T data) {
        this.success = true;
        this.isEmpty = false;
        this.message = message;
        this.data = data;
        return this;
    }
    
    public Response success(String message, T data, int count) {
        this.success = true;
        this.isEmpty = false;
        this.message = message;
        this.data = data;
        this.count = count;
        return this;
    }
    
    public Response failure() {
        this.success = false;
        this.isEmpty = true;
        this.message = "發生異常";
        return this;
    }
 
    public Response failure(String message) {
        this.success = false;
        this.isEmpty = true;
        this.message = message;
        return this;
    }
    
    public Response failure(String message,T data) {
        this.success = false;
        this.isEmpty = false;
        this.message = message;
        this.data = data;
        return this;
    }
 

    
    public boolean isSuccess() {
          return success;
      }
    
    public boolean isEmpty() {
          return isEmpty;
      }
 
    public String getMessage() {
          return message;
      }
    
    public T getData() {
          return data;
      }
    
    public void setData(T data) {
          this.data = data;
      }
    
    public int getCount() {
          return count;
      }
 
    
}
