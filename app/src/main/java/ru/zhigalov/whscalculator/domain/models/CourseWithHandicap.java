package ru.zhigalov.whscalculator.domain.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CourseWithHandicap {
    private final Course course;
    private final Double handicapIndex;

    public CourseWithHandicap(Course course, Double handicapIndex) {
        this.course = course;
        this.handicapIndex = handicapIndex;
    }

    public Course getCourse() {
        return course;
    }

    public Double getHandicapIndex() {
        return handicapIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseWithHandicap that = (CourseWithHandicap) o;
        return Objects.equals(course, that.course) && Objects.equals(handicapIndex, that.handicapIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, handicapIndex);
    }

    @NonNull
    @Override
    public String toString() {
        return "CourseWithHandicap{" +
                "course=" + course +
                ", handicapIndex=" + handicapIndex +
                '}';
    }
}
