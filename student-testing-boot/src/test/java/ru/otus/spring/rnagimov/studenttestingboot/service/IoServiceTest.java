package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;


@SpringBootTest
@DisplayName("Сервис ввода-вывода")
class IoServiceTest {

    @Autowired
    private MessageService messageService;

    private IoService ioService;
    private PrintStream printStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private InputStream inputStream;


    @BeforeEach
    protected void setup() {
        String input = "Test line\n9\n1\n2";
        inputStream = new ByteArrayInputStream(input.getBytes());

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
    void printLn() throws IOException {
        String testLine1 = "Pi 3.14159";
        String testLine2 = "Golden ratio 1.618";

        ioService.printLn(testLine1);
        ioService.printLn(testLine2);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())))) {
            Assertions.assertEquals(testLine1, br.readLine());
            Assertions.assertEquals(testLine2, br.readLine());
        }
    }

    @Test
    @DisplayName("Корректно считывает строки")
    void readLn() {
        String readLine = ioService.readLn();
        Assertions.assertEquals("Test line", readLine);
    }

    @Test
    @DisplayName("Коррекстно считывает целые числа")
    void readIntegerWithInterval() throws IOException {
        int fistRead = ioService.readIntegerWithInterval(1, 2);
        int secondRead = ioService.readIntegerWithInterval(1, 2);

        Assertions.assertEquals(1, fistRead);
        Assertions.assertEquals(2, secondRead);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())))) {
            Assertions.assertEquals(messageService.getMessage("messages.type.number"), br.readLine());
            Assertions.assertEquals(messageService.getMessage("messages.type.correct.number", 0, 3), br.readLine());
            Assertions.assertNull(br.readLine());
        }
    }
}