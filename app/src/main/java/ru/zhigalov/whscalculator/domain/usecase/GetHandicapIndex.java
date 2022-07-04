package ru.zhigalov.whscalculator.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import ru.zhigalov.whscalculator.domain.models.Score;
import ru.zhigalov.whscalculator.domain.models.UsedScore;

public class GetHandicapIndex {
    private final GetUsedScores getUsedScores;

    @Inject
    public GetHandicapIndex(GetUsedScores getUsedScores) {
        this.getUsedScores = getUsedScores;
    }

    public Flowable<Double> getHandicapIndex() {
        return getUsedScores.getUsedScores().map(this::map);
    }

    private Double map(List<UsedScore> usedScoreList) {
        return usedScoreList.stream()
                .filter(UsedScore::getUsedInHandicapIndex)
                .map(UsedScore::getScore)
                .mapToInt(this::getHandicapDifferential)
                .average()
                .orElse(0);

    }

    private int getHandicapDifferential(Score score) {
        //TODO: round the right way
        return (score.getScore() - score.getCourse().getCourseRating()) * 113 / score.getCourse().getSlopeRating();
    }
}
