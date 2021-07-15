package com.example.mathquizprojectdraft.model;

import java.io.Serializable;

public class Score implements Serializable, Comparable {
    private String question;
    private String answer;
    private boolean isAnswerCorrect;

    public Score(String question, String answer, boolean isAnswerCorrect) {
        this.question = question;
        this.answer = answer;
        this.isAnswerCorrect = isAnswerCorrect;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setAnswerCorrect(boolean answerCorrect) {
        isAnswerCorrect = answerCorrect;
    }

    @Override
    public String toString() {
        String rightWrong;
        if (isAnswerCorrect) {
            rightWrong = "right";
        } else {
            rightWrong = "wrong";
        }

        return question + ", " + answer + ", " + rightWrong;
    }


    @Override
    public int compareTo(Object o) {
        if ((isAnswerCorrect() && ((Score) o).isAnswerCorrect) || (!isAnswerCorrect() && !((Score) o).isAnswerCorrect)) {
            return 0;
        } else if ((!isAnswerCorrect() && ((Score) o).isAnswerCorrect)) {
            return -1;
        } else {
            return 1;
        }
    }

}
