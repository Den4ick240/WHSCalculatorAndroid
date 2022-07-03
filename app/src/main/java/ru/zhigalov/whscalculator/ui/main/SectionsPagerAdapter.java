package ru.zhigalov.whscalculator.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.zhigalov.whscalculator.R;
import ru.zhigalov.whscalculator.ui.main.courses.CoursesNavHostFragment;
import ru.zhigalov.whscalculator.ui.main.scores.ScoresNavHostFragment;
import ru.zhigalov.whscalculator.ui.main.scores.scorelist.ScoreListFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};

    public SectionsPagerAdapter(Lifecycle context, FragmentManager fm) {
        super(fm , context);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
//            return ScoreListFragment.newInstance();
            return new ScoresNavHostFragment();
        }
        if (position == 1) {
            return new CoursesNavHostFragment();
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}