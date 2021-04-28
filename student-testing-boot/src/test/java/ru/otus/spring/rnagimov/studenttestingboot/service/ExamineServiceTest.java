package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.rnagimov.studenttestingboot.facade.LocalizedMessageFacade;
import ru.otus.spring.rnagimov.studenttestingboot.facade.LocalizedMessageFacadeImpl;
import ru.otus.spring.rnagimov.studenttestingboot.repository.QuestionRepositoryImpl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@SpringBootTest
@DisplayName("Сервис запуска тестирования")
class ExamineServiceTest {

    private IoService ioService;

    private ExamineService examineService;

    @Autowired
    private MessageService messageService;

    @BeforeEach
    protected void setup() {
        ioService = mock(IoServiceImpl.class);
        LocalizedMessageFacade localizedMessageFacade = new LocalizedMessageFacadeImpl(ioService, messageService);
        TestingService testingService = new TestingServiceImpl(
                ioService,
                new QuestionRepositoryImpl(messageService),
                false,
                4,
                localizedMessageFacade);
        examineService = new ExamineServiceImpl(testingService, ioService, messageService, localizedMessageFacade);
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