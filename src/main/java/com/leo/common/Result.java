package com.leo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/25 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code, String msg) {
        return new Result(code,msg,null);
    }
    public static Result error() {
        return new Result(Constants.CODE_500,"系统错误",null);
    }

}
