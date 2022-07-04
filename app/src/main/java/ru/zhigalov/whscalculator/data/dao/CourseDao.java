package ru.zhigalov.whscalculator.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import ru.zhigalov.whscalculator.data.entities.CourseEntity;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courseentity")
    Flowable<List<CourseEntity>> getAllCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveCourse(CourseEntity course);

    @Query("SELECT * FROM courseentity WHERE courseId = :id")
    Maybe<CourseEntity> getCourseById(int id);
}
