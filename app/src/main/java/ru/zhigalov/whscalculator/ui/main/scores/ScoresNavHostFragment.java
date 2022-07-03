package ru.zhigalov.whscalculator.ui.main.scores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.zhigalov.whscalculator.databinding.FragmentScoresNavHostBinding;

public class ScoresNavHostFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentScoresNavHostBinding.inflate(inflater, container, false).getRoot();
    }
}
