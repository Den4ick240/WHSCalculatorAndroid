package ru.zhigalov.whscalculator.domain.usecase;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.domain.models.UsedScore;
import ru.zhigalov.whscalculator.domain.repository.ScoreRepository;

public class GetUsedScores {
    private final ScoreRepository scoreRepository;

    @Inject
    public GetUsedScores(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Flowable<List<UsedScore>> getUsedScores() {
        return scoreRepository.getScores().map(this::map);
    }

    private List<UsedScore> map(List<Score> scoreList) {
        int usedAmount = 1;
        if (scoreList.size() == 20)
            usedAmount = 8;
        else if (scoreList.size() > 7)
            usedAmount = (scoreList.size() - 4) / 2;

        scoreList = scoreList.stream()
                .sorted(Comparator.comparing(Score::getDate).reversed())
                .collect(Collectors.toList());

        List<Score> usedScores = scoreList.stream()
                .limit(usedAmount)
                .collect(Collectors.toList());

        return scoreList.stream()
                .map(score -> new UsedScore(score, usedScores.contains(score)))
                .collect(Collectors.toList());
    }
}
