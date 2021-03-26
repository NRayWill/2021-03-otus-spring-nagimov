package ru.otus.spring.rnagimov.service;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Реализация сервиса ввода-вывода с помощью консоли
 */
public class IoServiceFileInputImpl implements IoService {

    private Scanner scanner;

    public IoServiceFileInputImpl(String fileName) {
        updateFileScanner(fileName);
    }

    public void updateFileScanner(String fileName) {
        this.scanner = new Scanner(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));
    }

    @Override
    public void printLn(String text) {
        System.out.println(text);
    }

    @Override
    public String readLn() {
        String input = scanner.nextLine();
        printLn(input);
        return input;
    }
}
