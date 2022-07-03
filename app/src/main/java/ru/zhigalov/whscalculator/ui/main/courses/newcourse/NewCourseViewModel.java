package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

import ru.zhigalov.whscalculator.domain.models.Course;

public class NewCourseViewModel extends ViewModel {
    private final MutableLiveData<Course> course = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> saved = new MutableLiveData<>(false);

    public final LiveData<String> name = Transformations.map(course, course -> course == null ? "" : course.getName());
    public final LiveData<String> slopeRating = Transformations.map(course, course -> course == null ? "" : Integer.toString(course.getSlopeRating()));
    public final LiveData<String> courseRating = Transformations.map(course, course -> course == null ? "" : Integer.toString(course.getCourseRating()));
    public final LiveData<String> par = Transformations.map(course, course -> course == null ? "" : Integer.toString(course.getPar()));

    public void initCourse(Course course) {
        if (this.course.getValue() == null)
            this.course.setValue(course);
    }

    public void save(String name, int courseRating, int slopeRating, int par) {
        Integer id = this.course.getValue() == null ? null : this.course.getValue().getId();
        Course course = new Course(
                id,
                name,
                slopeRating,
                courseRating,
                par
        );
        this.course.setValue(course);
        //TODO: save to usecase (database)
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                saved.postValue(true);
            }
        },1000,1000);
    }

    public LiveData<Boolean> getSavedLiveData() {
        return saved;
    }
}
