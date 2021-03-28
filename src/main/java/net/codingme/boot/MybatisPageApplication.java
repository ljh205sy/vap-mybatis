package net.codingme.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wh1107066
 */
@tk.mybatis.spring.annotation.MapperScan(basePackages = "net.codingme.boot.dao.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class MybatisPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPageApplication.class, args);
    }
}
