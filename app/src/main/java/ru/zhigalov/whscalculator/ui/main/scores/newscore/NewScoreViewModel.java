package ru.zhigalov.whscalculator.ui.main.scores.newscore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.domain.repository.ScoreRepository;

@HiltViewModel
public class NewScoreViewModel extends ViewModel {
    private final ScoreRepository scoreRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Boolean> _saved = new MutableLiveData<>(false);
    public final LiveData<Boolean> saved = _saved;
    private Score score = null;

    private final MutableLiveData<Course> _course = new MutableLiveData<>();
    private final MutableLiveData<String> _scoreText = new MutableLiveData<>();
    private final MutableLiveData<Date> _date = new MutableLiveData<>();
    public final LiveData<Course> course = _course;
    public final LiveData<String> scoreText = _scoreText;
    public final LiveData<Date> date = _date;

    private final MutableLiveData<String> _courseError = new MutableLiveData<>();
    private final MutableLiveData<String> _scoreTextError = new MutableLiveData<>();
    private final MutableLiveData<String> _dateError = new MutableLiveData<>();
    public final LiveData<String> courseError = _courseError;
    public final LiveData<String> scoreTextError = _scoreTextError;
    public final LiveData<String> dateError = _dateError;

    @Inject
    public NewScoreViewModel(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void initScore(Score initialScore) {
        if (score != null) return;
        score = initialScore;
        _course.setValue(score.getCourse());
        _date.setValue(score.getDate());
        _scoreText.setValue(Integer.toString(score.getScore()));
    }

    public void setDate(Long date) {
        _date.setValue(new Date(date));
    }

    public void setCourse(Course course) {
        _course.setValue(course);
    }

    public void setScore(String scoreText) {
        _scoreText.setValue(scoreText);
    }

    public void saveScore() {
        _courseError.setValue(course.getValue() == null ? "Course must be selected" : null);
        _dateError.setValue(date.getValue() == null ? "Date must be specified" : null);
        _scoreTextError.setValue(scoreText.getValue() == null || scoreText.getValue().isEmpty() ?
                "Score cannot be empty" : null);

        if (_dateError.getValue() != null || _scoreTextError.getValue() != null
                || _courseError.getValue() != null) {
            _saved.postValue(false);
            return;
        }

        score = new Score(
                score == null ? null : score.getId(),
                Integer.parseInt(scoreText.getValue()),
                date.getValue(),
                course.getValue()
        );

        disposables.add(
                scoreRepository.saveScore(score)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> _saved.postValue(true))
        );
    }
}