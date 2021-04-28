package ru.otus.spring.rnagimov.studenttestingboot.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.studenttestingboot.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.exception.IncorrectQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingboot.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.service.MessageService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final String fileName;
    private final MessageService messageService;

    private final List<Question> allQuestionList = new ArrayList<>();

    public QuestionRepositoryImpl(MessageService messageService) {
        this.messageService = messageService;
        this.fileName = messageService.getMessage("messages.question.file");
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
            throw new NoSuchQuestionFileException(messageService.getMessage("messages.no.such.question.file"));
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputResource))) {

            String csvLine;
            while ((csvLine = br.readLine()) != null) {
                String[] cellArray = csvLine.split(";");

                if (cellArray.length < 4) {
                    throw new IncorrectQuestionFileException(messageService.getMessage("messages.incorrect.question.line"));
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