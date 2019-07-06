package com.mikelangdon.quizapp;

public class MultipleChoiceQuestion extends Question {

    // holds resource ID for the string array that has all the strings in it
    private int mOptionsResId;
    // index into the array of correct answers
    private int mAnswer;

    public MultipleChoiceQuestion(int textResId, int hintTextResId, int optionsResId, int indexOfAnswer) {
        super(textResId, hintTextResId);
        mOptionsResId = optionsResId;
        mAnswer = indexOfAnswer;
    }

    public int getOptionsResId() {
        return mOptionsResId;
    }

    @Override
    public boolean checkAnswer(int ans) {
        return mAnswer == ans;


    }

    @Override
    public boolean isMultipleChoiceQuestion() {
        return true;
    }
}
