package com.example.timemaster.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timemaster.R;

public class TimerFragment extends Fragment {

    private TextView timerText;
    private Button startButton, pauseButton, resetButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis = 60000; // 1 minute

    public TimerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timerText = view.findViewById(R.id.timerText);
        startButton = view.findViewById(R.id.startButton);
        pauseButton = view.findViewById(R.id.pauseButton);
        resetButton = view.findViewById(R.id.resetButton);

        updateTimerText();

        startButton.setOnClickListener(v -> startTimer());
        pauseButton.setOnClickListener(v -> pauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());
    }

    private void startTimer() {
        if (!isRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerText();
                }

                public void onFinish() {
                    isRunning = false;
                }
            }.start();
            isRunning = true;
        }
    }

    private void pauseTimer() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private void resetTimer() {
        timeLeftInMillis = 60000;
        updateTimerText();
        if (countDownTimer != null) countDownTimer.cancel();
        isRunning = false;
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
