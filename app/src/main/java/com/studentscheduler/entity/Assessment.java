package com.studentscheduler.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String title;
    private Date start;
    private Date end;
    private int startNotifyId;
    private int endNotifyId;
    private String type;
    private int courseId;

    public Assessment(String title, Date start, Date end, int startNotifyId, int endNotifyId,
                      String type, int courseId) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.startNotifyId = startNotifyId;
        this.endNotifyId = endNotifyId;
        this.type = type;
        this.courseId = courseId;
    }

    public Assessment(Assessment assessment) {
        assessmentId = assessment.assessmentId;
        title = assessment.title;
        start = assessment.start;
        end = assessment.end;
        type = assessment.type;
        courseId = assessment.courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getStartNotifyId() {
        return startNotifyId;
    }

    public void setStartNotifyId(int startNotifyId) {
        this.startNotifyId = startNotifyId;
    }

    public int getEndNotifyId() {
        return endNotifyId;
    }

    public void setEndNotifyId(int endNotifyId) {
        this.endNotifyId = endNotifyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", type=" + type +
                '}';
    }
}
