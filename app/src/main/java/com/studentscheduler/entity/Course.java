package com.studentscheduler.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private Date start;
    private Date end;
    private Status status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int associatedTermId;
    private String notes;

    public Course(Date start, Date end, Status status, String instructorName,
                  String instructorPhone, String instructorEmail, int associatedTermId,
                  String notes) {
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.associatedTermId = associatedTermId;
        this.notes = notes;
    }

    public int getCourseId() {
        return courseId;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Status getStatus() {
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

    public String getNotes() {
        return notes;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setStatus(Status status) {
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

    public void setNotes(String notes) {
        this.notes = notes;
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
