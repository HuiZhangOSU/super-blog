package com.bamzhy13.superblog.blogbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bamzhy13.superblog.blogbackend.mapper")
public class BlogBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogBackendApplication.class, args);
	}

}
