package ru.otus.spring.rnagimov.studenttestingshell.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.studenttestingshell.config.AppProperties;
import ru.otus.spring.rnagimov.studenttestingshell.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingshell.domain.Question;
import ru.otus.spring.rnagimov.studenttestingshell.exception.IncorrectQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingshell.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final AppProperties appProperties;
    private final List<Question> allQuestionList = new ArrayList<>();
    private final String locale;

    public QuestionRepositoryImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
        locale = appProperties.getLocale();
    }

    @Override
    public List<Question> getAllQuestions() throws TestingException, IOException {
        if (!Objects.equals(locale, appProperties.getLocale())) { // Если локаль была изменена
            allQuestionList.clear();
        }
        if (allQuestionList.isEmpty()) {
            fillQuestionList(appProperties.getLocalizedFileName());
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