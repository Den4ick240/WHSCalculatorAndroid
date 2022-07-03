package ru.zhigalov.whscalculator.domain.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable {
    private final Integer id;
    private final String name;
    private final int slopeRating;
    private final int courseRating;
    private final int par;

    public Course(Integer id, String name, int slopeRating, int courseRating, int par) {
        this.id = id;
        this.name = name;
        this.slopeRating = slopeRating;
        this.courseRating = courseRating;
        this.par = par;
    }

    public String getName() {
        return name;
    }

    public int getSlopeRating() {
        return slopeRating;
    }

    public int getCourseRating() {
        return courseRating;
    }

    public int getPar() {
        return par;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && slopeRating == course.slopeRating && courseRating == course.courseRating && par == course.par && Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slopeRating, courseRating, par);
    }

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slopeRating=" + slopeRating +
                ", courseRating=" + courseRating +
                ", par=" + par +
                '}';
    }
}
