package ru.zhigalov.whscalculator.domain.models;

import java.util.Objects;

public class UsedScore {
    private final Score score;
    private Boolean isUsedInHandicapIndex;

    public UsedScore(Score score, Boolean isUsedInHandicapIndex) {
        this.score = score;
        this.isUsedInHandicapIndex = isUsedInHandicapIndex;
    }

    public Score getScore() {
        return score;
    }

    public Boolean getUsedInHandicapIndex() {
        return isUsedInHandicapIndex;
    }

    public void setUsedInHandicapIndex(Boolean usedInHandicapIndex) {
        isUsedInHandicapIndex = usedInHandicapIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsedScore usedScore = (UsedScore) o;
        return Objects.equals(score, usedScore.score) && Objects.equals(isUsedInHandicapIndex, usedScore.isUsedInHandicapIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, isUsedInHandicapIndex);
    }

    @Override
    public String toString() {
        return "UsedScore{" +
                "score=" + score +
                ", isUsedInHandicapIndex=" + isUsedInHandicapIndex +
                '}';
    }
}
