package ru.zhigalov.whscalculator.ui.main.scores.newscore;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.TimeZone;

import ru.zhigalov.whscalculator.databinding.FragmentNewScoreBinding;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.ui.main.MyTextWatcher;

public class NewScoreFragment extends Fragment implements View.OnClickListener {

    private NewScoreViewModel viewModel;
    private FragmentNewScoreBinding binding;
    private MaterialDatePicker<Long> datePicker;

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

        viewModel.scoreText.observe(getViewLifecycleOwner(), binding.scoreText::setText);

        viewModel.scoreTextError.observe(getViewLifecycleOwner(), binding.score::setError);
        viewModel.dateError.observe(getViewLifecycleOwner(), binding.date::setError);
        viewModel.courseError.observe(getViewLifecycleOwner(), binding.course::setError);

        binding.scoreText.addTextChangedListener(new MyTextWatcher(() -> binding.score.setError(null)));

        binding.dateText.setInputType(InputType.TYPE_NULL);
        binding.dateText.setKeyListener(null);
        binding.dateText.setOnFocusChangeListener((unused, hasFocus) -> {
            if (hasFocus)
                showDatePicker();
        });
        binding.dateText.setOnClickListener(v ->

                showDatePicker()
        );


        binding.saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        viewModel.setScore(
                null, null, binding.scoreText.getText().toString()
        );
        viewModel.saveScore();
    }

    public void showDatePicker() {
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        calendar.set(Calendar.MONTH, Calendar.JANUARY);
//        calendar.set(Calendar.YEAR, 2020); //TODO: get rid of magic numbers
        long start = calendar.getTimeInMillis(); //TODO: and refactor this whole method
        calendar.setTimeInMillis(today);
        long end = calendar.getTimeInMillis();

        CalendarConstraints build = new CalendarConstraints.Builder()
                .setStart(start)
                .setEnd(end).build();
        datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setCalendarConstraints(build)
                        .build();
        datePicker.show(getParentFragmentManager(), "teg"); //TODO: proper tag
    }
}