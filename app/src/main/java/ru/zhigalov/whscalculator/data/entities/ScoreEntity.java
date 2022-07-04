package ru.zhigalov.whscalculator.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = CourseEntity.class,
                parentColumns = {"courseId"},
                childColumns = {"courseId"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class ScoreEntity {
    @PrimaryKey
    public final Integer id;

    @ColumnInfo(name = "date")
    public final Date date;

    @ColumnInfo(name = "score")
    public final int score;

    @ColumnInfo(name = "courseId")
    public final Integer courseId;

    public ScoreEntity(Integer id, Date date, int score, Integer courseId) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.courseId = courseId;
    }
}
