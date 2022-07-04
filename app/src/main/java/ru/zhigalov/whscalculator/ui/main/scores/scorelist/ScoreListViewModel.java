package ru.zhigalov.whscalculator.ui.main.scores.scorelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.UsedScore;
import ru.zhigalov.whscalculator.domain.repository.ScoreRepository;

@HiltViewModel
public class ScoreListViewModel extends ViewModel {
    private final ScoreRepository scoreRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<UsedScore>> scores = new MutableLiveData<>();

    public LiveData<List<UsedScore>> getScoresLiveData() {
        return scores;
    }

    @Inject
    public ScoreListViewModel(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void initScores() {
//        UsedScore[] arr = new UsedScore[]{new UsedScore(new Score(null, 24, new Date(),
//                new Course(null, "sldjkf", 23, 23, 23)), true)};
        if (scores.getValue() == null)
            loadScores();
//            scores.postValue(Arrays.asList(arr));
    }

    private void loadScores() {
        disposables.add(
                scoreRepository.getScores()
                        .map(list -> list.stream().map(score -> new UsedScore(score, true)).collect(Collectors.toList()))
                        .subscribeOn(Schedulers.io()).subscribe(
                        scores::postValue
                )
        );
    }
}
