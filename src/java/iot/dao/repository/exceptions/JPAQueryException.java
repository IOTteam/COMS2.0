package iot.dao.repository.exceptions;

/*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：定義JPA查詢失敗拋出異常
     * 
     ********************************************************************************/

public class JPAQueryException extends Exception {
    public JPAQueryException(String message, Throwable cause) {
        super(message, cause);
    }
    public JPAQueryException(String message) {
        super(message);
    }
}
