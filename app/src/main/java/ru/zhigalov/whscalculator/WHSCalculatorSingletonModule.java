package ru.zhigalov.whscalculator;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import ru.zhigalov.whscalculator.data.WHSCalculatorDatabase;
import ru.zhigalov.whscalculator.data.mappers.CourseMapper;
import ru.zhigalov.whscalculator.data.repository.CourseRepositoryRoom;
import ru.zhigalov.whscalculator.domain.repository.CourseRepository;

@Module
@InstallIn(SingletonComponent.class)
public class WHSCalculatorSingletonModule {
    @Provides
    public WHSCalculatorDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, WHSCalculatorDatabase.class,
                "ru.zhigalov.whscalculator")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public CourseRepository provideCourseRepository(WHSCalculatorDatabase database, CourseMapper courseMapper) {
        return new CourseRepositoryRoom(database.courseDao(), courseMapper);
    }

    @Provides
    public CourseMapper provideCourseMapper() {
        return new CourseMapper();
    }
}