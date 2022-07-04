package ru.zhigalov.whscalculator.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zhigalov.whscalculator.data.entities.ScoreEntity;
import ru.zhigalov.whscalculator.data.entities.relation.ScoreAndCourse;

@Dao
public interface ScoreDao {
    @Transaction
    @Query("SELECT * FROM ScoreEntity")
    Single<List<ScoreAndCourse>> getScoresWithCourses();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveScore(ScoreEntity score);
}
