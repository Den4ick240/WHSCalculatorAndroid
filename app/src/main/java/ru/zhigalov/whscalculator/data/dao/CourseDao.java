package ru.zhigalov.whscalculator.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import ru.zhigalov.whscalculator.domain.models.Course;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;

@Dao
public interface CourseDao extends CourseRepository {
    @Query("SELECT * FROM course")
    Single<List<Course>> getAllCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveCourse(Course course);

    @Query("SELECT * FROM course WHERE id = :id")
    Maybe<Course> getCourseById(int id);
}
