package com.leo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/6 14:46
 */
@TableName("sys_menu")
@Data
@ToString
public class Menu {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String path;
    private String icon;
    private String description;
    private Integer pid;
    @TableField(exist = false)
    private List<Menu> children;
}
