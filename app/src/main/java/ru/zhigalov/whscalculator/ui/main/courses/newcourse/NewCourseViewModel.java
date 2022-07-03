package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;

@HiltViewModel
public class NewCourseViewModel extends ViewModel {
    private final CourseRepository courseRepository;

    private final MutableLiveData<Course> course = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> saved = new MutableLiveData<>(false);

    private final CompositeDisposable disposables = new CompositeDisposable();

    public final LiveData<String> name = Transformations.map(course, course -> course == null ? "" : course.getName());
    public final LiveData<String> slopeRating = Transformations.map(course, course -> course == null ? "" : Integer.toString(course.getSlopeRating()));
    public final LiveData<String> courseRating = Transformations.map(course, course -> course == null ? "" : Integer.toString(course.getCourseRating()));
    public final LiveData<String> par = Transformations.map(course, course -> course == null ? "" : Integer.toString(course.getPar()));

    @Inject
    public NewCourseViewModel(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

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

        disposables.add(
                courseRepository.saveCourse(course)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> saved.postValue(true))
        );
    }

    public LiveData<Boolean> getSavedLiveData() {
        return saved;
    }
}
