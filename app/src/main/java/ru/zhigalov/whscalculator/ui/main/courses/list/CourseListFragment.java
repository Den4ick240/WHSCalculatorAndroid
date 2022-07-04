package ru.zhigalov.whscalculator.ui.main.courses.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.databinding.FragmentCourseListBinding;
import ru.zhigalov.whscalculator.domain.models.Course;

@AndroidEntryPoint
public class CourseListFragment extends Fragment implements CourseListRecyclerViewAdapter.OnCourseSelectedListener {
    private static final String ARG_REQUEST_KEY = "requestKey";
    public static final String DEFAULT_REQUEST_KEY = "course-list-fragment-request-key";

    private String requestKey;
    private CourseListViewModel viewModel;
    private FragmentCourseListBinding binding;

    public static CourseListFragment newInstance(String requestKey) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REQUEST_KEY, requestKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            requestKey = getArguments().getString(ARG_REQUEST_KEY);
        else
            requestKey = DEFAULT_REQUEST_KEY;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCourseListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CourseListViewModel.class);
        CourseListRecyclerViewAdapter adapter =
                new CourseListRecyclerViewAdapter(this);
        binding.list.setAdapter(adapter);
        viewModel.getCourseList().observe(getViewLifecycleOwner(), adapter::updateItems);
        viewModel.initData();
    }

    @Override
    public void courseSelected(Course course) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(requestKey, course);
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
    }
}