package com.yash.es.Appmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import guru.springframework.SpringBootWebApplication;

@SpringBootTest(classes = SpringBootWebApplication.class)
@RunWith(SpringRunner.class)
@ComponentScan(basePackages= {"com.yash.es.*"})
public class SpringWithElasticSearchApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithElasticSearchApplicationTest.class, args);
	}

}

