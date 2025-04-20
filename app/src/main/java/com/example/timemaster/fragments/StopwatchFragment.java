package com.example.timemaster.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timemaster.R;

public class StopwatchFragment extends Fragment {

    private TextView timerDisplay;
    private Button startButton, pauseButton, resetButton;

    private Handler handler = new Handler();
    private int seconds = 0;
    private boolean isRunning = false;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int hrs = seconds / 3600;
            int mins = (seconds % 3600) / 60;
            int secs = seconds % 60;

            String time = String.format("%02d:%02d:%02d", hrs, mins, secs);
            timerDisplay.setText(time);

            if (isRunning) {
                seconds++;
            }

            handler.postDelayed(this, 1000);
        }
    };

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timerDisplay = view.findViewById(R.id.timerDisplay);
        startButton = view.findViewById(R.id.startButton);
        pauseButton = view.findViewById(R.id.pauseButton);
        resetButton = view.findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> {
            isRunning = true;
            handler.post(runnable);
        });

        pauseButton.setOnClickListener(v -> isRunning = false);

        resetButton.setOnClickListener(v -> {
            isRunning = false;
            seconds = 0;
            timerDisplay.setText("00:00:00");
        });
    }
}
