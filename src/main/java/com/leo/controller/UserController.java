package com.leo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.common.Constants;
import com.leo.common.Result;
import com.leo.controller.dto.UserDto;
import com.leo.exception.ServiceException;
import com.leo.mapper.UserMapper;
import com.leo.pojo.User;
import com.leo.service.UserService;
import com.leo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/18 19:57
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public List<User> index() {
        return userService.list();
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDTO) {
//这里检测出null了
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
//        System.out.println(username);
//        System.out.println(password);
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }

        UserDto dto = userService.login(userDTO);
//        System.out.println(userDTO);
        return Result.success(dto);


    }

    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @PostMapping("/register")
    public Result register (@RequestBody UserDto userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }


        if (userService.register(userDTO)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"已有账号");
        }

    }
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam String name,
//                                        @RequestParam String email,
//                                        @RequestParam String address) {
//        pageNum = (pageNum -1)*pageSize;
//        List<User> data = userMapper.selectPage(pageNum,pageSize,name,email,address);
//        Integer total = userMapper.selectTotal(name,email,address);
//        Map<String, Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//    }

    /**
     * 分页查询 MP方式
     * @param pageNum
     * @param pageSize
     * @param name
     * @param email
     * @param address
     * @return
     */
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam("pageNum") Integer pageNum,
                      @RequestParam("pageSize") Integer pageSize,
                      @RequestParam(defaultValue = "") String name,
                      @RequestParam(defaultValue = "") String email,
                      @RequestParam(defaultValue = "") String address) {
        Page<User> page = new Page<User>(pageNum,pageSize);
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("name",name);
        qw.like("email",email);
        qw.like("address",address);
        qw.orderByDesc("id");

        //获取当前用户信息
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("获取当前用户信息=========================="+currentUser.getNickname());
        return userService.page(page,qw);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        List<User> list = userService.list();
        //通过工具类创建writer 写出到磁盘路径
        //ExcelWriter writer = ExcelUtil.getWriter(filesUpLoadPath + "用户信息.xlsx")
        //在内存中操作,同时写入导浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("name", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
//        writer.addHeaderAlias("createTime", "创建时间");
        // 一次写出list内的对象到excel, 使用默认样式,强制输出标题
        writer.write(list, true);

        //设置浏览器相应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * 导入接口
     *
     */
    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        /**
         * 方法一:
         */
//        reader.addHeaderAlias("Excel中的中文字段","数据库中的英文字段")//这是
        reader.addHeaderAlias("用户名","name");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("昵称","nickname");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("地址","address");
        List<User> user = reader.readAll(User.class);
        boolean b = userService.saveBatch(user);

        /**
         * 方法二
         */
//        List<User> user = reader.readAll(User.class);//会读到表头


        /**
         * 方法三
         */
//        倘若不想读到表头可以用下面的方式,忽略表头方式
/*        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : reader.read(1)) {
            User user1 = new User();
            user1.setName(row.get(0).toString());
            user1.setPassword(row.get(1).toString());
            user1.setNickname(row.get(2).toString());
            user1.setEmail(row.get(3).toString());
            user1.setAddress(row.get(4).toString());
            users.add(user1);
        }*/

        System.out.println(user);
        return b;
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name",username);
        System.out.println(1);
        return Result.success(userService.getOne(qw));
    }
}
