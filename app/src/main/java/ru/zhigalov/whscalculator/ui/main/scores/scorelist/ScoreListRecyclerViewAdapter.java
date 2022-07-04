package ru.zhigalov.whscalculator.ui.main.scores.scorelist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zhigalov.whscalculator.domain.models.UsedScore;
import ru.zhigalov.whscalculator.databinding.FragmentScoreItemBinding;

import java.text.DateFormat;
import java.util.List;

public class ScoreListRecyclerViewAdapter extends RecyclerView.Adapter<ScoreListRecyclerViewAdapter.ViewHolder> {

    interface OnScoreClicked {
        void onScoreClicked(UsedScore score);
    }

    private final OnScoreClicked onScoreClicked;


    private List<UsedScore> scores;
    private final Context context;

    public ScoreListRecyclerViewAdapter(OnScoreClicked onScoreClicked, Context context) {
        this.onScoreClicked = onScoreClicked;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                FragmentScoreItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onScoreClicked);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(scores.get(position));
    }

    @Override
    public int getItemCount() {
        if (scores == null) return 0;
        return scores.size();
    }

    public void updateData(List<UsedScore> list) {
        scores = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentScoreItemBinding binding;
        private final OnScoreClicked onScoreClicked;

        public ViewHolder(FragmentScoreItemBinding binding, OnScoreClicked onScoreClicked) {
            super(binding.getRoot());
            this.binding = binding;
            this.onScoreClicked = onScoreClicked;
        }

        public void bind(UsedScore score) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
            binding.isUsed.setVisibility(score.getUsedInHandicapIndex() ? View.VISIBLE : View.INVISIBLE);
            binding.score.setText(String.format("Score: %d", score.getScore().getScore()));
            binding.name.setText(score.getScore().getCourse().getName());
            binding.date.setText(dateFormat.format(score.getScore().getDate()));

            binding.getRoot().setOnClickListener(v -> onScoreClicked.onScoreClicked(score));

        }
    }
}