package ru.zhigalov.whscalculator.data.repository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import ru.zhigalov.whscalculator.data.dao.CourseDao;
import ru.zhigalov.whscalculator.data.mappers.CourseMapper;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;

public class CourseRepositoryRoom implements CourseRepository {
    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    public CourseRepositoryRoom(CourseDao courseDao, CourseMapper courseMapper) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
    }

    @Override
    public Completable saveCourse(Course course) {
        return courseDao.saveCourse(courseMapper.courseToCourseEntity(course));
    }

    @Override
    public Single<List<Course>> getAllCourses() {
        return courseDao
                .getAllCourses()
                .map(list ->
                        list.stream()
                                .map(courseMapper::courseEntityToCourse)
                                .collect(Collectors.toList()));
    }

    @Override
    public Maybe<Course> getCourseById(int id) {
        return courseDao.getCourseById(id).map(courseMapper::courseEntityToCourse);
    }
}
