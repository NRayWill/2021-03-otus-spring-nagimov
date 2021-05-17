package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Scanner;


@SpringBootTest
@DisplayName("Сервис ввода-вывода")
class IoServiceTest {

    @Autowired
    private MessageService messageService;

    private IoService ioService;
    private PrintStream printStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private InputStream inputStream;

    private final static String INPUT_STRING_1 = "Test line";
    private final static int INPUT_NUMBER_2 = 9;
    private final static int INPUT_NUMBER_3 = 1;
    private final static int INPUT_NUMBER_4 = 2;
    private final static String COMMON_INPUT_STRING = INPUT_STRING_1 + "\n" + INPUT_NUMBER_2 + "\n" + INPUT_NUMBER_3 + "\n" + INPUT_NUMBER_4;

    @BeforeEach
    protected void setup() {
        inputStream = new ByteArrayInputStream(COMMON_INPUT_STRING.getBytes());

        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);

        ioService = new IoServiceImpl(inputStream, printStream, messageService);
    }

    @AfterEach
    protected void tearDown() throws IOException {
        printStream.close();
        byteArrayOutputStream.close();
        inputStream.close();
    }

    @Test
    @DisplayName("Корректно выводит строки")
    void printLn() {
        String testLine1 = "Pi 3.14159";
        String testLine2 = "Golden ratio 1.618";

        ioService.printLn(testLine1);
        ioService.printLn(testLine2);

        try (Scanner scanner = new Scanner(byteArrayOutputStream.toString())) {
            Assertions.assertEquals(testLine1, scanner.nextLine());
            Assertions.assertEquals(testLine2, scanner.nextLine());
        }
    }

    @Test
    @DisplayName("Корректно считывает строки")
    void readLn() {
        String readLine = ioService.readLn();
        Assertions.assertEquals(INPUT_STRING_1, readLine);
    }

    @Test
    @DisplayName("Коррекстно считывает целые числа")
    void readIntegerWithInterval() {
        int fistRead = ioService.readIntegerWithInterval(1, 2);
        int secondRead = ioService.readIntegerWithInterval(1, 2);

        Assertions.assertEquals(INPUT_NUMBER_3, fistRead);
        Assertions.assertEquals(INPUT_NUMBER_4, secondRead);
        try (Scanner scanner = new Scanner(byteArrayOutputStream.toString())) {
            Assertions.assertEquals(messageService.getMessage("messages.type.number"), scanner.nextLine());
            Assertions.assertEquals(messageService.getMessage("messages.type.correct.number", 0, 3), scanner.nextLine());
            Assertions.assertFalse(scanner.hasNextLine());
        }
    }
}