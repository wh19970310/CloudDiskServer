package com.suyunpan;

import com.suyunpan.socketservice.SocketService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.suyunpan.mapper")
@SpringBootApplication
public class SuyunpanApplication {
    public static void main(String[] args) {
        new SocketService().start();
        SpringApplication.run(SuyunpanApplication.class, args);
    }

}
