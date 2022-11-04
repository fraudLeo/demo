package com.leo.exception;

import com.leo.common.Result;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.bind.annotation.ControllerAdvice;
/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/25 14:57
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se) {
        return Result.error(se.getCode(),se.getMessage());
    }
}
