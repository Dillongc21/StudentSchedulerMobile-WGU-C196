package com.studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studentscheduler.entity.CourseNote;

import java.util.List;

@Dao
public interface CourseNoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseNote courseNote);

    @Update
    void update(CourseNote courseNote);

    @Delete
    void delete(CourseNote courseNote);

    @Query("SELECT * FROM coursenotes WHERE courseId = :courseId ORDER BY courseNoteId ASC")
    List<CourseNote> getCourseNotesByCourseId(int courseId);
}
