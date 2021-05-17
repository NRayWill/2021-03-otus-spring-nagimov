package ru.otus.spring.rnagimov.studenttestingboot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.studenttestingboot.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.exception.IncorrectQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingboot.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final String fileName;
    private final List<Question> allQuestionList = new ArrayList<>();

    public QuestionRepositoryImpl(@Value("${question.file}") String fileName, @Value("${locale}") String locale) {
        String localizedFileName = fileName + "_" + locale + ".csv";
        URL u = this.getClass().getResource("/" + localizedFileName);
        if (u == null) {
            localizedFileName = fileName + ".csv";
        }
        this.fileName = localizedFileName;
    }

    @Override
    public List<Question> getAllQuestions() throws TestingException, IOException {
        if (allQuestionList.isEmpty()) {
            fillQuestionList(fileName);
        }
        return allQuestionList;
    }

    /**
     * Метод заполняет список всех вопросов {@link #allQuestionList}
     *
     * @param fileName Имя файла ресурсов с вопросами
     */
    private void fillQuestionList(String fileName) throws TestingException, IOException {
        BufferedInputStream inputResource = (BufferedInputStream) this.getClass().getResourceAsStream("/" + fileName);
        if (inputResource == null) {
            throw new NoSuchQuestionFileException("No such question file");
        }
        try (Scanner scanner = new Scanner(inputResource)) {

            String csvLine;
            while (scanner.hasNextLine()) {
                csvLine = scanner.nextLine();
                String[] cellArray = csvLine.split(";");

                if (cellArray.length < 4) {
                    throw new IncorrectQuestionFileException("The question-line must contain a number, a question and at least two answer options");
                }

                Question question = new Question();
                List<AnswerOption> answerOptionList = new ArrayList<>();
                question.setQuestionNumber(Integer.parseInt(cellArray[0]));
                question.setQuestionText(cellArray[1]);
                question.setAnswerOptionList(answerOptionList);

                for (int i = 2; i < cellArray.length; i++) {
                    answerOptionList.add(new AnswerOption(cellArray[i], i == 2));
                }
                allQuestionList.add(question);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TestingException(e);
        }
        inputResource.close();
    }
}