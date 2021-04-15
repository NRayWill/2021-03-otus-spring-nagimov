package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Student;
import ru.otus.spring.rnagimov.studenttestingboot.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.repository.MessageRepository;

@Service
public class ExamineServiceImpl implements ExamineService {

    private final TestingService testingService;
    private final IoService io;
    private final MessageRepository messageRepository;

    public ExamineServiceImpl(TestingService testingService, IoService io, MessageRepository messageRepository) {
        this.testingService = testingService;
        this.io = io;
        this.messageRepository = messageRepository;
    }

    @Override
    public void examine() {
        Student student = askStudentData();
        try {
            TestResult testResult = testingService.runTest();
            String successOrFailWord = testResult.isTestPassed() ? messageRepository.getMessage("messages.result.success") : messageRepository.getMessage("messaget.restul.fail");
            String result = messageRepository.getMessage("messages.score.result",
                    new String[]{student.getFirstName(),
                            Integer.toString(testResult.getCurrentScore()),
                            Integer.toString(testResult.getAllQuestionCount()),
                            successOrFailWord});
            io.printLn(String.format("\n%s", result));
            io.readLn();
        } catch (TestingException ex) {
            io.printLn(String.format("ERROR: %s", ex.getMessage()));
        }
    }

    private Student askStudentData() {
        Student student = new Student();


        io.printLn(messageRepository.getMessage("messages.hello") + "\n" + messageRepository.getMessage("messages.firstname.asking"));
        student.setFirstName(io.readLn());
        io.printLn(messageRepository.getMessage("messages.lastname.asking"));
        student.setLastName(io.readLn());

        return student;
    }
}
