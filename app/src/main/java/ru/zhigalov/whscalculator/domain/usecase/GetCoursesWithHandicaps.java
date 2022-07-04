package ru.zhigalov.whscalculator.domain.usecase;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import ru.zhigalov.whscalculator.domain.models.CourseWithHandicap;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;

public class GetCoursesWithHandicaps {
    private final CourseRepository courseRepository;
    private final GetHandicapIndex getHandicapIndex;
    private final GetCourseHandicap getCourseHandicap;

    @Inject
    public GetCoursesWithHandicaps(CourseRepository courseRepository, GetHandicapIndex getHandicapIndex, GetCourseHandicap getCourseHandicap) {
        this.courseRepository = courseRepository;
        this.getHandicapIndex = getHandicapIndex;
        this.getCourseHandicap = getCourseHandicap;
    }

    public Flowable<List<CourseWithHandicap>> getCoursesWithHandicaps() {
        return getHandicapIndex.getHandicapIndex()
                .flatMap(handicapIndex ->
                        courseRepository.getAllCourses()
                                .map(courseList -> courseList.stream()
                                        .map(course ->
                                                new CourseWithHandicap(course,
                                                        getCourseHandicap.getCourseHandicap(course, handicapIndex)))
                                        .collect(Collectors.toList())
                                )
                );
    }

}
