package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import ru.zhigalov.whscalculator.databinding.FragmentNewCourseBinding;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.ui.main.LoadingDialog;

public class NewCourseFragment extends Fragment implements View.OnClickListener {

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

        viewModel.courseRating.observe(getViewLifecycleOwner(), binding.courseRatingText::setText);
        viewModel.name.observe(getViewLifecycleOwner(), binding.nameText::setText);
        viewModel.slopeRating.observe(getViewLifecycleOwner(), binding.slopeRatingText::setText);
        viewModel.par.observe(getViewLifecycleOwner(), binding.parText::setText);


        binding.saveButton.setOnClickListener(this);
    }

    private void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onClick(View v) {
        String courseRatingString = binding.courseRatingText.getText().toString();
        String parString = binding.parText.getText().toString();
        String slopeRatingString = binding.slopeRatingText.getText().toString();
        String name = binding.nameText.getText().toString();

        if (courseRatingString.isEmpty())
            binding.courseRating.setError("Course rating cannot be empty.");
        if (slopeRatingString.isEmpty())
            binding.slopeRating.setError("Slope rating cannot be empty.");
        if (parString.isEmpty())
            binding.par.setError("Course PAR cannot be empty.");
        if (name.isEmpty())
            binding.name.setError("Course name cannot be empty.");

        if (courseRatingString.isEmpty() || slopeRatingString.isEmpty() || parString.isEmpty() || name.isEmpty())
            return;

        viewModel.save(name,
                Integer.parseInt(courseRatingString),
                Integer.parseInt(slopeRatingString),
                Integer.parseInt(parString)
        );
        LoadingDialog loadingDialog = new LoadingDialog(requireContext());
        loadingDialog.start();
        viewModel.getSavedLiveData().observe(getViewLifecycleOwner(), isSaved -> {
            if (!isSaved) return;
            loadingDialog.dismiss();
            navigateBack();
        });
    }
}