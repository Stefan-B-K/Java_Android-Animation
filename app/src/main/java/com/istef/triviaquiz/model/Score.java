package com.istef.triviaquiz.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Score {
    private int currentScore = 0;
    private static int highestScore;
    private final int pointsPerQuestion;
    private final SharedPreferences sharedPreferences;

    public Score(Activity context, int pointsPerQuestion) {
        this.pointsPerQuestion = pointsPerQuestion;
        this.sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        highestScore = sharedPreferences.getInt("highest_score", 0);
    }


    public void setCurrentScore(boolean correctAnswer) {
        if (correctAnswer) {
            currentScore += pointsPerQuestion;
        }
        if (!correctAnswer && currentScore >= pointsPerQuestion) {
            currentScore -= pointsPerQuestion;
        }
        if (currentScore > highestScore) {
            highestScore = currentScore;
            sharedPreferences
                    .edit()
                    .putInt("highest_score", highestScore)
                    .apply();
        }
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public static int getHighestScore() {
        return highestScore;
    }
}
