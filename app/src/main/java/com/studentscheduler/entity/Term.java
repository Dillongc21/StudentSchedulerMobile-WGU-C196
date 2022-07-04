package com.studentscheduler.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;

    private String title;
    private Date start;
    private Date end;

    public Term(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }
    public Term(Term copyTerm) {
        this.termId = copyTerm.termId;
        this.title = copyTerm.title;
        this.start = copyTerm.start;
        this.end = copyTerm.end;
    }

    @NonNull
    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", name='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public int getTermId() { return termId; }
    public String getTitle() { return title; }
    public Date getStart() { return start; }
    public Date getEnd() { return end; }

    public void setTermId(int termId) {
        this.termId = termId;
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
}
