package ru.zhigalov.whscalculator.domain.usecase;

import javax.inject.Inject;

import ru.zhigalov.whscalculator.domain.models.Course;

public class GetCourseHandicap {
    @Inject
    public GetCourseHandicap() {
    }

    public Double getCourseHandicap(Course course, Double handicapIndex) {
        double courseHandicap = handicapIndex * (course.getSlopeRating() / 113.0) + (course.getCourseRating() - course.getPar());
        System.out.println(courseHandicap);
        return courseHandicap;
    }
}
