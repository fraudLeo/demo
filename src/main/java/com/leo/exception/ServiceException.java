package com.leo.exception;

import lombok.Getter;

/**
 * @Description:自定义异常
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/25 15:02
 */
@Getter
public class ServiceException extends RuntimeException {
    private String code;
    public ServiceException(String code, String msg) {
        super(msg);

    }
}
