package com.usc.csci401.goatweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.usc.csci401"})
@EnableTransactionManagement
@EnableJpaRepositories("com.usc.csci401.goatdao")
@EntityScan("com.usc.csci401.goatdao.model")
public class GoatWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoatWebApplication.class, args);
	}

}
