package com.leo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.common.Result;
import com.leo.mapper.FileMapper;
import com.leo.pojo.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/1 15:57
 */


@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private FileMapper fileMapper;



    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
//        File uploadParentFile = new File(fileUploadPath);
//        if (!uploadParentFile.exists()) {
//            uploadParentFile.mkdirs();
//        }
//        String uuid = ;
        String fileUUid = IdUtil.fastSimpleUUID()+ StrUtil.DOT + type ;

        File uploadFile = new File(fileUploadPath + fileUUid);

//        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String md5 = null;
        String url = null;
            //获取文件的md5
            md5 = SecureUtil.md5(file.getInputStream());
//        String md5 = SecureUtil.md5(uploadFile);
            //判断是否存在相同的记录
            Files dbFiles = getFileMd5(md5);

            if (dbFiles!=null) {
                url = dbFiles.getUrl();
            } else {
                file.transferTo(uploadFile);
                url = "http://localhost:9090/file/" + fileUUid;
            }

        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);

//        System.out.println(md5);
         fileMapper.insert(saveFile);
        return url;
    }

    //新增或者更新
    @PostMapping("/update")
    public Result save(@RequestBody Files file) {
        return Result.success(fileMapper.updateById(file));
    }


    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        //设置输出流的格式
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    private Files getFileMd5(String md5) {
        QueryWrapper<Files> qw = new QueryWrapper<>();
        qw.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(qw);
        return filesList.size() == 0 ? null : filesList.get(0);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIs_delete(true);
        fileMapper.updateById(files);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> qw = new QueryWrapper<>();
        //查询未删除的记录
        qw.eq("is_delete",false);

        qw.orderByDesc("id");
        if (!"".equals(name)) {
            qw.like("name",name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum,pageSize), qw));
    }
}
