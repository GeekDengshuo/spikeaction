package com.dengshuo.spikeaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dengshuo.spikeaction.mapper")
public class SpikeActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpikeActionApplication.class, args);
    }

}
