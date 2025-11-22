package com.tencent.wxcloudrun.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author jessehu
 * @version 1.0
 * @date 2023/3/11 4:12 下午
 **/
public class RestHttpResponses {

    public static ResponseEntity createSuccess(Object data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
    public static ResponseEntity updateSuccess(Object data){return ResponseEntity.status(HttpStatus.OK).body(data);}
    public static ResponseEntity deleteSuccess(Object data){return ResponseEntity.status(HttpStatus.OK).body(data);}
    public static ResponseEntity getSuccess(Object data){return ResponseEntity.status(HttpStatus.OK).body(data);}

    public static ResponseEntity getInternalError(Object data){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);}

    public static ResponseEntity buildResponseEntityByHttpStatus(HttpStatus status, Object data){return ResponseEntity.status(status).body(data);}

    public static ResponseEntity buildResponseEntity(int status, Object data){return ResponseEntity.status(status).body(data);}


    public static ResponseEntity successResponseEntity(Object data){return ResponseEntity.status(HttpStatus.OK).body(data);}
}
