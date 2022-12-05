package ru.ds.education.testspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration
@EnableScheduling
@EnableAsync
public class TestspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestspringbootApplication.class, args);
	}

}
