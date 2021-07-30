package ru.otus.spring.rnagimov.libraryorm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Реализация сервиса ввода-вывода
 */
@Service
public class IoServiceImpl implements IoService {

    private final Scanner scanner;
    private final PrintStream out;

    public IoServiceImpl(@Value("#{T(System).in}") InputStream in, @Value("#{T(System).out}") PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    @Override
    public void printLn(String text) {
        out.println(text);
    }

    @Override
    public String readLn() {
        return scanner.nextLine();
    }
}
