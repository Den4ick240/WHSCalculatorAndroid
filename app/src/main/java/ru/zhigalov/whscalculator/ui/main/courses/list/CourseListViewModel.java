package ru.zhigalov.whscalculator.ui.main.courses.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;

@HiltViewModel
public class CourseListViewModel extends ViewModel {
    private final CourseRepository courseRepository;
    private final MutableLiveData<List<Course>> courseList = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CourseListViewModel(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public LiveData<List<Course>> getCourseList() {
        return courseList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void loadData() {
        disposables.add(courseRepository.getAllCourses()
                .subscribeOn(Schedulers.io())
                .subscribe(courseList::postValue)
        );
    }

    public void initData() {
        if (courseList.getValue() == null)
            loadData();
    }
}
