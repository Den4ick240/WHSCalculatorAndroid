package ru.zhigalov.whscalculator.ui.main.courses.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import ru.zhigalov.whscalculator.databinding.FragmentCourseItemBinding;

public class CourseListRecyclerViewAdapter extends RecyclerView.Adapter<CourseListRecyclerViewAdapter.ViewHolder> {
    public CourseListRecyclerViewAdapter(OnCourseSelectedListener onCourseSelectedListener) {
        this.onCourseSelectedListener = onCourseSelectedListener;
    }

    public interface OnCourseSelectedListener {
        void courseSelected(int id);
    }

    private final OnCourseSelectedListener onCourseSelectedListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentCourseItemBinding binding = FragmentCourseItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, onCourseSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentCourseItemBinding binding;
        private final OnCourseSelectedListener listener;

        public ViewHolder(FragmentCourseItemBinding binding, OnCourseSelectedListener onCourseSelectedListener) {
            super(binding.getRoot());
            this.binding = binding;
            listener = onCourseSelectedListener;
        }

        public void bind(int position) {
            binding.getRoot().setOnClickListener(v -> listener.courseSelected(position));
        }
    }
}
