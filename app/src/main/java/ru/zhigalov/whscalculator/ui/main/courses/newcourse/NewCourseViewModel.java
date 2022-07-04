package ru.zhigalov.whscalculator.ui.main.courses.newcourse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;
import ru.zhigalov.whscalculator.domain.usecase.GetCourseHandicap;
import ru.zhigalov.whscalculator.domain.usecase.GetHandicapIndex;

@HiltViewModel
public class NewCourseViewModel extends ViewModel {
    private final CourseRepository courseRepository;
    private final GetHandicapIndex getHandicapIndex;
    private final GetCourseHandicap getCourseHandicap;

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

    private final MutableLiveData<Double> _handicapIndex = new MutableLiveData<>();
    public final LiveData<Double> handicapIndex = _handicapIndex;
    public final LiveData<String> handicapIndexString = Transformations.map(
            handicapIndex, index -> index == null ? "" : Double.toString(index)
    );

    private final MutableLiveData<Double> calculatedHandicapIndex = new MutableLiveData<>();

    public final LiveData<Double> courseHandicap;

    private Course getCourse() {
        if (courseRating.getValue() == null || courseRating.getValue().isEmpty())
            return null;
        if (slopeRating.getValue() == null || slopeRating.getValue().isEmpty())
            return null;
        if (par.getValue() == null || par.getValue().isEmpty())
            return null;
        if (name.getValue() == null || name.getValue().isEmpty())
            return null;

        return new Course(
                course == null ? null : course.getId(),
                name.getValue(),
                Integer.parseInt(slopeRating.getValue()),
                Integer.parseInt(courseRating.getValue()),
                Integer.parseInt(par.getValue()));
    }

    private Double calculate() {
        course = getCourse();
        if (course == null) return null;
        Double handicapIndex = this.calculatedHandicapIndex.getValue() == null
                ? this.handicapIndex.getValue()
                : this.calculatedHandicapIndex.getValue();
        if (handicapIndex == null) return null;
        return getCourseHandicap.getCourseHandicap(course, handicapIndex);
    }

    @Inject
    public NewCourseViewModel(CourseRepository courseRepository, GetHandicapIndex getHandicapIndex, GetCourseHandicap getCourseHandicap) {
        this.courseRepository = courseRepository;
        this.getHandicapIndex = getHandicapIndex;
        this.getCourseHandicap = getCourseHandicap;

        MediatorLiveData<Double> mediatorCourseHandicap = new MediatorLiveData<>();
        mediatorCourseHandicap.addSource(Transformations.map(handicapIndex, unused -> calculate()), mediatorCourseHandicap::setValue);
        mediatorCourseHandicap.addSource(Transformations.map(courseRating, unused -> calculate()), mediatorCourseHandicap::setValue);
        mediatorCourseHandicap.addSource(Transformations.map(slopeRating, unused -> calculate()), mediatorCourseHandicap::setValue);
        mediatorCourseHandicap.addSource(Transformations.map(par, unused -> calculate()), mediatorCourseHandicap::setValue);
        mediatorCourseHandicap.addSource(Transformations.map(calculatedHandicapIndex, unused -> calculate()), mediatorCourseHandicap::setValue);
        courseHandicap = mediatorCourseHandicap;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void initData(Course course) {
        if (this.course != null) return;
        this.course = course;
        _name.setValue(course.getName());
        _par.setValue(Integer.toString(course.getPar()));
        _slopeRating.setValue(Integer.toString(course.getSlopeRating()));
        _courseRating.setValue(Integer.toString(course.getCourseRating()));
        calculate();

        disposables.add(
                getHandicapIndex.getHandicapIndex()
                        .subscribeOn(Schedulers.io())
                        .subscribe(_handicapIndex::postValue)
        );
    }


    public void setPar(String par) {
        _par.setValue(par);
        calculate();
    }

    public void setName(String name) {
        _name.setValue(name);
        calculate();
    }

    public void setCourseRating(String courseRating) {
        _courseRating.setValue(courseRating);
        calculate();
    }

    public void setSlopeRating(String slopeRating) {
        _slopeRating.setValue(slopeRating);
        calculate();
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

        course = getCourse();

        disposables.add(
                courseRepository.saveCourse(course)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> saved.postValue(true))
        );

    }

    public LiveData<Boolean> getSavedLiveData() {
        return saved;
    }

    public void setHandicapIndex(String calculatedHandicapIndex) {
        if (calculatedHandicapIndex.isEmpty())
            this.calculatedHandicapIndex.setValue(null);
        else
            this.calculatedHandicapIndex.setValue(Double.parseDouble(calculatedHandicapIndex));
    }
}
