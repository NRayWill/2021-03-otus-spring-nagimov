package ru.otus.spring.rnagimov.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Класс вопроса.
 * Содержит номер вопроса, текст и варианты овтета
 */
@NoArgsConstructor
@Getter
@Setter
public class Question {
    int questionNumber;
    String questionText;
    List<AnswerOption> answerOptionList;
}
