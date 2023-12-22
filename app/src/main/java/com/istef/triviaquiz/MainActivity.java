package com.istef.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
                break;
            case R.id.buttonFalse:
                break;
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
}