package com.matan.themtpchaser;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qCount, timer;
    private Button option1, option2, option3, option4;
    private List < Question > questionList = new ArrayList < > ();;
    private int quesNum;
    private int score;
    private CountDownTimer countDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.Question);
        qCount = findViewById(R.id.Question_num);
        timer = findViewById(R.id.countDown);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestionsList();
        score = 0;
    }
    private void getQuestionsList() {
        int f;
        questionList.add(new Question("Question 1", "A", "B", "C", "D", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 2", "B", "A", "C", "D", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 3", "C", "B", "A", "D", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 4", "A", "D", "C", "B", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 5", "C", "A", "D", "B", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 6", "A", "C", "D", "B", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 7", "A", "D", "C", "B", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 8", "D", "B", "C", "A", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 9", "B", "C", "A", "D", f = (int) ((Math.random()*(4-1)) + 1)));
        questionList.add(new Question("Question 10", "D", "A", "B", "C", f = (int) ((Math.random()*(4-1)) + 1)));

        setQuestion();
    }
    public void setQuestion() {
        timer.setText(String.valueOf(10));
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionList.size()));

        startTimer();

        quesNum = 0;
    }
    private void startTimer() {
        countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000)
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();
    }
    @Override
    public void onClick(View v) {
        int selectedOption = 0;
        switch (v.getId()) {
            case R.id.option1:
                selectedOption = 1;
                break;
            case R.id.option2:
                selectedOption = 2;
                break;
            case R.id.option3:
                selectedOption = 3;
                break;
            case R.id.option4:
                selectedOption = 4;
                break;
            default:

        }
        if (countDown != null) {
            countDown.cancel();
        }
        checkAnswer(selectedOption, v);
    }
    private void checkAnswer(int selectedOption, View view) {
        if (selectedOption == questionList.get(quesNum).getCorrectAns()) {
            //right answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score ++;
        } else {
            //wrong answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(quesNum).getCorrectAns()) {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000);

    }
    private void changeQuestion() {
        if (quesNum < questionList.size() - 1) {
            quesNum++;
            PlayAnim(question, 0, 0);
            PlayAnim(option1, 0, 1);
            PlayAnim(option2, 0, 2);
            PlayAnim(option3, 0, 3);
            PlayAnim(option4, 0, 4);
            qCount.setText(String.valueOf(quesNum + 1) + "/" + String.valueOf(questionList.size()));
            timer.setText(String.valueOf(10));
            startTimer();
        } else {
            //Go to score Activity
            Intent intent = new Intent(QuizActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
          //  QuizActivity.this.finish();
        }
    }
    private void PlayAnim(View view, final int value, int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value == 0) {
                            switch (viewNum) {
                                case 0:
                                    ((TextView) view).setText(questionList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionD());
                                    break;

                            }

                            if (viewNum != 0) {
                                ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("blue")));
                            }


                            PlayAnim(view, 1, viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDown.cancel();
    }
}