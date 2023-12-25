package com.istef.triviaquiz;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.istef.triviaquiz.data.QuestionPool;
import com.istef.triviaquiz.model.NextQuestion;
import com.istef.triviaquiz.model.Question;
import com.istef.triviaquiz.model.Score;

import java.util.List;
import java.util.function.Function;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtQuestion;
    private TextView txtCounter;
    private TextView txtScore;
    private TextView txtHighestScore;
    private Button btnTrue;
    private Button btnFalse;
    private CardView cardView;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
    private Score score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = new Score(this, 100);

        btnTrue = findViewById(R.id.buttonTrue);
        btnFalse = findViewById(R.id.buttonFalse);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtCounter = findViewById(R.id.txtCounter);
        cardView = findViewById(R.id.cardView);
        txtScore = findViewById(R.id.txtScore);
        txtHighestScore = findViewById(R.id.txtHighestScore);

        txtScore.setText("0");
        txtHighestScore.setText(String.valueOf(Score.getHighestScore()));

        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);


        new QuestionPool().getQuestions(questionList -> {
            this.questionList = questionList;
            nextQuestion();
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTrue:
                checkAnswer(true);
                break;
            case R.id.buttonFalse:
                checkAnswer(false);
        }
    }

    private void nextQuestion() {
        currentQuestionIndex = ++currentQuestionIndex % questionList.size();
        txtQuestion.setText(questionList.get(currentQuestionIndex).getText());
        String counterText = currentQuestionIndex + 1 + "/" + questionList.size();
        txtCounter.setText(counterText);
    }

    private void checkAnswer(boolean answer) {
        boolean correctAnswer = answer == questionList.get(currentQuestionIndex).isCorrect();

        score.setCurrentScore(correctAnswer);
        txtScore.setText(String.valueOf(score.getCurrentScore()));
        txtHighestScore.setText(String.valueOf(Score.getHighestScore()));

        @ColorInt int color = correctAnswer ? Color.GREEN : Color.RED;
        if (answer == questionList.get(currentQuestionIndex).isCorrect()) {
            fadeAnimation(cardView, Color.GREEN, this::nextQuestion);
        } else {
            shakeColorAnimation(cardView, Color.RED, this::nextQuestion);
        }
    }

    private void shakeColorAnimation(View view, @ColorInt int color, NextQuestion callback) {
        Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);

        view.setAnimation(shakeAnimation);

        shakeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setBackgroundColor(color);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setBackgroundColor(viewInitBackColor());
                callback.next();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            private @ColorInt int viewInitBackColor() {
                return view instanceof CardView
                        ? ((CardView) view).getCardBackgroundColor().getDefaultColor()
                        : ((ColorDrawable) view.getBackground()).getColor();
            }
        });

        view.requestLayout();
    }

    private void fadeAnimation(View view, @ColorInt int color, NextQuestion callback) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        view.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setBackgroundColor(color);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setBackgroundColor(viewInitBackColor());
                callback.next();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            private @ColorInt int viewInitBackColor() {
                return view instanceof CardView
                        ? ((CardView) view).getCardBackgroundColor().getDefaultColor()
                        : ((ColorDrawable) view.getBackground()).getColor();
            }
        });

        view.requestLayout();
    }
}