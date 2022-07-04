package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

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
            viewModel.initData(initialCourse);

        viewModel.courseRating.observe(getViewLifecycleOwner(),
                newText -> onLiveDataTextChange(binding.courseRatingText, newText));
        viewModel.name.observe(getViewLifecycleOwner(),
                newText -> onLiveDataTextChange(binding.nameText, newText));
        viewModel.slopeRating.observe(getViewLifecycleOwner(),
                newText -> onLiveDataTextChange(binding.slopeRatingText, newText));
        viewModel.par.observe(getViewLifecycleOwner(),
                newText -> onLiveDataTextChange(binding.parText, newText));

        viewModel.nameError.observe(getViewLifecycleOwner(), binding.name::setError);
        viewModel.courseRatingError.observe(getViewLifecycleOwner(), binding.courseRating::setError);
        viewModel.slopeRatingError.observe(getViewLifecycleOwner(), binding.slopeRating::setError);
        viewModel.parError.observe(getViewLifecycleOwner(), binding.par::setError);


        binding.parText.addTextChangedListener(new MyTextWatcher(text -> {
            binding.par.setError(null);
            viewModel.setPar(text);
        }));
        binding.nameText.addTextChangedListener(new MyTextWatcher(text -> {
            binding.name.setError(null);
            viewModel.setName(text);
        }));
        binding.courseRatingText.addTextChangedListener(new MyTextWatcher(text -> {
            binding.courseRating.setError(null);
            viewModel.setCourseRating(text);
        }));
        binding.slopeRatingText.addTextChangedListener(new MyTextWatcher(text -> {
            binding.slopeRating.setError(null);
            viewModel.setSlopeRating(text);
        }));

        setupHandicapIndexField();

        binding.saveButton.setOnClickListener(this);

        viewModel.courseHandicap.observe(getViewLifecycleOwner(), handicap -> binding.courseHandicap.setText(
                String.format("Course handicap = %.2f", handicap)
        ));
    }

    private void setupHandicapIndexField() {
        MediatorLiveData<String> hintText = new MediatorLiveData<>();
        hintText.addSource(viewModel.handicapIndexString, text ->
                hintText.setValue(
                        binding.handicapIndexText.getText() == null ||
                                binding.handicapIndexText.getText().toString().isEmpty() ? text : ""));

        binding.handicapIndexText.addTextChangedListener(new MyTextWatcher(text ->
                hintText.setValue(text.isEmpty() ? viewModel.handicapIndexString.getValue() : "")
        ));

        hintText.observe(getViewLifecycleOwner(), binding.handicapIndex::setHint);

        binding.handicapIndexText.addTextChangedListener(new MyTextWatcher(viewModel::setHandicapIndex));
    }

    private void onLiveDataTextChange(TextInputEditText textInputEditText, String newText) {
        Editable editable = textInputEditText.getText();
        String oldText = editable == null ? null : editable.toString();
        if (Objects.equals(oldText, newText)) return;
        textInputEditText.setText(newText);
    }

//    private void onTextChange(TextInputLayout textInputLayout) {
//        textInputLayout.setError(null);
////        setCourse();
//    }

    private void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
////        setCourse();
//    }

//    public void setCourse() {
//        String courseRatingString = binding.courseRatingText.getText().toString();
//        String parString = binding.parText.getText().toString();
//        String slopeRatingString = binding.slopeRatingText.getText().toString();
//        String name = binding.nameText.getText().toString();
//
////        viewModel.setCourse(name,
////                courseRatingString,
////                slopeRatingString,
////                parString
////        );
//    }

    @Override
    public void onClick(View v) {
//        setCourse();
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