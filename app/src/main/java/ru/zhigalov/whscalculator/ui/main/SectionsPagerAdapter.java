package ru.zhigalov.whscalculator.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import ru.zhigalov.whscalculator.ui.main.courses.CoursesNavHostFragment;
import ru.zhigalov.whscalculator.ui.main.scores.ScoresNavHostFragment;
public class SectionsPagerAdapter extends FragmentStateAdapter {

    public SectionsPagerAdapter(Lifecycle context, FragmentManager fm) {
        super(fm , context);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ScoresNavHostFragment();
        }
        if (position == 1) {
            return new CoursesNavHostFragment();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}