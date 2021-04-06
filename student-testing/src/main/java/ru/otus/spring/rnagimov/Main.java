package ru.otus.spring.rnagimov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.rnagimov.service.ExamineService;

@Configuration
@ComponentScan
@PropertySource("classpath:exam.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        ExamineService examineService = ctx.getBean(ExamineService.class);
        examineService.examine();
    }
}
