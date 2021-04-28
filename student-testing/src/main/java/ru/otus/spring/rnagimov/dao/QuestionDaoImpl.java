package ru.otus.spring.rnagimov.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.IncorrectQuestionFileException;
import ru.otus.spring.rnagimov.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    private final List<Question> allQuestionList = new ArrayList<>();

    public QuestionDaoImpl(@Value("${questions.filename}") String fileName) {
        this.fileName = fileName;
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
            throw new NoSuchQuestionFileException("No such question file!");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputResource))) {

            String csvLine;
            while ((csvLine = br.readLine()) != null) {
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