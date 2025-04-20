package com.example.timemaster;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.timemaster.fragments.StopwatchFragment;
import com.example.timemaster.fragments.TimerFragment;
import com.example.timemaster.fragments.CountdownFragment;
import com.example.timemaster.fragments.AlarmFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new StopwatchFragment();
            case 1: return new TimerFragment();
            case 2: return new CountdownFragment();
            case 3: return new AlarmFragment();
            default: return new StopwatchFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
