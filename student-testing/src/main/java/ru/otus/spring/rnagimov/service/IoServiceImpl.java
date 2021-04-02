package ru.otus.spring.rnagimov.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Реализация сервиса ввода-вывода с помощью консоли
 */
@Service
public class IoServiceImpl implements IoService {

    private final Scanner scanner;
    private final PrintStream out;

    public IoServiceImpl() {
        scanner = new Scanner(System.in);
        out = System.out;
    }

    @Override
    public void printLn(String text) {
        out.println(text);
    }

    @Override
    public String readLn() {
        return scanner.nextLine();
    }

    @Override
    public int readIntegerWithInterval(int leftBorder, int rightBorder) {
        int userOption = -1;
        boolean optionIsValid = false;
        while (!optionIsValid) {
            try {
                userOption = Integer.parseInt(readLn());
                if (userOption < 1 || userOption > rightBorder) {
                    printLn(String.format("You answer must be greater than 0 and less than %s", rightBorder + 1));
                } else {
                    optionIsValid = true;
                }
            } catch (NumberFormatException nfe) {
                printLn("Type a number of option please");
            }
        }
        return userOption;
    }
}
