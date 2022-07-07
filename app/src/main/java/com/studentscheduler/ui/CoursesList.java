package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Course;
import com.studentscheduler.entity.Term;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CoursesList extends AppCompatActivity {

    int id;
    String title;
    String startFormatted;
    String endFormatted;

    SimpleDateFormat sdf;

    Repository repo;

    Term displayTerm;

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

        repo = new Repository(getApplication());

        displayTerm = repo.getAllTerms().stream()
                .filter(term -> term.getTermId() == id)
                .collect(Collectors.toList()).get(0);

        title = displayTerm.getTitle();
        Date start = displayTerm.getStart();
        Date end = displayTerm.getEnd();

        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        startFormatted = sdf.format(start);
        endFormatted = sdf.format(end);

        termTitleView.setText(title);
        termStartView.setText(startFormatted);
        termEndView.setText(endFormatted);

        RecyclerView recyclerView = findViewById(R.id.coursesListRecyclerView);
        List<Course> termCourses = repo.getAllCourses().stream()
                .filter(course -> course.getTermId() == id)
                .collect(Collectors.toList());
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(termCourses);
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

    public void deleteTerm(View view) {
        List<Course> termCourses = repo.getAllCourses().stream()
                .filter(course -> course.getTermId() == id)
                .collect(Collectors.toList());

        if (!termCourses.isEmpty()) {
            new AlertDialog.Builder(CoursesList.this)
                    .setTitle("Delete All Term Courses")
                    .setMessage("You must delete all courses associated with this term before deletion.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            repo.delete(displayTerm);
            Intent intent = new Intent(CoursesList.this, TermsList.class);
            startActivity(intent);
        }
    }
}