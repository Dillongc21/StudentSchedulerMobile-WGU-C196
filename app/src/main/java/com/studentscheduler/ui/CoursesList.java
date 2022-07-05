package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.studentscheduler.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CoursesList extends AppCompatActivity {

    int id;
    String title;
    String startFormatted;
    String endFormatted;

    SimpleDateFormat sdf;

    TextView termTitleView;
    TextView termStartView;
    TextView termEndView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        termTitleView = findViewById(R.id.coursesListTermTitle);
        termStartView = findViewById(R.id.coursesListTermStart);
        termEndView = findViewById(R.id.coursesListTermEnd);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        Date start = (Date)getIntent().getSerializableExtra("start");
        Date end = (Date)getIntent().getSerializableExtra("end");

        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        startFormatted = sdf.format(start);
        endFormatted = sdf.format(end);

        termTitleView.setText(title);
        termStartView.setText(startFormatted);
        termEndView.setText(endFormatted);
    }


    public void goToEditTermDetails(View view) {
        TermDetails.editTermId = id;
        Intent intent = new Intent(CoursesList.this, TermDetails.class);
        startActivity(intent);
    }

    public void goToAddCourse(View view) {
        Intent intent = new Intent(CoursesList.this, CourseDetails.class);
        intent.putExtra("termId", id);
        intent.putExtra("courseId", -1);
        startActivity(intent);
    }
}