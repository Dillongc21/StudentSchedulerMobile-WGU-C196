package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.CourseNote;

import java.util.List;
import java.util.stream.Collectors;

public class CourseNoteDisplay extends AppCompatActivity {

    private int courseNoteId;
    private CourseNote displayCourseNote;

    private TextView noteView;

    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note_display);

        noteView = findViewById(R.id.courseNoteDisplayNote);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseNoteId = getIntent().getIntExtra("courseNoteId", -1);
        int courseId = getIntent().getIntExtra("courseId", -1);

        repo = new Repository(getApplication());
        List<CourseNote> courseNotes = repo.getCourseNotesByCourseId(courseId);

        displayCourseNote = courseNotes.stream()
                .filter(courseNote -> courseNote.getCourseNoteId() == courseNoteId)
                .collect(Collectors.toList()).get(0);

        noteView.setText(displayCourseNote.getNote());
    }

    public void editCourseNote(View view) {
        Intent intent = new Intent(CourseNoteDisplay.this, CourseNoteDetails.class);
        intent.putExtra("courseId", displayCourseNote.getCourseId());
        intent.putExtra("courseNoteId", displayCourseNote.getCourseNoteId());
        startActivity(intent);
    }

    public void deleteCourseNote(View view) {
        int courseId = displayCourseNote.getCourseId();
        repo.delete(displayCourseNote);
        Intent intent = new Intent(CourseNoteDisplay.this, AssessmentsList.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursenotedisplay, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CourseNoteDisplay.this, AssessmentsList.class);
                intent.putExtra("id", displayCourseNote.getCourseId());
                startActivity(intent);
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, noteView.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}