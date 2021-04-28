package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.otus.spring.rnagimov.dao.QuestionDaoImpl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@DisplayName("Сервис запуска тестирования")
class ExamineServiceTest {

    private IoService ioService;

    private ExamineService examineService;

    @BeforeEach
    protected void setup() {
        ioService = mock(IoServiceImpl.class);
        TestingService testingService = new TestingServiceImpl(
                ioService,
                new QuestionDaoImpl("questions.csv"),
                false,
                4);
        examineService = new ExamineServiceImpl(testingService, ioService);
    }

    @Test
    @DisplayName("При правильных ответах должно выводиться соответствующее сообщение")
    void examineSuccess() {
        doReturn(1).when(ioService).readIntegerWithInterval(anyInt(), anyInt());
        examineService.examine();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, atLeastOnce()).printLn(captor.capture());
        String resultString = captor.getValue();
        Assertions.assertTrue(resultString.endsWith(", your score: 6/6. The exam has been passed."));

    }

    @Test
    @DisplayName("При неправильных ответах должно выводиться соответствующее сообщение")
    void examineFailed() {
        doReturn(2).when(ioService).readIntegerWithInterval(anyInt(), anyInt());
        examineService.examine();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, atLeastOnce()).printLn(captor.capture());
        String resultString = captor.getValue();
        Assertions.assertTrue(resultString.endsWith(", your score: 0/6. The exam hasn't been passed."));
    }
}