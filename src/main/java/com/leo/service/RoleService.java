package com.leo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo.pojo.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/5 16:16
 */

public interface RoleService extends IService<Role> {
    void saveOrUpdate(Integer roleId, List<Integer> menuIds);

    List<Integer> getRole_Menu(Integer roleId);
}
