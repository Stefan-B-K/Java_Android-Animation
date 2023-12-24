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
import android.widget.ImageButton;
import android.widget.TextView;

import com.istef.triviaquiz.data.QuestionPool;
import com.istef.triviaquiz.model.Question;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtQuestion;
    private TextView txtCounter;
    private Button btnTrue;
    private Button btnFalse;
    private ImageButton btnPrev;
    private ImageButton btnNext;
    private CardView cardView;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrev = findViewById(R.id.buttonPrev);
        btnNext = findViewById(R.id.buttonNext);
        btnTrue = findViewById(R.id.buttonTrue);
        btnFalse = findViewById(R.id.buttonFalse);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtCounter = findViewById(R.id.txtCounter);
        cardView = findViewById(R.id.cardView);

        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);


        new QuestionPool().getQuestions(questionList -> {
            this.questionList = questionList;
            updateQuestionAndCounter(0);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPrev:
                updateQuestionAndCounter(-1);
                break;
            case R.id.buttonNext:
                updateQuestionAndCounter(1);
                break;
            case R.id.buttonTrue:
                checkAnswer(true);
                break;
            case R.id.buttonFalse:
                checkAnswer(false);
        }
    }

    private void updateQuestionAndCounter(int relativeIndex) {
        if (relativeIndex == -1 && currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }
        if (relativeIndex == 1) {
            currentQuestionIndex = ++currentQuestionIndex % questionList.size();
        }
        txtQuestion.setText(questionList.get(currentQuestionIndex).getText());
        String counterText = currentQuestionIndex + 1 + "/" + questionList.size();
        txtCounter.setText(counterText);
    }

    private void checkAnswer(boolean answer) {
        @ColorInt int color = answer == questionList.get(currentQuestionIndex).isCorrect()
                ? Color.GREEN : Color.RED;
        if (answer == questionList.get(currentQuestionIndex).isCorrect()) {
            fadeAnimation(cardView, Color.GREEN);
        } else {
            shakeColorAnimation(cardView, Color.RED);
        }

    }

    private void shakeColorAnimation(View view, @ColorInt int color) {
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

    private void fadeAnimation(View view, @ColorInt int color) {
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