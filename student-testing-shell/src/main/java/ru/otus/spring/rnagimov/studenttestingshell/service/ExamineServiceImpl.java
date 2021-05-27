package ru.otus.spring.rnagimov.studenttestingshell.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingshell.domain.Student;
import ru.otus.spring.rnagimov.studenttestingshell.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

import java.io.IOException;

@Service
public class ExamineServiceImpl implements ExamineService {

    private final TestingService testingService;
    private final IoService io;
    private final LocalizedIoService localizedIoService;

    public ExamineServiceImpl(TestingService testingService, IoService io, LocalizedIoService localizedIoService) {
        this.testingService = testingService;
        this.io = io;
        this.localizedIoService = localizedIoService;
    }

    @Override
    public void examine() {
        Student student = askStudentData();
        try {
            TestResult testResult = testingService.runTest();
            String successOrFailWord = testResult.isTestPassed() ? localizedIoService.getMessage("messages.result.success") : localizedIoService.getMessage("messages.result.fail");
            io.printLn("");
            localizedIoService.printLocalizedMessage("messages.score.result",
                    student.getFirstName(),
                    Integer.toString(testResult.getCurrentScore()),
                    Integer.toString(testResult.getAllQuestionCount()),
                    successOrFailWord);
        } catch (TestingException | IOException ex) {
            io.printLn(String.format("ERROR: %s", ex.getMessage()));
        }
    }

    private Student askStudentData() {
        Student student = new Student();

        localizedIoService.printLocalizedMessage("messages.hello");
        localizedIoService.printLocalizedMessage("messages.firstname.asking");
        student.setFirstName(io.readLn());
        localizedIoService.printLocalizedMessage("messages.lastname.asking");
        student.setLastName(io.readLn());

        return student;
    }
}
