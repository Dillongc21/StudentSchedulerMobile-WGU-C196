package com.studentscheduler.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String title;
    private Date start;
    private Date end;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int associatedTermId;

    public Course(String title, Date start, Date end, String status, String instructorName,
                  String instructorPhone, String instructorEmail, int associatedTermId) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.associatedTermId = associatedTermId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() { return title; }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getStatus() {
        return status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public int getAssociatedTermId() {
        return associatedTermId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public void setAssociatedTermId(int associatedTermId) {
        this.associatedTermId = associatedTermId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", start=" + start +
                ", end=" + end +
                ", status=" + status +
                ", instructorName='" + instructorName + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", associatedTermId=" + associatedTermId +
                '}';
    }
}
