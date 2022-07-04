package com.studentscheduler.db;

import android.app.Application;

import com.studentscheduler.dao.AssessmentDao;
import com.studentscheduler.dao.CourseDao;
import com.studentscheduler.dao.TermDao;
import com.studentscheduler.entity.Assessment;
import com.studentscheduler.entity.Course;
import com.studentscheduler.entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final TermDao mTermDao;
    private final CourseDao mCourseDao;
    private final AssessmentDao mAssessmentDao;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;

    private static final int NUM_THREADS=4;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public Repository(Application application) {
        StudentSchedulerDBBuilder db = StudentSchedulerDBBuilder.getDBInstance(application);
        mTermDao = db.termDao();
        mCourseDao = db.courseDao();
        mAssessmentDao = db.assessmentDao();
    }

    // CREATE
    public void insert(Term term) {
        dbExecutor.execute(() -> {
            mTermDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert(Course course) {
        dbExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert(Assessment assessment) {
        dbExecutor.execute(() -> {
            mAssessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // READ
    public List<Term> getAllTerms() {
        dbExecutor.execute(() -> {
            mAllTerms = mTermDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public List<Course> getAllCourses() {
        dbExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public List<Assessment> getAllAssessments() {
        dbExecutor.execute(() -> {
            mAllAssessments = mAssessmentDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    // UPDATE
    public void update(Term term) {
        dbExecutor.execute(() -> {
            mTermDao.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Course course) {
        dbExecutor.execute(() -> {
            mCourseDao.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment) {
        dbExecutor.execute(() -> {
            mAssessmentDao.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(Term term) {
        dbExecutor.execute(() -> {
            mTermDao.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Course course) {
        dbExecutor.execute(() -> {
            mCourseDao.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment) {
        dbExecutor.execute(() -> {
            mAssessmentDao.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
