package ru.zhigalov.whscalculator.ui.main.courses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zhigalov.whscalculator.R;
import ru.zhigalov.whscalculator.databinding.FragmentCoursesBinding;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.ui.main.courses.list.CourseListFragment;


public class CoursesFragment extends Fragment implements FragmentResultListener {
    private static final String ON_COURSE_CLICKED_REQUEST_KEY = "course-fragment-on-course-clicked-request-key";
    private FragmentCoursesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.addCourseButton.setOnClickListener(v -> navigateToNewCourseFragment());
        Fragment courseListFragment = CourseListFragment.newInstance(ON_COURSE_CLICKED_REQUEST_KEY);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.course_list_fragment_container, courseListFragment)
                .commit();
        getChildFragmentManager()
                .setFragmentResultListener(ON_COURSE_CLICKED_REQUEST_KEY, this, this);
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        int position = result.getInt(ON_COURSE_CLICKED_REQUEST_KEY, -1);
        if (position == -1) return;
        Course initialCourse = new Course(position, "fsdf", 1, 2, 3);
        navigateToNewCourseFragment(initialCourse);
    }

    private void navigateToNewCourseFragment(Course initialCourse) {
        CoursesFragmentDirections.ActionCoursesFragmentToNewCourseFragment action =
                CoursesFragmentDirections.actionCoursesFragmentToNewCourseFragment();
        action.setInitialCourse(initialCourse);
        NavHostFragment.findNavController(this).navigate(action);

    }

    private void navigateToNewCourseFragment() {
        navigateToNewCourseFragment(null);
    }
}