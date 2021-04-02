package ru.otus.spring.rnagimov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Вариант ответа
 * Содержит текст и флаг правильности ответа
 */
@AllArgsConstructor
@Getter
public class AnswerOption {
    String answerText;
    boolean isCorrect;
}
