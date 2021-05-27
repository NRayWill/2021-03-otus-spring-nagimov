package ru.otus.spring.rnagimov.studenttestingshell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.rnagimov.studenttestingshell.service.ExamineService;

@SpringBootApplication
@EnableConfigurationProperties
public class StudentTestingShellApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentTestingShellApplication.class, args);
	}

}
