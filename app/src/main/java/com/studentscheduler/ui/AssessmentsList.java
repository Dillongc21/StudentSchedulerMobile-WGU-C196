package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Course;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class AssessmentsList extends AppCompatActivity {

    private int id;

    private String startFormatted;
    private String endFormatted;

    SimpleDateFormat sdf;

    TextView courseTitleView;
    TextView courseStartView;
    TextView courseEndView;
    TextView courseINameView;
    TextView courseIPhoneView;
    TextView courseIEmailView;
    TextView courseStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_list);

        courseTitleView = findViewById(R.id.assessmentsListCourseTitle);
        courseStartView = findViewById(R.id.assessmentsListCourseStart);
        courseEndView = findViewById(R.id.assessmentsListCourseEnd);
        courseINameView = findViewById(R.id.assessmentsListCourseInstructorName);
        courseIPhoneView = findViewById(R.id.assessmentsListCourseInstructorPhone);
        courseIEmailView = findViewById(R.id.assessmentsListCourseInstructorEmail);
        courseStatusView = findViewById(R.id.assessmentsListCourseStatus);

        id = getIntent().getIntExtra("id", -1);

        Repository repo = new Repository(getApplication());

        Course displayCourse = repo.getAllCourses()
                .stream().filter(course -> course.getCourseId() == id)
                .collect(Collectors.toList()).get(0);

        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        startFormatted = sdf.format(displayCourse.getStart());
        endFormatted = sdf.format(displayCourse.getEnd());

        courseTitleView.setText(displayCourse.getTitle());
        courseStartView.setText(startFormatted);
        courseEndView.setText(endFormatted);
        courseINameView.setText(displayCourse.getInstructorName());
        courseIPhoneView.setText(displayCourse.getInstructorPhone());
        courseIEmailView.setText(displayCourse.getInstructorEmail());
        courseStatusView.setText(displayCourse.getStatus());
    }

    public void goToAddAssessment(View view) {
    }

    public void goToEditCourseDetails(View view) {
    }
}