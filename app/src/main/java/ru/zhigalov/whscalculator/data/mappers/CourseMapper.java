package ru.zhigalov.whscalculator.data.mappers;

import ru.zhigalov.whscalculator.data.entities.CourseEntity;
import ru.zhigalov.whscalculator.domain.models.Course;

public class CourseMapper {
    public CourseEntity courseToCourseEntity(Course course) {
        return new CourseEntity(
                course.getId(),
                course.getName(),
                course.getSlopeRating(),
                course.getCourseRating(),
                course.getPar()
        );
    }

    public Course courseEntityToCourse(CourseEntity courseEntity) {
        return new Course(
                courseEntity.courseId,
                courseEntity.name,
                courseEntity.slopeRating,
                courseEntity.courseRating,
                courseEntity.par
        );
    }
}
