package com.matan.themtpchaser;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qCount, timer;
    private Button option1, option2, option3, option4;
    private KonfettiView konfettiView;
    private final List<Question> QUESTIONS = new ArrayList<>();
    private final List<Question> questionList = new ArrayList<>();
    private int quesNum;
    private int score;
    int nQuestions = 10;
    private CountDownTimer countDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //הפעולה הראשית בה יופעלו הפעולות שייפורטו בהמשך
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.Question);
        qCount = findViewById(R.id.Question_num);
        timer = findViewById(R.id.countDown);
        konfettiView = findViewById(R.id.konfettiView);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        try {
            getQuestionsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        score = 0;
        Intent music = new Intent(QuizActivity.this, BackgroundMusicService.class);
        stopService(music);
        MediaPlayer.create(this, R.raw.chaserbackgroundmusic).start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //בעת סיום מוזיקת צייס'ר האפליקציה תחכה 10 שניות ואז תתחיל את המוזיקת הקודמת מחדש
                Intent music = new Intent(QuizActivity.this, BackgroundMusicService.class);
                startService(music);
            }
        }, 10000);

    }

    private void getQuestionsList() throws IOException {
        //פעולה בה נקרא את השאלות מקובץ scv לתוך List על מנת ליצור שאלון מקובץ שאלות גדול בידע כללי באופן רנדומלי.
        InputStream inputStream = getResources().openRawResource(R.raw.transformed_questions);
        Reader reader = new InputStreamReader(inputStream);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('#')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();

        csvReader.readNext(); // to skip the header

        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null)
            QUESTIONS.add(new Question(nextLine[0], nextLine[2], nextLine[3], nextLine[4], nextLine[5], (int) Double.parseDouble(nextLine[1])));

        csvReader.close();
        reader.close();

        Collections.shuffle(QUESTIONS);
        for (int i = 0; i < nQuestions; ++i)
            questionList.add(QUESTIONS.get(i));

        setQuestion();
    }

    public void setQuestion() {
        //הכנסת השאלות לList
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
        //התחלת טיימר 20 שניות
        countDown = new CountDownTimer(20000, 1000) {
            @Override
            //
            public void onTick(long millisUntilFinished) {
                //הצגת השניות היורדות בטיימר
                if (millisUntilFinished < 20000)
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
            //בסיום טיימר החלף שאלה
        };
        countDown.start();
    }

    @Override
    public void onClick(View v) {
        //מציאת הID של התשובה שנבחרה
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
        //בדיקת האם התשובה שנבחרה נכונה,אם כן היא תיצבע בירוק(ספור +1 לתשובות נכונות) + הצגת קונפטי. אם לא היא תיצבע באדום והתשובה הנכונה בירוק.
        if (selectedOption == questionList.get(quesNum).getCorrectAns()) {
            //right answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
            konfettiView.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                    .addSizes(new Size(12, 5f))
                    .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 1_000L);
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
                //לאחר הצגת התשובה האפליקציה תמתין 2 שניות ואז תחליף שאלה.
                changeQuestion();
            }
        }, 2000);

    }

    private void changeQuestion() {
        //האפליקציה תבדוק האם הגענו לשאלה ה10 אם לא האפליקציה תחליף שאלה(הפעל אנימציה של החלפה),תספור שעברנו שאלה ותתחיל את הטיימר מחדש. אם כן האפליקציה תעבור למסך הניקוד.
        if (quesNum < questionList.size() - 1) {
            quesNum++;
            PlayAnim(question, 0, 0);
            PlayAnim(option1, 0, 1);
            PlayAnim(option2, 0, 2);
            PlayAnim(option3, 0, 3);
            PlayAnim(option4, 0, 4);
            qCount.setText((quesNum + 1) + "/" + questionList.size());
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
        //הפעלת אנימציה של מעבר שאלה
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //התחל אנימציה

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //בסיום האנימציה הכנס את השאלה הבאה
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
                        //בטל אנימציה

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        //חזור על האנימציה
                    }
                });
    }

    @Override
    public void onBackPressed() {
        //בלחיצה אחורה התחל שאלון חדש
        super.onBackPressed();

        countDown.cancel();
    }
}