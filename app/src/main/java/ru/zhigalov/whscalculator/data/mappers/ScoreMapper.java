package ru.zhigalov.whscalculator.data.mappers;

import ru.zhigalov.whscalculator.data.entities.ScoreEntity;
import ru.zhigalov.whscalculator.data.entities.relation.ScoreAndCourse;
import ru.zhigalov.whscalculator.domain.models.Score;

public class ScoreMapper {
    private final CourseMapper courseMapper;

    public ScoreMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public Score scoreAndCourseToScore(ScoreAndCourse scoreAndCourse) {
        return new Score(
               scoreAndCourse.score.id,
               scoreAndCourse.score.score,
               scoreAndCourse.score.date,
                courseMapper.courseEntityToCourse(scoreAndCourse.course)
        );
    }

    public ScoreEntity scoreToScoreEntity(Score score) {
        return new ScoreEntity(
                score.getId(),
                score.getDate(),
                score.getScore(),
                score.getCourse().getId()
        );
    }
}
