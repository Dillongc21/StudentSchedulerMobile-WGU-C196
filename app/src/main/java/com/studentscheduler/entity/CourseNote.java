package com.studentscheduler.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coursenotes")
public class CourseNote {
    
    @PrimaryKey(autoGenerate = true)
    private int courseNoteId;
    
    private String note;

    public int getCourseNoteId() {
        return courseNoteId;
    }

    public void setCourseNoteId(int courseNoteId) {
        this.courseNoteId = courseNoteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    private int courseId;
    
    public CourseNote(String note, int courseId) {
        this.note = note;
        this.courseId = courseId;
    }
    
    public CourseNote(CourseNote courseNote) {
        courseNoteId = courseNote.courseNoteId;
        note = courseNote.note;
        courseId = courseNote.courseId;
    }
}
