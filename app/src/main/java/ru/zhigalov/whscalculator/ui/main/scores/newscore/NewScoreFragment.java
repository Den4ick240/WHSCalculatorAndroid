package ru.zhigalov.whscalculator.ui.main.scores.newscore;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zhigalov.whscalculator.databinding.FragmentNewScoreBinding;

public class NewScoreFragment extends Fragment {

    private NewScoreViewModel mViewModel;

    public static NewScoreFragment newInstance() {
        return new NewScoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentNewScoreBinding binding =FragmentNewScoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewScoreViewModel.class);
        // TODO: Use the ViewModel
    }

}