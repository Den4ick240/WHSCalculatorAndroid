package ru.zhigalov.whscalculator.ui.main.scores.scorelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.domain.models.UsedScore;

@HiltViewModel
public class ScoreListViewModel extends ViewModel {

    private final MutableLiveData<List<UsedScore>> scores = new MutableLiveData<>();

    public LiveData<List<UsedScore>> getScoresLiveData() {
        return scores;
    }

    @Inject
    public ScoreListViewModel() {}

    public void initScores() {
        UsedScore[] arr = new UsedScore[]{new UsedScore(new Score(null, 24, new Date(),
                new Course(null, "sldjkf", 23, 23, 23)), true)};
        if (scores.getValue() == null)
            scores.postValue(Arrays.asList(arr));
    }
}
