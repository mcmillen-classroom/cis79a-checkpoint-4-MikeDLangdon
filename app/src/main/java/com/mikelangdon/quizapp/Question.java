package com.mikelangdon.quizapp;

public class Question {
    private int mTextResId;
    private int mHintTextResId;

    public Question(int textResId, int hintTextResId) {
        mTextResId = textResId;
        mHintTextResId = hintTextResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public int getHintTextResId() {
        return mHintTextResId;
    }

    public void setHintTextResId(int hintTextResId) {
        mHintTextResId = hintTextResId;
    }

    // stub method - intentionally does nothing
    // only applies to true false question
    // and app related question
    public boolean checkAnswer(boolean boolResponse)
    {
        return false;
    }

    // stub method
    // only applies to fill the blank question
    public boolean checkAnswer(String userAnswer)
    {
        return false;
    }

    // stub method
    // only applies to multiple choice question
    public boolean checkAnswer(int userAnswer) {
        return false;
    }

    // stub method
    // only applies to app related question
    public boolean isAppRelatedQuestion() {
        return false;
    }

    // stub
    public boolean isTrueFalseQuestion() {
        return false;
    }

    // stub
    public boolean isFillTheBlankQuestion() {
        return false;
    }

    // stub
    public boolean isMultipleChoiceQuestion() {
        return false;
    }
}
