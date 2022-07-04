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
import ru.zhigalov.whscalculator.domain.usecase.GetUsedScores;

@HiltViewModel
public class ScoreListViewModel extends ViewModel {
    private final GetUsedScores getUsedScores;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<UsedScore>> scores = new MutableLiveData<>();

    public LiveData<List<UsedScore>> getScoresLiveData() {
        return scores;
    }

    @Inject
    public ScoreListViewModel(GetUsedScores getUsedScores) {
        this.getUsedScores = getUsedScores;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void initScores() {
        disposables.add(
                getUsedScores.getUsedScores()
                        .subscribeOn(Schedulers.io()).subscribe(
                                scores::postValue
                        )
        );
    }
}
