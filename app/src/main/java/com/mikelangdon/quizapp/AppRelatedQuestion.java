package com.mikelangdon.quizapp;

public class AppRelatedQuestion extends Question {
    private boolean mAnswer;

    public AppRelatedQuestion(int textResId, int hintTextResId) {
        super(textResId, hintTextResId);
    }

    @Override
    public boolean isAppRelatedQuestion() {
        return true;
    }


}
