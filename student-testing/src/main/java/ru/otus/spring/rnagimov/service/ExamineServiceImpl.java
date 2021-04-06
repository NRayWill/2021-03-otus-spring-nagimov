package ru.otus.spring.rnagimov.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.domain.Student;
import ru.otus.spring.rnagimov.domain.TestResult;
import ru.otus.spring.rnagimov.exception.TestingException;

@Service
public class ExamineServiceImpl implements ExamineService {

    private final TestingService testingService;
    private final IoService io;
    private final QuestionDao questionDao;

    public ExamineServiceImpl(TestingService testingService, IoService io, QuestionDao questionDao) {
        this.testingService = testingService;
        this.io = io;
        this.questionDao = questionDao;
    }

    @Override
    public void examine() {
        Student student = new Student();

        io.printLn("Hello!\nWhat is your first name?");
        student.setFirstName(io.readLn());
        io.printLn("What is your last name?");
        student.setLastName(io.readLn());

        try {
            TestResult testResult = testingService.runTest();
            String result = String.format("%s, your score: %s/%s. The exam has%s been passed.",
                    student.getFirstName(),
                    testResult.getCurrentScore(),
                    questionDao.getAllQuestions().size(),
                    testResult.testPassed() ? StringUtils.EMPTY : "n't");
            io.printLn(String.format("\n%s", result));
            io.readLn();
        } catch (TestingException ex) {
            io.printLn(String.format("ERROR: %s", ex.getMessage()));
        }
    }
}
