package com.paramedic.mobshaman.models;

/**
 * Created by maxo on 21/7/16.
 */
public class TriageQuestion {

    private String Question;
    private String Answer;

    public TriageQuestion(String question, String answer) {
        this.Question = question;
        this.Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
