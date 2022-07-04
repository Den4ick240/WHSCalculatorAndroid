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

    private final MutableLiveData<Boolean> saved = new MutableLiveData<>(false);
    private Course course = null;

    private final CompositeDisposable disposables = new CompositeDisposable();


    private final MutableLiveData<String> _name = new MutableLiveData<>();
    private final MutableLiveData<String> _slopeRating = new MutableLiveData<>();
    private final MutableLiveData<String> _courseRating = new MutableLiveData<>();
    private final MutableLiveData<String> _par = new MutableLiveData<>();
    public final LiveData<String> name = _name;
    public final LiveData<String> slopeRating = _slopeRating;
    public final LiveData<String> courseRating = _courseRating;
    public final LiveData<String> par = _par;

    private final MutableLiveData<String> _nameError = new MutableLiveData<>();
    private final MutableLiveData<String> _courseRatingError = new MutableLiveData<>();
    private final MutableLiveData<String> _slopeRatingError = new MutableLiveData<>();
    private final MutableLiveData<String> _parError = new MutableLiveData<>();
    public final LiveData<String> nameError = _nameError;
    public final LiveData<String> courseRatingError = _courseRatingError;
    public final LiveData<String> slopeRatingError = _slopeRatingError;
    public final LiveData<String> parError = _parError;

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
        if (this.course != null) return;
        this.course = course;
        _name.setValue(course.getName());
        _par.setValue(Integer.toString(course.getPar()));
        _slopeRating.setValue(Integer.toString(course.getSlopeRating()));
        _courseRating.setValue(Integer.toString(course.getCourseRating()));
    }


    public void setCourse(String name, String courseRating, String slopeRating, String par) {
        _name.setValue(name);
        _par.setValue(par);
        _courseRating.setValue(courseRating);
        _slopeRating.setValue(slopeRating);

    }

    public void saveCourse() {
        if (courseRating.getValue() == null || courseRating.getValue().isEmpty())
            _courseRatingError.setValue("Course rating cannot be empty.");
        else
            _courseRatingError.setValue(null);
        if (slopeRating.getValue() == null || slopeRating.getValue().isEmpty())
            _slopeRatingError.setValue("Slope rating cannot be empty.");
        else
            _slopeRatingError.setValue(null);
        if (par.getValue() == null || par.getValue().isEmpty())
            _parError.setValue("Course PAR cannot be empty.");
        else
            _parError.setValue(null);
        if (name.getValue() == null || name.getValue().isEmpty())
            _nameError.setValue("Course name cannot be empty.");
        else
            _nameError.setValue(null);
        System.out.println(_nameError.getValue());

        if (courseRatingError.getValue() != null || nameError.getValue() != null || slopeRatingError.getValue() != null || parError.getValue() != null) {
            saved.postValue(false);
            return;
        }

        course = new Course(
                course == null ? null : course.getId(),
                name.getValue(),
                Integer.parseInt(slopeRating.getValue()),
                Integer.parseInt(courseRating.getValue()),
                Integer.parseInt(par.getValue())
        );

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
