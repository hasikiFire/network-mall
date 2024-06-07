package com.hasikiFire.networkmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hasikiFire.networkmall.dao.mapper")
@SpringBootApplication
public class NetworkMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkMallApplication.class, args);
	}

}