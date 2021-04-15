package ru.otus.spring.rnagimov.studenttestingboot.service;

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

    @Override
    public int readIntegerWithInterval(int leftBorder, int rightBorder) {
        int userOption = -1;
        boolean optionIsValid = false;
        while (!optionIsValid) {
            try {
                userOption = Integer.parseInt(readLn());
                if (userOption < leftBorder || userOption > rightBorder) {
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
