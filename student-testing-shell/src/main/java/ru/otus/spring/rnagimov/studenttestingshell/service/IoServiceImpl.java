package ru.otus.spring.rnagimov.studenttestingshell.service;

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
    private final MessageService messageService;

    public IoServiceImpl(@Value("#{T(System).in}") InputStream in, @Value("#{T(System).out}") PrintStream out, MessageService messageService) {
        scanner = new Scanner(in);
        this.out = out;
        this.messageService = messageService;
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
                    printLn(messageService.getMessage("messages.type.correct.number", leftBorder - 1, rightBorder + 1));
                } else {
                    optionIsValid = true;
                }
            } catch (NumberFormatException nfe) {
                printLn(messageService.getMessage("messages.type.number"));
            }
        }
        return userOption;
    }
}
