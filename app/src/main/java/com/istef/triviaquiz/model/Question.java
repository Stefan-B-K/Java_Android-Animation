package com.istef.triviaquiz.model;

public class Question {
    private String text;
    private boolean correct;

    public Question() {
    }

    public Question(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", correct=" + correct +
                '}';
    }
}
