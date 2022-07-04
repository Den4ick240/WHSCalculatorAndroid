package ru.zhigalov.whscalculator.domain.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Score implements Serializable {
    private final Integer id;
    private final int score;
    private final Date date;
    private final Course course;

    public Score(Integer id, int score, Date date, Course course) {
        this.id = id;
        this.score = score;
        this.date = date;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return score == score1.score && Objects.equals(id, score1.id) && Objects.equals(date, score1.date) && Objects.equals(course, score1.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, date, course);
    }

    @NonNull
    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                ", date=" + date +
                ", course=" + course +
                '}';
    }
}
