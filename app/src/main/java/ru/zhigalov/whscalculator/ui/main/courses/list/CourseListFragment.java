package ru.zhigalov.whscalculator.ui.main.courses.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zhigalov.whscalculator.databinding.FragmentCourseListBinding;
import ru.zhigalov.whscalculator.ui.main.courses.CoursesFragment;


public class CourseListFragment extends Fragment implements CourseListRecyclerViewAdapter.OnCourseSelectedListener {
    private static final String ARG_REQUEST_KEY = "requestKey";
    public static final String DEFAULT_REQUEST_KEY = "course-list-fragment-request-key";

    private String requestKey;

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
        FragmentCourseListBinding binding =
                FragmentCourseListBinding.inflate(inflater, container, false);
        CourseListRecyclerViewAdapter adapter =
                new CourseListRecyclerViewAdapter(this);
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void courseSelected(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(requestKey, id);
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
    }
}