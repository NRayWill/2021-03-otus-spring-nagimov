package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Student;
import ru.otus.spring.rnagimov.studenttestingboot.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.facade.LocalizedMessageFacade;

import java.io.IOException;

@Service
public class ExamineServiceImpl implements ExamineService {

    private final TestingService testingService;
    private final IoService io;
    private final MessageService messageService;
    private final LocalizedMessageFacade localizedMessageFacade;

    public ExamineServiceImpl(TestingService testingService, IoService io, MessageService messageService, LocalizedMessageFacade localizedMessageFacade) {
        this.testingService = testingService;
        this.io = io;
        this.messageService = messageService;
        this.localizedMessageFacade = localizedMessageFacade;
    }

    @Override
    public void examine() {
        Student student = askStudentData();
        try {
            TestResult testResult = testingService.runTest();
            String successOrFailWord = testResult.isTestPassed() ? messageService.getMessage("messages.result.success") : messageService.getMessage("messages.result.fail");
            io.printLn("");
            localizedMessageFacade.printLocalizedMessageFromBundle("messages.score.result",
                    student.getFirstName(),
                    Integer.toString(testResult.getCurrentScore()),
                    Integer.toString(testResult.getAllQuestionCount()),
                    successOrFailWord);
            io.readLn();
        } catch (TestingException | IOException ex) {
            io.printLn(String.format("ERROR: %s", ex.getMessage()));
        }
    }

    private Student askStudentData() {
        Student student = new Student();

        localizedMessageFacade.printLocalizedMessageFromBundle("messages.hello");
        localizedMessageFacade.printLocalizedMessageFromBundle("messages.firstname.asking");
        student.setFirstName(io.readLn());
        localizedMessageFacade.printLocalizedMessageFromBundle("messages.lastname.asking");
        student.setLastName(io.readLn());

        return student;
    }
}
