package com.leo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.common.Constants;
import com.leo.common.Result;
import com.leo.mapper.DictMapper;
import com.leo.pojo.Dict;
import com.leo.pojo.Menu;
import com.leo.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/6 14:56
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @Resource
    private DictMapper dictMapper;


    //查询单个数据
    @PostMapping("/findOne/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    //查询整页
    @GetMapping("/findAll")
    public Result findAll(@RequestParam String name) {

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
////        new QueryWrapper<>();//可以使用查询条件
        List<Menu> list = menuService.list(queryWrapper);
        //找出一级菜单(pid为null)
        /**
         * 下面的部分着重理解Lambda表达式
         */
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentNode) {
            //筛选所有数据中的pid
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        //建立父子级关系

        return Result.success(parentNode);
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu) {
//        System.out.println("**************");
//        System.out.println(menu);
//        System.out.println("**************");
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    //删除单个
    @DeleteMapping("/del/{id}")
    public Result delById(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    //删除多个
    @PostMapping("/del/batch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        return Result.success(menuService.removeBatchByIds(ids));
    }

    //整页显示
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }


    @GetMapping("/{icons}")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }
}