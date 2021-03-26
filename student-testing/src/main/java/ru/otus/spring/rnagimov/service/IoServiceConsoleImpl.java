package ru.otus.spring.rnagimov.service;

import java.util.Scanner;

/**
 * Реализация сервиса ввода-вывода с помощью консоли
 */
public class IoServiceConsoleImpl implements IoService {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void printLn(String text) {
        System.out.println(text);
    }

    @Override
    public String readLn() {
        return scanner.nextLine();
    }
}
