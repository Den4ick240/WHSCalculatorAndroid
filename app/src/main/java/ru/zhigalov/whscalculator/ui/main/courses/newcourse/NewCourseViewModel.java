package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ru.zhigalov.whscalculator.domain.models.Course;

public class NewCourseViewModel extends ViewModel {
    private final MutableLiveData<Course> course = new MutableLiveData<>(null);

    public final LiveData<String> name = Transformations.map(course, Course::getName);
    public final LiveData<String> slopeRating = Transformations.map(course, course -> Integer.toString(course.getSlopeRating()));
    public final LiveData<String> courseRating = Transformations.map(course, course -> Integer.toString(course.getCourseRating()));
    public final LiveData<String> par = Transformations.map(course, course -> Integer.toString(course.getPar()));

    public void initCourse(Course course) {
        if (this.course.getValue() == null)
            this.course.setValue(course);
    }

    public void save(Course course) {
        this.course.setValue(course);
        //TODO save to usecase
    }
}
