package ru.otus.spring.rnagimov.domain;

import lombok.Getter;

public class TestResult {

    private final int scoreToPass;

    @Getter
    private int currentScore;

    @Getter
    private final int allQuestionCount;

    public TestResult(int scoreToPass, int allQuestionCount) {
        this.scoreToPass = scoreToPass;
        this.currentScore = 0;
        this.allQuestionCount = allQuestionCount;
    }

    public void increaseCurrentScore() {
        currentScore++;
    }

    public boolean isTestPassed() {
        return currentScore >= scoreToPass;
    }
}
