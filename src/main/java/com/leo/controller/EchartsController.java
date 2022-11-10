package com.leo.controller;

import cn.hutool.core.collection.CollUtil;
import com.leo.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/4 10:54
 */

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon","Tue","Wed","Thu","Fri","Sat","Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));

        return Result.success(map);
    }
}
