package com.example.comp_4200project;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabasExecutor {

    private static final Executor executor = Executors.newSingleThreadExecutor();

    public static void insertAthleteDataAsync(
            int studentID, String studentName, String firstName, String lastName, String birthdate,
            String faculty, double height, double weight, String team, int jerseyNumber,
            OnCompleteListener onCompleteListener) {
        executor.execute(() -> {
            MySql.insertAthleteData(studentID, studentName, firstName, lastName, birthdate,
                    faculty, height, weight, team, jerseyNumber);
            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }
        });
    }

    public interface OnCompleteListener {
        void onComplete();
    }
}