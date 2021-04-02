package ru.otus.spring.rnagimov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.rnagimov.service.TestingService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestingService testingService = ctx.getBean(TestingService.class);
        testingService.examineWithOutput();
    }
}
