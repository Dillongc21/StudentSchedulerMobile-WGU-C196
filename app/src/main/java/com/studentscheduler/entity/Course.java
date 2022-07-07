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
    private int startNotifyId;
    private int endNotifyId;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termId;

    public Course(String title, Date start, Date end, int startNotifyId, int endNotifyId,
                  String status, String instructorName, String instructorPhone,
                  String instructorEmail, int termId) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.startNotifyId = startNotifyId;
        this.endNotifyId = endNotifyId;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.termId = termId;
    }

    public Course(Course course) {
        courseId = course.courseId;
        title = course.title;
        start = course.start;
        end = course.end;
        startNotifyId = course.startNotifyId;
        endNotifyId = course.endNotifyId;
        status = course.status;
        instructorName = course.instructorName;
        instructorPhone = course.instructorPhone;
        instructorEmail = course.instructorEmail;
        termId = course.termId;
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

    public int getStartNotifyId() {
        return startNotifyId;
    }

    public int getEndNotifyId() {
        return endNotifyId;
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

    public int getTermId() {
        return termId;
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

    public void setStartNotifyId(int startNotifyId) {
        this.startNotifyId = startNotifyId;
    }

    public void setEndNotifyId(int endNotifyId) {
        this.endNotifyId = endNotifyId;
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

    public void setTermId(int termId) {
        this.termId = termId;
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
                ", associatedTermId=" + termId +
                '}';
    }
}
