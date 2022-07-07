package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.CourseNote;

import java.util.List;
import java.util.stream.Collectors;

public class CourseNoteDetails extends AppCompatActivity {

    private EditText noteField;

    private int courseId;
    private int courseNoteId;

    private CourseNote toEdit;

    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note_details);

        repo = new Repository(getApplication());

        courseId = getIntent().getIntExtra("courseId", -1);
        courseNoteId = getIntent().getIntExtra("courseNoteId", -1);

        noteField = findViewById(R.id.courseNoteDetailsNote);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (courseNoteId == -1) {
            getSupportActionBar().setTitle("Add Course Note");
        } else {
            getSupportActionBar().setTitle("Edit Course Note");
            toEdit = repo.getCourseNotesByCourseId(courseId).stream()
                    .filter(courseNote -> courseNote.getCourseNoteId() == courseNoteId)
                    .collect(Collectors.toList()).get(0);
            noteField.setText(toEdit.getNote());
        }
    }

    public void saveCourseNote(View view) {
        if (courseNoteId == -1) {
            CourseNote newNote = new CourseNote(noteField.getText().toString(), courseId);
            repo.insert(newNote);
        } else {
            CourseNote courseNoteCopy = new CourseNote(toEdit);
            courseNoteCopy.setNote(noteField.getText().toString());
            repo.update(courseNoteCopy);
        }
        Intent intent = new Intent(CourseNoteDetails.this, AssessmentsList.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
        this.finish();
    }

    public void cancelActivity(View view) {
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursenotedetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}