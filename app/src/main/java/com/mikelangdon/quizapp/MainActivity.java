package com.mikelangdon.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private TextView mFinalTextView;
    private TextView mScoreTextView;
    private TextView mQuestionTextView;

    private LinearLayout mTrueFalseContainer;
    private LinearLayout mFillTheBlankContainer;
    private LinearLayout mMultipleChoiceContainer;
    private LinearLayout mFinalPageContainer;
    private LinearLayout mNextAndPreviousButtonContainer;


    private EditText mEditText;

    private Button mCheckButton;
    private Button mCheckButtonCorrect;
    private Button mCheckButtonIncorrect;

    private Button mTrueButton;
    private Button mFalseButton;

    private Button mAButton;
    private Button mAButtonCorrect;
    private Button mAButtonIncorrect;

    private Button mBButton;
    private Button mBButtonCorrect;
    private Button mBButtonIncorrect;

    private Button mCButton;
    private Button mCButtonCorrect;
    private Button mCButtonIncorrect;

    private Button mQuitButton;

    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private ImageButton mHintButton;


    private Question[] mQuestions;


    private int mIndex;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire 'em up
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        mCheckButton = (Button) findViewById(R.id.check_button);
        mCheckButtonCorrect = (Button) findViewById(R.id.check_button_correct);
        mCheckButtonIncorrect = (Button) findViewById(R.id.check_button_incorrect);

        mAButton = (Button) findViewById(R.id.a_button);
        mAButtonCorrect = (Button) findViewById(R.id.a_button_correct);
        mAButtonIncorrect = (Button) findViewById(R.id.a_button_incorrect);

        mBButton = (Button) findViewById(R.id.b_button);
        mBButtonCorrect = (Button) findViewById(R.id.b_button_correct);
        mBButtonIncorrect = (Button) findViewById(R.id.b_button_incorrect);

        mCButton = (Button) findViewById(R.id.c_button);
        mCButtonCorrect = (Button) findViewById(R.id.c_button_correct);
        mCButtonIncorrect = (Button) findViewById(R.id.c_button_incorrect);

        mQuitButton = (Button) findViewById(R.id.quit_button);

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mHintButton = (ImageButton) findViewById(R.id.hint_button);

        mTrueFalseContainer = (LinearLayout) findViewById(R.id.true_false_container);
        mFillTheBlankContainer = (LinearLayout) findViewById(R.id.fill_the_blank_container);
        mMultipleChoiceContainer = (LinearLayout) findViewById(R.id.multiple_choice_container);
        mFinalPageContainer = (LinearLayout) findViewById(R.id.final_page_container);
        mNextAndPreviousButtonContainer = (LinearLayout) findViewById(R.id.next_and_previous_container);

        mEditText = (EditText) findViewById(R.id.edit_text);

        // mTextView is redundant, handled by mQuestionTextView
        //mTextView = (TextView) findViewById(R.id.question_text_view);
        mFinalTextView = (TextView) findViewById(R.id.final_question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mHintButton.setOnClickListener(this);

        mCheckButton.setOnClickListener(this);
        mCheckButtonCorrect.setOnClickListener(this);
        mCheckButtonIncorrect.setOnClickListener(this);

        mAButton.setOnClickListener(this);
        mAButtonCorrect.setOnClickListener(this);
        mAButtonIncorrect.setOnClickListener(this);

        mBButton.setOnClickListener(this);
        mBButtonCorrect.setOnClickListener(this);
        mBButtonIncorrect.setOnClickListener(this);

        mCButton.setOnClickListener(this);
        mCButtonCorrect.setOnClickListener(this);
        mCButtonIncorrect.setOnClickListener(this);

        mQuitButton.setOnClickListener(this);


        // Initialize an array of questions
        mQuestions = new Question[8];  // change this number if adding more questions
        mIndex = 0;

        mQuestions[0] = new TrueFalseQuestion(R.string.question_1, R.string.question_1_hint, false);
        mQuestions[1] = new TrueFalseQuestion(R.string.question_2, R.string.question_2_hint, true);
        mQuestions[2] = new TrueFalseQuestion(R.string.question_3, R.string.question_3_hint, false);
        mQuestions[3] = new TrueFalseQuestion(R.string.question_4, R.string.question_4_hint, true);
        mQuestions[4] = new TrueFalseQuestion(R.string.question_5, R.string.question_5_hint, false);

        // NTS: even though question_6_answers is in strings.xml, it is referenced by R.array, not R.string
        String[] q6Answers = getResources().getStringArray(R.array.question_6_answers);
        mQuestions[5] = new FillTheBlankQuestion(R.string.question_6, R.string.question_6_hint, q6Answers);


        mQuestions[6] = new MultipleChoiceQuestion(R.string.question_7, R.string.question_7_hint, R.array.question_7_answers, 2);

        // The final page, where I ask if the user wants to quit (no other option given)
        mQuestions[7] = new AppRelatedQuestion(R.string.question_last, R.string.question_last_hint);


        mScoreTextView.setText("Score: " + mScore);
        // Set up the first question
        setupQuestion();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.true_button) {
            checkAnswer(true);
        } else if (v.getId() == R.id.false_button) {
            checkAnswer(false);
        } else if (v.getId() == R.id.check_button || v.getId() == R.id.check_button_correct || v.getId() == R.id.check_button_incorrect) {
            checkAnswer(mEditText.getText().toString());
        } else if (v.getId() == R.id.quit_button) {
            Toast myToast = Toast.makeText(this, "K, Bye!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();
            System.exit(0);
        } else if (v.getId() == R.id.a_button) {
            checkAnswer(0);
        } else if (v.getId() == R.id.b_button) {
            checkAnswer(1);
        } else if (v.getId() == R.id.c_button) {
            checkAnswer(2);
        } else if (v.getId() == R.id.next_button) {

            // Increment the index by 1
            mIndex++;
            if (mIndex == mQuestions.length - 1) {


                mScoreTextView.setText("Score: " + mScore);
                setupQuestion();
            }


            // Change text in view.
            mScoreTextView.setText("Score: " + mScore);
            setupQuestion();
        } else if (v.getId() == R.id.previous_button) {
            // Decrement the index by 1
            mIndex--;
            // The following 'if statement' prevents the user from trying to
            // select a previous question when there are no more previous questions
            // to select.
            if (mIndex < 0) {
                mIndex = 0;
            }

            // Change text in view.
            mScoreTextView.setText("Score: " + mScore);
            setupQuestion();

        } else if (v.getId() == R.id.hint_button) {
            Toast myToast = Toast.makeText(this, mQuestions[mIndex].getHintTextResId(), Toast.LENGTH_LONG);
            myToast.setGravity(Gravity.BOTTOM, 0, 10);
            myToast.show();

        }

    }

    public void setupQuestion() {

        if (mQuestions[mIndex].isAppRelatedQuestion()) {
            mFinalTextView.setText(mQuestions[mIndex].getTextResId());
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
            mTrueFalseContainer.setVisibility(View.GONE);
            mFinalPageContainer.setVisibility(View.VISIBLE);
            mNextAndPreviousButtonContainer.setVisibility(View.GONE);
            mQuestionTextView.setVisibility(View.GONE);


        } else {
// just changed this from mTextView to mQuestionTextView fri 7.5
            mQuestionTextView.setText(mQuestions[mIndex].getTextResId());

            if (mQuestions[mIndex].isTrueFalseQuestion()) {
                mTrueFalseContainer.setVisibility(View.VISIBLE);
                mFillTheBlankContainer.setVisibility(View.GONE);
                mMultipleChoiceContainer.setVisibility(View.GONE);
                mFinalPageContainer.setVisibility(View.GONE);

            } else if (mQuestions[mIndex].isFillTheBlankQuestion()) {
                mTrueFalseContainer.setVisibility(View.GONE);
                mFillTheBlankContainer.setVisibility(View.VISIBLE);
                mMultipleChoiceContainer.setVisibility(View.GONE);
                mFinalPageContainer.setVisibility(View.GONE);

                mCheckButton.setVisibility(View.VISIBLE);
                mCheckButtonCorrect.setVisibility(View.GONE);
                mCheckButtonIncorrect.setVisibility(View.GONE);

            } else if (mQuestions[mIndex].isMultipleChoiceQuestion()) {
                mTrueFalseContainer.setVisibility(View.GONE);
                mFillTheBlankContainer.setVisibility(View.GONE);
                mMultipleChoiceContainer.setVisibility(View.VISIBLE);
                mFinalPageContainer.setVisibility(View.GONE);

                mAButton.setVisibility(View.VISIBLE);
                mAButtonCorrect.setVisibility(View.GONE);
                mAButtonIncorrect.setVisibility(View.GONE);

                mBButton.setVisibility(View.VISIBLE);
                mBButtonCorrect.setVisibility(View.GONE);
                mBButtonIncorrect.setVisibility(View.GONE);

                mCButton.setVisibility(View.VISIBLE);
                mCButtonCorrect.setVisibility(View.GONE);
                mCButtonIncorrect.setVisibility(View.GONE);


                // NTS: the line below is a little complex, but we are just
                // casting the generic question to a MC question
                int optionsResId = ((MultipleChoiceQuestion) mQuestions[mIndex]).getOptionsResId();
                String[] options = getResources().getStringArray(optionsResId);

                mAButton.setText(options[0]);
                mAButtonCorrect.setText(options[0]);
                mAButtonIncorrect.setText(options[0]);

                mBButton.setText(options[1]);
                mBButtonCorrect.setText(options[1]);
                mBButtonIncorrect.setText(options[1]);

                mCButton.setText(options[2]);
                mCButtonCorrect.setText(options[2]);
                mCButtonCorrect.setText(options[2]);
            }
        }
    }

    // True False Question
    public boolean checkAnswer(boolean userInput) {
        // check if answer matches the user's answer
        if (mQuestions[mIndex].checkAnswer(userInput)) {

            mScore++;
            mScoreTextView.setText("Score: " + mScore);
            Toast myToast = Toast.makeText(this, "You are correct!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();

            return true;
        } else {
            mScore--;
            mScoreTextView.setText("Score: " + mScore);
            Toast myToast = Toast.makeText(this, "You are incorrect :(", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();

            return false;
        }
    }

    // overloading checkAnswer for MC
    public boolean checkAnswer(int userInput) {
        // check if answer matches the user's answer
        if (mQuestions[mIndex].checkAnswer(userInput)) {
            // 'A' button pressed and correct
            if (userInput == 0) {
                mAButtonCorrect.setVisibility(View.VISIBLE);
                mAButton.setVisibility(View.GONE);
                mAButtonIncorrect.setVisibility(View.GONE);

                mBButton.setVisibility(View.VISIBLE);
                mBButtonCorrect.setVisibility(View.GONE);
                mBButtonIncorrect.setVisibility(View.GONE);

                mCButton.setVisibility(View.VISIBLE);
                mCButtonCorrect.setVisibility(View.GONE);
                mCButtonIncorrect.setVisibility(View.GONE);
            }
            // 'B' button pressed and correct

            else if (userInput == 1) {
                mBButtonCorrect.setVisibility(View.VISIBLE);
                mBButton.setVisibility(View.GONE);
                mBButtonIncorrect.setVisibility(View.GONE);

                mAButton.setVisibility(View.VISIBLE);
                mAButtonCorrect.setVisibility(View.GONE);
                mAButtonIncorrect.setVisibility(View.GONE);

                mCButton.setVisibility(View.VISIBLE);
                mCButtonCorrect.setVisibility(View.GONE);
                mCButtonIncorrect.setVisibility(View.GONE);
            }
            // 'C' button pressed and correct
            else if (userInput == 2) {
                mCButtonCorrect.setVisibility(View.VISIBLE);
                mCButton.setVisibility(View.GONE);
                mCButtonIncorrect.setVisibility(View.GONE);

                mBButton.setVisibility(View.VISIBLE);
                mBButtonCorrect.setVisibility(View.GONE);
                mBButtonIncorrect.setVisibility(View.GONE);

                mAButton.setVisibility(View.VISIBLE);
                mAButtonCorrect.setVisibility(View.GONE);
                mAButtonIncorrect.setVisibility(View.GONE);
            }


            mScore++;
            mScoreTextView.setText("Score: " + mScore);
            Toast myToast = Toast.makeText(this, "You are correct!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();

            return true;
        } else {
            if (userInput == 0) {
                mAButtonIncorrect.setVisibility(View.VISIBLE);
                mAButton.setVisibility(View.GONE);
                mAButtonCorrect.setVisibility(View.GONE);

                mBButton.setVisibility(View.VISIBLE);
                mBButtonCorrect.setVisibility(View.GONE);
                mBButtonIncorrect.setVisibility(View.GONE);

                mCButton.setVisibility(View.VISIBLE);
                mCButtonCorrect.setVisibility(View.GONE);
                mCButtonIncorrect.setVisibility(View.GONE);
            }
            // 'B' button pressed and correct

            else if (userInput == 1) {
                mBButtonIncorrect.setVisibility(View.VISIBLE);
                mBButton.setVisibility(View.GONE);
                mBButtonCorrect.setVisibility(View.GONE);

                mCButton.setVisibility(View.VISIBLE);
                mCButtonCorrect.setVisibility(View.GONE);
                mCButtonIncorrect.setVisibility(View.GONE);

                mAButton.setVisibility(View.VISIBLE);
                mAButtonCorrect.setVisibility(View.GONE);
                mAButtonIncorrect.setVisibility(View.GONE);
            }
            // 'C' button pressed and correct
            else if (userInput == 2) {
                mCButtonIncorrect.setVisibility(View.VISIBLE);
                mCButton.setVisibility(View.GONE);
                mCButtonCorrect.setVisibility(View.GONE);

                mBButton.setVisibility(View.VISIBLE);
                mBButtonCorrect.setVisibility(View.GONE);
                mBButtonIncorrect.setVisibility(View.GONE);

                mAButton.setVisibility(View.VISIBLE);
                mAButtonCorrect.setVisibility(View.GONE);
                mAButtonIncorrect.setVisibility(View.GONE);
            }


            mScore--;
            mScoreTextView.setText("Score: " + mScore);
            Toast myToast = Toast.makeText(this, "You are incorrect :(", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();

            return false;
        }
    }

    // overloading checkAnswer for FillTheBlank.  Maybe we can use generics...
    public boolean checkAnswer(String userInput) {
        // check if answer matches the user's answer
        if (mQuestions[mIndex].checkAnswer(userInput)) {

            mCheckButton.setVisibility(View.GONE);
            mCheckButtonCorrect.setVisibility(View.VISIBLE);
            mCheckButtonIncorrect.setVisibility(View.GONE);

            mScore++;
            mScoreTextView.setText("Score: " + mScore);
            Toast myToast = Toast.makeText(this, "You are correct!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();

            return true;
        } else {

            mCheckButton.setVisibility(View.GONE);
            mCheckButtonIncorrect.setVisibility(View.VISIBLE);
            mCheckButtonCorrect.setVisibility(View.GONE);

            mScore--;
            mScoreTextView.setText("Score: " + mScore);
            Toast myToast = Toast.makeText(this, "You are incorrect :(", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 10);
            myToast.show();

            return false;
        }
    }
}
