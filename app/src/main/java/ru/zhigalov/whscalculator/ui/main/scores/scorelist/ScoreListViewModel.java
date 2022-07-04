package ru.zhigalov.whscalculator.ui.main.scores.scorelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.zhigalov.whscalculator.domain.models.UsedScore;
import ru.zhigalov.whscalculator.domain.usecase.GetHandicapIndex;
import ru.zhigalov.whscalculator.domain.usecase.GetUsedScores;

@HiltViewModel
public class ScoreListViewModel extends ViewModel {
    private final GetUsedScores getUsedScores;
    private final GetHandicapIndex getHandicapIndex;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<UsedScore>> scores = new MutableLiveData<>();
    private final MutableLiveData<Double> handicapIndex = new MutableLiveData<>();

    public LiveData<List<UsedScore>> getScoresLiveData() {
        return scores;
    }

    public LiveData<Double> getHandicapIndexLiveData() {
        return handicapIndex;
    }

    @Inject
    public ScoreListViewModel(GetUsedScores getUsedScores, GetHandicapIndex getHandicapIndex) {
        this.getUsedScores = getUsedScores;
        this.getHandicapIndex = getHandicapIndex;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void initData() {
        disposables.add(
                getUsedScores.getUsedScores()
                        .subscribeOn(Schedulers.io())
                        .subscribe(scores::postValue)
        );
        disposables.add(
                getHandicapIndex.getHandicapIndex()
                        .subscribeOn(Schedulers.io())
                        .subscribe(handicapIndex::postValue)
        );
    }
}
