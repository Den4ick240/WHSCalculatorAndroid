package ru.zhigalov.whscalculator.domain.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zhigalov.whscalculator.domain.models.Score;

public interface ScoreRepository {
    Single<List<Score>> getScores();
    Completable saveScore(Score score);
}
