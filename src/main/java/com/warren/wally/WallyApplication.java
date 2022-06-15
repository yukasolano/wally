package com.warren.wally;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.warren.wally.*")
@ComponentScan(basePackages = {"com.warren.wally.*"})
@EntityScan("com.warren.wally.*")
public class WallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallyApplication.class, args);
    }
}
