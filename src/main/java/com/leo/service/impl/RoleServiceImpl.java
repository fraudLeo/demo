package com.leo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo.mapper.RoleMapper;
import com.leo.mapper.Role_Menu_Mapper;
import com.leo.pojo.Role;
import com.leo.pojo.Role_Menu;
import com.leo.service.MenuService;
import com.leo.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/5 16:17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private Role_Menu_Mapper role_menu_mapper;
    @Resource
    private MenuService menuService;

    //采用先删除后添加
    @Transactional
    @Override
    public void saveOrUpdate(Integer roleId, List<Integer> menuIds) {

        /**
         * 先删除所有的绑定关系.
         */
        //方法一
        /*
        QueryWrapper<Role_Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        role_menu_mapper.deleteById(queryWrapper);
         */
        //方法二 直接使用sql语句
        role_menu_mapper.deleteByRoleId(roleId);
        /**
         * 再把前端传过来的菜单id数组绑定到当前这个角色的id上面
         */
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Role_Menu role_menu = new Role_Menu();
            role_menu.setRoleId(roleId);
            role_menu.setMenuId(menuId);
            System.out.println(role_menu);
            role_menu_mapper.insert(role_menu);
        }
    }

    @Override
    public List<Integer> getRole_Menu(Integer roleId) {
        return role_menu_mapper.selectByRoleId(roleId);
    }
}
