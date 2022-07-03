package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.zhigalov.whscalculator.databinding.FragmentNewCourseBinding;
import ru.zhigalov.whscalculator.domain.models.Course;

public class NewCourseFragment extends Fragment {

    private FragmentNewCourseBinding binding;
    private NewCourseViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewCourseViewModel.class);
        Course initialCourse = NewCourseFragmentArgs.fromBundle(getArguments()).getInitialCourse();
        if (initialCourse != null)
            viewModel.initCourse(initialCourse);

        viewModel.courseRating.observe(getViewLifecycleOwner(), binding.courseRating.getEditText()::setText);
        //TODO: course can be null
//            binding.courseRating.getEditText().setText(Integer.toString(initialCourse.getCourseRating()));
    }
}