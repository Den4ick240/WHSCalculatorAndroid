package ru.zhigalov.whscalculator.ui.main.courses.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.zhigalov.whscalculator.databinding.FragmentCourseItemBinding;
import ru.zhigalov.whscalculator.domain.models.Course;

public class CourseListRecyclerViewAdapter extends RecyclerView.Adapter<CourseListRecyclerViewAdapter.ViewHolder> {
    private List<Course> courses;

    public CourseListRecyclerViewAdapter(OnCourseSelectedListener onCourseSelectedListener) {
        this.onCourseSelectedListener = onCourseSelectedListener;
    }

    public void updateItems(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged(); //TODO: proper notificaton
    }

    public interface OnCourseSelectedListener {
        void courseSelected(Course course);
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
        holder.bind(courses.get(position));
    }

    @Override
    public int getItemCount() {
        if (courses == null) return 0;
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentCourseItemBinding binding;
        private final OnCourseSelectedListener listener;

        public ViewHolder(FragmentCourseItemBinding binding, OnCourseSelectedListener onCourseSelectedListener) {
            super(binding.getRoot());
            this.binding = binding;
            listener = onCourseSelectedListener;
        }

        public void bind(Course course) {
            binding.getRoot().setOnClickListener(v -> listener.courseSelected(course));
            binding.par.setText(String.format("PAR: %d", course.getPar()));
            binding.courseRating.setText(String.format("Course rating: %d", course.getCourseRating()));
            binding.slopeRating.setText(String.format("Slope rating: %d", course.getCourseRating()));
            binding.name.setText(course.getName());
        }
    }
}
