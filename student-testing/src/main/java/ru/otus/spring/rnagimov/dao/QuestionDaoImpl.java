package ru.otus.spring.rnagimov.dao;

import lombok.AllArgsConstructor;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.service.IoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private String fileName;
    private IoService io;

    final private List<Question> allQuestionList = new ArrayList<>();

    @Override
    public List<Question> getAllQuestions() {
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
    private void fillQuestionList(String fileName) {
        try {
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));

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
            io.printLn("Error during loading test: " + e.getMessage());
        }
    }
}