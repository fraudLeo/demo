package com.leo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.common.Result;
import com.leo.pojo.Role;
import com.leo.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/5 18:19
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    //新增成员
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        roleService.save(role);
        return Result.success();
    }

    //删除单个用户
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        roleService.removeById(id);
        return Result.success();
    }

    //删除多个用户
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        roleService.removeByIds(ids);
        return Result.success();
    }

    //获取全列表
    @GetMapping("/findAll")
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //获取一个
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    //整页输出
    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
//        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page(pageNum,pageSize),queryWrapper));
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRole_Menu(@PathVariable Integer roleId) {
        return Result.success( roleService.getRole_Menu(roleId));
    }

    /**
     * /当前角色和菜单绑定关系.
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.saveOrUpdate(roleId, menuIds);
        return Result.success();
    }
}
