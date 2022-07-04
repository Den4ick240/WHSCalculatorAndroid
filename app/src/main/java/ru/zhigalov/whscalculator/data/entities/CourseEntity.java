package ru.zhigalov.whscalculator.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CourseEntity implements Serializable {
    @PrimaryKey
    public final Integer id;
    @ColumnInfo(name = "name")
    public final String name;
    @ColumnInfo(name = "slopeRating")
    public final int slopeRating;
    @ColumnInfo(name = "courseRating")
    public final int courseRating;
    @ColumnInfo(name = "par")
    public final int par;

    public CourseEntity(Integer id, String name, int slopeRating, int courseRating, int par) {
        this.id = id;
        this.name = name;
        this.slopeRating = slopeRating;
        this.courseRating = courseRating;
        this.par = par;
    }
}
