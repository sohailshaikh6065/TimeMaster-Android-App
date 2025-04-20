package com.example.timemaster.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timemaster.R;
import com.example.timemaster.utils.AlarmReceiver;

import java.util.Calendar;

public class AlarmFragment extends Fragment {

    private TimePicker timePicker;
    private Button setAlarmButton, cancelAlarmButton, playAlarmButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public AlarmFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timePicker = view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        setAlarmButton = view.findViewById(R.id.setAlarmButton);
        cancelAlarmButton = view.findViewById(R.id.cancelAlarmButton);
        playAlarmButton = view.findViewById(R.id.playAlarmButton); // new button

        alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        setAlarmButton.setOnClickListener(v -> setAlarm());
        cancelAlarmButton.setOnClickListener(v -> cancelAlarm());

        playAlarmButton.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm_sound);
            mediaPlayer.start();
        });
    }

    private void setAlarm() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1); // Set for next day
        }

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
