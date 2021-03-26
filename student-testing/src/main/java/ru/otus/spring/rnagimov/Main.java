package ru.otus.spring.rnagimov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.rnagimov.service.IoService;
import ru.otus.spring.rnagimov.service.TestingService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");

        IoService io = ctx.getBean(IoService.class);
        TestingService testingService = ctx.getBean(TestingService.class);

        testingService.examine();

        io.printLn("Thank you!");
        io.readLn();
    }
}
