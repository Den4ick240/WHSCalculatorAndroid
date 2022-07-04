package ru.zhigalov.whscalculator.ui.main.scores.scorelist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.databinding.FragmentScoreListBinding;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.domain.models.UsedScore;

@AndroidEntryPoint
public class ScoreListFragment extends Fragment implements ScoreListRecyclerViewAdapter.OnScoreClicked, View.OnClickListener {
    private FragmentScoreListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScoreListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView list = binding.list;
        Context context = list.getContext();
        list.setLayoutManager(new LinearLayoutManager(context));
        ScoreListRecyclerViewAdapter adapter = new ScoreListRecyclerViewAdapter(this, requireContext());
        list.setAdapter(adapter);
        binding.addScoreButton.setOnClickListener(this);
        ScoreListViewModel viewModel = new ViewModelProvider(this).get(ScoreListViewModel.class);
        viewModel.getScoresLiveData().observe(getViewLifecycleOwner(), adapter::updateData);
        viewModel.initScores();
    }

    @Override
    public void onScoreClicked(UsedScore score) {
        navigateToNewScoreFragment(score.getScore());
    }

    private void navigateToNewScoreFragment(Score score) {
        ScoreListFragmentDirections.ActionScoreListFragmentToNewScoreFragment action =
                ScoreListFragmentDirections.actionScoreListFragmentToNewScoreFragment();
        action.setInitialScore(score);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onClick(View v) {
        navigateToNewScoreFragment(null);
    }
}