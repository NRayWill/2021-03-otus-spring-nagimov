package ru.otus.spring.rnagimov.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.TestingIoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:exam.properties")
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    private final List<Question> allQuestionList = new ArrayList<>();

    public QuestionDaoImpl(@Value("${questions.filename}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAllQuestions() throws TestingIoException {
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
    private void fillQuestionList(String fileName) throws TestingIoException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)))) {

            String csvLine;
            while ((csvLine = br.readLine()) != null) {
                String[] employee = csvLine.split(";");

                Question question = new Question();
                List<AnswerOption> answerOptionList = new ArrayList<>();
                question.setQuestionNumber(Integer.parseInt(employee[0]));
                question.setQuestionText(employee[1]);
                question.setAnswerOptionList(answerOptionList);

                for (int i = 2; i < employee.length; i++) {
                    answerOptionList.add(new AnswerOption(employee[i], i == 2));
                }
                allQuestionList.add(question);
            }
        } catch (IOException e) {
            throw new TestingIoException(e);
        }
    }
}