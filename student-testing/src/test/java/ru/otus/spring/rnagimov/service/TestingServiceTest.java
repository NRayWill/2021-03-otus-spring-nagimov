package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.exception.TestingIoException;

import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(locations = {"/spring-test-context.xml"})
public class TestingServiceTest {

    @Autowired
    private QuestionDao questionDao;

    @Mock
    private IoService ioService;

    private TestingService testingService;

    @BeforeAll
    protected void setup() {
        ioService = mock(IoServiceImpl.class);
        testingService = new TestingServiceImpl(ioService, questionDao, false);
    }

    @Test
    public void testExamineRight() throws TestingIoException {
        when(ioService.readIntegerWithInterval(anyInt(), anyInt())).thenReturn(1);
        Assertions.assertEquals(6, testingService.examine());
    }

    @Test
    public void testExamine() throws TestingIoException {
        when(ioService.readIntegerWithInterval(anyInt(), anyInt())).thenReturn(1, 1, 1, 2, 2, 2);
        Assertions.assertEquals(3, testingService.examine());
    }

    @Test
    public void testExamineWrong() throws TestingIoException {
        when(ioService.readIntegerWithInterval(anyInt(), anyInt())).thenReturn(2);
        Assertions.assertEquals(0, testingService.examine());
    }
}
