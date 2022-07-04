package ru.zhigalov.whscalculator.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ru.zhigalov.whscalculator.data.converters.Converters;
import ru.zhigalov.whscalculator.data.dao.CourseDao;
import ru.zhigalov.whscalculator.data.entities.CourseEntity;

@Database(entities = {CourseEntity.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class WHSCalculatorDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
