package com.yash.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages= {"com.yash.es.*"})
public class SpringWithElasticSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithElasticSearchApplication.class, args);
	}

}

