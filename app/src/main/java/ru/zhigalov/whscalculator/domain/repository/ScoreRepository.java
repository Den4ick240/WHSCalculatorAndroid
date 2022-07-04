package ru.zhigalov.whscalculator.domain.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import ru.zhigalov.whscalculator.domain.models.Score;

public interface ScoreRepository {
    Flowable<List<Score>> getScores();
    Completable saveScore(Score score);
}
