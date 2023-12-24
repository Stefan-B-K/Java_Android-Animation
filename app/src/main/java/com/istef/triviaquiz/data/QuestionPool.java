package com.istef.triviaquiz.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.istef.triviaquiz.controller.AppController;
import com.istef.triviaquiz.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class QuestionPool {

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json";
    private List<Question> questions = new ArrayList<>();

    public void getQuestions(final QuestionsAsyncResponse callback) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        Question q = new Question();
                        try {
                            q.setText(response.getJSONArray(i).getString(0));
                            q.setCorrect(response.getJSONArray(i).getBoolean(1));
                            questions.add(q);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    questions = questions.stream()
                            .sorted((a, b) -> (int) (Math.random() * 100) - 50)
                            .collect(Collectors.toList());
                    if (callback != null) callback.processFinished(questions);
                },
                error -> Log.e("JSON error: ", error.getMessage() )
        );
        AppController.getInstance().addToRequestQueue(request);
    }

}
