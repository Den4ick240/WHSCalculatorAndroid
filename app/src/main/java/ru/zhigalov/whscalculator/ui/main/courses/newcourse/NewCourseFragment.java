package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.databinding.FragmentNewCourseBinding;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.ui.main.LoadingDialog;
import ru.zhigalov.whscalculator.ui.main.MyTextWatcher;

@AndroidEntryPoint
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

        viewModel.nameError.observe(getViewLifecycleOwner(), binding.name::setError);
        viewModel.courseRatingError.observe(getViewLifecycleOwner(), binding.courseRating::setError);
        viewModel.slopeRatingError.observe(getViewLifecycleOwner(), binding.slopeRating::setError);
        viewModel.parError.observe(getViewLifecycleOwner(), binding.par::setError);


        binding.parText.addTextChangedListener(new MyTextWatcher(() -> binding.par.setError(null)));
        binding.nameText.addTextChangedListener(new MyTextWatcher(() -> binding.name.setError(null)));
        binding.courseRatingText.addTextChangedListener(new MyTextWatcher(() -> binding.courseRating.setError(null)));
        binding.slopeRatingText.addTextChangedListener(new MyTextWatcher(() -> binding.slopeRating.setError(null)));

        binding.saveButton.setOnClickListener(this);
    }

    private void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onPause() {
        super.onPause();
        setCourse();
    }

    public void setCourse() {
        String courseRatingString = binding.courseRatingText.getText().toString();
        String parString = binding.parText.getText().toString();
        String slopeRatingString = binding.slopeRatingText.getText().toString();
        String name = binding.nameText.getText().toString();

        viewModel.setCourse(name,
                courseRatingString,
                slopeRatingString,
                parString
        );
    }

    @Override
    public void onClick(View v) {
        setCourse();
        viewModel.saveCourse();
        LoadingDialog loadingDialog = new LoadingDialog(requireContext());
        loadingDialog.start();
        viewModel.getSavedLiveData().observe(getViewLifecycleOwner(), isSaved -> {
            if (isSaved == null) return;
            loadingDialog.dismiss();
            if (isSaved)
                navigateBack();
        });
    }
}