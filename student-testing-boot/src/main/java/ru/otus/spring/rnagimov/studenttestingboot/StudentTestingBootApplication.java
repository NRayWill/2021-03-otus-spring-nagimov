package ru.otus.spring.rnagimov.studenttestingboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.rnagimov.studenttestingboot.service.ExamineService;

@SpringBootApplication
@EnableConfigurationProperties
public class StudentTestingBootApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(StudentTestingBootApplication.class, args);
		ExamineService examineService = ctx.getBean(ExamineService.class);
		examineService.examine();
	}

}
