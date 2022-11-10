package com.leo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/8 11:02
 */
@TableName("sys_dict")
@Data
@ToString
public class Dict {
    private String name;
    private String value;
    private String type;

}
