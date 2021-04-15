package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.rnagimov.Main;

import java.io.*;


@ContextConfiguration(classes = Main.class)
class IoServiceTest {

    @Autowired
    private IoService ioService;
    private PrintStream printStream;
    private InputStream inputStream;

    private final static String OUT_FILE_NAME = "testOutFileName.txt";
    private final static String IN_FILE_NAME = "testInFileName.txt";

    @BeforeEach
    protected void setup() throws IOException {
        printStream = new PrintStream(OUT_FILE_NAME);

        File inFile = new File(IN_FILE_NAME);
        if (inFile.createNewFile()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(IN_FILE_NAME))) {
                bw.write("Test line\n9\n1\n2");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = new FileInputStream(inFile);
        }
        ioService = new IoServiceImpl(inputStream, printStream);
    }

    @AfterEach
    protected void tearDown() throws IOException {
        printStream.close();
        inputStream.close();
        if (!(new File(OUT_FILE_NAME)).delete() || !(new File(IN_FILE_NAME)).delete()) {
            System.out.println("Test files weren't deleted");
        }
    }

    @Test
    void printLn() throws IOException {
        String testLine1 = "Pi 3.14159";
        String testLine2 = "Golden ratio 1.618";

        ioService.printLn(testLine1);
        ioService.printLn(testLine2);

        try (BufferedReader br = new BufferedReader(new FileReader(OUT_FILE_NAME))) {
            Assertions.assertEquals(testLine1, br.readLine());
            Assertions.assertEquals(testLine2, br.readLine());
        }
    }

    @Test
    void readLn() {
        String readLine = ioService.readLn();
        Assertions.assertEquals("Test line", readLine);
    }

    @Test
    void readIntegerWithInterval() throws IOException {
        int fistRead = ioService.readIntegerWithInterval(1, 2);
        int secondRead = ioService.readIntegerWithInterval(1, 2);

        Assertions.assertEquals(1, fistRead);
        Assertions.assertEquals(2, secondRead);
        try (BufferedReader br = new BufferedReader(new FileReader(OUT_FILE_NAME))) {
            Assertions.assertEquals("Type a number of option please", br.readLine());
            Assertions.assertEquals("You answer must be greater than 0 and less than 3", br.readLine());
            Assertions.assertNull(br.readLine());
        }
    }
}