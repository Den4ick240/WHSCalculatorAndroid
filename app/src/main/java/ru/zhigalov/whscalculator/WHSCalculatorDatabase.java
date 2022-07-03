package ru.zhigalov.whscalculator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.zhigalov.whscalculator.data.dao.CourseDao;
import ru.zhigalov.whscalculator.domain.models.Course;

@Database(entities = {Course.class}, version = 1)
public abstract class WHSCalculatorDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
