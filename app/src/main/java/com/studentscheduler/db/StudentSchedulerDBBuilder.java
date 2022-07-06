package com.studentscheduler.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.studentscheduler.dao.AssessmentDao;
import com.studentscheduler.dao.CourseDao;
import com.studentscheduler.dao.CourseNoteDao;
import com.studentscheduler.dao.TermDao;
import com.studentscheduler.entity.Assessment;
import com.studentscheduler.entity.Course;
import com.studentscheduler.entity.CourseNote;
import com.studentscheduler.entity.Term;

@Database(entities={Term.class, Course.class, Assessment.class, CourseNote.class}, version=6, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class StudentSchedulerDBBuilder extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract CourseNoteDao courseNoteDao();

    public static volatile StudentSchedulerDBBuilder INSTANCE;

    static StudentSchedulerDBBuilder getDBInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudentSchedulerDBBuilder.class) {
                INSTANCE = Room.databaseBuilder(context, StudentSchedulerDBBuilder.class, "myStudentSchedulerDB")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
