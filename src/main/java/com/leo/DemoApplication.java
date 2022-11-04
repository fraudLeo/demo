package com.leo;

import com.leo.mapper.UserMapper;
import com.leo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.MultipartConfigElement;
import java.util.List;

@SpringBootApplication

public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  设置单个文件大小
        factory.setMaxFileSize(DataSize.parse("102400KB"));//KB 或者 MB 都可以 1MB=1024KB。1KB=1024B(字节)
        /// 设置总上传文件大小
        factory.setMaxRequestSize(DataSize.parse("102400KB"));//KB 或者 MB 都可以 1MB=1024KB。1KB=1024B(字节)
        return factory.createMultipartConfig();
    }




}
