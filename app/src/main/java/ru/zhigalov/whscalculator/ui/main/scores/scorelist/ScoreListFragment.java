package ru.zhigalov.whscalculator.ui.main.scores.scorelist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zhigalov.whscalculator.R;
import ru.zhigalov.whscalculator.databinding.FragmentScoreListBinding;
import ru.zhigalov.whscalculator.ui.main.scores.scorelist.placeholder.PlaceholderContent;


public class ScoreListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentScoreListBinding binding = FragmentScoreListBinding.inflate(inflater, container, false);
        RecyclerView list = binding.list;
        Context context = list.getContext();
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(new ScoreListRecyclerViewAdapter(PlaceholderContent.ITEMS));
        binding.addScoreButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_scoreListFragment_to_newScoreFragment));
        return binding.getRoot();
    }
}