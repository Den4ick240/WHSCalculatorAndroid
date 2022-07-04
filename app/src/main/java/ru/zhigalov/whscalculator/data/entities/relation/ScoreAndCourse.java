package ru.zhigalov.whscalculator.data.entities.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import ru.zhigalov.whscalculator.data.entities.CourseEntity;
import ru.zhigalov.whscalculator.data.entities.ScoreEntity;

public class ScoreAndCourse {
    @Embedded
    public final ScoreEntity score;
    @Relation(
            parentColumn = "courseId",
            entityColumn = "courseId"
    )
    public final CourseEntity course;

    public ScoreAndCourse(ScoreEntity score, CourseEntity course) {
        this.score = score;
        this.course = course;
    }
}
