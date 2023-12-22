package com.istef.triviaquiz.data;

import com.istef.triviaquiz.model.Question;

import java.util.List;

public interface QuestionsAsyncResponse {
    void processFinished(List<Question> questionList);
}
