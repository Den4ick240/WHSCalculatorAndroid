package ru.zhigalov.whscalculator.domain.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import ru.zhigalov.whscalculator.domain.models.Course;

public interface CourseRepository {
    Completable saveCourse(Course course);
    Flowable<List<Course>> getAllCourses();
    Maybe<Course> getCourseById(int id);
}
