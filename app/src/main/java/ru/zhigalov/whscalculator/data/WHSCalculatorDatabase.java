package ru.zhigalov.whscalculator.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ru.zhigalov.whscalculator.data.converters.Converters;
import ru.zhigalov.whscalculator.data.dao.CourseDao;
import ru.zhigalov.whscalculator.data.dao.ScoreDao;
import ru.zhigalov.whscalculator.data.entities.CourseEntity;
import ru.zhigalov.whscalculator.data.entities.ScoreEntity;

@Database(entities = {CourseEntity.class, ScoreEntity.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class WHSCalculatorDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
    public abstract ScoreDao scoreDao();
}
