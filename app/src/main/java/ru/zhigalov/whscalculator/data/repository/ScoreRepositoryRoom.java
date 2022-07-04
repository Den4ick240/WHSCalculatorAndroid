package ru.zhigalov.whscalculator.data.repository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import ru.zhigalov.whscalculator.data.dao.ScoreDao;
import ru.zhigalov.whscalculator.data.mappers.ScoreMapper;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.domain.repository.ScoreRepository;

public class ScoreRepositoryRoom implements ScoreRepository {
    private final ScoreDao scoreDao;
    private final ScoreMapper scoreMapper;

    public ScoreRepositoryRoom(ScoreDao scoreDao, ScoreMapper scoreMapper) {
        this.scoreDao = scoreDao;
        this.scoreMapper = scoreMapper;
    }

    @Override
    public Flowable<List<Score>> getScores() {
        return scoreDao.getScoresWithCourses()
                .map(scoreAndCourses -> scoreAndCourses.stream()
                        .map(scoreMapper::scoreAndCourseToScore)
                        .collect(Collectors.toList())
                );
    }

    @Override
    public Completable saveScore(Score score) {
        return scoreDao.saveScore(scoreMapper.scoreToScoreEntity(score));
    }
}
