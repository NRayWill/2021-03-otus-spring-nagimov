package ru.otus.spring.rnagimov.domain;

import lombok.Getter;

public class TestResult {

    private final int scoreToPass;

    @Getter
    private int currentScore;

    public TestResult(int scoreToPass) {
        this.scoreToPass = scoreToPass;
        this.currentScore = 0;
    }

    public void increaseCurrentScore() {
        currentScore++;
    }

    public boolean testPassed() {
        return currentScore >= scoreToPass;
    }
}
