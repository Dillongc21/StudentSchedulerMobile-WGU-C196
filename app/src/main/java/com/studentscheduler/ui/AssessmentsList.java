package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Assessment;
import com.studentscheduler.entity.Course;
import com.studentscheduler.entity.CourseNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AssessmentsList extends AppCompatActivity {

    private int id;

    private Repository repo;

    private Course displayCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView courseTitleView = findViewById(R.id.assessmentsListCourseTitle);
        TextView courseStartView = findViewById(R.id.assessmentsListCourseStart);
        TextView courseEndView = findViewById(R.id.assessmentsListCourseEnd);
        TextView courseINameView = findViewById(R.id.assessmentsListCourseInstructorName);
        TextView courseIPhoneView = findViewById(R.id.assessmentsListCourseInstructorPhone);
        TextView courseIEmailView = findViewById(R.id.assessmentsListCourseInstructorEmail);
        TextView courseStatusView = findViewById(R.id.assessmentsListCourseStatus);

        id = getIntent().getIntExtra("id", -1);

        repo = new Repository(getApplication());

        displayCourse = repo.getAllCourses()
                .stream().filter(course -> course.getCourseId() == id)
                .collect(Collectors.toList()).get(0);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String startFormatted = sdf.format(displayCourse.getStart());
        String endFormatted = sdf.format(displayCourse.getEnd());

        courseTitleView.setText(displayCourse.getTitle());
        courseStartView.setText(startFormatted);
        courseEndView.setText(endFormatted);
        courseINameView.setText(displayCourse.getInstructorName());
        courseIPhoneView.setText(displayCourse.getInstructorPhone());
        courseIEmailView.setText(displayCourse.getInstructorEmail());
        courseStatusView.setText(displayCourse.getStatus());

        RecyclerView assessmentsRecyclerView = findViewById(R.id.assessmentsListAssessmentsRecyclerView);
        List<Assessment> courseAssessments = repo.getAllAssessments().stream()
                .filter(assessment -> assessment.getCourseId() == id)
                .collect(Collectors.toList());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentsRecyclerView.setAdapter(assessmentAdapter);
        assessmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(courseAssessments);

        RecyclerView courseNotesRecyclerView = findViewById(R.id.assessmentsListNotesRecyclerView);
        List<CourseNote> courseNotes = repo.getCourseNotesByCourseId(id);
        final CourseNoteAdapter courseNoteAdapter = new CourseNoteAdapter(this);
        courseNotesRecyclerView.setAdapter(courseNoteAdapter);
        courseNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseNoteAdapter.setCourseNotes(courseNotes);
    }

    public void goToAddAssessment(View view) {
        Intent intent = new Intent(AssessmentsList.this, AssessmentDetails.class);
        intent.putExtra("assessmentId", -1);
        intent.putExtra("courseId", id);
        startActivity(intent);
    }

    public void goToEditCourseDetails(View view) {
        Intent intent = new Intent(AssessmentsList.this, CourseDetails.class);
        intent.putExtra("courseId", id);
        startActivity(intent);
    }

    public void goToAddNote(View view) {
        Intent intent = new Intent(AssessmentsList.this, CourseNoteDetails.class);
        intent.putExtra("courseNoteId", -1);
        intent.putExtra("courseId", id);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        int termId = displayCourse.getTermId();
        repo.delete(displayCourse);
        Intent intent = new Intent(AssessmentsList.this, CoursesList.class);
        intent.putExtra("id", termId);
        startActivity(intent);
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentslist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AssessmentsList.this, CoursesList.class);
                intent.putExtra("id", displayCourse.getTermId());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}