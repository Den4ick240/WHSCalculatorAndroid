package ru.zhigalov.whscalculator.ui.main.courses.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.DataSyncViewModel;
import ru.zhigalov.whscalculator.MainActivity;
import ru.zhigalov.whscalculator.databinding.FragmentCourseListBinding;
import ru.zhigalov.whscalculator.domain.models.Course;

@AndroidEntryPoint
public class CourseListFragment extends Fragment implements CourseListRecyclerViewAdapter.OnCourseSelectedListener {
    private static final String ARG_REQUEST_KEY = "requestKey";

    private String requestKey = null;
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
        CourseListViewModel viewModel = new ViewModelProvider(this).get(CourseListViewModel.class);
        CourseListRecyclerViewAdapter adapter =
                new CourseListRecyclerViewAdapter(this);
        binding.list.setAdapter(adapter);
        viewModel.getCourseList().observe(getViewLifecycleOwner(), adapter::updateItems);
        new ViewModelProvider(requireActivity()).get(DataSyncViewModel.class)
                .handicapIndexChanged.observe(getViewLifecycleOwner(),
                        unused -> viewModel.initData()
                );
    }

    @Override
    public void courseSelected(Course course) {
        boolean fragmentWasCreatedUsingNewInstance = requestKey != null;
        if (fragmentWasCreatedUsingNewInstance)
            setFragmentResult(course);

        setFragmentResultUsingNavigationComponent(course);
    }

    private Bundle getResultBundle(Course course) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(requestKey, course);
        return bundle;
    }

    private void setFragmentResult(Course course) {
        getParentFragmentManager().setFragmentResult(requestKey, getResultBundle(course));
    }

    private void setFragmentResultUsingNavigationComponent(Course course) {
        NavController navController = NavHostFragment.findNavController(this);
        String resultCode = CourseListFragmentArgs.fromBundle(getArguments()).getResultCode();
        NavBackStackEntry previousBackStackEntry = navController.getPreviousBackStackEntry();

        boolean fragmentWasCreatedUsingNavigationComponent = resultCode != null && previousBackStackEntry != null;
        if (!fragmentWasCreatedUsingNavigationComponent) return;

        previousBackStackEntry.getSavedStateHandle().set(resultCode, course);

        navController.navigateUp();
    }
}