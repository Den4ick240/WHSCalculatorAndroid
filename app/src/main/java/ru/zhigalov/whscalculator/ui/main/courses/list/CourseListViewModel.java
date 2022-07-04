package ru.zhigalov.whscalculator.ui.main.courses.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.CourseWithHandicap;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;
import ru.zhigalov.whscalculator.domain.usecase.GetCoursesWithHandicaps;

@HiltViewModel
public class CourseListViewModel extends ViewModel {
    private final GetCoursesWithHandicaps getCoursesWithHandicaps;
    private final MutableLiveData<List<CourseWithHandicap>> courseList = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CourseListViewModel(GetCoursesWithHandicaps getCoursesWithHandicaps) {
        this.getCoursesWithHandicaps = getCoursesWithHandicaps;
    }

    public LiveData<List<CourseWithHandicap>> getCourseList() {
        return courseList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void initData() {
        disposables.add(getCoursesWithHandicaps.getCoursesWithHandicaps()
                .subscribeOn(Schedulers.io())
                .subscribe(courseList::postValue)
        );
    }

}
