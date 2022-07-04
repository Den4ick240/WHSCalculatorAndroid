package ru.zhigalov.whscalculator.ui.main.scores.newscore;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.databinding.FragmentNewScoreBinding;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.ui.main.MyTextWatcher;

@AndroidEntryPoint
public class NewScoreFragment extends Fragment implements View.OnClickListener {
    private static final String CHOSE_COURSE_RESULT_CODE = "choose-course-result-code";

    private NewScoreViewModel viewModel;
    private FragmentNewScoreBinding binding;

    public static NewScoreFragment newInstance() {
        return new NewScoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewScoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewScoreViewModel.class);
        Score initialScore = NewScoreFragmentArgs.fromBundle(getArguments()).getInitialScore();
        if (initialScore != null)
            viewModel.initScore(initialScore);

        setupCourseInput();
        setupDateInput();
        setupScoreInput();

        binding.saveButton.setOnClickListener(this);
    }

    public void setupScoreInput() {
        viewModel.scoreTextError.observe(getViewLifecycleOwner(), binding.score::setError);
        binding.scoreText.addTextChangedListener(new MyTextWatcher(() -> binding.score.setError(null)));
        viewModel.scoreText.observe(getViewLifecycleOwner(), binding.scoreText::setText);

    }

    public void setupDateInput() {
        viewModel.date.observe(getViewLifecycleOwner(), date -> {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(requireContext());
            binding.dateText.setText(dateFormat.format(date));
        });

        viewModel.dateError.observe(getViewLifecycleOwner(), binding.date::setError);
        binding.dateText.addTextChangedListener(new MyTextWatcher(() -> binding.date.setError(null)));
        binding.dateText.setKeyListener(null);
        binding.dateText.setOnFocusChangeListener((unused, hasFocus) -> {
            if (hasFocus)
                showDatePicker();
        });
        binding.dateText.setOnClickListener(v ->
                showDatePicker()
        );
    }

    public void setupCourseInput() {
        viewModel.courseError.observe(getViewLifecycleOwner(), binding.course::setError);
        viewModel.course.observe(getViewLifecycleOwner(), course ->
                binding.courseText.setText(
                        course == null ? null : course.getName()
                ));
        binding.courseText.addTextChangedListener(new MyTextWatcher(() -> binding.course.setError(null)));
        binding.courseText.setKeyListener(null);

        binding.courseText.setOnFocusChangeListener((unused, hasFocus) -> {
            if (hasFocus) navigateToChooseCourse();
        });
        binding.courseText.setOnClickListener(v -> navigateToChooseCourse());

        NavBackStackEntry navBackStackEntry =
                Objects.requireNonNull(NavHostFragment.findNavController(this).getCurrentBackStackEntry());
        navBackStackEntry
                .getSavedStateHandle()
                .getLiveData(CHOSE_COURSE_RESULT_CODE)
                .observe(getViewLifecycleOwner(), course -> viewModel.setCourse((Course) course)
                );
    }

    public void navigateToChooseCourse() {
        NewScoreFragmentDirections.ActionNewScoreFragmentToSelectCourseFragment action =
                NewScoreFragmentDirections.actionNewScoreFragmentToSelectCourseFragment();
        action.setResultCode(CHOSE_COURSE_RESULT_CODE);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onPause() {
        super.onPause();
        setScore();
    }

    private void setScore() {
        Editable editable = binding.scoreText.getText();
        viewModel.setScore(
                editable == null ? null : editable.toString());
    }

    @Override
    public void onClick(View v) {
        setScore();
        viewModel.saveScore();
        viewModel.saved.observe(getViewLifecycleOwner(), isSaved -> {
            if (isSaved == null) return;
            if (isSaved) NavHostFragment.findNavController(this).navigateUp();
        });
    }

    public void showDatePicker() {
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        calendar.set(Calendar.YEAR, 2020); //TODO: get rid of magic numbers
        long start = calendar.getTimeInMillis(); //TODO: and refactor this whole method
        calendar.setTimeInMillis(today);
        long end = calendar.getTimeInMillis();

        CalendarConstraints build = new CalendarConstraints.Builder()
                .setStart(start)
                .setEnd(end).build();
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(build)
                .build();
        datePicker.addOnPositiveButtonClickListener(viewModel::setDate);
        datePicker.show(getParentFragmentManager(), "date-picker-tag"); //TODO: proper tag
    }
}