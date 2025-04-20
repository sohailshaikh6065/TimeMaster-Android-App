package com.example.timemaster.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timemaster.R;

public class CountdownFragment extends Fragment {

    private EditText inputMinutes;
    private TextView countdownDisplay;
    private Button startCountdown, pauseCountdown, resetCountdown;

    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis;
    private long initialTimeInMillis;

    public CountdownFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_countdown, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputMinutes = view.findViewById(R.id.inputMinutes);
        countdownDisplay = view.findViewById(R.id.countdownDisplay);
        startCountdown = view.findViewById(R.id.startCountdown);
        pauseCountdown = view.findViewById(R.id.pauseCountdown);
        resetCountdown = view.findViewById(R.id.resetCountdown);

        startCountdown.setOnClickListener(v -> {
            if (!isRunning) {
                if (timeLeftInMillis == 0) {
                    String input = inputMinutes.getText().toString();
                    if (TextUtils.isEmpty(input)) return;
                    initialTimeInMillis = Long.parseLong(input) * 60 * 1000;
                    timeLeftInMillis = initialTimeInMillis;
                }
                startTimer();
            }
        });

        pauseCountdown.setOnClickListener(v -> pauseTimer());
        resetCountdown.setOnClickListener(v -> resetTimer());
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            public void onFinish() {
                isRunning = false;
            }
        }.start();
        isRunning = true;
    }

    private void pauseTimer() {
        if (isRunning && countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timeLeftInMillis = initialTimeInMillis;
        updateCountdownText();
        isRunning = false;
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        countdownDisplay.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
